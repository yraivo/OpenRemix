package net.minecraft.client.resources;

import java.io.File;
import java.io.FileNotFoundException;

public class ResourcePackFileNotFoundException extends FileNotFoundException {
    private static String a;

    public ResourcePackFileNotFoundException(File file1, String text) {
        super(String.format(a, text, file1));
    }

    static {
        a = "'%s' in ResourcePack '%s'";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
