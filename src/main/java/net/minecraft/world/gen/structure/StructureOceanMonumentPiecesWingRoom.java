package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class StructureOceanMonumentPiecesWingRoom extends StructureOceanMonumentPiecesPiece {
    private int rz;

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.cR();
        if (this.rz == 0) {
            int j = 0;

            while (j < 4) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        10 - j,
                        3 - j,
                        20 - j,
                        12 + j,
                        3 - j,
                        20,
                        aOh,
                        aOh,
                        false);
                j++;
                if (i == 0) {
                    break;
                }
            }

            this.fillWithBlocks(
                    world, structureBoundingBox, 7, 0, 6, 15, 0, 16, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 6, 0, 6, 6, 3, 20, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 16, 0, 6, 16, 3, 20, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 7, 1, 7, 7, 1, 20, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 15, 1, 7, 15, 1, 20, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 7, 1, 6, 9, 3, 6, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 13, 1, 6, 15, 3, 6, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 8, 1, 7, 9, 1, 7, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 13, 1, 7, 14, 1, 7, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 9, 0, 5, 13, 0, 5, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 10, 0, 7, 12, 0, 7, bLX, bLX, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 8, 0, 10, 8, 0, 12, bLX, bLX, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 14, 0, 10, 14, 0, 12, bLX, bLX, false);
            byte b2 = 18;

            while (b2 >= 7) {
                this.setBlockState(world, bLV, 6, 3, b2, structureBoundingBox);
                this.setBlockState(world, bLV, 16, 3, b2, structureBoundingBox);
                b2 -= 3;
                if (i == 0) {
                    break;
                }
            }

            this.setBlockState(world, bLV, 10, 0, 10, structureBoundingBox);
            this.setBlockState(world, bLV, 12, 0, 10, structureBoundingBox);
            this.setBlockState(world, bLV, 10, 0, 12, structureBoundingBox);
            this.setBlockState(world, bLV, 12, 0, 12, structureBoundingBox);
            this.setBlockState(world, bLV, 8, 3, 6, structureBoundingBox);
            this.setBlockState(world, bLV, 14, 3, 6, structureBoundingBox);
            this.setBlockState(world, aOh, 4, 2, 4, structureBoundingBox);
            this.setBlockState(world, bLV, 4, 1, 4, structureBoundingBox);
            this.setBlockState(world, aOh, 4, 0, 4, structureBoundingBox);
            this.setBlockState(world, aOh, 18, 2, 4, structureBoundingBox);
            this.setBlockState(world, bLV, 18, 1, 4, structureBoundingBox);
            this.setBlockState(world, aOh, 18, 0, 4, structureBoundingBox);
            this.setBlockState(world, aOh, 4, 2, 18, structureBoundingBox);
            this.setBlockState(world, bLV, 4, 1, 18, structureBoundingBox);
            this.setBlockState(world, aOh, 4, 0, 18, structureBoundingBox);
            this.setBlockState(world, aOh, 18, 2, 18, structureBoundingBox);
            this.setBlockState(world, bLV, 18, 1, 18, structureBoundingBox);
            this.setBlockState(world, aOh, 18, 0, 18, structureBoundingBox);
            this.setBlockState(world, aOh, 9, 7, 20, structureBoundingBox);
            this.setBlockState(world, aOh, 13, 7, 20, structureBoundingBox);
            this.fillWithBlocks(
                    world, structureBoundingBox, 6, 0, 21, 7, 4, 21, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 15, 0, 21, 16, 4, 21, aOh, aOh, false);
            this.method_073023fc34d438b4ac6c(world, structureBoundingBox, 11, 2, 16);
            if (i != 0) {
                return true;
            }
        }

        if (this.rz == 1) {
            this.fillWithBlocks(
                    world, structureBoundingBox, 9, 3, 18, 13, 3, 20, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 9, 0, 18, 9, 2, 18, aOh, aOh, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 13, 0, 18, 13, 2, 18, aOh, aOh, false);
            byte b3 = 9;
            byte b0 = 20;
            byte b1 = 5;
            int k = 0;

            while (k < 2) {
                this.setBlockState(world, aOh, b3, b1 + 1, b0, structureBoundingBox);
                this.setBlockState(world, bLV, b3, b1, b0, structureBoundingBox);
                this.setBlockState(world, aOh, b3, b1 - 1, b0, structureBoundingBox);
                b3 = 13;
                k++;
                if (i == 0) {
                    break;
                }
            }

            this.fillWithBlocks(
                    world, structureBoundingBox, 7, 3, 7, 15, 3, 14, aOh, aOh, false);
            b3 = 10;
            k = 0;

            while (k < 2) {
                this.fillWithBlocks(
                        world, structureBoundingBox, b3, 0, 10, b3, 6, 10, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, b3, 0, 12, b3, 6, 12, aOh, aOh, false);
                this.setBlockState(world, bLV, b3, 0, 10, structureBoundingBox);
                this.setBlockState(world, bLV, b3, 0, 12, structureBoundingBox);
                this.setBlockState(world, bLV, b3, 4, 10, structureBoundingBox);
                this.setBlockState(world, bLV, b3, 4, 12, structureBoundingBox);
                b3 = 12;
                k++;
                if (i == 0) {
                    break;
                }
            }

            b3 = 8;
            k = 0;

            while (k < 2) {
                this.fillWithBlocks(
                        world, structureBoundingBox, b3, 0, 7, b3, 2, 7, aOh, aOh, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, b3, 0, 14, b3, 2, 14, aOh, aOh, false);
                b3 = 14;
                k++;
                if (i == 0) {
                    break;
                }
            }

            this.fillWithBlocks(
                    world, structureBoundingBox, 8, 3, 8, 8, 3, 13, bLX, bLX, false);
            this.fillWithBlocks(
                    world, structureBoundingBox, 14, 3, 8, 14, 3, 13, bLX, bLX, false);
            this.method_073023fc34d438b4ac6c(world, structureBoundingBox, 11, 5, 13);
        }

        return true;
    }

    public StructureOceanMonumentPiecesWingRoom(
            Facing facing, StructureBoundingBox structureBoundingBox, int p171_2) {
        super(facing, structureBoundingBox);
        this.rz = p171_2 & 1;
    }

    public StructureOceanMonumentPiecesWingRoom() {}
}
