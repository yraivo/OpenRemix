package remix.module;

import java.awt.Color;

public enum ModuleCategory {
    HIDDEN(initializeConstantState(), constantTextFragment(2)),
    COMBAT(new Color(248, 83, 111).getRGB(), "Combat"),
    EXPLOIT(new Color(72, 230, 167).getRGB(), "Exploits"),
    MOVEMENT(new Color(90, 205, 209).getRGB(), "Move"),
    PLAYER(new Color(241, 196, 15).getRGB(), "Player"),
    WORLD(new Color(247, 154, 62).getRGB(), "World"),
    RENDER(new Color(155, 93, 236).getRGB(), "Render");
    private static ModuleCategory[] zC;

    private int a;
    private String b;
    private static String[] packedTextFragments;

    public int getColor() {
        return this.a;
    }

    // region String decoding support

    // endregion

    public void setDisplayName(String text) {
        this.b = text;
    }

    public void a(int i) {
        this.a = i;
    }

    public String getDisplayName() {
        return this.b;
    }

    // region Static initialization and string tables
    static {
        zC = new ModuleCategory[] {HIDDEN, COMBAT, EXPLOIT, MOVEMENT, PLAYER, WORLD, RENDER};
    }

    // endregion

    ModuleCategory(int j, String text) {
        this.a = j;
        this.b = text;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private static ModuleCategory[] __recovery_values171() {
        return zC.clone();
    }

    // Initialize shared state before the first constant is published.
    private static int initializeConstantState() {
        remix.module.ModuleCategory.packedTextFragments = new String[3];
        remix.module.ModuleCategory.packedTextFragments[2] = new String("");
        return -1;
    }

    private static String constantTextFragment(int index) {
        return packedTextFragments[index];
    }
}
