package remix.event;

import net.minecraft.entity.Entity;

public abstract class CancellableEvent implements Cancellable, Event {
    private static Entity[] b;
    private boolean cancelled;

    public void cancel() {
        this.cancelled = true;
    }

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    // region Static initialization and string tables
    static {
        method_da2ac5c94686ee11e9e6();
    }

    // endregion

    public void setCancelled(boolean cancelledValue) {
        this.cancelled = cancelledValue;
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }
}
