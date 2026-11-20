package net.minecraft.server.management;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class LowerStringMap implements Map {
    private final Map internalMap = Maps.newLinkedHashMap();

    @Override
    public boolean containsKey(Object object) {
        return this.internalMap.containsKey(object.toString().toLowerCase());
    }

    @Override
    public Object remove(Object object) {
        return this.internalMap.remove(object.toString().toLowerCase());
    }

    @Override
    public boolean containsValue(Object object) {
        return this.internalMap.containsKey(object);
    }

    @Override
    public Object put(Object object, Object object1) {
        return this.put((String) object, object1);
    }

    @Override
    public boolean isEmpty() {
        return this.internalMap.isEmpty();
    }

    @Override
    public Set keySet() {
        return this.internalMap.keySet();
    }

    @Override
    public void putAll(Map map) {
        ServerConfigurationManager.kr();

        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            this.put((String) entry.getKey(), entry.getValue());
        }
    }

    public Object put(String text, Object object) {
        return this.internalMap.put(text.toLowerCase(), object);
    }

    @Override
    public Set entrySet() {
        return this.internalMap.entrySet();
    }

    @Override
    public int size() {
        return this.internalMap.size();
    }

    @Override
    public Collection values() {
        return this.internalMap.values();
    }

    @Override
    public Object get(Object object) {
        return this.internalMap.get(object.toString().toLowerCase());
    }

    @Override
    public void clear() {
        this.internalMap.clear();
    }
}
