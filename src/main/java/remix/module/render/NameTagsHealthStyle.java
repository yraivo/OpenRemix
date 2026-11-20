package remix.module.render;

import remix.setting.SettingOption;

public enum NameTagsHealthStyle implements SettingOption {
    NUMERIC("Numeric"),
    PERCENTAGE("Percentage"),
    HEALTH_BAR("HealthBar");

    private String displayName;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    NameTagsHealthStyle(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
