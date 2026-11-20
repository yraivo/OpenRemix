package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

import java.util.Random;

public class BlockSeaLantern extends Block {
    public int quantityDroppedWithBonus(int i, Random random) {
        return MathHelper.clamp(this.quantityDropped(random) + random.nextInt(i + 1), 1, 5);
    }

    protected boolean canSilkHarvest() {
        return true;
    }

    public MapColor getMapColor(BlockState blockState) {
        return MapColor.quartzColor;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.prismarine_crystals;
    }

    public BlockSeaLantern(Material material) {
        super(material);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public int quantityDropped(Random random) {
        return 2 + random.nextInt(2);
    }
}
