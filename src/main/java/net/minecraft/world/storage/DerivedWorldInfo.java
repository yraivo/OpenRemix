package net.minecraft.world.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.WorldType;

public class DerivedWorldInfo extends WorldInfo {
    private WorldInfo theWorldInfo;

    public void setSpawnX(int i) {}

    public WorldSettingsGameType getGameType() {
        return this.theWorldInfo.getGameType();
    }

    public boolean isInitialized() {
        return this.theWorldInfo.isInitialized();
    }

    public void setAllowCommands(boolean flag) {}

    public boolean isDifficultyLocked() {
        return this.theWorldInfo.isDifficultyLocked();
    }

    public int getSpawnY() {
        return this.theWorldInfo.getSpawnY();
    }

    public void setThundering(boolean flag) {}

    public CompoundTag cloneNBTCompound(CompoundTag compoundTag) {
        return this.theWorldInfo.cloneNBTCompound(compoundTag);
    }

    public void setSpawn(BlockPos blockPos) {}

    public long getSizeOnDisk() {
        return this.theWorldInfo.getSizeOnDisk();
    }

    public CompoundTag getPlayerNBTTagCompound() {
        return this.theWorldInfo.getPlayerNBTTagCompound();
    }

    public void setTerrainType(WorldType worldType) {}

    public void setDifficulty(EnumDifficulty enumDifficulty) {}

    public void setSpawnY(int i) {}

    public long getWorldTime() {
        return this.theWorldInfo.getWorldTime();
    }

    public GameRules getGameRulesInstance() {
        return this.theWorldInfo.getGameRulesInstance();
    }

    public String getWorldName() {
        return this.theWorldInfo.getWorldName();
    }

    public int getThunderTime() {
        return this.theWorldInfo.getThunderTime();
    }

    public boolean isMapFeaturesEnabled() {
        return this.theWorldInfo.isMapFeaturesEnabled();
    }

    public int getSaveVersion() {
        return this.theWorldInfo.getSaveVersion();
    }

    public long getLastTimePlayed() {
        return this.theWorldInfo.getLastTimePlayed();
    }

    public void setWorldName(String text) {}

    public boolean isThundering() {
        return this.theWorldInfo.isThundering();
    }

    public EnumDifficulty getDifficulty() {
        return this.theWorldInfo.getDifficulty();
    }

    public void setWorldTime(long i) {}

    public int getSpawnZ() {
        return this.theWorldInfo.getSpawnZ();
    }

    public void setServerInitialized(boolean flag) {}

    public void setSaveVersion(int i) {}

    public int getSpawnX() {
        return this.theWorldInfo.getSpawnX();
    }

    public void setRaining(boolean flag) {}

    public boolean isRaining() {
        return this.theWorldInfo.isRaining();
    }

    public void setRainTime(int i) {}

    public void setThunderTime(int i) {}

    public long getWorldTotalTime() {
        return this.theWorldInfo.getWorldTotalTime();
    }

    public WorldType getTerrainType() {
        return this.theWorldInfo.getTerrainType();
    }

    public void setSpawnZ(int i) {}

    public void setDifficultyLocked(boolean flag) {}

    public boolean areCommandsAllowed() {
        return this.theWorldInfo.areCommandsAllowed();
    }

    public CompoundTag getNBTTagCompound() {
        return this.theWorldInfo.getNBTTagCompound();
    }

    public int getRainTime() {
        return this.theWorldInfo.getRainTime();
    }

    public long getSeed() {
        return this.theWorldInfo.getSeed();
    }

    public boolean isHardcoreModeEnabled() {
        return this.theWorldInfo.isHardcoreModeEnabled();
    }

    public DerivedWorldInfo(WorldInfo bOfValue) {
        this.theWorldInfo = bOfValue;
    }

    public void incrementTotalWorldTime(long i) {}
}
