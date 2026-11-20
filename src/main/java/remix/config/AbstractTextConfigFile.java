package remix.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public abstract class AbstractTextConfigFile extends remix.config.AbstractConfigFile {
    private static String[] packedTextFragments;
    private Charset charset;
    protected static final String LINE_SEPARATOR;
    protected static final String legacyInitialTextFragment;

    public void save() {
        boolean writeOnlyFirstRecord = AbstractConfigFile.getLegacyConfigLoadMarker() != null;
        super.save();
        try (BufferedWriter writer = Files.newBufferedWriter(this.file.toPath())) {
            TextConfigLineList records = new TextConfigLineList();
            this.saveTo(records);
            for (TextConfigLineBuilder record : records) {
                writer.write(record.toString());
                if (writeOnlyFirstRecord) {
                    break;
                }
            }
        } catch (IOException failure) {
            failure.printStackTrace();
        }
    }

    public void load() {
        boolean loadOnlyFirstRecord = AbstractConfigFile.getLegacyConfigLoadMarker() != null;
        if (!this.file.exists()) {
            this.createFileIfNotExists(this.file);
            this.save();
            if (!loadOnlyFirstRecord) {
                return;
            }
        }
        try {
            TextConfigLineList records = new TextConfigLineList(Files.readAllLines(this.file.toPath()));
            for (TextConfigLineBuilder record : records) {
                this.loadFrom(record);
                if (loadOnlyFirstRecord) {
                    break;
                }
            }
        } catch (IOException failure) {
            failure.printStackTrace();
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.config.AbstractTextConfigFile.packedTextFragments[0] = new String(":");
    }

    // endregion

    // region String decoding support

    // endregion

    public AbstractTextConfigFile(File file1, Charset charset) {
        super(file1);
        this.charset = charset;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        legacyInitialTextFragment = packedTextFragments[0];
        LINE_SEPARATOR = System.lineSeparator();
    }

}
