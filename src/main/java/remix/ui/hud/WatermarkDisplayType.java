package remix.ui.hud;

import remix.setting.SettingOption;

public enum WatermarkDisplayType implements SettingOption {
    TEXT("Text"),
    IMAGE("Image"),
    NONE("None");

    private String a;

    public String getDisplayName() {
        return this.a;
    }

    // region String decoding support

    // endregion


    WatermarkDisplayType(String text) {
        this.a = text;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
