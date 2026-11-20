package net.minecraft.client.renderer;

import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

import com.google.common.collect.Maps;

import java.util.LinkedHashMap;

class StoneDoubleSlabStateMapper extends StateMapperBase {

    BlockModelShapes field_178139_a;


    StoneDoubleSlabStateMapper(BlockModelShapes aCdValue) {
        this.field_178139_a = aCdValue;
    }

    protected ModelResourceLocation getModelResourceLocation(BlockState blockState) {
        LinkedHashMap linkedhashmap = Maps.newLinkedHashMap(blockState.getProperties());
        String text =
                BlockStoneSlab.VARIANT.getName(
                        (Comparable) linkedhashmap.remove(BlockStoneSlab.VARIANT));
        linkedhashmap.remove(BlockStoneSlab.SEAMLESS);
        String textValue =
                ((java.lang.Boolean) blockState.getValue(BlockStoneSlab.SEAMLESS))
                        ? "all"
                        : "normal";
        return new ModelResourceLocation(text + "_double_slab", textValue);
    }
}
