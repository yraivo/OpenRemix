package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class StructureOceanMonumentPiecesSimpleTopRoom extends StructureOceanMonumentPiecesPiece {
    public StructureOceanMonumentPiecesSimpleTopRoom(
            Facing facing, StructureOceanMonumentPiecesRoomDefinition structureOceanMonumentPiecesRoomDefinition, Random random) {
        super(1, facing, structureOceanMonumentPiecesRoomDefinition, 1, 1, 1);
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (this.bLW.a / 25 > 0) {
            this.method_693bdf1dd0a98b17a651(
                    world, structureBoundingBox, 0, 0, this.bLW.aCM[Facing.DOWN.getIndex()]);
        }

        if (this.bLW.bMV[Facing.UP.getIndex()] == null) {
            this.method_0f930b19b46d08e97b6e(world, structureBoundingBox, 1, 4, 1, 6, 4, 6, bLt);
        }

        int j = 1;

        label42:
        while (j <= 6) {
            int k = 1;

            while (true) {
                if (k <= 6) {
                    if (random.nextInt(3) != 0) {
                        int l = 2 + (random.nextInt(4) == 0 ? 0 : 1);
                        this.fillWithBlocks(
                                world,
                                structureBoundingBox,
                                j,
                                l,
                                k,
                                j,
                                3,
                                k,
                                Blocks.sponge.getStateFromMeta(1),
                                Blocks.sponge.getStateFromMeta(1),
                                false);
                    }

                    k++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label42;
                }
                break;
            }
        }

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

        return true;
    }

    public StructureOceanMonumentPiecesSimpleTopRoom() {}
}
