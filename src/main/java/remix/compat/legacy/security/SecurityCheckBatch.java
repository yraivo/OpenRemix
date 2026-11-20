package remix.compat.legacy.security;

import remix.util.collection.FluentIterableList;

public interface SecurityCheckBatch {
    FluentIterableList<SecurityCheckSelection> getFluentIterableList();
}
