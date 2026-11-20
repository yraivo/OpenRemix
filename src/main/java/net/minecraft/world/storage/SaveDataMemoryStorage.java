package net.minecraft.world.storage;

import net.minecraft.world.WorldSavedData;

public class SaveDataMemoryStorage extends MapStorage {
    public void setData(String text, WorldSavedData worldSavedData) {
        this.loadedDataMap.put(text, worldSavedData);
    }

    public int getUniqueDataId(String text) {
        return 0;
    }

    public WorldSavedData loadData(Class type, String text) {
        return (net.minecraft.world.WorldSavedData) this.loadedDataMap.get(text);
    }

    public SaveDataMemoryStorage() {
        super((net.minecraft.world.storage.ISaveHandler) null);
    }

    public void saveAllData() {}
}
