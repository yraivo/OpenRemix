package net.minecraft.world.storage;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.util.IProgressUpdate;

import java.util.List;

public interface ISaveFormat {
    void renameWorld(String text, String textValue);

    WorldInfo getWorldInfo(String text);

    ISaveHandler getSaveLoader(String text, boolean flag);

    boolean b(String text);

    List getSaveList() throws AnvilConverterException;

    boolean canLoadWorld(String text);

    void flushCache();

    boolean ag(String text);

    String getName();

    boolean isOldMapFormat(String text);

    boolean convertMapFormat(String text, IProgressUpdate iProgressUpdate);

    boolean deleteWorldDirectory(String text);
}
