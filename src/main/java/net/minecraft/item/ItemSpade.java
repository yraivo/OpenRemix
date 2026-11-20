package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import com.google.common.collect.Sets;

import java.util.Set;

public class ItemSpade extends ItemTool {
    private static final Set EFFECTIVE_ON =
            Sets.newHashSet(
                    new Block[] {
                        Blocks.clay,
                        Blocks.dirt,
                        Blocks.farmland,
                        Blocks.grass,
                        Blocks.gravel,
                        Blocks.mycelium,
                        Blocks.sand,
                        Blocks.snow,
                        Blocks.snow_layer,
                        Blocks.soul_sand
                    });

    public ItemSpade(ItemToolMaterial itemToolMaterial) {
        super(1.0F, itemToolMaterial, net.minecraft.item.ItemSpade.EFFECTIVE_ON);
    }

    public boolean canHarvestBlock(Block block) {
        return block == Blocks.snow_layer ? true : block == Blocks.snow;
    }
}
