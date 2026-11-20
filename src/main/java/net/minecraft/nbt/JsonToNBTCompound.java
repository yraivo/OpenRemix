package net.minecraft.nbt;

import com.google.common.collect.Lists;

import java.util.List;

class JsonToNBTCompound extends JsonToNBTAny {
    protected List uQ = Lists.newArrayList();

    public JsonToNBTCompound(String text) {
        this.json = text;
    }

    public NBTBase parse() {
        String[] astring1 = NBTBase.xf();
        CompoundTag compoundTag = new CompoundTag();
        String[] astring = astring1;

        for (java.util.Iterator<?> __recoveryIterator171_617 = (this.uQ).iterator();
                (__recoveryIterator171_617).hasNext(); ) {
            JsonToNBTAny jsonToNBTAny = (JsonToNBTAny) (__recoveryIterator171_617).next();
            compoundTag.setTag(jsonToNBTAny.json, jsonToNBTAny.parse());
            if (astring != null) {
                break;
            }
        }

        return compoundTag;
    }
}
