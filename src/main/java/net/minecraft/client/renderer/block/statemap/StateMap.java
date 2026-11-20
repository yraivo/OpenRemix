package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Maps;

import java.util.LinkedHashMap;
import java.util.List;

public class StateMap extends StateMapperBase {
    private List ignored;
    private String suffix;
    private IProperty name;

    protected ModelResourceLocation getModelResourceLocation(BlockState blockState) {
        int[] aint;
        LinkedHashMap linkedhashmap;
        String text;
        {
            int[] aint1 = StateMapperBase.method_650532ba484532b3efb0();
            linkedhashmap = Maps.newLinkedHashMap(blockState.getProperties());
            aint = aint1;
            if (this.name == null) {
                text =
                        ((ResourceLocation)
                                        Block.blockRegistry.getNameForObject(blockState.getBlock()))
                                .toString();

                
            } else {

                text = this.name.getName((Comparable) linkedhashmap.remove(this.name));
            }
        }

        if (this.suffix != null) {
            text = text + this.suffix;
        }

        for (java.util.Iterator<?> __recoveryIterator171_1341 = (this.ignored).iterator();
                (__recoveryIterator171_1341).hasNext(); ) {
            IProperty iProperty = (IProperty) (__recoveryIterator171_1341).next();
            linkedhashmap.remove(iProperty);
        }

        return new ModelResourceLocation(text, this.getPropertyString(linkedhashmap));
    }

    private StateMap(IProperty aChValue, String text, List ignoredValue) {
        this.name = aChValue;
        this.suffix = text;
        this.ignored = ignoredValue;
    }

    StateMap(IProperty iProperty, String text, List list, Object object) {
        this(iProperty, text, list);
    }
}
