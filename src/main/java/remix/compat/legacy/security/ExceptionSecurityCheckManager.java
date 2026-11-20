package remix.compat.legacy.security;

import remix.compat.legacy.environment.VmExceptionClassArrayPool;
import remix.compat.legacy.environment.VmExceptionSecurityCheckEntry;
import remix.compat.legacy.invocation.ClassLookupReflectiveTargetSupplier;
import remix.compat.legacy.invocation.UnsafeFieldTargetSupplier;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

public class ExceptionSecurityCheckManager
        extends SecurityCheckManager<AbstractExceptionSecurityCheckEntry, Exception> {
    public ExceptionSecurityCheckManager() {
        this(FluentCollections.newMutableMap());
    }

    public ExceptionSecurityCheckManager(
            FluentMapWrapper fluentMapWrapper,
            SecurityPoolProvider securityPoolProvider) {
        super(fluentMapWrapper, securityPoolProvider);
    }

    public ExceptionSecurityCheckManager(FluentMapWrapper fluentMapWrapper) {
        this(
                fluentMapWrapper,
                new ExceptionSecurityPoolProvider(
                        new IndexedValuePool[] {
                            new VmExceptionClassArrayPool(), new WindowTitleExceptionClassArrayPool()
                        }));
        this.getFluentMapWrapper()
                .putEntry(
                        7473453, new VmExceptionSecurityCheckEntry(new ClassLookupReflectiveTargetSupplier()));
        this.getFluentMapWrapper()
                .putEntry(
                        10924712, new WindowTitleExceptionSecurityCheckEntry(new UnsafeFieldTargetSupplier()));
    }
}
