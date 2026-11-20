package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class StructureOceanMonumentPiecesDoubleYRoom extends StructureOceanMonumentPiecesPiece {
    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (this.bLW.a / 25 > 0) {
            this.method_693bdf1dd0a98b17a651(
                    world, structureBoundingBox, 0, 0, this.bLW.aCM[Facing.DOWN.getIndex()]);
        }

        StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition = this.bLW.bMV[Facing.UP.getIndex()];
        if (structureOceanMonumentPiecesRoomDefinition.bMV[Facing.UP.getIndex()] == null) {
            this.method_0f930b19b46d08e97b6e(world, structureBoundingBox, 1, 8, 1, 6, 8, 6, bLt);
        }

        this.fillWithBlocks(
                world, structureBoundingBox, 0, 4, 0, 0, 4, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 7, 4, 0, 7, 4, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 4, 0, 6, 4, 0, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 4, 7, 6, 4, 7, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 2, 4, 1, 2, 4, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 4, 2, 1, 4, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 4, 1, 5, 4, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 6, 4, 2, 6, 4, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 2, 4, 5, 2, 4, 6, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 4, 5, 1, 4, 5, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 4, 5, 5, 4, 6, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 6, 4, 5, 6, 4, 5, aOh, aOh, false);
        StructureOceanMonumentPiecesRoomDefinition bLWValue = this.bLW;
        byte b0 = 1;

        while (b0 <= 5) {
            {
                byte b1 = 0;
                if (bLWValue.aCM[Facing.SOUTH.getIndex()]) {
                    this.fillWithBlocks(
                            world, structureBoundingBox, 2, b0, b1, 2, b0 + 2, b1, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 5, b0, b1, 5, b0 + 2, b1, aOh, aOh, false);
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            3,
                            b0 + 2,
                            b1,
                            4,
                            b0 + 2,
                            b1,
                            aOh,
                            aOh,
                            false);
                    if (!(i == 0)) {

                        this.fillWithBlocks(
                                world, structureBoundingBox, 0, b0, b1, 7, b0 + 2, b1, aOh, aOh, false);
                        this.fillWithBlocks(
                                world, structureBoundingBox, 0, b0 + 1, b1, 7, b0 + 1, b1, bLt, bLt, false);
                    }
                } else {

                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, b0, b1, 7, b0 + 2, b1, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, b0 + 1, b1, 7, b0 + 1, b1, bLt, bLt, false);
                }
            }

            {
                byte b3 = 7;
                if (bLWValue.aCM[Facing.NORTH.getIndex()]) {
                    this.fillWithBlocks(
                            world, structureBoundingBox, 2, b0, b3, 2, b0 + 2, b3, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 5, b0, b3, 5, b0 + 2, b3, aOh, aOh, false);
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            3,
                            b0 + 2,
                            b3,
                            4,
                            b0 + 2,
                            b3,
                            aOh,
                            aOh,
                            false);
                    if (!(i == 0)) {

                        this.fillWithBlocks(
                                world, structureBoundingBox, 0, b0, b3, 7, b0 + 2, b3, aOh, aOh, false);
                        this.fillWithBlocks(
                                world, structureBoundingBox, 0, b0 + 1, b3, 7, b0 + 1, b3, bLt, bLt, false);
                    }
                } else {

                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, b0, b3, 7, b0 + 2, b3, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, b0 + 1, b3, 7, b0 + 1, b3, bLt, bLt, false);
                }
            }

            {
                byte b2 = 0;
                if (bLWValue.aCM[Facing.WEST.getIndex()]) {
                    this.fillWithBlocks(
                            world, structureBoundingBox, b2, b0, 2, b2, b0 + 2, 2, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, b2, b0, 5, b2, b0 + 2, 5, aOh, aOh, false);
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            b2,
                            b0 + 2,
                            3,
                            b2,
                            b0 + 2,
                            4,
                            aOh,
                            aOh,
                            false);
                    if (!(i == 0)) {

                        this.fillWithBlocks(
                                world, structureBoundingBox, b2, b0, 0, b2, b0 + 2, 7, aOh, aOh, false);
                        this.fillWithBlocks(
                                world, structureBoundingBox, b2, b0 + 1, 0, b2, b0 + 1, 7, bLt, bLt, false);
                    }
                } else {

                    this.fillWithBlocks(
                            world, structureBoundingBox, b2, b0, 0, b2, b0 + 2, 7, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, b2, b0 + 1, 0, b2, b0 + 1, 7, bLt, bLt, false);
                }
            }

            {
                byte b4 = 7;
                if (bLWValue.aCM[Facing.EAST.getIndex()]) {
                    this.fillWithBlocks(
                            world, structureBoundingBox, b4, b0, 2, b4, b0 + 2, 2, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, b4, b0, 5, b4, b0 + 2, 5, aOh, aOh, false);
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            b4,
                            b0 + 2,
                            3,
                            b4,
                            b0 + 2,
                            4,
                            aOh,
                            aOh,
                            false);
                    if (!(i == 0)) {

                        this.fillWithBlocks(
                                world, structureBoundingBox, b4, b0, 0, b4, b0 + 2, 7, aOh, aOh, false);
                        this.fillWithBlocks(
                                world, structureBoundingBox, b4, b0 + 1, 0, b4, b0 + 1, 7, bLt, bLt, false);
                    }
                } else {

                    this.fillWithBlocks(
                            world, structureBoundingBox, b4, b0, 0, b4, b0 + 2, 7, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, b4, b0 + 1, 0, b4, b0 + 1, 7, bLt, bLt, false);
                }
            }

            bLWValue = structureOceanMonumentPiecesRoomDefinition;
            b0 += 4;
            if (i != 0) {
                break;
            }
        }

        return true;
    }

    public StructureOceanMonumentPiecesDoubleYRoom() {}

    public StructureOceanMonumentPiecesDoubleYRoom(
            Facing facing, StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition, Random random) {
        super(1, facing, structureOceanMonumentPiecesRoomDefinition, 1, 2, 1);
    }
}
