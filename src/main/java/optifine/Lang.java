package optifine;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.Charsets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class Lang {
    private static Pattern pattern;

    private static Splitter splitter;

    public static String getFancy() {
        return I18n.format("options.graphics.fancy", new Object[0]);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public static String get(String text, String textValue) {
        String textValue2 = I18n.format(text, new Object[0]);
        return textValue2 != null && !textValue2.equals(text) ? textValue2 : textValue;
    }

    public static String getFast() {
        return I18n.format("options.graphics.fast", new Object[0]);
    }

    public static String getOff() {
        return I18n.format("options.off", new Object[0]);
    }

    public static String getOn() {
        return I18n.format("options.on", new Object[0]);
    }

    public static String get(String text) {
        return I18n.format(text, new Object[0]);
    }

    private static void loadResources(IResourcePack iResourcePack, String[] astring, Map map) {
        int i = ReflectorMethod.cR();

        try {
            int j = 0;

            while (j < astring.length) {
                String text = astring[j];
                ResourceLocation resourceLocation = new ResourceLocation(text);
                if (iResourcePack.resourceExists(resourceLocation)) {
                    InputStream inputstream = iResourcePack.getInputStream(resourceLocation);

                    try {
                        if (inputstream != null) {
                            loadLocaleData(inputstream, map);
                        }
                    } catch (IOException ioexception) {
                        throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
                    }
                }

                j++;
                if (i == 0) {
                    break;
                }
            }
        } catch (IOException ioexception1) {
            ioexception1.printStackTrace();
        }
    }

    public static void resourcesReloaded() {
        int k = ReflectorMethod.cR();
        Map map = I18n.getLocaleProperties();
        int i = k;
        ArrayList arrayList = new ArrayList();
        String text = "optifine/lang/";
        String textValue = "en_US";
        String textValue2 = ".lang";
        arrayList.add(text + textValue + textValue2);
        if (!OptiFineConfig.getGameSettings().language.equals(textValue)) {
            arrayList.add(text + OptiFineConfig.getGameSettings().language + textValue2);
        }

        String[] astring = ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]));
        loadResources(OptiFineConfig.getDefaultResourcePack(), astring, map);
        IResourcePack[] iResourcePackArray = OptiFineConfig.getResourcePacks();
        int j = 0;

        while (j < iResourcePackArray.length) {
            IResourcePack iResourcePack = iResourcePackArray[j];
            loadResources(iResourcePack, astring, map);
            j++;
            if (i == 0) {
                break;
            }
        }
    }

    static {
        optifine.Lang.splitter = Splitter.on('=').limit(2);
        optifine.Lang.pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    }

    public static String getDefault() {
        return I18n.format("generator.default", new Object[0]);
    }

    public static void loadLocaleData(InputStream inputstream, Map map) throws IOException {
        int j = ReflectorMethod.f();
        Iterator iterator =
                remix.compat.library.LegacyLibraryAdapters.readLines(inputstream, Charsets.UTF_8)
                        .iterator();
        int i = j;

        while (iterator.hasNext()) {
            String text = (String) iterator.next();

            {
                boolean runGuardedCode2;
                try {
                    if (false)
                        throw (java.io.IOException)
                                null; // Original JVM checked handler; no emitted instructions.
                    runGuardedCode2 = !(text.isEmpty() || text.charAt(0) == '#');
                } catch (IOException ioexception1) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception1));
                }
                if (runGuardedCode2) {

                    String[] astring =
                            (String[])
                                    Iterables.toArray(
                                            optifine.Lang.splitter.split(text), String.class);

                    boolean runGuardedCode;
                    try {
                        if (false)
                            throw (java.io.IOException)
                                    null; // Original JVM checked handler; no emitted instructions.
                        runGuardedCode = !(astring == null || astring.length != 2);
                    } catch (IOException ioexception) {
                        throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
                    }
                    if (runGuardedCode) {

                        String textValue = astring[0];
                        String textValue2 =
                                optifine.Lang.pattern.matcher(astring[1]).replaceAll("%$1s");
                        map.put(textValue, textValue2);
                    }
                }
            }

            if (i != 0) {
                break;
            }
        }
    }
}
