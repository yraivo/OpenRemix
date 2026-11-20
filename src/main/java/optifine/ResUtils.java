package optifine;

import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ResUtils {
    private static String[] packedTextFragments;

    public static String[] collectFiles(
            IResourcePack iResourcePack, String[] astring, String[] astring1) {
        return collectFiles(iResourcePack, astring, astring1, (String[]) null);
    }

    public static String[] collectFiles(
            IResourcePack iResourcePack, String text, String textValue, String[] astring) {
        return collectFiles(
                iResourcePack, new String[] {text}, new String[] {textValue}, astring);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public static String[] collectFiles(String text, String textValue) {
        return collectFiles(new String[] {text}, new String[] {textValue});
    }

    public static String[] collectFiles(String[] astring, String[] astring1) {
        LinkedHashSet linkedhashset = new LinkedHashSet();
        int k = ReflectorMethod.cR();
        IResourcePack[] iResourcePackArray = OptiFineConfig.getResourcePacks();
        int j = 0;
        int i = k;

        while (j < iResourcePackArray.length) {
            IResourcePack iResourcePack = iResourcePackArray[j];
            String[] astring2 =
                    collectFiles(iResourcePack, astring, astring1, (String[]) null);
            linkedhashset.addAll(Arrays.asList(astring2));
            j++;
            if (i == 0) {
                break;
            }
        }

        return ((java.lang.String[]) linkedhashset.toArray(new String[linkedhashset.size()]));
    }

    private static String[] collectFilesFixed(
            IResourcePack iResourcePack, String[] astring) {
        int i = ReflectorMethod.f();
        if (astring == null) {
            return new String[0];
        }

        ArrayList arrayList = new ArrayList();
        int j = 0;

        while (j < astring.length) {
            String text = astring[j];
            ResourceLocation resourceLocation = new ResourceLocation(text);
            if (iResourcePack.resourceExists(resourceLocation)) {
                arrayList.add(text);
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    private static String[] collectFilesZIP(
            File file1, String[] astring, String[] astring1) {
        int j = ReflectorMethod.f();
        ArrayList arrayList = new ArrayList();
        String text = "assets/minecraft/";
        int i = j;

        try {
            ZipFile zipfile = new ZipFile(file1);
            Enumeration enumeration = zipfile.entries();

            while (enumeration.hasMoreElements()) {
                ZipEntry zipentry = (ZipEntry) enumeration.nextElement();
                String name = zipentry.getName();
                if (name.startsWith(text)) {
                    name = name.substring(text.length());
                    if (StrUtils.startsWith(name, astring)
                            && StrUtils.endsWith(name, astring1)) {
                        arrayList.add(name);
                    }
                }

                if (i != 0) {
                    break;
                }
            }

            zipfile.close();
            return ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]));
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            return new String[0];
        }
    }

    public static String[] collectFiles(
            IResourcePack iResourcePack, String[] astring, String[] astring1, String[] astring2) {
        if (iResourcePack instanceof DefaultResourcePack) {
            return collectFilesFixed(iResourcePack, astring2);
        }

        if (!(iResourcePack instanceof AbstractResourcePack)) {
            return new String[0];
        }

        AbstractResourcePack abstractResourcePack = (AbstractResourcePack) iResourcePack;
        File file1 = abstractResourcePack.resourcePackFile;
        return file1 == null
                ? new String[0]
                : (file1.isDirectory()
                        ? collectFilesFolder(
                                file1, packedTextFragments[0], astring, astring1)
                        : (file1.isFile()
                                ? collectFilesZIP(file1, astring, astring1)
                                : new String[0]));
    }

    private static String[] collectFilesFolder(
            File file1, String text, String[] astring, String[] astring1) {
        ArrayList arrayList = new ArrayList();
        int l = ReflectorMethod.f();
        String textValue = "assets/minecraft/";
        int i = l;
        File[] afile = file1.listFiles();
        if (afile == null) {
            return new String[0];
        }

        int j = 0;

        while (j < afile.length) {
            label40:
            {
                File file2 = afile[j];
                if (file2.isFile()) {
                    String textValue2 = text + file2.getName();
                    if (!textValue2.startsWith(textValue)) {
                        break label40;
                    }

                    textValue2 = textValue2.substring(textValue.length());
                    if (!StrUtils.startsWith(textValue2, astring)
                            || !StrUtils.endsWith(textValue2, astring1)) {
                        break label40;
                    }

                    arrayList.add(textValue2);
                    if (i == 0) {
                        break label40;
                    }
                }

                if (file2.isDirectory()) {
                    String textValue3 = text + file2.getName() + packedTextFragments[1];
                    String[] astring2 =
                            collectFilesFolder(file2, textValue3, astring, astring1);
                    int k = 0;

                    while (k < astring2.length) {
                        String textValue4 = astring2[k];
                        arrayList.add(textValue4);
                        k++;
                        if (i != 0) {
                            break;
                        }
                    }
                }
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    static {
        optifine.ResUtils.packedTextFragments = new String[3];
        optifine.ResUtils.packedTextFragments[0] = new String("");
        optifine.ResUtils.packedTextFragments[1] = new String("/");
    }
}
