package remix.compat.legacy.security;

import remix.compat.legacy.lifecycle.AbstractScheduledTimerTask;

public abstract class AbstractSecurityTimerTask extends AbstractScheduledTimerTask {
    private volatile SecurityManagerRegistry securityManagerRegistry;
    public static boolean il;

    public AbstractSecurityTimerTask(SecurityManagerRegistry bWcValue) {
        this.securityManagerRegistry = bWcValue;
        AbstractScheduledTimerTask.method_da2ac5c94686ee11e9e6();

        il = true;
    }

    public void setSecurityManagerRegistry(SecurityManagerRegistry bWcValue) {
        this.securityManagerRegistry = bWcValue;
    }

    public SecurityManagerRegistry getSecurityManagerRegistry() {
        return this.securityManagerRegistry;
    }
}
