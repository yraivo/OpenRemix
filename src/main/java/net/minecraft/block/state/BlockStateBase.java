package net.minecraft.block.state;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraft.block.state.BlockStateBaseMapEntryToStringFunction;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;

import java.util.Collection;
import java.util.Iterator;

public abstract class BlockStateBase implements BlockState {
    private int metadata;
    private static String[] packedTextFragments;
    private ResourceLocation blockLocation;
    private int blockId = -1;
    private int blockStateId = -1;
    private static final Function MAP_ENTRY_TO_STRING = new BlockStateBaseMapEntryToStringFunction();
    private static final Joiner COMMA_JOINER = Joiner.on(',');

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    public int getMetadata() {
        if (this.metadata < 0) {
            this.metadata = this.getBlock().getMetaFromState(this);
        }

        return this.metadata;
    }

    public ImmutableTable<IProperty, Comparable, BlockState> getPropertyValueTable() {
        return null;
    }

    protected static Object cyclePropertyValue(Collection collection, Object object) {
        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().equals(object)) {
                if (iterator.hasNext()) {
                    return iterator.next();
                }

                return collection.iterator().next();
            }
        }

        return iterator.next();
    }

    @Override
    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(Block.blockRegistry.getNameForObject(this.getBlock()));
        if (!this.getProperties().isEmpty()) {
            stringbuilder.append(packedTextFragments[0]);
            net.minecraft.block.state.BlockStateBase.COMMA_JOINER.appendTo(
                    stringbuilder,
                    Iterables.transform(
                            this.getProperties().entrySet(),
                            net.minecraft.block.state.BlockStateBase.MAP_ENTRY_TO_STRING));
            stringbuilder.append(packedTextFragments[1]);
        }

        return stringbuilder.toString();
    }

    public BlockState cycleProperty(IProperty iProperty) {
        return this.withProperty(
                iProperty,
                (Comparable)
                        cyclePropertyValue(iProperty.getAllowedValues(), this.getValue(iProperty)));
    }

    public int getBlockStateId() {
        if (this.blockStateId < 0) {
            this.blockStateId = Block.getStateId(this);
        }

        return this.blockStateId;
    }

    // region String decoding support

    // endregion

    public int getBlockId() {
        if (this.blockId < 0) {
            this.blockId = Block.getIdFromBlock(this.getBlock());
        }

        return this.blockId;
    }

    public ResourceLocation getBlockLocation() {
        if (this.blockLocation == null) {
            this.blockLocation =
                    (ResourceLocation) Block.blockRegistry.getNameForObject(this.getBlock());
        }

        return this.blockLocation;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.block.state.BlockStateBase.packedTextFragments[0] = new String("[");
        net.minecraft.block.state.BlockStateBase.packedTextFragments[1] = new String("]");
    }

    // endregion

    public BlockStateBase() {
        this.metadata = -1;
        this.blockLocation = null;
    }
}
