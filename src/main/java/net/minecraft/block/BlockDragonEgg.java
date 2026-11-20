package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDragonEgg extends Block {
    private void checkFall(World world, BlockPos blockPos) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (BlockFalling.canFallInto(world, blockPos.offsetDown()) && blockPos.getY() >= 0) {
            byte b0 = 32;
            if (!BlockFalling.fallInstantly
                    && world.isAreaLoaded(blockPos.add(-b0, -b0, -b0), blockPos.add(b0, b0, b0))) {
                world.spawnEntityInWorld(
                        new EntityFallingBlock(
                                world,
                                blockPos.getX() + 0.5F,
                                blockPos.getY(),
                                blockPos.getZ() + 0.5F,
                                this.getDefaultState()));
                if (i != 0) {
                    return;
                }
            }

            world.setBlockToAir(blockPos);
            BlockPos blockPosValue = blockPos;

            while (BlockFalling.canFallInto(world, blockPosValue) && blockPosValue.getY() > 0) {
                blockPosValue = blockPosValue.offsetDown();
                if (i == 0) {
                    break;
                }
            }

            if (blockPosValue.getY() > 0) {
                world.setBlockState(blockPosValue, this.getDefaultState(), 2);
            }
        }
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }

    public Item getItem(World world, BlockPos blockPos) {
        return null;
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return true;
    }

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float p171_5,
            float p171_6,
            float p171_7) {
        this.teleport(world, blockPos);
        return true;
    }

    public boolean isFullCube() {
        return false;
    }

    public int tickRate(World world) {
        return 5;
    }

    public void onBlockClicked(World world, BlockPos blockPos, PlayerEntity playerEntity) {
        this.teleport(world, blockPos);
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        this.checkFall(world, blockPos);
    }

    private void teleport(World world, BlockPos blockPos) {
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockState blockState = world.getBlockState(blockPos);
        int i = l;
        if (blockState.getBlock() == this) {
            int j = 0;

            while (j < 1000) {
                BlockPos blockPosValue =
                        blockPos.add(
                                world.rand.nextInt(16) - world.rand.nextInt(16),
                                world.rand.nextInt(8) - world.rand.nextInt(8),
                                world.rand.nextInt(16) - world.rand.nextInt(16));
                if (world.getBlockState(blockPosValue).getBlock().blockMaterial == Material.AIR) {
                    if (world.isRemote) {
                        int k = 0;

                        while (k < 128) {
                            double d0 = world.rand.nextDouble();
                            float f = (world.rand.nextFloat() - 0.5F) * 0.2F;
                            float f1 = (world.rand.nextFloat() - 0.5F) * 0.2F;
                            float f2 = (world.rand.nextFloat() - 0.5F) * 0.2F;
                            double d1 =
                                    blockPosValue.getX()
                                            + (blockPos.getX() - blockPosValue.getX()) * d0
                                            + (world.rand.nextDouble() - 0.5) * 1.0
                                            + 0.5;
                            double d2 =
                                    blockPosValue.getY()
                                            + (blockPos.getY() - blockPosValue.getY()) * d0
                                            + world.rand.nextDouble() * 1.0
                                            - 0.5;
                            double d3 =
                                    blockPosValue.getZ()
                                            + (blockPos.getZ() - blockPosValue.getZ()) * d0
                                            + (world.rand.nextDouble() - 0.5) * 1.0
                                            + 0.5;
                            world.spawnParticle(
                                    EnumParticleTypes.PORTAL, d1, d2, d3, f, f1, f2, new int[0]);
                            k++;
                            if (i != 0) {
                                break;
                            }
                        }

                        if (i == 0) {
                            return;
                        }
                    }

                    world.setBlockState(blockPosValue, blockState, 2);
                    world.setBlockToAir(blockPos);
                    return;
                }

                j++;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    public BlockDragonEgg() {
        super(Material.dragonEgg);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
    }
}
