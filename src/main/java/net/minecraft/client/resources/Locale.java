package net.minecraft.client.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Locale {
    Map properties = Maps.newHashMap();

    private static Pattern pattern;
    private boolean unicode;
    private static Splitter splitter;

    public boolean isUnicode() {
        return this.unicode;
    }

    public synchronized void loadLocaleDataFiles(IResourceManager iResourceManager, List list) {
        String text = I18n.getLegacyResourceFlowMarker();
        this.properties.clear();
        String textValue = text;

        label30:
        for (java.util.Iterator<?> __recoveryIterator171_2161 = (list).iterator();
                (__recoveryIterator171_2161).hasNext(); ) {
            String textValue2 = (String) (__recoveryIterator171_2161).next();
            String textValue3 = String.format("lang/%s.lang", textValue2);
            Iterator iterator = iResourceManager.getResourceDomains().iterator();

            while (true) {
                if (iterator.hasNext()) {
                    String textValue4 = (String) iterator.next();

                    try {
                        this.loadLocaleData(
                                iResourceManager.getAllResources(
                                        new ResourceLocation(textValue4, textValue3)));
                    } catch (IOException ioexception) {
                    }

                    if (textValue != null) {
                        continue;
                    }
                }

                if (textValue == null) {
                    break label30;
                }
                break;
            }
        }

        this.method_784e95624d6d70fe7f50();
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    private void method_784e95624d6d70fe7f50() {
        this.unicode = false;
        int i = 0;
        String text = I18n.getLegacyResourceFlowMarker();
        int j = 0;
        String textValue = text;

        label44:
        for (java.util.Iterator<?> __recoveryIterator171_3846 =
                        (this.properties.values()).iterator();
                (__recoveryIterator171_3846).hasNext(); ) {
            String textValue2 = (String) (__recoveryIterator171_3846).next();
            int k = textValue2.length();
            j += k;
            int l = 0;

            while (true) {
                if (l < k) {
                    try {
                        if (textValue2.charAt(l) >= 256) {
                            i++;
                        }
                    } catch (IllegalFormatException illegalformatexception) {
                        throw remix.compat.compiler.JvmThrowTypes.flow(a(illegalformatexception));
                    }

                    l++;
                    if (textValue != null) {
                        continue;
                    }
                }

                if (textValue == null) {
                    break label44;
                }
                break;
            }
        }

        float fx = (float) i / j;

        Locale locale;
        byte b0;
        label30:
        {
            try {
                locale = this;
                if (fx > 0.1) {
                    b0 = 1;
                    break label30;
                }
            } catch (IllegalFormatException illegalformatexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(illegalformatexception1));
            }

            b0 = 0;
        }

        locale.unicode = remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    private String translateKeyPrivate(String text) {
        String textValue = (String) this.properties.get(text);

        try {
            if (textValue == null) {
                return text;
            }
        } catch (IllegalFormatException illegalformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(illegalformatexception));
        }

        return textValue;
    }

    private void loadLocaleData(List list) throws IOException {
        String text = I18n.getLegacyResourceFlowMarker();
        Iterator iterator = list.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            IResource iResource = (IResource) iterator.next();
            InputStream inputstream = iResource.getInputStream();

            try {
                this.loadLocaleData(inputstream);
            } finally {
                IOUtils.closeQuietly(inputstream);
            }

            if (textValue == null) {
                break;
            }
        }
    }

    static {
        net.minecraft.client.resources.Locale.splitter = Splitter.on('=').limit(2);
        net.minecraft.client.resources.Locale.pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    }

    private void loadLocaleData(InputStream inputstream) throws IOException {
        String text = I18n.getLegacyResourceFlowMarker();
        Iterator iterator =
                remix.compat.library.LegacyLibraryAdapters.readLines(inputstream, Charsets.UTF_8)
                        .iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            String textValue2 = (String) iterator.next();

            {
                boolean runGuardedCode2;
                try {
                    if (false)
                        throw (java.io.IOException)
                                null; // Original JVM checked handler; no emitted instructions.
                    runGuardedCode2 = !(textValue2.isEmpty() || textValue2.charAt(0) == '#');
                } catch (IOException ioexception1) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception1));
                }
                if (runGuardedCode2) {

                    String[] astring =
                            (String[])
                                    Iterables.toArray(
                                            net.minecraft.client.resources.Locale.splitter.split(
                                                    textValue2),
                                            String.class);

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

                        String textValue3 = astring[0];
                        String textValue4 =
                                net.minecraft.client.resources.Locale.pattern
                                        .matcher(astring[1])
                                        .replaceAll("%$1s");
                        this.properties.put(textValue3, textValue4);
                    }
                }
            }

            if (textValue == null) {
                break;
            }
        }
    }

    public String formatMessage(String text, Object[] aobject) {
        String textValue = this.translateKeyPrivate(text);

        try {
            return String.format(textValue, aobject);
        } catch (IllegalFormatException illegalformatexception) {
            return "Format error: " + textValue;
        }
    }
}
