package remix.module.world;

import remix.setting.SettingOption;

public enum AntiBotMode implements SettingOption {
    HYPIXEL("Hypixel"),
    HYPIXELCN("HypixelCN"),
    MINEPLEX("Mineplex"),
    ADVANCED("Advanced"),
    DISTANCE("Distance"),
    UUID("UUID"),
    TABLIST("TabList");

    private String displayName;

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    AntiBotMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

}
