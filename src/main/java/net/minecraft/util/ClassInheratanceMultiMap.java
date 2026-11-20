package net.minecraft.util;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ClassUtils.Interfaces;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

public class ClassInheratanceMultiMap extends AbstractSet {
    private static String d;
    private Class baseClass;
    private Set knownKeys;

    private final Multimap map = HashMultimap.create();

    // region String decoding support

    // endregion

    public void createLookup(Class oclass) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Iterator iterator =
                this.map.get(this.method_c86c213b196a2b25c1a6(oclass, false)).iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (oclass.isAssignableFrom(object.getClass())) {
                this.map.put(oclass, object);
            }

            if (textValue == null) {
                break;
            }
        }

        this.knownKeys.add(oclass);
    }

    static Multimap access$000(ClassInheratanceMultiMap classInheratanceMultiMap) {
        return classInheratanceMultiMap.map;
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    // region String decoding support

    // endregion

    @Override
    public int size() {
        return this.map.get(this.baseClass).size();
    }

    @Override
    public Iterator iterator() {
        Iterator iterator = this.map.get(this.baseClass).iterator();
        return new InheritanceMapIterator(this, iterator);
    }

    @Override
    public boolean remove(Object object) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Object object1 = object;
        byte b0 = 0;
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_3669 = (this.knownKeys).iterator();
                (__recoveryIterator171_3669).hasNext(); ) {
            Class oclass = (Class) (__recoveryIterator171_3669).next();
            if (oclass.isAssignableFrom(object1.getClass())) {
                b0 |= remix.compat.compiler.JvmBooleanSlots.toInt(this.map.remove(oclass, object1));
            }

            if (textValue == null) {
                break;
            }
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    public Iterable getByClass(Class oclass) {
        return new ClassInheratanceMultiMapGetByClassIterable(this, oclass);
    }

    static {
        d = "Don't know how to search for ";
    }

    @Override
    public boolean add(Object object) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Iterator iterator = this.knownKeys.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            Class oclass = (Class) iterator.next();
            if (oclass.isAssignableFrom(object.getClass())) {
                this.map.put(oclass, object);
            }

            if (textValue == null) {
                break;
            }
        }

        return true;
    }

    protected Class method_c86c213b196a2b25c1a6(Class oclass, boolean flag) {
        for (java.util.Iterator<?> __recoveryIterator171_6649 =
                        (ClassUtils.hierarchy(oclass, Interfaces.INCLUDE)).iterator();
                (__recoveryIterator171_6649).hasNext(); ) {
            Class oclass1 = (Class) (__recoveryIterator171_6649).next();
            if (this.knownKeys.contains(oclass1)) {
                if (oclass1 == this.baseClass && flag) {
                    this.createLookup(oclass);
                }

                return oclass1;
            }
        }

        throw new IllegalArgumentException(d + oclass);
    }

    public ClassInheratanceMultiMap(Class oclass) {
        this.knownKeys = Sets.newIdentityHashSet();
        this.baseClass = oclass;
        this.knownKeys.add(oclass);
    }
}
