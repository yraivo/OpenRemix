package optifine;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSprite {
    private float minU;
    private float posX;
    private float sizeAdd;
    private float minV;
    private int sizeZ;
    private float posZ;
    private int sizeX;
    private float maxV;
    private int c;
    private int sizeY;
    private float maxU;
    private ModelRenderer modelRenderer = null;
    private float posY;
    private int cz = 0;

    public void render(Tessellator tessellator, float p171_1) {
        net.minecraft.client.renderer.GlStateManager.translate(this.posX * p171_1, this.posY * p171_1, this.posZ * p171_1);
        float minUValue = this.minU;
        float maxUValue = this.maxU;
        float minVValue = this.minV;
        float maxVValue = this.maxV;
        if (this.modelRenderer.mirror) {
            minUValue = this.maxU;
            maxUValue = this.minU;
        }

        if (this.modelRenderer.mirrorV) {
            minVValue = this.maxV;
            maxVValue = this.minV;
        }

        optifine.ModelSprite.renderItemIn2D(
                tessellator,
                minUValue,
                minVValue,
                maxUValue,
                maxVValue,
                this.sizeX,
                this.sizeY,
                p171_1 * this.sizeZ,
                this.modelRenderer.textureWidth,
                this.modelRenderer.textureHeight);
        net.minecraft.client.renderer.GlStateManager.translate(
                -this.posX * p171_1, -this.posY * p171_1, -this.posZ * p171_1);
    }

    public static void renderItemIn2D(
            Tessellator tessellator,
            float f,
            float f1,
            float f2,
            float f3,
            int i,
            int j,
            float f4,
            float f5,
            float f6) {
        int k = ReflectorMethod.cR();
        if (f4 < 6.25E-4F) {
            f4 = 6.25E-4F;
        }

        float f7 = f2 - f;
        float f8 = f3 - f1;
        double d0 = MathHelper.abs(f7) * (f5 / 16.0F);
        double d1 = MathHelper.abs(f8) * (f6 / 16.0F);
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexNormal(0.0F, 0.0F, -1.0F);
        worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, f, f1);
        worldRenderer.addVertexWithUV(d0, 0.0, 0.0, f2, f1);
        worldRenderer.addVertexWithUV(d0, d1, 0.0, f2, f3);
        worldRenderer.addVertexWithUV(0.0, d1, 0.0, f, f3);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexNormal(0.0F, 0.0F, 1.0F);
        worldRenderer.addVertexWithUV(0.0, d1, f4, f, f3);
        worldRenderer.addVertexWithUV(d0, d1, f4, f2, f3);
        worldRenderer.addVertexWithUV(d0, 0.0, f4, f2, f1);
        worldRenderer.addVertexWithUV(0.0, 0.0, f4, f, f1);
        tessellator.draw();
        float f9 = 0.5F * f7 / i;
        float f10 = 0.5F * f8 / j;
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexNormal(-1.0F, 0.0F, 0.0F);
        int l = 0;

        while (l < i) {
            float f11 = (float) l / i;
            float f12 = f + f7 * f11 + f9;
            worldRenderer.addVertexWithUV(f11 * d0, 0.0, f4, f12, f1);
            worldRenderer.addVertexWithUV(f11 * d0, 0.0, 0.0, f12, f1);
            worldRenderer.addVertexWithUV(f11 * d0, d1, 0.0, f12, f3);
            worldRenderer.addVertexWithUV(f11 * d0, d1, f4, f12, f3);
            l++;
            if (k == 0) {
                break;
            }
        }

        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexNormal(1.0F, 0.0F, 0.0F);
        l = 0;

        while (l < i) {
            float f14 = (float) l / i;
            float f17 = f + f7 * f14 + f9;
            float f13 = f14 + 1.0F / i;
            worldRenderer.addVertexWithUV(f13 * d0, d1, f4, f17, f3);
            worldRenderer.addVertexWithUV(f13 * d0, d1, 0.0, f17, f3);
            worldRenderer.addVertexWithUV(f13 * d0, 0.0, 0.0, f17, f1);
            worldRenderer.addVertexWithUV(f13 * d0, 0.0, f4, f17, f1);
            l++;
            if (k == 0) {
                break;
            }
        }

        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexNormal(0.0F, 1.0F, 0.0F);
        l = 0;

        while (l < j) {
            float f15 = (float) l / j;
            float f18 = f1 + f8 * f15 + f10;
            float f20 = f15 + 1.0F / j;
            worldRenderer.addVertexWithUV(0.0, f20 * d1, 0.0, f, f18);
            worldRenderer.addVertexWithUV(d0, f20 * d1, 0.0, f2, f18);
            worldRenderer.addVertexWithUV(d0, f20 * d1, f4, f2, f18);
            worldRenderer.addVertexWithUV(0.0, f20 * d1, f4, f, f18);
            l++;
            if (k == 0) {
                break;
            }
        }

        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexNormal(0.0F, -1.0F, 0.0F);
        l = 0;

        while (l < j) {
            float f16 = (float) l / j;
            float f19 = f1 + f8 * f16 + f10;
            worldRenderer.addVertexWithUV(d0, f16 * d1, 0.0, f2, f19);
            worldRenderer.addVertexWithUV(0.0, f16 * d1, 0.0, f, f19);
            worldRenderer.addVertexWithUV(0.0, f16 * d1, f4, f, f19);
            worldRenderer.addVertexWithUV(d0, f16 * d1, f4, f2, f19);
            l++;
            if (k == 0) {
                break;
            }
        }

        tessellator.draw();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ReflectorMethod.b(++k);
        }
    }

    public ModelSprite(
            ModelRenderer modelRenderer,
            int p171_1,
            int p171_2,
            float posXValue,
            float posYValue,
            float posZValue,
            int sizeXValue,
            int sizeYValue,
            int sizeZValue,
            float sizeAddValue) {
        this.modelRenderer = modelRenderer;
        this.cz = p171_1;
        this.c = p171_2;
        this.posX = posXValue;
        this.posY = posYValue;
        this.posZ = posZValue;
        this.sizeX = sizeXValue;
        this.sizeY = sizeYValue;
        this.sizeZ = sizeZValue;
        this.sizeAdd = sizeAddValue;
        this.minU = p171_1 / modelRenderer.textureWidth;
        this.minV = p171_2 / modelRenderer.textureHeight;
        this.maxU = (p171_1 + sizeXValue) / modelRenderer.textureWidth;
        this.maxV = (p171_2 + sizeYValue) / modelRenderer.textureHeight;
    }
}
