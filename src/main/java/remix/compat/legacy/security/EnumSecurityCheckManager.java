package remix.compat.legacy.security;

import remix.compat.legacy.invocation.DefaultReflectiveTargetSupplier2;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

public final class EnumSecurityCheckManager
        extends SecurityCheckManager<AbstractEnumSecurityCheckEntry, Enum> {
    public EnumSecurityCheckManager() {
        this(FluentCollections.newMutableMap());
    }

    public EnumSecurityCheckManager(
            FluentMapWrapper fluentMapWrapper,
            SecurityPoolProvider securityPoolProvider) {
        super(fluentMapWrapper, securityPoolProvider);
    }

    public EnumSecurityCheckManager(FluentMapWrapper fluentMapWrapper) {
        this(
                fluentMapWrapper,
                new DefaultSecurityPoolProvider(
                        new IndexedValuePool[] {new ProcessThreatEnumArrayPool()}));
        this.getFluentMapWrapper()
                .putEntry(
                        102947, new ProcessThreatEnumSecurityCheckEntry(new DefaultReflectiveTargetSupplier2()));
    }
}
