package net.minecraft.client.stream;

import net.minecraft.entity.LivingEntity;

public class MetadataCombat extends Metadata {


    public MetadataCombat(LivingEntity livingEntity, LivingEntity livingEntityValue) {
        this(livingEntity, livingEntityValue, IngestServerTester.sT());
    }

    private MetadataCombat(
            LivingEntity livingEntity, LivingEntity livingEntityValue, String initialFlowMarker) {
        super("player_combat");

        String text = initialFlowMarker;

        this.O("player", livingEntity.getName());
        String textValue = text;
        if (livingEntityValue != null) {
            this.O("primary_opponent", livingEntityValue.getName());
        }

        if (livingEntityValue != null) {
            this.setDescription(
                    "Combat between "
                            + livingEntity.getName()
                            + " and "
                            + livingEntityValue.getName());
            if (textValue == null) {
                return;
            }
        }

        this.setDescription("Combat between " + livingEntity.getName() + " and others");
    }
}
