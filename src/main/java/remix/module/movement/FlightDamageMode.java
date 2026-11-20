package remix.module.movement;

import remix.setting.SettingOption;

public enum FlightDamageMode implements SettingOption {
    VANILLA("Vanilla"),
    NCP("NCP"),
    RANDOMIZED("Randomized"),
    HYPIXEL("Hypixel");

    private String displayName;

    FlightDamageMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

}
