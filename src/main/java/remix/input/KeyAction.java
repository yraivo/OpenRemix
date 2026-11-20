package remix.input;

import net.minecraft.entity.Entity;

import remix.client.ClientServices;
import remix.event.InputType;

public class KeyAction<E> {
    private InputType inputType;
    private BoundKeyTask<E> boundKeyTask;
    public static boolean inputTypeReadBeforeEligibilityCheck;
    public static boolean eligibilityCheckedAfterInputRead;
    private static Entity[] legacyKeyActionMarker;

    public KeyAction(BoundKeyTask<E> boundKeyTaskValue, InputType inputType) {
        super();

        this.boundKeyTask = boundKeyTaskValue;
        this.inputType = inputType;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyKeyActionMarker(new Entity[2]);
        }
    }

    public BoundKeyTask<E> getBoundKeyTask() {
        return this.boundKeyTask;
    }

    public static Entity[] getLegacyKeyActionMarker() {
        return remix.input.KeyAction.legacyKeyActionMarker;
    }

    public InputType getInputType() {
        if (!remix.input.KeyAction.eligibilityCheckedAfterInputRead) {
            remix.input.KeyAction.inputTypeReadBeforeEligibilityCheck = true;
        }

        return this.inputType;
    }

    public boolean canExecute(ClientServices clientServices, int i) {
        if (remix.input.KeyAction.inputTypeReadBeforeEligibilityCheck) {
            remix.input.KeyAction.eligibilityCheckedAfterInputRead = true;
        }

        return this.getBoundKeyTask().canExecute(clientServices, i);
    }

    // region Static initialization and string tables
    static {
        if (getLegacyKeyActionMarker() != null) {
            setLegacyKeyActionMarker(new Entity[3]);
        }
    }

    // endregion

    public KeyActionOutcome execute() {
        return this.getBoundKeyTask().execute();
    }

    public static void setLegacyKeyActionMarker(Entity[] entityArray) {
        remix.input.KeyAction.legacyKeyActionMarker = entityArray;
    }
}
