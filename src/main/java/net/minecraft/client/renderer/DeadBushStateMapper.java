package net.minecraft.client.renderer;

import net.minecraft.block.state.BlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

class DeadBushStateMapper extends StateMapperBase {

    BlockModelShapes field_178136_a;

    protected ModelResourceLocation getModelResourceLocation(BlockState blockState) {
        return new ModelResourceLocation("dead_bush", "normal");
    }

    DeadBushStateMapper(BlockModelShapes aCdValue) {
        this.field_178136_a = aCdValue;
    }


}
