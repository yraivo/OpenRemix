package net.minecraft.world;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Container;

public interface IInteractionObject extends IWorldNameable {
    Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity);

    String getGuiID();
}
