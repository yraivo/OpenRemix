package optifine;

import java.util.ArrayList;

public class CompactArrayList {
    private ArrayList list = null;
    private int countValid;
    private float loadFactor;
    private int initialCapacity = 0;

    public Object set(int p171_0, Object p171_1) {
        java.lang.Object object1 = this.list.set(p171_0, p171_1);
        if (p171_1 != object1) {
            if (object1 == null) {
                this.countValid++;
            }

            if (p171_1 == null) {
                this.countValid--;
            }
        }

        return object1;
    }

    public boolean contains(Object p171_0) {
        return this.list.contains(p171_0);
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public void compact() {
        int i = optifine.ReflectorMethod.f();
        if (this.countValid <= 0 && this.list.size() <= 0) {
            this.clear();
            if (i == 0) {
                return;
            }
        }

        if (this.list.size() > this.initialCapacity) {
            float f = this.countValid * 1.0F / this.list.size();
            if (f <= this.loadFactor) {
                int j = 0;
                int k = 0;

                while (k < this.list.size()) {
                    java.lang.Object object = this.list.get(k);
                    if (object != null) {
                        if (k != j) {
                            this.list.set(j, object);
                        }

                        j++;
                    }

                    k++;
                    if (i != 0) {
                        break;
                    }
                }

                k = this.list.size() - 1;

                while (k >= j) {
                    this.list.remove(k);
                    k--;
                    if (i != 0) {
                        break;
                    }
                }
            }
        }
    }

    public Object get(int p171_0) {
        return this.list.get(p171_0);
    }

    public void insert(int p171_0, Object p171_1) {
        if (p171_1 != null) {
            this.countValid++;
        }

        this.list.add(p171_0, p171_1);
    }

    public int getCountValid() {
        return this.countValid;
    }

    public int size() {
        return this.list.size();
    }

    public boolean add(Object p171_0) {
        if (p171_0 != null) {
            this.countValid++;
        }

        return this.list.add(p171_0);
    }

    public CompactArrayList(int p171_0) {
        this(p171_0, 0.75F);
    }

    public CompactArrayList(int p171_0, float p171_1) {
        this.list = new java.util.ArrayList(p171_0);
        this.initialCapacity = p171_0;
        this.loadFactor = p171_1;
    }

    public CompactArrayList() {
        this(10, 0.75F);
    }

    public void clear() {
        this.list.clear();
        this.countValid = 0;
    }

    public Object remove(int p171_0) {
        java.lang.Object object = this.list.remove(p171_0);
        if (object != null) {
            this.countValid--;
        }

        return object;
    }
}
