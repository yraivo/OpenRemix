package net.minecraft.entity.player;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import java.util.concurrent.Callable;

public class InventoryPlayerItemNameCallable implements Callable {
    ItemStack uK;
    InventoryPlayer bkZ;

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public InventoryPlayerItemNameCallable(InventoryPlayer bkZValue, ItemStack itemStack) {
        this.bkZ = bkZValue;
        this.uK = itemStack;
    }

    public String getCrashDetail() {
        return this.uK.getDisplayName();
    }
}
