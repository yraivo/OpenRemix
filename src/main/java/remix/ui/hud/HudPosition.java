package remix.ui.hud;

import remix.setting.SettingOption;

public enum HudPosition implements SettingOption {
    TOP_RIGHT("TopRight"),
    TOP_LEFT("TopLeft"),
    BOTTOM_RIGHT("BottomRight"),
    BOTTOM_LEFT("BottomLeft");

    private String displayName;

    HudPosition(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    public float getDirectionalYOffset(float f) {
        return this != TOP_RIGHT && this != TOP_LEFT ? -f : f;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

}
