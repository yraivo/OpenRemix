package net.minecraft.world.storage;

import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.WorldType;

public class WorldInfo {
    private String levelName;
    private int thunderTime;
    private long borderSizeLerpTime;
    private int borderWarningDistance;
    private int saveVersion;
    private boolean mapFeaturesEnabled;
    private EnumDifficulty difficulty;
    private boolean initialized;
    private int cleanWeatherTime;
    private int rainTime;
    private int spawnY;
    private static Entity[] bNZ;
    private boolean allowCommands;
    private boolean thundering;
    private double borderSize;
    private int spawnX;
    private GameRules theGameRules;

    private static String[] packedTextFragments;
    private int borderWarningTime;
    private long sizeOnDisk;
    private int spawnZ;
    public static EnumDifficulty DEFAULT_DIFFICULTY;
    private long lastTimePlayed;
    private long totalTime;

    private boolean difficultyLocked;
    private double borderCenterZ;
    private double borderCenterX;
    private WorldType terrainType = WorldType.DEFAULT;
    private WorldSettingsGameType theGameType;
    private boolean hardcore;
    private long randomSeed;
    private double borderSafeZone;
    private int dimension;
    private String generatorOptions;
    private CompoundTag playerTag;
    private double borderDamagePerBlock;
    private long worldTime;
    private double borderSizeLerpTarget;
    private boolean raining;

