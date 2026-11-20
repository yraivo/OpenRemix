package remix.module.world;

import remix.setting.SettingOption;

public enum FakeHackerTypeMode implements SettingOption {
    SNEAK("Sneak"),
    BLINK("Blink"),
    NO_SWING("No Swing"),
    HEADLESS("Headless"),
    KILL_AURA("KillAura");

    private String displayName;

    // region String decoding support

    // endregion

    FakeHackerTypeMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

}
