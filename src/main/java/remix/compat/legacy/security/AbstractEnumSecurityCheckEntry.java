package remix.compat.legacy.security;

import remix.util.task.NamedValueHolder;
import remix.util.task.OneShotNamedValueHolder;

public abstract class AbstractEnumSecurityCheckEntry<T extends Enum, S, F>
        extends AbstractSecurityCheckEntry<T, S, F> {
    public AbstractEnumSecurityCheckEntry(
            SecurityRulePair<S, F> securityRulePair,
            NamedValueHolder namedValueHolder) {
        super(securityRulePair, namedValueHolder);
    }

    public AbstractEnumSecurityCheckEntry(
            OneShotNamedValueHolder oneShotNamedValueHolder,
            NamedValueHolder namedValueHolder,
            NamedValueHolder namedValueHolderValue) {
        super(oneShotNamedValueHolder, namedValueHolder, namedValueHolderValue);
    }
}
