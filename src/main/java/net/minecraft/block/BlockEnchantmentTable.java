package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class BlockEnchantmentTable extends BlockContainer {
    public boolean isOpaqueCube() {
        return false;
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i1 = BlockPlanksEnumType.getInvertedBlockFlowToken();
        super.randomDisplayTick(world, blockPos, blockState, random);
        int i = i1;
        int j = -2;

        while (j <= 2) {
            int k = -2;

            while (true) {
                if (k <= 2) {
                    if (j > -2 && j < 2 && k == -1) {
                        k = 2;
                    }

                    if (random.nextInt(16) == 0) {
                        int l = 0;

                        while (l <= 1) {
                            BlockPos blockPosValue = blockPos.add(j, l, k);
                            if (world.getBlockState(blockPosValue).getBlock() == Blocks.bookshelf) {
                                if (!world.isAirBlock(blockPos.add(j / 2, 0, k / 2)) && i == 0) {
                                    break;
                                }

                                world.spawnParticle(
                                        EnumParticleTypes.ENCHANTMENT_TABLE,
                                        blockPos.getX() + 0.5,
                                        blockPos.getY() + 2.0,
                                        blockPos.getZ() + 0.5,
                                        j + random.nextFloat() - 0.5,
                                        l - random.nextFloat() - 1.0F,
                                        k + random.nextFloat() - 0.5,
                                        new int[0]);
                            }

                            l++;
                            if (i != 0) {
                                break;
                            }
                        }
                    }

                    k++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    return;
                }
                break;
            }
        }
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float f,
            float f1,
            float f2) {
        if (world.isRemote) {
            return true;
        }

        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityEnchantmentTable) {
            playerEntity.displayGui((TileEntityEnchantmentTable) tileEntity);
        }

        return true;
    }

    public int getRenderType() {
        return 3;
    }

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, livingEntity, itemStack);
        if (itemStack.hasDisplayName()) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityEnchantmentTable) {
                ((TileEntityEnchantmentTable) tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityEnchantmentTable();
    }

    protected BlockEnchantmentTable() {
        super(Material.rock);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
