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

public class StructureVillagePiecesChurch extends StructureVillagePiecesVillage {
    public StructureVillagePiecesChurch() {}

    public static StructureVillagePiecesChurch createPiece(
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
                        i, j, k, 0, 0, 0, 5, 12, 9, facing);
        return canVillageGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureVillagePiecesChurch(
                        structureVillagePiecesStart, l, random, structureBoundingBox, facing)
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

            this.boundingBox.offset(0, this.ef - this.boundingBox.maxY + 12 - 1, 0);
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                1,
                3,
                3,
                7,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                5,
                1,
                3,
                9,
                3,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                0,
                0,
                3,
                0,
                8,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                0,
                3,
                10,
                0,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                1,
                0,
                10,
                3,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                1,
                4,
                10,
                3,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                4,
                0,
                4,
                7,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                0,
                4,
                4,
                4,
                7,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                8,
                3,
                4,
                8,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                5,
                4,
                3,
                10,
                4,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                5,
                5,
                3,
                5,
                7,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                9,
                0,
                4,
                9,
                4,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                4,
                0,
                4,
                4,
                4,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 0, 11, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 4, 11, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 2, 11, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 2, 11, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 1, 1, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 1, 1, 7, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 2, 1, 7, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 3, 1, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 3, 1, 7, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_stairs.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.stone_stairs, 3)),
                1,
                1,
                5,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_stairs.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.stone_stairs, 3)),
                2,
                1,
                6,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_stairs.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.stone_stairs, 3)),
                3,
                1,
                5,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_stairs.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.stone_stairs, 1)),
                1,
                2,
                7,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_stairs.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.stone_stairs, 0)),
                3,
                2,
                7,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 3, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 4, 3, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 6, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 7, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 4, 6, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 4, 7, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 2, 6, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 2, 7, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 2, 6, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 2, 7, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 3, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 4, 3, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 2, 3, 8, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.torch
                        .getDefaultState()
                        .withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.getOpposite()),
                2,
                4,
                7,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.torch
                        .getDefaultState()
                        .withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.rotateY()),
                1,
                4,
                6,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.torch
                        .getDefaultState()
                        .withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.rotateYCCW()),
                3,
                4,
                6,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.torch
                        .getDefaultState()
                        .withProperty(BlockTorch.FACING_PROP, this.coordBaseMode),
                2,
                4,
                5,
                structureBoundingBox);
        int j = this.getMetadataWithOffset(Blocks.ladder, 4);
        int k = 1;

        while (k <= 9) {
            this.setBlockState(
                    world, Blocks.ladder.getStateFromMeta(j), 3, k, 3, structureBoundingBox);
            k++;
            if (i != 0) {
                break;
            }
        }

        this.setBlockState(world, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBox);
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

        k = 0;

        label38:
        while (k < 9) {
            int l = 0;

            while (true) {
                if (l < 5) {
                    this.clearCurrentPositionBlocksUpwards(world, l, 12, k, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            l,
                            -1,
                            k,
                            structureBoundingBox);
                    l++;
                    if (i == 0) {
                        continue;
                    }
                }

                k++;
                if (i != 0) {
                    break label38;
                }
                break;
            }
        }

        this.spawnVillagers(world, structureBoundingBox, 2, 1, 2, 1);
        return true;
    }

    public StructureVillagePiecesChurch(
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
        return 2;
    }
}