    public void setBorderLerpTarget(double ajeValue) {
        this.borderSizeLerpTarget = ajeValue;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void setGameType(WorldSettingsGameType bOdValue) {
        this.theGameType = bOdValue;
    }

    public void setSpawnY(int spawnYValue) {
        this.spawnY = spawnYValue;
    }

    static WorldSettingsGameType access$1400(WorldInfo worldInfo) {
        return worldInfo.theGameType;
    }

    public void setWorldTime(long worldTimeValue) {
        this.worldTime = worldTimeValue;
    }

    public void setTerrainType(WorldType bGyValue) {
        this.terrainType = bGyValue;
    }

    public long getWorldTotalTime() {
        return this.totalTime;
    }

    public int getCleanWeatherTime() {
        return this.cleanWeatherTime;
    }

    public int getThunderTime() {
        return this.thunderTime;
    }

    public double getBorderDamagePerBlock() {
        return this.borderDamagePerBlock;
    }

    public String getWorldName() {
        return this.levelName;
    }

    public WorldInfo(WorldSettings worldSettings, String text) {
        this.generatorOptions = packedTextFragments[2];
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;

        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = 5;
        this.borderWarningTime = 15;
        this.theGameRules = new GameRules();
        this.populateFromWorldSettings(worldSettings);
        this.levelName = text;
        this.difficulty = net.minecraft.world.storage.WorldInfo.DEFAULT_DIFFICULTY;
        this.initialized = false;
    }

    public int getSpawnX() {
        return this.spawnX;
    }

    protected WorldInfo() {
        this.generatorOptions = packedTextFragments[0];
        this.borderCenterX = 0.0;
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;

        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = 5;
        this.borderWarningTime = 15;
        this.theGameRules = new GameRules();
    }

    static boolean access$1500(WorldInfo worldInfo) {
        return worldInfo.hardcore;
    }

    static {
        net.minecraft.world.storage.WorldInfo.packedTextFragments = new String[5];
        net.minecraft.world.storage.WorldInfo.packedTextFragments[0] = new String("");
        net.minecraft.world.storage.WorldInfo.packedTextFragments[1] = new String("");
        net.minecraft.world.storage.WorldInfo.packedTextFragments[2] = new String("");
        net.minecraft.world.storage.WorldInfo.packedTextFragments[3] = new String("");
        Entity[] aclass_b6df81f333fa31dc98ea = new Entity[1];
        b(aclass_b6df81f333fa31dc98ea);
        net.minecraft.world.storage.WorldInfo.DEFAULT_DIFFICULTY = EnumDifficulty.NORMAL;
    }

    public void setDifficultyLocked(boolean aorValue) {
        this.difficultyLocked = aorValue;
    }

    static int access$1200(WorldInfo worldInfo) {
        return worldInfo.thunderTime;
    }

    public void setBorderWarningDistance(int borderWarningDistanceValue) {
        this.borderWarningDistance = borderWarningDistanceValue;
    }

    static boolean access$100(WorldInfo worldInfo) {
        return worldInfo.mapFeaturesEnabled;
    }

    public boolean isThundering() {
        return this.thundering;
    }

    // region String decoding support

    // endregion

    static String access$200(WorldInfo worldInfo) {
        return worldInfo.generatorOptions;
    }

    public void setSpawnX(int spawnXValue) {
        this.spawnX = spawnXValue;
    }

    public double getBorderSafeZone() {
        return this.borderSafeZone;
    }

    private void updateTagCompound(CompoundTag compoundTag, CompoundTag compoundTagValue) {
        compoundTag.setLong("RandomSeed", this.randomSeed);
        compoundTag.setString("generatorName", this.terrainType.getWorldTypeName());
        compoundTag.setInteger("generatorVersion", this.terrainType.getGeneratorVersion());
        compoundTag.setString("generatorOptions", this.generatorOptions);
        compoundTag.setInteger("GameType", this.theGameType.getID());
        compoundTag.setBoolean("MapFeatures", this.mapFeaturesEnabled);
        compoundTag.setInteger("SpawnX", this.spawnX);
        compoundTag.setInteger("SpawnY", this.spawnY);
        compoundTag.setInteger("SpawnZ", this.spawnZ);
        compoundTag.setLong("Time", this.totalTime);
        compoundTag.setLong("DayTime", this.worldTime);
        compoundTag.setLong("SizeOnDisk", this.sizeOnDisk);
        compoundTag.setLong("LastPlayed", MinecraftServer.getCurrentTimeMillis());
        compoundTag.setString("LevelName", this.levelName);
        compoundTag.setInteger("version", this.saveVersion);
        compoundTag.setInteger("clearWeatherTime", this.cleanWeatherTime);
        compoundTag.setInteger("rainTime", this.rainTime);
        compoundTag.setBoolean("raining", this.raining);
        compoundTag.setInteger("thunderTime", this.thunderTime);
        compoundTag.setBoolean("thundering", this.thundering);
        compoundTag.setBoolean("hardcore", this.hardcore);
        compoundTag.setBoolean("allowCommands", this.allowCommands);
        compoundTag.setBoolean("initialized", this.initialized);
        compoundTag.setDouble("BorderCenterX", this.borderCenterX);
        compoundTag.setDouble("BorderCenterZ", this.borderCenterZ);
        compoundTag.setDouble("BorderSize", this.borderSize);
        compoundTag.setLong("BorderSizeLerpTime", this.borderSizeLerpTime);
        compoundTag.setDouble("BorderSafeZone", this.borderSafeZone);
        compoundTag.setDouble("BorderDamagePerBlock", this.borderDamagePerBlock);
        compoundTag.setDouble("BorderSizeLerpTarget", this.borderSizeLerpTarget);
        compoundTag.setDouble("BorderWarningBlocks", this.borderWarningDistance);
        compoundTag.setDouble("BorderWarningTime", this.borderWarningTime);
        if (this.difficulty != null) {
            compoundTag.setByte("Difficulty", (byte) this.difficulty.getDifficultyId());
        }

        compoundTag.setBoolean("DifficultyLocked", this.difficultyLocked);
        compoundTag.setTag("GameRules", this.theGameRules.writeGameRulesToNBT());
        if (compoundTagValue != null) {
            compoundTag.setTag("Player", compoundTagValue);
        }
    }

    public void setServerInitialized(boolean initializedValue) {
        this.initialized = initializedValue;
    }

    public void setAllowCommands(boolean allowCommandsValue) {
        this.allowCommands = allowCommandsValue;
    }

    static boolean access$1600(WorldInfo worldInfo) {
        return worldInfo.allowCommands;
    }

    public int getSpawnY() {
        return this.spawnY;
    }

    static boolean access$1100(WorldInfo worldInfo) {
        return worldInfo.raining;
    }

    public boolean isMapFeaturesEnabled() {
        return this.mapFeaturesEnabled;
    }

    public long getLastTimePlayed() {
        return this.lastTimePlayed;
    }

    public CompoundTag cloneNBTCompound(CompoundTag compoundTag) {
        CompoundTag compoundTagValue = new CompoundTag();
        this.updateTagCompound(compoundTagValue, compoundTag);
        return compoundTagValue;
    }

    public void setBorderLerpTime(long borderSizeLerpTimeValue) {
        this.borderSizeLerpTime = borderSizeLerpTimeValue;
    }

    public void setSaveVersion(int saveVersionValue) {
        this.saveVersion = saveVersionValue;
    }

    public GameRules getGameRulesInstance() {
        return this.theGameRules;
    }

    public String getGeneratorOptions() {
        return this.generatorOptions;
    }

    public void setBorderSize(double bOaValue) {
        this.borderSize = bOaValue;
    }

    public void setRaining(boolean rainingValue) {
        this.raining = rainingValue;
    }

    public void setThunderTime(int thunderTimeValue) {
        this.thunderTime = thunderTimeValue;
    }

    public int getBorderWarningTime() {
        return this.borderWarningTime;
    }

    static boolean access$1300(WorldInfo worldInfo) {
        return worldInfo.thundering;
    }

    static int access$500(WorldInfo worldInfo) {
        return worldInfo.spawnZ;
    }

    public double getBorderSize() {
        return this.borderSize;
    }

    static WorldType access$000(WorldInfo worldInfo) {
        return worldInfo.terrainType;
    }

    public void setThundering(boolean thunderingValue) {
        this.thundering = thunderingValue;
    }

    public void incrementTotalWorldTime(long totalTimeValue) {
        this.totalTime = totalTimeValue;
    }

    public void setSpawnZ(int spawnZValue) {
        this.spawnZ = spawnZValue;
    }

    public int getBorderWarningDistance() {
        return this.borderWarningDistance;
    }

    public long getSeed() {
        return this.randomSeed;
    }

    public void setRainTime(int rainTimeValue) {
        this.rainTime = rainTimeValue;
    }

    public boolean isRaining() {
        return this.raining;
    }

    public int getRainTime() {
        return this.rainTime;
    }

    public long getBorderLerpTime() {
        return this.borderSizeLerpTime;
    }

    public void setHardcore(boolean hardcoreValue) {
        this.hardcore = hardcoreValue;
    }

    static long access$700(WorldInfo worldInfo) {
        return worldInfo.worldTime;
    }

    public EnumDifficulty getDifficulty() {
        return this.difficulty;
    }

    public CompoundTag getPlayerNBTTagCompound() {
        return this.playerTag;
    }

    public double getBorderCenterZ() {
        return this.borderCenterZ;
    }

    public void addToCrashReport(CrashReportCategory crashReportCategory) {
        crashReportCategory.addCrashSectionCallable("Level seed", new WorldSeedCrashDetail(this));
        crashReportCategory.addCrashSectionCallable(
                "Level generator", new WorldGeneratorCrashDetail(this));
        crashReportCategory.addCrashSectionCallable(
                "Level generator options", new WorldGeneratorOptionsCrashDetail(this));
        crashReportCategory.addCrashSectionCallable(
                "Level spawn location", new WorldSpawnCrashDetail(this));
        crashReportCategory.addCrashSectionCallable("Level time", new WorldTimeCrashDetail(this));
        crashReportCategory.addCrashSectionCallable(
                "Level dimension", new WorldDimensionCrashDetail(this));
        crashReportCategory.addCrashSectionCallable(
                "Level storage version", new WorldStorageVersionCrashDetail(this));
        crashReportCategory.addCrashSectionCallable(
                "Level weather", new WorldWeatherCrashDetail(this));
        crashReportCategory.addCrashSectionCallable(
                "Level game mode", new WorldGameModeCrashDetail(this));
    }

    public static Entity[] YP() {
        return bNZ;
    }

    public CompoundTag getNBTTagCompound() {
        CompoundTag compoundTag = new CompoundTag();
        this.updateTagCompound(compoundTag, this.playerTag);
        return compoundTag;
    }

    public static void b(Entity[] bNZValue) {
        bNZ = bNZValue;
    }

    public WorldInfo(CompoundTag compoundTag) {
        this.generatorOptions = packedTextFragments[1];
        this.borderCenterX = 0.0;
        YP();

        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;

        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = 5;
        this.borderWarningTime = 15;
        this.theGameRules = new GameRules();
        this.randomSeed = compoundTag.getLong("RandomSeed");

        if (compoundTag.hasKey("generatorName", 8)) {
            {
                String text = compoundTag.getString("generatorName");
                this.terrainType = WorldType.parseWorldType(text);
                if (this.terrainType == null) {
                    this.terrainType = WorldType.DEFAULT;

                } else {

                    if (this.terrainType.isVersioned()) {
                        int i = 0;
                        if (compoundTag.hasKey("generatorVersion", 99)) {
                            i = compoundTag.getInteger("generatorVersion");
                        }

                        this.terrainType = this.terrainType.getWorldTypeForGeneratorVersion(i);
                    }
                }
            }

            if (compoundTag.hasKey("generatorOptions", 8)) {
                this.generatorOptions = compoundTag.getString("generatorOptions");
            }
        }

        {
            this.theGameType = WorldSettingsGameType.getByID(compoundTag.getInteger("GameType"));
            if (compoundTag.hasKey("MapFeatures", 99)) {
                this.mapFeaturesEnabled = compoundTag.getBoolean("MapFeatures");

            } else {

                this.mapFeaturesEnabled = true;
            }
        }

        {
            this.spawnX = compoundTag.getInteger("SpawnX");
            this.spawnY = compoundTag.getInteger("SpawnY");
            this.spawnZ = compoundTag.getInteger("SpawnZ");
            this.totalTime = compoundTag.getLong("Time");
            if (compoundTag.hasKey("DayTime", 99)) {
                this.worldTime = compoundTag.getLong("DayTime");

            } else {

                this.worldTime = this.totalTime;
            }
        }

        {
            this.lastTimePlayed = compoundTag.getLong("LastPlayed");
            this.sizeOnDisk = compoundTag.getLong("SizeOnDisk");
            this.levelName = compoundTag.getString("LevelName");
            this.saveVersion = compoundTag.getInteger("version");
            this.cleanWeatherTime = compoundTag.getInteger("clearWeatherTime");
            this.rainTime = compoundTag.getInteger("rainTime");
            this.raining = compoundTag.getBoolean("raining");
            this.thunderTime = compoundTag.getInteger("thunderTime");
            this.thundering = compoundTag.getBoolean("thundering");
            this.hardcore = compoundTag.getBoolean("hardcore");
            if (compoundTag.hasKey("initialized", 99)) {
                this.initialized = compoundTag.getBoolean("initialized");

            } else {

                this.initialized = true;
            }
        }

        {
            if (compoundTag.hasKey("allowCommands", 99)) {
                this.allowCommands = compoundTag.getBoolean("allowCommands");

            } else {

                this.allowCommands =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (this.theGameType == WorldSettingsGameType.CREATIVE ? 1 : 0));
            }
        }

        if (compoundTag.hasKey("Player", 10)) {
            this.playerTag = compoundTag.getCompoundTag("Player");
            this.dimension = this.playerTag.getInteger("Dimension");
        }

        if (compoundTag.hasKey("GameRules", 10)) {
            this.theGameRules.readFromNBT(compoundTag.getCompoundTag("GameRules"));
        }

        if (compoundTag.hasKey("Difficulty", 99)) {
            this.difficulty = EnumDifficulty.getDifficultyEnum(compoundTag.getByte("Difficulty"));
        }

        if (compoundTag.hasKey("DifficultyLocked", 1)) {
            this.difficultyLocked = compoundTag.getBoolean("DifficultyLocked");
        }

        if (compoundTag.hasKey("BorderCenterX", 99)) {
            this.borderCenterX = compoundTag.getDouble("BorderCenterX");
        }

        if (compoundTag.hasKey("BorderCenterZ", 99)) {
            this.borderCenterZ = compoundTag.getDouble("BorderCenterZ");
        }

        if (compoundTag.hasKey("BorderSize", 99)) {
            this.borderSize = compoundTag.getDouble("BorderSize");
        }

        if (compoundTag.hasKey("BorderSizeLerpTime", 99)) {
            this.borderSizeLerpTime = compoundTag.getLong("BorderSizeLerpTime");
        }

        if (compoundTag.hasKey("BorderSizeLerpTarget", 99)) {
            this.borderSizeLerpTarget = compoundTag.getDouble("BorderSizeLerpTarget");
        }

        if (compoundTag.hasKey("BorderSafeZone", 99)) {
            this.borderSafeZone = compoundTag.getDouble("BorderSafeZone");
        }

        if (compoundTag.hasKey("BorderDamagePerBlock", 99)) {
            this.borderDamagePerBlock = compoundTag.getDouble("BorderDamagePerBlock");
        }

        if (compoundTag.hasKey("BorderWarningBlocks", 99)) {
            this.borderWarningDistance = compoundTag.getInteger("BorderWarningBlocks");
        }

        if (compoundTag.hasKey("BorderWarningTime", 99)) {
            this.borderWarningTime = compoundTag.getInteger("BorderWarningTime");
        }
    }

