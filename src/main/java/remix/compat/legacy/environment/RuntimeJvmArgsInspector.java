package remix.compat.legacy.environment;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;

import net.minecraft.entity.Entity;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public final class RuntimeJvmArgsInspector {
    private FluentListWrapper fluentListWrapper;
    private RuntimeMXBean runtimeMXBean;
    private static boolean debugModeEnabled;

    public RuntimeJvmArgsInspector() {
        this(ManagementFactory.getRuntimeMXBean());
    }

    public void refreshArguments() {
        boolean flag1 = isDebugModeEnabled();
        this.fluentListWrapper.cQ();
        boolean flag = flag1;
        this.fluentListWrapper.addAll(this.runtimeMXBean.getInputArguments());
        if (flag) {
            Entity.setLegacyGlobalFlowMarker(new String[3]);
        }
    }

    public static boolean isDebugModeEnabled() {
        return remix.compat.legacy.environment.RuntimeJvmArgsInspector.debugModeEnabled;
    }

    public RuntimeMXBean getRuntimeMXBean() {
        return this.runtimeMXBean;
    }

    public static void setDebugModeEnabled(boolean flag) {
        remix.compat.legacy.environment.RuntimeJvmArgsInspector.debugModeEnabled = flag;
    }

    public RuntimeJvmArgsInspector(RuntimeMXBean runtimemxbean) {
        this.runtimeMXBean = runtimemxbean;
        this.fluentListWrapper =
                FluentCollections.method_e2cb25ad6dbcdd481b22(
                        runtimemxbean.getInputArguments());
    }

    public String formatArgumentList() {
        return this.fluentListWrapper.asList().toString();
    }

    static {
        if (!f()) {
            setDebugModeEnabled(true);
        }
    }

    public FluentListWrapper getFluentListWrapper() {
        return this.fluentListWrapper;
    }

    public static boolean f() {
        boolean flag = isDebugModeEnabled();
        return !flag;
    }
}
