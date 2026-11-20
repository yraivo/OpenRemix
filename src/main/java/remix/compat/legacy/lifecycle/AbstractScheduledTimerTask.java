package remix.compat.legacy.lifecycle;

import net.minecraft.entity.Entity;

import java.util.TimerTask;

public abstract class AbstractScheduledTimerTask extends TimerTask {
    private static Entity[] b;

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }

    static {
        if (method_da2ac5c94686ee11e9e6() == null) {
            b(new Entity[1]);
        }
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }
}
