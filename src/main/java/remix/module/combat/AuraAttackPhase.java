package remix.module.combat;

import remix.event.MotionPhase;
import remix.setting.SettingOption;

public enum AuraAttackPhase implements SettingOption {
    PRE("PRE", MotionPhase.PRE),
    POST("POST", MotionPhase.POST);

    private MotionPhase fT;
    private String displayName;

    // region String decoding support

    // endregion

    public static MotionPhase getMotionPhase(AuraAttackPhase auraAttackPhase) {
        return auraAttackPhase.fT;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    AuraAttackPhase(String displayNameValue, MotionPhase motionPhase) {
        this.displayName = displayNameValue;
        this.fT = motionPhase;
    }

    public MotionPhase os() {
        return this.fT;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

}
