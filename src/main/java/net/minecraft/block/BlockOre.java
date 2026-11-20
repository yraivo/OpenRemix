package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOre extends Block {
    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return this == Blocks.coal_ore
                ? Items.coal
                : (this == Blocks.diamond_ore
                        ? Items.diamond
                        : (this == Blocks.lapis_ore
                                ? Items.dye
                                : (this == Blocks.emerald_ore
                                        ? Items.emerald
                                        : (this == Blocks.quartz_ore
                                                ? Items.quartz
                                                : Item.getItemFromBlock(this)))));
    }

    public int getDamageValue(World world, BlockPos blockPos) {
        return 0;
    }

    public int damageDropped(BlockState blockState) {
        return this == Blocks.lapis_ore ? EnumDyeColor.BLUE.getDyeColorDamage() : 0;
    }

    public BlockOre() {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public int quantityDropped(Random random) {
        return this == Blocks.lapis_ore ? 4 + random.nextInt(5) : 1;
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {
        int l = BlockPlanksEnumType.getLegacyBlockFlowToken();
        super.dropBlockAsItemWithChance(world, blockPos, blockState, f, i);
        int j = l;
        if (this.getItemDropped(blockState, world.rand, i) != Item.getItemFromBlock(this)) {
            int k;
            label34:
            {
                k = 0;
                if (this == Blocks.coal_ore) {
                    k = MathHelper.nextIntInclusive(world.rand, 0, 2);
                    if (j != 0) {
                        break label34;
                    }
                }

                if (this == Blocks.diamond_ore) {
                    k = MathHelper.nextIntInclusive(world.rand, 3, 7);
                    if (j != 0) {
                        break label34;
                    }
                }

                if (this == Blocks.emerald_ore) {
                    k = MathHelper.nextIntInclusive(world.rand, 3, 7);
                    if (j != 0) {
                        break label34;
                    }
                }

                if (this == Blocks.lapis_ore) {
                    k = MathHelper.nextIntInclusive(world.rand, 2, 5);
                    if (j != 0) {
                        break label34;
                    }
                }

                if (this == Blocks.quartz_ore) {
                    k = MathHelper.nextIntInclusive(world.rand, 2, 5);
                }
            }

            this.dropXpOnBlockBreak(world, blockPos, k);
        }
    }

    public int quantityDroppedWithBonus(int i, Random random) {
        if (i > 0
                && Item.getItemFromBlock(this)
                        != this.getItemDropped(
                                (BlockState)
                                        this.getBlockState().getValidStates().iterator().next(),
                                random,
                                i)) {
            int j = random.nextInt(i + 2) - 1;
            if (j < 0) {
                j = 0;
            }

            return this.quantityDropped(random) * (j + 1);
        } else {
            return this.quantityDropped(random);
        }
    }
}
