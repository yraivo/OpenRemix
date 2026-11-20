package net.minecraft.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Iterator;
import java.util.Map;

public class RegistryNamespaced extends RegistrySimple implements IObjectIntIterable {
    protected final ObjectIntIdentityMap underlyingIntegerMap = new ObjectIntIdentityMap();
    protected final Map inverseObjectRegistry = ((BiMap) this.registryObjects).inverse();

    public Object getNameForObject(Object p171_0) {
        return this.inverseObjectRegistry.get(p171_0);
    }

    protected Map createUnderlyingMap() {
        return HashBiMap.create();
    }

    public Iterator iterator() {
        return this.underlyingIntegerMap.iterator();
    }

    public void register(int i, Object object, Object object1) {
        this.underlyingIntegerMap.put(object1, i);
        this.putObject(object, object1);
    }

    public int getIDForObject(Object object) {
        return this.underlyingIntegerMap.get(object);
    }

    public Object getObject(Object object) {
        return super.getObject(object);
    }

    public boolean containsKey(Object object) {
        return super.containsKey(object);
    }

    public Object getObjectById(int i) {
        return this.underlyingIntegerMap.getByValue(i);
    }
}
