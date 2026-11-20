package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TileEntityEnchantmentTableRenderer extends TileEntitySpecialRenderer {
    private static ResourceLocation TEXTURE_BOOK;
    private ModelBook aFK = new ModelBook();

    static {
        net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer.TEXTURE_BOOK =
                new ResourceLocation("textures/entity/enchanting_table_book.png");
    }

    public void method_323af966f36466354963(
            TileEntityEnchantmentTable tileEntityEnchantmentTable,
            double d0,
            double d1,
            double d2,
            float f,
            int i) {
        GlStateManager.pushMatrix();
        TileEntitySpecialRenderer.getLegacyTileRendererState();

        GlStateManager.translate((float) d0 + 0.5F, (float) d1 + 0.75F, (float) d2 + 0.5F);
        float f1 = tileEntityEnchantmentTable.tickCount + f;
        GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f1 * 0.1F) * 0.01F, 0.0F);
        float f2 = tileEntityEnchantmentTable.bookRotation - tileEntityEnchantmentTable.bookRotationPrev;

        while (f2 >= (float) Math.PI) {
            f2 -= (float) (Math.PI * 2);
        }

        while (f2 < (float) -Math.PI) {
            f2 += (float) (Math.PI * 2);
        }

        float f3 = tileEntityEnchantmentTable.bookRotationPrev + f2 * f;
        GlStateManager.rotate(-f3 * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);
        this.bindTexture(net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer.TEXTURE_BOOK);
        float f4 =
                tileEntityEnchantmentTable.pageFlipPrev
                        + (tileEntityEnchantmentTable.pageFlip - tileEntityEnchantmentTable.pageFlipPrev) * f
                        + 0.25F;
        float f5 =
                tileEntityEnchantmentTable.pageFlipPrev
                        + (tileEntityEnchantmentTable.pageFlip - tileEntityEnchantmentTable.pageFlipPrev) * f
                        + 0.75F;
        f4 = (f4 - MathHelper.truncateDoubleToInt(f4)) * 1.6F - 0.3F;
        f5 = (f5 - MathHelper.truncateDoubleToInt(f5)) * 1.6F - 0.3F;
        if (f4 < 0.0F) {
            f4 = 0.0F;
        }

        if (f5 < 0.0F) {
            f5 = 0.0F;
        }

        if (f4 > 1.0F) {
            f4 = 1.0F;
        }

        if (f5 > 1.0F) {
            f5 = 1.0F;
        }

        float f6 =
                tileEntityEnchantmentTable.bookSpreadPrev
                        + (tileEntityEnchantmentTable.bookSpread - tileEntityEnchantmentTable.bookSpreadPrev) * f;
        GlStateManager.enableCull();
        this.aFK.render((Entity) null, f1, f4, f5, f6, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
    }

    public void renderTileEntityAt(
            TileEntity tileEntity, double d0, double d1, double d2, float f, int i) {
        this.method_323af966f36466354963((TileEntityEnchantmentTable) tileEntity, d0, d1, d2, f, i);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
