package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePiecesCorridor3 extends StructureNetherBridgePiece {
    public StructureNetherBridgePiecesCorridor3() {}

    public static StructureNetherBridgePiecesCorridor3 method_9cee7f452da891fac37b(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -1, -7, 0, 5, 14, 10, facing);
        return isAboveGround(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureNetherBridgePiecesCorridor3(l, random, structureBoundingBox, facing)
                : null;
    }

    public StructureNetherBridgePiecesCorridor3(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        this.getNextComponentNormal(
                (StructureNetherBridgePiecesStart) structureComponent, list, random, 1, 0, true);
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int l1 = StructureComponent.f();
        int j = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 2);
        int i = l1;
        int k = 0;

        while (k <= 9) {
            int l = Math.max(1, 7 - k);
            int i1 = Math.min(Math.max(l + 5, 14 - k), 13);
            int j1 = k;
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    0,
                    0,
                    k,
                    4,
                    l,
                    k,
                    Blocks.nether_brick.getDefaultState(),
                    Blocks.nether_brick.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    1,
                    l + 1,
                    k,
                    3,
                    i1 - 1,
                    k,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
            if (k <= 6) {
                this.setBlockState(
                        world,
                        Blocks.nether_brick_stairs.getStateFromMeta(j),
                        1,
                        l + 1,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.nether_brick_stairs.getStateFromMeta(j),
                        2,
                        l + 1,
                        k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.nether_brick_stairs.getStateFromMeta(j),
                        3,
                        l + 1,
                        k,
                        structureBoundingBox);
            }

            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    0,
                    i1,
                    k,
                    4,
                    i1,
                    k,
                    Blocks.nether_brick.getDefaultState(),
                    Blocks.nether_brick.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    0,
                    l + 1,
                    k,
                    0,
                    i1 - 1,
                    k,
                    Blocks.nether_brick.getDefaultState(),
                    Blocks.nether_brick.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    4,
                    l + 1,
                    k,
                    4,
                    i1 - 1,
                    k,
                    Blocks.nether_brick.getDefaultState(),
                    Blocks.nether_brick.getDefaultState(),
                    false);
            if ((k & 1) == 0) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        0,
                        l + 2,
                        k,
                        0,
                        l + 3,
                        k,
                        Blocks.nether_brick_fence.getDefaultState(),
                        Blocks.nether_brick_fence.getDefaultState(),
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        4,
                        l + 2,
                        k,
                        4,
                        l + 3,
                        k,
                        Blocks.nether_brick_fence.getDefaultState(),
                        Blocks.nether_brick_fence.getDefaultState(),
                        false);
            }

            int k1 = 0;

            while (true) {
                if (k1 <= 4) {
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            k1,
                            -1,
                            j1,
                            structureBoundingBox);
                    k1++;
                    if (i == 0) {
                        continue;
                    }
                }

                k++;
                if (i != 0) {
                    return true;
                }
                break;
            }
        }

        return true;
    }
}
