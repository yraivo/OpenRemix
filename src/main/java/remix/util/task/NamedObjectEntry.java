package remix.util.task;

import net.minecraft.entity.Entity;

public class NamedObjectEntry<O> {
    private String b;
    private O byd;
    private static Entity[] aXG;

    static {
        bA();
    }

    public String method_c60693f457aa70be7160() {
        return this.b;
    }

    public static Entity[] bA() {
        return aXG;
    }

    public static void b(Entity[] aXGValue) {
        aXG = aXGValue;
    }

    public O Qu() {
        return this.byd;
    }

    public NamedObjectEntry(O object, String text) {
        super();

        this.byd = (O) object;
        this.b = text;
    }
}
