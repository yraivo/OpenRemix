package net.minecraft.entity.monster;

import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSilverfishEnumType;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

class EntitySilverfishAISummonSilverfish extends EntityAIBase {
    EntitySilverfish bjT;
    private static String f;
    private int field_179463_b;

    private EntitySilverfish bjV;

    public void updateTask() {
        int j = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        this.field_179463_b--;
        int i = j;
        if (this.field_179463_b <= 0) {
            World world = this.bjV.world;
            Random random = this.bjV.getRNG();
            BlockPos blockPos = new BlockPos(this.bjV);
            byte b0 = 0;

            while (b0 <= 5 && b0 >= -5) {
                byte b1 = 0;

                while (b1 <= 10 && b1 >= -10) {
                    byte b2 = 0;

                    while (b2 <= 10 && b2 >= -10) {
                        BlockPos blockPosValue = blockPos.add(b1, b0, b2);
                        BlockState blockState = world.getBlockState(blockPosValue);
                        if (blockState.getBlock() == Blocks.monster_egg) {
                            label61:
                            {
                                if (world.getGameRules().getGameRuleBooleanValue(f)) {
                                    world.destroyBlock(blockPosValue, true);
                                    if (i == 0) {
                                        break label61;
                                    }
                                }

                                world.setBlockState(
                                        blockPosValue,
                                        ((BlockSilverfishEnumType)
                                                        blockState.getValue(
                                                                BlockSilverfish.VARIANT_PROP))
                                                .getModelBlock(),
                                        3);
                            }

                            if (random.nextBoolean()) {
                                return;
                            }
                        }

                        b2 = (byte) (b2 <= 0 ? 1 - b2 : 0 - b2);
                        if (i != 0) {
                            break;
                        }
                    }

                    b1 = (byte) (b1 <= 0 ? 1 - b1 : 0 - b1);
                    if (i != 0) {
                        break;
                    }
                }

                b0 = (byte) (b0 <= 0 ? 1 - b0 : 0 - b0);
                if (i != 0) {
                    break;
                }
            }
        }
    }

    static {
        f = "mobGriefing";
    }

    EntitySilverfishAISummonSilverfish(EntitySilverfish bjTValue) {
        this.bjT = bjTValue;
        this.bjV = this.bjT;
    }

    public void func_179462_f() {
        if (this.field_179463_b == 0) {
            this.field_179463_b = 20;
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public boolean shouldExecute() {
        return this.field_179463_b > 0;
    }
}
