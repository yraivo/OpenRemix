package remix.ui.hud;

import remix.setting.SettingOption;

public enum HudMode implements SettingOption {
    REMIX(initializeConstantState()),
    AMBER("Amber");
    private static HudMode[] hp;

    private String displayName;
    private static boolean initialized;

    public static boolean isInitialized() {
        return remix.ui.hud.HudMode.initialized;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    public static void setInitialized(boolean flag) {
        remix.ui.hud.HudMode.initialized = flag;
    }

    public static boolean isNotInitialized() {
        boolean flag = isInitialized();
        return !flag;
    }

    // region String decoding support

    // endregion

    HudMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region Static initialization and string tables
    static {
        hp = new HudMode[] {REMIX, AMBER};
    }

    // endregion

    private static HudMode[] __recovery_values171() {
        return hp.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        setInitialized(true);
        return "Remix";
    }
}
