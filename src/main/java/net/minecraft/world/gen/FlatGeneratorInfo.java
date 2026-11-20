package net.minecraft.world.gen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlatGeneratorInfo {
    private Map worldFeatures;
    private final List flatLayers = Lists.newArrayList();
    private static String[] packedTextFragments;
    private int biomeToUse;

    public Map getWorldFeatures() {
        return this.worldFeatures;
    }

    public FlatGeneratorInfo() {
        this.worldFeatures = Maps.newHashMap();
    }

    private static List method_dc9ef4d826ab7811d26d(int i, String text) {
        MapGenBase.b();

        if (text != null && text.length() >= 1) {
            ArrayList arrayList = Lists.newArrayList();
            String[] astring1 = text.split(packedTextFragments[14]);
            int j = 0;
            String[] astring2 = astring1;
            int k = astring1.length;
            int l = 0;

            while (l < k) {
                String textValue = astring2[l];
                FlatLayerInfo flatLayerInfo = method_6bc94f386b2154943b31(i, textValue, j);
                if (flatLayerInfo == null) {
                    return null;
                }

                arrayList.add(flatLayerInfo);
                j += flatLayerInfo.getLayerCount();
                l++;
            }

            return arrayList;
        } else {
            return null;
        }
    }

    public void l() {
        int i = 0;

        for (net.minecraft.world.gen.FlatLayerInfo flatLayerInfo :
                (java.util.List<net.minecraft.world.gen.FlatLayerInfo>) (java.util.List<?>) (this.flatLayers)) {
            flatLayerInfo.setMinY(i);
            i += flatLayerInfo.getLayerCount();
        }
    }

    public static FlatGeneratorInfo createFlatGeneratorFromString(String text) {
        MapGenBase.b();

        if (text == null) {
            return getDefaultFlatGenerator();
        }

        String[] astring1 = text.split(packedTextFragments[15], -1);
        int i = astring1.length == 1 ? 0 : MathHelper.parseIntWithDefault(astring1[0], 0);
        if (i >= 0 && i <= 3) {
            FlatGeneratorInfo flatGeneratorInfo = new FlatGeneratorInfo();
            int j = astring1.length == 1 ? 0 : 1;
            List list = method_dc9ef4d826ab7811d26d(i, astring1[j++]);
            if (list != null && !list.isEmpty()) {
                flatGeneratorInfo.getFlatLayers().addAll(list);
                flatGeneratorInfo.l();
                int k = BiomeGenBase.plains.biomeID;
                if (i > 0 && astring1.length > j) {
                    k = MathHelper.parseIntWithDefault(astring1[j++], k);
                }

                flatGeneratorInfo.setBiome(k);
                if (i > 0 && astring1.length > j) {
                    String[] astring2 = astring1[j++].toLowerCase().split(packedTextFragments[16]);
                    String[] astring3 = astring2;
                    int l = astring2.length;
                    int i1 = 0;

                    while (i1 < l) {
                        String textValue = astring3[i1];
                        String[] astring4 = textValue.split("\\(", 2);
                        HashMap hashmap = Maps.newHashMap();
                        if (astring4[0].length() > 0) {
                            flatGeneratorInfo.getWorldFeatures().put(astring4[0], hashmap);
                            if (astring4.length > 1
                                    && astring4[1].endsWith(packedTextFragments[17])
                                    && astring4[1].length() > 1) {
                                String[] astring5 =
                                        astring4[1]
                                                .substring(0, astring4[1].length() - 1)
                                                .split(packedTextFragments[18]);
                                int j1 = 0;

                                while (j1 < astring5.length) {
                                    String[] astring6 =
                                            astring5[j1].split(packedTextFragments[19], 2);
                                    if (astring6.length == 2) {
                                        hashmap.put(astring6[0], astring6[1]);
                                    }

                                    j1++;
                                }
                            }
                        }

                        i1++;
                    }

                    return flatGeneratorInfo;
                }

                flatGeneratorInfo.getWorldFeatures().put("village", Maps.newHashMap());
                return flatGeneratorInfo;
            } else {
                return getDefaultFlatGenerator();
            }
        } else {
            return getDefaultFlatGenerator();
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public static FlatGeneratorInfo getDefaultFlatGenerator() {
        FlatGeneratorInfo flatGeneratorInfo = new FlatGeneratorInfo();
        flatGeneratorInfo.setBiome(BiomeGenBase.plains.biomeID);
        flatGeneratorInfo.getFlatLayers().add(new FlatLayerInfo(1, Blocks.bedrock));
        flatGeneratorInfo.getFlatLayers().add(new FlatLayerInfo(2, Blocks.dirt));
        flatGeneratorInfo.getFlatLayers().add(new FlatLayerInfo(1, Blocks.grass));
        flatGeneratorInfo.l();
        flatGeneratorInfo.getWorldFeatures().put("village", Maps.newHashMap());
        return flatGeneratorInfo;
    }

    public List getFlatLayers() {
        return this.flatLayers;
    }

    public int getBiome() {
        return this.biomeToUse;
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    private static FlatLayerInfo method_6bc94f386b2154943b31(int i, String text, int j) {
        MapGenBase.b();

        String[] astring1 = i >= 3 ? text.split("\\*", 2) : text.split(packedTextFragments[10], 2);
        int k = 1;
        int l = 0;
        if (astring1.length == 2) {
            try {
                k = Integer.parseInt(astring1[0]);
                if (j + k >= 256) {
                    k = 256 - j;
                }

                if (k < 0) {
                    k = 0;
                }
            } catch (Throwable throwable) {
                return null;
            }
        }

        Block block = null;

        try {
            String textValue = astring1[astring1.length - 1];
            if (i < 3) {
                astring1 = textValue.split(packedTextFragments[11], 2);
                if (astring1.length > 1) {
                    l = Integer.parseInt(astring1[1]);
                }

                block = Block.getBlockById(Integer.parseInt(astring1[0]));
            } else {
                {
                    astring1 = textValue.split(packedTextFragments[12], 3);
                    block =
                            astring1.length > 1
                                    ? Block.getBlockFromName(
                                            astring1[0] + packedTextFragments[13] + astring1[1])
                                    : null;
                    if (block != null) {
                        l = astring1.length > 2 ? Integer.parseInt(astring1[2]) : 0;

                        
                    } else {

                        block = Block.getBlockFromName(astring1[0]);
                        if (block != null) {
                            l = astring1.length > 1 ? Integer.parseInt(astring1[1]) : 0;
                        }
                    }
                }

                if (block == null) {
                    return null;
                }
            }

            if (block == Blocks.air) {
                l = 0;
            }

            if (l < 0 || l > 15) {
                l = 0;
            }
        } catch (Throwable throwable1) {
            return null;
        }

        FlatLayerInfo flatLayerInfo = new FlatLayerInfo(i, k, block, l);
        flatLayerInfo.setMinY(j);
        return flatLayerInfo;
    }

    @Override
    public String toString() {
        java.lang.StringBuilder stringbuilder = new java.lang.StringBuilder();
        stringbuilder.append(3);
        stringbuilder.append(net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[0]);

        for (int i = 0; i < this.flatLayers.size(); i++) {
            if (i > 0) {
                stringbuilder.append(net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[1]);
            }

            stringbuilder.append(((net.minecraft.world.gen.FlatLayerInfo) this.flatLayers.get(i)).toString());
        }

        stringbuilder.append(net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[2]);
        stringbuilder.append(this.biomeToUse);
        if (!this.worldFeatures.isEmpty()) {
            stringbuilder.append(net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[3]);
            int k = 0;

            for (java.util.Iterator<?> typedIterator88 =
                            ((java.util.Iterator)
                                    (((java.util.Set) (this.worldFeatures.entrySet())).iterator()));
                    ((boolean) (((java.util.Iterator) (typedIterator88)).hasNext())); ) {
                java.util.Map.Entry entry =
                        (java.util.Map.Entry)
                                ((java.lang.Object)
                                        (((java.util.Iterator) (typedIterator88)).next()));
                if (k++ > 0) {
                    stringbuilder.append(net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[4]);
                }

                stringbuilder.append(((java.lang.String) entry.getKey()).toLowerCase());
                java.util.Map map = (java.util.Map) entry.getValue();
                if (!map.isEmpty()) {
                    stringbuilder.append(net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[5]);
                    int j = 0;

                    for (java.util.Iterator<?> typedIterator99 =
                                    ((java.util.Iterator)
                                            (((java.util.Set) (map.entrySet())).iterator()));
                            ((boolean) (((java.util.Iterator) (typedIterator99)).hasNext())); ) {
                        java.util.Map.Entry entry1 =
                                (java.util.Map.Entry)
                                        ((java.lang.Object)
                                                (((java.util.Iterator) (typedIterator99)).next()));
                        if (j++ > 0) {
                            stringbuilder.append(net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[6]);
                        }

                        stringbuilder.append((java.lang.String) entry1.getKey());
                        stringbuilder.append(net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[7]);
                        stringbuilder.append((java.lang.String) entry1.getValue());
                    }

                    stringbuilder.append(net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[8]);
                }
            }
        } else {
            stringbuilder.append(net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[9]);
        }

        return stringbuilder.toString();
    }

    public void setBiome(int biomeToUseValue) {
        this.biomeToUse = biomeToUseValue;
    }

    static {
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments = new String[22];
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[0] = new String(";");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[1] = new String(",");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[2] = new String(";");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[3] = new String(";");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[4] = new String(",");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[5] = new String("(");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[6] = new String(" ");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[7] = new String("=");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[8] = new String(")");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[9] = new String(";");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[10] = new String("x");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[11] = new String(":");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[12] = new String(":");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[13] = new String(":");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[14] = new String(",");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[15] = new String(";");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[16] = new String(",");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[17] = new String(")");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[18] = new String(" ");
        net.minecraft.world.gen.FlatGeneratorInfo.packedTextFragments[19] = new String("=");
    }
}
