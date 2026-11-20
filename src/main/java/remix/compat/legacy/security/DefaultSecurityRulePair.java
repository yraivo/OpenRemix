package remix.compat.legacy.security;

import remix.util.task.NamedValueHolder;
import remix.util.task.OneShotNamedValueHolder;

public class DefaultSecurityRulePair<S, F> implements SecurityRulePair<S, F> {
    private OneShotNamedValueHolder oneShotNamedValueHolder;
    private NamedValueHolder namedValueHolder;
    private static boolean ruleActive;

    public static void setRuleActive(boolean flag) {
        remix.compat.legacy.security.DefaultSecurityRulePair.ruleActive = flag;
    }

    public S method_dd944bba6a15824bf1ba(OneShotNamedValueHolder oneShotNamedValueHolderValue) {
        return (S) this.oneShotNamedValueHolder.getValue();
    }

    public F method_b68b253a075c1969fddb(NamedValueHolder namedValueHolderValue) {
        return (F) this.namedValueHolder.getValue();
    }

    public static boolean isRuleActive() {
        return remix.compat.legacy.security.DefaultSecurityRulePair.ruleActive;
    }

    static {
        if (method_d87039b07bd6961907f2()) {
            setRuleActive(true);
        }
    }

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = isRuleActive();
        return !flag;
    }

    public DefaultSecurityRulePair(
            OneShotNamedValueHolder bSvValue, NamedValueHolder bQqValue) {
        this.oneShotNamedValueHolder = bSvValue;
        this.namedValueHolder = bQqValue;
    }
}
