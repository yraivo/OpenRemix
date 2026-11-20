package remix.module.combat;

import remix.setting.SettingOption;

public enum AuraRotationMode implements SettingOption {
    LOCK_VIEW("Lock view"),
    SILENT("Silent"),
    NO_ROTATE("Disabled");

    private String displayName;

    // region String decoding support

    // endregion

    AuraRotationMode(String displayNameValue) {
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
