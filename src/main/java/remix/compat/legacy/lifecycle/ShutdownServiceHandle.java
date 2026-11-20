package remix.compat.legacy.lifecycle;

import remix.client.lifecycle.LifecycleCallback;
import remix.client.lifecycle.ServiceComponentState;

public interface ShutdownServiceHandle extends ServiceComponentState {
    LifecycleCallback activate();

    ShutdownStateFlag getShutdownStateFlag();
}
