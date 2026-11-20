package net.minecraft.client.renderer;

import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockQuartzEnumType;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

class QuartzBlockStateMapper extends StateMapperBase {

    BlockModelShapes field_178143_a;


    protected ModelResourceLocation getModelResourceLocation(BlockState blockState) {
        BlockQuartzEnumType value =
                (BlockQuartzEnumType) blockState.getValue(BlockQuartz.VARIANT_PROP);
        switch (value) {
            case DEFAULT:
            default:
                return new ModelResourceLocation("quartz_block", "normal");
            case CHISELED:
                return new ModelResourceLocation("chiseled_quartz_block", "normal");
            case LINES_Y:
                return new ModelResourceLocation("quartz_column", "axis=y");
            case LINES_X:
                return new ModelResourceLocation("quartz_column", "axis=x");
            case LINES_Z:
                return new ModelResourceLocation("quartz_column", "axis=z");
        }
    }

    QuartzBlockStateMapper(BlockModelShapes aCdValue) {
        this.field_178143_a = aCdValue;
    }
}
