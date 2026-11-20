package shadersmod.client;

import optifine.OptiFineConfig;
import optifine.StrUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShaderPackParser {
    private static Pattern PATTERN_INCLUDE;
    private static String[] packedTextFragments;

    private static Set<String> setConstNames;

    static {
        shadersmod.client.ShaderPackParser.packedTextFragments = new String[18];
        shadersmod.client.ShaderPackParser.packedTextFragments[0] = new String("");
        shadersmod.client.ShaderPackParser.packedTextFragments[1] = new String("/");
        shadersmod.client.ShaderPackParser.packedTextFragments[2] = new String("/");
        shadersmod.client.ShaderPackParser.packedTextFragments[3] = new String(" ");
        shadersmod.client.ShaderPackParser.packedTextFragments[4] = new String("!");
        shadersmod.client.ShaderPackParser.packedTextFragments[5] = new String(" ");
        shadersmod.client.ShaderPackParser.packedTextFragments[6] = new String("*");
        shadersmod.client.ShaderPackParser.packedTextFragments[7] = new String("[");
        shadersmod.client.ShaderPackParser.packedTextFragments[8] = new String("]");
        shadersmod.client.ShaderPackParser.packedTextFragments[9] = new String("[");
        shadersmod.client.ShaderPackParser.packedTextFragments[10] = new String("]");
        shadersmod.client.ShaderPackParser.packedTextFragments[11] = new String("/");
        shadersmod.client.ShaderPackParser.packedTextFragments[12] = new String("/");
        shadersmod.client.ShaderPackParser.packedTextFragments[13] = new String("/");
        shadersmod.client.ShaderPackParser.packedTextFragments[14] = new String("/");
        shadersmod.client.ShaderPackParser.packedTextFragments[15] = new String("\n");
        shadersmod.client.ShaderPackParser.packedTextFragments[16] = new String("\n");
        shadersmod.client.ShaderPackParser.PATTERN_INCLUDE =
                Pattern.compile("^\\s*#include\\s+\"([A-Za-z0-9_/\\.]+)\".*$");
        shadersmod.client.ShaderPackParser.setConstNames = makeSetConstNames();
    }

    private static ShaderOption getShaderOption(String text, String textValue) {
        ShaderOption shaderOption = null;
        if (shaderOption == null) {
            shaderOption = ShaderOptionSwitch.parseOption_ShaderOption(text, textValue);
        }

        if (shaderOption == null) {
            shaderOption = ShaderOptionVariable.parseOption(text, textValue);
        }

        if (shaderOption != null) {
            return shaderOption;
        }

        if (shaderOption == null) {
            shaderOption = ShaderOptionSwitchConst.parseOption(text, textValue);
        }

        if (shaderOption == null) {
            shaderOption = ShaderOptionVariableConst.kC(text, textValue);
        }

        return shaderOption != null
                        && shadersmod.client.ShaderPackParser.setConstNames.contains(
                                shaderOption.getName())
                ? shaderOption
                : null;
    }

    private static boolean parseGuiScreen(
            String text,
            Properties properties,
            Map<String, ShaderOption[]> map,
            ShaderProfile[] shaderProfileArray,
            ShaderOption[] shaderOptionArray) {
        int[] aint1 = ShaderOption.lz();
        String property = properties.getProperty(text);
        int[] aint = aint1;
        if (property == null) {
            return false;
        }

        ArrayList arrayList = new ArrayList();
        HashSet hashset = new HashSet();
        String[] astring = OptiFineConfig.tokenize(property, packedTextFragments[5]);
        int i = 0;

        while (i < astring.length) {
            label79:
            {
                String textValue = astring[i];
                if (textValue.equals("<empty>")) {
                    arrayList.add(null);
                    if (aint == null) {
                        break label79;
                    }
                }

                if (hashset.contains(textValue)) {
                    OptiFineConfig.warn(
                            "[Shaders] Duplicate option: " + textValue + ", key: " + text);
                    if (aint == null) {
                        break label79;
                    }
                }

                hashset.add(textValue);
                if (textValue.equals("<profile>")) {
                    if (shaderProfileArray == null) {
                        OptiFineConfig.warn(
                                "[Shaders] Option profile can not be used, no profiles defined: "
                                        + textValue
                                        + ", key: "
                                        + text);
                        if (aint == null) {
                            break label79;
                        }
                    }

                    ShaderOptionProfile shaderOptionProfile =
                            new ShaderOptionProfile(shaderProfileArray, shaderOptionArray);
                    arrayList.add(shaderOptionProfile);
                    if (aint == null) {
                        break label79;
                    }
                }

                if (textValue.equals(packedTextFragments[6])) {
                    ShaderOptionRest shaderOptionRest = new ShaderOptionRest("<rest>");
                    arrayList.add(shaderOptionRest);
                    if (aint == null) {
                        break label79;
                    }
                }

                if (textValue.startsWith(packedTextFragments[7])
                        && textValue.endsWith(packedTextFragments[8])) {
                    label80:
                    {
                        String textValue2 =
                                StrUtils.removePrefixSuffix(
                                        textValue, packedTextFragments[9], packedTextFragments[10]);
                        if (!textValue2.matches("^[a-zA-Z0-9_]+$")) {
                            OptiFineConfig.warn(
                                    "[Shaders] Invalid screen: " + textValue + ", key: " + text);
                            if (aint == null) {
                                break label80;
                            }
                        }

                        if (!parseGuiScreen(
                                "screen." + textValue2,
                                properties,
                                map,
                                shaderProfileArray,
                                shaderOptionArray)) {
                            OptiFineConfig.warn(
                                    "[Shaders] Invalid screen: " + textValue + ", key: " + text);
                            if (aint == null) {
                                break label80;
                            }
                        }

                        ShaderOptionScreen shaderOptionScreen = new ShaderOptionScreen(textValue2);
                        arrayList.add(shaderOptionScreen);
                    }

                    if (aint == null) {
                        break label79;
                    }
                }

                ShaderOption shaderOption =
                        ShaderUtils.getShaderOption(textValue, shaderOptionArray);
                if (shaderOption == null) {
                    OptiFineConfig.warn(
                            "[Shaders] Invalid option: " + textValue + ", key: " + text);
                    arrayList.add(null);
                    if (aint == null) {
                        break label79;
                    }
                }

                shaderOption.setVisible(true);
                arrayList.add(shaderOption);
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        ShaderOption[] shaderOptionArrayValue =
                ((shadersmod.client.ShaderOption[])
                        arrayList.toArray(new ShaderOption[arrayList.size()]));
        map.put(text, shaderOptionArrayValue);
        return true;
    }

    private static String[] getLines(IShaderPack iShaderPack, String text) {
        try {
            String textValue = method_92e8fb1765cdf75039dd(text, iShaderPack, 0);
            if (textValue == null) {
                return new String[0];
            }

            ByteArrayInputStream bytearrayinputstream =
                    new ByteArrayInputStream(textValue.getBytes());
            return OptiFineConfig.readLines(bytearrayinputstream);
        } catch (IOException ioexception) {
            OptiFineConfig.dbg(ioexception.getClass().getName() + ": " + ioexception.getMessage());
            return new String[0];
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    private static ShaderProfile parseProfile(
            String text, Properties properties, Set<String> set, ShaderOption[] shaderOptionArray) {
        String textValue = "profile.";
        int[] aint1 = ShaderOption.lz();
        String textValue2 = textValue + text;
        int[] aint = aint1;
        if (set.contains(textValue2)) {
            OptiFineConfig.warn("[Shaders] Profile already parsed: " + text);
            return null;
        }

        set.add(text);
        ShaderProfile shaderProfile = new ShaderProfile(text);
        String property = properties.getProperty(textValue2);
        String[] astring = OptiFineConfig.tokenize(property, packedTextFragments[3]);
        int i = 0;

        while (i < astring.length) {
            label84:
            {
                String textValue3 = astring[i];
                if (textValue3.startsWith(textValue)) {
                    String textValue4 = textValue3.substring(textValue.length());
                    ShaderProfile shaderProfileValue =
                            parseProfile(textValue4, properties, set, shaderOptionArray);
                    if (shaderProfile != null) {
                        shaderProfile.addOptionValues(shaderProfileValue);
                        shaderProfile.addDisabledPrograms(shaderProfileValue.getDisabledPrograms());
                    }

                    if (aint == null) {
                        break label84;
                    }
                }

                String[] astring1 = OptiFineConfig.tokenize(textValue3, ":=");
                if (astring1.length == 1) {
                    String textValue5 = astring1[0];
                    byte b0 = 1;
                    if (textValue5.startsWith(packedTextFragments[4])) {
                        b0 = 0;
                        textValue5 = textValue5.substring(1);
                    }

                    label86:
                    {
                        String textValue6 = "program.";
                        if (b0 == 0 && textValue5.startsWith("program.")) {
                            {
                                String textValue7 = textValue5.substring(textValue6.length());
                                if (!Shaders.isProgramPath(textValue7)) {
                                    OptiFineConfig.warn(
                                            "Invalid program: "
                                                    + textValue7
                                                    + " in profile: "
                                                    + shaderProfile.getName());
                                    if (!(aint == null)) {

                                        shaderProfile.addDisabledProgram(textValue7);
                                    }
                                } else {

                                    shaderProfile.addDisabledProgram(textValue7);
                                }
                            }

                            if (aint == null) {
                                break label86;
                            }
                        }

                        ShaderOption shaderOption =
                                ShaderUtils.getShaderOption(textValue5, shaderOptionArray);
                        if (!(shaderOption instanceof ShaderOptionSwitch)) {
                            OptiFineConfig.warn("[Shaders] Invalid option: " + textValue5);
                            if (aint == null) {
                                break label86;
                            }
                        }

                        shaderProfile.addOptionValue(
                                textValue5,
                                String.valueOf(remix.compat.compiler.JvmBooleanSlots.fromInt(b0)));
                        shaderOption.setVisible(true);
                    }

                    if (aint == null) {
                        break label84;
                    }
                }

                if (astring1.length != 2) {
                    OptiFineConfig.warn("[Shaders] Invalid option value: " + textValue3);
                    if (aint == null) {
                        break label84;
                    }
                }

                String textValue8 = astring1[0];
                String textValue9 = astring1[1];
                ShaderOption shaderOptionValue =
                        ShaderUtils.getShaderOption(textValue8, shaderOptionArray);
                if (shaderOptionValue == null) {
                    OptiFineConfig.warn("[Shaders] Invalid option: " + textValue3);
                    if (aint == null) {
                        break label84;
                    }
                }

                if (!shaderOptionValue.isValidValue(textValue9)) {
                    OptiFineConfig.warn("[Shaders] Invalid value: " + textValue3);
                    if (aint == null) {
                        break label84;
                    }
                }

                shaderOptionValue.setVisible(true);
                shaderProfile.addOptionValue(textValue8, textValue9);
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        return shaderProfile;
    }

    private static Set<String> makeSetConstNames() {
        HashSet hashset = new HashSet();
        hashset.add("shadowMapResolution");
        hashset.add("shadowDistance");
        hashset.add("shadowIntervalSize");
        hashset.add("generateShadowMipmap");
        hashset.add("generateShadowColorMipmap");
        hashset.add("shadowHardwareFiltering");
        hashset.add("shadowHardwareFiltering0");
        hashset.add("shadowHardwareFiltering1");
        hashset.add("shadowtex0Mipmap");
        hashset.add("shadowtexMipmap");
        hashset.add("shadowtex1Mipmap");
        hashset.add("shadowcolor0Mipmap");
        hashset.add("shadowColor0Mipmap");
        hashset.add("shadowcolor1Mipmap");
        hashset.add("shadowColor1Mipmap");
        hashset.add("shadowtex0Nearest");
        hashset.add("shadowtexNearest");
        hashset.add("shadow0MinMagNearest");
        hashset.add("shadowtex1Nearest");
        hashset.add("shadow1MinMagNearest");
        hashset.add("shadowcolor0Nearest");
        hashset.add("shadowColor0Nearest");
        hashset.add("shadowColor0MinMagNearest");
        hashset.add("shadowcolor1Nearest");
        hashset.add("shadowColor1Nearest");
        hashset.add("shadowColor1MinMagNearest");
        hashset.add("wetnessHalflife");
        hashset.add("drynessHalflife");
        hashset.add("eyeBrightnessHalflife");
        hashset.add("centerDepthHalflife");
        hashset.add("sunPathRotation");
        hashset.add("ambientOcclusionLevel");
        hashset.add("superSamplingLevel");
        hashset.add("noiseTextureResolution");
        return hashset;
    }

    public static Map<String, ShaderOption[]> parseGuiScreens(
            Properties properties,
            ShaderProfile[] shaderProfileArray,
            ShaderOption[] shaderOptionArray) {
        HashMap hashmap = new HashMap();
        parseGuiScreen("screen", properties, hashmap, shaderProfileArray, shaderOptionArray);
        return hashmap.isEmpty() ? null : hashmap;
    }

    public static BufferedReader method_6fa68e5662b8e255afce(
            BufferedReader bufferedreader, String text, IShaderPack iShaderPack, int i)
            throws IOException {
        String textValue = packedTextFragments[11];
        int j = text.lastIndexOf(packedTextFragments[12]);
        if (j >= 0) {
            textValue = text.substring(0, j);
        }

        CharArrayWriter chararraywriter = new CharArrayWriter();

        while (true) {
            String textValue2 = bufferedreader.readLine();
            if (textValue2 == null) {
                CharArrayReader chararrayreader =
                        new CharArrayReader(chararraywriter.toCharArray());
                return new BufferedReader(chararrayreader);
            }

            Matcher matcher =
                    shadersmod.client.ShaderPackParser.PATTERN_INCLUDE.matcher(textValue2);
            if (matcher.matches()) {
                String textValue3 = matcher.group(1);
                boolean flag = textValue3.startsWith(packedTextFragments[13]);

                String textValue4;
                label35:
                {
                    try {
                        if (false)
                            throw (java.io.IOException)
                                    null; // Original JVM checked handler; no emitted instructions.
                        if (flag) {
                            textValue4 = "/shaders" + textValue3;
                            break label35;
                        }
                    } catch (IOException ioexception1) {
                        throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception1));
                    }

                    textValue4 = textValue + packedTextFragments[14] + textValue3;
                }

                String textValue5 = textValue4;
                textValue2 = method_92e8fb1765cdf75039dd(textValue5, iShaderPack, i);

                try {
                    if (textValue2 == null) {
                        throw new IOException("Included file not found: " + text);
                    }
                } catch (IOException ioexception) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
                }
            }

            chararraywriter.write(textValue2);
            chararraywriter.write(packedTextFragments[15]);
        }
    }

    public static ShaderProfile[] parseProfiles(
            Properties properties, ShaderOption[] shaderOptionArray) {
        int[] aint1 = ShaderOption.lz();
        String text = "profile.";
        ArrayList arrayList = new ArrayList();
        int[] aint = aint1;

        for (java.util.Iterator<?> __recoveryIterator171_46024 = (properties.keySet()).iterator();
                (__recoveryIterator171_46024).hasNext(); ) {
            String textValue = (String) (__recoveryIterator171_46024).next();
            if (textValue.startsWith(text)) {
                String textValue2 = textValue.substring(text.length());
                properties.getProperty(textValue);
                HashSet hashset = new HashSet();
                ShaderProfile shaderProfile =
                        parseProfile(textValue2, properties, hashset, shaderOptionArray);
                if (shaderProfile != null) {
                    arrayList.add(shaderProfile);
                }
            }

            if (aint != null) {
                break;
            }
        }

        return arrayList.size() <= 0
                ? null
                : ((shadersmod.client.ShaderProfile[])
                        arrayList.toArray(new ShaderProfile[arrayList.size()]));
    }

    private static boolean isOptionUsed(ShaderOption shaderOption, String[] astring) {
        int[] aint1 = ShaderOption.lz();
        int i = 0;
        int[] aint = aint1;

        while (i < astring.length) {
            String text = astring[i];
            if (shaderOption.isUsedInLine(text)) {
                return true;
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        return false;
    }

    private static String method_92e8fb1765cdf75039dd(String text, IShaderPack iShaderPack, int i)
            throws IOException {
        try {
            if (i >= 10) {
                throw new IOException("#include depth exceeded: " + i + ", file: " + text);
            }
        } catch (IOException ioexception2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception2));
        }

        i++;
        InputStream inputstream = iShaderPack.getResourceAsStream(text);

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (inputstream == null) {
                return null;
            }
        } catch (IOException ioexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception1));
        }

        InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "ASCII");
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
        bufferedreader = method_6fa68e5662b8e255afce(bufferedreader, text, iShaderPack, i);
        CharArrayWriter chararraywriter = new CharArrayWriter();

        while (true) {
            String textValue = bufferedreader.readLine();

            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (textValue == null) {
                    return chararraywriter.toString();
                }
            } catch (IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
            }

            chararraywriter.write(textValue);
            chararraywriter.write(packedTextFragments[16]);
        }
    }

    private static void collectShaderOptions(
            IShaderPack iShaderPack, String text, Map<String, ShaderOption> map) {
        int[] aint1 = ShaderOption.lz();
        String[] astring = getLines(iShaderPack, text);
        int[] aint = aint1;
        int i = 0;

        while (i < astring.length) {
            String textValue = astring[i];
            ShaderOption shaderOption = getShaderOption(textValue, text);
            if (shaderOption != null
                    && (!shaderOption.checkUsed() || isOptionUsed(shaderOption, astring))) {
                String textValue2 = shaderOption.getName();
                ShaderOption shaderOptionValue = (ShaderOption) map.get(textValue2);
                if (shaderOptionValue != null) {
                    if (!OptiFineConfig.equals(
                            shaderOptionValue.getValueDefault(), shaderOption.getValueDefault())) {
                        OptiFineConfig.warn("Ambiguous shader option: " + shaderOption.getName());
                        OptiFineConfig.warn(
                                " - in "
                                        + OptiFineConfig.arrayToString(shaderOptionValue.getPaths())
                                        + ": "
                                        + shaderOptionValue.getValueDefault());
                        OptiFineConfig.warn(
                                " - in "
                                        + OptiFineConfig.arrayToString(shaderOption.getPaths())
                                        + ": "
                                        + shaderOption.getValueDefault());
                        shaderOptionValue.setEnabled(false);
                    }

                    if (shaderOptionValue.getDescription() == null
                            || shaderOptionValue.getDescription().length() <= 0) {
                        shaderOptionValue.setDescription(shaderOption.getDescription());
                    }

                    shaderOptionValue.addPaths(shaderOption.getPaths());
                    if (!(aint == null)) {

                        map.put(textValue2, shaderOption);
                    }
                } else {

                    map.put(textValue2, shaderOption);
                }
            }

            i++;
            if (aint != null) {
                break;
            }
        }
    }

    private static void collectShaderOptions(
            IShaderPack iShaderPack, String text, String[] astring, Map<String, ShaderOption> map) {
        int[] aint1 = ShaderOption.lz();
        int i = 0;
        int[] aint = aint1;

        while (i < astring.length) {
            String textValue = astring[i];
            if (!textValue.equals(packedTextFragments[0])) {
                String textValue2 = text + packedTextFragments[1] + textValue + ".vsh";
                String textValue3 = text + packedTextFragments[2] + textValue + ".fsh";
                collectShaderOptions(iShaderPack, textValue2, map);
                collectShaderOptions(iShaderPack, textValue3, map);
            }

            i++;
            if (aint != null) {
                break;
            }
        }
    }

    public static ShaderOption[] parseShaderPackOptions(
            IShaderPack iShaderPack, String[] astring, List<Integer> integerList) {
        int[] aint = ShaderOption.lz();
        if (iShaderPack == null) {
            return new ShaderOption[0];
        }

        HashMap hashmap = new HashMap();
        collectShaderOptions(iShaderPack, "/shaders", astring, hashmap);

        for (int i : integerList) {
            String text = "/shaders/world" + i;
            collectShaderOptions(iShaderPack, text, astring, hashmap);
            if (aint != null) {
                break;
            }
        }

        Collection collection = hashmap.values();
        ShaderOption[] shaderOptionArray =
                ((shadersmod.client.ShaderOption[])
                        collection.toArray(new ShaderOption[collection.size()]));
        ShaderPackParserParseShaderPackOptionsComparator
                shaderPackParserParseShaderPackOptionsComparator =
                        new ShaderPackParserParseShaderPackOptionsComparator();
        Arrays.sort(shaderOptionArray, shaderPackParserParseShaderPackOptionsComparator);
        return shaderOptionArray;
    }
}
