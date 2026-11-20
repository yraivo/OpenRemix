package net.minecraft.world.storage;

import net.minecraft.world.WorldSettingsGameType;

public class SaveFormatComparator implements Comparable {
    private long sizeOnDisk;
    private String displayName;
    private String fileName;
    private long lastTimePlayed;
    private boolean cheatsEnabled;
    private WorldSettingsGameType theEnumGameType;
    private boolean requiresConversion;
    private boolean hardcore;

    public boolean getCheatsEnabled() {
        return this.cheatsEnabled;
    }

    public WorldSettingsGameType getEnumGameType() {
        return this.theEnumGameType;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public long getSizeOnDisk() {
        return this.sizeOnDisk;
    }

    public String getFileName() {
        return this.fileName;
    }

    public long getLastTimePlayed() {
        return this.lastTimePlayed;
    }

    public int compareTo(SaveFormatComparator saveFormatComparator) {
        return this.lastTimePlayed < saveFormatComparator.lastTimePlayed
                ? 1
                : (this.lastTimePlayed > saveFormatComparator.lastTimePlayed
                        ? -1
                        : this.fileName.compareTo(saveFormatComparator.fileName));
    }

    public boolean requiresConversion() {
        return this.requiresConversion;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((SaveFormatComparator) object);
    }

    public boolean isHardcoreModeEnabled() {
        return this.hardcore;
    }

    public SaveFormatComparator(
            String text,
            String textValue,
            long awHValue,
            long j,
            WorldSettingsGameType bNQValue,
            boolean requiresConversionValue,
            boolean hardcoreValue,
            boolean cheatsEnabledValue) {
        this.fileName = text;
        this.displayName = textValue;
        this.lastTimePlayed = awHValue;
        this.sizeOnDisk = j;
        this.theEnumGameType = bNQValue;
        this.requiresConversion = requiresConversionValue;
        this.hardcore = hardcoreValue;
        this.cheatsEnabled = cheatsEnabledValue;
    }
}
