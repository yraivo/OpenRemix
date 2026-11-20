package net.minecraft.world;

public class WorldType {

    public static WorldType AMPLIFIED;
    public static WorldType DEFAULT;
    public static WorldType DEFAULT_1_1;
    private int generatorVersion;
    private boolean isWorldTypeVersioned;

    public static WorldType FLAT;
    private boolean hasNotificationData;
    private String worldType;
    private int worldTypeId;
    private boolean canBeCreated;
    public static WorldType LARGE_BIOMES;
    public static WorldType CUSTOMIZED;

    public static WorldType DEBUG_WORLD;
    public static WorldType[] worldTypes;

    public int getGeneratorVersion() {
        return this.generatorVersion;
    }

    static {
        net.minecraft.world.WorldType.worldTypes = new WorldType[16];
        net.minecraft.world.WorldType.DEFAULT = new WorldType(0, "default", 1).setVersioned();
        net.minecraft.world.WorldType.FLAT = new WorldType(1, "flat");
        net.minecraft.world.WorldType.LARGE_BIOMES = new WorldType(2, "largeBiomes");
        net.minecraft.world.WorldType.AMPLIFIED = new WorldType(3, "amplified").setNotificationData();
        net.minecraft.world.WorldType.CUSTOMIZED = new WorldType(4, "customized");
        net.minecraft.world.WorldType.DEBUG_WORLD = new WorldType(5, "debug_all_block_states");
        net.minecraft.world.WorldType.DEFAULT_1_1 = new WorldType(8, "default_1_1", 0).setCanBeCreated(false);
    }

    private WorldType(int worldTypeIdValue, String text, int j) {
        this.worldType = text;
        this.generatorVersion = j;
        this.canBeCreated = true;
        this.worldTypeId = worldTypeIdValue;
        net.minecraft.world.WorldType.worldTypes[worldTypeIdValue] = this;
    }

    private WorldType setVersioned() {
        this.isWorldTypeVersioned = true;
        return this;
    }

    private WorldType setNotificationData() {
        this.hasNotificationData = true;
        return this;
    }

    public String func_151359_c() {
        return this.getTranslateName() + ".info";
    }

    public boolean showWorldInfoNotice() {
        return this.hasNotificationData;
    }

    public String getTranslateName() {
        return "generator." + this.worldType;
    }

    public WorldType getWorldTypeForGeneratorVersion(int i) {
        return this == net.minecraft.world.WorldType.DEFAULT && i == 0 ? net.minecraft.world.WorldType.DEFAULT_1_1 : this;
    }

    public boolean getCanBeCreated() {
        return this.canBeCreated;
    }

    private WorldType setCanBeCreated(boolean canBeCreatedValue) {
        this.canBeCreated = canBeCreatedValue;
        return this;
    }

    public int getWorldTypeID() {
        return this.worldTypeId;
    }

    private WorldType(int i, String text) {
        this(i, text, 0);
    }

    public boolean isVersioned() {
        return this.isWorldTypeVersioned;
    }

    public static WorldType parseWorldType(String text) {
        World.getLegacyWorldFlowMarker();

        int i = 0;

        while (i < net.minecraft.world.WorldType.worldTypes.length) {
            if (net.minecraft.world.WorldType.worldTypes[i] != null
                    && net.minecraft.world.WorldType.worldTypes[i].worldType.equalsIgnoreCase(text)) {
                return net.minecraft.world.WorldType.worldTypes[i];
            }

            i++;
        }

        return null;
    }

    public String getWorldTypeName() {
        return this.worldType;
    }
}
