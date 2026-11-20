package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePiecesCorridor4 extends StructureNetherBridgePiece {
    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        byte b0 = 1;
        if (this.coordBaseMode == Facing.WEST || this.coordBaseMode == Facing.NORTH) {
            b0 = 5;
        }

        this.getNextComponentX(
                (StructureNetherBridgePiecesStart) structureComponent,
                list,
                random,
                0,
                b0,
                random.nextInt(8) > 0);
        this.getNextComponentZ(
                (StructureNetherBridgePiecesStart) structureComponent,
                list,
                random,
                0,
                b0,
                random.nextInt(8) > 0);
    }

    public StructureNetherBridgePiecesCorridor4() {}

    public static StructureNetherBridgePiecesCorridor4 method_129520813f4a131fcbc1(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -3, 0, 0, 9, 7, 9, facing);
        return isAboveGround(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureNetherBridgePiecesCorridor4(l, random, structureBoundingBox, facing)
                : null;
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int l = StructureComponent.cR();
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                8,
                1,
                8,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                0,
                8,
                5,
                8,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                6,
                0,
                8,
                6,
                5,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                0,
                2,
                5,
                0,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        int i = l;
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                2,
                0,
                8,
                5,
                0,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                3,
                0,
                1,
                4,
                0,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                3,
                0,
                7,
                4,
                0,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                4,
                8,
                2,
                8,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                4,
                2,
                2,
                4,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                1,
                4,
                7,
                2,
                4,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                3,
                8,
                8,
                3,
                8,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                3,
                6,
                0,
                3,
                7,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                3,
                6,
                8,
                3,
                7,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                3,
                4,
                0,
                5,
                5,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                3,
                4,
                8,
                5,
                5,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                3,
                5,
                2,
                5,
                5,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                3,
                5,
                7,
                5,
                5,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                4,
                5,
                1,
                5,
                5,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                4,
                5,
                7,
                5,
                5,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        int j = 0;

        while (j <= 5) {
            int k = 0;

            while (true) {
                if (k <= 8) {
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            k,
                            -1,
                            j,
                            structureBoundingBox);
                    k++;
                    if (i != 0) {
                        continue;
                    }
                }

                j++;
                if (i == 0) {
                    return true;
                }
                break;
            }
        }

        return true;
    }

    public StructureNetherBridgePiecesCorridor4(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }
}
