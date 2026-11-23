package remix.ui.hud;

import net.minecraft.block.material.Material;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.util.MathHelper;
import remix.ui.hud.HudElement;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.client.RemixClient;
import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;

import org.lwjgl.opengl.GL11;

public final class InfoOverlayHudElement implements HudElement {
    private long timeUpdateIntervalMillis;

    private static String[] packedTextFragments;

    private Setting coordinatesSetting;
    private long previousTimeUpdateMillis;
    private Setting armorSetting;
    public Setting serverSetting;
    public Setting uidSetting;
    private long currentTimeUpdateMillis;
    private ClientFontRenderer clientFontRenderer;
    private Setting fpsSetting;
    private Setting tpsSetting;

    @Subscribe(mc = PacketEvent.class, priority = SubscriberPriority.HIGHEST)
    public void onPacket(PacketEvent event) {
        WatermarkHudElement.isWatermarkDisabled();

        if (event.getDirection() == PacketDirection.RECEIVED) {
            if (!ec.isIntegratedServerRunning()) {
                if (!(event.getPacket() instanceof S03PacketTimeUpdate)) {
                    return;
                }

                this.currentTimeUpdateMillis = System.currentTimeMillis();
                this.timeUpdateIntervalMillis = Math.abs(this.previousTimeUpdateMillis - this.currentTimeUpdateMillis);
                this.previousTimeUpdateMillis = this.currentTimeUpdateMillis;

                return;
            }

            this.timeUpdateIntervalMillis = 1000L;
        }
    }

    public void render(ScaledResolution scaledResolution) {
        boolean flag = ec.currentScreen instanceof GuiChat;
        float f = flag ? 24.5F : 12.0F;
        if (this.coordinatesSetting.asBoolean() || this.fpsSetting.asBoolean() || this.tpsSetting.asBoolean()) {
            String text = this.formatWorldStatusLine();
            this.clientFontRenderer.drawStringWithShadow(
                    text,
                    3.0,
                    scaledResolution.getHeight()
                            - (flag && ((GuiChat) ec.currentScreen).suggestedCommand != null ? f + 12.0F : f),
                    -1);
        }

        if (this.armorSetting.asBoolean()) {
            this.renderArmorStatus(scaledResolution);
        }

        if (this.serverSetting.asBoolean() || this.uidSetting.asBoolean()) {
            StringBuilder stringbuilder = new StringBuilder();
            if (this.uidSetting.asBoolean()) {
                stringbuilder.append("\u00a77");
                // Local builds have no remote licensing account or UID.
                stringbuilder.append("Local");
            }

            if (this.uidSetting.asBoolean() && this.serverSetting.asBoolean()) {
                stringbuilder.append(" - ");
            }

            if (this.serverSetting.asBoolean()) {
                stringbuilder.append("\u00a7f");
                stringbuilder.append(this.getServerVersionOrOffline());
            }

            String textValue = stringbuilder.toString();
            this.clientFontRenderer.drawStringWithShadow(
                    textValue,
                    scaledResolution.getWidth() - this.clientFontRenderer.getStringWidth(textValue) - 3,
                    scaledResolution.getHeight() - f,
                    -1);
        }
    }

    static {
        remix.ui.hud.InfoOverlayHudElement.packedTextFragments = new String[7];
        remix.ui.hud.InfoOverlayHudElement.packedTextFragments[0] = new String("");
        remix.ui.hud.InfoOverlayHudElement.packedTextFragments[1] = new String(" ");
        remix.ui.hud.InfoOverlayHudElement.packedTextFragments[2] = new String("");
        remix.ui.hud.InfoOverlayHudElement.packedTextFragments[3] = new String("");
        remix.ui.hud.InfoOverlayHudElement.packedTextFragments[4] = new String("");
    }

    public InfoOverlayHudElement(
            Setting coordinatesSetting,
            Setting fpsSetting,
            Setting tpsSetting,
            Setting armorSetting,
            Setting serverSetting,
            Setting uidSetting,
            ClientFontRenderer clientFontRenderer) {
        this.coordinatesSetting = coordinatesSetting;
        this.fpsSetting = fpsSetting;
        this.tpsSetting = tpsSetting;
        this.armorSetting = armorSetting;
        this.serverSetting = serverSetting;
        this.uidSetting = uidSetting;
        this.clientFontRenderer = clientFontRenderer;
        RemixClient.eventBus.register(this);
    }

    private String getServerVersionOrOffline() {
        return ec.getNetworkHandler() != null
                        && ec.getCurrentServerData() != null
                        && !ec.getNetworkHandler().getNetworkManager().isLocalChannel()
                ? ec.getCurrentServerData().gameVersion
                : "Offline";
    }

    private String formatWorldStatusLine() {
        StringBuilder text = new StringBuilder(64);
        if (this.coordinatesSetting.asBoolean()) {
            LocalPlayer player = ec.player;
            text.append("XYZ: \u00a77 ").append(MathHelper.floor(player.posX)).append(", ")
                    .append(MathHelper.floor(player.posY)).append(", ")
                    .append(MathHelper.floor(player.posZ)).append("  ");
        }
        if (this.fpsSetting.asBoolean()) {
            text.append("\u00a7fFPS: \u00a77 ").append(net.minecraft.client.MinecraftClient.getDebugFPS()).append("  ");
        }
        if (this.tpsSetting.asBoolean()) {
            double interval = this.timeUpdateIntervalMillis <= 0L ? 1.0 : this.timeUpdateIntervalMillis;
            long tps = Math.min(Math.round(20.0 * interval / 1000.0), 20L);
            text.append("\u00a7fTPS: \u00a77 ").append(tps);
        }
        return text.toString();
    }

    public void renderArmorStatus(ScaledResolution scaledResolution) {
        WatermarkHudElement.isWatermarkEnabled();

        if (ec.playerController.isNotCreative()) {
            LocalPlayer player = ec.player;
            FontRenderer anp = ec.fontRendererObj;
            ItemStack[] bkX = player.inventory.armorInventory;
            RenderItem renderItem = ec.getRenderItem();
            int i =
                    scaledResolution.getHeight()
                            - (player.isInsideOfMaterial(Material.water) ? 65 : 55);
            byte b0 = 15;
            GL11.glPushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            int j = 3;

            while (j >= 0) {
                ItemStack itemStack = bkX[j];
                if (itemStack != null) {
                    renderItem.renderItemModelIntoGUI(
                            itemStack, scaledResolution.getWidth() / 2 + b0, i);
                    renderItem.renderItemOverlayIntoGUI(
                            anp,
                            itemStack,
                            scaledResolution.getWidth() / 2 + b0,
                            i,
                            packedTextFragments[4]);
                    b0 += 18;
                }

                j--;
            }

            RenderHelper.disableStandardItemLighting();
            GL11.glPopMatrix();
        }
    }
}
