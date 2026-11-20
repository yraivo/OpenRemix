package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class StructureOceanMonumentPiecesSimpleRoom extends StructureOceanMonumentPiecesPiece {
    private int rz;

    public StructureOceanMonumentPiecesSimpleRoom(
            Facing facing, StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition, Random random) {
        super(1, facing, structureOceanMonumentPiecesRoomDefinition, 1, 1, 1);
        this.rz = random.nextInt(3);
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.cR();
        if (this.bLW.a / 25 > 0) {
            this.method_693bdf1dd0a98b17a651(
                    world, structureBoundingBox, 0, 0, this.bLW.aCM[Facing.DOWN.getIndex()]);
        }

        if (this.bLW.bMV[Facing.UP.getIndex()] == null) {
            this.method_0f930b19b46d08e97b6e(world, structureBoundingBox, 1, 4, 1, 6, 4, 6, bLt);
        }

        int j;
        label107:
        {
            j =
                    this.rz != 0
                                    && random.nextBoolean()
                                    && !this.bLW.aCM[Facing.DOWN.getIndex()]
                                    && !this.bLW.aCM[Facing.UP.getIndex()]
                                    && this.bLW.method_beea3e16fe3ec0995eb9() > 1
                            ? 1
                            : 0;
            if (this.rz == 0) {
                label90:
                {
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, 1, 0, 2, 1, 2, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, 3, 0, 2, 3, 2, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, 2, 0, 0, 2, 2, bLt, bLt, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 1, 2, 0, 2, 2, 0, bLt, bLt, false);
                    this.setBlockState(world, bLV, 1, 2, 1, structureBoundingBox);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 5, 1, 0, 7, 1, 2, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 5, 3, 0, 7, 3, 2, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 7, 2, 0, 7, 2, 2, bLt, bLt, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 5, 2, 0, 6, 2, 0, bLt, bLt, false);
                    this.setBlockState(world, bLV, 6, 2, 1, structureBoundingBox);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, 1, 5, 2, 1, 7, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, 3, 5, 2, 3, 7, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, 2, 5, 0, 2, 7, bLt, bLt, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 1, 2, 7, 2, 2, 7, bLt, bLt, false);
                    this.setBlockState(world, bLV, 1, 2, 6, structureBoundingBox);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 5, 1, 5, 7, 1, 7, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 5, 3, 5, 7, 3, 7, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 7, 2, 5, 7, 2, 7, bLt, bLt, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 5, 2, 7, 6, 2, 7, bLt, bLt, false);
                    this.setBlockState(world, bLV, 6, 2, 6, structureBoundingBox);
                    if (this.bLW.aCM[Facing.SOUTH.getIndex()]) {
                        this.fillWithBlocks(
                                world, structureBoundingBox, 3, 3, 0, 4, 3, 0, aOh, aOh, false);
                        if (i != 0) {
                            break label90;
                        }
                    }

                    this.fillWithBlocks(
                            world, structureBoundingBox, 3, 3, 0, 4, 3, 1, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 3, 2, 0, 4, 2, 0, bLt, bLt, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 3, 1, 0, 4, 1, 1, aOh, aOh, false);
                }

                label85:
                {
                    if (this.bLW.aCM[Facing.NORTH.getIndex()]) {
                        this.fillWithBlocks(
                                world, structureBoundingBox, 3, 3, 7, 4, 3, 7, aOh, aOh, false);
                        if (i != 0) {
                            break label85;
                        }
                    }

                    this.fillWithBlocks(
                            world, structureBoundingBox, 3, 3, 6, 4, 3, 7, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 3, 2, 7, 4, 2, 7, bLt, bLt, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 3, 1, 6, 4, 1, 7, aOh, aOh, false);
                }

                label80:
                {
                    if (this.bLW.aCM[Facing.WEST.getIndex()]) {
                        this.fillWithBlocks(
                                world, structureBoundingBox, 0, 3, 3, 0, 3, 4, aOh, aOh, false);
                        if (i != 0) {
                            break label80;
                        }
                    }

                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, 3, 3, 1, 3, 4, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, 2, 3, 0, 2, 4, bLt, bLt, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, 1, 3, 1, 1, 4, aOh, aOh, false);
                }

                if (this.bLW.aCM[Facing.EAST.getIndex()]) {
                    this.fillWithBlocks(
                            world, structureBoundingBox, 7, 3, 3, 7, 3, 4, aOh, aOh, false);
                    if (i != 0) {
                        break label107;
                    }
                }

                this.fillWithBlocks(
                        world, structureBoundingBox, 6, 3, 3, 7, 3, 4, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 7, 2, 3, 7, 2, 4, bLt, bLt, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 6, 1, 3, 7, 1, 4, aOh, aOh, false);
                if (i != 0) {
                    break label107;
                }
            }

            if (this.rz == 1) {
                this.fillWithBlocks(
                        world, structureBoundingBox, 2, 1, 2, 2, 3, 2, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 2, 1, 5, 2, 3, 5, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 5, 1, 5, 5, 3, 5, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 5, 1, 2, 5, 3, 2, aOh, aOh, false);
                this.setBlockState(world, bLV, 2, 2, 2, structureBoundingBox);
                this.setBlockState(world, bLV, 2, 2, 5, structureBoundingBox);
                this.setBlockState(world, bLV, 5, 2, 5, structureBoundingBox);
                this.setBlockState(world, bLV, 5, 2, 2, structureBoundingBox);
                this.fillWithBlocks(
                        world, structureBoundingBox, 0, 1, 0, 1, 3, 0, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 0, 1, 1, 0, 3, 1, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 0, 1, 7, 1, 3, 7, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 0, 1, 6, 0, 3, 6, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 6, 1, 7, 7, 3, 7, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 7, 1, 6, 7, 3, 6, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 6, 1, 0, 7, 3, 0, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 7, 1, 1, 7, 3, 1, aOh, aOh, false);
                this.setBlockState(world, bLt, 1, 2, 0, structureBoundingBox);
                this.setBlockState(world, bLt, 0, 2, 1, structureBoundingBox);
                this.setBlockState(world, bLt, 1, 2, 7, structureBoundingBox);
                this.setBlockState(world, bLt, 0, 2, 6, structureBoundingBox);
                this.setBlockState(world, bLt, 6, 2, 7, structureBoundingBox);
                this.setBlockState(world, bLt, 7, 2, 6, structureBoundingBox);
                this.setBlockState(world, bLt, 6, 2, 0, structureBoundingBox);
                this.setBlockState(world, bLt, 7, 2, 1, structureBoundingBox);
                if (!this.bLW.aCM[Facing.SOUTH.getIndex()]) {
                    this.fillWithBlocks(
                            world, structureBoundingBox, 1, 3, 0, 6, 3, 0, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 1, 2, 0, 6, 2, 0, bLt, bLt, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 1, 1, 0, 6, 1, 0, aOh, aOh, false);
                }

                if (!this.bLW.aCM[Facing.NORTH.getIndex()]) {
                    this.fillWithBlocks(
                            world, structureBoundingBox, 1, 3, 7, 6, 3, 7, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 1, 2, 7, 6, 2, 7, bLt, bLt, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 1, 1, 7, 6, 1, 7, aOh, aOh, false);
                }

                if (!this.bLW.aCM[Facing.WEST.getIndex()]) {
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, 3, 1, 0, 3, 6, aOh, aOh, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, 2, 1, 0, 2, 6, bLt, bLt, false);
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, 1, 1, 0, 1, 6, aOh, aOh, false);
                }

                if (this.bLW.aCM[Facing.EAST.getIndex()]) {
                    break label107;
                }

                this.fillWithBlocks(
                        world, structureBoundingBox, 7, 3, 1, 7, 3, 6, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 7, 2, 1, 7, 2, 6, bLt, bLt, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 7, 1, 1, 7, 1, 6, aOh, aOh, false);
                if (i != 0) {
                    break label107;
                }
            }

            if (this.rz == 2) {
                this.fillWithBlocks(
                        world, structureBoundingBox, 0, 1, 0, 0, 1, 7, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 7, 1, 0, 7, 1, 7, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 1, 1, 0, 6, 1, 0, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 1, 1, 7, 6, 1, 7, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 0, 2, 0, 0, 2, 7, bLX, bLX, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 7, 2, 0, 7, 2, 7, bLX, bLX, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 1, 2, 0, 6, 2, 0, bLX, bLX, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 1, 2, 7, 6, 2, 7, bLX, bLX, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 0, 3, 0, 0, 3, 7, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 7, 3, 0, 7, 3, 7, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 1, 3, 0, 6, 3, 0, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 1, 3, 7, 6, 3, 7, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 0, 1, 3, 0, 2, 4, bLX, bLX, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 7, 1, 3, 7, 2, 4, bLX, bLX, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 3, 1, 0, 4, 2, 0, bLX, bLX, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 3, 1, 7, 4, 2, 7, bLX, bLX, false);
                if (this.bLW.aCM[Facing.SOUTH.getIndex()]) {
                    this.fillWithBlocks(
                            world, structureBoundingBox, 3, 1, 0, 4, 2, 0, bBJ, bBJ, false);
                }

                if (this.bLW.aCM[Facing.NORTH.getIndex()]) {
                    this.fillWithBlocks(
                            world, structureBoundingBox, 3, 1, 7, 4, 2, 7, bBJ, bBJ, false);
                }

                if (this.bLW.aCM[Facing.WEST.getIndex()]) {
                    this.fillWithBlocks(
                            world, structureBoundingBox, 0, 1, 3, 0, 2, 4, bBJ, bBJ, false);
                }

                if (this.bLW.aCM[Facing.EAST.getIndex()]) {
                    this.fillWithBlocks(
                            world, structureBoundingBox, 7, 1, 3, 7, 2, 4, bBJ, bBJ, false);
                }
            }
        }

        if (j != 0) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 3, 1, 3, 4, 1, 4, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 3, 2, 3, 4, 2, 4, bLt, bLt, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 3, 3, 3, 4, 3, 4, aOh, aOh, false);
        }

        return true;
    }

    public StructureOceanMonumentPiecesSimpleRoom() {}
}
