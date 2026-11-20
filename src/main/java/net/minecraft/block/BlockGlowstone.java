package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

import java.util.Random;

public class BlockGlowstone extends Block {
    public int quantityDroppedWithBonus(int i, Random random) {
        return MathHelper.clamp(this.quantityDropped(random) + random.nextInt(i + 1), 1, 4);
    }

    public MapColor getMapColor(BlockState blockState) {
        return MapColor.sandColor;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.glowstone_dust;
    }

    public int quantityDropped(Random random) {
        return 2 + random.nextInt(3);
    }

    public BlockGlowstone(Material material) {
        super(material);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
