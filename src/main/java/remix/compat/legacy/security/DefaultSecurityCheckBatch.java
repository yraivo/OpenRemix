package remix.compat.legacy.security;

import remix.util.collection.FluentIterableList;

public final class DefaultSecurityCheckBatch implements SecurityCheckBatch {
    private FluentIterableList<SecurityCheckSelection> fluentIterableList;

    public DefaultSecurityCheckBatch(IndexedSecurityCheckSelection... indexedSecurityCheckSelectionArray) {
        this.fluentIterableList = new FluentIterableList(indexedSecurityCheckSelectionArray);
    }

    public FluentIterableList<SecurityCheckSelection> getFluentIterableList() {
        return this.fluentIterableList;
    }
}
