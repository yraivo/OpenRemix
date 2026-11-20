package net.minecraft.world.gen.structure;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureMineshaftPiecesCross extends StructureComponent {

    private static String[] packedTextFragments;
    private boolean isMultipleFloors;
    private Facing corridorDirection;

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
        }

        label37:
        {
            if (this.isMultipleFloors) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        this.boundingBox.minX + 1,
                        this.boundingBox.minY,
                        this.boundingBox.minZ,
                        this.boundingBox.maxX - 1,
                        this.boundingBox.minY + 3 - 1,
                        this.boundingBox.maxZ,
                        Blocks.air.getDefaultState(),
                        Blocks.air.getDefaultState(),
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        this.boundingBox.minX,
                        this.boundingBox.minY,
                        this.boundingBox.minZ + 1,
                        this.boundingBox.maxX,
                        this.boundingBox.minY + 3 - 1,
                        this.boundingBox.maxZ - 1,
                        Blocks.air.getDefaultState(),
                        Blocks.air.getDefaultState(),
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        this.boundingBox.minX + 1,
                        this.boundingBox.maxY - 2,
                        this.boundingBox.minZ,
                        this.boundingBox.maxX - 1,
                        this.boundingBox.maxY,
                        this.boundingBox.maxZ,
                        Blocks.air.getDefaultState(),
                        Blocks.air.getDefaultState(),
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        this.boundingBox.minX,
                        this.boundingBox.maxY - 2,
                        this.boundingBox.minZ + 1,
                        this.boundingBox.maxX,
                        this.boundingBox.maxY,
                        this.boundingBox.maxZ - 1,
                        Blocks.air.getDefaultState(),
                        Blocks.air.getDefaultState(),
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        this.boundingBox.minX + 1,
                        this.boundingBox.minY + 3,
                        this.boundingBox.minZ + 1,
                        this.boundingBox.maxX - 1,
                        this.boundingBox.minY + 3,
                        this.boundingBox.maxZ - 1,
                        Blocks.air.getDefaultState(),
                        Blocks.air.getDefaultState(),
                        false);
                if (i == 0) {
                    break label37;
                }
            }

            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    this.boundingBox.minX + 1,
                    this.boundingBox.minY,
                    this.boundingBox.minZ,
                    this.boundingBox.maxX - 1,
                    this.boundingBox.maxY,
                    this.boundingBox.maxZ,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    this.boundingBox.minX,
                    this.boundingBox.minY,
                    this.boundingBox.minZ + 1,
                    this.boundingBox.maxX,
                    this.boundingBox.maxY,
                    this.boundingBox.maxZ - 1,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.boundingBox.minX + 1,
                this.boundingBox.minY,
                this.boundingBox.minZ + 1,
                this.boundingBox.minX + 1,
                this.boundingBox.maxY,
                this.boundingBox.minZ + 1,
                Blocks.planks.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.boundingBox.minX + 1,
                this.boundingBox.minY,
                this.boundingBox.maxZ - 1,
                this.boundingBox.minX + 1,
                this.boundingBox.maxY,
                this.boundingBox.maxZ - 1,
                Blocks.planks.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.boundingBox.maxX - 1,
                this.boundingBox.minY,
                this.boundingBox.minZ + 1,
                this.boundingBox.maxX - 1,
                this.boundingBox.maxY,
                this.boundingBox.minZ + 1,
                Blocks.planks.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.boundingBox.maxX - 1,
                this.boundingBox.minY,
                this.boundingBox.maxZ - 1,
                this.boundingBox.maxX - 1,
                this.boundingBox.maxY,
                this.boundingBox.maxZ - 1,
                Blocks.planks.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        int j = this.boundingBox.minX;

        while (j <= this.boundingBox.maxX) {
            int k = this.boundingBox.minZ;

            while (true) {
                if (k <= this.boundingBox.maxZ) {
                    if (this.getBlockStateFromPos(
                                            world,
                                            j,
                                            this.boundingBox.minY - 1,
                                            k,
                                            structureBoundingBox)
                                    .getBlock()
                                    .getMaterial()
                            == Material.AIR) {
                        this.setBlockState(
                                world,
                                Blocks.planks.getDefaultState(),
                                j,
                                this.boundingBox.minY - 1,
                                k,
                                structureBoundingBox);
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

    public static StructureBoundingBox findPieceBox(
            List list, Random random, int i, int j, int k, Facing facing) {
        int i1 = StructureComponent.f();
        StructureBoundingBox structureBoundingBox = new StructureBoundingBox(i, j, k, i, j + 2, k);
        int l = i1;
        if (random.nextInt(4) == 0) {
            structureBoundingBox.maxY += 4;
        }

        switch (facing) {
            case NORTH:
                structureBoundingBox.minX = i - 1;
                structureBoundingBox.maxX = i + 3;
                structureBoundingBox.minZ = k - 4;
                if (l == 0) {
                    break;
                }
            case SOUTH:
                structureBoundingBox.minX = i - 1;
                structureBoundingBox.maxX = i + 3;
                structureBoundingBox.maxZ = k + 4;
                if (l == 0) {
                    break;
                }
            case WEST:
                structureBoundingBox.minX = i - 4;
                structureBoundingBox.minZ = k - 1;
                structureBoundingBox.maxZ = k + 3;
                if (l == 0) {
                    break;
                }
            case EAST:
                structureBoundingBox.maxX = i + 4;
                structureBoundingBox.minZ = k - 1;
                structureBoundingBox.maxZ = k + 3;
        }

        return StructureComponent.findIntersecting(list, structureBoundingBox) != null
                ? null
                : structureBoundingBox;
    }

    public StructureMineshaftPiecesCross() {}

    static {
        net.minecraft.world.gen.structure.StructureMineshaftPiecesCross.packedTextFragments =
                new String[3];
        net.minecraft.world.gen.structure.StructureMineshaftPiecesCross.packedTextFragments[0] =
                new String("D");
        net.minecraft.world.gen.structure.StructureMineshaftPiecesCross.packedTextFragments[1] =
                new String("D");
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        compoundTag.setBoolean("tf", this.isMultipleFloors);
        compoundTag.setInteger(packedTextFragments[0], this.corridorDirection.getHorizontalIndex());
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        this.isMultipleFloors = compoundTag.getBoolean("tf");
        this.corridorDirection =
                Facing.getHorizontal(compoundTag.getInteger(packedTextFragments[1]));
    }

    public StructureMineshaftPiecesCross(
            int i, Random random, StructureBoundingBox bMdValue, Facing bMmValue) {
        super(i);
        this.corridorDirection = bMmValue;
        this.boundingBox = bMdValue;
        this.isMultipleFloors =
                remix.compat.compiler.JvmBooleanSlots.fromInt((bMdValue.getYSize() > 3 ? 1 : 0));
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        int k = StructureComponent.cR();
        int j = this.getComponentType();
        int i = k;
        switch (this.corridorDirection) {
            case NORTH:
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX + 1,
                        this.boundingBox.minY,
                        this.boundingBox.minZ - 1,
                        Facing.NORTH,
                        j);
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX - 1,
                        this.boundingBox.minY,
                        this.boundingBox.minZ + 1,
                        Facing.WEST,
                        j);
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.maxX + 1,
                        this.boundingBox.minY,
                        this.boundingBox.minZ + 1,
                        Facing.EAST,
                        j);
                if (i != 0) {
                    break;
                }
            case SOUTH:
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX + 1,
                        this.boundingBox.minY,
                        this.boundingBox.maxZ + 1,
                        Facing.SOUTH,
                        j);
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX - 1,
                        this.boundingBox.minY,
                        this.boundingBox.minZ + 1,
                        Facing.WEST,
                        j);
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.maxX + 1,
                        this.boundingBox.minY,
                        this.boundingBox.minZ + 1,
                        Facing.EAST,
                        j);
                if (i != 0) {
                    break;
                }
            case WEST:
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX + 1,
                        this.boundingBox.minY,
                        this.boundingBox.minZ - 1,
                        Facing.NORTH,
                        j);
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX + 1,
                        this.boundingBox.minY,
                        this.boundingBox.maxZ + 1,
                        Facing.SOUTH,
                        j);
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX - 1,
                        this.boundingBox.minY,
                        this.boundingBox.minZ + 1,
                        Facing.WEST,
                        j);
                if (i != 0) {
                    break;
                }
            case EAST:
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX + 1,
                        this.boundingBox.minY,
                        this.boundingBox.minZ - 1,
                        Facing.NORTH,
                        j);
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX + 1,
                        this.boundingBox.minY,
                        this.boundingBox.maxZ + 1,
                        Facing.SOUTH,
                        j);
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.maxX + 1,
                        this.boundingBox.minY,
                        this.boundingBox.minZ + 1,
                        Facing.EAST,
                        j);
        }

        if (this.isMultipleFloors) {
            if (random.nextBoolean()) {
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX + 1,
                        this.boundingBox.minY + 3 + 1,
                        this.boundingBox.minZ - 1,
                        Facing.NORTH,
                        j);
            }

            if (random.nextBoolean()) {
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX - 1,
                        this.boundingBox.minY + 3 + 1,
                        this.boundingBox.minZ + 1,
                        Facing.WEST,
                        j);
            }

            if (random.nextBoolean()) {
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.maxX + 1,
                        this.boundingBox.minY + 3 + 1,
                        this.boundingBox.minZ + 1,
                        Facing.EAST,
                        j);
            }

            if (random.nextBoolean()) {
                StructureMineshaftPieces.method_6267762401a35bb3f241(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX + 1,
                        this.boundingBox.minY + 3 + 1,
                        this.boundingBox.maxZ + 1,
                        Facing.SOUTH,
                        j);
            }
        }
    }
}
