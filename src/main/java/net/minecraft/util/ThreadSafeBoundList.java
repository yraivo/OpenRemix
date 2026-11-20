package net.minecraft.util;

import java.lang.reflect.Array;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeBoundList {
    private Object[] field_152759_a;
    private int field_152762_d;
    private Class field_152760_b;
    private final ReadWriteLock field_152761_c = new ReentrantReadWriteLock();
    private int field_152763_e;

    public Object func_152757_a(Object object) {
        this.field_152761_c.writeLock().lock();
        this.field_152759_a[this.field_152763_e] = object;
        this.field_152763_e = (this.field_152763_e + 1) % this.func_152758_b();
        if (this.field_152762_d < this.func_152758_b()) {
            this.field_152762_d++;
        }

        this.field_152761_c.writeLock().unlock();
        return object;
    }

    public ThreadSafeBoundList(Class oclass, int i) {
        this.field_152760_b = oclass;
        this.field_152759_a = (Object[]) Array.newInstance(oclass, i);
    }

    public int func_152758_b() {
        this.field_152761_c.readLock().lock();
        int length = this.field_152759_a.length;
        this.field_152761_c.readLock().unlock();
        return length;
    }

    public Object[] func_152756_c() {
        Object[] aobject = (Object[]) Array.newInstance(this.field_152760_b, this.field_152762_d);
        this.field_152761_c.readLock().lock();
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int i = 0;
        String textValue = text;

        while (i < this.field_152762_d) {
            int j = (this.field_152763_e - this.field_152762_d + i) % this.func_152758_b();
            if (j < 0) {
                j += this.func_152758_b();
            }

            aobject[i] = this.field_152759_a[j];
            i++;
            if (textValue == null) {
                break;
            }
        }

        this.field_152761_c.readLock().unlock();
        return aobject;
    }
}
