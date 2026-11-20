package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class RenderLightningBolt extends Render {
    public RenderLightningBolt(RenderManager renderManagerValue) {
        super(renderManagerValue);
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityLightningBolt) entity, d0, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityLightningBolt) entity);
    }

    public void doRender(
            EntityLightningBolt entityLightningBolt,
            double d0,
            double d1,
            double d2,
            float f1,
            float f2) {
        Render.getLegacyEntityRenderState();

        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();

        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 1);
        double[] adouble = new double[8];
        double[] adouble1 = new double[8];
        double d3 = 0.0;
        double d4 = 0.0;
        Random random = new Random(entityLightningBolt.boltVertex);
        int i = 7;

        while (i >= 0) {
            adouble[i] = d3;
            adouble1[i] = d4;
            d3 += random.nextInt(11) - 5;
            d4 += random.nextInt(11) - 5;
            i--;
        }

        i = 0;

        while (i < 4) {
            Random random1 = new Random(entityLightningBolt.boltVertex);
            int j = 0;

            while (true) {
                if (j < 3) {
                    int k = 7;
                    int l = 0;
                    if (j > 0) {
                        k = 7 - j;
                    }

                    if (j > 0) {
                        l = k - 2;
                    }

                    double d5 = adouble[k] - d3;
                    double d6 = adouble1[k] - d4;
                    int i1 = k;

                    while (i1 >= l) {
                        double d7;
                        double d8;
                        {
                            d7 = d5;
                            d8 = d6;
                            if (j == 0) {
                                d5 += random1.nextInt(11) - 5;
                                d6 += random1.nextInt(11) - 5;

                                
                            } else {

                                d5 += random1.nextInt(31) - 15;
                                d6 += random1.nextInt(31) - 15;
                            }
                        }

                        worldRenderer.startDrawing(5);
                        float f = 0.5F;
                        worldRenderer.setVertexColorFromFloats(0.9F * f, 0.9F * f, 1.0F * f, 0.3F);
                        double d9 = 0.1 + i * 0.2;
                        if (j == 0) {
                            d9 *= i1 * 0.1 + 1.0;
                        }

                        double d10 = 0.1 + i * 0.2;
                        if (j == 0) {
                            d10 *= (i1 - 1) * 0.1 + 1.0;
                        }

                        int j1 = 0;

                        while (j1 < 5) {
                            double d11 = d0 + 0.5 - d9;
                            double d12 = d2 + 0.5 - d9;
                            if (j1 == 1 || j1 == 2) {
                                d11 += d9 * 2.0;
                            }

                            if (j1 == 2 || j1 == 3) {
                                d12 += d9 * 2.0;
                            }

                            double d13 = d0 + 0.5 - d10;
                            double d14 = d2 + 0.5 - d10;
                            if (j1 == 1 || j1 == 2) {
                                d13 += d10 * 2.0;
                            }

                            if (j1 == 2 || j1 == 3) {
                                d14 += d10 * 2.0;
                            }

                            worldRenderer.addVertex(d13 + d5, d1 + i1 * 16, d14 + d6);
                            worldRenderer.addVertex(d11 + d7, d1 + (i1 + 1) * 16, d12 + d8);
                            j1++;
                        }

                        instance.draw();
                        i1--;
                    }

                    j++;

                    continue;
                }

                i++;

                break;
            }
        }

        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
    }

    protected ResourceLocation getEntityTexture(EntityLightningBolt entityLightningBolt) {
        return null;
    }
}
