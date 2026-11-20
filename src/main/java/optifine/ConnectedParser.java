package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectedParser {
    private String context = null;

    private static MatchBlock[] NO_MATCH_BLOCKS;

    private static String[] packedTextFragments;

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public String parseName(String text) {
        java.lang.String textValue = text;
        int i = text.lastIndexOf(47);
        if (i >= 0) {
            textValue = text.substring(i + 1);
        }

        int j = textValue.lastIndexOf(46);
        if (j >= 0) {
            textValue = textValue.substring(0, j);
        }

        return textValue;
    }

    public boolean matchState(
            BlockState blockState, Map<IProperty, List<Comparable>> map) {
        for (java.util.Iterator<?> typedIterator551 =
                        ((java.util.Iterator) (((java.util.Set) (map.keySet())).iterator()));
                ((boolean) (((java.util.Iterator) (typedIterator551)).hasNext())); ) {
            net.minecraft.block.properties.IProperty iProperty =
                    (net.minecraft.block.properties.IProperty)
                            ((java.lang.Object) (((java.util.Iterator) (typedIterator551)).next()));
            java.util.List list = (java.util.List) map.get(iProperty);
            java.lang.Comparable comparable = blockState.getValue(iProperty);

            try {
                if (comparable == null) {
                    return false;
                }
            } catch (java.lang.IllegalArgumentException illegalargumentexception) {
                throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception);
            }

            if (!list.contains(comparable)) {
                return false;
            }
        }

        return true;
    }

    public Block[] parseBlockPart(String text, String textValue) {
        int i = ReflectorMethod.f();
        if (!this.startsWithDigit(textValue)) {
            String textValue2 = text + packedTextFragments[4] + textValue;
            Block block = Block.getBlockFromName(textValue2);
            if (block == null) {
                this.warn("Block not found for name: " + textValue2);
                return null;
            } else {
                return new Block[] {block};
            }
        } else {
            int[] aint = this.parseIntList(textValue);
            if (aint == null) {
                return null;
            }

            Block[] blockArray = new Block[aint.length];
            int j = 0;

            while (j < aint.length) {
                int k = aint[j];
                Block blockValue = Block.getBlockById(k);
                if (blockValue == null) {
                    this.warn("Block not found for id: " + k);
                    return null;
                }

                blockArray[j] = blockValue;
                j++;
                if (i != 0) {
                    break;
                }
            }

            return blockArray;
        }
    }

    public boolean[] parseFaces(String text, boolean[] aboolean) {
        int i = ReflectorMethod.cR();
        if (text == null) {
            return aboolean;
        }

        EnumSet enumset = EnumSet.allOf(Facing.class);
        String[] astring = OptiFineConfig.tokenize(text, " ,");
        int j = 0;

        while (j < astring.length) {
            label50:
            {
                String textValue = astring[j];
                if (textValue.equals("sides")) {
                    enumset.add(Facing.NORTH);
                    enumset.add(Facing.SOUTH);
                    enumset.add(Facing.WEST);
                    enumset.add(Facing.EAST);
                    if (i != 0) {
                        break label50;
                    }
                }

                if (textValue.equals("all")) {
                    enumset.addAll(Arrays.asList(Facing.VALUES));
                    if (i != 0) {
                        break label50;
                    }
                }

                Facing facing = this.parseFace(textValue);
                if (facing != null) {
                    enumset.add(facing);
                }
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        boolean[] aboolean1 = new boolean[Facing.VALUES.length];
        int k = 0;

        while (k < aboolean1.length) {
            aboolean1[k] = enumset.contains(Facing.VALUES[k]);
            k++;
            if (i == 0) {
                break;
            }
        }

        return aboolean1;
    }

    public void w_void(String text) {
        OptiFineConfig.dbg(packedTextFragments[12] + this.context + ": " + text);
    }

    public int[] parseIntList(String text) {
        int i = ReflectorMethod.f();
        if (text == null) {
            return null;
        }

        ArrayList arrayList = new ArrayList();
        String[] astring = OptiFineConfig.tokenize(text, " ,");
        int j = 0;

        while (j < astring.length) {
            label76:
            {
                String textValue = astring[j];
                if (textValue.contains(packedTextFragments[10])) {
                    label77:
                    {
                        String[] astring1 =
                                OptiFineConfig.tokenize(textValue, packedTextFragments[11]);
                        if (astring1.length != 2) {
                            this.warn("Invalid interval: " + textValue + ", when parsing: " + text);
                            if (i == 0) {
                                break label77;
                            }
                        }

                        int k = OptiFineConfig.parseInt(astring1[0], -1);
                        int l = OptiFineConfig.parseInt(astring1[1], -1);
                        if (k >= 0 && l >= 0 && k <= l) {
                            int i1 = k;

                            while (i1 <= l) {
                                arrayList.add(i1);
                                i1++;
                                if (i != 0) {
                                    break;
                                }
                            }

                            if (i == 0) {
                                break label77;
                            }
                        }

                        this.warn("Invalid interval: " + textValue + ", when parsing: " + text);
                    }

                    if (i == 0) {
                        break label76;
                    }
                }

                int k1 = OptiFineConfig.parseInt(textValue, -1);
                if (k1 < 0) {
                    this.warn("Invalid number: " + textValue + ", when parsing: " + text);
                    if (i == 0) {
                        break label76;
                    }
                }

                arrayList.add(k1);
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        int[] aint = new int[arrayList.size()];
        int j1 = 0;

        while (j1 < aint.length) {
            aint[j1] = (Integer) arrayList.get(j1);
            j1++;
            if (i != 0) {
                break;
            }
        }

        return aint;
    }

    public String parseBasePath(String text) {
        int i = text.lastIndexOf(47);

        try {
            if (i < 0) {
                return optifine.ConnectedParser.packedTextFragments[0];
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception);
        }

        return text.substring(0, i);
    }

    public static boolean parseBoolean(String text) {
        return text == null ? false : text.toLowerCase().equals("true");
    }

    public Facing parseFace(String text) {
        text = text.toLowerCase();
        if (text.equals("bottom") || text.equals("down")) {
            return Facing.DOWN;
        }

        if (text.equals("top") || text.equals("up")) {
            return Facing.UP;
        }

        if (text.equals("north")) {
            return Facing.NORTH;
        }

        if (text.equals("south")) {
            return Facing.SOUTH;
        }

        if (text.equals("east")) {
            return Facing.EAST;
        }

        if (text.equals("west")) {
            return Facing.WEST;
        }

        OptiFineConfig.warn("Unknown face: " + text);
        return null;
    }

    public boolean startsWithDigit(String text) {
        try {
            if (text == null) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception);
        }

        try {
            if (text.length() < 1) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception1) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception1);
        }

        char c0 = text.charAt(0);
        return java.lang.Character.isDigit(c0);
    }

    public int parseIntOrDefault(String text, int i) {
        if (text == null) {
            return i;
        } else {
            int j = OptiFineConfig.parseInt(text, -1);
            if (j < 0) {
                this.warn("Invalid number: " + text);
                return i;
            } else {
                return j;
            }
        }
    }

    public static Comparable parsePropertyValue(IProperty iProperty, String text) {
        java.lang.Class oclass = iProperty.getValueClass();
        java.lang.Comparable comparable = optifine.ConnectedParser.parseValue(text, oclass);
        if (comparable == null) {
            java.util.Collection allowedValues = iProperty.getAllowedValues();
            comparable = optifine.ConnectedParser.getPropertyValue(text, allowedValues);
        }

        return comparable;
    }

    public void warn(String text) {
        OptiFineConfig.warn(packedTextFragments[13] + this.context + ": " + text);
    }

    public static Comparable getPropertyValue(String text, Collection collection) {
        for (java.util.Iterator<?> typedIterator487 =
                        ((java.util.Iterator) (((java.util.Collection) (collection)).iterator()));
                ((boolean) (((java.util.Iterator) (typedIterator487)).hasNext())); ) {
            java.lang.Comparable comparable =
                    (java.lang.Comparable)
                            ((java.lang.Object) (((java.util.Iterator) (typedIterator487)).next()));
            if (java.lang.String.valueOf(comparable).equals(text)) {
                return comparable;
            }
        }

        return null;
    }

    static {
        optifine.ConnectedParser.packedTextFragments = new String[17];
        optifine.ConnectedParser.packedTextFragments[0] = new String("");
        optifine.ConnectedParser.packedTextFragments[1] = new String(" ");
        optifine.ConnectedParser.packedTextFragments[2] = new String(":");
        optifine.ConnectedParser.packedTextFragments[3] = new String("=");
        optifine.ConnectedParser.packedTextFragments[4] = new String(":");
        optifine.ConnectedParser.packedTextFragments[5] = new String("=");
        optifine.ConnectedParser.packedTextFragments[6] = new String(",");
        optifine.ConnectedParser.packedTextFragments[7] = new String(" ");
        optifine.ConnectedParser.packedTextFragments[8] = new String(" ");
        optifine.ConnectedParser.packedTextFragments[9] = new String("");
        optifine.ConnectedParser.packedTextFragments[10] = new String("-");
        optifine.ConnectedParser.packedTextFragments[11] = new String("-");
        optifine.ConnectedParser.packedTextFragments[12] = new String("");
        optifine.ConnectedParser.packedTextFragments[13] = new String("");
        optifine.ConnectedParser.packedTextFragments[14] = new String("-");
        optifine.ConnectedParser.NO_MATCH_BLOCKS = new MatchBlock[0];
    }

    public MatchBlock[] parseMatchBlocks(String text) {
        int i = optifine.ReflectorMethod.f();

        try {
            if (text == null) {
                return null;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception1) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception1);
        }

        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.lang.String[] astring =
                optifine.OptiFineConfig.tokenize(text, optifine.ConnectedParser.packedTextFragments[1]);
        int j = 0;

        while (j < astring.length) {
            java.lang.String textValue = astring[j];
            optifine.MatchBlock[] matchBlockArray = this.parseMatchBlock(textValue);

            try {
                if (matchBlockArray == null) {
                    return optifine.ConnectedParser.NO_MATCH_BLOCKS;
                }
            } catch (java.lang.IllegalArgumentException illegalargumentexception) {
                throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception);
            }

            arrayList.addAll(java.util.Arrays.asList(matchBlockArray));
            j++;
            if (i != 0) {
                break;
            }
        }

        return ((optifine.MatchBlock[])
                ((java.lang.Object[])
                        (((java.util.ArrayList) (arrayList))
                                .toArray(
                                        ((java.lang.Object[])
                                                (new optifine.MatchBlock[arrayList.size()]))))));
    }

    public ConnectedParser(String text) {
        this.context = text;
    }

    public RangeListInt parseRangeListInt(String text) {
        int i = ReflectorMethod.cR();
        if (text == null) {
            return null;
        }

        RangeListInt rangeListInt = new RangeListInt();
        String[] astring = OptiFineConfig.tokenize(text, " ,");
        int j = 0;

        while (j < astring.length) {
            String textValue = astring[j];
            RangeInt rangeInt = this.parseRangeInt(textValue);
            if (rangeInt == null) {
                return null;
            }

            rangeListInt.addRange(rangeInt);
            j++;
            if (i == 0) {
                break;
            }
        }

        return rangeListInt;
    }

    public int parseInt(String text) {
        if (text == null) {
            return -1;
        }

        int i = OptiFineConfig.parseInt(text, -1);
        if (i < 0) {
            this.warn("Invalid number: " + text);
        }

        return i;
    }

    public boolean isFullBlockName(String[] textArray) {
        try {
            if (textArray.length < 2) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception3) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception3);
        }

        java.lang.String text = textArray[1];

        try {
            if (text.length() < 1) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception2) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception2);
        }

        try {
            if (this.startsWithDigit(text)) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception1) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception1);
        }

        try {
            if (!text.contains(optifine.ConnectedParser.packedTextFragments[3])) {
                return true;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception);
        }

        return false;
    }

    // region String decoding support

    // endregion

    public static Comparable parseValue(String text, Class type) {
        try {
            if (type == java.lang.String.class) {
                return text;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception5) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception5);
        }

        try {
            if (type == java.lang.Boolean.class) {
                return java.lang.Boolean.valueOf(text);
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception4) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception4);
        }

        try {
            if (type == java.lang.Float.class) {
                return (double) java.lang.Float.valueOf(text).floatValue();
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception3) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception3);
        }

        try {
            if (type == java.lang.Double.class) {
                return java.lang.Double.valueOf(text);
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception);
        }

        try {
            if (type == java.lang.Integer.class) {
                return (double) java.lang.Integer.valueOf(text).intValue();
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception2) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception2);
        }

        try {
            if (type == java.lang.Long.class) {
                return (double) java.lang.Long.valueOf(text).longValue();
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception1) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(illegalargumentexception1);
        }

        return (java.lang.Comparable)
                (java.lang.Object)
                        (java.lang.Comparable)
                                (java.lang.Object)
                                        ((java.lang.Double)
                                                (java.lang.Double.valueOf(
                                                        ((double)
                                                                ((double)
                                                                        ((long)
                                                                                (((java.lang.Long)
                                                                                                (null))
                                                                                        .longValue())))))));
    }

    public static int parseColor(String text, int p171_1) {
        try {
            if (text == null) {
                return p171_1;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw optifine.ConnectedParser.method_9a70918140b0a7642dba(numberformatexception1);
        }

        text = text.trim();

        try {
            return java.lang.Integer.parseInt(text, 16) & 16777215;
        } catch (java.lang.NumberFormatException numberformatexception) {
            return p171_1;
        }
    }

    // region String decoding support

    // endregion

    public BiomeGenBase[] c(String text) {
        int i = ReflectorMethod.cR();
        if (text == null) {
            return null;
        }

        String[] astring = OptiFineConfig.tokenize(text, packedTextFragments[7]);
        ArrayList arrayList = new ArrayList();
        int j = 0;

        while (j < astring.length) {
            {
                String textValue = astring[j];
                BiomeGenBase biomeGenBase = this.findBiome(textValue);
                if (biomeGenBase == null) {
                    this.warn("Biome not found: " + textValue);
                    if (!(i != 0)) {

                        arrayList.add(biomeGenBase);
                    }
                } else {

                    arrayList.add(biomeGenBase);
                }
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return ((net.minecraft.world.biome.BiomeGenBase[]) arrayList.toArray(new BiomeGenBase[arrayList.size()]));
    }

    private RangeInt parseRangeInt(String text) {
        if (text == null) {
            return null;
        }

        if (text.indexOf(45) >= 0) {
            String[] astring = OptiFineConfig.tokenize(text, packedTextFragments[14]);
            if (astring.length != 2) {
                this.warn("Invalid range: " + text);
                return null;
            }

            int j = OptiFineConfig.parseInt(astring[0], -1);
            int k = OptiFineConfig.parseInt(astring[1], -1);
            if (j >= 0 && k >= 0) {
                return new RangeInt(j, k);
            }

            this.warn("Invalid range: " + text);
            return null;
        } else {
            int i = OptiFineConfig.parseInt(text, -1);
            if (i < 0) {
                this.warn("Invalid integer: " + text);
                return null;
            } else {
                return new RangeInt(i, i);
            }
        }
    }

    private BlockState getStateFromMeta(Block block, int i) {
        try {
            BlockState blockState = block.getStateFromMeta(i);
            if (block == Blocks.double_plant && i > 7) {
                BlockState blockStateValue = block.getStateFromMeta(i & 7);
                blockState =
                        blockState.withProperty(
                                BlockDoublePlant.VARIANT_PROP,
                                blockStateValue.getValue(BlockDoublePlant.VARIANT_PROP));
            }

            return blockState;
        } catch (IllegalArgumentException illegalargumentexception) {
            return block.getDefaultState();
        }
    }

    public int[] parseBlockMetadatas(Block block, String[] astring) {
        int i = ReflectorMethod.cR();
        if (astring.length <= 0) {
            return null;
        }

        String text = astring[0];
        if (this.startsWithDigit(text)) {
            return this.parseIntList(text);
        }

        BlockState blockState = block.getDefaultState();
        Collection propertyNames = blockState.getPropertyNames();
        HashMap hashmap = new HashMap();
        int j = 0;

        while (j < astring.length) {
            String textValue = astring[j];
            if (textValue.length() > 0) {
                String[] astring1 = OptiFineConfig.tokenize(textValue, packedTextFragments[5]);
                if (astring1.length != 2) {
                    this.warn("Invalid block property: " + textValue);
                    return null;
                }

                String textValue2 = astring1[0];
                String textValue3 = astring1[1];
                IProperty iProperty =
                        ConnectedProperties.getProperty(textValue2, propertyNames);
                if (iProperty == null) {
                    this.warn("Property not found: " + textValue2 + ", block: " + block);
                    return null;
                }

                List list = (List) hashmap.get(textValue2);
                if (list == null) {
                    list = new ArrayList();
                    hashmap.put(iProperty, list);
                }

                String[] astring2 = OptiFineConfig.tokenize(textValue3, packedTextFragments[6]);
                int k = 0;

                while (k < astring2.length) {
                    String textValue4 = astring2[k];
                    Comparable comparable = parsePropertyValue(iProperty, textValue4);
                    if (comparable == null) {
                        this.warn(
                                "Property value not found: "
                                        + textValue4
                                        + ", property: "
                                        + textValue2
                                        + ", block: "
                                        + block);
                        return null;
                    }

                    list.add(comparable);
                    k++;
                    if (i == 0) {
                        break;
                    }
                }
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        if (hashmap.isEmpty()) {
            return null;
        }

        ArrayList arrayList = new ArrayList();
        int j1 = 0;

        while (j1 < 16) {
            int l = j1;

            try {
                BlockState blockStateValue = this.getStateFromMeta(block, l);
                if (this.matchState(blockStateValue, hashmap)) {
                    arrayList.add(l);
                }
            } catch (IllegalArgumentException illegalargumentexception) {
            }

            j1++;
            if (i == 0) {
                break;
            }
        }

        if (arrayList.size() == 16) {
            return null;
        }

        int[] aint = new int[arrayList.size()];
        int i1 = 0;

        while (i1 < aint.length) {
            aint[i1] = (Integer) arrayList.get(i1);
            i1++;
            if (i == 0) {
                break;
            }
        }

        return aint;
    }

    public BiomeGenBase findBiome(String text) {
        int k = ReflectorMethod.cR();
        text = text.toLowerCase();
        int i = k;
        if (text.equals("nether")) {
            return BiomeGenBase.hell;
        }

        BiomeGenBase[] biomeGenArray = BiomeGenBase.getBiomeGenArray();
        int j = 0;

        while (j < biomeGenArray.length) {
            BiomeGenBase biomeGenBase = biomeGenArray[j];
            if (biomeGenBase != null) {
                String textValue =
                        biomeGenBase
                                .biomeName
                                .replace(packedTextFragments[8], packedTextFragments[9])
                                .toLowerCase();
                if (textValue.equals(text)) {
                    return biomeGenBase;
                }
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return null;
    }

    public MatchBlock[] parseMatchBlock(String text) {
        int i = ReflectorMethod.f();
        if (text == null) {
            return null;
        }

        text = text.trim();
        if (text.length() <= 0) {
            return null;
        }

        String[] astring;
        byte b1;
        String textValue;
        {
            astring = OptiFineConfig.tokenize(text, packedTextFragments[2]);
            textValue = "minecraft";

            if (astring.length > 1 && this.isFullBlockName(astring)) {
                textValue = astring[0];
                b1 = 1;
                if (!(i == 0)) {

                    textValue = "minecraft";
                    b1 = 0;
                }
            } else {

                textValue = "minecraft";
                b1 = 0;
            }
        }

        String textValue2 = astring[b1];
        String[] astring1 =
                ((java.lang.String[]) Arrays.copyOfRange(astring, b1 + 1, astring.length));
        Block[] blockArray = this.parseBlockPart(textValue, textValue2);
        if (blockArray == null) {
            return null;
        }

        MatchBlock[] matchBlockArray = new MatchBlock[blockArray.length];
        int j = 0;

        while (j < blockArray.length) {
            Block block = blockArray[j];
            int k = Block.getIdFromBlock(block);
            int[] aint = null;
            if (astring1.length > 0) {
                aint = this.parseBlockMetadatas(block, astring1);
                if (aint == null) {
                    return null;
                }
            }

            MatchBlock matchBlock = new MatchBlock(k, aint);
            matchBlockArray[j] = matchBlock;
            j++;
            if (i != 0) {
                break;
            }
        }

        return matchBlockArray;
    }
}
