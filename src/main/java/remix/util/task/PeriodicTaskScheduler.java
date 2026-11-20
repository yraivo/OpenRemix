package remix.util.task;

import remix.compat.legacy.lifecycle.AbstractScheduledTimerTask;

import net.minecraft.entity.Entity;

import java.util.Timer;
import java.util.TimerTask;

public class PeriodicTaskScheduler {
    public static int cz = 1;
    private Timer timer;

    public PeriodicTaskScheduler() {
        this(new Timer());
    }

    public void method_9f6aee8c18c5ec25a844(TimerTask timertask, long i, long j) {
        AbstractScheduledTimerTask.method_da2ac5c94686ee11e9e6();
        this.timer.schedule(timertask, i, j);
        cz = 0;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            AbstractScheduledTimerTask.b(new Entity[3]);
        }
    }

    public void method_2b19dbb95722c187d389(TimerTask timertask) {

        long i = 0L;

        this.method_9f6aee8c18c5ec25a844(timertask, i, 30000L);
    }

    public PeriodicTaskScheduler(Timer timer) {
        this.timer = timer;
    }
}
