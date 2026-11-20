package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.Facing;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Maps;

import java.util.LinkedHashMap;

class MelonStemStateMapper extends StateMapperBase {
    BlockModelShapes field_178134_a;

    MelonStemStateMapper(BlockModelShapes aCdValue) {
        this.field_178134_a = aCdValue;
    }

    protected ModelResourceLocation getModelResourceLocation(BlockState blockState) {
        LinkedHashMap linkedhashmap = Maps.newLinkedHashMap(blockState.getProperties());
        if (blockState.getValue(BlockStem.FACING_PROP) != Facing.UP) {
            linkedhashmap.remove(BlockStem.AGE_PROP);
        }

        return new ModelResourceLocation(
                (ResourceLocation) Block.blockRegistry.getNameForObject(blockState.getBlock()),
                this.getPropertyString(linkedhashmap));
    }
}
