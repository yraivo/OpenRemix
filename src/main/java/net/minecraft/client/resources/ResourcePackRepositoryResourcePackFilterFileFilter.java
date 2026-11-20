package net.minecraft.client.resources;

import java.io.File;
import java.io.FileFilter;

public final class ResourcePackRepositoryResourcePackFilterFileFilter implements FileFilter {

    @Override
    public boolean accept(File file1) {
        int i = file1.isFile() && file1.getName().endsWith(".zip") ? 1 : 0;
        int j = file1.isDirectory() && new File(file1, "pack.mcmeta").isFile() ? 1 : 0;
        return i != 0 || j != 0;
    }


}
