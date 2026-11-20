package net.minecraft.entity.passive;

import net.minecraft.entity.passive.EntityPig;

public class EntityPigExhaustionThread extends Thread {
    EntityPig entityPig;

    @Override
    public void run() {
        // Local recovery disables the recursive thread exhaustion callback.
        return;
    }

    public EntityPigExhaustionThread(EntityPig bNFValue) {
        this.entityPig = bNFValue;
    }
}
