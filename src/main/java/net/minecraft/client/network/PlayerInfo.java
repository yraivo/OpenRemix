package net.minecraft.client.network;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.network.play.server.S38PacketPlayerListItemAddPlayerData;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettingsGameType;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.render.NameProtectModule;

public class PlayerInfo {
    private long healthBlinkUntilTick;
    private long lastHealthChangeMillis;
    private GameProfile gameProfile;
    private ResourceLocation locationCape;
    private WorldSettingsGameType gameType;
    private long lastTabOpenMillis;

    private int displayHealth;
    private boolean playerTexturesLoaded = false;
    private ChatComponent displayName;
    private int responseTime;

    private ResourceLocation locationSkin;

    private String skinType;
    private int lastHealth = 0;

    public void setLastTabOpenMillis(long i) {
        this.lastTabOpenMillis = i;
    }


    public void setLastHealth(int i) {
        this.lastHealth = i;
    }

    protected void setResponseTime(int responseTimeValue) {
        this.responseTime = responseTimeValue;
    }

    public GameProfile getGameProfile() {
        return this.gameProfile;
    }

    public void setDisplayName(ChatComponent avWValue) {
        this.displayName = avWValue;
    }

    public PlayerInfo(GameProfile gameprofile) {
        this.displayHealth = 0;

        this.lastHealthChangeMillis = 0L;

        this.healthBlinkUntilTick = 0L;

        this.lastTabOpenMillis = 0L;
        this.gameProfile = gameprofile;
    }

    protected void loadPlayerTextures() {
        synchronized (this) {
            if (!this.playerTexturesLoaded) {
                this.playerTexturesLoaded = true;
                MinecraftClient.getMinecraft()
                        .getSkinManager()
                        .loadProfileTextures(
                                this.gameProfile,
                                (type, resourceLocation, minecraftprofiletexture) -> {
                                    ClientPlayHandler.getLegacyPlayHandlerFlowMarker();

                                    switch (type) {
                                        case SKIN:
                                            this.locationSkin = resourceLocation;
                                            this.skinType =
                                                    minecraftprofiletexture.getMetadata("model");
                                            if (this.skinType != null) {
                                                break;
                                            }

                                            this.skinType = "default";

                                            break;

                                        case CAPE:
                                            this.locationCape = resourceLocation;
                                    }
                                },
                                true);
            }
        }
    }

    public int getResponseTime() {
        return this.responseTime;
    }

    public ResourceLocation getLocationCape() {
        if (this.locationCape == null) {
            this.loadPlayerTextures();
        }

        return this.locationCape;
    }

    public boolean hasLocationSkin() {
        return this.locationSkin != null;
    }

    public long getLastHealthChangeMillis() {
        return this.lastHealthChangeMillis;
    }

    public int getLastHealth() {
        return this.lastHealth;
    }

    public void setLastHealthChangeMillis(long i) {
        this.lastHealthChangeMillis = i;
    }

    public ChatComponent getDisplayName() {
        return this.displayName;
    }

    public void setDisplayHealth(int i) {
        this.displayHealth = i;
    }

    public void setHealthBlinkUntilTick(long avUValue) {
        this.healthBlinkUntilTick = avUValue;
    }

    public ScorePlayerTeam getPlayerTeam() {
        return MinecraftClient.getMinecraft()
                .world
                .getScoreboard()
                .getPlayersTeam(this.getGameProfile().getName());
    }

    protected void setGameType(WorldSettingsGameType avVValue) {
        this.gameType = avVValue;
    }

    public WorldSettingsGameType getGameType() {
        return this.gameType;
    }

    public long getHealthBlinkUntilTick() {
        return this.healthBlinkUntilTick;
    }

    public int getDisplayHealth() {
        return this.displayHealth;
    }

    public long getLastTabOpenMillis() {
        return this.lastTabOpenMillis;
    }

    public String getSkinType() {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        NameProtectModule nameProtect = ClientServices.moduleManager.nameProtect;
        return this.skinType != null
                        && (!nameProtect.isEnabled() || !nameProtect.skinsSetting.asBoolean())
                ? this.skinType
                : DefaultPlayerSkin.getSkinType(this.gameProfile.getId());
    }

    public PlayerInfo(S38PacketPlayerListItemAddPlayerData s38PacketPlayerListItemAddPlayerData) {
        this.displayHealth = 0;

        this.lastHealthChangeMillis = 0L;

        this.healthBlinkUntilTick = 0L;

        this.lastTabOpenMillis = 0L;
        this.gameProfile = s38PacketPlayerListItemAddPlayerData.getProfile();
        this.gameType = s38PacketPlayerListItemAddPlayerData.getGameMode();
        this.responseTime = s38PacketPlayerListItemAddPlayerData.getPing();
    }

    public ResourceLocation getLocationSkin() {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        NameProtectModule nameProtect = ClientServices.moduleManager.nameProtect;
        if (nameProtect.isEnabled() && nameProtect.skinsSetting.asBoolean()) {
            return DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId());
        }

        if (this.locationSkin == null) {
            this.loadPlayerTextures();
        }

        return (ResourceLocation)
                com.google.common.base.MoreObjects.firstNonNull(
                        this.locationSkin,
                        DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId()));
    }
}
