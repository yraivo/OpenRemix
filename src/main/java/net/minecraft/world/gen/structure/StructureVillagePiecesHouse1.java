package net.minecraft.world.gen.structure;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePiecesVillage;

import java.util.List;
import java.util.Random;

public class StructureVillagePiecesHouse1 extends StructureVillagePiecesVillage {
    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (this.ef < 0) {
            this.ef = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.ef < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.ef - this.boundingBox.maxY + 9 - 1, 0);
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                1,
                7,
                5,
                4,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                8,
                0,
                5,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                5,
                0,
                8,
                5,
                5,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                6,
                1,
                8,
                6,
                4,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                7,
                2,
                8,
                7,
                3,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        int j = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
        int k = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
        int l = -1;

        label54:
        while (l <= 2) {
            int i1 = 0;

            while (true) {
                if (i1 <= 8) {
                    this.setBlockState(
                            world,
                            Blocks.oak_stairs.getStateFromMeta(j),
                            i1,
                            6 + l,
                            l,
                            structureBoundingBox);
                    this.setBlockState(
                            world,
                            Blocks.oak_stairs.getStateFromMeta(k),
                            i1,
                            6 + l,
                            5 - l,
                            structureBoundingBox);
                    i1++;
                    if (i == 0) {
                        continue;
                    }
                }

                l++;
                if (i != 0) {
                    break label54;
                }
                break;
            }
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                0,
                0,
                1,
                5,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                5,
                8,
                1,
                5,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                1,
                0,
                8,
                1,
                4,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                1,
                0,
                7,
                1,
                0,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                0,
                0,
                4,
                0,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                5,
                0,
                4,
                5,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                2,
                5,
                8,
                4,
                5,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                2,
                0,
                8,
                4,
                0,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                1,
                0,
                4,
                4,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                2,
                5,
                7,
                4,
                5,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                2,
                1,
                8,
                4,
                4,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                2,
                0,
                7,
                4,
                0,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 4, 2, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 6, 2, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 4, 3, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 5, 3, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 6, 3, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 3, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 3, 3, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 8, 3, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 8, 3, 3, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 2, 2, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 3, 2, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 5, 2, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 6, 2, 5, structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                4,
                1,
                7,
                4,
                1,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                4,
                4,
                7,
                4,
                4,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                3,
                4,
                7,
                3,
                4,
                Blocks.bookshelf.getDefaultState(),
                Blocks.bookshelf.getDefaultState(),
                false);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 7, 1, 4, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.oak_stairs.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.oak_stairs, 0)),
                7,
                1,
                3,
                structureBoundingBox);
        l = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
        this.setBlockState(
                world, Blocks.oak_stairs.getStateFromMeta(l), 6, 1, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_stairs.getStateFromMeta(l), 5, 1, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_stairs.getStateFromMeta(l), 4, 1, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_stairs.getStateFromMeta(l), 3, 1, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 6, 1, 3, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.wooden_pressure_plate.getDefaultState(),
                6,
                2,
                3,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 4, 1, 3, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.wooden_pressure_plate.getDefaultState(),
                4,
                2,
                3,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.crafting_table.getDefaultState(), 7, 1, 1, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBox);
        this.placeDoorCurrentPosition(
                world,
                structureBoundingBox,
                random,
                1,
                1,
                0,
                Facing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
        if (this.getBlockStateFromPos(world, 1, 0, -1, structureBoundingBox)
                                .getBlock()
                                .getMaterial()
                        == Material.AIR
                && this.getBlockStateFromPos(world, 1, -1, -1, structureBoundingBox)
                                .getBlock()
                                .getMaterial()
                        != Material.AIR) {
            this.setBlockState(
                    world,
                    Blocks.stone_stairs.getStateFromMeta(
                            this.getMetadataWithOffset(Blocks.stone_stairs, 3)),
                    1,
                    0,
                    -1,
                    structureBoundingBox);
        }

        int k1 = 0;

        label41:
        while (k1 < 6) {
            int j1 = 0;

            while (true) {
                if (j1 < 9) {
                    this.clearCurrentPositionBlocksUpwards(world, j1, 9, k1, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            j1,
                            -1,
                            k1,
                            structureBoundingBox);
                    j1++;
                    if (i == 0) {
                        continue;
                    }
                }

                k1++;
                if (i != 0) {
                    break label41;
                }
                break;
            }
        }

        this.spawnVillagers(world, structureBoundingBox, 2, 1, 2, 1);
        return true;
    }

    protected int method_eea4c703766e71e0df14(int i, int j) {
        return 1;
    }

    public StructureVillagePiecesHouse1() {}

    public static StructureVillagePiecesHouse1 createPiece(
            StructureVillagePiecesStart structureVillagePiecesStart,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, 0, 0, 0, 9, 9, 6, facing);
        return canVillageGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureVillagePiecesHouse1(
                        structureVillagePiecesStart, l, random, structureBoundingBox, facing)
                : null;
    }

    public StructureVillagePiecesHouse1(
            StructureVillagePiecesStart structureVillagePiecesStart,
            int i,
            Random random,
            StructureBoundingBox bMdValue,
            Facing aAcValue) {
        super(structureVillagePiecesStart, i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }
}
