package remix.module.combat;

import remix.setting.SettingOption;

public enum CriticalsMode implements SettingOption {
    PACKET("Packet", CriticalsAttackMode.PACKET),
    PACKET2("Packet2", CriticalsAttackMode.PACKET),
    HYPIXEL("Hypixel", CriticalsAttackMode.PACKET),
    DOWN("Down", CriticalsAttackMode.PACKET),
    EXPERIMENTAL("Experimental", CriticalsAttackMode.BOTH),
    HOVER("Hover", CriticalsAttackMode.EDIT),
    NOGROUND("NoGround", CriticalsAttackMode.EDIT),
    EDIT("Edit", CriticalsAttackMode.EDIT),
    JUMP("Jump", CriticalsAttackMode.EDIT);

    private CriticalsAttackMode RB;
    private String displayName;

    // region Static initialization and string tables

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    public static CriticalsAttackMode getAttackMode(CriticalsMode criticalsMode) {
        return criticalsMode.RB;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    CriticalsMode(String displayNameValue, CriticalsAttackMode criticalsAttackMode) {
        this.displayName = displayNameValue;
        this.RB = criticalsAttackMode;
    }
}
