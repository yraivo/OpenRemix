package remix.compat.legacy.security;

public class SecurityCheckRule extends remix.util.task.OneShotNamedValueHolder {
    private static String[] packedTextFragments;

    public Object getValue() {
        return this.abg();
    }

    public SecurityCheckRule() {
        super(
                CheckResultStatus.bYq,
                CheckResultStatus.bYl,
                packedTextFragments[0]);
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    // region String decoding support

    // endregion

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.security.SecurityCheckRule.packedTextFragments[0] = new String("");
    }

    // endregion

    public CheckResultStatus abg() {
        return (CheckResultStatus) super.getValue();
    }
}
