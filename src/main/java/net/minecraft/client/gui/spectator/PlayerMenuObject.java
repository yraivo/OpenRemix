package net.minecraft.client.gui.spectator;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

import com.mojang.authlib.GameProfile;

public class PlayerMenuObject implements ISpectatorMenuObject {
    private ResourceLocation resourceLocation;
    private GameProfile profile;

    @Override
    public boolean method_b417d7500ec59d69e373() {
        return true;
    }

    @Override
    public void method_8b0a960c841ff568c052(float f, int i) {
        String text = SpectatorMenu.cI();
        MinecraftClient.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
        GlStateManager.color(1.0F, 1.0F, 1.0F, i / 255.0F);
        String textValue = text;
        Gui.drawScaledCustomSizeModalRect(
                2.0F, 2.0F, 8.0F, 8.0F, 8.0F, 8.0F, 12.0F, 12.0F, 64.0F, 64.0F);
        Gui.drawScaledCustomSizeModalRect(
                2.0F, 2.0F, 40.0F, 8.0F, 8.0F, 8.0F, 12.0F, 12.0F, 64.0F, 64.0F);
        if (textValue == null) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }
    }

    public PlayerMenuObject(GameProfile gameprofile) {
        this.profile = gameprofile;
        this.resourceLocation = AbstractClientPlayer.getLocationSkin(gameprofile.getName());
        AbstractClientPlayer.getDownloadImageSkin(this.resourceLocation, gameprofile.getName());
    }

    @Override
    public ChatComponent getSpectatorName() {
        return new ChatComponentText(this.profile.getName());
    }

    @Override
    public void method_22ae0a8170d5c831fff3(SpectatorMenu spectatorMenu) {
        MinecraftClient.getMinecraft()
                .getNetworkHandler()
                .addToSendQueue(new C18PacketSpectate(this.profile.getId()));
    }
}
