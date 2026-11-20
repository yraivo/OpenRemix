package remix.compat.legacy.security;

public final class SecurityCheckTimerTask extends AbstractSecurityTimerTask {
    private static String[] packedTextFragments;
    private static String d;

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (method_e0e3092a59fee58fae52() == null) {
            b(packedTextFragments[0]);
        }
    }

    public void run() {
        method_e0e3092a59fee58fae52();

        SecurityCheckRunner primaryCheckRunner = this.getSecurityManagerRegistry().getPrimaryCheckRunner();
        this.getSecurityManagerRegistry();
        primaryCheckRunner.method_07fe5d638b5ce784a6de(
                this, SecurityManagerRegistry.bYi);
    }

    public static String method_e0e3092a59fee58fae52() {
        return d;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.security.SecurityCheckTimerTask.packedTextFragments[0] = new String("NwsAW");
    }

    // endregion

    // region String decoding support

    // endregion

    public static void b(String text) {
        d = text;
    }

    public SecurityCheckTimerTask(SecurityManagerRegistry securityManagerRegistry) {
        super(securityManagerRegistry);
    }
}
