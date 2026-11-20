package remix.module.movement;

import remix.setting.SettingOption;

public enum SneakMode implements SettingOption {
    KEYBIND("Keybind"),
    PACKET("Packet"),
    SOLID("Solid");

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

    SneakMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

}
