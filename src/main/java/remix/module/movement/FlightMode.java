package remix.module.movement;

import remix.setting.SettingOption;

public enum FlightMode implements SettingOption {
    HYPIXEL("Hypixel"),
    HYPIXELCOMBO("HypixelCombo"),
    CUBECRAFT("Cubecraft"),
    CUBECRAFT2("Cubecraft2"),
    CUBECRAFT3("Cubecraft3"),
    MCCENTRAL("MCCentral"),
    COLLISION("Collision"),
    VANILLA("Vanilla");

    private String displayName;

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    FlightMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
