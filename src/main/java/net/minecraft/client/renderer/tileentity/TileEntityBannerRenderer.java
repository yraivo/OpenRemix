package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TileEntityBannerRenderer extends TileEntitySpecialRenderer {
    private static Map DESIGNS;

    private static ResourceLocation BANNERTEXTURES;

    private ModelBanner bannerModel = new ModelBanner();

    private ResourceLocation method_6a9902fdddb7c6684068(TileEntityBanner tileEntityBanner) {
        TileEntitySpecialRenderer.getLegacyTileRendererState();

        String text = tileEntityBanner.func_175116_e();

        if (text.isEmpty()) {
            return null;
        }

        TileEntityBannerRendererTimedBannerTexture tileEntityBannerRendererTimedBannerTexture =
                (TileEntityBannerRendererTimedBannerTexture) net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer.DESIGNS.get(text);
        if (tileEntityBannerRendererTimedBannerTexture == null) {
            if (net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer.DESIGNS.size() >= 256) {
                long i = System.currentTimeMillis();
                Iterator iterator = net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer.DESIGNS.keySet().iterator();

                while (iterator.hasNext()) {
                    String textValue = (String) iterator.next();
                    TileEntityBannerRendererTimedBannerTexture tileEntityBannerRendererTimedBannerTextureValue =
                            (TileEntityBannerRendererTimedBannerTexture)
                                    net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer.DESIGNS.get(textValue);
                    long j = i - tileEntityBannerRendererTimedBannerTextureValue.systemTime;

                    if (j > 60000L) {
                        MinecraftClient.getMinecraft()
                                .getTextureManager()
                                .deleteTexture(tileEntityBannerRendererTimedBannerTextureValue.bannerTexture);
                        iterator.remove();
                    }
                }

                if (net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer.DESIGNS.size() >= 256) {
                    return null;
                }
            }

            List listValue = tileEntityBanner.getPatternList();
            List colorList = tileEntityBanner.getColorList();
            ArrayList arrayList = Lists.newArrayList();

            for (java.util.Iterator<?> __recoveryIterator171_2505 = (listValue).iterator();
                    (__recoveryIterator171_2505).hasNext(); ) {
                BannerPattern bannerPattern = (BannerPattern) (__recoveryIterator171_2505).next();
                arrayList.add("textures/entity/banner/" + bannerPattern.getPatternName() + ".png");
            }

            tileEntityBannerRendererTimedBannerTexture = new TileEntityBannerRendererTimedBannerTexture(null);
            tileEntityBannerRendererTimedBannerTexture.bannerTexture = new ResourceLocation(text);
            MinecraftClient.getMinecraft()
                    .getTextureManager()
                    .loadTexture(
                            tileEntityBannerRendererTimedBannerTexture.bannerTexture,
                            new LayeredColorMaskTexture(
                                    net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer.BANNERTEXTURES,
                                    arrayList,
                                    colorList));
            net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer.DESIGNS.put(text, tileEntityBannerRendererTimedBannerTexture);
        }

        tileEntityBannerRendererTimedBannerTexture.systemTime = System.currentTimeMillis();
        return tileEntityBannerRendererTimedBannerTexture.bannerTexture;
    }

    public void renderTileEntityAt(
            TileEntity tileEntity, double d0, double d1, double d2, float f, int i) {
        this.method_268068f0fb2c531f77c6((TileEntityBanner) tileEntity, d0, d1, d2, f, i);
    }

    public void method_268068f0fb2c531f77c6(
            TileEntityBanner tileEntityBanner, double d0, double d1, double d2, float f, int k1) {
        TileEntitySpecialRenderer.getLegacyTileRendererState();

        int i = tileEntityBanner.getWorld() != null ? 1 : 0;
        int j = i != 0 && tileEntityBanner.getBlockType() != Blocks.standing_banner ? 0 : 1;
        int k = i != 0 ? tileEntityBanner.getBlockMetadata() : 0;
        long i1;
        if (i != 0) {
            i1 = tileEntityBanner.getWorld().getTotalWorldTime();
        } else {

            i1 = 0L;
        }

        long l;
        float f1;
        {
            l = i1;
            GlStateManager.pushMatrix();
            f1 = 0.6666667F;
            if (j != 0) {
                GlStateManager.translate(
                        (float) d0 + 0.5F, (float) d1 + 0.75F * f1, (float) d2 + 0.5F);
                float f3 = k * 360 / 16.0F;
                GlStateManager.rotate(-f3, 0.0F, 1.0F, 0.0F);
                this.bannerModel.bannerStand.showModel = true;

                
            } else {

                float f2 = 0.0F;
                if (k == 2) {
                    f2 = 180.0F;
                }

                if (k == 4) {
                    f2 = 90.0F;
                }

                if (k == 5) {
                    f2 = -90.0F;
                }

                GlStateManager.translate((float) d0 + 0.5F, (float) d1 - 0.25F * f1, (float) d2 + 0.5F);
                GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
                this.bannerModel.bannerStand.showModel = false;
            }
        }

        BlockPos blockPos = tileEntityBanner.getPos();
        float f4 = blockPos.getX() * 7 + blockPos.getY() * 9 + blockPos.getZ() * 13 + (float) l + f;
        this.bannerModel.bannerSlate.rotateAngleX =
                (-0.0125F + 0.01F * MathHelper.cos(f4 * (float) Math.PI * 0.02F)) * (float) Math.PI;
        GlStateManager.enableRescaleNormal();
        ResourceLocation resourceLocation = this.method_6a9902fdddb7c6684068(tileEntityBanner);
        if (resourceLocation != null) {
            this.bindTexture(resourceLocation);
            GlStateManager.pushMatrix();
            GlStateManager.scale(f1, -f1, -f1);
            this.bannerModel.renderBanner();
            GlStateManager.popMatrix();
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    static {
        net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer.DESIGNS = Maps.newHashMap();
        net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer.BANNERTEXTURES =
                new ResourceLocation("textures/entity/banner_base.png");
    }
}
