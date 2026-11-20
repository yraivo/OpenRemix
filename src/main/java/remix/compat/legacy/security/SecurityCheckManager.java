package remix.compat.legacy.security;

import remix.util.collection.FluentIterableList;
import remix.util.collection.FluentMapWrapper;

public class SecurityCheckManager<M extends SecurityCheckEntry, T> {
    private FluentMapWrapper fluentMapWrapper;
    private static String[] d;
    private FluentIterableList<IndexedValuePool<T>> fluentIterableList;
    private SecurityPoolProvider securityPoolProvider;

    public static String[] kM() {
        return d;
    }

    public static void b(String[] astring) {
        d = astring;
    }

    public SecurityCheckManager(
            FluentMapWrapper bOlValue, SecurityPoolProvider bSYValue) {
        this.fluentMapWrapper = bOlValue;
        this.securityPoolProvider = bSYValue;
        this.fluentIterableList = bSYValue.getFluentIterableList();
    }

    static {
        if (kM() != null) {
            b(new String[2]);
        }
    }

    public FluentIterableList<IndexedValuePool<T>> getFluentIterableList() {
        return this.fluentIterableList;
    }

    public FluentMapWrapper getFluentMapWrapper() {
        return this.fluentMapWrapper;
    }

    public SecurityPoolProvider getSecurityPoolProvider() {
        return this.securityPoolProvider;
    }
}
