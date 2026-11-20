package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemSeedFood extends ItemFood {
    private Block crops;
    private Block soilId;

    public ItemSeedFood(int p171_0, float p171_1, Block bnKValue, Block bnPValue) {
        super(p171_0, p171_1, false);
        this.crops = bnKValue;
        this.soilId = bnPValue;
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
        } else if (world.getBlockState(blockPos).getBlock() == this.soilId
                && world.isAirBlock(blockPos.offsetUp())) {
            world.setBlockState(blockPos.offsetUp(), this.crops.getDefaultState());
            itemStack.stackSize--;
            return true;
        } else {
            return false;
        }
    }
}
