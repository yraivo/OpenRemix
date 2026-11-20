package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.world.WorldSavedData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

public class MapStorage {

    private ISaveHandler saveHandler;
    private List loadedDataList;

    private Map idCounts;

    protected Map loadedDataMap = Maps.newHashMap();

    public void setData(String text, WorldSavedData worldSavedData) {
        try {
            if (this.loadedDataMap.containsKey(text)) {
                this.loadedDataList.remove(this.loadedDataMap.remove(text));
            }
        } catch (RuntimeException runtimeexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(runtimeexception));
        }

        this.loadedDataMap.put(text, worldSavedData);
        this.loadedDataList.add(worldSavedData);
    }

    public MapStorage(ISaveHandler bNOValue) {
        this.loadedDataList = Lists.newArrayList();
        this.idCounts = Maps.newHashMap();
        this.saveHandler = bNOValue;
        this.loadIdCounts();
    }

    private static Exception a(Exception exception) {
        return exception;
    }


    public int getUniqueDataId(String text) {
        Entity[] entityArray;
        Short oshort;
        {
            Entity[] entityArrayValue = WorldInfo.YP();
            oshort = (Short) this.idCounts.get(text);
            entityArray = entityArrayValue;
            if (oshort == null) {
                oshort = (short) 0;

            } else {

                oshort = (short) (oshort + 1);
            }
        }

        try {
            this.idCounts.put(text, oshort);
            if (this.saveHandler == null) {
                return oshort;
            }
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception));
        }

        try {
            File file1 = this.saveHandler.getMapFileFromName("idcounts");
            if (file1 != null) {
                CompoundTag compoundTag = new CompoundTag();

                for (java.util.Iterator<?> __recoveryIterator171_6892 =
                                (this.idCounts.keySet()).iterator();
                        (__recoveryIterator171_6892).hasNext(); ) {
                    String textValue = (String) (__recoveryIterator171_6892).next();
                    short short1 = (Short) this.idCounts.get(textValue);
                    compoundTag.setShort(textValue, short1);
                }

                DataOutputStream dataoutputstream =
                        new DataOutputStream(new FileOutputStream(file1));
                CompressedStreamTools.write(compoundTag, dataoutputstream);
                dataoutputstream.close();
            }
        } catch (Exception exception1) {
            exception1.printStackTrace();
        }

        return oshort;
    }

    public void saveAllData() {
        WorldInfo.YP();

        int i = 0;

        while (i < this.loadedDataList.size()) {
            WorldSavedData worldSavedData = (WorldSavedData) this.loadedDataList.get(i);

            try {
                if (worldSavedData.isDirty()) {
                    this.saveData(worldSavedData);
                    worldSavedData.setDirty(false);
                }
            } catch (RuntimeException runtimeexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(runtimeexception));
            }

            i++;
        }
    }

    private void loadIdCounts() {
        WorldInfo.YP();

        try {
            this.idCounts.clear();
            if (this.saveHandler == null) {
                return;
            }

            File file1 = this.saveHandler.getMapFileFromName("idcounts");

            try {
                if (file1 == null || !file1.exists()) {
                    return;
                }
            } catch (Exception exception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(exception));
            }

            DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
            CompoundTag compoundTag = CompressedStreamTools.read(datainputstream);
            datainputstream.close();

            for (java.util.Iterator<?> __recoveryIterator171_9899 =
                            (compoundTag.getKeySet()).iterator();
                    (__recoveryIterator171_9899).hasNext(); ) {
                String text = (String) (__recoveryIterator171_9899).next();
                NBTBase tag = compoundTag.getTag(text);
                if (tag instanceof NBTTagShort) {
                    NBTTagShort nbtTagShort = (NBTTagShort) tag;
                    short short1 = nbtTagShort.getShort();
                    this.idCounts.put(text, short1);
                }
            }
        } catch (Exception exception1) {
            exception1.printStackTrace();
        }
    }

    private void saveData(WorldSavedData worldSavedData) {
        if (this.saveHandler != null) {
            try {
                File file1 = this.saveHandler.getMapFileFromName(worldSavedData.mapName);
                if (file1 != null) {
                    CompoundTag compoundTag = new CompoundTag();
                    worldSavedData.writeToNBT(compoundTag);
                    CompoundTag compoundTagValue = new CompoundTag();
                    compoundTagValue.setTag("data", compoundTag);
                    FileOutputStream fileoutputstream = new FileOutputStream(file1);
                    CompressedStreamTools.writeCompressed(compoundTagValue, fileoutputstream);
                    fileoutputstream.close();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public WorldSavedData loadData(Class oclass, String text) {
        WorldSavedData worldSavedData = (WorldSavedData) this.loadedDataMap.get(text);

        try {
            if (worldSavedData != null) {
                return worldSavedData;
            }
        } catch (Exception exception2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception2));
        }

        if (this.saveHandler != null) {
            try {
                {
                    File file1 = this.saveHandler.getMapFileFromName(text);

                    boolean runGuardedCode;
                    try {
                        runGuardedCode = !(file1 == null || !file1.exists());
                    } catch (Exception exception3) {
                        throw remix.compat.compiler.JvmThrowTypes.flow(a(exception3));
                    }
                    if (runGuardedCode) {

                        try {
                            worldSavedData =
                                    (WorldSavedData)
                                            oclass.getConstructor(String.class).newInstance(text);
                        } catch (Exception exception1) {
                            throw new RuntimeException(
                                    "Failed to instantiate " + oclass.toString(), exception1);
                        }

                        FileInputStream fileinputstream = new FileInputStream(file1);
                        CompoundTag compoundTag =
                                CompressedStreamTools.readCompressed(fileinputstream);
                        fileinputstream.close();
                        worldSavedData.readFromNBT(compoundTag.getCompoundTag("data"));
                    }
                }
            } catch (Exception exception4) {
                exception4.printStackTrace();
            }
        }

        try {
            if (worldSavedData != null) {
                this.loadedDataMap.put(text, worldSavedData);
                this.loadedDataList.add(worldSavedData);
            }

            return worldSavedData;
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception));
        }
    }
}
