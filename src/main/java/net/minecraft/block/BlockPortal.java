package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPortal extends BlockBreakable {

    public static PropertyEnum AXIS;

    public BlockPortal() {
        super(Material.portal, false);
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockPortal.AXIS, FacingAxis.X));
        this.setTickRandomly(true);
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        FacingAxis facingAxis = null;
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        if (iBlockAccess.getBlockState(blockPos).getBlock() == this) {
            facingAxis = (FacingAxis) blockState.getValue(net.minecraft.block.BlockPortal.AXIS);
            if (facingAxis == null) {
                return false;
            }

            if (facingAxis == FacingAxis.Z && facing != Facing.EAST && facing != Facing.WEST) {
                return false;
            }

            if (facingAxis == FacingAxis.X && facing != Facing.SOUTH && facing != Facing.NORTH) {
                return false;
            }
        }

        int i =
                iBlockAccess.getBlockState(blockPos.offsetWest()).getBlock() == this
                                && iBlockAccess.getBlockState(blockPos.offsetWest(2)).getBlock()
                                        != this
                        ? 1
                        : 0;
        int j =
                iBlockAccess.getBlockState(blockPos.offsetEast()).getBlock() == this
                                && iBlockAccess.getBlockState(blockPos.offsetEast(2)).getBlock()
                                        != this
                        ? 1
                        : 0;
        int k =
                iBlockAccess.getBlockState(blockPos.offsetNorth()).getBlock() == this
                                && iBlockAccess.getBlockState(blockPos.offsetNorth(2)).getBlock()
                                        != this
                        ? 1
                        : 0;
        int l =
                iBlockAccess.getBlockState(blockPos.offsetSouth()).getBlock() == this
                                && iBlockAccess.getBlockState(blockPos.offsetSouth(2)).getBlock()
                                        != this
                        ? 1
                        : 0;
        int i1 = i == 0 && j == 0 && facingAxis != FacingAxis.X ? 0 : 1;
        int j1 = k == 0 && l == 0 && facingAxis != FacingAxis.Z ? 0 : 1;
        return i1 != 0 && facing == Facing.WEST
                ? true
                : (i1 != 0 && facing == Facing.EAST
                        ? true
                        : (j1 != 0 && facing == Facing.NORTH
                                ? true
                                : j1 != 0 && facing == Facing.SOUTH));
    }

    public int getMetaFromState(BlockState blockState) {
        return getMetaForAxis((FacingAxis) blockState.getValue(net.minecraft.block.BlockPortal.AXIS));
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        FacingAxis facingAxis =
                (FacingAxis) iBlockAccess.getBlockState(blockPos).getValue(net.minecraft.block.BlockPortal.AXIS);
        float f = 0.125F;
        float f1 = 0.125F;
        if (facingAxis == FacingAxis.X) {
            f = 0.5F;
        }

        if (facingAxis == FacingAxis.Z) {
            f1 = 0.5F;
        }

        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        super.updateTick(world, blockPos, blockState, random);
        int i = k;
        if (world.provider.isSurfaceWorld()
                && world.getGameRules().getGameRuleBooleanValue("doMobSpawning")
                && random.nextInt(2000) < world.getDifficulty().getDifficultyId()) {
            int j = blockPos.getY();
            BlockPos blockPosValue = blockPos;

            while (!World.doesBlockHaveSolidTopSurface(world, blockPosValue)
                    && blockPosValue.getY() > 0) {
                blockPosValue = blockPosValue.offsetDown();
                if (i != 0) {
                    break;
                }
            }

            if (j > 0 && !world.getBlockState(blockPosValue.offsetUp()).getBlock().isNormalCube()) {
                Entity entity =
                        ItemMonsterPlacer.spawnCreature(
                                world,
                                57,
                                blockPosValue.getX() + 0.5,
                                blockPosValue.getY() + 1.1,
                                blockPosValue.getZ() + 0.5);
                if (entity != null) {
                    entity.timeUntilPortal = entity.getPortalCooldown();
                }
            }
        }
    }

    public Item getItem(World world, BlockPos blockPos) {
        return null;
    }

    public static int getMetaForAxis(FacingAxis facingAxis) {
        return facingAxis == FacingAxis.X ? 1 : (facingAxis == FacingAxis.Z ? 2 : 0);
    }

    public void onEntityCollidedWithBlock(
            World world, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (entity.ridingEntity == null && entity.riddenByEntity == null) {
            entity.setInPortal();
        }
    }

    public boolean func_176548_d(World world, BlockPos blockPos) {
        BlockPortalSize blockPortalSize = new BlockPortalSize(world, blockPos, FacingAxis.X);
        if (blockPortalSize.func_150860_b() && BlockPortalSize.access$000(blockPortalSize) == 0) {
            blockPortalSize.func_150859_c();
            return true;
        } else {
            BlockPortalSize blockPortalSizeValue =
                    new BlockPortalSize(world, blockPos, FacingAxis.Z);
            if (blockPortalSizeValue.func_150860_b()
                    && BlockPortalSize.access$000(blockPortalSizeValue) == 0) {
                blockPortalSizeValue.func_150859_c();
                return true;
            } else {
                return false;
            }
        }
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (random.nextInt(100) == 0) {
            world.playSound(
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 0.5,
                    blockPos.getZ() + 0.5,
                    "portal.portal",
                    0.5F,
                    random.nextFloat() * 0.4F + 0.8F,
                    false);
        }

        int j = 0;

        while (j < 4) {
            double d0;
            double d1;
            double d2;
            double d3;
            double d4;
            double d5;
            {
                d0 = blockPos.getX() + random.nextFloat();
                d1 = blockPos.getY() + random.nextFloat();
                d2 = blockPos.getZ() + random.nextFloat();
                d3 = (random.nextFloat() - 0.5) * 0.5;
                d4 = (random.nextFloat() - 0.5) * 0.5;
                d5 = (random.nextFloat() - 0.5) * 0.5;
                int k = random.nextInt(2) * 2 - 1;
                if (world.getBlockState(blockPos.offsetWest()).getBlock() != this
                        && world.getBlockState(blockPos.offsetEast()).getBlock() != this) {
                    d0 = blockPos.getX() + 0.5 + 0.25 * k;
                    d3 = random.nextFloat() * 2.0F * k;
                    if (!(i == 0)) {

                        d2 = blockPos.getZ() + 0.5 + 0.25 * k;
                        d5 = random.nextFloat() * 2.0F * k;
                    }
                } else {

                    d2 = blockPos.getZ() + 0.5 + 0.25 * k;
                    d5 = random.nextFloat() * 2.0F * k;
                }
            }

            world.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    static {
        net.minecraft.block.BlockPortal.AXIS =
                PropertyEnum.create(
                        "axis", FacingAxis.class, new FacingAxis[] {FacingAxis.X, FacingAxis.Z});
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public boolean isFullCube() {
        return false;
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        FacingAxis facingAxis = (FacingAxis) blockState.getValue(net.minecraft.block.BlockPortal.AXIS);
        int i = j;
        if (facingAxis == FacingAxis.X) {
            BlockPortalSize blockPortalSize = new BlockPortalSize(world, blockPos, FacingAxis.X);
            if (blockPortalSize.func_150860_b()
                    && BlockPortalSize.access$000(blockPortalSize)
                            >= BlockPortalSize.access$100(blockPortalSize)
                                    * BlockPortalSize.access$200(blockPortalSize)) {
                return;
            }

            world.setBlockState(blockPos, Blocks.air.getDefaultState());
            if (i == 0) {
                return;
            }
        }

        if (facingAxis == FacingAxis.Z) {
            BlockPortalSize blockPortalSizeValue =
                    new BlockPortalSize(world, blockPos, FacingAxis.Z);
            if (!blockPortalSizeValue.func_150860_b()
                    || BlockPortalSize.access$000(blockPortalSizeValue)
                            < BlockPortalSize.access$100(blockPortalSizeValue)
                                    * BlockPortalSize.access$200(blockPortalSizeValue)) {
                world.setBlockState(blockPos, Blocks.air.getDefaultState());
            }
        }
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockPortal.AXIS});
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockPortal.AXIS, (i & 3) == 2 ? FacingAxis.Z : FacingAxis.X);
    }
}
