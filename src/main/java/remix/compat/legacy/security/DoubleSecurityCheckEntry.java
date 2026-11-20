package remix.compat.legacy.security;

import remix.compat.legacy.crypto.ClassFileHasher;
import remix.compat.legacy.crypto.ClassHashDoubleProbe;
import remix.compat.legacy.crypto.SelfClassFileHasher;
import remix.util.task.NamedValueHolder;

public final class DoubleSecurityCheckEntry
        extends AbstractNumberSecurityCheckEntry<
                Double, CheckResultStatus, CheckResultStatus> {
    public DoubleSecurityCheckEntry(NamedValueHolder namedValueHolder) {
        super(
                new CommandsAndClickGuiInitSecurityRule(),
                namedValueHolder,
                new ClassHashDoubleProbe(
                        new SelfClassFileHasher(ClassFileHasher.bXm)));
    }
}
