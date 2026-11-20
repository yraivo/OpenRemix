package remix.module.world;

import remix.setting.SettingOption;

public enum ScaffoldMode implements SettingOption {
    RAYTRACE("RayTrace"),
    SCAN("Scan");

    private String displayName;

    // region String decoding support

    // endregion

    ScaffoldMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }
}
