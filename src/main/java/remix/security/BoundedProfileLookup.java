package remix.security;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Optional public-profile lookups: bounded workers/queue, per-key deduplication, no caller
 * blocking.
 */
public final class BoundedProfileLookup {
    private static final Set<String> IN_FLIGHT =
            Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    private static final AtomicInteger SERIAL = new AtomicInteger();
    private static final ThreadPoolExecutor POOL =
            new ThreadPoolExecutor(
                    2,
                    2,
                    30L,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(32),
                    new ThreadFactory() {
                        public Thread newThread(Runnable task) {
                            Thread thread =
                                    new Thread(
                                            task,
                                            "Remix-profile-lookup-" + SERIAL.incrementAndGet());
                            thread.setDaemon(true);
                            return thread;
                        }
                    },
                    new ThreadPoolExecutor.AbortPolicy());

    static {
        POOL.allowCoreThreadTimeOut(true);
    }

    private BoundedProfileLookup() {}

    public static boolean submit(final String key, final Runnable task) {
        if (key == null || task == null || !IN_FLIGHT.add(key)) return false;
        try {
            POOL.execute(
                    new Runnable() {
                        public void run() {
                            try {
                                task.run();
                            } finally {
                                IN_FLIGHT.remove(key);
                            }
                        }
                    });
            return true;
        } catch (RejectedExecutionException rejected) {
            IN_FLIGHT.remove(key);
            return false;
        }
    }
}
