package remix.config;

import java.io.File;
import java.io.IOException;

public interface ConfigFile {
    void loadFrom(Object f) throws IOException;

    void load();

    void saveTo(java.util.List list) throws IOException;

    void save();

    default void createFileIfNotExists(File file1) {
        try {
            file1.getParentFile().mkdirs();
            file1.createNewFile();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }
}
