package net.minecraft.entity.item;

import com.google.common.collect.Maps;

import java.util.Map;

public enum EntityMinecartEnumMinecartType {
    RIDEABLE(initializeConstantState(), 0, 0, "MinecartRideable"),
    CHEST("CHEST", 1, 1, "MinecartChest"),
    FURNACE("FURNACE", 2, 2, "MinecartFurnace"),
    TNT("TNT", 3, 3, "MinecartTNT"),
    SPAWNER("SPAWNER", 4, 4, "MinecartSpawner"),
    HOPPER("HOPPER", 5, 5, "MinecartHopper"),
    COMMAND_BLOCK("COMMAND_BLOCK", 6, 6, "MinecartCommandBlock");
    private static EntityMinecartEnumMinecartType[] afu;

    private static EntityMinecartEnumMinecartType[] afs;

    private static boolean f;
    private String name;
    private static Map ID_LOOKUP;

    private int networkID;

    public static boolean method_d87039b07bd6961907f2() {
        return f;
    }

    EntityMinecartEnumMinecartType(String text, int k, int j, String textValue) {
        this.networkID = j;
        this.name = textValue;
    }

    public int getNetworkID() {
        return this.networkID;
    }

    static {
        afu =
                new EntityMinecartEnumMinecartType[] {
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.RIDEABLE,
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.CHEST,
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.FURNACE,
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.TNT,
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.SPAWNER,
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.HOPPER,
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.COMMAND_BLOCK
                };
        afs =
                new EntityMinecartEnumMinecartType[] {
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.RIDEABLE,
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.CHEST,
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.FURNACE,
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.TNT,
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.SPAWNER,
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.HOPPER,
                    net.minecraft.entity.item.EntityMinecartEnumMinecartType.COMMAND_BLOCK
                };
        net.minecraft.entity.item.EntityMinecartEnumMinecartType.ID_LOOKUP = Maps.newHashMap();
        for (EntityMinecartEnumMinecartType class_770e55a99108f8fc2793 : values()) {
            net.minecraft.entity.item.EntityMinecartEnumMinecartType.ID_LOOKUP.put(
                    class_770e55a99108f8fc2793.getNetworkID(), class_770e55a99108f8fc2793);
        }
    }

    // region String decoding support

    // endregion

    public static boolean d() {
        boolean flag = method_d87039b07bd6961907f2();
        return !flag;
    }

    public static EntityMinecartEnumMinecartType byNetworkID(int i) {
        EntityMinecartEnumMinecartType entityMinecartEnumMinecartType =
                (EntityMinecartEnumMinecartType)
                        net.minecraft.entity.item.EntityMinecartEnumMinecartType.ID_LOOKUP.get(i);
        return entityMinecartEnumMinecartType == null
                ? net.minecraft.entity.item.EntityMinecartEnumMinecartType.RIDEABLE
                : entityMinecartEnumMinecartType;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        f = flag;
    }

    public String getName() {
        return this.name;
    }

    private static EntityMinecartEnumMinecartType[] __recovery_values171() {
        return afu.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        method_a8745b073fa8adfa1857(true);
        return "RIDEABLE";
    }
}
