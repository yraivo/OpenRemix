package net.minecraft.util;

public class Tuple {
    private Object aZp;
    private Object b;

    public Object getSecond() {
        return this.aZp;
    }

    public Tuple(Object p171_0, Object aZpValue) {
        this.b = p171_0;
        this.aZp = aZpValue;
    }

    public Object getFirst() {
        return this.b;
    }
}
