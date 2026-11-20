package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class StructureMineshaftPiecesRoom extends StructureComponent {

    private List roomsLinkedToTheRoom = Lists.newLinkedList();

    public StructureMineshaftPiecesRoom() {}

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        int j = StructureComponent.f();
        NBTTagList nbtTagList = new NBTTagList();
        Iterator iterator = this.roomsLinkedToTheRoom.iterator();
        int i = j;

        while (iterator.hasNext()) {
            StructureBoundingBox structureBoundingBox = (StructureBoundingBox) iterator.next();
            nbtTagList.appendTag(structureBoundingBox.toNBTTagIntArray());
            if (i != 0) {
                break;
            }
        }

        compoundTag.setTag("Entrances", nbtTagList);
    }

    public StructureMineshaftPiecesRoom(int i, Random random, int j, int k) {
        super(i);
        this.boundingBox =
                new StructureBoundingBox(
                        j,
                        50,
                        k,
                        j + 7 + random.nextInt(6),
                        54 + random.nextInt(6),
                        k + 7 + random.nextInt(6));
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.boundingBox.minX,
                this.boundingBox.minY,
                this.boundingBox.minZ,
                this.boundingBox.maxX,
                this.boundingBox.minY,
                this.boundingBox.maxZ,
                Blocks.dirt.getDefaultState(),
                Blocks.air.getDefaultState(),
                true);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                this.boundingBox.minX,
                this.boundingBox.minY + 1,
                this.boundingBox.minZ,
                this.boundingBox.maxX,
                Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY),
                this.boundingBox.maxZ,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);

        for (java.util.Iterator<?> __recoveryIterator171_24085 = (this.roomsLinkedToTheRoom).iterator();
                (__recoveryIterator171_24085).hasNext(); ) {
            StructureBoundingBox structureBoundingBoxValue =
                    (StructureBoundingBox) (__recoveryIterator171_24085).next();
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    structureBoundingBoxValue.minX,
                    structureBoundingBoxValue.maxY - 2,
                    structureBoundingBoxValue.minZ,
                    structureBoundingBoxValue.maxX,
                    structureBoundingBoxValue.maxY,
                    structureBoundingBoxValue.maxZ,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
            if (i != 0) {
                break;
            }
        }

        this.randomlyRareFillWithBlocks(
                world,
                structureBoundingBox,
                this.boundingBox.minX,
                this.boundingBox.minY + 4,
                this.boundingBox.minZ,
                this.boundingBox.maxX,
                this.boundingBox.maxY,
                this.boundingBox.maxZ,
                Blocks.air.getDefaultState(),
                false);
        return true;
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        int j = this.getComponentType();
        int i1 = StructureComponent.cR();
        int k = this.boundingBox.getYSize() - 3 - 1;
        int i = i1;
        if (k <= 0) {
            k = 1;
        }

        int l = 0;

        while (l < this.boundingBox.getXSize()) {
            l += random.nextInt(this.boundingBox.getXSize());
            if (l + 3 > this.boundingBox.getXSize() && i != 0) {
                break;
            }

            StructureComponent structureComponentValue =
                    StructureMineshaftPieces.method_6267762401a35bb3f241(
                            structureComponent,
                            list,
                            random,
                            this.boundingBox.minX + l,
                            this.boundingBox.minY + random.nextInt(k) + 1,
                            this.boundingBox.minZ - 1,
                            Facing.NORTH,
                            j);
            if (structureComponentValue != null) {
                StructureBoundingBox structureBoundingBox =
                        structureComponentValue.getBoundingBox();
                this.roomsLinkedToTheRoom.add(
                        new StructureBoundingBox(
                                structureBoundingBox.minX,
                                structureBoundingBox.minY,
                                this.boundingBox.minZ,
                                structureBoundingBox.maxX,
                                structureBoundingBox.maxY,
                                this.boundingBox.minZ + 1));
            }

            l += 4;
            if (i == 0) {
                break;
            }
        }

        l = 0;

        while (l < this.boundingBox.getXSize()) {
            l += random.nextInt(this.boundingBox.getXSize());
            if (l + 3 > this.boundingBox.getXSize() && i != 0) {
                break;
            }

            StructureComponent structureComponentValue2 =
                    StructureMineshaftPieces.method_6267762401a35bb3f241(
                            structureComponent,
                            list,
                            random,
                            this.boundingBox.minX + l,
                            this.boundingBox.minY + random.nextInt(k) + 1,
                            this.boundingBox.maxZ + 1,
                            Facing.SOUTH,
                            j);
            if (structureComponentValue2 != null) {
                StructureBoundingBox structureBoundingBoxValue =
                        structureComponentValue2.getBoundingBox();
                this.roomsLinkedToTheRoom.add(
                        new StructureBoundingBox(
                                structureBoundingBoxValue.minX,
                                structureBoundingBoxValue.minY,
                                this.boundingBox.maxZ - 1,
                                structureBoundingBoxValue.maxX,
                                structureBoundingBoxValue.maxY,
                                this.boundingBox.maxZ));
            }

            l += 4;
            if (i == 0) {
                break;
            }
        }

        l = 0;

        while (l < this.boundingBox.getZSize()) {
            l += random.nextInt(this.boundingBox.getZSize());
            if (l + 3 > this.boundingBox.getZSize() && i != 0) {
                break;
            }

            StructureComponent structureComponentValue3 =
                    StructureMineshaftPieces.method_6267762401a35bb3f241(
                            structureComponent,
                            list,
                            random,
                            this.boundingBox.minX - 1,
                            this.boundingBox.minY + random.nextInt(k) + 1,
                            this.boundingBox.minZ + l,
                            Facing.WEST,
                            j);
            if (structureComponentValue3 != null) {
                StructureBoundingBox structureBoundingBoxValue2 =
                        structureComponentValue3.getBoundingBox();
                this.roomsLinkedToTheRoom.add(
                        new StructureBoundingBox(
                                this.boundingBox.minX,
                                structureBoundingBoxValue2.minY,
                                structureBoundingBoxValue2.minZ,
                                this.boundingBox.minX + 1,
                                structureBoundingBoxValue2.maxY,
                                structureBoundingBoxValue2.maxZ));
            }

            l += 4;
            if (i == 0) {
                break;
            }
        }

        l = 0;

        while (l < this.boundingBox.getZSize()) {
            l += random.nextInt(this.boundingBox.getZSize());
            if (l + 3 > this.boundingBox.getZSize() && i != 0) {
                break;
            }

            StructureComponent structureComponentValue4 =
                    StructureMineshaftPieces.method_6267762401a35bb3f241(
                            structureComponent,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY + random.nextInt(k) + 1,
                            this.boundingBox.minZ + l,
                            Facing.EAST,
                            j);
            if (structureComponentValue4 != null) {
                StructureBoundingBox structureBoundingBoxValue3 =
                        structureComponentValue4.getBoundingBox();
                this.roomsLinkedToTheRoom.add(
                        new StructureBoundingBox(
                                this.boundingBox.maxX - 1,
                                structureBoundingBoxValue3.minY,
                                structureBoundingBoxValue3.minZ,
                                this.boundingBox.maxX,
                                structureBoundingBoxValue3.maxY,
                                structureBoundingBoxValue3.maxZ));
            }

            l += 4;
            if (i == 0) {
                break;
            }
        }
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        int k = StructureComponent.f();
        NBTTagList nbtTagList = compoundTag.getTagList("Entrances", 11);
        int i = k;
        int j = 0;

        while (j < nbtTagList.tagCount()) {
            this.roomsLinkedToTheRoom.add(new StructureBoundingBox(nbtTagList.getIntArrayAt(j)));
            j++;
            if (i != 0) {
                break;
            }
        }
    }


}
