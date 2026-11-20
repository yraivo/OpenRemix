package net.minecraft.block.state.pattern;

import net.minecraft.entity.Entity;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FactoryBlockPattern {

    private int aisleHeight;
    private static String[] packedTextFragments;
    private int rowWidth;

    private Map symbolMap;
    private static Joiner COMMA_JOIN;
    private final List depth = Lists.newArrayList();

    public static FactoryBlockPattern start() {
        return new FactoryBlockPattern();
    }

    private void checkMissingPredicates() {
        int[] aint1 = BlockPattern.c();
        ArrayList arrayList = Lists.newArrayList();
        int[] aint = aint1;

        for (java.util.Iterator<?> __recoveryIterator171_1514 =
                        (this.symbolMap.entrySet()).iterator();
                (__recoveryIterator171_1514).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_1514).next();
            if (entry.getValue() == null) {
                arrayList.add(entry.getKey());
            }

            if (aint != null) {
                break;
            }
        }

        if (!arrayList.isEmpty()) {
            throw new IllegalStateException(
                    "Predicates for character(s) "
                            + net.minecraft.block.state.pattern.FactoryBlockPattern.COMMA_JOIN.join(arrayList)
                            + " are missing");
        }
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    static {
        net.minecraft.block.state.pattern.FactoryBlockPattern.packedTextFragments = new String[5];
        net.minecraft.block.state.pattern.FactoryBlockPattern.packedTextFragments[0] = new String(")");
        net.minecraft.block.state.pattern.FactoryBlockPattern.packedTextFragments[1] = new String(")");
        net.minecraft.block.state.pattern.FactoryBlockPattern.packedTextFragments[4] = new String(",");
        net.minecraft.block.state.pattern.FactoryBlockPattern.COMMA_JOIN = Joiner.on(packedTextFragments[4]);
    }

    public BlockPattern build() {
        return new BlockPattern(this.makePredicateArray());
    }

    private Predicate[][][] makePredicateArray() {
        this.checkMissingPredicates();
        Predicate[][][] apredicate =
                (Predicate[][][])
                        Array.newInstance(Predicate.class, this.depth.size(), this.aisleHeight, this.rowWidth);
        int[] aint1 = BlockPattern.c();
        int i = 0;
        int[] aint = aint1;

        while (i < this.depth.size()) {
            int j = 0;

            while (true) {
                if (j < this.aisleHeight) {
                    int k = 0;

                    while (k < this.rowWidth) {
                        apredicate[i][j][k] =
                                (Predicate)
                                        this.symbolMap.get(
                                                ((String[]) this.depth.get(i))[j].charAt(k));
                        k++;
                        if (aint != null) {
                            break;
                        }
                    }

                    j++;
                    if (aint == null) {
                        continue;
                    }
                }

                i++;
                if (aint != null) {
                    return apredicate;
                }
                break;
            }
        }

        return apredicate;
    }

    private FactoryBlockPattern() {
        this.symbolMap = Maps.newHashMap();
        this.symbolMap.put(' ', Predicates.alwaysTrue());
    }

    public FactoryBlockPattern where(char c0, Predicate predicate) {
        this.symbolMap.put(c0, predicate);
        return this;
    }

    public FactoryBlockPattern aisle(String... astring) {
        int[] aint = BlockPattern.c();
        if (!ArrayUtils.isEmpty(astring) && !StringUtils.isEmpty(astring[0])) {
            if (this.depth.isEmpty()) {
                this.aisleHeight = astring.length;
                this.rowWidth = astring[0].length();
            }

            if (astring.length != this.aisleHeight) {
                throw new IllegalArgumentException(
                        "Expected aisle with height of "
                                + this.aisleHeight
                                + ", but was given one with a height of "
                                + astring.length
                                + packedTextFragments[0]);
            }

            String[] astring1 = astring;
            int length = astring.length;
            int j = 0;

            label46:
            while (j < length) {
                String text = astring1[j];
                if (text.length() != this.rowWidth) {
                    throw new IllegalArgumentException(
                            "Not all rows in the given aisle are the correct width (expected "
                                    + this.rowWidth
                                    + ", found one with "
                                    + text.length()
                                    + packedTextFragments[1]);
                }

                char[] achar = text.toCharArray();
                int k = achar.length;
                int l = 0;

                while (true) {
                    if (l < k) {
                        char c0 = achar[l];
                        if (!this.symbolMap.containsKey(c0)) {
                            this.symbolMap.put(c0, null);
                        }

                        l++;
                        if (aint == null) {
                            continue;
                        }
                    }

                    j++;
                    if (aint != null) {
                        break label46;
                    }
                    break;
                }
            }

            this.depth.add(astring);
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                BlockPattern.b(new int[2]);
            }

            return this;
        } else {
            throw new IllegalArgumentException("Empty pattern for aisle");
        }
    }
}
