package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.world.World;

public class BlockPortalSize {
    private Facing aAA;
    private BlockPos field_150861_f;
    private Facing Uf;
    private FacingAxis axis;
    private int field_150864_e;
    private int field_150868_h;
    private World world;
    private int field_150862_g;

    static int access$200(BlockPortalSize blockPortalSize) {
        return blockPortalSize.field_150862_g;
    }

    public BlockPortalSize(World world, BlockPos blockPos, FacingAxis facingAxis) {
        super();
        int initialFlowToken = BlockPlanksEnumType.getLegacyBlockFlowToken();

        int i;
        {
            int k = initialFlowToken;

            this.field_150864_e = 0;
            this.world = world;
            this.axis = facingAxis;
            i = k;
            if (facingAxis == FacingAxis.X) {
                this.Uf = Facing.EAST;
                this.aAA = Facing.WEST;
                if (!(i != 0)) {

                    this.Uf = Facing.NORTH;
                    this.aAA = Facing.SOUTH;
                }
            } else {

                this.Uf = Facing.NORTH;
                this.aAA = Facing.SOUTH;
            }
        }

        BlockPos blockPosValue = blockPos;

        while (blockPos.getY() > blockPosValue.getY() - 21
                && blockPos.getY() > 0
                && this.func_150857_a(world.getBlockState(blockPos.offsetDown()).getBlock())) {
            blockPos = blockPos.offsetDown();
            if (i == 0) {
                break;
            }
        }

        int j = this.method_11b7c1a81196f3154ef7(blockPos, this.Uf) - 1;
        if (j >= 0) {
            this.field_150861_f = blockPos.offset(this.Uf, j);
            this.field_150868_h = this.method_11b7c1a81196f3154ef7(this.field_150861_f, this.aAA);
            if (this.field_150868_h < 2 || this.field_150868_h > 21) {
                this.field_150861_f = null;
                this.field_150868_h = 0;
            }
        }

        if (this.field_150861_f != null) {
            this.field_150862_g = this.method_9b417d27949cba33a0d0();
        }
    }

    static int access$000(BlockPortalSize blockPortalSize) {
        return blockPortalSize.field_150864_e;
    }

    protected boolean func_150857_a(Block block) {
        return block.blockMaterial == Material.AIR
                || block == Blocks.fire
                || block == Blocks.portal;
    }

    public void func_150859_c() {
        int l = BlockPlanksEnumType.getLegacyBlockFlowToken();
        int j = 0;
        int i = l;

        while (j < this.field_150868_h) {
            BlockPos blockPos = this.field_150861_f.offset(this.aAA, j);
            int k = 0;

            while (true) {
                if (k < this.field_150862_g) {
                    this.world.setBlockState(
                            blockPos.offsetUp(k),
                            Blocks.portal
                                    .getDefaultState()
                                    .withProperty(BlockPortal.AXIS, this.axis),
                            2);
                    k++;
                    if (i != 0) {
                        continue;
                    }
                }

                j++;
                if (i == 0) {
                    return;
                }
                break;
            }
        }
    }

    protected int method_11b7c1a81196f3154ef7(BlockPos blockPos, Facing facing) {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int j = 0;
        int i = k;

        while (j < 22) {
            BlockPos blockPosValue = blockPos.offset(facing, j);
            if (!this.func_150857_a(this.world.getBlockState(blockPosValue).getBlock())
                    || this.world.getBlockState(blockPosValue.offsetDown()).getBlock()
                                    != Blocks.obsidian
                            && i == 0) {
                break;
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        Block block = this.world.getBlockState(blockPos.offset(facing, j)).getBlock();
        return block == Blocks.obsidian ? j : 0;
    }

    public boolean func_150860_b() {
        return this.field_150861_f != null
                && this.field_150868_h >= 2
                && this.field_150868_h <= 21
                && this.field_150862_g >= 3
                && this.field_150862_g <= 21;
    }

    protected int method_9b417d27949cba33a0d0() {
        int l = BlockPlanksEnumType.getLegacyBlockFlowToken();
        this.field_150862_g = 0;
        int i = l;

        label67:
        while (this.field_150862_g < 21) {
            int j = 0;

            while (true) {
                if (j < this.field_150868_h) {
                    BlockPos blockPos =
                            this.field_150861_f.offset(this.aAA, j).offsetUp(this.field_150862_g);
                    Block block = this.world.getBlockState(blockPos).getBlock();
                    if (!this.func_150857_a(block) && i != 0) {
                        break label67;
                    }

                    if (block == Blocks.portal) {
                        this.field_150864_e++;
                    }

                    label58:
                    {
                        if (j == 0) {
                            block = this.world.getBlockState(blockPos.offset(this.Uf)).getBlock();
                            if (block == Blocks.obsidian) {
                                break label58;
                            }

                            if (i != 0) {
                                break label67;
                            }
                        }

                        if (j == this.field_150868_h - 1) {
                            block = this.world.getBlockState(blockPos.offset(this.aAA)).getBlock();
                            if (block != Blocks.obsidian && i != 0) {
                                break label67;
                            }
                        }
                    }

                    j++;
                    if (i != 0) {
                        continue;
                    }
                }

                this.field_150862_g++;
                if (i == 0) {
                    break label67;
                }
                break;
            }
        }

        int k = 0;

        while (k < this.field_150868_h) {
            if (this.world
                            .getBlockState(
                                    this.field_150861_f
                                            .offset(this.aAA, k)
                                            .offsetUp(this.field_150862_g))
                            .getBlock()
                    != Blocks.obsidian) {
                this.field_150862_g = 0;
                if (i != 0) {
                    break;
                }
            }

            k++;
            if (i == 0) {
                break;
            }
        }

        if (this.field_150862_g <= 21 && this.field_150862_g >= 3) {
            return this.field_150862_g;
        }

        this.field_150861_f = null;
        this.field_150868_h = 0;
        this.field_150862_g = 0;
        return 0;
    }

    static int access$100(BlockPortalSize blockPortalSize) {
        return blockPortalSize.field_150868_h;
    }
}
