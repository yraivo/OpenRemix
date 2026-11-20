package net.minecraft.client.renderer;

import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

import com.google.common.collect.Maps;

import java.util.LinkedHashMap;

class RedSandstoneDoubleSlabStateMapper extends StateMapperBase {
    BlockModelShapes field_178138_a;


    RedSandstoneDoubleSlabStateMapper(BlockModelShapes aCdValue) {
        this.field_178138_a = aCdValue;
    }

    protected ModelResourceLocation getModelResourceLocation(BlockState blockState) {
        LinkedHashMap linkedhashmap = Maps.newLinkedHashMap(blockState.getProperties());
        String text =
                BlockStoneSlabNew.VARIANT.getName(
                        (Comparable) linkedhashmap.remove(BlockStoneSlabNew.VARIANT));
        linkedhashmap.remove(BlockStoneSlab.SEAMLESS);
        String textValue =
                ((java.lang.Boolean) blockState.getValue(BlockStoneSlabNew.SEAMLESS))
                        ? "all"
                        : "normal";
        return new ModelResourceLocation(text + "_double_slab", textValue);
    }
}
