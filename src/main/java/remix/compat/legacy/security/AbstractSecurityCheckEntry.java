package remix.compat.legacy.security;

import remix.util.task.NamedValueHolder;
import remix.util.task.OneShotNamedValueHolder;

public abstract class AbstractSecurityCheckEntry<T, S, F>
        implements SecurityCheckEntry<T, S, F> {
    private NamedValueHolder namedValueHolder;
    private static int c;
    private SecurityRulePair<S, F> securityRulePair;

    public static int method_7eec03da329a017ec143() {
        return c;
    }

    public AbstractSecurityCheckEntry(
            SecurityRulePair<S, F> bQrValue, NamedValueHolder bQqValue) {
        this.securityRulePair = bQrValue;
        this.namedValueHolder = bQqValue;
    }

    public static void b(int i) {
        c = i;
    }

    public SecurityRulePair<S, F> getSecurityRulePair() {
        return this.securityRulePair;
    }

    public T cy() {
        return (T) this.namedValueHolder.getValue();
    }

    public AbstractSecurityCheckEntry(
            OneShotNamedValueHolder oneShotNamedValueHolder,
            NamedValueHolder namedValueHolderValue,
            NamedValueHolder namedValueHolderValue2) {
        this(
                new DefaultSecurityRulePair(
                        oneShotNamedValueHolder, namedValueHolderValue),
                namedValueHolderValue2);
    }

    public static int method_beea3e16fe3ec0995eb9() {
        method_7eec03da329a017ec143();

        return (100);
    }

    static {
        method_beea3e16fe3ec0995eb9();
    }
}
