package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;

import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;

import java.util.Map;
import java.util.Map.Entry;

public abstract class StateMapperBase implements IStateMapper {
    private static String f;
    private static String[] packedTextFragments;
    private static int[] gy;
    protected Map mapStateModelLocations = Maps.newLinkedHashMap();

    public String getPropertyString(Map map) {
        method_650532ba484532b3efb0();

        StringBuilder stringbuilder = new StringBuilder();

        for (java.util.Iterator<?> __recoveryIterator171_969 = (map.entrySet()).iterator();
                (__recoveryIterator171_969).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_969).next();
            if (stringbuilder.length() != 0) {
                stringbuilder.append(packedTextFragments[0]);
            }

            IProperty key = (IProperty) entry.getKey();
            Comparable comparable = (Comparable) entry.getValue();
            stringbuilder.append(key.getName());
            stringbuilder.append(packedTextFragments[1]);
            stringbuilder.append(key.getName(comparable));
        }

        if (stringbuilder.length() == 0) {
            stringbuilder.append(f);
        }

        String text = stringbuilder.toString();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(new int[2]);
        }

        return text;
    }

    static {
        net.minecraft.client.renderer.block.statemap.StateMapperBase.packedTextFragments = new String[3];
        net.minecraft.client.renderer.block.statemap.StateMapperBase.packedTextFragments[0] = new String(",");
        net.minecraft.client.renderer.block.statemap.StateMapperBase.packedTextFragments[1] = new String("=");
        net.minecraft.client.renderer.block.statemap.StateMapperBase.packedTextFragments[2] = new String("s%'}u\u0014");
        if (method_650532ba484532b3efb0() == null) {
            b(new int[3]);
        }
        f = "normal";
    }

    protected abstract ModelResourceLocation getModelResourceLocation(BlockState blockState);

    public static void b(int[] aint) {
        gy = aint;
    }

    // region String decoding support

    // endregion

    public static int[] method_650532ba484532b3efb0() {
        return gy;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public Map putStateModelLocations(Block block) {
        method_650532ba484532b3efb0();

        UnmodifiableIterator unmodifiableiterator =
                block.getBlockState().getValidStates().iterator();

        while (unmodifiableiterator.hasNext()) {
            BlockState blockState = (BlockState) unmodifiableiterator.next();
            this.mapStateModelLocations.put(blockState, this.getModelResourceLocation(blockState));
        }

        return this.mapStateModelLocations;
    }
}
