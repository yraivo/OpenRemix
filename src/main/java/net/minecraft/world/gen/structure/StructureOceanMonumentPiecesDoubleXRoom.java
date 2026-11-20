package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class StructureOceanMonumentPiecesDoubleXRoom extends StructureOceanMonumentPiecesPiece {
    public StructureOceanMonumentPiecesDoubleXRoom(
            Facing facing, StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition, Random random) {
        super(1, facing, structureOceanMonumentPiecesRoomDefinition, 2, 1, 1);
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition =
                this.bLW.bMV[Facing.EAST.getIndex()];
        StructureOceanMonumentPiecesRoomDefinition bLWValue = this.bLW;
        if (this.bLW.a / 25 > 0) {
            this.method_693bdf1dd0a98b17a651(
                    world,
                    structureBoundingBox,
                    8,
                    0,
                    structureOceanMonumentPiecesRoomDefinition.aCM[Facing.DOWN.getIndex()]);
            this.method_693bdf1dd0a98b17a651(
                    world, structureBoundingBox, 0, 0, bLWValue.aCM[Facing.DOWN.getIndex()]);
        }

        if (bLWValue.bMV[Facing.UP.getIndex()] == null) {
            this.method_0f930b19b46d08e97b6e(world, structureBoundingBox, 1, 4, 1, 7, 4, 6, bLt);
        }

        if (structureOceanMonumentPiecesRoomDefinition.bMV[Facing.UP.getIndex()] == null) {
            this.method_0f930b19b46d08e97b6e(world, structureBoundingBox, 8, 4, 1, 14, 4, 6, bLt);
        }

        this.fillWithBlocks(
                world, structureBoundingBox, 0, 3, 0, 0, 3, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 15, 3, 0, 15, 3, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 3, 0, 15, 3, 0, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 3, 7, 14, 3, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 0, 2, 0, 0, 2, 7, bLt, bLt, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 15, 2, 0, 15, 2, 7, bLt, bLt, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 2, 0, 15, 2, 0, bLt, bLt, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 2, 7, 14, 2, 7, bLt, bLt, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 0, 1, 0, 0, 1, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 15, 1, 0, 15, 1, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 1, 0, 15, 1, 0, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 1, 7, 14, 1, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 1, 0, 10, 1, 4, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 6, 2, 0, 9, 2, 3, bLt, bLt, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 3, 0, 10, 3, 4, aOh, aOh, false);
        this.setBlockState(world, bLV, 6, 2, 3, structureBoundingBox);
        this.setBlockState(world, bLV, 9, 2, 3, structureBoundingBox);
        if (bLWValue.aCM[Facing.SOUTH.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 3, 1, 0, 4, 2, 0, bBJ, bBJ, false);
        }

        if (bLWValue.aCM[Facing.NORTH.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 3, 1, 7, 4, 2, 7, bBJ, bBJ, false);
        }

        if (bLWValue.aCM[Facing.WEST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 0, 1, 3, 0, 2, 4, bBJ, bBJ, false);
        }

        if (structureOceanMonumentPiecesRoomDefinition.aCM[Facing.SOUTH.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 11, 1, 0, 12, 2, 0, bBJ, bBJ, false);
        }

        if (structureOceanMonumentPiecesRoomDefinition.aCM[Facing.NORTH.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 11, 1, 7, 12, 2, 7, bBJ, bBJ, false);
        }

        if (structureOceanMonumentPiecesRoomDefinition.aCM[Facing.EAST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 15, 1, 3, 15, 2, 4, bBJ, bBJ, false);
        }

        return true;
    }

    public StructureOceanMonumentPiecesDoubleXRoom() {}
}
