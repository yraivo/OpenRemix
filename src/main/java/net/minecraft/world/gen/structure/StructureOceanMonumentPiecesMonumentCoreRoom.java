package net.minecraft.world.gen.structure;

import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class StructureOceanMonumentPiecesMonumentCoreRoom extends StructureOceanMonumentPiecesPiece {
    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        this.method_0f930b19b46d08e97b6e(world, structureBoundingBox, 1, 8, 0, 14, 8, 14, bLt);
        byte b0 = 7;
        int k = StructureComponent.cR();
        BlockState aOhValue = aOh;
        this.fillWithBlocks(
                world, structureBoundingBox, 0, b0, 0, 0, b0, 15, aOhValue, aOhValue, false);
        int i = k;
        this.fillWithBlocks(
                world, structureBoundingBox, 15, b0, 0, 15, b0, 15, aOhValue, aOhValue, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, b0, 0, 15, b0, 0, aOhValue, aOhValue, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, b0, 15, 14, b0, 15, aOhValue, aOhValue, false);
        int j = 1;

        label48:
        while (j <= 6) {
            aOhValue = aOh;
            if (j == 2 || j == 6) {
                aOhValue = bLt;
            }

            byte b1 = 0;

            while (true) {
                if (b1 <= 15) {
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            b1,
                            j,
                            0,
                            b1,
                            j,
                            1,
                            aOhValue,
                            aOhValue,
                            false);
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            b1,
                            j,
                            6,
                            b1,
                            j,
                            9,
                            aOhValue,
                            aOhValue,
                            false);
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            b1,
                            j,
                            14,
                            b1,
                            j,
                            15,
                            aOhValue,
                            aOhValue,
                            false);
                    b1 += 15;
                    if (i != 0) {
                        continue;
                    }
                }

                this.fillWithBlocks(
                        world, structureBoundingBox, 1, j, 0, 1, j, 0, aOhValue, aOhValue, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 6, j, 0, 9, j, 0, aOhValue, aOhValue, false);
                this.fillWithBlocks(
                        world, structureBoundingBox, 14, j, 0, 14, j, 0, aOhValue, aOhValue, false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        1,
                        j,
                        15,
                        14,
                        j,
                        15,
                        aOhValue,
                        aOhValue,
                        false);
                j++;
                if (i == 0) {
                    break label48;
                }
                break;
            }
        }

        this.fillWithBlocks(
                world, structureBoundingBox, 6, 3, 6, 9, 6, 9, bLX, bLX, false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                4,
                7,
                8,
                5,
                8,
                Blocks.gold_block.getDefaultState(),
                Blocks.gold_block.getDefaultState(),
                false);
        byte b2 = 3;

        label30:
        while (b2 <= 6) {
            byte b3 = 6;

            while (true) {
                if (b3 <= 9) {
                    this.setBlockState(world, bLV, b3, b2, 6, structureBoundingBox);
                    this.setBlockState(world, bLV, b3, b2, 9, structureBoundingBox);
                    b3 += 3;
                    if (i != 0) {
                        continue;
                    }
                }

                b2 += 3;
                if (i == 0) {
                    break label30;
                }
                break;
            }
        }

        this.fillWithBlocks(
                world, structureBoundingBox, 5, 1, 6, 5, 2, 6, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 1, 9, 5, 2, 9, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 10, 1, 6, 10, 2, 6, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 10, 1, 9, 10, 2, 9, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 6, 1, 5, 6, 2, 5, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 9, 1, 5, 9, 2, 5, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 6, 1, 10, 6, 2, 10, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 9, 1, 10, 9, 2, 10, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 2, 5, 5, 6, 5, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 2, 10, 5, 6, 10, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 10, 2, 5, 10, 6, 5, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 10, 2, 10, 10, 6, 10, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 7, 1, 5, 7, 6, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 10, 7, 1, 10, 7, 6, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 5, 7, 9, 5, 7, 14, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 10, 7, 9, 10, 7, 14, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 7, 5, 6, 7, 5, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 1, 7, 10, 6, 7, 10, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 9, 7, 5, 14, 7, 5, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 9, 7, 10, 14, 7, 10, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 2, 1, 2, 2, 1, 3, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 3, 1, 2, 3, 1, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 13, 1, 2, 13, 1, 3, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 12, 1, 2, 12, 1, 2, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 2, 1, 12, 2, 1, 13, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 3, 1, 13, 3, 1, 13, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 13, 1, 12, 13, 1, 13, aOh, aOh, false);
        this.fillWithBlocks(
                world, structureBoundingBox, 12, 1, 13, 12, 1, 13, aOh, aOh, false);
        return true;
    }

    public StructureOceanMonumentPiecesMonumentCoreRoom(
            Facing facing, StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition, Random random) {
        super(1, facing, structureOceanMonumentPiecesRoomDefinition, 2, 2, 2);
    }

    public StructureOceanMonumentPiecesMonumentCoreRoom() {}
}
