package net.minecraft.client.stream;

import net.minecraft.entity.LivingEntity;

public class MetadataPlayerDeath extends Metadata {

    public MetadataPlayerDeath(LivingEntity livingEntity, LivingEntity livingEntityValue) {
        super("player_death");
        if (livingEntity != null) {
            this.O("player", livingEntity.getName());
        }

        if (livingEntityValue != null) {
            this.O("killer", livingEntityValue.getName());
        }
    }


}
