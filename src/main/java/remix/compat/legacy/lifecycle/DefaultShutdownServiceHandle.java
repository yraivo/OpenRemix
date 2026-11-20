package remix.compat.legacy.lifecycle;

import remix.client.AbstractGameDirectoryProvider;
import remix.client.lifecycle.LifecycleCallback;

import net.minecraft.entity.Entity;

public final class DefaultShutdownServiceHandle implements ShutdownServiceHandle {
    private final ShutdownStateFlag shutdownStateFlag = new ShutdownStateFlag();
    private LifecycleCallback lifecycleCallback;

    public LifecycleCallback activate() {
        boolean flag1 = AbstractGameDirectoryProvider.isLegacyDirectoryFlagSet();
        this.shutdownStateFlag.method_5a0449839d28b4a71843(true);
        boolean flag = flag1;
        LifecycleCallback bOqValue = this.lifecycleCallback;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            AbstractGameDirectoryProvider.setLegacyDirectoryFlag(!flag);
        }

        return bOqValue;
    }

    public ShutdownStateFlag getShutdownStateFlag() {
        return this.shutdownStateFlag;
    }

    public DefaultShutdownServiceHandle(LifecycleCallback bOqValue) {
        this.lifecycleCallback = bOqValue;
    }

    public boolean passesLegacyStateCheck() {
        return false;
    }
}
