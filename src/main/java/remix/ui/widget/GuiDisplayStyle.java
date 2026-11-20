package remix.ui.widget;

import remix.setting.SettingOption;

public enum GuiDisplayStyle implements SettingOption {
    TAB("Tab"),
    PANEL("Panel");

    private String a;


    GuiDisplayStyle(String text) {
        this.a = text;
    }

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.a;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
