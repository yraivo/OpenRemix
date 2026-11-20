package remix.module.player;

import remix.setting.SettingOption;

public enum AutoHypixelLanguage implements SettingOption {
    ENGLISH("English"),
    GERMAN("German"),
    FRENCH("French"),
    DUTCH("Dutch"),
    SPANISH("Spanish"),
    ITALIAN("Italian"),
    CHINESE_SIMPLIFIED("Chinese Simplified"),
    CHINESE_TRADITIONAL("Chinese Traditional"),
    PORTUGUESE_BR("Portuguese"),
    RUSSIAN("Russian"),
    KOREAN("Korean"),
    POLISH("Polish"),
    JAPANESE("Japanese"),
    PIRATE("Pirate");

    private String displayName;

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    AutoHypixelLanguage(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
