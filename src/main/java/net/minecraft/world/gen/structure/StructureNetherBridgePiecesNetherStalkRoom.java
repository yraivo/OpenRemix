package net.minecraft.world.gen.structure;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePiecesNetherStalkRoom extends StructureNetherBridgePiece {
    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        this.getNextComponentNormal(
                (StructureNetherBridgePiecesStart) structureComponent, list, random, 5, 3, true);
        this.getNextComponentNormal(
                (StructureNetherBridgePiecesStart) structureComponent, list, random, 5, 11, true);
    }

    public StructureNetherBridgePiecesNetherStalkRoom() {}

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
        int j2 = StructureComponent.cR();
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
        int i = j2;
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

        j = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 3);
        int k = 0;

        while (k <= 6) {
            int l = k + 4;
            int i1 = 5;

            while (i1 <= 7) {
                this.setBlockState(
                        world,
                        Blocks.nether_brick_stairs.getStateFromMeta(j),
                        i1,
                        5 + k,
                        l,
                        structureBoundingBox);
                i1++;
                if (i == 0) {
                    break;
                }
            }

            label89:
            {
                if (l >= 5 && l <= 8) {
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            5,
                            5,
                            l,
                            7,
                            k + 4,
                            l,
                            Blocks.nether_brick.getDefaultState(),
                            Blocks.nether_brick.getDefaultState(),
                            false);
                    if (i != 0) {
                        break label89;
                    }
                }

                if (l >= 9 && l <= 10) {
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            5,
                            8,
                            l,
                            7,
                            k + 4,
                            l,
                            Blocks.nether_brick.getDefaultState(),
                            Blocks.nether_brick.getDefaultState(),
                            false);
                }
            }

            if (k >= 1) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        5,
                        6 + k,
                        l,
                        7,
                        9 + k,
                        l,
                        Blocks.air.getDefaultState(),
                        Blocks.air.getDefaultState(),
                        false);
            }

            k++;
            if (i == 0) {
                break;
            }
        }

        k = 5;

        while (k <= 7) {
            this.setBlockState(
                    world,
                    Blocks.nether_brick_stairs.getStateFromMeta(j),
                    k,
                    12,
                    11,
                    structureBoundingBox);
            k++;
            if (i == 0) {
                break;
            }
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                6,
                7,
                5,
                7,
                7,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                6,
                7,
                7,
                7,
                7,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                13,
                12,
                7,
                13,
                12,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                5,
                2,
                3,
                5,
                3,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                5,
                9,
                3,
                5,
                10,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                5,
                4,
                2,
                5,
                8,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                9,
                5,
                2,
                10,
                5,
                3,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                9,
                5,
                9,
                10,
                5,
                10,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                10,
                5,
                4,
                10,
                5,
                8,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        k = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 0);
        int k1 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 1);
        this.setBlockState(
                world,
                Blocks.nether_brick_stairs.getStateFromMeta(k1),
                4,
                5,
                2,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.nether_brick_stairs.getStateFromMeta(k1),
                4,
                5,
                3,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.nether_brick_stairs.getStateFromMeta(k1),
                4,
                5,
                9,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.nether_brick_stairs.getStateFromMeta(k1),
                4,
                5,
                10,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.nether_brick_stairs.getStateFromMeta(k),
                8,
                5,
                2,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.nether_brick_stairs.getStateFromMeta(k),
                8,
                5,
                3,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.nether_brick_stairs.getStateFromMeta(k),
                8,
                5,
                9,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.nether_brick_stairs.getStateFromMeta(k),
                8,
                5,
                10,
                structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                4,
                4,
                4,
                4,
                8,
                Blocks.soul_sand.getDefaultState(),
                Blocks.soul_sand.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                4,
                4,
                9,
                4,
                8,
                Blocks.soul_sand.getDefaultState(),
                Blocks.soul_sand.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                5,
                4,
                4,
                5,
                8,
                Blocks.nether_wart.getDefaultState(),
                Blocks.nether_wart.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                5,
                4,
                9,
                5,
                8,
                Blocks.nether_wart.getDefaultState(),
                Blocks.nether_wart.getDefaultState(),
                false);
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
        int l1 = 4;

        label70:
        while (l1 <= 8) {
            int j1 = 0;

            while (true) {
                if (j1 <= 2) {
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            l1,
                            -1,
                            j1,
                            structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            l1,
                            -1,
                            12 - j1,
                            structureBoundingBox);
                    j1++;
                    if (i != 0) {
                        continue;
                    }
                }

                l1++;
                if (i == 0) {
                    break label70;
                }
                break;
            }
        }

        l1 = 0;

        label57:
        while (l1 <= 2) {
            int i2 = 4;

            while (true) {
                if (i2 <= 8) {
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            l1,
                            -1,
                            i2,
                            structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            12 - l1,
                            -1,
                            i2,
                            structureBoundingBox);
                    i2++;
                    if (i != 0) {
                        continue;
                    }
                }

                l1++;
                if (i == 0) {
                    break label57;
                }
                break;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            StructureComponent.b(++i);
        }

        return true;
    }

    public StructureNetherBridgePiecesNetherStalkRoom(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }

    public static StructureNetherBridgePiecesNetherStalkRoom method_2b0b0c528d1655a58b28(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -5, -3, 0, 13, 14, 13, facing);
        return isAboveGround(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureNetherBridgePiecesNetherStalkRoom(l, random, structureBoundingBox, facing)
                : null;
    }
}
