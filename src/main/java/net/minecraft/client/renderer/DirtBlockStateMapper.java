package net.minecraft.client.renderer;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirtDirtType;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

import com.google.common.collect.Maps;

import java.util.LinkedHashMap;

class DirtBlockStateMapper extends StateMapperBase {
    BlockModelShapes field_178135_a;

    protected ModelResourceLocation getModelResourceLocation(BlockState blockState) {
        LinkedHashMap linkedhashmap = Maps.newLinkedHashMap(blockState.getProperties());
        String text =
                BlockDirt.VARIANT.getName((Comparable) linkedhashmap.remove(BlockDirt.VARIANT));
        if (BlockDirtDirtType.PODZOL != blockState.getValue(BlockDirt.VARIANT)) {
            linkedhashmap.remove(BlockDirt.SNOWY);
        }

        return new ModelResourceLocation(text, this.getPropertyString(linkedhashmap));
    }

    DirtBlockStateMapper(BlockModelShapes aCdValue) {
        this.field_178135_a = aCdValue;
    }
}
