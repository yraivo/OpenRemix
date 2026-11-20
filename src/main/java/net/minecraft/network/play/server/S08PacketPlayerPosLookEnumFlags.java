package net.minecraft.network.play.server;

import java.util.EnumSet;
import java.util.Set;

public enum S08PacketPlayerPosLookEnumFlags {
    RELATIVE_X(initializeConstantState(), 0, 0),
    RELATIVE_Y(constantTextFragment(5), 1, 1),
    RELATIVE_Z(constantTextFragment(7), 2, 2),
    RELATIVE_YAW("Y_ROT", 3, 3),
    RELATIVE_PITCH("X_ROT", 4, 4);
    private static S08PacketPlayerPosLookEnumFlags[] abW;

    private static S08PacketPlayerPosLookEnumFlags[] abT;

    private int field_180058_f;
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    private boolean func_180054_b(int i) {
        return (i & this.func_180055_a()) == this.func_180055_a();
    }

    // region String decoding support

    // endregion

    S08PacketPlayerPosLookEnumFlags(String text, int k, int j) {
        this.field_180058_f = j;
    }

    public static int packFlags(Set set) {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        int i = 0;
        int[] aint = aint1;

        for (java.util.Iterator<?> __recoveryIterator171_3875 = (set).iterator();
                (__recoveryIterator171_3875).hasNext(); ) {
            S08PacketPlayerPosLookEnumFlags s08PacketPlayerPosLookEnumFlags =
                    (S08PacketPlayerPosLookEnumFlags) (__recoveryIterator171_3875).next();
            i |= s08PacketPlayerPosLookEnumFlags.func_180055_a();
            if (aint != null) {
                break;
            }
        }

        return i;
    }

    // region String decoding support

    // endregion

    static {
        abW =
                new S08PacketPlayerPosLookEnumFlags[] {
                    net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.RELATIVE_X,
                    net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.RELATIVE_Y,
                    net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.RELATIVE_Z,
                    net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.RELATIVE_YAW,
                    net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.RELATIVE_PITCH
                };
        abT =
                new S08PacketPlayerPosLookEnumFlags[] {
                    net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.RELATIVE_X,
                    net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.RELATIVE_Y,
                    net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.RELATIVE_Z,
                    net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.RELATIVE_YAW,
                    net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.RELATIVE_PITCH
                };
    }

    // region String decoding support

    // endregion

    private int func_180055_a() {
        return 1 << this.field_180058_f;
    }

    public static Set unpackFlags(int i) {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        EnumSet enumset = EnumSet.noneOf(S08PacketPlayerPosLookEnumFlags.class);
        S08PacketPlayerPosLookEnumFlags[] s08PacketPlayerPosLookEnumFlagsArray = values();
        int[] aint = aint1;

        for (Object __enumElement171_2 : s08PacketPlayerPosLookEnumFlagsArray) {
            S08PacketPlayerPosLookEnumFlags s08PacketPlayerPosLookEnumFlags =
                    (S08PacketPlayerPosLookEnumFlags) __enumElement171_2;
            if (s08PacketPlayerPosLookEnumFlags.func_180054_b(i)) {
                enumset.add(s08PacketPlayerPosLookEnumFlags);
            }

            if (aint != null) {
                break;
            }
        }

        return enumset;
    }

    private static S08PacketPlayerPosLookEnumFlags[] __recovery_values171() {
        return abW.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.packedTextFragments =
                new String[8];
        net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.packedTextFragments[2] =
                new String("X");
        net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.packedTextFragments[3] =
                new String("X");
        net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.packedTextFragments[4] =
                new String("Y");
        net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.packedTextFragments[5] =
                new String("Y");
        net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.packedTextFragments[6] =
                new String("Z");
        net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags.packedTextFragments[7] =
                new String("Z");
        return net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags
                .packedTextFragments[3];
    }

    private static String constantTextFragment(int index) {
        return packedTextFragments[index];
    }
}
