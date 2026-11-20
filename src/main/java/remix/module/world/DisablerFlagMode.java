package remix.module.world;

import remix.setting.SettingOption;

public enum DisablerFlagMode implements SettingOption {
    EDIT("Edit"),
    PACKET("Packet");

    private String displayName;

    // region String decoding support

    // endregion

    DisablerFlagMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region Static initialization and string tables

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
