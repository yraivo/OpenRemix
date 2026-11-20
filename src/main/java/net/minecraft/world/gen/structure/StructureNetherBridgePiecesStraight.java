package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePiecesStraight extends StructureNetherBridgePiece {
    public StructureNetherBridgePiecesStraight() {}

    public StructureNetherBridgePiecesStraight(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        this.getNextComponentNormal(
                (StructureNetherBridgePiecesStart) structureComponent, list, random, 1, 3, false);
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int l = StructureComponent.cR();
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                3,
                0,
                4,
                4,
                18,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        int i = l;
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                5,
                0,
                3,
                7,
                18,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                5,
                0,
                0,
                5,
                18,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                5,
                0,
                4,
                5,
                18,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                0,
                4,
                2,
                5,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                13,
                4,
                2,
                18,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                4,
                1,
                3,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                15,
                4,
                1,
                18,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        int j = 0;

        label21:
        while (j <= 4) {
            int k = 0;

            while (true) {
                if (k <= 2) {
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            j,
                            -1,
                            k,
                            structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            j,
                            -1,
                            18 - k,
                            structureBoundingBox);
                    k++;
                    if (i != 0) {
                        continue;
                    }
                }

                j++;
                if (i == 0) {
                    break label21;
                }
                break;
            }
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                1,
                0,
                4,
                1,
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
                4,
                4,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                3,
                14,
                0,
                4,
                14,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                17,
                0,
                4,
                17,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                1,
                4,
                4,
                1,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                3,
                4,
                4,
                4,
                4,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                3,
                14,
                4,
                4,
                14,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                17,
                4,
                4,
                17,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        return true;
    }

    public static StructureNetherBridgePiecesStraight method_920d8a91c126ebd5c921(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -1, -3, 0, 5, 10, 19, facing);
        return isAboveGround(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureNetherBridgePiecesStraight(l, random, structureBoundingBox, facing)
                : null;
    }
}
