package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlantEnumPlantType;
import net.minecraft.world.ColorizerGrass;

import com.google.common.base.Function;

public class ItemDoublePlant extends ItemMultiTexture {
    public int getColorFromItemStack(ItemStack itemStack, int i) {
        BlockDoublePlantEnumPlantType blockDoublePlantEnumPlantType =
                BlockDoublePlantEnumPlantType.byMetadata(itemStack.getItemDamage());
        return blockDoublePlantEnumPlantType != BlockDoublePlantEnumPlantType.GRASS
                        && blockDoublePlantEnumPlantType != BlockDoublePlantEnumPlantType.FERN
                ? super.getColorFromItemStack(itemStack, i)
                : ColorizerGrass.getGrassColor(0.5, 1.0);
    }

    public ItemDoublePlant(Block block, Block blockValue, Function p171_2) {
        super(block, blockValue, p171_2);
    }
}
