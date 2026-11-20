package net.minecraft.world;

import net.minecraft.nbt.CompoundTag;

public abstract class WorldSavedData {
    public String mapName;
    private boolean dirty;

    public boolean isDirty() {
        return this.dirty;
    }

    public WorldSavedData(String text) {
        this.mapName = text;
    }

    public void setDirty(boolean dirtyValue) {
        this.dirty = dirtyValue;
    }

    public void markDirty() {
        this.setDirty(true);
    }

    public abstract void readFromNBT(CompoundTag compoundTag);

    public abstract void writeToNBT(CompoundTag compoundTag);
}
