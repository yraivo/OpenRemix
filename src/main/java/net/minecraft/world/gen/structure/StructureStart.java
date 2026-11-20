package net.minecraft.world.gen.structure;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public abstract class StructureStart {

    protected LinkedList components = new LinkedList();

    private int chunkPosX;
    protected StructureBoundingBox boundingBox;
    private int chunkPosZ;

    public LinkedList getComponents() {
        return this.components;
    }

    public void method_21c9d6746214a41e69d7(ChunkCoordIntPair chunkCoordIntPair) {}

    public StructureBoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    public void readFromNBT(CompoundTag compoundTag) {}

    public int getChunkPosX() {
        return this.chunkPosX;
    }

    public void readStructureComponentsFromNBT(World world, CompoundTag compoundTag) {
        this.chunkPosX = compoundTag.getInteger("ChunkX");
        int k = StructureComponent.cR();
        this.chunkPosZ = compoundTag.getInteger("ChunkZ");
        int i = k;
        if (compoundTag.hasKey("BB")) {
            this.boundingBox = new StructureBoundingBox(compoundTag.getIntArray("BB"));
        }

        NBTTagList nbtTagList = compoundTag.getTagList("Children", 10);
        int j = 0;

        while (j < nbtTagList.tagCount()) {
            this.components.add(
                    MapGenStructureIO.getStructureComponent(
                            nbtTagList.getCompoundTagAt(j), world));
            j++;
            if (i == 0) {
                break;
            }
        }

        this.readFromNBT(compoundTag);
    }

    public boolean isSizeableStructure() {
        return true;
    }

    public StructureStart(int chunkPosXValue, int j) {
        this.chunkPosX = chunkPosXValue;
        this.chunkPosZ = j;
    }

    public void writeToNBT(CompoundTag compoundTag) {}

    public StructureStart() {}

    public void generateStructure(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int j = StructureComponent.f();
        Iterator iterator = this.components.iterator();
        int i = j;

        while (iterator.hasNext()) {
            StructureComponent structureComponent = (StructureComponent) iterator.next();
            if (structureComponent.getBoundingBox().intersectsWith(structureBoundingBox)
                    && !structureComponent.addComponentParts(world, random, structureBoundingBox)) {
                iterator.remove();
            }

            if (i != 0) {
                break;
            }
        }
    }

    protected void markAvailableHeight(World world, Random random, int i) {
        int j1 = StructureComponent.f();
        int k = 63 - i;
        int j = j1;
        int l = this.boundingBox.getYSize() + 1;
        if (l < k) {
            l += random.nextInt(k - l);
        }

        int i1 = l - this.boundingBox.maxY;
        this.boundingBox.offset(0, i1, 0);

        for (java.util.Iterator<?> __recoveryIterator171_27281 = (this.components).iterator();
                (__recoveryIterator171_27281).hasNext(); ) {
            StructureComponent structureComponent =
                    (StructureComponent) (__recoveryIterator171_27281).next();
            structureComponent.getBoundingBox().offset(0, i1, 0);
            if (j != 0) {
                break;
            }
        }
    }


    public boolean method_8f6348b78777a95640a1(ChunkCoordIntPair chunkCoordIntPair) {
        return true;
    }

    public CompoundTag writeStructureComponentsToNBT(int i, int j) {
        CompoundTag compoundTag = new CompoundTag();
        int l = StructureComponent.cR();
        compoundTag.setString("id", MapGenStructureIO.getStructureStartName(this));
        compoundTag.setInteger("ChunkX", i);
        compoundTag.setInteger("ChunkZ", j);
        int k = l;
        compoundTag.setTag("BB", this.boundingBox.toNBTTagIntArray());
        NBTTagList nbtTagList = new NBTTagList();

        for (java.util.Iterator<?> __recoveryIterator171_31980 = (this.components).iterator();
                (__recoveryIterator171_31980).hasNext(); ) {
            StructureComponent structureComponent =
                    (StructureComponent) (__recoveryIterator171_31980).next();
            nbtTagList.appendTag(structureComponent.createStructureBaseNBT());
            if (k == 0) {
                break;
            }
        }

        compoundTag.setTag("Children", nbtTagList);
        this.writeToNBT(compoundTag);
        return compoundTag;
    }

    protected void cj() {
        int j = StructureComponent.f();
        this.boundingBox = StructureBoundingBox.getNewBoundingBox();
        Iterator iterator = this.components.iterator();
        int i = j;

        while (iterator.hasNext()) {
            StructureComponent structureComponent = (StructureComponent) iterator.next();
            this.boundingBox.expandTo(structureComponent.getBoundingBox());
            if (i != 0) {
                break;
            }
        }
    }

    public int getChunkPosZ() {
        return this.chunkPosZ;
    }

    protected void setRandomHeight(World world, Random random, int i, int j) {
        int k;
        int i1;
        {
            int k1 = StructureComponent.cR();
            int l = j - i + 1 - this.boundingBox.getYSize();

            k = k1;
            if (l > 1) {
                i1 = i + random.nextInt(l);
                if (!(k != 0)) {

                    i1 = i;
                }
            } else {

                i1 = i;
            }
        }

        int j1 = i1 - this.boundingBox.minY;
        this.boundingBox.offset(0, j1, 0);

        for (java.util.Iterator<?> __recoveryIterator171_33726 = (this.components).iterator();
                (__recoveryIterator171_33726).hasNext(); ) {
            StructureComponent structureComponent =
                    (StructureComponent) (__recoveryIterator171_33726).next();
            structureComponent.getBoundingBox().offset(0, j1, 0);
            if (k == 0) {
                break;
            }
        }
    }
}
