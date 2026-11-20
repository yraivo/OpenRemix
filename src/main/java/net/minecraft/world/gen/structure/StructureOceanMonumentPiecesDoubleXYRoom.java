package net.minecraft.world.gen.structure;

import net.minecraft.block.state.BlockState;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class StructureOceanMonumentPiecesDoubleXYRoom extends StructureOceanMonumentPiecesPiece {
    public StructureOceanMonumentPiecesDoubleXYRoom(
            Facing facing, StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition, Random random) {
        super(1, facing, structureOceanMonumentPiecesRoomDefinition, 2, 2, 1);
    }

    public StructureOceanMonumentPiecesDoubleXYRoom() {}

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition =
                this.bLW.bMV[Facing.EAST.getIndex()];
        StructureOceanMonumentPiecesRoomDefinition bLWValue = this.bLW;
        StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinitionValue = bLWValue.bMV[Facing.UP.getIndex()];
        int k = StructureComponent.f();
        StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinitionValue2 =
                structureOceanMonumentPiecesRoomDefinition.bMV[Facing.UP.getIndex()];
        int i = k;
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

        if (structureOceanMonumentPiecesRoomDefinitionValue.bMV[Facing.UP.getIndex()] == null) {
            this.method_0f930b19b46d08e97b6e(world, structureBoundingBox, 1, 8, 1, 7, 8, 6, bLt);
        }

        if (structureOceanMonumentPiecesRoomDefinitionValue2.bMV[Facing.UP.getIndex()] == null) {
            this.method_0f930b19b46d08e97b6e(world, structureBoundingBox, 8, 8, 1, 14, 8, 6, bLt);
        }

        int j = 1;

        while (j <= 7) {
            BlockState aOhValue = aOh;
            if (j == 2 || j == 6) {
                aOhValue = bLt;
            }

            this.fillWithBlocks(
                    world, structureBoundingBox, 0, j, 0, 0, j, 7, aOhValue, aOhValue, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 15, j, 0, 15, j, 7, aOhValue, aOhValue, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 1, j, 0, 15, j, 0, aOhValue, aOhValue, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 1, j, 7, 14, j, 7, aOhValue, aOhValue, false);
            j++;
            if (i != 0) {
                break;
            }
        }

        this.fillWithBlocks(
                world, structureBoundingBox, 2, 1, 3, 2, 7, 4, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 3, 1, 2, 4, 7, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 3, 1, 5, 4, 7, 5, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 13, 1, 3, 13, 7, 4, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 11, 1, 2, 12, 7, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 11, 1, 5, 12, 7, 5, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 1, 3, 5, 3, 4, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 10, 1, 3, 10, 3, 4, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 7, 2, 10, 7, 5, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 5, 2, 5, 7, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 10, 5, 2, 10, 7, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 5, 5, 5, 7, 5, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 10, 5, 5, 10, 7, 5, aOh, aOh, false);
        this.setBlockState(world, aOh, 6, 6, 2, structureBoundingBox);
        this.setBlockState(world, aOh, 9, 6, 2, structureBoundingBox);
        this.setBlockState(world, aOh, 6, 6, 5, structureBoundingBox);
        this.setBlockState(world, aOh, 9, 6, 5, structureBoundingBox);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 4, 3, 6, 4, 4, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 9, 4, 3, 10, 4, 4, aOh, aOh, false);
        this.setBlockState(world, bLV, 5, 4, 2, structureBoundingBox);
        this.setBlockState(world, bLV, 5, 4, 5, structureBoundingBox);
        this.setBlockState(world, bLV, 10, 4, 2, structureBoundingBox);
        this.setBlockState(world, bLV, 10, 4, 5, structureBoundingBox);
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

        if (structureOceanMonumentPiecesRoomDefinitionValue.aCM[Facing.SOUTH.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 3, 5, 0, 4, 6, 0, bBJ, bBJ, false);
        }

        if (structureOceanMonumentPiecesRoomDefinitionValue.aCM[Facing.NORTH.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 3, 5, 7, 4, 6, 7, bBJ, bBJ, false);
        }

        if (structureOceanMonumentPiecesRoomDefinitionValue.aCM[Facing.WEST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 0, 5, 3, 0, 6, 4, bBJ, bBJ, false);
        }

        if (structureOceanMonumentPiecesRoomDefinitionValue2.aCM[Facing.SOUTH.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 11, 5, 0, 12, 6, 0, bBJ, bBJ, false);
        }

        if (structureOceanMonumentPiecesRoomDefinitionValue2.aCM[Facing.NORTH.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 11, 5, 7, 12, 6, 7, bBJ, bBJ, false);
        }

        if (structureOceanMonumentPiecesRoomDefinitionValue2.aCM[Facing.EAST.getIndex()]) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 15, 5, 3, 15, 6, 4, bBJ, bBJ, false);
        }

        return true;
    }
}
