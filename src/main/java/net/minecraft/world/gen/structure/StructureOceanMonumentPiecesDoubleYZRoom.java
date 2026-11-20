package net.minecraft.world.gen.structure;

import net.minecraft.block.state.BlockState;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class StructureOceanMonumentPiecesDoubleYZRoom extends StructureOceanMonumentPiecesPiece {
    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition =
                this.bLW.bMV[Facing.NORTH.getIndex()];
        int k = StructureComponent.f();
        StructureOceanMonumentPiecesRoomDefinition bLWValue = this.bLW;
        StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinitionValue =
                structureOceanMonumentPiecesRoomDefinition.bMV[Facing.UP.getIndex()];
        int i = k;
        StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinitionValue2 = bLWValue.bMV[Facing.UP.getIndex()];
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

        if (structureOceanMonumentPiecesRoomDefinitionValue2.bMV[Facing.UP.getIndex()] == null) {
            this.method_0f930b19b46d08e97b6e(world, structureBoundingBox, 1, 8, 1, 6, 8, 7, bLt);
        }

        if (structureOceanMonumentPiecesRoomDefinitionValue.bMV[Facing.UP.getIndex()] == null) {
            this.method_0f930b19b46d08e97b6e(world, structureBoundingBox, 1, 8, 8, 6, 8, 14, bLt);
        }

        int j = 1;

        while (j <= 7) {
            BlockState aOhValue = aOh;
            if (j == 2 || j == 6) {
                aOhValue = bLt;
            }

            this.fillWithBlocks(
                    world, structureBoundingBox, 0, j, 0, 0, j, 15, aOhValue, aOhValue, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 7, j, 0, 7, j, 15, aOhValue, aOhValue, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 1, j, 0, 6, j, 0, aOhValue, aOhValue, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 1, j, 15, 6, j, 15, aOhValue, aOhValue, false);
            j++;
            if (i != 0) {
                break;
            }
        }

        j = 1;

        while (j <= 7) {
            BlockState bLXValue = bLX;
            if (j == 2 || j == 6) {
                bLXValue = bLV;
            }

            this.fillWithBlocks(
                    world, structureBoundingBox, 3, j, 7, 4, j, 8, bLXValue, bLXValue, false);
            j++;
            if (i != 0) {
                break;
            }
        }

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

        if (structureOceanMonumentPiecesRoomDefinitionValue2.aCM[Facing.SOUTH.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 3, 5, 0, 4, 6, 0, bBJ, bBJ, false);
        }

        if (structureOceanMonumentPiecesRoomDefinitionValue2.aCM[Facing.EAST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 7, 5, 3, 7, 6, 4, bBJ, bBJ, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 5, 4, 2, 6, 4, 5, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 6, 1, 2, 6, 3, 2, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 6, 1, 5, 6, 3, 5, aOh, aOh, false);
        }

        if (structureOceanMonumentPiecesRoomDefinitionValue2.aCM[Facing.WEST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 0, 5, 3, 0, 6, 4, bBJ, bBJ, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 1, 4, 2, 2, 4, 5, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 1, 1, 2, 1, 3, 2, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 1, 1, 5, 1, 3, 5, aOh, aOh, false);
        }

        if (structureOceanMonumentPiecesRoomDefinitionValue.aCM[Facing.NORTH.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 3, 5, 15, 4, 6, 15, bBJ, bBJ, false);
        }

        if (structureOceanMonumentPiecesRoomDefinitionValue.aCM[Facing.WEST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 0, 5, 11, 0, 6, 12, bBJ, bBJ, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 1, 4, 10, 2, 4, 13, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 1, 1, 10, 1, 3, 10, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 1, 1, 13, 1, 3, 13, aOh, aOh, false);
        }

        if (structureOceanMonumentPiecesRoomDefinitionValue.aCM[Facing.EAST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 7, 5, 11, 7, 6, 12, bBJ, bBJ, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 5, 4, 10, 6, 4, 13, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 6, 1, 10, 6, 3, 10, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 6, 1, 13, 6, 3, 13, aOh, aOh, false);
        }

        return true;
    }

    public StructureOceanMonumentPiecesDoubleYZRoom(
            Facing facing, StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition, Random random) {
        super(1, facing, structureOceanMonumentPiecesRoomDefinition, 1, 2, 2);
    }

    public StructureOceanMonumentPiecesDoubleYZRoom() {}
}
