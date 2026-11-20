package net.minecraft.block.state;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.entity.Entity;
import net.minecraft.util.Cartesian;
import net.minecraft.util.MapPopulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BlockStateContainer {
    private static int[] f;
    private ImmutableList validStates;
    private static Function GET_NAME_FUNC;
    private Block block;
    private ImmutableList properties;

    private static Joiner COMMA_JOINER;

    // region String decoding support

    // endregion

    static {
        b(null);
        net.minecraft.block.state.BlockStateContainer.COMMA_JOINER = Joiner.on(", ");
        net.minecraft.block.state.BlockStateContainer.GET_NAME_FUNC =
                new BlockStateGetNameFuncFunction();
    }

    // region String decoding support

    // endregion

    public static int[] xZ() {
        return f;
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("block", Block.blockRegistry.getNameForObject(this.block))
                .add(
                        "properties",
                        Iterables.transform(
                                this.properties,
                                net.minecraft.block.state.BlockStateContainer.GET_NAME_FUNC))
                .toString();
    }

    public ImmutableList getValidStates() {
        return this.validStates;
    }

    private List getAllowedValues() {
        ArrayList arrayList = Lists.newArrayList();
        int[] aint1 = xZ();
        int i = 0;
        int[] aint = aint1;

        while (i < this.properties.size()) {
            arrayList.add(((IProperty) this.properties.get(i)).getAllowedValues());
            i++;
            if (aint != null) {
                break;
            }
        }

        return arrayList;
    }

    public Block getBlock() {
        return this.block;
    }

    public BlockState getBaseState() {
        return (BlockState) this.validStates.get(0);
    }

    public BlockStateContainer(Block block, IProperty... iPropertyArray) {
        super();
        int[] initialFlowMarker = xZ();

        int[] aint1 = initialFlowMarker;

        int[] aint = aint1;
        this.block = block;
        Arrays.sort(iPropertyArray, new BlockStateComparator2(this));
        this.properties = ImmutableList.copyOf(iPropertyArray);
        LinkedHashMap linkedhashmap = Maps.newLinkedHashMap();
        ArrayList arrayList = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_27614 =
                        (Cartesian.cartesianProduct(this.getAllowedValues())).iterator();
                (__recoveryIterator171_27614).hasNext(); ) {
            List list = (List) (__recoveryIterator171_27614).next();
            Map map = MapPopulator.createMap(this.properties, list);
            BlockStateStateImplemenation blockStateStateImplemenation =
                    new BlockStateStateImplemenation(block, ImmutableMap.copyOf(map), null);
            linkedhashmap.put(map, blockStateStateImplemenation);
            arrayList.add(blockStateStateImplemenation);
            if (aint != null) {
                Entity.setLegacyGlobalFlowMarker(new String[1]);
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_28203 = (arrayList).iterator();
                (__recoveryIterator171_28203).hasNext(); ) {
            BlockStateStateImplemenation blockStateStateImplemenationValue =
                    (BlockStateStateImplemenation) (__recoveryIterator171_28203).next();
            blockStateStateImplemenationValue.buildPropertyValueTable(linkedhashmap);
            if (aint != null) {
                break;
            }
        }

        this.validStates = ImmutableList.copyOf(arrayList);
    }

    public Collection getProperties() {
        return this.properties;
    }

    public static void b(int[] aint) {
        f = aint;
    }

    // Helper used only by this class.
    public static class BlockStateComparator2 implements java.util.Comparator {
        net.minecraft.block.state.BlockStateContainer aVa;

        @java.lang.Override
        public int compare(java.lang.Object object, java.lang.Object object1) {
            return this.compare(
                    (net.minecraft.block.properties.IProperty) object,
                    (net.minecraft.block.properties.IProperty) object1);
        }

        public BlockStateComparator2(net.minecraft.block.state.BlockStateContainer aVaValue) {
            this.aVa = aVaValue;
        }

        public int compare(
                net.minecraft.block.properties.IProperty iProperty,
                net.minecraft.block.properties.IProperty iPropertyValue) {
            return iProperty.getName().compareTo(iPropertyValue.getName());
        }
    }
}
