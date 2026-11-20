package remix.client.lifecycle;

import java.io.Serializable;

public interface LifecycleCallback extends Serializable {
    void executeLifecycleEvent();
}