    public boolean isDifficultyLocked() {
        return this.difficultyLocked;
    }

    public WorldSettingsGameType getGameType() {
        return this.theGameType;
    }

    public int getSpawnZ() {
        return this.spawnZ;
    }

    public void getBorderCenterZ(double borderCenterZValue) {
        this.borderCenterZ = borderCenterZValue;
    }

    static int access$300(WorldInfo worldInfo) {
        return worldInfo.spawnX;
    }

    static long access$600(WorldInfo worldInfo) {
        return worldInfo.totalTime;
    }

    static int access$900(WorldInfo worldInfo) {
        return worldInfo.saveVersion;
    }

    public void setWorldName(String text) {
        this.levelName = text;
    }

    public void setBorderDamagePerBlock(double borderDamagePerBlockValue) {
        this.borderDamagePerBlock = borderDamagePerBlockValue;
    }

    public void setDifficulty(EnumDifficulty bNYValue) {
        this.difficulty = bNYValue;
    }

    public void getBorderCenterX(double borderCenterXValue) {
        this.borderCenterX = borderCenterXValue;
    }

    static int access$400(WorldInfo worldInfo) {
        return worldInfo.spawnY;
    }

    public void setSpawn(BlockPos blockPos) {
        this.spawnX = blockPos.getX();
        this.spawnY = blockPos.getY();
        this.spawnZ = blockPos.getZ();
    }

