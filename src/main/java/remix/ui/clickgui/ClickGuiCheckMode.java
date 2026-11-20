package remix.ui.clickgui;

import remix.setting.SettingOption;

public enum ClickGuiCheckMode implements SettingOption {
    CHECK(initializeConstantState()),
    BOX("Box");
    private static ClickGuiCheckMode[] ENUM;

    private String displayName;
    private static boolean flag;

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables
    static {
        remix.ui.clickgui.ClickGuiCheckMode.ENUM = new ClickGuiCheckMode[] {CHECK, BOX};
    }

    // endregion

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        remix.ui.clickgui.ClickGuiCheckMode.flag = flag;
    }

    ClickGuiCheckMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = method_b417d7500ec59d69e373();
        return !flag;
    }

    public static boolean method_b417d7500ec59d69e373() {
        return remix.ui.clickgui.ClickGuiCheckMode.flag;
    }

    // region String decoding support

    // endregion

    private static ClickGuiCheckMode[] __recovery_values171() {
        return remix.ui.clickgui.ClickGuiCheckMode.ENUM.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        method_a8745b073fa8adfa1857(true);
        return "Check";
    }
}
