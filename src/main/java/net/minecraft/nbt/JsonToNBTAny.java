package net.minecraft.nbt;

abstract class JsonToNBTAny {
    protected String json;

    public abstract NBTBase parse();
}
