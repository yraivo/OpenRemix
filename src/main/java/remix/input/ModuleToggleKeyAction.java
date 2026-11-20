package remix.input;

import net.minecraft.entity.Entity;

import remix.event.InputType;
import remix.module.ModuleBase;

public final class ModuleToggleKeyAction extends KeyAction<ModuleBase> {

    private static String taskName;
    private static int[] legacyModuleToggleFlowState;

    public ModuleToggleKeyAction(ModuleBase moduleBase, InputType inputType) {
        this(moduleBase, inputType, getLegacyModuleToggleFlowState());
    }

    private ModuleToggleKeyAction(
            ModuleBase moduleBase, InputType inputType, int[] initialFlowMarker) {
        super(
                new BoundKeyTask<ModuleBase>(
                        remix.input.ModuleToggleKeyAction.taskName,
                        moduleBase,
                        (moduleBaseValue, integer) ->
                                moduleBaseValue != null
                                        && moduleBaseValue.getKeyBind() != 0
                                        && integer != 0
                                        && integer == moduleBaseValue.getKeyBind(),
                        ModuleBase::toggle),
                inputType);

        int[] aint1 = initialFlowMarker;

        int[] aint = aint1;
        if (aint != null) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }
    }

    // region String decoding support

    // endregion

    public static void setLegacyModuleToggleFlowState(int[] aint) {
        remix.input.ModuleToggleKeyAction.legacyModuleToggleFlowState = aint;
    }

    public static int[] getLegacyModuleToggleFlowState() {
        return remix.input.ModuleToggleKeyAction.legacyModuleToggleFlowState;
    }

    static {
        if (getLegacyModuleToggleFlowState() != null) {
            setLegacyModuleToggleFlowState(new int[1]);
        }
        remix.input.ModuleToggleKeyAction.taskName = "toggle_task";
    }

    // region String decoding support

    // endregion
}
