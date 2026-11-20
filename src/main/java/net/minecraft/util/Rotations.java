package net.minecraft.util;

import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;

public class Rotations {
    protected float x;
    protected float z;
    protected float y;

    public float getX() {
        return this.x;
    }

    public Rotations(NBTTagList nbtTagList) {
        this.x = nbtTagList.getFloat(0);
        this.y = nbtTagList.getFloat(1);
        this.z = nbtTagList.getFloat(2);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Rotations)) {
            return false;
        }

        Rotations rotations = (Rotations) object;
        return this.x == rotations.x && this.y == rotations.y && this.z == rotations.z;
    }

    public Rotations(float f, float f1, float f2) {
        this.x = f;
        this.y = f1;
        this.z = f2;
    }

    public float getZ() {
        return this.z;
    }

    public NBTTagList writeToNBT() {
        NBTTagList nbtTagList = new NBTTagList();
        nbtTagList.appendTag(new NBTTagFloat(this.x));
        nbtTagList.appendTag(new NBTTagFloat(this.y));
        nbtTagList.appendTag(new NBTTagFloat(this.z));
        return nbtTagList;
    }

    public float getY() {
        return this.y;
    }
}
