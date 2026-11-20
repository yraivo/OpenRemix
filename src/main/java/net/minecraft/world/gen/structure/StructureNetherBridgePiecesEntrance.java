package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePiecesEntrance extends StructureNetherBridgePiece {
    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        this.getNextComponentNormal(
                (StructureNetherBridgePiecesStart) structureComponent, list, random, 5, 3, true);
    }

    public static StructureNetherBridgePiecesEntrance method_4da62529495b1c9d1b5d(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -5, -3, 0, 13, 14, 13, facing);
        return isAboveGround(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureNetherBridgePiecesEntrance(l, random, structureBoundingBox, facing)
                : null;
    }

    public StructureNetherBridgePiecesEntrance() {}

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                3,
                0,
                12,
                4,
                12,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                5,
                0,
                12,
                13,
                12,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        int i1 = StructureComponent.cR();
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                5,
                0,
                1,
                12,
                12,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                11,
                5,
                0,
                12,
                12,
                12,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                5,
                11,
                4,
                12,
                12,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                5,
                11,
                10,
                12,
                12,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        int i = i1;
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                9,
                11,
                7,
                12,
                12,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                5,
                0,
                4,
                12,
                1,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                5,
                0,
                10,
                12,
                1,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                9,
                0,
                7,
                12,
                1,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                11,
                2,
                10,
                12,
                10,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                8,
                0,
                7,
                8,
                0,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        int j = 1;

        while (j <= 11) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    j,
                    10,
                    0,
                    j,
                    11,
                    0,
                    Blocks.nether_brick_fence.getDefaultState(),
                    Blocks.nether_brick_fence.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    j,
                    10,
                    12,
                    j,
                    11,
                    12,
                    Blocks.nether_brick_fence.getDefaultState(),
                    Blocks.nether_brick_fence.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    0,
                    10,
                    j,
                    0,
                    11,
                    j,
                    Blocks.nether_brick_fence.getDefaultState(),
                    Blocks.nether_brick_fence.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    12,
                    10,
                    j,
                    12,
                    11,
                    j,
                    Blocks.nether_brick_fence.getDefaultState(),
                    Blocks.nether_brick_fence.getDefaultState(),
                    false);
            this.setBlockState(
                    world, Blocks.nether_brick.getDefaultState(), j, 13, 0, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.nether_brick.getDefaultState(), j, 13, 12, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.nether_brick.getDefaultState(), 0, 13, j, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.nether_brick.getDefaultState(), 12, 13, j, structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.nether_brick_fence.getDefaultState(),
                    j + 1,
                    13,
                    0,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.nether_brick_fence.getDefaultState(),
                    j + 1,
                    13,
                    12,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.nether_brick_fence.getDefaultState(),
                    0,
                    13,
                    j + 1,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.nether_brick_fence.getDefaultState(),
                    12,
                    13,
                    j + 1,
                    structureBoundingBox);
            j += 2;
            if (i == 0) {
                break;
            }
        }

        this.setBlockState(
                world, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.nether_brick_fence.getDefaultState(),
                0,
                13,
                12,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.nether_brick_fence.getDefaultState(),
                12,
                13,
                0,
                structureBoundingBox);
        j = (byte) 3;

        while (j <= 9) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    1,
                    7,
                    j,
                    1,
                    8,
                    j,
                    Blocks.nether_brick_fence.getDefaultState(),
                    Blocks.nether_brick_fence.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    11,
                    7,
                    j,
                    11,
                    8,
                    j,
                    Blocks.nether_brick_fence.getDefaultState(),
                    Blocks.nether_brick_fence.getDefaultState(),
                    false);
            j += 2;
            if (i == 0) {
                break;
            }
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                2,
                0,
                8,
                2,
                12,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                4,
                12,
                2,
                8,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                0,
                0,
                8,
                1,
                3,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                0,
                9,
                8,
                1,
                12,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                4,
                3,
                1,
                8,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                9,
                0,
                4,
                12,
                1,
                8,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        j = 4;

        label50:
        while (j <= 8) {
            int k = 0;

            while (true) {
                if (k <= 2) {
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            j,
                            -1,
                            k,
                            structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            j,
                            -1,
                            12 - k,
                            structureBoundingBox);
                    k++;
                    if (i != 0) {
                        continue;
                    }
                }

                j++;
                if (i == 0) {
                    break label50;
                }
                break;
            }
        }

        j = 0;

        label37:
        while (j <= 2) {
            int l = 4;

            while (true) {
                if (l <= 8) {
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            j,
                            -1,
                            l,
                            structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            12 - j,
                            -1,
                            l,
                            structureBoundingBox);
                    l++;
                    if (i != 0) {
                        continue;
                    }
                }

                j++;
                if (i == 0) {
                    break label37;
                }
                break;
            }
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                5,
                5,
                7,
                5,
                7,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                1,
                6,
                6,
                4,
                6,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.nether_brick.getDefaultState(), 6, 0, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.flowing_lava.getDefaultState(), 6, 5, 6, structureBoundingBox);
        BlockPos blockPos =
                new BlockPos(
                        this.getXWithOffset(6, 6),
                        this.getYWithOffset(5),
                        this.getZWithOffset(6, 6));
        if (structureBoundingBox.isVecInside(blockPos)) {
            world.forceBlockUpdateTick(Blocks.flowing_lava, blockPos, random);
        }

        return true;
    }

    public StructureNetherBridgePiecesEntrance(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }
}
