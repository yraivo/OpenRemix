package remix.compat.legacy.security;

import remix.util.task.NamedValueHolder;
import remix.util.task.OneShotNamedValueHolder;

public abstract class AbstractNumberSecurityCheckEntry<T extends Number, S, F>
        extends AbstractSecurityCheckEntry<T, S, F> {
    private static int[] rg;

    public static int[] s() {
        return rg;
    }

    public AbstractNumberSecurityCheckEntry(
            OneShotNamedValueHolder oneShotNamedValueHolder,
            NamedValueHolder namedValueHolder,
            NamedValueHolder namedValueHolderValue) {
        super(oneShotNamedValueHolder, namedValueHolder, namedValueHolderValue);
    }

    public static void b(int[] aint) {
        rg = aint;
    }

    public AbstractNumberSecurityCheckEntry(
            SecurityRulePair<S, F> securityRulePair,
            NamedValueHolder namedValueHolder) {
        super(securityRulePair, namedValueHolder);
    }

    static {
        s();
    }
}
