package net.minecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class ItemSoup extends ItemFood {
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        super.onItemUseFinish(itemStack, world, playerEntity);
        return new ItemStack(Items.bowl);
    }

    public ItemSoup(int i) {
        super(i, false);
        this.setMaxStackSize(1);
    }
}