    public void setBorderWarningTime(int borderWarningTimeValue) {
        this.borderWarningTime = borderWarningTimeValue;
    }

    public long getSizeOnDisk() {
        return this.sizeOnDisk;
    }

    public void setMapFeaturesEnabled(boolean mapFeaturesEnabledValue) {
        this.mapFeaturesEnabled = mapFeaturesEnabledValue;
    }

    public double getBorderLerpTarget() {
        return this.borderSizeLerpTarget;
    }

    public void populateFromWorldSettings(WorldSettings worldSettings) {
        this.randomSeed = worldSettings.getSeed();
        this.theGameType = worldSettings.getGameType();
        this.mapFeaturesEnabled = worldSettings.isMapFeaturesEnabled();
        this.hardcore = worldSettings.getHardcoreEnabled();
        this.terrainType = worldSettings.getTerrainType();
        this.generatorOptions = worldSettings.getWorldName();
        this.allowCommands = worldSettings.areCommandsAllowed();
    }

    public boolean areCommandsAllowed() {
        return this.allowCommands;
    }

    public long getWorldTime() {
        return this.worldTime;
    }

    static int access$800(WorldInfo worldInfo) {
        return worldInfo.dimension;
    }

    public void setCleanWeatherTime(int cleanWeatherTimeValue) {
        this.cleanWeatherTime = cleanWeatherTimeValue;
    }

