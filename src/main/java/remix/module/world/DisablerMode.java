package remix.module.world;

import remix.setting.SettingOption;

public enum DisablerMode implements SettingOption {
    LAG("Lag"),
    HYPIXELFLAG("HypixelFlag"),
    HYPIXEL("Hypixel"),
    RIDING("Riding"),
    STUPID("Stupid"),
    TIMER("Timer"),
    TRANSACTION("Transaction"),
    PING("Ping"),
    MMC("MMC"),
    ROTATION("Rotation"),
    PACKET("Packet"),
    FLAG("Flag");

    private String displayName;

    // region Static initialization and string tables

    // endregion

    DisablerMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }
}
