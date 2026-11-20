package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.Random;

public class BlockIce extends BlockBreakable {
    public int getMobilityFlag() {
        return 0;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }

    public BlockIce() {
        super(Material.ice, false);
        this.slipperiness = 0.98F;
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public void harvestBlock(
            World world,
            PlayerEntity playerEntity,
            BlockPos blockPos,
            BlockState blockState,
            TileEntity tileEntity) {
        playerEntity.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        playerEntity.addExhaustion(0.025F);
        int i = k;
        if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(playerEntity)) {
            ItemStack itemStack = this.createStackedBlock(blockState);
            if (itemStack != null) {
                spawnAsEntity(world, blockPos, itemStack);
            }

            if (i != 0) {
                return;
            }
        }

        if (world.provider.doesWaterVaporize()) {
            world.setBlockToAir(blockPos);
        } else {
            int j = EnchantmentHelper.getFortuneModifier(playerEntity);
            this.dropBlockAsItem(world, blockPos, blockState, j);
            Material material = world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial();
            if (material.blocksMovement() || material.isLiquid()) {
                world.setBlockState(blockPos, Blocks.flowing_water.getDefaultState());
            }
        }
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (world.getLightFor(EnumSkyBlock.BLOCK, blockPos) > 11 - this.getLightOpacity()) {
            if (world.provider.doesWaterVaporize()) {
                world.setBlockToAir(blockPos);
                if (i != 0) {
                    return;
                }
            }

            this.dropBlockAsItem(world, blockPos, world.getBlockState(blockPos), 0);
            world.setBlockState(blockPos, Blocks.water.getDefaultState());
        }
    }

    public int quantityDropped(Random random) {
        return 0;
    }
}
