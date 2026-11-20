package remix.module.movement;

import remix.setting.SettingOption;

public enum StepMode implements SettingOption {
    VANILLA("Vanilla"),
    MOTION("Motion"),
    NCP("NCP");

    private String displayName;

    // region String decoding support

    // endregion

    StepMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

}
