package remix.client;

import remix.client.lifecycle.ServiceComponentState;

import java.io.File;
import java.util.Random;

public abstract class AbstractGameDirectoryProvider implements ServiceComponentState {
    private static boolean legacyDirectoryFlag;

    private String legacySampleDirectoryPath;
    private static String f;
    private Random random;
    private File file;

    // region String decoding support

    // endregion

    public static void setLegacyDirectoryFlag(boolean flag) {
        remix.client.AbstractGameDirectoryProvider.legacyDirectoryFlag = flag;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        if (!isLegacyDirectoryFlagClear()) {
            setLegacyDirectoryFlag(true);
        }
        f = "mibAIS6KHFbi";
    }

    public File getOrCreateConfigDirectory() {
        // Keep the explicitly supplied game-directory location independent of authorization state.
        if (this.file != null && !this.file.exists()) {
            this.file.mkdirs();
            this.file.mkdir();
        }

        return this.file;
    }

    private String sampleLegacyDirectoryEntryName() {
        File file1 = new File(this.legacySampleDirectoryPath);
        String[] astring = file1.list();
        File file2 = file1;
        if (astring != null) {
            file2 = new File(astring[this.random.nextInt(astring.length)]);
        }

        return file2.getName();
    }

    public static boolean isLegacyDirectoryFlagSet() {
        return remix.client.AbstractGameDirectoryProvider.legacyDirectoryFlag;
    }

    public static boolean isLegacyDirectoryFlagClear() {
        boolean flag = isLegacyDirectoryFlagSet();
        return !flag;
    }

    public AbstractGameDirectoryProvider(File file1) {
        this.file = file1;
    }
}
