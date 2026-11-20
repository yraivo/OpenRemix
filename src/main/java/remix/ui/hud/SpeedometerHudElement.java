package remix.ui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;

import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;

public final class SpeedometerHudElement implements HudElement {
    public static boolean jumpSampleRequested;
    private static String[] packedTextFragments;
    public static final double ix = 3.0;
    private double relativeSpeedDelta;
    private long lastJumpSampleMillis;
    private boolean previousJumpSampleRequested;
    public static final boolean ENABLED_DEFAULT = true;
    public static final boolean d = false;
    private double jumpBaselineSpeed;
    private Setting uidSetting;
    private Setting serverSetting;
    private boolean firstJumpSample;

    private double firstJumpSpeed = 0.0;
    public static SpeedUnit speedUnit;
    private double absoluteSpeedDelta;
    public static final int cz = 2;
    private double horizontalSpeed;
    private ClientFontRenderer clientFontRenderer;
    public static final boolean e = true;

    public static void setSpeedUnitByName(String text) {
        setSpeedUnit(SpeedUnit.fromName(text));
    }

    static {
        remix.ui.hud.SpeedometerHudElement.packedTextFragments = new String[2];
        remix.ui.hud.SpeedometerHudElement.packedTextFragments[0] = new String(" ");
        remix.ui.hud.SpeedometerHudElement.speedUnit = SpeedUnit.BLOCKSPERSECOND;
        remix.ui.hud.SpeedometerHudElement.jumpSampleRequested = false;
    }

    public SpeedometerHudElement(
            ClientFontRenderer clientFontRenderer, Setting serverSetting, Setting uidSetting) {
        this.jumpBaselineSpeed = 0.0;
        this.relativeSpeedDelta = 0.0;
        this.absoluteSpeedDelta = 0.0;
        this.previousJumpSampleRequested = false;

        this.lastJumpSampleMillis = 0L;
        this.firstJumpSample = true;
        this.horizontalSpeed = 0.0;
        this.clientFontRenderer = clientFontRenderer;
        this.serverSetting = serverSetting;
        this.uidSetting = uidSetting;
    }

    private boolean isJumpResetActive() {

        return this.lastJumpSampleMillis != 0L && this.secondsSinceJumpSample() <= 3.0F;
    }

    public static void setSpeedUnit(SpeedUnit speedUnit) {
        WatermarkHudElement.isWatermarkEnabled();

        if (speedUnit == null) {
            remix.ui.hud.SpeedometerHudElement.speedUnit = SpeedUnit.BLOCKSPERSECOND;

            return;
        }

        remix.ui.hud.SpeedometerHudElement.speedUnit = speedUnit;
    }

    private float secondsSinceJumpSample() {
        return (float) (MinecraftClient.getSystemTime() - this.lastJumpSampleMillis) / 1000.0F;
    }

    public void render(ScaledResolution scaledResolution) {
        MinecraftClient client = MinecraftClient.getMinecraft();
        if (client == null || client.player == null || client.world == null) {
            this.resetSpeedSamples();
            return;
        }

        if (this.jumpBaselineSpeed != 0.0 && this.isJumpResetActive()) {

        } else {

        }

        String sx = "%.2f";
        String text = packedTextFragments[0] + remix.ui.hud.SpeedometerHudElement.speedUnit.name;
        String textValue =
                (String.format(
                        sx + "%s",
                        remix.ui.hud.SpeedometerHudElement.speedUnit.convert(this.horizontalSpeed),
                        text));
        if (!this.firstJumpSample) {
            String.format(
                    "%+.2f (%+.1f%%)",
                    remix.ui.hud.SpeedometerHudElement.speedUnit.convert(this.absoluteSpeedDelta),
                    this.relativeSpeedDelta * 100.0);
        } else {
            String.format(
                    sx, remix.ui.hud.SpeedometerHudElement.speedUnit.convert(this.firstJumpSpeed));
        }

        {
            int i = !this.uidSetting.asBoolean() && !this.serverSetting.asBoolean() ? 0 : 1;
            float f =
                    client.currentScreen instanceof GuiChat
                            ? (i != 0 ? 30.5F : 24.5F)
                            : (i != 0 ? 18 : 7);
            int j = scaledResolution.getWidth();
            int k = scaledResolution.getHeight() - 4;
            this.clientFontRenderer.drawStringWithShadow(
                    textValue,
                    j - this.clientFontRenderer.getStringWidth(textValue) - 3,
                    k - f,
                    14737632);
        }
    }

    public void updateSpeedSample() {
        WatermarkHudElement.isWatermarkEnabled();

        MinecraftClient client = MinecraftClient.getMinecraft();
        LocalPlayer player = client == null ? null : client.player;
        if (player == null || client.world == null) {
            this.resetSpeedSamples();
            return;
        }
        double d0 = player.posX - player.prevPosX;

        double d1 = player.posZ - player.prevPosZ;
        this.horizontalSpeed = MathHelper.sqrt(d0 * d0 + d1 * d1);
        if ((this.isJumpResetActive() || remix.ui.hud.SpeedometerHudElement.jumpSampleRequested)
                && (!player.onGround
                        || player.movementInput.jump
                        || player.capabilities.isFlying)) {
            if (remix.ui.hud.SpeedometerHudElement.jumpSampleRequested
                    && !this.previousJumpSampleRequested) {
                this.firstJumpSample =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (this.jumpBaselineSpeed == 0.0 ? 1 : 0));
                this.relativeSpeedDelta =
                        this.horizontalSpeed != 0.0
                                ? this.horizontalSpeed / this.jumpBaselineSpeed - 1.0
                                : -1.0;
                this.absoluteSpeedDelta = this.horizontalSpeed - this.jumpBaselineSpeed;
                this.lastJumpSampleMillis = MinecraftClient.getSystemTime();
                this.jumpBaselineSpeed = this.horizontalSpeed;
                this.firstJumpSpeed = this.firstJumpSample ? this.jumpBaselineSpeed : 0.0;
            }

            this.previousJumpSampleRequested =
                    remix.ui.hud.SpeedometerHudElement.jumpSampleRequested;

            return;
        }

        this.previousJumpSampleRequested = false;
        this.jumpBaselineSpeed = 0.0;
    }

    private void resetSpeedSamples() {
        this.horizontalSpeed = 0.0;
        this.jumpBaselineSpeed = 0.0;
        this.firstJumpSpeed = 0.0;
        this.relativeSpeedDelta = 0.0;
        this.absoluteSpeedDelta = 0.0;
        this.lastJumpSampleMillis = 0L;
        this.firstJumpSample = true;
        this.previousJumpSampleRequested = false;
        jumpSampleRequested = false;
    }
}
