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

public class StructureVillagePiecesHall extends StructureVillagePiecesVillage {
    public StructureVillagePiecesHall() {}

    public static StructureVillagePiecesHall createPiece(
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
                        i, j, k, 0, 0, 0, 9, 7, 11, facing);
        return canVillageGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureVillagePiecesHall(
                        structureVillagePiecesStart, l, random, structureBoundingBox, facing)
                : null;
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.cR();
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
                6,
                8,
                0,
                10,
                Blocks.dirt.getDefaultState(),
                Blocks.dirt.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 6, 0, 6, structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                1,
                6,
                2,
                1,
                10,
                Blocks.oak_fence.getDefaultState(),
                Blocks.oak_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                1,
                6,
                8,
                1,
                10,
                Blocks.oak_fence.getDefaultState(),
                Blocks.oak_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                1,
                10,
                7,
                1,
                10,
                Blocks.oak_fence.getDefaultState(),
                Blocks.oak_fence.getDefaultState(),
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
                5,
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
                1,
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
                7,
                1,
                5,
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
                7,
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
                8,
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
                            4 + l,
                            l,
                            structureBoundingBox);
                    this.setBlockState(
                            world,
                            Blocks.oak_stairs.getStateFromMeta(k),
                            i1,
                            4 + l,
                            5 - l,
                            structureBoundingBox);
                    i1++;
                    if (i != 0) {
                        continue;
                    }
                }

                l++;
                if (i == 0) {
                    break label54;
                }
                break;
            }
        }

        this.setBlockState(world, Blocks.log.getDefaultState(), 0, 2, 1, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 0, 2, 4, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 8, 2, 1, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 8, 2, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 2, 2, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 3, 2, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 6, 2, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 2, 1, 3, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.wooden_pressure_plate.getDefaultState(),
                2,
                2,
                3,
                structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 1, 4, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.oak_stairs.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.oak_stairs, 3)),
                2,
                1,
                4,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.oak_stairs.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.oak_stairs, 1)),
                1,
                1,
                3,
                structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                0,
                1,
                7,
                0,
                3,
                Blocks.double_stone_slab.getDefaultState(),
                Blocks.double_stone_slab.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.double_stone_slab.getDefaultState(), 6, 1, 1, structureBoundingBox);
        this.setBlockState(
                world, Blocks.double_stone_slab.getDefaultState(), 6, 1, 2, structureBoundingBox);
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

        this.setBlockState(world, Blocks.air.getDefaultState(), 6, 1, 5, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 6, 2, 5, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.torch
                        .getDefaultState()
                        .withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.getOpposite()),
                6,
                3,
                4,
                structureBoundingBox);
        this.placeDoorCurrentPosition(
                world,
                structureBoundingBox,
                random,
                6,
                1,
                5,
                Facing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
        l = 0;

        label41:
        while (l < 5) {
            int j1 = 0;

            while (true) {
                if (j1 < 9) {
                    this.clearCurrentPositionBlocksUpwards(world, j1, 7, l, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            j1,
                            -1,
                            l,
                            structureBoundingBox);
                    j1++;
                    if (i != 0) {
                        continue;
                    }
                }

                l++;
                if (i == 0) {
                    break label41;
                }
                break;
            }
        }

        this.spawnVillagers(world, structureBoundingBox, 4, 1, 2, 2);
        return true;
    }

    public StructureVillagePiecesHall(
            StructureVillagePiecesStart structureVillagePiecesStart,
            int i,
            Random random,
            StructureBoundingBox bMdValue,
            Facing aAcValue) {
        super(structureVillagePiecesStart, i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }

    protected int method_eea4c703766e71e0df14(int i, int j) {
        return i == 0 ? 4 : super.method_eea4c703766e71e0df14(i, j);
    }
}
