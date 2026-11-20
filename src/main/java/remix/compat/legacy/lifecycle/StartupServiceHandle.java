package remix.compat.legacy.lifecycle;

import remix.client.lifecycle.LifecycleCallback;
import remix.client.lifecycle.ServiceComponentState;

public interface StartupServiceHandle extends ServiceComponentState {
    LifecycleCallback activate();

    StartupStateFlag getStartupStateFlag();
}
