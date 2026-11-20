package remix.compat.legacy.security;

import remix.compat.legacy.invocation.ClassLookupReflectiveTargetSupplier;
import remix.compat.legacy.invocation.DefaultReflectiveTargetSupplier1;
import remix.compat.legacy.invocation.ExceptionFieldTargetSupplier;
import remix.compat.legacy.invocation.TypeArrayReflectiveTargetSupplier;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

public class NumberSecurityCheckManager
        extends SecurityCheckManager<AbstractNumberSecurityCheckEntry, Number> {
    public NumberSecurityCheckManager(FluentMapWrapper fluentMapWrapper) {
        this(
                fluentMapWrapper,
                new DefaultSecurityPoolProvider(
                        new IndexedValuePool[] {
                            new DoubleIndexedArrayPool(),
                            new FloatIndexedArrayPool(),
                            new IntegerIndexedArrayPool(),
                            new LongIndexedArrayPool()
                        }));
        this.getFluentMapWrapper()
                .putEntry(
                        19034, new DoubleSecurityCheckEntry(new DefaultReflectiveTargetSupplier1()));
        this.getFluentMapWrapper()
                .putEntry(
                        90342634, new FloatSecurityCheckEntry(new ExceptionFieldTargetSupplier()));
        this.getFluentMapWrapper()
                .putEntry(
                        39806, new IntegerSecurityCheckEntry(new ClassLookupReflectiveTargetSupplier()));
        this.getFluentMapWrapper()
                .putEntry(
                        83453977, new LongSecurityCheckEntry(new TypeArrayReflectiveTargetSupplier()));
    }

    public NumberSecurityCheckManager() {
        this(FluentCollections.newMutableMap());
    }

    public NumberSecurityCheckManager(
            FluentMapWrapper fluentMapWrapper,
            SecurityPoolProvider securityPoolProvider) {
        super(fluentMapWrapper, securityPoolProvider);
    }
}
