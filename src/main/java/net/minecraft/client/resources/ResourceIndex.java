package net.minecraft.client.resources;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.minecraft.util.JsonUtils;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Map.Entry;

public class ResourceIndex {
    private Map resourceMap;

    private static Logger logger;
    private static String[] packedTextFragments;

    public Map getResourceMap() {
        return this.resourceMap;
    }

    public ResourceIndex(File file1, String text) {
        super();
        String initialFlowMarker = I18n.getLegacyResourceFlowMarker();

        String textValue = initialFlowMarker;

        this.resourceMap = Maps.newHashMap();
        String textValue2 = textValue;
        if (text != null) {
            File file2 = new File(file1, "objects");
            File file3 = new File(file1, "indexes/" + text + ".json");
            BufferedReader bufferedreader = null;

            try {
                bufferedreader = Files.newReader(file3, Charsets.UTF_8);
                JsonObject jsonobject = new JsonParser().parse(bufferedreader).getAsJsonObject();
                JsonObject jsonobject1 =
                        JsonUtils.getJsonObject(jsonobject, "objects", (JsonObject) null);
                if (jsonobject1 != null) {
                    for (Entry entry : jsonobject1.entrySet()) {
                        JsonObject jsonobject2 = (JsonObject) entry.getValue();
                        String key = (String) entry.getKey();
                        String[] astring = key.split(packedTextFragments[0], 2);

                        label75:
                        {
                            try {
                                if (astring.length == 1) {
                                    textValue = astring[0];
                                    break label75;
                                }
                            } catch (JsonParseException jsonparseexception) {
                                throw method_a7757c6047e9e5ecea97(jsonparseexception);
                            }

                            textValue = astring[0] + packedTextFragments[1] + astring[1];
                        }

                        String textValue3 = textValue;
                        String textValue4 = JsonUtils.getString(jsonobject2, "hash");
                        File file4 =
                                new File(
                                        file2,
                                        textValue4.substring(0, 2)
                                                + packedTextFragments[2]
                                                + textValue4);
                        this.resourceMap.put(textValue3, file4);
                        if (textValue2 == null) {
                            break;
                        }
                    }
                }
            } catch (JsonParseException jsonparseexception1) {
                net.minecraft.client.resources.ResourceIndex.logger.error(
                        "Unable to parse resource index file: " + file3);
            } catch (FileNotFoundException filenotfoundexception) {
                net.minecraft.client.resources.ResourceIndex.logger.error(
                        "Can't find the resource index file: " + file3);
            } finally {
                IOUtils.closeQuietly(bufferedreader);
            }
        }
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }

    static {
        net.minecraft.client.resources.ResourceIndex.packedTextFragments = new String[5];
        net.minecraft.client.resources.ResourceIndex.packedTextFragments[0] = new String("/");
        net.minecraft.client.resources.ResourceIndex.packedTextFragments[1] = new String(":");
        net.minecraft.client.resources.ResourceIndex.packedTextFragments[2] = new String("/");
        net.minecraft.client.resources.ResourceIndex.logger = LogManager.getLogger();
    }
}
