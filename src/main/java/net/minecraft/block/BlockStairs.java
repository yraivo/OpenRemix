package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockStairs extends Block {
    private Block modelBlock;
    public static PropertyEnum HALF;
    private static int[][] field_150150_a;
    private BlockState modelState;
    public static PropertyDirection FACING;
    public static PropertyEnum SHAPE;

    private boolean hasRaytraced;
    private int rayTracePass;

    public int func_176305_g(IBlockAccess iBlockAccess, BlockPos blockPos) {
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockStairs.FACING);
        BlockStairsEnumHalf value =
                (BlockStairsEnumHalf) blockState.getValue(net.minecraft.block.BlockStairs.HALF);
        int i = value == BlockStairsEnumHalf.TOP ? 1 : 0;
        if (facing == Facing.EAST) {
            BlockState blockStateValue = iBlockAccess.getBlockState(blockPos.offsetWest());
            Block block = blockStateValue.getBlock();
            if (isBlockStairs(block)
                    && value == blockStateValue.getValue(net.minecraft.block.BlockStairs.HALF)) {
                Facing facingValue =
                        (Facing) blockStateValue.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue == Facing.NORTH
                        && !isSameStair(iBlockAccess, blockPos.offsetNorth(), blockState)) {
                    return i != 0 ? 1 : 2;
                }

                if (facingValue == Facing.SOUTH
                        && !isSameStair(iBlockAccess, blockPos.offsetSouth(), blockState)) {
                    return i != 0 ? 2 : 1;
                }
            }
        } else if (facing == Facing.WEST) {
            BlockState blockStateValue2 = iBlockAccess.getBlockState(blockPos.offsetEast());
            Block blockValue = blockStateValue2.getBlock();
            if (isBlockStairs(blockValue)
                    && value == blockStateValue2.getValue(net.minecraft.block.BlockStairs.HALF)) {
                Facing facingValue2 =
                        (Facing) blockStateValue2.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue2 == Facing.NORTH
                        && !isSameStair(iBlockAccess, blockPos.offsetNorth(), blockState)) {
                    return i != 0 ? 2 : 1;
                }

                if (facingValue2 == Facing.SOUTH
                        && !isSameStair(iBlockAccess, blockPos.offsetSouth(), blockState)) {
                    return i != 0 ? 1 : 2;
                }
            }
        } else if (facing == Facing.SOUTH) {
            BlockState blockStateValue3 = iBlockAccess.getBlockState(blockPos.offsetNorth());
            Block blockValue2 = blockStateValue3.getBlock();
            if (isBlockStairs(blockValue2)
                    && value == blockStateValue3.getValue(net.minecraft.block.BlockStairs.HALF)) {
                Facing facingValue3 =
                        (Facing) blockStateValue3.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue3 == Facing.WEST
                        && !isSameStair(iBlockAccess, blockPos.offsetWest(), blockState)) {
                    return i != 0 ? 2 : 1;
                }

                if (facingValue3 == Facing.EAST
                        && !isSameStair(iBlockAccess, blockPos.offsetEast(), blockState)) {
                    return i != 0 ? 1 : 2;
                }
            }
        } else if (facing == Facing.NORTH) {
            BlockState blockStateValue4 = iBlockAccess.getBlockState(blockPos.offsetSouth());
            Block blockValue3 = blockStateValue4.getBlock();
            if (isBlockStairs(blockValue3)
                    && value == blockStateValue4.getValue(net.minecraft.block.BlockStairs.HALF)) {
                Facing facingValue4 =
                        (Facing) blockStateValue4.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue4 == Facing.WEST
                        && !isSameStair(iBlockAccess, blockPos.offsetWest(), blockState)) {
                    return i != 0 ? 1 : 2;
                }

                if (facingValue4 == Facing.EAST
                        && !isSameStair(iBlockAccess, blockPos.offsetEast(), blockState)) {
                    return i != 0 ? 2 : 1;
                }
            }
        }

        return 0;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockStairs.FACING,
                    net.minecraft.block.BlockStairs.HALF,
                    net.minecraft.block.BlockStairs.SHAPE
                });
    }

    public RayTraceResult collisionRayTrace(
            World world, BlockPos blockPos, Vec3 vec3, Vec3 vec3Value) {
        RayTraceResult[] rayTraceResultArray = new RayTraceResult[8];
        int j2 = BlockPlanksEnumType.getLegacyBlockFlowToken();
        BlockState blockState = world.getBlockState(blockPos);
        int i = j2;
        int j =
                ((Facing) blockState.getValue(net.minecraft.block.BlockStairs.FACING))
                        .getHorizontalIndex();
        int k =
                blockState.getValue(net.minecraft.block.BlockStairs.HALF) == BlockStairsEnumHalf.TOP
                        ? 1
                        : 0;
        int[] aint = net.minecraft.block.BlockStairs.field_150150_a[j + (k != 0 ? 4 : 0)];
        this.hasRaytraced = true;
        int l = 0;

        while (l < 8) {
            this.rayTracePass = l;
            if (Arrays.binarySearch(aint, l) < 0) {
                rayTraceResultArray[l] = super.collisionRayTrace(world, blockPos, vec3, vec3Value);
            }

            l++;
            if (i == 0) {
                break;
            }
        }

        int[] aint1 = aint;
        int length = aint.length;
        int j1 = 0;

        while (j1 < length) {
            int k1 = aint1[j1];
            rayTraceResultArray[k1] = null;
            j1++;
            if (i == 0) {
                break;
            }
        }

        RayTraceResult rayTraceResult = null;
        double d1 = 0.0;
        RayTraceResult[] rayTraceResultArrayValue = rayTraceResultArray;
        int l1 = rayTraceResultArray.length;
        int i2 = 0;

        while (i2 < l1) {
            RayTraceResult rayTraceResultValue = rayTraceResultArrayValue[i2];
            if (rayTraceResultValue != null) {
                double d0 = rayTraceResultValue.hitVec.squareDistanceTo(vec3Value);
                if (d0 > d1) {
                    rayTraceResult = rayTraceResultValue;
                    d1 = d0;
                }
            }

            i2++;
            if (i == 0) {
                break;
            }
        }

        return rayTraceResult;
    }

    public boolean canCollideCheck(BlockState blockState, boolean flag) {
        return this.modelBlock.canCollideCheck(blockState, flag);
    }

    public boolean func_176306_h(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockStairs.FACING);
        BlockStairsEnumHalf value =
                (BlockStairsEnumHalf) blockState.getValue(net.minecraft.block.BlockStairs.HALF);
        int i = k;
        int j = value == BlockStairsEnumHalf.TOP ? 1 : 0;
        float f = 0.5F;
        float f1 = 1.0F;
        if (j != 0) {
            f = 0.0F;
            f1 = 0.5F;
        }

        float f2;
        float f3;
        float f4;
        float f5;
        byte b0;
        label110:
        {
            f2 = 0.0F;
            f3 = 1.0F;
            f4 = 0.0F;
            f5 = 0.5F;
            b0 = 1;
            if (facing == Facing.EAST) {
                f2 = 0.5F;
                f5 = 1.0F;
                BlockState blockStateValue = iBlockAccess.getBlockState(blockPos.offsetEast());
                Block block = blockStateValue.getBlock();
                if (!isBlockStairs(block)
                        || value
                                != blockStateValue.getValue(net.minecraft.block.BlockStairs.HALF)) {
                    break label110;
                }

                Facing facingValue =
                        (Facing) blockStateValue.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue == Facing.NORTH
                        && !isSameStair(iBlockAccess, blockPos.offsetSouth(), blockState)) {
                    f5 = 0.5F;
                    b0 = 0;
                    if (i != 0) {
                        break label110;
                    }
                }

                if (facingValue != Facing.SOUTH
                        || isSameStair(iBlockAccess, blockPos.offsetNorth(), blockState)) {
                    break label110;
                }

                f4 = 0.5F;
                b0 = 0;
                if (i != 0) {
                    break label110;
                }
            }

            if (facing == Facing.WEST) {
                f3 = 0.5F;
                f5 = 1.0F;
                BlockState blockStateValue2 = iBlockAccess.getBlockState(blockPos.offsetWest());
                Block blockValue = blockStateValue2.getBlock();
                if (!isBlockStairs(blockValue)
                        || value
                                != blockStateValue2.getValue(
                                        net.minecraft.block.BlockStairs.HALF)) {
                    break label110;
                }

                Facing facingValue2 =
                        (Facing) blockStateValue2.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue2 == Facing.NORTH
                        && !isSameStair(iBlockAccess, blockPos.offsetSouth(), blockState)) {
                    f5 = 0.5F;
                    b0 = 0;
                    if (i != 0) {
                        break label110;
                    }
                }

                if (facingValue2 != Facing.SOUTH
                        || isSameStair(iBlockAccess, blockPos.offsetNorth(), blockState)) {
                    break label110;
                }

                f4 = 0.5F;
                b0 = 0;
                if (i != 0) {
                    break label110;
                }
            }

            if (facing == Facing.SOUTH) {
                f4 = 0.5F;
                f5 = 1.0F;
                BlockState blockStateValue3 = iBlockAccess.getBlockState(blockPos.offsetSouth());
                Block blockValue2 = blockStateValue3.getBlock();
                if (!isBlockStairs(blockValue2)
                        || value
                                != blockStateValue3.getValue(
                                        net.minecraft.block.BlockStairs.HALF)) {
                    break label110;
                }

                Facing facingValue3 =
                        (Facing) blockStateValue3.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue3 == Facing.WEST
                        && !isSameStair(iBlockAccess, blockPos.offsetEast(), blockState)) {
                    f3 = 0.5F;
                    b0 = 0;
                    if (i != 0) {
                        break label110;
                    }
                }

                if (facingValue3 != Facing.EAST
                        || isSameStair(iBlockAccess, blockPos.offsetWest(), blockState)) {
                    break label110;
                }

                f2 = 0.5F;
                b0 = 0;
                if (i != 0) {
                    break label110;
                }
            }

            if (facing == Facing.NORTH) {
                BlockState blockStateValue4 = iBlockAccess.getBlockState(blockPos.offsetNorth());
                Block blockValue3 = blockStateValue4.getBlock();
                if (isBlockStairs(blockValue3)
                        && value
                                == blockStateValue4.getValue(
                                        net.minecraft.block.BlockStairs.HALF)) {
                    Facing facingValue4 =
                            (Facing)
                                    blockStateValue4.getValue(
                                            net.minecraft.block.BlockStairs.FACING);
                    if (facingValue4 == Facing.WEST
                            && !isSameStair(iBlockAccess, blockPos.offsetEast(), blockState)) {
                        f3 = 0.5F;
                        b0 = 0;
                        if (!(i != 0)) {

                            if (facingValue4 == Facing.EAST
                                    && !isSameStair(
                                            iBlockAccess, blockPos.offsetWest(), blockState)) {
                                f2 = 0.5F;
                                b0 = 0;
                            }
                        }
                    } else {

                        if (facingValue4 == Facing.EAST
                                && !isSameStair(iBlockAccess, blockPos.offsetWest(), blockState)) {
                            f2 = 0.5F;
                            b0 = 0;
                        }
                    }
                }
            }
        }

        this.setBlockBounds(f2, f, f4, f3, f1, f5);
        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        this.onNeighborBlockChange(world, blockPos, this.modelState, Blocks.air);
        this.modelBlock.onBlockAdded(world, blockPos, this.modelState);
    }

    public int getMixedBrightnessForBlock(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return this.modelBlock.getMixedBrightnessForBlock(iBlockAccess, blockPos);
    }

    public float getExplosionResistance(Entity entity) {
        return this.modelBlock.getExplosionResistance(entity);
    }

    protected BlockStairs(BlockState aUeValue) {
        super(aUeValue.getBlock().blockMaterial);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockStairs.FACING, Facing.NORTH)
                        .withProperty(
                                net.minecraft.block.BlockStairs.HALF, BlockStairsEnumHalf.BOTTOM)
                        .withProperty(
                                net.minecraft.block.BlockStairs.SHAPE,
                                BlockStairsEnumShape.STRAIGHT));
        this.modelBlock = aUeValue.getBlock();
        this.modelState = aUeValue;
        this.setHardness(this.modelBlock.blockHardness);
        this.setResistance(this.modelBlock.blockResistance / 3.0F);
        this.setStepSound(this.modelBlock.stepSound);
        this.setLightOpacity(255);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public void onEntityCollidedWithBlock(World world, BlockPos blockPos, Entity entity) {
        this.modelBlock.onEntityCollidedWithBlock(world, blockPos, entity);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (this.hasRaytraced) {
            this.setBlockBounds(
                    0.5F * (this.rayTracePass % 2),
                    0.5F * (this.rayTracePass / 4 % 2),
                    0.5F * (this.rayTracePass / 2 % 2),
                    0.5F + 0.5F * (this.rayTracePass % 2),
                    0.5F + 0.5F * (this.rayTracePass / 4 % 2),
                    0.5F + 0.5F * (this.rayTracePass / 2 % 2));
            if (i == 0) {
                return;
            }
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void setBaseCollisionBounds(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (iBlockAccess.getBlockState(blockPos).getValue(net.minecraft.block.BlockStairs.HALF)
                == BlockStairsEnumHalf.TOP) {
            this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
            if (i != 0) {
                return;
            }
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    public int func_176307_f(IBlockAccess iBlockAccess, BlockPos blockPos) {
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockStairs.FACING);
        BlockStairsEnumHalf value =
                (BlockStairsEnumHalf) blockState.getValue(net.minecraft.block.BlockStairs.HALF);
        int i = value == BlockStairsEnumHalf.TOP ? 1 : 0;
        if (facing == Facing.EAST) {
            BlockState blockStateValue = iBlockAccess.getBlockState(blockPos.offsetEast());
            Block block = blockStateValue.getBlock();
            if (isBlockStairs(block)
                    && value == blockStateValue.getValue(net.minecraft.block.BlockStairs.HALF)) {
                Facing facingValue =
                        (Facing) blockStateValue.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue == Facing.NORTH
                        && !isSameStair(iBlockAccess, blockPos.offsetSouth(), blockState)) {
                    return i != 0 ? 1 : 2;
                }

                if (facingValue == Facing.SOUTH
                        && !isSameStair(iBlockAccess, blockPos.offsetNorth(), blockState)) {
                    return i != 0 ? 2 : 1;
                }
            }
        } else if (facing == Facing.WEST) {
            BlockState blockStateValue2 = iBlockAccess.getBlockState(blockPos.offsetWest());
            Block blockValue = blockStateValue2.getBlock();
            if (isBlockStairs(blockValue)
                    && value == blockStateValue2.getValue(net.minecraft.block.BlockStairs.HALF)) {
                Facing facingValue2 =
                        (Facing) blockStateValue2.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue2 == Facing.NORTH
                        && !isSameStair(iBlockAccess, blockPos.offsetSouth(), blockState)) {
                    return i != 0 ? 2 : 1;
                }

                if (facingValue2 == Facing.SOUTH
                        && !isSameStair(iBlockAccess, blockPos.offsetNorth(), blockState)) {
                    return i != 0 ? 1 : 2;
                }
            }
        } else if (facing == Facing.SOUTH) {
            BlockState blockStateValue3 = iBlockAccess.getBlockState(blockPos.offsetSouth());
            Block blockValue2 = blockStateValue3.getBlock();
            if (isBlockStairs(blockValue2)
                    && value == blockStateValue3.getValue(net.minecraft.block.BlockStairs.HALF)) {
                Facing facingValue3 =
                        (Facing) blockStateValue3.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue3 == Facing.WEST
                        && !isSameStair(iBlockAccess, blockPos.offsetEast(), blockState)) {
                    return i != 0 ? 2 : 1;
                }

                if (facingValue3 == Facing.EAST
                        && !isSameStair(iBlockAccess, blockPos.offsetWest(), blockState)) {
                    return i != 0 ? 1 : 2;
                }
            }
        } else if (facing == Facing.NORTH) {
            BlockState blockStateValue4 = iBlockAccess.getBlockState(blockPos.offsetNorth());
            Block blockValue3 = blockStateValue4.getBlock();
            if (isBlockStairs(blockValue3)
                    && value == blockStateValue4.getValue(net.minecraft.block.BlockStairs.HALF)) {
                Facing facingValue4 =
                        (Facing) blockStateValue4.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue4 == Facing.WEST
                        && !isSameStair(iBlockAccess, blockPos.offsetEast(), blockState)) {
                    return i != 0 ? 1 : 2;
                }

                if (facingValue4 == Facing.EAST
                        && !isSameStair(iBlockAccess, blockPos.offsetWest(), blockState)) {
                    return i != 0 ? 2 : 1;
                }
            }
        }

        return 0;
    }

    public boolean func_176304_i(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockStairs.FACING);
        BlockStairsEnumHalf value =
                (BlockStairsEnumHalf) blockState.getValue(net.minecraft.block.BlockStairs.HALF);
        int i = k;
        int j = value == BlockStairsEnumHalf.TOP ? 1 : 0;
        float f = 0.5F;
        float f1 = 1.0F;
        if (j != 0) {
            f = 0.0F;
            f1 = 0.5F;
        }

        float f2;
        float f3;
        float f4;
        float f5;
        byte b0;
        label114:
        {
            f2 = 0.0F;
            f3 = 0.5F;
            f4 = 0.5F;
            f5 = 1.0F;
            b0 = 0;
            if (facing == Facing.EAST) {
                BlockState blockStateValue = iBlockAccess.getBlockState(blockPos.offsetWest());
                Block block = blockStateValue.getBlock();
                if (!isBlockStairs(block)
                        || value
                                != blockStateValue.getValue(net.minecraft.block.BlockStairs.HALF)) {
                    break label114;
                }

                Facing facingValue =
                        (Facing) blockStateValue.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue == Facing.NORTH
                        && !isSameStair(iBlockAccess, blockPos.offsetNorth(), blockState)) {
                    f4 = 0.0F;
                    f5 = 0.5F;
                    b0 = 1;
                    if (i == 0) {
                        break label114;
                    }
                }

                if (facingValue != Facing.SOUTH
                        || isSameStair(iBlockAccess, blockPos.offsetSouth(), blockState)) {
                    break label114;
                }

                f4 = 0.5F;
                f5 = 1.0F;
                b0 = 1;
                if (i == 0) {
                    break label114;
                }
            }

            if (facing == Facing.WEST) {
                BlockState blockStateValue2 = iBlockAccess.getBlockState(blockPos.offsetEast());
                Block blockValue = blockStateValue2.getBlock();
                if (!isBlockStairs(blockValue)
                        || value
                                != blockStateValue2.getValue(
                                        net.minecraft.block.BlockStairs.HALF)) {
                    break label114;
                }

                f2 = 0.5F;
                f3 = 1.0F;
                Facing facingValue2 =
                        (Facing) blockStateValue2.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue2 == Facing.NORTH
                        && !isSameStair(iBlockAccess, blockPos.offsetNorth(), blockState)) {
                    f4 = 0.0F;
                    f5 = 0.5F;
                    b0 = 1;
                    if (i == 0) {
                        break label114;
                    }
                }

                if (facingValue2 != Facing.SOUTH
                        || isSameStair(iBlockAccess, blockPos.offsetSouth(), blockState)) {
                    break label114;
                }

                f4 = 0.5F;
                f5 = 1.0F;
                b0 = 1;
                if (i == 0) {
                    break label114;
                }
            }

            if (facing == Facing.SOUTH) {
                BlockState blockStateValue3 = iBlockAccess.getBlockState(blockPos.offsetNorth());
                Block blockValue2 = blockStateValue3.getBlock();
                if (!isBlockStairs(blockValue2)
                        || value
                                != blockStateValue3.getValue(
                                        net.minecraft.block.BlockStairs.HALF)) {
                    break label114;
                }

                f4 = 0.0F;
                f5 = 0.5F;
                Facing facingValue3 =
                        (Facing) blockStateValue3.getValue(net.minecraft.block.BlockStairs.FACING);
                if (facingValue3 == Facing.WEST
                        && !isSameStair(iBlockAccess, blockPos.offsetWest(), blockState)) {
                    b0 = 1;
                    if (i == 0) {
                        break label114;
                    }
                }

                if (facingValue3 != Facing.EAST
                        || isSameStair(iBlockAccess, blockPos.offsetEast(), blockState)) {
                    break label114;
                }

                f2 = 0.5F;
                f3 = 1.0F;
                b0 = 1;
                if (i == 0) {
                    break label114;
                }
            }

            if (facing == Facing.NORTH) {
                BlockState blockStateValue4 = iBlockAccess.getBlockState(blockPos.offsetSouth());
                Block blockValue3 = blockStateValue4.getBlock();
                if (isBlockStairs(blockValue3)
                        && value
                                == blockStateValue4.getValue(
                                        net.minecraft.block.BlockStairs.HALF)) {
                    Facing facingValue4 =
                            (Facing)
                                    blockStateValue4.getValue(
                                            net.minecraft.block.BlockStairs.FACING);
                    if (facingValue4 == Facing.WEST
                            && !isSameStair(iBlockAccess, blockPos.offsetWest(), blockState)) {
                        b0 = 1;
                        if (!(i == 0)) {

                            if (facingValue4 == Facing.EAST
                                    && !isSameStair(
                                            iBlockAccess, blockPos.offsetEast(), blockState)) {
                                f2 = 0.5F;
                                f3 = 1.0F;
                                b0 = 1;
                            }
                        }
                    } else {

                        if (facingValue4 == Facing.EAST
                                && !isSameStair(iBlockAccess, blockPos.offsetEast(), blockState)) {
                            f2 = 0.5F;
                            f3 = 1.0F;
                            b0 = 1;
                        }
                    }
                }
            }
        }

        if (b0 != 0) {
            this.setBlockBounds(f2, f, f4, f3, f1, f5);
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        this.modelBlock.breakBlock(world, blockPos, this.modelState);
    }

    public boolean isCollidable() {
        return this.modelBlock.isCollidable();
    }

    public void onBlockDestroyedByExplosion(World world, BlockPos blockPos, Explosion explosion) {
        this.modelBlock.onBlockDestroyedByExplosion(world, blockPos, explosion);
    }

    // region String decoding support

    // endregion

    public static boolean isBlockStairs(Block block) {
        return block instanceof BlockStairs;
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        this.modelBlock.updateTick(world, blockPos, blockState, random);
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return this.modelBlock.getBlockLayer();
    }

    public void addCollisionBoxesToList(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            BoundingBox boundingBox,
            List list,
            Entity entity) {
        this.setBaseCollisionBounds(world, blockPos);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        boolean flag = this.func_176306_h(world, blockPos);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        if (flag && this.func_176304_i(world, blockPos)) {
            super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return this.modelBlock.canPlaceBlockAt(world, blockPos);
    }

    public void onBlockClicked(World world, BlockPos blockPos, PlayerEntity playerEntity) {
        this.modelBlock.onBlockClicked(world, blockPos, playerEntity);
    }

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2,
            int i,
            LivingEntity livingEntity) {
        BlockState blockState =
                super.onBlockPlaced(world, blockPos, facing, f, f1, f2, i, livingEntity);
        blockState =
                blockState
                        .withProperty(
                                net.minecraft.block.BlockStairs.FACING,
                                livingEntity.getHorizontalFacing())
                        .withProperty(
                                net.minecraft.block.BlockStairs.SHAPE,
                                BlockStairsEnumShape.STRAIGHT);
        return facing == Facing.DOWN || facing != Facing.UP && !(f1 <= 0.5)
                ? blockState.withProperty(
                        net.minecraft.block.BlockStairs.HALF, BlockStairsEnumHalf.TOP)
                : blockState.withProperty(
                        net.minecraft.block.BlockStairs.HALF, BlockStairsEnumHalf.BOTTOM);
    }

    public BoundingBox getSelectedBoundingBox(World world, BlockPos blockPos) {
        return this.modelBlock.getSelectedBoundingBox(world, blockPos);
    }

    public BlockState getStateFromMeta(int i) {
        BlockState blockState =
                this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockStairs.HALF,
                                (i & 4) > 0 ? BlockStairsEnumHalf.TOP : BlockStairsEnumHalf.BOTTOM);
        return blockState.withProperty(
                net.minecraft.block.BlockStairs.FACING, Facing.getFront(5 - (i & 3)));
    }

    public void onBlockDestroyedByPlayer(World world, BlockPos blockPos, BlockState blockState) {
        this.modelBlock.onBlockDestroyedByPlayer(world, blockPos, blockState);
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
        return this.modelBlock.onBlockActivated(
                world, blockPos, this.modelState, playerEntity, Facing.DOWN, 0.0F, 0.0F, 0.0F);
    }

    public static boolean isSameStair(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState) {
        BlockState blockStateValue = iBlockAccess.getBlockState(blockPos);
        Block block = blockStateValue.getBlock();
        return isBlockStairs(block)
                && blockStateValue.getValue(net.minecraft.block.BlockStairs.HALF)
                        == blockState.getValue(net.minecraft.block.BlockStairs.HALF)
                && blockStateValue.getValue(net.minecraft.block.BlockStairs.FACING)
                        == blockState.getValue(net.minecraft.block.BlockStairs.FACING);
    }

    public MapColor getMapColor(BlockState blockState) {
        return this.modelBlock.getMapColor(this.modelState);
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        if (blockState.getValue(net.minecraft.block.BlockStairs.HALF) == BlockStairsEnumHalf.TOP) {
            b0 |= 4;
        }

        return b0
                | 5
                        - ((Facing) blockState.getValue(net.minecraft.block.BlockStairs.FACING))
                                .getIndex();
    }

    public int tickRate(World world) {
        return this.modelBlock.tickRate(world);
    }

    public Vec3 modifyAcceleration(World world, BlockPos blockPos, Entity entity, Vec3 vec3) {
        return this.modelBlock.modifyAcceleration(world, blockPos, entity, vec3);
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        this.modelBlock.randomDisplayTick(world, blockPos, blockState, random);
    }

    static {
        net.minecraft.block.BlockStairs.FACING =
                PropertyDirection.create("facing", FacingPlane.HORIZONTAL);
        net.minecraft.block.BlockStairs.HALF =
                PropertyEnum.create("half", BlockStairsEnumHalf.class);
        net.minecraft.block.BlockStairs.SHAPE =
                PropertyEnum.create("shape", BlockStairsEnumShape.class);
        net.minecraft.block.BlockStairs.field_150150_a =
                new int[][] {{4, 5}, {5, 7}, {6, 7}, {4, 6}, {0, 1}, {1, 3}, {2, 3}, {0, 2}};
    }

    // region String decoding support

    // endregion

    public boolean isOpaqueCube() {
        return false;
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (this.func_176306_h(iBlockAccess, blockPos)) {
            switch (this.func_176305_g(iBlockAccess, blockPos)) {
                case 0:
                    blockState =
                            blockState.withProperty(
                                    net.minecraft.block.BlockStairs.SHAPE,
                                    BlockStairsEnumShape.STRAIGHT);
                    if (i != 0) {
                        break;
                    }
                case 1:
                    blockState =
                            blockState.withProperty(
                                    net.minecraft.block.BlockStairs.SHAPE,
                                    BlockStairsEnumShape.INNER_RIGHT);
                    if (i != 0) {
                        break;
                    }
                case 2:
                    blockState =
                            blockState.withProperty(
                                    net.minecraft.block.BlockStairs.SHAPE,
                                    BlockStairsEnumShape.INNER_LEFT);
            }

            if (i != 0) {
                return blockState;
            }
        }

        switch (this.func_176307_f(iBlockAccess, blockPos)) {
            case 0:
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockStairs.SHAPE,
                                BlockStairsEnumShape.STRAIGHT);
                if (i != 0) {
                    break;
                }
            case 1:
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockStairs.SHAPE,
                                BlockStairsEnumShape.OUTER_RIGHT);
                if (i != 0) {
                    break;
                }
            case 2:
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockStairs.SHAPE,
                                BlockStairsEnumShape.OUTER_LEFT);
        }

        return blockState;
    }

    public boolean isFullCube() {
        return false;
    }
}
