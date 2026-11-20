package net.minecraft.world.gen.structure;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.WorldSavedData;

public class MapGenStructureData extends WorldSavedData {
    private static String[] packedTextFragments;

    private CompoundTag tagCompound = new CompoundTag();

    static {
        net.minecraft.world.gen.structure.MapGenStructureData.packedTextFragments = new String[4];
        net.minecraft.world.gen.structure.MapGenStructureData.packedTextFragments[0] = new String("[");
        net.minecraft.world.gen.structure.MapGenStructureData.packedTextFragments[1] = new String(",");
        net.minecraft.world.gen.structure.MapGenStructureData.packedTextFragments[2] = new String("]");
    }

    public static String formatChunkCoords(int i, int j) {
        return packedTextFragments[0] + i + packedTextFragments[1] + j + packedTextFragments[2];
    }

    public void writeToNBT(CompoundTag compoundTag) {
        compoundTag.setTag("Features", this.tagCompound);
    }

    public void readFromNBT(CompoundTag compoundTag) {
        this.tagCompound = compoundTag.getCompoundTag("Features");
    }

    public void writeInstance(CompoundTag compoundTag, int i, int j) {
        this.tagCompound.setTag(formatChunkCoords(i, j), compoundTag);
    }

    public MapGenStructureData(String text) {
        super(text);
    }

    public CompoundTag getTagCompound() {
        return this.tagCompound;
    }
}
