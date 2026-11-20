package remix.ui.hud;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import remix.ui.hud.HudElement;
import remix.util.render.Render2D3DUtils;
import remix.setting.Setting;

public final class CompassHudElement implements HudElement {
    private String markerColorCode;

    private float drawDepth;
    private static String[] packedTextFragments;

    private Setting setting;
    private int textureRow;

    public void render(ScaledResolution scaledResolution) {
        int j;
        int k;
        {
            WatermarkHudElement.isWatermarkDisabled();

            int i = MathHelper.floor(ec.player.rotationYaw * 256.0F / 360.0F - 0.5) & 0xFF;

            j =
                    this.computeVerticalOffset(
                            scaledResolution,
                            1,
                            BossStatus.bossName != null && BossStatus.statusBarTime > 0 ? 42 : 12);
            k = this.centerHorizontally(scaledResolution, 65);
            ec.getTextureManager().bindTexture(new ResourceLocation("remix/Compass.png"));
            this.textureRow = (int) this.setting.asDouble();
            if (i < 128) {
                this.drawCompassTexture(k, j, i, this.textureRow * 24, 65, 12, this.drawDepth);

                
            } else {

                this.drawCompassTexture(k, j, i - 128, this.textureRow * 24 + 12, 65, 12, this.drawDepth);
            }
        }

        ec.fontRendererObj.drawString(
                packedTextFragments[1] + this.markerColorCode.toLowerCase() + packedTextFragments[2],
                k + 32,
                j + 1,
                16777215);
        ec.fontRendererObj.drawString(
                packedTextFragments[3] + this.markerColorCode.toLowerCase() + "|\u00a7r",
                k + 32,
                j + 5,
                16777215);
        Render2D3DUtils.drawBorder(
                k - 1, j - 1, k + 65.0 + 1.0, j + 12.0 + 1.0, 1.0, -3092272);
    }

    private int centerHorizontally(ScaledResolution scaledResolution, int i) {
        return scaledResolution.getWidth() / 2 - i / 2;
    }

    private void drawCompassTexture(int i, int j, int k, int l, int i1, int j1, float f) {
        float f1 = 0.00390625F;
        float f2 = 0.00390625F;
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(i, j + j1, f, k * f1, (l + j1) * f2);
        worldRenderer.addVertexWithUV(i + i1, j + j1, f, (k + i1) * f1, (l + j1) * f2);
        worldRenderer.addVertexWithUV(i + i1, j, f, (k + i1) * f1, l * f2);
        worldRenderer.addVertexWithUV(i, j, f, k * f1, l * f2);
        instance.draw();
    }

    private int computeVerticalOffset(ScaledResolution scaledResolution, int i, int j) {
        return i * j / 2;
    }

    static {
        remix.ui.hud.CompassHudElement.packedTextFragments = new String[5];
        remix.ui.hud.CompassHudElement.packedTextFragments[0] = new String("c");
        remix.ui.hud.CompassHudElement.packedTextFragments[1] = new String("\u00a7");
        remix.ui.hud.CompassHudElement.packedTextFragments[2] = new String("|");
        remix.ui.hud.CompassHudElement.packedTextFragments[3] = new String("\u00a7");
    }

    public CompassHudElement(Setting setting) {
        this.markerColorCode = packedTextFragments[0];
        this.textureRow = 1;
        this.drawDepth = -100.0F;
        this.setting = setting;
    }
}
