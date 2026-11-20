package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureStrongholdPiecesCorridor extends StructureStrongholdPiecesStronghold {

    private int f;


    public StructureStrongholdPiecesCorridor(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
        this.f =
                aAcValue != Facing.NORTH && aAcValue != Facing.SOUTH
                        ? bMdValue.getXSize()
                        : bMdValue.getZSize();
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.cR();
        if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
        }

        int j = 0;

        while (j < this.f) {
            this.setBlockState(
                    world, Blocks.stonebrick.getDefaultState(), 0, 0, j, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.stonebrick.getDefaultState(), 1, 0, j, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.stonebrick.getDefaultState(), 2, 0, j, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.stonebrick.getDefaultState(), 3, 0, j, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.stonebrick.getDefaultState(), 4, 0, j, structureBoundingBox);
            int k = 1;

            while (true) {
                if (k <= 3) {
                    this.setBlockState(
                            world,
                            Blocks.stonebrick.getDefaultState(),
                            0,
                            k,
                            j,
                            structureBoundingBox);
                    this.setBlockState(
                            world, Blocks.air.getDefaultState(), 1, k, j, structureBoundingBox);
                    this.setBlockState(
                            world, Blocks.air.getDefaultState(), 2, k, j, structureBoundingBox);
                    this.setBlockState(
                            world, Blocks.air.getDefaultState(), 3, k, j, structureBoundingBox);
                    this.setBlockState(
                            world,
                            Blocks.stonebrick.getDefaultState(),
                            4,
                            k,
                            j,
                            structureBoundingBox);
                    k++;
                    if (i != 0) {
                        continue;
                    }
                }

                this.setBlockState(
                        world, Blocks.stonebrick.getDefaultState(), 0, 4, j, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stonebrick.getDefaultState(), 1, 4, j, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stonebrick.getDefaultState(), 2, 4, j, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stonebrick.getDefaultState(), 3, 4, j, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stonebrick.getDefaultState(), 4, 4, j, structureBoundingBox);
                j++;
                if (i == 0) {
                    return true;
                }
                break;
            }
        }

        return true;
    }

    public StructureStrongholdPiecesCorridor() {}

    public static StructureBoundingBox createPiece(
            List list, Random random, int i, int j, int k, Facing facing) {
        int j1 = StructureComponent.cR();

        int l = j1;
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -1, -1, 0, 5, 5, 4, facing);
        StructureComponent intersecting =
                StructureComponent.findIntersecting(list, structureBoundingBox);
        if (intersecting == null) {
            return null;
        }

        if (intersecting.getBoundingBox().minY == structureBoundingBox.minY) {
            int i1 = 3;

            while (i1 >= 1) {
                structureBoundingBox =
                        StructureBoundingBox.getComponentToAddBoundingBox(
                                i, j, k, -1, -1, 0, 5, 5, i1 - 1, facing);
                if (!intersecting.getBoundingBox().intersectsWith(structureBoundingBox)) {
                    return StructureBoundingBox.getComponentToAddBoundingBox(
                            i, j, k, -1, -1, 0, 5, 5, i1, facing);
                }

                i1--;
                if (l == 0) {
                    break;
                }
            }
        }

        return null;
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setInteger("Steps", this.f);
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.f = compoundTag.getInteger("Steps");
    }
}
