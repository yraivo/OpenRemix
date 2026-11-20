package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.item.ItemStack;
import remix.compat.legacy.crypto.ClassHashDoubleProbe;

public class ChestRenderer {
    public void renderChestBrightness(Block block, float f) {

        if (ClassHashDoubleProbe.lastClassHashValidationCode > 0L) {
            GlStateManager.color(f, f, f, 1.0F);
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            TileEntityRendererChestHelper.instance.renderByItem(new ItemStack(block));
        }
    }
}
