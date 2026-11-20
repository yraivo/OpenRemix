package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePiecesCorridor5 extends StructureNetherBridgePiece {
    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int l = StructureComponent.f();
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                4,
                1,
                4,
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
                5,
                4,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        int i = l;
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                0,
                0,
                5,
                4,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                2,
                0,
                4,
                5,
                4,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                3,
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
                3,
                0,
                4,
                3,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                3,
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
                3,
                4,
                4,
                3,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                6,
                0,
                4,
                6,
                4,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        int j = 0;

        while (j <= 4) {
            int k = 0;

            while (true) {
                if (k <= 4) {
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            j,
                            -1,
                            k,
                            structureBoundingBox);
                    k++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    return true;
                }
                break;
            }
        }

        return true;
    }

    public static StructureNetherBridgePiecesCorridor5 method_0e922694bc8dc0e8d129(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -1, 0, 0, 5, 7, 5, facing);
        return isAboveGround(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureNetherBridgePiecesCorridor5(l, random, structureBoundingBox, facing)
                : null;
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        this.getNextComponentNormal(
                (StructureNetherBridgePiecesStart) structureComponent, list, random, 1, 0, true);
    }

    public StructureNetherBridgePiecesCorridor5(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }

    public StructureNetherBridgePiecesCorridor5() {}
}
