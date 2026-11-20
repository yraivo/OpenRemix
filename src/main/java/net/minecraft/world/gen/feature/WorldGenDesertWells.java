package net.minecraft.world.gen.feature;

import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandEnumType;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlabEnumBlockHalf;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabEnumType;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;

import com.google.common.base.Predicates;

import java.util.Random;

public class WorldGenDesertWells extends WorldGenerator {
    private BlockState field_175910_d;
    private BlockState field_175912_c;
    private final BlockState field_175911_b =
            Blocks.stone_slab
                    .getDefaultState()
                    .withProperty(BlockStoneSlab.VARIANT, BlockStoneSlabEnumType.SAND)
                    .withProperty(BlockSlab.HALF_PROP, BlockSlabEnumBlockHalf.BOTTOM);
    private static final BlockStateHelper field_175913_a =
            BlockStateHelper.forBlock(Blocks.sand)
                    .where(
                            BlockSand.VARIANT_PROP, Predicates.equalTo(BlockSandEnumType.SAND));

    public boolean generate(World world, Random random, BlockPos blockPos) {
        String text = WorldGenerator.method_c60693f457aa70be7160();

        while (world.isAirBlock(blockPos) && blockPos.getY() > 2) {
            blockPos = blockPos.offsetDown();
            if (text == null) {
                break;
            }
        }

        if (!net.minecraft.world.gen.feature.WorldGenDesertWells.field_175913_a.apply(
                world.getBlockState(blockPos))) {
            return false;
        }

        int i = -2;

        label144:
        while (i <= 2) {
            int j = -2;

            while (true) {
                if (j <= 2) {
                    if (world.isAirBlock(blockPos.add(i, -1, j))
                            && world.isAirBlock(blockPos.add(i, -2, j))) {
                        return false;
                    }

                    j++;
                    if (text != null) {
                        continue;
                    }
                }

                i++;
                if (text == null) {
                    break label144;
                }
                break;
            }
        }

        i = -1;

        label130:
        while (i <= 0) {
            int l = -2;

            while (true) {
                if (l <= 2) {
                    int k = -2;

                    while (k <= 2) {
                        world.setBlockState(blockPos.add(l, i, k), this.field_175912_c, 2);
                        k++;
                        if (text == null) {
                            break;
                        }
                    }

                    l++;
                    if (text != null) {
                        continue;
                    }
                }

                i++;
                if (text == null) {
                    break label130;
                }
                break;
            }
        }

        world.setBlockState(blockPos, this.field_175910_d, 2);

        for (java.util.Iterator<?> __recoveryIterator171_3303 = (FacingPlane.HORIZONTAL).iterator();
                (__recoveryIterator171_3303).hasNext(); ) {
            Facing facing = (Facing) (__recoveryIterator171_3303).next();
            world.setBlockState(blockPos.offset(facing), this.field_175910_d, 2);
            if (text == null) {
                break;
            }
        }

        i = -2;

        label103:
        while (i <= 2) {
            int i1 = -2;

            while (true) {
                if (i1 <= 2) {
                    if (i == -2 || i == 2 || i1 == -2 || i1 == 2) {
                        world.setBlockState(blockPos.add(i, 1, i1), this.field_175912_c, 2);
                    }

                    i1++;
                    if (text != null) {
                        continue;
                    }
                }

                i++;
                if (text == null) {
                    break label103;
                }
                break;
            }
        }

        world.setBlockState(blockPos.add(2, 1, 0), this.field_175911_b, 2);
        world.setBlockState(blockPos.add(-2, 1, 0), this.field_175911_b, 2);
        world.setBlockState(blockPos.add(0, 1, 2), this.field_175911_b, 2);
        world.setBlockState(blockPos.add(0, 1, -2), this.field_175911_b, 2);
        i = -1;

        label83:
        while (i <= 1) {
            int j1 = -1;

            while (true) {
                if (j1 <= 1) {
                    {
                        if (i == 0 && j1 == 0) {
                            world.setBlockState(blockPos.add(i, 4, j1), this.field_175912_c, 2);
                            if (!(text != null)) {

                                world.setBlockState(blockPos.add(i, 4, j1), this.field_175911_b, 2);
                            }
                        } else {

                            world.setBlockState(blockPos.add(i, 4, j1), this.field_175911_b, 2);
                        }
                    }

                    j1++;
                    if (text != null) {
                        continue;
                    }
                }

                i++;
                if (text == null) {
                    break label83;
                }
                break;
            }
        }

        i = 1;

        while (i <= 3) {
            world.setBlockState(blockPos.add(-1, i, -1), this.field_175912_c, 2);
            world.setBlockState(blockPos.add(-1, i, 1), this.field_175912_c, 2);
            world.setBlockState(blockPos.add(1, i, -1), this.field_175912_c, 2);
            world.setBlockState(blockPos.add(1, i, 1), this.field_175912_c, 2);
            i++;
            if (text == null) {
                break;
            }
        }

        return true;
    }

    public WorldGenDesertWells() {
        this.field_175912_c = Blocks.sandstone.getDefaultState();
        this.field_175910_d = Blocks.flowing_water.getDefaultState();
    }
}
