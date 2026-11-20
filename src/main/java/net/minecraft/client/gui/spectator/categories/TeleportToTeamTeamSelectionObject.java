package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Random;

class TeleportToTeamTeamSelectionObject implements ISpectatorMenuObject {
    private List va;
    TeleportToTeam auS;
    private ResourceLocation amq;
    private ScorePlayerTeam auT;

    public void method_8b0a960c841ff568c052(float f, int i) {
        int l = TeleportToTeam.method_7eec03da329a017ec143();
        int k = -1;
        String text = FontRenderer.getFormatFromString(this.auT.getColorPrefix());
        int j = l;
        if (text.length() >= 2) {
            k = MinecraftClient.getMinecraft().fontRendererObj.getColorCode(text.charAt(1));
        }

        if (k >= 0) {
            float f1 = (k >> 16 & 0xFF) / 255.0F;
            float f2 = (k >> 8 & 0xFF) / 255.0F;
            float f3 = (k & 0xFF) / 255.0F;
            Gui.drawRect(
                    1.0,
                    1.0,
                    15.0,
                    15.0,
                    MathHelper.packNormalizedRgb(f1 * f, f2 * f, f3 * f) | i << 24);
        }

        MinecraftClient.getMinecraft().getTextureManager().bindTexture(this.amq);
        GlStateManager.color(f, f, f, i / 255.0F);
        Gui.drawScaledCustomSizeModalRect(
                2.0F, 2.0F, 8.0F, 8.0F, 8.0F, 8.0F, 12.0F, 12.0F, 64.0F, 64.0F);
        Gui.drawScaledCustomSizeModalRect(
                2.0F, 2.0F, 40.0F, 8.0F, 8.0F, 8.0F, 12.0F, 12.0F, 64.0F, 64.0F);
        if (j != 0) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }
    }

    public TeleportToTeamTeamSelectionObject(TeleportToTeam auSValue, ScorePlayerTeam auTValue) {
        super();

        this.auS = auSValue;
        int j = TeleportToTeam.method_7eec03da329a017ec143();

        int i = j;
        this.auT = auTValue;
        this.va = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_2297 =
                        (auTValue.getMembershipCollection()).iterator();
                (__recoveryIterator171_2297).hasNext(); ) {
            String text = (String) (__recoveryIterator171_2297).next();
            PlayerInfo playerInfo =
                    MinecraftClient.getMinecraft().getNetworkHandler().getPlayerInfo(text);
            if (playerInfo != null) {
                this.va.add(playerInfo);
            }

            if (i != 0) {
                break;
            }
        }

        {
            if (!this.va.isEmpty()) {
                String name =
                        ((PlayerInfo) this.va.get(new Random().nextInt(this.va.size())))
                                .getGameProfile()
                                .getName();
                this.amq = AbstractClientPlayer.getLocationSkin(name);
                AbstractClientPlayer.getDownloadImageSkin(this.amq, name);
                if (!(i == 0)) {

                    this.amq = DefaultPlayerSkin.getDefaultSkinLegacy();
                }
            } else {

                this.amq = DefaultPlayerSkin.getDefaultSkinLegacy();
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            TeleportToTeam.b(++i);
        }
    }

    public ChatComponent getSpectatorName() {
        return new ChatComponentText(this.auT.getTeamName());
    }

    public void method_22ae0a8170d5c831fff3(SpectatorMenu spectatorMenu) {
        spectatorMenu.method_9f34d2c8942297ad165c(new TeleportToPlayer(this.va));
    }

    public boolean method_b417d7500ec59d69e373() {
        return !this.va.isEmpty();
    }
}
