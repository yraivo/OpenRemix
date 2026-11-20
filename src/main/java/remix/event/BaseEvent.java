package remix.event;

import net.minecraft.entity.Entity;

public final class BaseEvent implements Event {
    private static Entity[] b;

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }

    static {
        method_da2ac5c94686ee11e9e6();
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }
}
