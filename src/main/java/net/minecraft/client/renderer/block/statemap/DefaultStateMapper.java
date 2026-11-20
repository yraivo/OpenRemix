package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

public class DefaultStateMapper extends StateMapperBase {
    protected ModelResourceLocation getModelResourceLocation(BlockState blockState) {
        return new ModelResourceLocation(
                (ResourceLocation) Block.blockRegistry.getNameForObject(blockState.getBlock()),
                this.getPropertyString(blockState.getProperties()));
    }
}
