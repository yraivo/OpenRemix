package net.minecraft.world;

import net.minecraft.world.storage.WorldInfo;

public final class WorldSettings {
    private long seed;
    private boolean mapFeaturesEnabled;
    private boolean commandsAllowed;
    private WorldSettingsGameType theGameType;
    private WorldType terrainType;
    private static String[] packedTextFragments;
    private boolean hardcoreEnabled;
    private boolean bonusChestEnabled;
    private String worldName;

    public WorldSettings setWorldName(String text) {
        this.worldName = text;
        return this;
    }

    public boolean isMapFeaturesEnabled() {
        return this.mapFeaturesEnabled;
    }

    public boolean isBonusChestEnabled() {
        return this.bonusChestEnabled;
    }

    public boolean areCommandsAllowed() {
        return this.commandsAllowed;
    }

    public long getSeed() {
        return this.seed;
    }

    public WorldType getTerrainType() {
        return this.terrainType;
    }

    // region String decoding support

    // endregion

    public WorldSettings(WorldInfo worldInfo) {
        this(
                worldInfo.getSeed(),
                worldInfo.getGameType(),
                worldInfo.isMapFeaturesEnabled(),
                worldInfo.isHardcoreModeEnabled(),
                worldInfo.getTerrainType());
    }

    public WorldSettings enableCommands() {
        this.commandsAllowed = true;
        return this;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public WorldSettings enableBonusChest() {
        this.bonusChestEnabled = true;
        return this;
    }

    public boolean getHardcoreEnabled() {
        return this.hardcoreEnabled;
    }

    public static WorldSettingsGameType getGameTypeById(int i) {
        return WorldSettingsGameType.getByID(i);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.world.WorldSettings.packedTextFragments[0] = new String("");
    }

    // endregion

    public WorldSettingsGameType getGameType() {
        return this.theGameType;
    }

    public WorldSettings(
            long seedValue,
            WorldSettingsGameType avVValue,
            boolean mapFeaturesEnabledValue,
            boolean hardcoreEnabledValue,
            WorldType bFGValue) {
        this.worldName = packedTextFragments[0];
        this.seed = seedValue;
        this.theGameType = avVValue;
        this.mapFeaturesEnabled = mapFeaturesEnabledValue;
        this.hardcoreEnabled = hardcoreEnabledValue;
        this.terrainType = bFGValue;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }
}
