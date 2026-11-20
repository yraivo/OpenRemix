package net.minecraft.world.gen.structure;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePiecesVillage;

import java.util.List;
import java.util.Random;

public class StructureVillagePiecesHouse3 extends StructureVillagePiecesVillage {
    public static StructureVillagePiecesHouse3 createPiece(
            StructureVillagePiecesStart p171_0,
            List list,
            Random random,
            int p171_3,
            int p171_4,
            int p171_5,
            Facing facing,
            int p171_7) {
        net.minecraft.world.gen.structure.StructureBoundingBox structureBoundingBox =
                net.minecraft.world.gen.structure.StructureBoundingBox.getComponentToAddBoundingBox(
                        p171_3, p171_4, p171_5, 0, 0, 0, 9, 7, 12, facing);
        return net.minecraft.world.gen.structure.StructureVillagePiecesVillage.canVillageGoDeeper(structureBoundingBox)
                        && net.minecraft.world.gen.structure.StructureComponent.findIntersecting(list, structureBoundingBox)
                                == null
                ? new net.minecraft.world.gen.structure.StructureVillagePiecesHouse3(
                        p171_0, p171_7, random, structureBoundingBox, facing)
                : null;
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (this.ef < 0) {
            this.ef = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.ef < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.ef - this.boundingBox.maxY + 7 - 1, 0);
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                1,
                7,
                4,
                4,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                1,
                6,
                8,
                4,
                10,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                0,
                5,
                8,
                0,
                10,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                0,
                1,
                7,
                0,
                4,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                0,
                3,
                5,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                0,
                0,
                8,
                3,
                10,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                0,
                0,
                7,
                2,
                0,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                0,
                5,
                2,
                1,
                5,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                0,
                6,
                2,
                3,
                10,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                0,
                10,
                7,
                3,
                10,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                2,
                0,
                7,
                3,
                0,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                2,
                5,
                2,
                3,
                5,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                4,
                1,
                8,
                4,
                1,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                4,
                4,
                3,
                4,
                4,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                5,
                2,
                8,
                5,
                3,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 0, 4, 2, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 0, 4, 3, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 4, 2, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 4, 3, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 4, 4, structureBoundingBox);
        int j = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
        int k = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
        int l = -1;

        label131:
        while (l <= 2) {
            int i1 = 0;

            while (true) {
                if (i1 <= 8) {
                    this.setBlockState(
                            world,
                            Blocks.oak_stairs.getStateFromMeta(j),
                            i1,
                            4 + l,
                            l,
                            structureBoundingBox);
                    if ((l > -1 || i1 <= 1)
                            && (l > 0 || i1 <= 3)
                            && (l > 1 || i1 <= 4 || i1 >= 6)) {
                        this.setBlockState(
                                world,
                                Blocks.oak_stairs.getStateFromMeta(k),
                                i1,
                                4 + l,
                                5 - l,
                                structureBoundingBox);
                    }

                    i1++;
                    if (i == 0) {
                        continue;
                    }
                }

                l++;
                if (i != 0) {
                    break label131;
                }
                break;
            }
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                4,
                5,
                3,
                4,
                10,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                4,
                2,
                7,
                4,
                10,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                5,
                4,
                4,
                5,
                10,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                5,
                4,
                6,
                5,
                10,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                6,
                3,
                5,
                6,
                10,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        l = this.getMetadataWithOffset(Blocks.oak_stairs, 0);
        int l1 = 4;

        label106:
        while (l1 >= 1) {
            this.setBlockState(
                    world,
                    Blocks.planks.getDefaultState(),
                    l1,
                    2 + l1,
                    7 - l1,
                    structureBoundingBox);
            int j1 = 8 - l1;

            while (true) {
                if (j1 <= 10) {
                    this.setBlockState(
                            world,
                            Blocks.oak_stairs.getStateFromMeta(l),
                            l1,
                            2 + l1,
                            j1,
                            structureBoundingBox);
                    j1++;
                    if (i == 0) {
                        continue;
                    }
                }

                l1--;
                if (i != 0) {
                    break label106;
                }
                break;
            }
        }

        l1 = this.getMetadataWithOffset(Blocks.oak_stairs, 1);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 6, 6, 3, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 7, 5, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_stairs.getStateFromMeta(l1), 6, 6, 4, structureBoundingBox);
        int i2 = 6;

        label93:
        while (i2 <= 8) {
            int k1 = 5;

            while (true) {
                if (k1 <= 10) {
                    this.setBlockState(
                            world,
                            Blocks.oak_stairs.getStateFromMeta(l1),
                            i2,
                            12 - i2,
                            k1,
                            structureBoundingBox);
                    k1++;
                    if (i == 0) {
                        continue;
                    }
                }

                i2++;
                if (i != 0) {
                    break label93;
                }
                break;
            }
        }

        this.setBlockState(world, Blocks.log.getDefaultState(), 0, 2, 1, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 0, 2, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 4, 2, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 6, 2, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 8, 2, 1, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 8, 2, 4, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 2, 5, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 8, 2, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 8, 2, 7, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 8, 2, 8, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 8, 2, 9, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 2, 2, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 2, 2, 7, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 2, 2, 8, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 2, 2, 9, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 4, 4, 10, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 5, 4, 10, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 6, 4, 10, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 5, 5, 10, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.torch
                        .getDefaultState()
                        .withProperty(BlockTorch.FACING_PROP, this.coordBaseMode),
                2,
                3,
                1,
                structureBoundingBox);
        this.placeDoorCurrentPosition(
                world,
                structureBoundingBox,
                random,
                2,
                1,
                0,
                Facing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                0,
                -1,
                3,
                2,
                -1,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        if (this.getBlockStateFromPos(world, 2, 0, -1, structureBoundingBox)
                                .getBlock()
                                .getMaterial()
                        == Material.AIR
                && this.getBlockStateFromPos(world, 2, -1, -1, structureBoundingBox)
                                .getBlock()
                                .getMaterial()
                        != Material.AIR) {
            this.setBlockState(
                    world,
                    Blocks.stone_stairs.getStateFromMeta(
                            this.getMetadataWithOffset(Blocks.stone_stairs, 3)),
                    2,
                    0,
                    -1,
                    structureBoundingBox);
        }

        i2 = 0;

        label80:
        while (i2 < 5) {
            int j2 = 0;

            while (true) {
                if (j2 < 9) {
                    this.clearCurrentPositionBlocksUpwards(world, j2, 7, i2, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            j2,
                            -1,
                            i2,
                            structureBoundingBox);
                    j2++;
                    if (i == 0) {
                        continue;
                    }
                }

                i2++;
                if (i != 0) {
                    break label80;
                }
                break;
            }
        }

        i2 = 5;

        label67:
        while (i2 < 11) {
            int k2 = 2;

            while (true) {
                if (k2 < 9) {
                    this.clearCurrentPositionBlocksUpwards(world, k2, 7, i2, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            k2,
                            -1,
                            i2,
                            structureBoundingBox);
                    k2++;
                    if (i == 0) {
                        continue;
                    }
                }

                i2++;
                if (i != 0) {
                    break label67;
                }
                break;
            }
        }

        this.spawnVillagers(world, structureBoundingBox, 4, 1, 2, 2);
        return true;
    }

    public StructureVillagePiecesHouse3(
            StructureVillagePiecesStart p171_0,
            int p171_1,
            Random random,
            StructureBoundingBox bMdValue,
            Facing aAcValue) {
        super(p171_0, p171_1);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }

    public StructureVillagePiecesHouse3() {}
}
