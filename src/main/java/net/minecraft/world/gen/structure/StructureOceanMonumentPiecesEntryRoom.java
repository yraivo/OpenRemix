package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class StructureOceanMonumentPiecesEntryRoom extends StructureOceanMonumentPiecesPiece {
    public StructureOceanMonumentPiecesEntryRoom() {}

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        this.fillWithBlocks(
                world, structureBoundingBox, 0, 3, 0, 2, 3, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 3, 0, 7, 3, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 0, 2, 0, 1, 2, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 6, 2, 0, 7, 2, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 0, 1, 0, 0, 1, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 7, 1, 0, 7, 1, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 0, 1, 7, 7, 3, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 1, 0, 2, 3, 0, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 1, 0, 6, 3, 0, aOh, aOh, false);
        if (this.bLW.aCM[Facing.NORTH.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 3, 1, 7, 4, 2, 7, bBJ, bBJ, false);
        }

        if (this.bLW.aCM[Facing.WEST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 0, 1, 3, 1, 2, 4, bBJ, bBJ, false);
        }

        if (this.bLW.aCM[Facing.EAST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 6, 1, 3, 7, 2, 4, bBJ, bBJ, false);
        }

        return true;
    }

    public StructureOceanMonumentPiecesEntryRoom(
            Facing facing, StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition) {
        super(1, facing, structureOceanMonumentPiecesRoomDefinition, 1, 1, 1);
    }
}
