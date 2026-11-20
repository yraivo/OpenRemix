package net.minecraft.entity;

public enum EntityLivingSpawnPlacementType {
    ON_GROUND("ON_GROUND", 0, "ON_GROUND", 0),
    IN_AIR("IN_AIR", 1, "IN_AIR", 1),
    IN_WATER("IN_WATER", 2, "IN_WATER", 2);

    private static EntityLivingSpawnPlacementType[] VALUES;

    // region String decoding support

    // endregion

    EntityLivingSpawnPlacementType(String text, int j, String textValue, int k) {}

    static {
        net.minecraft.entity.EntityLivingSpawnPlacementType.VALUES =
                new EntityLivingSpawnPlacementType[] {
                    net.minecraft.entity.EntityLivingSpawnPlacementType.ON_GROUND,
                    net.minecraft.entity.EntityLivingSpawnPlacementType.IN_AIR,
                    net.minecraft.entity.EntityLivingSpawnPlacementType.IN_WATER
                };
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
