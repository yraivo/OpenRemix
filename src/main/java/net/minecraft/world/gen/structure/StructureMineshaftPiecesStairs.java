package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureMineshaftPiecesStairs extends StructureComponent {
    public StructureMineshaftPiecesStairs() {}

    protected void readStructureFromNBT(CompoundTag compoundTag) {}

    public StructureMineshaftPiecesStairs(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {}

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                5,
                0,
                2,
                7,
                1,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                7,
                2,
                2,
                8,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        int j = 0;

        while (j < 5) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    0,
                    5 - j - (j < 4 ? 1 : 0),
                    2 + j,
                    2,
                    7 - j,
                    2 + j,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
            j++;
            if (i != 0) {
                break;
            }
        }

        return true;
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        int k = StructureComponent.f();
        int j = this.getComponentType();
        int i = k;
        if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
                case NORTH:
                    StructureMineshaftPieces.method_6267762401a35bb3f241(
                            structureComponent,
                            list,
                            random,
                            this.boundingBox.minX,
                            this.boundingBox.minY,
                            this.boundingBox.minZ - 1,
                            Facing.NORTH,
                            j);
                    if (i == 0) {
                        break;
                    }
                case SOUTH:
                    StructureMineshaftPieces.method_6267762401a35bb3f241(
                            structureComponent,
                            list,
                            random,
                            this.boundingBox.minX,
                            this.boundingBox.minY,
                            this.boundingBox.maxZ + 1,
                            Facing.SOUTH,
                            j);
                    if (i == 0) {
                        break;
                    }
                case WEST:
                    StructureMineshaftPieces.method_6267762401a35bb3f241(
                            structureComponent,
                            list,
                            random,
                            this.boundingBox.minX - 1,
                            this.boundingBox.minY,
                            this.boundingBox.minZ,
                            Facing.WEST,
                            j);
                    if (i == 0) {
                        break;
                    }
                case EAST:
                    StructureMineshaftPieces.method_6267762401a35bb3f241(
                            structureComponent,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY,
                            this.boundingBox.minZ,
                            Facing.EAST,
                            j);
            }
        }
    }

    public static StructureBoundingBox findPieceBox(
            List list, Random random, int i, int j, int k, Facing facing) {
        int i1 = StructureComponent.f();
        StructureBoundingBox structureBoundingBox =
                new StructureBoundingBox(i, j - 5, k, i, j + 2, k);
        int l = i1;
        switch (facing) {
            case NORTH:
                structureBoundingBox.maxX = i + 2;
                structureBoundingBox.minZ = k - 8;
                if (l == 0) {
                    break;
                }
            case SOUTH:
                structureBoundingBox.maxX = i + 2;
                structureBoundingBox.maxZ = k + 8;
                if (l == 0) {
                    break;
                }
            case WEST:
                structureBoundingBox.minX = i - 8;
                structureBoundingBox.maxZ = k + 2;
                if (l == 0) {
                    break;
                }
            case EAST:
                structureBoundingBox.maxX = i + 8;
                structureBoundingBox.maxZ = k + 2;
        }

        return StructureComponent.findIntersecting(list, structureBoundingBox) != null
                ? null
                : structureBoundingBox;
    }
}
