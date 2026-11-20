package remix.module.combat;

import remix.setting.SettingOption;

public enum AuraBlockMode implements SettingOption {
    NCP("NCP"),
    HYPIXEL("Hypixel"),
    OLD("Old"),
    LEGIT("Legit"),
    MMC("MMC"),
    VANILLA("Vanilla"),
    FAKE("Fake");

    private String displayName;

    AuraBlockMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

}
