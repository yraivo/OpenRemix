package net.minecraft.entity.passive;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Container;

class SheepDyeMixingContainer extends Container {
    EntitySheep field_90034_a;

    SheepDyeMixingContainer(EntitySheep bszValue) {
        this.field_90034_a = bszValue;
    }

    public boolean canInteractWith(PlayerEntity playerEntity) {
        return false;
    }
}
