package net.minecraft.world.gen.structure;

import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class StructureOceanMonumentPiecesPenthouse extends StructureOceanMonumentPiecesPiece {
    public StructureOceanMonumentPiecesPenthouse(Facing facing, StructureBoundingBox structureBoundingBox) {
        super(facing, structureBoundingBox);
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        this.fillWithBlocks(
                world, structureBoundingBox, 2, -1, 2, 11, -1, 11, aOh, aOh, false);
        int k = StructureComponent.cR();
        this.fillWithBlocks(
                world, structureBoundingBox, 0, -1, 0, 1, -1, 11, bLt, bLt, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 12, -1, 0, 13, -1, 11, bLt, bLt, false);
        int i = k;
        this.fillWithBlocks(
                world, structureBoundingBox, 2, -1, 0, 11, -1, 1, bLt, bLt, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 2, -1, 12, 11, -1, 13, bLt, bLt, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 0, 0, 0, 0, 0, 13, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 13, 0, 0, 13, 0, 13, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 0, 0, 12, 0, 0, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 0, 13, 12, 0, 13, aOh, aOh, false);
        byte b0 = 2;

        while (b0 <= 11) {
            this.setBlockState(world, bLV, 0, 0, b0, structureBoundingBox);
            this.setBlockState(world, bLV, 13, 0, b0, structureBoundingBox);
            this.setBlockState(world, bLV, b0, 0, 0, structureBoundingBox);
            b0 += 3;
            if (i == 0) {
                break;
            }
        }

        this.fillWithBlocks(
                world, structureBoundingBox, 2, 0, 3, 4, 0, 9, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 9, 0, 3, 11, 0, 9, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 4, 0, 9, 9, 0, 11, aOh, aOh, false);
        this.setBlockState(world, aOh, 5, 0, 8, structureBoundingBox);
        this.setBlockState(world, aOh, 8, 0, 8, structureBoundingBox);
        this.setBlockState(world, aOh, 10, 0, 10, structureBoundingBox);
        this.setBlockState(world, aOh, 3, 0, 10, structureBoundingBox);
        this.fillWithBlocks(
                world, structureBoundingBox, 3, 0, 3, 3, 0, 7, bLX, bLX, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 10, 0, 3, 10, 0, 7, bLX, bLX, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 6, 0, 10, 7, 0, 10, bLX, bLX, false);
        b0 = 3;
        int j = 0;

        label24:
        while (j < 2) {
            byte b1 = 2;

            while (true) {
                if (b1 <= 8) {
                    this.fillWithBlocks(
                            world, structureBoundingBox, b0, 0, b1, b0, 2, b1, aOh, aOh, false);
                    b1 += 3;
                    if (i != 0) {
                        continue;
                    }
                }

                b0 = 10;
                j++;
                if (i == 0) {
                    break label24;
                }
                break;
            }
        }

        this.fillWithBlocks(
                world, structureBoundingBox, 5, 0, 10, 5, 2, 10, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 8, 0, 10, 8, 2, 10, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 6, -1, 7, 7, -1, 8, bLX, bLX, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 6, -1, 3, 7, -1, 4, bBJ, bBJ, false);
        this.method_073023fc34d438b4ac6c(world, structureBoundingBox, 6, 1, 6);
        return true;
    }

    public StructureOceanMonumentPiecesPenthouse() {}
}
