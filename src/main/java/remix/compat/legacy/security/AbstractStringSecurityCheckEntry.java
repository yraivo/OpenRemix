package remix.compat.legacy.security;

import remix.util.task.NamedValueHolder;
import remix.util.task.OneShotNamedValueHolder;

public abstract class AbstractStringSecurityCheckEntry<S, F>
        extends AbstractSecurityCheckEntry<String, S, F> {
    public AbstractStringSecurityCheckEntry(
            OneShotNamedValueHolder oneShotNamedValueHolder,
            NamedValueHolder namedValueHolder,
            NamedValueHolder namedValueHolderValue) {
        super(oneShotNamedValueHolder, namedValueHolder, namedValueHolderValue);
    }

    public AbstractStringSecurityCheckEntry(
            SecurityRulePair<S, F> securityRulePair,
            NamedValueHolder namedValueHolder) {
        super(securityRulePair, namedValueHolder);
    }
}
