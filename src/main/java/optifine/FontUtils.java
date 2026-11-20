package optifine;

import net.minecraft.util.ResourceLocation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class FontUtils {

    public static float readFloat(Properties properties, String text, float f) {
        String property = properties.getProperty(text);
        if (property == null) {
            return f;
        } else {
            float f1 = OptiFineConfig.parseFloat(property, Float.MIN_VALUE);
            if (f1 == Float.MIN_VALUE) {
                OptiFineConfig.warn("Invalid value for " + text + ": " + property);
                return f;
            } else {
                return f1;
            }
        }
    }

    private static FileNotFoundException method_fd3f0eb1dd2c178d63ce(
            FileNotFoundException filenotfoundexception) {
        return filenotfoundexception;
    }

    public static void readCustomCharWidths(Properties properties, float[] afloat) {
        int k = ReflectorMethod.f();
        Set set = properties.keySet();
        int i = k;

        for (java.util.Iterator<?> __recoveryIterator171_2759 = (set).iterator();
                (__recoveryIterator171_2759).hasNext(); ) {
            String text = (String) (__recoveryIterator171_2759).next();
            String textValue = "width.";
            if (text.startsWith(textValue)) {
                String textValue2 = text.substring(textValue.length());
                int j = OptiFineConfig.parseInt(textValue2, -1);
                if (j >= 0 && j < afloat.length) {
                    String property = properties.getProperty(text);
                    float f = OptiFineConfig.parseFloat(property, -1.0F);
                    if (f >= 0.0F) {
                        afloat[j] = f;
                    }
                }
            }

            if (i != 0) {
                break;
            }
        }
    }

    public static Properties readFontProperties(ResourceLocation resourceLocation) {
        String text = resourceLocation.getResourcePath();
        Properties properties = new Properties();
        String textValue = ".png";

        try {
            if (false)
                throw (java.io.FileNotFoundException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (!text.endsWith(textValue)) {
                return properties;
            }
        } catch (FileNotFoundException filenotfoundexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_fd3f0eb1dd2c178d63ce(filenotfoundexception1));
        }

        String textValue2 = text.substring(0, text.length() - textValue.length()) + ".properties";

        try {
            ResourceLocation resourceLocationValue =
                    new ResourceLocation(resourceLocation.getResourceDomain(), textValue2);
            InputStream inputstream =
                    OptiFineConfig.getResourceStream(
                            OptiFineConfig.getResourceManager(), resourceLocationValue);
            if (inputstream == null) {
                return properties;
            }

            OptiFineConfig.log("Loading " + textValue2);
            properties.load(inputstream);
        } catch (FileNotFoundException filenotfoundexception) {
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

        return properties;
    }


    public static ResourceLocation getHdFontLocation(ResourceLocation resourceLocation) {
        if (!OptiFineConfig.isCustomFonts()) {
            return resourceLocation;
        }

        if (resourceLocation == null) {
            return resourceLocation;
        }

        String text = resourceLocation.getResourcePath();
        String textValue = "textures/";
        String textValue2 = "mcpatcher/";
        if (!text.startsWith(textValue)) {
            return resourceLocation;
        }

        text = text.substring(textValue.length());
        text = textValue2 + text;
        ResourceLocation resourceLocationValue =
                new ResourceLocation(resourceLocation.getResourceDomain(), text);
        return OptiFineConfig.hasResource(
                        OptiFineConfig.getResourceManager(), resourceLocationValue)
                ? resourceLocationValue
                : resourceLocation;
    }
}
