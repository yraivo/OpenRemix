package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;

public class RenderTntMinecart extends RenderMinecart {
    protected void method_ff86f9ed91fd1201cee6(
            EntityMinecartTNT entityMinecartTNT, float f, BlockState blockState) {
        int fuseTicks = entityMinecartTNT.getFuseTicks();
        if (fuseTicks > -1 && fuseTicks - f + 1.0F < 10.0F) {
            float f1 = 1.0F - (fuseTicks - f + 1.0F) / 10.0F;
            f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
            f1 *= f1;
            f1 *= f1;
            float f2 = 1.0F + f1 * 0.3F;
            GlStateManager.scale(f2, f2, f2);
        }

        super.method_483853dc58042e156d99(entityMinecartTNT, f, blockState);
        if (fuseTicks > -1 && fuseTicks / 5 % 2 == 0) {
            BlockRendererDispatcher blockRendererDispatcher =
                    MinecraftClient.getMinecraft().getBlockRendererDispatcher();
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 772);
            GlStateManager.color(1.0F, 1.0F, 1.0F, (1.0F - (fuseTicks - f + 1.0F) / 100.0F) * 0.8F);
            GlStateManager.pushMatrix();
            blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0F);
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
        }
    }

    protected void method_483853dc58042e156d99(
            EntityMinecart entityMinecart, float p171_1, BlockState blockState) {
        this.method_ff86f9ed91fd1201cee6(
                (net.minecraft.entity.item.EntityMinecartTNT) entityMinecart, p171_1, blockState);
    }

    public RenderTntMinecart(RenderManager renderManagerValue) {
        super(renderManagerValue);
    }
}
