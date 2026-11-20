package remix.compat.legacy.security;

import remix.util.task.NamedValueHolder;
import remix.util.task.OneShotNamedValueHolder;

public abstract class AbstractExceptionSecurityCheckEntry<T extends Class<? extends Exception>, S, F>
        extends AbstractSecurityCheckEntry<T, S, F> {
    public AbstractExceptionSecurityCheckEntry(
            OneShotNamedValueHolder oneShotNamedValueHolder,
            NamedValueHolder namedValueHolder,
            NamedValueHolder namedValueHolderValue) {
        super(oneShotNamedValueHolder, namedValueHolder, namedValueHolderValue);
    }

    public AbstractExceptionSecurityCheckEntry(
            SecurityRulePair<S, F> securityRulePair,
            NamedValueHolder namedValueHolder) {
        super(securityRulePair, namedValueHolder);
    }
}