    public boolean isHardcoreModeEnabled() {
        return this.hardcore;
    }

    // region String decoding support

    // endregion

    static int access$1000(WorldInfo worldInfo) {
        return worldInfo.rainTime;
    }

    public WorldType getTerrainType() {
        return this.terrainType;
    }

    // region String decoding support

    // endregion

    public int getSaveVersion() {
        return this.saveVersion;
    }

    public double getBorderCenterX() {
        return this.borderCenterX;
    }

    public void setBorderSafeZone(double borderSafeZoneValue) {
        this.borderSafeZone = borderSafeZoneValue;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public WorldInfo(WorldInfo worldInfo) {
        super();
        this.generatorOptions = packedTextFragments[3];
        this.borderCenterX = 0.0;
        YP();
        this.borderCenterZ = 0.0;
        this.borderSize = 6.0E7;

        this.borderSizeLerpTime = 0L;
        this.borderSizeLerpTarget = 0.0;
        this.borderSafeZone = 5.0;
        this.borderDamagePerBlock = 0.2;
        this.borderWarningDistance = 5;
        this.borderWarningTime = 15;
        this.theGameRules = new GameRules();
        this.randomSeed = worldInfo.randomSeed;
        this.terrainType = worldInfo.terrainType;
        this.generatorOptions = worldInfo.generatorOptions;
        this.theGameType = worldInfo.theGameType;
        this.mapFeaturesEnabled = worldInfo.mapFeaturesEnabled;
        this.spawnX = worldInfo.spawnX;
        this.spawnY = worldInfo.spawnY;
        this.spawnZ = worldInfo.spawnZ;
        this.totalTime = worldInfo.totalTime;
        this.worldTime = worldInfo.worldTime;
        this.lastTimePlayed = worldInfo.lastTimePlayed;
        this.sizeOnDisk = worldInfo.sizeOnDisk;
        this.playerTag = worldInfo.playerTag;
        this.dimension = worldInfo.dimension;
        this.levelName = worldInfo.levelName;
        this.saveVersion = worldInfo.saveVersion;
        this.rainTime = worldInfo.rainTime;
        this.raining = worldInfo.raining;
        this.thunderTime = worldInfo.thunderTime;
        this.thundering = worldInfo.thundering;
        this.hardcore = worldInfo.hardcore;
        this.allowCommands = worldInfo.allowCommands;
        this.initialized = worldInfo.initialized;
        this.theGameRules = worldInfo.theGameRules;
        this.difficulty = worldInfo.difficulty;
        this.difficultyLocked = worldInfo.difficultyLocked;
        this.borderCenterX = worldInfo.borderCenterX;
        this.borderCenterZ = worldInfo.borderCenterZ;
        this.borderSize = worldInfo.borderSize;
        this.borderSizeLerpTime = worldInfo.borderSizeLerpTime;
        this.borderSizeLerpTarget = worldInfo.borderSizeLerpTarget;
        this.borderSafeZone = worldInfo.borderSafeZone;
        this.borderDamagePerBlock = worldInfo.borderDamagePerBlock;
        this.borderWarningTime = worldInfo.borderWarningTime;
        this.borderWarningDistance = worldInfo.borderWarningDistance;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(new Entity[4]);
        }
    }
}
