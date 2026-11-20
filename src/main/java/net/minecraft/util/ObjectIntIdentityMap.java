package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

public class ObjectIntIdentityMap implements IObjectIntIterable {
    private final IdentityHashMap identityMap = new IdentityHashMap(512);
    private final List objectList = Lists.newArrayList();

    public final Object getByValue(int i) {
        return i >= 0 && i < this.objectList.size() ? this.objectList.get(i) : null;
    }

    public int get(Object object) {
        Integer integer = (Integer) this.identityMap.get(object);
        return integer == null ? -1 : integer;
    }

    public void put(Object object, int i) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        this.identityMap.put(object, i);
        String textValue = text;

        while (this.objectList.size() <= i) {
            this.objectList.add(null);
            if (textValue == null) {
                break;
            }
        }

        this.objectList.set(i, object);
    }

    public Iterator iterator() {
        return Iterators.filter(this.objectList.iterator(), Predicates.notNull());
    }

    public List getObjectList() {
        return this.objectList;
    }
}
