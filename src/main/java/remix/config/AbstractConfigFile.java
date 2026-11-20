package remix.config;

import java.io.File;

public abstract class AbstractConfigFile implements remix.config.ConfigFile {
    protected File file;
    private static int[] legacyConfigLoadMarker;

    public void save() {
        if (!this.file.exists()) {
            this.createFileIfNotExists(this.file);
        }
    }

    public static void setLegacyConfigLoadMarker(int[] aint) {
        remix.config.AbstractConfigFile.legacyConfigLoadMarker = aint;
    }

    public AbstractConfigFile(File file1) {
        this.file = file1;
    }

    static {
        if (getLegacyConfigLoadMarker() != null) {
            setLegacyConfigLoadMarker(new int[4]);
        }
    }

    public static int[] getLegacyConfigLoadMarker() {
        return remix.config.AbstractConfigFile.legacyConfigLoadMarker;
    }

    public File getFile() {
        return this.file;
    }
}
