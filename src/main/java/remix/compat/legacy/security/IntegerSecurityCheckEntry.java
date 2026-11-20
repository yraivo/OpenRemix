package remix.compat.legacy.security;

import remix.compat.legacy.auth.UserIdIntegerProbe;
import remix.util.task.NamedValueHolder;

public final class IntegerSecurityCheckEntry
        extends AbstractNumberSecurityCheckEntry<
                Integer, CheckResultStatus, CheckResultStatus> {
    public IntegerSecurityCheckEntry(NamedValueHolder namedValueHolder) {
        super(
                new HackerCheckManagerInitSecurityRule(),
                namedValueHolder,
                new UserIdIntegerProbe());
    }
}
