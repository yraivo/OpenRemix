package optifine;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class PropertiesOrdered extends Properties {
    private Set<Object> keysOrdered = new LinkedHashSet<>();

    @Override
    public Set<Object> keySet() {
        java.util.Set set = super.keySet();
        this.keysOrdered.retainAll(set);
        return java.util.Collections.unmodifiableSet(this.keysOrdered);
    }

    @Override
    public synchronized Object put(Object p171_0, Object p171_1) {
        this.keysOrdered.add(p171_0);
        return super.put(p171_0, p171_1);
    }

    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(this.keySet());
    }
}
