package remix.ui.clickgui;

public enum ClickGuiEntryKind {
    CATEGORY,
    MODULE,
    SETTING;

    private static int[] legacyTabGuiTraversalMarker;

    // region String decoding support

    // endregion


    // region String decoding support

    // endregion

    public static void setLegacyTabGuiTraversalMarker(int[] aint) {
        remix.ui.clickgui.ClickGuiEntryKind.legacyTabGuiTraversalMarker = aint;
    }

    public static int[] getLegacyTabGuiTraversalMarker() {
        return remix.ui.clickgui.ClickGuiEntryKind.legacyTabGuiTraversalMarker;
    }

    private ClickGuiEntryKind() {}
}
