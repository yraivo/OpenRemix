package remix.module.render;

import remix.setting.SettingOption;

public enum FullBrightMode implements SettingOption {
    WORLD("World"),
    NIGHT_VISION("Night Vision");

    private String displayName;

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    FullBrightMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }
}
