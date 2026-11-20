package remix.compat.legacy.crypto;

import net.minecraft.entity.Entity;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.compat.legacy.auth.LicenseValidationState;
import remix.compat.legacy.auth.UserRoleTier;

public class ClassHashDoubleProbe extends remix.util.task.NamedValueHolder {
    private static String[] packedTextFragments;
    private ClassFileHasher classFileHasher;
    private SelfClassFileHasher selfClassFileHasher;
    private static Entity[] legacyClassHashProbeMarker;
    public static long lastClassHashValidationCode = -1L;

    public Object getValue() {
        return this.evaluateClassHashProbe();
    }

    public SelfClassFileHasher getSelfClassFileHasher() {
        return this.selfClassFileHasher;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.crypto.ClassHashDoubleProbe.packedTextFragments[0] = new String("");
    }

    // endregion

    public static Entity[] getLegacyClassHashProbeMarker() {
        return remix.compat.legacy.crypto.ClassHashDoubleProbe.legacyClassHashProbeMarker;
    }

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        Entity[] aclass_b6df81f333fa31dc98ea = new Entity[3];

        setLegacyClassHashProbeMarker(aclass_b6df81f333fa31dc98ea);
    }

    public static void setLegacyClassHashProbeMarker(Entity[] entityArray) {
        remix.compat.legacy.crypto.ClassHashDoubleProbe.legacyClassHashProbeMarker = entityArray;
    }

    public ClassHashDoubleProbe(SelfClassFileHasher selfClassFileHasherValue) {
        super(2.2250738585072014E-308, packedTextFragments[0]);

        this.selfClassFileHasher = selfClassFileHasherValue;
        this.classFileHasher = selfClassFileHasherValue.getClassFileHasher();
    }

    public ClassFileHasher getClassFileHasher() {
        return this.classFileHasher;
    }

    public Double evaluateClassHashProbe() {
        getLegacyClassHashProbeMarker();

        UserRoleTier userRoleTier;
        try {
            userRoleTier =
                    ((ClientServices) RemixClient.INSTANCE.getClientServices())
                            .getLicenseSessionVerifier()
                            .getLicenseProfileRecord()
                            .getLicenseSession()
                            .getUserRoleTier();
        } catch (Exception exception) {
            userRoleTier = UserRoleTier.BANNED_EXTRA;
            return (double) userRoleTier.ordinal();
        }

        if (userRoleTier.equals(UserRoleTier.BANNED)) {
            return (Double) super.getValue();
        }

        boolean flag = ClassFileHasher.isRunningFromPackagedClasses();
        remix.compat.legacy.crypto.ClassHashDoubleProbe.lastClassHashValidationCode =
                LicenseValidationState.VALIDATING_HWID_EXTRA.method_9b417d27949cba33a0d0();
        if (!flag && userRoleTier != UserRoleTier.DEVELOPER) {

            return Double.POSITIVE_INFINITY;
        }

        if (!flag && userRoleTier == UserRoleTier.DEVELOPER) {

            return 4.8479E-319;
        }

        Double doubleValue =
                VersionHashCheckFactory.method_0767c23f62cde9739def(
                                this.selfClassFileHasher,
                                this,
                                namedValueHolder -> {
                                    namedValueHolder.setValue(4.9E-324);
                                })
                        .method_77ca1ae9ef52990e8495(this);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyClassHashProbeMarker(new Entity[1]);
        }

        return doubleValue;
    }
}
