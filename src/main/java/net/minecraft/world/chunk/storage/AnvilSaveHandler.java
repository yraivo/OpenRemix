package net.minecraft.world.chunk.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.ThreadedFileIOBase;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;

public class AnvilSaveHandler extends SaveHandler {

    public IChunkLoader getChunkLoader(WorldProvider worldProvider) {
        File file1 = this.getWorldDirectory();
        if (worldProvider instanceof WorldProviderHell) {
            File file3 = new File(file1, "DIM-1");
            file3.mkdirs();
            return new AnvilChunkLoader(file3);
        } else if (worldProvider instanceof WorldProviderEnd) {
            File file2 = new File(file1, "DIM1");
            file2.mkdirs();
            return new AnvilChunkLoader(file2);
        } else {
            return new AnvilChunkLoader(file1);
        }
    }

    public AnvilSaveHandler(File file1, String text, boolean flag) {
        super(file1, text, flag);
    }

    public void saveWorldInfoWithPlayer(WorldInfo worldInfo, CompoundTag compoundTag) {
        worldInfo.setSaveVersion(19133);
        super.saveWorldInfoWithPlayer(worldInfo, compoundTag);
    }


    public void flush() {
        try {
            ThreadedFileIOBase.getThreadedIOInstance().waitForFinish();
        } catch (InterruptedException interruptedexception) {
            interruptedexception.printStackTrace();
        }

        RegionFileCache.clearRegionFileReferences();
    }
}
