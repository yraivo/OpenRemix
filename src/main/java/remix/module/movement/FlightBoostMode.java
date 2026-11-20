package remix.module.movement;

import remix.setting.SettingOption;

public enum FlightBoostMode implements SettingOption {
    NCP1("NCP1"),
    NCP2("NCP2"),
    NCP3("NCP3");

    private String displayName;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    FlightBoostMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }
}
