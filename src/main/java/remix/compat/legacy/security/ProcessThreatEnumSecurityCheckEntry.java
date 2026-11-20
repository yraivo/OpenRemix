package remix.compat.legacy.security;

import remix.compat.legacy.environment.BlacklistedProcessDetector;
import remix.compat.legacy.environment.LinuxPsCommandSupplier;
import remix.compat.legacy.environment.ProcessOutputReader;
import remix.compat.legacy.environment.ProcessThreatLevel;
import remix.compat.legacy.environment.RunningProcessesThreatProbe;
import remix.compat.legacy.environment.WindowsTasklistCommandSupplier;
import remix.util.task.NamedValueHolder;

public final class ProcessThreatEnumSecurityCheckEntry
        extends AbstractEnumSecurityCheckEntry<
                ProcessThreatLevel,
                CheckResultStatus,
                CheckResultStatus> {
    private static final ProcessOutputReader bQs =
            new BlacklistedProcessDetector(new LinuxPsCommandSupplier());
    private static final ProcessOutputReader bQt =
            new BlacklistedProcessDetector(new WindowsTasklistCommandSupplier());

    public ProcessThreatEnumSecurityCheckEntry(NamedValueHolder namedValueHolder) {
        super(
                new CreativeTabAndKeybindInitSecurityRule(),
                namedValueHolder,
                new RunningProcessesThreatProbe(bQs, bQt));
    }
}
