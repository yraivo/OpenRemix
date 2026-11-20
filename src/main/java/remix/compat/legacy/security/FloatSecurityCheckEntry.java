package remix.compat.legacy.security;

import remix.compat.legacy.auth.RoleTierFloatProbe;
import remix.util.task.NamedValueHolder;

public final class FloatSecurityCheckEntry
        extends AbstractNumberSecurityCheckEntry<
                Float, CheckResultStatus, CheckResultStatus> {
    public FloatSecurityCheckEntry(NamedValueHolder namedValueHolder) {
        super(
                new PortAndProxyInitSecurityRule(),
                namedValueHolder,
                new RoleTierFloatProbe());
    }
}
