package remix.compat.legacy.security;

import remix.compat.legacy.environment.DisplayTitleExceptionProbe;
import remix.compat.legacy.environment.VmDetectionException;
import remix.util.task.NamedValueHolder;

public final class WindowTitleExceptionSecurityCheckEntry
        extends AbstractExceptionSecurityCheckEntry<
                Class<? extends VmDetectionException>,
                CheckResultStatus,
                CheckResultStatus> {
    public WindowTitleExceptionSecurityCheckEntry(NamedValueHolder namedValueHolder) {
        super(
                new SettingRegistryInitSecurityRule(),
                namedValueHolder,
                new DisplayTitleExceptionProbe());
    }
}
