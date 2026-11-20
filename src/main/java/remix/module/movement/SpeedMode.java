package remix.module.movement;

import remix.setting.SettingOption;

public enum SpeedMode implements SettingOption {
    AAC("AAC"),
    BHOP("Bhop"),
    LOWHOP("LowHop"),
    OLDHOP("OldHop"),
    OLDPORT("OldPort"),
    ONGROUND("OnGround"),
    HYPIXEL("Hypixel"),
    MINEPLEX("Mineplex"),
    GUARDIAN("Guardian"),
    VANILLA("Vanilla"),
    VANILLAHOP("VanillaHop");

    private String displayName;

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

    SpeedMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

}
