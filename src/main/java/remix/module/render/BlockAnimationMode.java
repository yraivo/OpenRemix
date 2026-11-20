package remix.module.render;

import remix.setting.SettingOption;

public enum BlockAnimationMode implements SettingOption {
    ONEDOTSEVEN("1.7"),
    SLIDE("Slide"),
    EXHIBITION("Exhibition"),
    MADOORY("Madoory"),
    JURI("Juri"),
    NONE("None");

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

    BlockAnimationMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

}
