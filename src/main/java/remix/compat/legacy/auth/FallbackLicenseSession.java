package remix.compat.legacy.auth;

import net.minecraft.entity.Entity;

import remix.compat.legacy.codec.CipherKeySelector;

public final class FallbackLicenseSession extends DefaultLicenseSession {
    private static String f;

    static {
        f = "oA==";
    }

    public FallbackLicenseSession(String text) throws Exception {
        this(text, LicenseProfileRecord.getLegacyProfileRecordFlowState());
    }

    private FallbackLicenseSession(String text, int initialFlowToken) throws Exception {
        super(
                new DefaultClientIdentityToken(
                        new CipherKeySelector(text, true), null, new String("_")),
                UserRoleTier.BANNED_LOL);

        int j = initialFlowToken;

        int i = j;

        try {
            if (i != 0) {
                Entity.setLegacyGlobalFlowMarker(new String[4]);
            }
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception));
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private static Exception a(Exception exception) {
        return exception;
    }
}
