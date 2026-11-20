package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePiecesStairs extends StructureNetherBridgePiece {
    public StructureNetherBridgePiecesStairs() {}

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        this.getNextComponentZ(
                (net.minecraft.world.gen.structure.StructureNetherBridgePiecesStart) structureComponent, list, random, 6, 2, false);
    }

    public StructureNetherBridgePiecesStairs(
            int p171_0, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(p171_0);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                6,
                1,
                6,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        int l = StructureComponent.f();
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                0,
                6,
                10,
                6,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                0,
                1,
                8,
                0,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                2,
                0,
                6,
                8,
                0,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                1,
                0,
                8,
                6,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                2,
                1,
                6,
                8,
                6,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                2,
                6,
                5,
                8,
                6,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                3,
                2,
                0,
                5,
                4,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                3,
                2,
                6,
                5,
                2,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                3,
                4,
                6,
                5,
                4,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        int i = l;
        this.setBlockState(
                world, Blocks.nether_brick.getDefaultState(), 5, 2, 5, structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                2,
                5,
                4,
                3,
                5,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                2,
                5,
                3,
                4,
                5,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                2,
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
                1,
                2,
                5,
                1,
                6,
                5,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                7,
                1,
                5,
                7,
                4,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                8,
                2,
                6,
                8,
                4,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                6,
                0,
                4,
                8,
                0,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                5,
                0,
                4,
                5,
                0,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        int j = 0;

        while (j <= 6) {
            int k = 0;

            while (true) {
                if (k <= 6) {
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

    public static StructureNetherBridgePiecesStairs method_77a90abf570eb3af8f31(
            List list,
            Random random,
            int p171_2,
            int p171_3,
            int p171_4,
            int p171_5,
            Facing facing) {
        net.minecraft.world.gen.structure.StructureBoundingBox structureBoundingBox =
                net.minecraft.world.gen.structure.StructureBoundingBox.getComponentToAddBoundingBox(
                        p171_2, p171_3, p171_4, -2, 0, 0, 7, 11, 7, facing);
        return net.minecraft.world.gen.structure.StructureNetherBridgePiece.isAboveGround(structureBoundingBox)
                        && net.minecraft.world.gen.structure.StructureComponent.findIntersecting(list, structureBoundingBox)
                                == null
                ? new net.minecraft.world.gen.structure.StructureNetherBridgePiecesStairs(p171_5, random, structureBoundingBox, facing)
                : null;
    }
}
