package remix.compat.legacy.security;

import remix.compat.legacy.environment.ProcessThreatLevel;

public final class ProcessThreatEnumArrayPool
        extends AbstractEnumArrayPool<ProcessThreatLevel> {
    public Class<? extends SecurityCheckEntry> mc() {
        return ProcessThreatEnumSecurityCheckEntry.class;
    }

    public ProcessThreatEnumArrayPool() {
        super(1, new int[] {3, 5}, ProcessThreatLevel.NEUTRAL);
    }
}
