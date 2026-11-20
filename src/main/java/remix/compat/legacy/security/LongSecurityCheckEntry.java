package remix.compat.legacy.security;

import remix.compat.legacy.auth.RoleTierLongProbe;
import remix.util.task.NamedValueHolder;

public final class LongSecurityCheckEntry
        extends AbstractNumberSecurityCheckEntry<
                Long, CheckResultStatus, CheckResultStatus> {
    private static String[] e;

    public static void b(String[] astring) {
        e = astring;
    }

    public LongSecurityCheckEntry(NamedValueHolder namedValueHolder) {
        super(
                new FriendManagerInitSecurityRule(),
                namedValueHolder,
                new RoleTierLongProbe());
    }

    public static String[] y() {
        return e;
    }

    static {
        y();
    }
}
