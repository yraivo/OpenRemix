package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureStrongholdPiecesPrison extends StructureStrongholdPiecesStronghold {
    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
        }

        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                8,
                4,
                10,
                true,
                random,
                StructureStrongholdPieces.access$200());
        this.placeDoor(world, random, structureBoundingBox, this.bNb, 1, 1, 0);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                10,
                3,
                3,
                10,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                1,
                4,
                3,
                1,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                3,
                4,
                3,
                3,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                7,
                4,
                3,
                7,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                9,
                4,
                3,
                9,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                4,
                4,
                3,
                6,
                Blocks.iron_bars.getDefaultState(),
                Blocks.iron_bars.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                1,
                5,
                7,
                3,
                5,
                Blocks.iron_bars.getDefaultState(),
                Blocks.iron_bars.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.iron_bars.getDefaultState(), 4, 3, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.iron_bars.getDefaultState(), 4, 3, 8, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3)),
                4,
                1,
                2,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.iron_door.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.iron_door, 3) + 8),
                4,
                2,
                2,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3)),
                4,
                1,
                8,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.iron_door.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.iron_door, 3) + 8),
                4,
                2,
                8,
                structureBoundingBox);
        return true;
    }

    public static StructureStrongholdPiecesPrison createPiece(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -1, -1, 0, 9, 5, 11, facing);
        return canStrongholdGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureStrongholdPiecesPrison(l, random, structureBoundingBox, facing)
                : null;
    }

    public StructureStrongholdPiecesPrison() {}

    public StructureStrongholdPiecesPrison(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.bNb = this.getRandomDoor(random);
        this.boundingBox = bMdValue;
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        this.getNextComponentNormal(
                (StructureStrongholdPiecesStairs2) structureComponent, list, random, 1, 1);
    }
}
