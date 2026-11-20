package remix.module.movement;

import remix.setting.SettingOption;

public enum BlinkOnDamageMode implements SettingOption {
    DISABLE(initializeConstantState()),
    UPDATE("Update"),
    STAY("Stay");
    private static BlinkOnDamageMode[] ENUM;

    private String displayName;

    private static int[] k;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    BlinkOnDamageMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region Static initialization and string tables
    static {
        remix.module.movement.BlinkOnDamageMode.ENUM =
                new BlinkOnDamageMode[] {DISABLE, UPDATE, STAY};
    }

    // endregion

    public static int[] method_650532ba484532b3efb0() {
        return k;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    public static void b(int[] aint) {
        k = aint;
    }

    private static BlinkOnDamageMode[] __recovery_values171() {
        return remix.module.movement.BlinkOnDamageMode.ENUM.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        int[] aint = new int[2];
        b(aint);
        return "Disable";
    }
}
