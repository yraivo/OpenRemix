package remix.compat.legacy.auth;

import remix.client.ClientServices;
import remix.client.RemixClient;

public class UserIdIntegerProbe extends remix.util.task.NamedValueHolder {
    private static String[] packedTextFragments;
    private static boolean legacyUserIdProbeFlowEnabled;

    public static boolean isLegacyUserIdProbeFlowEnabled() {
        return remix.compat.legacy.auth.UserIdIntegerProbe.legacyUserIdProbeFlowEnabled;
    }

    public Integer evaluateUserIdProbe() {
        isLegacyUserIdProbeFlowEnabled();

        String text =
                ((ClientServices) RemixClient.INSTANCE.getClientServices())
                        .getLicenseSessionVerifier()
                        .getLicenseSessionBuilder()
                        .method_e0e3092a59fee58fae52();

        if (text.length() > LicenseValidationState.VALIDATING_PROCESS.method_9b417d27949cba33a0d0()) {
            return (Integer) this.setValue(text.hashCode());
        }

        int j = 0;

        while (j < text.length()) {
            char c0 = text.charAt(j);
            int i = '0' <= c0 && c0 <= '9' ? 1 : 0;
            this.setValue(j);
            if (i == 0) {
                this.setValue(124890);

                break;
            }

            j++;
        }

        return (Integer) super.getValue();
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();

        isLegacyUserIdProbeFlowDisabled();
    }

    public static boolean isLegacyUserIdProbeFlowDisabled() {
        boolean flag = isLegacyUserIdProbeFlowEnabled();
        return !flag;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.auth.UserIdIntegerProbe.packedTextFragments[0] = new String("");
    }

    // endregion

    public static void setLegacyUserIdProbeFlowEnabled(boolean flag) {
        remix.compat.legacy.auth.UserIdIntegerProbe.legacyUserIdProbeFlowEnabled = flag;
    }

    public UserIdIntegerProbe() {
        super(147, packedTextFragments[0]);
    }

    public Object getValue() {
        return this.evaluateUserIdProbe();
    }

    // region String decoding support

    // endregion
}
