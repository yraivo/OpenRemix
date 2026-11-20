package remix.compat.legacy.security;

import remix.compat.legacy.environment.JvmArgsStringSecurityCheckEntry;
import remix.compat.legacy.environment.JvmArgumentsStringProbe;

public final class StringIndexedArrayPool extends IndexedArrayPool<String> {
    public StringIndexedArrayPool() {
        super(
                0,
                new int[] {2},
                new String[] {
                    JvmArgumentsStringProbe.cleanArgumentsValue,
                    JvmArgumentsStringProbe.flaggedArgumentsValue,
                    JvmArgumentsStringProbe.additionalProbeValue
                });
    }

    public Class<? extends SecurityCheckEntry> mc() {
        return JvmArgsStringSecurityCheckEntry.class;
    }
}
