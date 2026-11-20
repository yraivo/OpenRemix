package remix.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import remix.compat.legacy.script.JsonObjectListTypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJsonConfigFile extends remix.config.AbstractConfigFile {
    protected static final Logger CONFIG_LOGGER = LogManager.getLogger();
    protected static final Gson CONFIG_GSON =
            new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    protected static final Type JSON_OBJECT_TYPE = JsonObject.class;
    protected static final Type JSON_RECORD_LIST_TYPE = new JsonObjectListTypeToken().getType();

    public void save() {
        super.save();
        try (FileWriter writer = new FileWriter(this.file)) {
            List<JsonObject> records = new ArrayList<>();
            this.saveTo(records);
            remix.config.AbstractJsonConfigFile.CONFIG_GSON.toJson(records, writer);
        } catch (IOException failure) {
            failure.printStackTrace();
        }
    }

    public AbstractJsonConfigFile(File file1) {
        super(file1);
    }

    public Gson getConfigGson() {
        return remix.config.AbstractJsonConfigFile.CONFIG_GSON;
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

        final JsonElement root;
        try (FileReader reader = new FileReader(this.file)) {
            root = CONFIG_GSON.fromJson(reader, JsonElement.class);
        } catch (IOException | JsonParseException failure) {
            CONFIG_LOGGER.warn(
                    "Could not read config {}: {}", this.file.getName(), failure.toString());
            return;
        }
        if (root == null || root.isJsonNull()) {
            return;
        }
        if (root.isJsonObject()) {
            this.applyRecord(root, "root");
        } else if (root.isJsonArray()) {
            int index = 0;
            for (JsonElement record : root.getAsJsonArray()) {
                this.applyRecord(record, "record[" + index++ + "]");
                if (loadOnlyFirstRecord) {
                    break;
                }
            }
        } else {
            CONFIG_LOGGER.warn(
                    "Ignoring config {}: expected an object or array", this.file.getName());
        }
    }

    private void applyRecord(JsonElement record, String location) {
        if (record == null || !record.isJsonObject()) {
            CONFIG_LOGGER.warn(
                    "Ignoring invalid config entry {} in {}", location, this.file.getName());
            return;
        }
        try {
            this.loadFrom(record.getAsJsonObject());
        } catch (IOException | RuntimeException failure) {
            CONFIG_LOGGER.warn(
                    "Could not apply config {} in {}: {}",
                    location,
                    this.file.getName(),
                    failure.toString());
        }
    }
}
