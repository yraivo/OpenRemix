package net.minecraft.world.gen.structure;

import net.minecraft.block.BlockSilverfishEnumType;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;

import java.util.Random;

class StructureStrongholdPiecesStones extends StructureComponentBlockSelector {
    public void selectBlocks(Random random, int j, int k, int l, boolean flag) {
        int i = StructureComponent.f();
        if (flag) {
            label31:
            {
                float f = random.nextFloat();
                if (f < 0.2F) {
                    this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CRACKED_META);
                    if (i == 0) {
                        break label31;
                    }
                }

                if (f < 0.5F) {
                    this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.MOSSY_META);
                    if (i == 0) {
                        break label31;
                    }
                }

                if (f < 0.55F) {
                    this.blockstate =
                            Blocks.monster_egg.getStateFromMeta(
                                    BlockSilverfishEnumType.STONEBRICK.getMetadata());
                    if (i == 0) {
                        break label31;
                    }
                }

                this.blockstate = Blocks.stonebrick.getDefaultState();
            }

            if (i == 0) {
                return;
            }
        }

        this.blockstate = Blocks.air.getDefaultState();
    }

    private StructureStrongholdPiecesStones() {}

    StructureStrongholdPiecesStones(Object object) {
        this();
    }
}
