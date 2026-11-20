package net.minecraft.nbt;

import com.google.common.collect.Lists;

import java.util.List;

class JsonToNBTList extends JsonToNBTAny {
    protected List uQ = Lists.newArrayList();

    public JsonToNBTList(String text) {
        this.json = text;
    }

    public NBTBase parse() {
        String[] astring1 = NBTBase.xf();
        NBTTagList nbtTagList = new NBTTagList();
        String[] astring = astring1;

        for (java.util.Iterator<?> __recoveryIterator171_617 = (this.uQ).iterator();
                (__recoveryIterator171_617).hasNext(); ) {
            JsonToNBTAny jsonToNBTAny = (JsonToNBTAny) (__recoveryIterator171_617).next();
            nbtTagList.appendTag(jsonToNBTAny.parse());
            if (astring != null) {
                break;
            }
        }

        return nbtTagList;
    }
}
