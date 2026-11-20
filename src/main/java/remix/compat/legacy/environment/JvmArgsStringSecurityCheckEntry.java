package remix.compat.legacy.environment;

import remix.compat.legacy.security.AbstractStringSecurityCheckEntry;
import remix.compat.legacy.security.CheckResultStatus;
import remix.compat.legacy.security.ModulesAndConfigsInitSecurityRule;
import remix.util.task.NamedValueHolder;

public final class JvmArgsStringSecurityCheckEntry
        extends AbstractStringSecurityCheckEntry<CheckResultStatus, CheckResultStatus> {
    public JvmArgsStringSecurityCheckEntry(NamedValueHolder namedValueHolder) {
        super(
                new ModulesAndConfigsInitSecurityRule(),
                namedValueHolder,
                new JvmArgumentsStringProbe(new RuntimeJvmArgsInspector()));
    }
}
