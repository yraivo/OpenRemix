package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureVillagePiecesWell extends StructureVillagePiecesVillage {
    public StructureVillagePiecesWell() {}

    public StructureVillagePiecesWell(
            StructureVillagePiecesStart structureVillagePiecesStart,
            int i,
            Random random,
            int j,
            int k) {
        this(structureVillagePiecesStart, i, random, j, k, StructureComponent.f());
    }

    private StructureVillagePiecesWell(
            StructureVillagePiecesStart structureVillagePiecesStart,
            int i,
            Random random,
            int j,
            int k,
            int initialFlowToken) {
        super(structureVillagePiecesStart, i);

        int i1 = initialFlowToken;

        int l = i1;
        this.coordBaseMode = FacingPlane.HORIZONTAL.random(random);
        switch (this.coordBaseMode) {
            case NORTH:
            case SOUTH:
                this.boundingBox = new StructureBoundingBox(j, 64, k, j + 6 - 1, 78, k + 6 - 1);
                if (l == 0) {
                    break;
                }
            default:
                this.boundingBox = new StructureBoundingBox(j, 64, k, j + 6 - 1, 78, k + 6 - 1);
        }
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (this.ef < 0) {
            this.ef = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.ef < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.ef - this.boundingBox.maxY + 3, 0);
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                0,
                1,
                4,
                12,
                4,
                Blocks.cobblestone.getDefaultState(),
                Blocks.flowing_water.getDefaultState(),
                false);
        this.setBlockState(world, Blocks.air.getDefaultState(), 2, 12, 2, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 3, 12, 2, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 2, 12, 3, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 3, 12, 3, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 1, 13, 1, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 1, 14, 1, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 4, 13, 1, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 4, 14, 1, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 1, 13, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 1, 14, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 4, 13, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 4, 14, 4, structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                15,
                1,
                4,
                15,
                4,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        int j = 0;

        while (j <= 5) {
            int k = 0;

            while (true) {
                if (k <= 5) {
                    if (k == 0 || k == 5 || j == 0 || j == 5) {
                        this.setBlockState(
                                world,
                                Blocks.gravel.getDefaultState(),
                                k,
                                11,
                                j,
                                structureBoundingBox);
                        this.clearCurrentPositionBlocksUpwards(
                                world, k, 12, j, structureBoundingBox);
                    }

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

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        StructureVillagePieces.method_3f00e5c935f0d75ee343(
                (StructureVillagePiecesStart) structureComponent,
                list,
                random,
                this.boundingBox.minX - 1,
                this.boundingBox.maxY - 4,
                this.boundingBox.minZ + 1,
                Facing.WEST,
                this.getComponentType());
        StructureVillagePieces.method_3f00e5c935f0d75ee343(
                (StructureVillagePiecesStart) structureComponent,
                list,
                random,
                this.boundingBox.maxX + 1,
                this.boundingBox.maxY - 4,
                this.boundingBox.minZ + 1,
                Facing.EAST,
                this.getComponentType());
        StructureVillagePieces.method_3f00e5c935f0d75ee343(
                (StructureVillagePiecesStart) structureComponent,
                list,
                random,
                this.boundingBox.minX + 1,
                this.boundingBox.maxY - 4,
                this.boundingBox.minZ - 1,
                Facing.NORTH,
                this.getComponentType());
        StructureVillagePieces.method_3f00e5c935f0d75ee343(
                (StructureVillagePiecesStart) structureComponent,
                list,
                random,
                this.boundingBox.minX + 1,
                this.boundingBox.maxY - 4,
                this.boundingBox.maxZ + 1,
                Facing.SOUTH,
                this.getComponentType());
    }
}
