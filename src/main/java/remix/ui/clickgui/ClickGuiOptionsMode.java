package remix.ui.clickgui;

import remix.setting.SettingOption;

public enum ClickGuiOptionsMode implements SettingOption {
    LINES("Lines"),
    DOTS("Dots");

    private String displayName;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    ClickGuiOptionsMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

}
