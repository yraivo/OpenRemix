package remix.compat.legacy.environment;

import remix.compat.legacy.security.AbstractExceptionSecurityCheckEntry;
import remix.compat.legacy.security.AltServiceInitSecurityRule;
import remix.compat.legacy.security.CheckResultStatus;
import remix.compat.legacy.security.SecurityCheckException;
import remix.util.task.NamedValueHolder;

public final class VmExceptionSecurityCheckEntry
        extends AbstractExceptionSecurityCheckEntry<
                Class<? extends SecurityCheckException>,
                CheckResultStatus,
                CheckResultStatus> {
    public VmExceptionSecurityCheckEntry(NamedValueHolder namedValueHolder) {
        super(
                new AltServiceInitSecurityRule(),
                namedValueHolder,
                new VmFileExceptionProbe());
    }
}
