package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import com.google.common.collect.Sets;

import java.util.Set;

public class ItemAxe extends ItemTool {
    private static final Set EFFECTIVE_ON =
            Sets.newHashSet(
                    new Block[] {
                        Blocks.planks,
                        Blocks.bookshelf,
                        Blocks.log,
                        Blocks.log2,
                        Blocks.chest,
                        Blocks.pumpkin,
                        Blocks.lit_pumpkin,
                        Blocks.melon_block,
                        Blocks.ladder
                    });

    protected ItemAxe(ItemToolMaterial itemToolMaterial) {
        super(3.0F, itemToolMaterial, net.minecraft.item.ItemAxe.EFFECTIVE_ON);
    }

    public float getStrVsBlock(ItemStack itemStack, Block block) {
        return block.getMaterial() != Material.wood
                        && block.getMaterial() != Material.plants
                        && block.getMaterial() != Material.vine
                ? super.getStrVsBlock(itemStack, block)
                : this.efficiencyOnProperMaterial;
    }
}
