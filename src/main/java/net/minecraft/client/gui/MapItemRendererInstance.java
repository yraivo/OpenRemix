package net.minecraft.client.gui;

import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;

class MapItemRendererInstance {
    private static String f;
    private ResourceLocation location;
    MapItemRenderer field_148244_a;
    private int[] mapTextureData;
    private MapData mapData;

    private DynamicTexture mapTexture;

    static {
        f = "map/";
    }

    // region String decoding support

    // endregion

    MapItemRendererInstance(MapItemRenderer mapItemRenderer, MapData mapData, Object object) {
        this(mapItemRenderer, mapData);
    }

    static ResourceLocation access$300(MapItemRendererInstance mapItemRendererInstance) {
        return mapItemRendererInstance.location;
    }

    private MapItemRendererInstance(MapItemRenderer avBValue, MapData avCValue) {
        this.field_148244_a = avBValue;
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.mapData = avCValue;
        String[] astring = astring1;
        this.mapTexture = new DynamicTexture(128, 128);
        this.mapTextureData = this.mapTexture.getTextureData();
        this.location =
                MapItemRenderer.access$400(avBValue)
                        .getDynamicTextureLocation(f + avCValue.mapName, this.mapTexture);
        int i = 0;

        while (i < this.mapTextureData.length) {
            this.mapTextureData[i] = 0;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    // region String decoding support

    // endregion

    static void access$000(MapItemRendererInstance mapItemRendererInstance) {
        mapItemRendererInstance.updateMapTexture();
    }

    static void access$100(MapItemRendererInstance mapItemRendererInstance, boolean flag) {
        mapItemRendererInstance.render(flag);
    }

    private void render(boolean flag) {
        byte b0 = 0;
        byte b1 = 0;
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        float fx = 0.0F;
        MapItemRenderer.access$400(this.field_148244_a).bindTexture(this.location);
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(1, 771, 0, 1);
        GlStateManager.disableAlpha();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(b0 + 0 + fx, b1 + 128 - fx, -0.01F, 0.0, 1.0);
        String[] astring = astring1;
        worldRenderer.addVertexWithUV(b0 + 128 - fx, b1 + 128 - fx, -0.01F, 1.0, 1.0);
        worldRenderer.addVertexWithUV(b0 + 128 - fx, b1 + 0 + fx, -0.01F, 1.0, 0.0);
        worldRenderer.addVertexWithUV(b0 + 0 + fx, b1 + 0 + fx, -0.01F, 0.0, 0.0);
        instance.draw();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        MapItemRenderer.access$400(this.field_148244_a).bindTexture(MapItemRenderer.access$500());
        int i = 0;

        for (java.util.Iterator<?> __recoveryIterator171_7051 =
                        (this.mapData.playersVisibleOnMap.values()).iterator();
                (__recoveryIterator171_7051).hasNext(); ) {
            Vec4b vec4b = (Vec4b) (__recoveryIterator171_7051).next();
            if (!flag || vec4b.func_176110_a() == 1) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(
                        b0 + vec4b.func_176112_b() / 2.0F + 64.0F,
                        b1 + vec4b.func_176113_c() / 2.0F + 64.0F,
                        -0.02F);
                GlStateManager.rotate(vec4b.func_176111_d() * 360 / 16.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.scale(4.0F, 4.0F, 3.0F);
                GlStateManager.translate(-0.125F, 0.125F, 0.0F);
                byte b2 = vec4b.func_176110_a();
                float f1 = (b2 % 4 + 0) / 4.0F;
                float f2 = (b2 / 4 + 0) / 4.0F;
                float f3 = (b2 % 4 + 1) / 4.0F;
                float f4 = (b2 / 4 + 1) / 4.0F;
                worldRenderer.startDrawingQuads();
                worldRenderer.addVertexWithUV(-1.0, 1.0, i * 0.001F, f1, f2);
                worldRenderer.addVertexWithUV(1.0, 1.0, i * 0.001F, f3, f2);
                worldRenderer.addVertexWithUV(1.0, -1.0, i * 0.001F, f3, f4);
                worldRenderer.addVertexWithUV(-1.0, -1.0, i * 0.001F, f1, f4);
                instance.draw();
                GlStateManager.popMatrix();
                i++;
            }

            if (astring != null) {
                break;
            }
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, -0.04F);
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    private void updateMapTexture() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int i = 0;
        String[] astring = astring1;

        while (i < 16384) {
            {
                int j = this.mapData.colors[i] & 255;
                if (j / 4 == 0) {
                    this.mapTextureData[i] = (i + i / 128 & 1) * 8 + 16 << 24;
                    if (!(astring == null)) {

                        this.mapTextureData[i] =
                                MapColor.mapColorArray[j / 4].method_c640c0ab3979b883b99e(j & 3);
                    }
                } else {

                    this.mapTextureData[i] =
                            MapColor.mapColorArray[j / 4].method_c640c0ab3979b883b99e(j & 3);
                }
            }

            i++;
            if (astring != null) {
                break;
            }
        }

        this.mapTexture.updateDynamicTexture();
    }
}
