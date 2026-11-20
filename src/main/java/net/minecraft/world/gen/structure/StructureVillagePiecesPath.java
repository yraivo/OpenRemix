package net.minecraft.world.gen.structure;

import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureVillagePiecesPath extends StructureVillagePiecesRoad {

    private int length;

    public static StructureBoundingBox findPieceBox(
            StructureVillagePiecesStart structureVillagePiecesStart,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing) {
        int j1 = StructureComponent.cR();
        int i1 = 7 * MathHelper.nextIntInclusive(random, 3, 5);
        int l = j1;

        while (i1 >= 7) {
            StructureBoundingBox structureBoundingBox =
                    StructureBoundingBox.getComponentToAddBoundingBox(
                            i, j, k, 0, 0, 0, 3, 3, i1, facing);
            if (StructureComponent.findIntersecting(list, structureBoundingBox) == null) {
                return structureBoundingBox;
            }

            i1 -= 7;
            if (l == 0) {
                break;
            }
        }

        return null;
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.length = compoundTag.getInteger("Length");
    }

    public StructureVillagePiecesPath(
            StructureVillagePiecesStart structureVillagePiecesStart,
            int i,
            Random random,
            StructureBoundingBox bMdValue,
            Facing aAcValue) {
        super(structureVillagePiecesStart, i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
        this.length = Math.max(bMdValue.getXSize(), bMdValue.getZSize());
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        BlockState blockState = this.method_bfd0bbcb6bd28da56f06(Blocks.gravel.getDefaultState());
        BlockState blockStateValue =
                this.method_bfd0bbcb6bd28da56f06(Blocks.cobblestone.getDefaultState());
        int l = StructureComponent.cR();
        int j = this.boundingBox.minX;
        int i = l;

        while (j <= this.boundingBox.maxX) {
            int k = this.boundingBox.minZ;

            while (true) {
                if (k <= this.boundingBox.maxZ) {
                    BlockPos blockPos = new BlockPos(j, 64, k);
                    if (structureBoundingBox.isVecInside(blockPos)) {
                        blockPos = world.getTopSolidOrLiquidBlock(blockPos).offsetDown();
                        world.setBlockState(blockPos, blockState, 2);
                        world.setBlockState(blockPos.offsetDown(), blockStateValue, 2);
                    }

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


    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        byte b0 = 0;
        int k = StructureComponent.cR();
        int j = random.nextInt(5);
        int i = k;

        while (j < this.length - 8) {
            StructureComponent structureComponentValue =
                    this.getNextComponentNN(
                            (StructureVillagePiecesStart) structureComponent, list, random, 0, j);
            if (structureComponentValue != null) {
                j +=
                        Math.max(
                                structureComponentValue.boundingBox.getXSize(),
                                structureComponentValue.boundingBox.getZSize());
                b0 = 1;
            }

            j += 2 + random.nextInt(5);
            if (i == 0) {
                break;
            }
        }

        j = random.nextInt(5);

        while (j < this.length - 8) {
            StructureComponent structureComponentValue2 =
                    this.getNextComponentPP(
                            (StructureVillagePiecesStart) structureComponent, list, random, 0, j);
            if (structureComponentValue2 != null) {
                j +=
                        Math.max(
                                structureComponentValue2.boundingBox.getXSize(),
                                structureComponentValue2.boundingBox.getZSize());
                b0 = 1;
            }

            j += 2 + random.nextInt(5);
            if (i == 0) {
                break;
            }
        }

        if (b0 != 0 && random.nextInt(3) > 0 && this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
                case NORTH:
                    StructureVillagePieces.method_3f00e5c935f0d75ee343(
                            (StructureVillagePiecesStart) structureComponent,
                            list,
                            random,
                            this.boundingBox.minX - 1,
                            this.boundingBox.minY,
                            this.boundingBox.minZ,
                            Facing.WEST,
                            this.getComponentType());
                    if (i != 0) {
                        break;
                    }
                case SOUTH:
                    StructureVillagePieces.method_3f00e5c935f0d75ee343(
                            (StructureVillagePiecesStart) structureComponent,
                            list,
                            random,
                            this.boundingBox.minX - 1,
                            this.boundingBox.minY,
                            this.boundingBox.maxZ - 2,
                            Facing.WEST,
                            this.getComponentType());
                    if (i != 0) {
                        break;
                    }
                case WEST:
                    StructureVillagePieces.method_3f00e5c935f0d75ee343(
                            (StructureVillagePiecesStart) structureComponent,
                            list,
                            random,
                            this.boundingBox.minX,
                            this.boundingBox.minY,
                            this.boundingBox.minZ - 1,
                            Facing.NORTH,
                            this.getComponentType());
                    if (i != 0) {
                        break;
                    }
                case EAST:
                    StructureVillagePieces.method_3f00e5c935f0d75ee343(
                            (StructureVillagePiecesStart) structureComponent,
                            list,
                            random,
                            this.boundingBox.maxX - 2,
                            this.boundingBox.minY,
                            this.boundingBox.minZ - 1,
                            Facing.NORTH,
                            this.getComponentType());
            }
        }

        if (b0 != 0 && random.nextInt(3) > 0 && this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
                case NORTH:
                    StructureVillagePieces.method_3f00e5c935f0d75ee343(
                            (StructureVillagePiecesStart) structureComponent,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY,
                            this.boundingBox.minZ,
                            Facing.EAST,
                            this.getComponentType());
                    if (i != 0) {
                        break;
                    }
                case SOUTH:
                    StructureVillagePieces.method_3f00e5c935f0d75ee343(
                            (StructureVillagePiecesStart) structureComponent,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY,
                            this.boundingBox.maxZ - 2,
                            Facing.EAST,
                            this.getComponentType());
                    if (i != 0) {
                        break;
                    }
                case WEST:
                    StructureVillagePieces.method_3f00e5c935f0d75ee343(
                            (StructureVillagePiecesStart) structureComponent,
                            list,
                            random,
                            this.boundingBox.minX,
                            this.boundingBox.minY,
                            this.boundingBox.maxZ + 1,
                            Facing.SOUTH,
                            this.getComponentType());
                    if (i != 0) {
                        break;
                    }
                case EAST:
                    StructureVillagePieces.method_3f00e5c935f0d75ee343(
                            (StructureVillagePiecesStart) structureComponent,
                            list,
                            random,
                            this.boundingBox.maxX - 2,
                            this.boundingBox.minY,
                            this.boundingBox.maxZ + 1,
                            Facing.SOUTH,
                            this.getComponentType());
            }
        }
    }

    public StructureVillagePiecesPath() {}

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setInteger("Length", this.length);
    }
}
