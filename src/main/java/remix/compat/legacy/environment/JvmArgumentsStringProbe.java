package remix.compat.legacy.environment;

import remix.compat.legacy.auth.UserRoleTier;

import net.minecraft.entity.Entity;
import remix.client.ClientServices;
import remix.client.RemixClient;

public final class JvmArgumentsStringProbe extends remix.util.task.NamedValueHolder {
    private static String[] packedTextFragments;
    private JvmArgFilter jvmArgFilter;
    public static String flaggedArgumentsValue;
    public static String additionalProbeValue;
    private RuntimeJvmArgsInspector runtimeJvmArgsInspector;

    public static String cleanArgumentsValue;
    private static String legacyProbeMarker;

    public static void setLegacyProbeMarker(String text) {
        remix.compat.legacy.environment.JvmArgumentsStringProbe.legacyProbeMarker = text;
    }

    static {
        remix.compat.legacy.environment.JvmArgumentsStringProbe.packedTextFragments = new String[6];
        remix.compat.legacy.environment.JvmArgumentsStringProbe.packedTextFragments[0] = new String("");
        remix.compat.legacy.environment.JvmArgumentsStringProbe.packedTextFragments[1] = new String("");
        remix.compat.legacy.environment.JvmArgumentsStringProbe.packedTextFragments[2] = new String("YHlyPc");
        remix.compat.legacy.environment.JvmArgumentsStringProbe.packedTextFragments[3] = new String("yhluG");
        String s2 = packedTextFragments[3];
        setLegacyProbeMarker(s2);
        remix.compat.legacy.environment.JvmArgumentsStringProbe.cleanArgumentsValue = new String("lol");
        remix.compat.legacy.environment.JvmArgumentsStringProbe.flaggedArgumentsValue = new String("xD");
        remix.compat.legacy.environment.JvmArgumentsStringProbe.additionalProbeValue = new String("haha");
    }

    public String readArgumentProbeValue() {
        UserRoleTier role;
        try {
            role = ((ClientServices) RemixClient.INSTANCE.getClientServices()).getLicenseSessionVerifier()
                    .getLicenseProfileRecord().getLicenseSession().getUserRoleTier();
        } catch (Exception unavailableProfile) {
            return String.valueOf(UserRoleTier.BETA_TESTER_LOL.ordinal());
        }
        if (role.equals(UserRoleTier.BANNED)) {
            return (String) super.getValue();
        }

        String debugArgument = ((ClientServices) RemixClient.INSTANCE.getClientServices()).getVerifiedLicenseProfile()
                .getLicenseSession().getUserRoleTier().equals(UserRoleTier.DEVELOPER) ? remix.compat.legacy.environment.JvmArgumentsStringProbe.flaggedArgumentsValue : new String("jdwp");
        JvmArgFilter filter = this.jvmArgFilter;
        for (String candidate : new String[] {
                "-Xverify:none", "DisableAttachMechanism", "-Xbootclasspath", "-Xrun",
                debugArgument, "-Xdebug", "-Xrunjdwp"}) {
            filter = JvmArgFilter.asFilterOrPassThrough(filter.matchArgumentOrContinue(candidate, remix.compat.legacy.environment.JvmArgumentsStringProbe.flaggedArgumentsValue));
        }

        String result = filter.isPassThroughFilter(filter)
                ? (String) filter.matchArgumentOrContinue(packedTextFragments[1], remix.compat.legacy.environment.JvmArgumentsStringProbe.flaggedArgumentsValue) : remix.compat.legacy.environment.JvmArgumentsStringProbe.cleanArgumentsValue;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyProbeMarker(packedTextFragments[2]);
        }
        return result;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public Object getValue() {
        return this.readArgumentProbeValue();
    }

    public static String getLegacyProbeMarker() {
        return remix.compat.legacy.environment.JvmArgumentsStringProbe.legacyProbeMarker;
    }

    // region String decoding support

    // endregion

    public JvmArgumentsStringProbe(RuntimeJvmArgsInspector bSFValue) {
        super(remix.compat.legacy.environment.JvmArgumentsStringProbe.flaggedArgumentsValue, packedTextFragments[0]);
        this.runtimeJvmArgsInspector = bSFValue;
        this.jvmArgFilter = new JvmArgFilter(bSFValue);
    }
}
