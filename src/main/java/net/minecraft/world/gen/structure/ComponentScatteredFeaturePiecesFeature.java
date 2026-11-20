package net.minecraft.world.gen.structure;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;

import java.util.Random;

public abstract class ComponentScatteredFeaturePiecesFeature extends StructureComponent {

    protected int scatteredFeatureSizeZ;

    protected int scatteredFeatureSizeY;
    protected int sR;

    protected int scatteredFeatureSizeX;

    public ComponentScatteredFeaturePiecesFeature(
            Random random, int i, int j, int k, int l, int scatteredFeatureSizeYValue, int j1) {
        super(0);
        int l1 = StructureComponent.cR();
        this.sR = -1;
        int k1 = l1;
        this.scatteredFeatureSizeX = l;
        this.scatteredFeatureSizeY = scatteredFeatureSizeYValue;
        this.scatteredFeatureSizeZ = j1;
        this.coordBaseMode = FacingPlane.HORIZONTAL.random(random);
        switch (this.coordBaseMode) {
            case NORTH:
            case SOUTH:
                this.boundingBox =
                        new StructureBoundingBox(
                                i, j, k, i + l - 1, j + scatteredFeatureSizeYValue - 1, k + j1 - 1);
                if (k1 != 0) {
                    break;
                }
            default:
                this.boundingBox =
                        new StructureBoundingBox(
                                i, j, k, i + j1 - 1, j + scatteredFeatureSizeYValue - 1, k + l - 1);
        }
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        this.scatteredFeatureSizeX = compoundTag.getInteger("Width");
        this.scatteredFeatureSizeY = compoundTag.getInteger("Height");
        this.scatteredFeatureSizeZ = compoundTag.getInteger("Depth");
        this.sR = compoundTag.getInteger("HPos");
    }

    public ComponentScatteredFeaturePiecesFeature() {
        this.sR = -1;
    }


    protected boolean method_0e94e718e78ead49f0b8(
            World world, StructureBoundingBox structureBoundingBox, int i) {
        int j = StructureComponent.f();
        if (this.sR >= 0) {
            return true;
        }

        int k = 0;
        int l = 0;
        int minZ = this.boundingBox.minZ;

        label33:
        while (minZ <= this.boundingBox.maxZ) {
            int j1 = this.boundingBox.minX;

            while (true) {
                if (j1 <= this.boundingBox.maxX) {
                    BlockPos blockPos = new BlockPos(j1, 64, minZ);
                    if (structureBoundingBox.isVecInside(blockPos)) {
                        k +=
                                Math.max(
                                        world.getTopSolidOrLiquidBlock(blockPos).getY(),
                                        world.provider.getAverageGroundLevel());
                        l++;
                    }

                    j1++;
                    if (j == 0) {
                        continue;
                    }
                }

                minZ++;
                if (j != 0) {
                    break label33;
                }
                break;
            }
        }

        if (l == 0) {
            return false;
        }

        this.sR = k / l;
        this.boundingBox.offset(0, this.sR - this.boundingBox.minY + i, 0);
        return true;
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        compoundTag.setInteger("Width", this.scatteredFeatureSizeX);
        compoundTag.setInteger("Height", this.scatteredFeatureSizeY);
        compoundTag.setInteger("Depth", this.scatteredFeatureSizeZ);
        compoundTag.setInteger("HPos", this.sR);
    }
}
