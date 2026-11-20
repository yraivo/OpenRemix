package remix.module.render;

import remix.setting.SettingOption;

public enum ESPMode implements SettingOption {
    TWO_D(initializeConstantState()),
    POLYGON("Polygon"),
    CYLINDER("Cylinder");
    private static ESPMode[] eH;

    private static String[] packedTextFragments;
    private String displayName;
    private static String c;

    public static void b(String text) {
        c = text;
    }

    public static String method_c60693f457aa70be7160() {
        return c;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables
    static {
        eH = new ESPMode[] {TWO_D, POLYGON, CYLINDER};
    }

    // endregion

    ESPMode(String displayNameValue) {
        this.displayName = displayNameValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private static ESPMode[] __recovery_values171() {
        return eH.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        remix.module.render.ESPMode.packedTextFragments = new String[3];
        remix.module.render.ESPMode.packedTextFragments[0] = new String("Xsf2D");
        String s2 = packedTextFragments[0];
        b(s2);
        return "2D";
    }
}
