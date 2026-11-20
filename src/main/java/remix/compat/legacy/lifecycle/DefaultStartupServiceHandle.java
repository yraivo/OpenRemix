package remix.compat.legacy.lifecycle;

import remix.client.AbstractGameDirectoryProvider;
import remix.client.lifecycle.LifecycleCallback;

import net.minecraft.entity.Entity;

public final class DefaultStartupServiceHandle implements StartupServiceHandle {
    private final StartupStateFlag startupStateFlag = new StartupStateFlag();
    private LifecycleCallback lifecycleCallback;

    public StartupStateFlag getStartupStateFlag() {
        return this.startupStateFlag;
    }

    public DefaultStartupServiceHandle(LifecycleCallback bOqValue) {
        this.lifecycleCallback = bOqValue;
    }

    public LifecycleCallback activate() {
        boolean flag1 = AbstractGameDirectoryProvider.isLegacyDirectoryFlagClear();
        this.startupStateFlag.method_5a0449839d28b4a71843(true);
        boolean flag = flag1;
        if (!flag) {
            Entity.setLegacyGlobalFlowMarker(new String[3]);
        }

        return this.lifecycleCallback;
    }

    public boolean passesLegacyStateCheck() {
        return false;
    }
}
