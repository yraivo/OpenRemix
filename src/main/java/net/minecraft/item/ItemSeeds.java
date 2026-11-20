package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemSeeds extends Item {
    private Block crops;
    private Block soilBlockID;

    public ItemSeeds(Block blWValue, Block bmcValue) {
        this.crops = blWValue;
        this.soilBlockID = bmcValue;
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float p171_5,
            float p171_6,
            float p171_7) {
        if (facing != net.minecraft.util.Facing.UP) {
            return false;
        } else if (!playerEntity.canPlayerEdit(blockPos.offset(facing), facing, itemStack)) {
            return false;
        } else if (world.getBlockState(blockPos).getBlock() == this.soilBlockID
                && world.isAirBlock(blockPos.offsetUp())) {
            world.setBlockState(blockPos.offsetUp(), this.crops.getDefaultState());
            itemStack.stackSize--;
            return true;
        } else {
            return false;
        }
    }
}
