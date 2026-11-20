package remix.event;

import remix.setting.SettingOption;

enum MotionPhaseOption implements SettingOption {
    PRE("PRE", MotionPhase.PRE),
    POST("POST", MotionPhase.POST);

    private String a;

    private MotionPhase fT;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion


    MotionPhaseOption(String text, MotionPhase motionPhase) {
        this.a = text;
        this.fT = motionPhase;
    }

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.a;
    }
}
