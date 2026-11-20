package remix.module.movement;

import remix.setting.SettingOption;

public enum FlightBlinkMode implements SettingOption {
    STAY("Stay"),
    PULSE("Pulse"),
    DAMAGE("Damage");

    private String displayName;

    // region String decoding support

    // endregion

    FlightBlinkMode(String displayNameValue) {
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
