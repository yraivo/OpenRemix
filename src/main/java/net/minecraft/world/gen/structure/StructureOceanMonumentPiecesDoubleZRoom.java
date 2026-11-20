package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class StructureOceanMonumentPiecesDoubleZRoom extends StructureOceanMonumentPiecesPiece {
    public StructureOceanMonumentPiecesDoubleZRoom() {}

    public StructureOceanMonumentPiecesDoubleZRoom(
            Facing facing, StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition, Random random) {
        super(1, facing, structureOceanMonumentPiecesRoomDefinition, 1, 1, 2);
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition =
                this.bLW.bMV[Facing.NORTH.getIndex()];
        StructureOceanMonumentPiecesRoomDefinition bLWValue = this.bLW;
        if (this.bLW.a / 25 > 0) {
            this.method_693bdf1dd0a98b17a651(
                    world,
                    structureBoundingBox,
                    0,
                    8,
                    structureOceanMonumentPiecesRoomDefinition.aCM[Facing.DOWN.getIndex()]);
            this.method_693bdf1dd0a98b17a651(
                    world, structureBoundingBox, 0, 0, bLWValue.aCM[Facing.DOWN.getIndex()]);
        }

        if (bLWValue.bMV[Facing.UP.getIndex()] == null) {
            this.method_0f930b19b46d08e97b6e(world, structureBoundingBox, 1, 4, 1, 6, 4, 7, bLt);
        }

        if (structureOceanMonumentPiecesRoomDefinition.bMV[Facing.UP.getIndex()] == null) {
            this.method_0f930b19b46d08e97b6e(world, structureBoundingBox, 1, 4, 8, 6, 4, 14, bLt);
        }

        this.fillWithBlocks(
                world, structureBoundingBox, 0, 3, 0, 0, 3, 15, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 7, 3, 0, 7, 3, 15, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 3, 0, 7, 3, 0, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 3, 15, 6, 3, 15, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 0, 2, 0, 0, 2, 15, bLt, bLt, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 7, 2, 0, 7, 2, 15, bLt, bLt, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 2, 0, 7, 2, 0, bLt, bLt, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 2, 15, 6, 2, 15, bLt, bLt, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 0, 1, 0, 0, 1, 15, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 7, 1, 0, 7, 1, 15, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 1, 0, 7, 1, 0, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 1, 15, 6, 1, 15, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 1, 1, 1, 1, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 6, 1, 1, 6, 1, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 3, 1, 1, 3, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 6, 3, 1, 6, 3, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 1, 13, 1, 1, 14, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 6, 1, 13, 6, 1, 14, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 3, 13, 1, 3, 14, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 6, 3, 13, 6, 3, 14, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 2, 1, 6, 2, 3, 6, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 1, 6, 5, 3, 6, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 2, 1, 9, 2, 3, 9, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 1, 9, 5, 3, 9, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 3, 2, 6, 4, 2, 6, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 3, 2, 9, 4, 2, 9, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 2, 2, 7, 2, 2, 8, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 2, 7, 5, 2, 8, aOh, aOh, false);
        this.setBlockState(world, bLV, 2, 2, 5, structureBoundingBox);
        this.setBlockState(world, bLV, 5, 2, 5, structureBoundingBox);
        this.setBlockState(world, bLV, 2, 2, 10, structureBoundingBox);
        this.setBlockState(world, bLV, 5, 2, 10, structureBoundingBox);
        this.setBlockState(world, aOh, 2, 3, 5, structureBoundingBox);
        this.setBlockState(world, aOh, 5, 3, 5, structureBoundingBox);
        this.setBlockState(world, aOh, 2, 3, 10, structureBoundingBox);
        this.setBlockState(world, aOh, 5, 3, 10, structureBoundingBox);
        if (bLWValue.aCM[Facing.SOUTH.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 3, 1, 0, 4, 2, 0, bBJ, bBJ, false);
        }

        if (bLWValue.aCM[Facing.EAST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 7, 1, 3, 7, 2, 4, bBJ, bBJ, false);
        }

        if (bLWValue.aCM[Facing.WEST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 0, 1, 3, 0, 2, 4, bBJ, bBJ, false);
        }

        if (structureOceanMonumentPiecesRoomDefinition.aCM[Facing.NORTH.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 3, 1, 15, 4, 2, 15, bBJ, bBJ, false);
        }

        if (structureOceanMonumentPiecesRoomDefinition.aCM[Facing.WEST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 0, 1, 11, 0, 2, 12, bBJ, bBJ, false);
        }

        if (structureOceanMonumentPiecesRoomDefinition.aCM[Facing.EAST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 7, 1, 11, 7, 2, 12, bBJ, bBJ, false);
        }

        return true;
    }
}
