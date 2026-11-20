package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePiecesEnd extends StructureNetherBridgePiece {

    private int fillSeed;

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.fillSeed = compoundTag.getInteger("Seed");
    }

    public static StructureNetherBridgePiecesEnd method_12abd4d1ac3751bacef7(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -1, -3, 0, 5, 10, 8, facing);
        return isAboveGround(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureNetherBridgePiecesEnd(l, random, structureBoundingBox, facing)
                : null;
    }


    public boolean addComponentParts(
            World world, Random random1, StructureBoundingBox structureBoundingBox) {
        int l1 = StructureComponent.f();
        Random random = new Random(this.fillSeed);
        int j = 0;
        int i = l1;

        label50:
        while (j <= 4) {
            int k = 3;

            while (true) {
                if (k <= 4) {
                    int l = random.nextInt(8);
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            j,
                            k,
                            0,
                            j,
                            k,
                            l,
                            Blocks.nether_brick.getDefaultState(),
                            Blocks.nether_brick.getDefaultState(),
                            false);
                    k++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label50;
                }
                break;
            }
        }

        j = random.nextInt(8);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                5,
                0,
                0,
                5,
                j,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        j = random.nextInt(8);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                5,
                0,
                4,
                5,
                j,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        j = 0;

        while (j <= 4) {
            int i1 = random.nextInt(5);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    j,
                    2,
                    0,
                    j,
                    2,
                    i1,
                    Blocks.nether_brick.getDefaultState(),
                    Blocks.nether_brick.getDefaultState(),
                    false);
            j++;
            if (i != 0) {
                break;
            }
        }

        j = 0;

        while (j <= 4) {
            int j1 = 0;

            while (true) {
                if (j1 <= 1) {
                    int k1 = random.nextInt(3);
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            j,
                            j1,
                            0,
                            j,
                            j1,
                            k1,
                            Blocks.nether_brick.getDefaultState(),
                            Blocks.nether_brick.getDefaultState(),
                            false);
                    j1++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    return true;
                }
                break;
            }
        }

        return true;
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setInteger("Seed", this.fillSeed);
    }

    public StructureNetherBridgePiecesEnd() {}

    public StructureNetherBridgePiecesEnd(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
        this.fillSeed = random.nextInt();
    }
}
