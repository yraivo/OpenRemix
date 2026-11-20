package net.minecraft.world.chunk.storage;

import java.io.File;
import java.io.FilenameFilter;

class McrRegionFilenameFilter implements FilenameFilter {
    AnvilSaveConverter bIU;

    private static String b;

    @Override
    public boolean accept(File file1, String text) {
        return text.endsWith(b);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        b = ".mcr";
    }

    McrRegionFilenameFilter(AnvilSaveConverter bIUValue) {
        this.bIU = bIUValue;
    }
}
