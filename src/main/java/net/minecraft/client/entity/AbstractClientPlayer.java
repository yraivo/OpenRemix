package net.minecraft.client.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettingsGameType;
import optifine.CapeUtils;
import optifine.OptiFineConfig;
import optifine.PlayerConfigurations;
import optifine.Reflector;
import net.minecraft.client.gui.GuiScreen;
import remix.event.impl.SprintStateEvent;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.ModuleManager;
import remix.module.render.CameraModule;
import remix.module.render.NameProtectModule;

import com.mojang.authlib.GameProfile;

public abstract class AbstractClientPlayer extends PlayerEntity {

    private PlayerInfo playerInfo;
    private static Entity[] legacyClientPlayerMarker;

    private ResourceLocation locationOfCape = null;
    private String nameClear = null;

    public boolean isCreativeGameModeFromPlayerInfo() {
        GameProfile gameprofile = this.getGameProfile();
        if (gameprofile == null) {
            return false;
        }

        PlayerInfo playerInfo =
                MinecraftClient.getMinecraft()
                        .getNetworkHandler()
                        .getPlayerInfo(gameprofile.getId());
        return playerInfo != null && playerInfo.getGameType() == WorldSettingsGameType.CREATIVE;
    }

    static {
        setLegacyClientPlayerMarker(((net.minecraft.entity.Entity[]) (null)));
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public ResourceLocation getLocationSkin() {
        PlayerInfo playerInfo = this.getPlayerInfo();
        return playerInfo == null
                ? DefaultPlayerSkin.getDefaultSkin(this.getUniqueId())
                : playerInfo.getLocationSkin();
    }

    public static void setLegacyClientPlayerMarker(Entity[] aLkValue) {
        net.minecraft.client.entity.AbstractClientPlayer.legacyClientPlayerMarker = aLkValue;
    }

    public String getNameClear() {
        return this.nameClear;
    }

    public boolean hasSkin() {
        PlayerInfo playerInfo = this.getPlayerInfo();
        return playerInfo != null && playerInfo.hasLocationSkin();
    }

    public static ResourceLocation getLocationSkin(String text) {
        return new ResourceLocation("skins/" + StringUtils.stripControlCodes(text));
    }

    public void setSprinting(boolean flag) {
        SprintStateEvent sprintStateEvent = new SprintStateEvent(flag);
        RemixClient.eventBus.post(sprintStateEvent);
        flag = sprintStateEvent.isSprinting();
        super.setSprinting(flag);
        IAttributeInstance entityAttribute =
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (entityAttribute.getModifier(LivingEntity.sprintingSpeedBoostModifierUUID) != null) {
            entityAttribute.removeModifier(LivingEntity.sprintingSpeedBoostModifier);
        }

        if (flag) {
            entityAttribute.applyModifier(LivingEntity.sprintingSpeedBoostModifier);
        }
    }

    public float getFovModifier() {
        boolean legacyFlow = GuiScreen.getLegacyScreenFlowMarker() != null;
        float modifier = this.capabilities.isFlying ? 1.1F : 1.0F;
        float walkSpeed = this.capabilities.getWalkSpeed();
        IAttributeInstance movementSpeed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        modifier = (float) (modifier * ((movementSpeed.getAttributeValue() / walkSpeed + 1.0) / 2.0));
        if (walkSpeed == 0.0F || Float.isNaN(modifier) || Float.isInfinite(modifier)) {
            modifier = 1.0F;
        }

        if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
            float drawProgress = this.getItemInUseDuration() / 20.0F;
            if (drawProgress > 1.0F) {
                drawProgress = 1.0F;
                if (legacyFlow) {
                    drawProgress *= drawProgress;
                }
            } else {
                drawProgress *= drawProgress;
            }
            modifier *= 1.0F - drawProgress * 0.15F;
        }
        return Reflector.forgeGetOffsetFov.exists() ? Reflector.callFloat(Reflector.forgeGetOffsetFov, new Object[] {this, modifier}) : modifier;
    }

    protected PlayerInfo getPlayerInfo() {
        if (this.playerInfo == null) {
            this.playerInfo =
                    MinecraftClient.getMinecraft()
                            .getNetworkHandler()
                            .getPlayerInfo(this.getUniqueId());
        }

        return this.playerInfo;
    }

    public void setLocationOfCape(ResourceLocation aLmValue) {
        this.locationOfCape = aLmValue;
    }

    public AbstractClientPlayer(World worldValue, GameProfile gameprofile) {
        super(worldValue, gameprofile);
        this.nameClear = gameprofile.getName();
        if (this.nameClear != null && !this.nameClear.isEmpty()) {
            this.nameClear = StringUtils.stripControlCodes(this.nameClear);
        }

        CapeUtils.downloadCape(this);
        PlayerConfigurations.getPlayerConfiguration(this);
    }

    public static Entity[] getLegacyClientPlayerMarker() {
        return net.minecraft.client.entity.AbstractClientPlayer.legacyClientPlayerMarker;
    }

    public String getSkinType() {
        PlayerInfo playerInfo = this.getPlayerInfo();
        return playerInfo == null
                ? DefaultPlayerSkin.getSkinType(this.getUniqueId())
                : playerInfo.getSkinType();
    }

    public ResourceLocation v_ResourceLocation() {
        return this.locationOfCape;
    }

    public static ThreadDownloadImageData getDownloadImageSkin(
            ResourceLocation resourceLocation, String text) {
        TextureManager textureManager = MinecraftClient.getMinecraft().getTextureManager();
        Object texture = textureManager.getTexture(resourceLocation);
        if (texture == null) {
            texture =
                    new ThreadDownloadImageData(
                            null,
                            String.format(
                                    "http://skins.minecraft.net/MinecraftSkins/%s.png",
                                    StringUtils.stripControlCodes(text)),
                            DefaultPlayerSkin.getDefaultSkin(
                                    getOfflineUUID(text)),
                            new ImageBufferDownload());
            textureManager.loadTexture(resourceLocation, (ITextureObject) texture);
        }

        return (ThreadDownloadImageData) texture;
    }

    public ResourceLocation getLocationCape() {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        ModuleManager moduleManager = ClientServices.moduleManager;
        CameraModule camera = moduleManager.camera;
        NameProtectModule nameProtect = moduleManager.nameProtect;
        if (OptiFineConfig.isShowCapes()
                && (!nameProtect.isEnabled() || !nameProtect.capesSetting.asBoolean())) {
            if (this == MinecraftClient.getMinecraft().player
                    && camera.isEnabled()
                    && camera.capeSetting.asBoolean()) {
                return camera.capeTexture;
            }

            if (this.locationOfCape != null) {
                return this.locationOfCape;
            }

            PlayerInfo playerInfo = this.getPlayerInfo();
            return playerInfo == null ? null : playerInfo.getLocationCape();
        } else {
            return null;
        }
    }

    public boolean isSpectator() {
        GameProfile gameprofile = this.getGameProfile();
        if (gameprofile != null && MinecraftClient.getMinecraft().getNetworkHandler() != null) {
            PlayerInfo playerInfo =
                    MinecraftClient.getMinecraft()
                            .getNetworkHandler()
                            .getPlayerInfo(gameprofile.getId());
            return playerInfo != null
                    && playerInfo.getGameType() == WorldSettingsGameType.SPECTATOR;
        } else {
            return false;
        }
    }

    public boolean hasPlayerInfo() {
        return this.getPlayerInfo() != null;
    }
}
