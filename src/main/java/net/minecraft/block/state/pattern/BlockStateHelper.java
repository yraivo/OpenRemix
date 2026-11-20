package net.minecraft.block.state.pattern;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Map.Entry;

public class BlockStateHelper implements Predicate {

    private final Map propertyPredicates = Maps.newHashMap();
    private static String c;
    private BlockStateContainer blockstate;

    public boolean apply(BlockState blockState) {
        try {
            if (blockState == null || !blockState.getBlock().equals(this.blockstate.getBlock())) {
                return false;
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }

        for (java.util.Iterator<?> __recoveryIterator171_1161 =
                        (this.propertyPredicates.entrySet()).iterator();
                (__recoveryIterator171_1161).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_1161).next();
            Comparable comparable = blockState.getValue((IProperty) entry.getKey());
            if (!((Predicate) entry.getValue()).apply(comparable)) {
                return false;
            }
        }

        return true;
    }

    // region String decoding support

    // endregion

    public static BlockStateHelper forBlock(Block block) {
        return new BlockStateHelper(block.getBlockState());
    }

    // region String decoding support

    // endregion

    static {
        c = " cannot support property ";
    }

    private BlockStateHelper(BlockStateContainer aVaValue) {
        this.blockstate = aVaValue;
    }

    public boolean apply(Object object) {
        return this.apply((BlockState) object);
    }

    public BlockStateHelper where(IProperty iProperty, Predicate predicate) {
        try {
            if (!this.blockstate.getProperties().contains(iProperty)) {
                throw new IllegalArgumentException(this.blockstate + c + iProperty);
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }

        this.propertyPredicates.put(iProperty, predicate);
        return this;
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }
}
