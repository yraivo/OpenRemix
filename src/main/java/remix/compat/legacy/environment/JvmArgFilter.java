package remix.compat.legacy.environment;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;

import net.minecraft.entity.Entity;

public class JvmArgFilter {
    private static final JvmArgFilter PASS_THROUGH = new PassThroughJvmArgFilter();
    private RuntimeJvmArgsInspector runtimeJvmArgsInspector;
    private FluentListWrapper fluentListWrapper;

    public Object matchArgumentOrContinue(String text, Object object) {
        boolean flag = RuntimeJvmArgsInspector.isDebugModeEnabled();
        if (!this.fluentListWrapper.asList().contains(text)
                && !this.runtimeJvmArgsInspector.formatArgumentList().contains(text)) {
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                RuntimeJvmArgsInspector.setDebugModeEnabled(!flag);
            }

            return this;
        } else {
            return object;
        }
    }

    public static JvmArgFilter asFilterOrPassThrough(Object object) {
        return object instanceof JvmArgFilter
                ? (JvmArgFilter) object
                : remix.compat.legacy.environment.JvmArgFilter.PASS_THROUGH;
    }

    public boolean isPassThroughFilter(
            JvmArgFilter jvmArgFilter) {
        return jvmArgFilter instanceof PassThroughJvmArgFilter;
    }

    public JvmArgFilter(RuntimeJvmArgsInspector bPGValue) {
        this.runtimeJvmArgsInspector = bPGValue;
        this.fluentListWrapper = bPGValue == null ? FluentCollections.ax() : bPGValue.getFluentListWrapper();
    }

    public RuntimeJvmArgsInspector getRuntimeJvmArgsInspector() {
        return this.runtimeJvmArgsInspector;
    }
}
