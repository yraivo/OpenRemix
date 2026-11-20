package remix.ui.hud;

import remix.setting.SettingOption;

import remix.module.ModuleMetadata;

import java.awt.Color;
import java.util.function.Function;

public enum HudColor implements SettingOption {
    CLIENT(
            "Client",
            (java.util.function.Function<remix.module.ModuleMetadata, java.lang.Integer>)
                    (class_6ef578fa5f951c31aee7 -> -13710223)),
    RED(
            "Red",
            (java.util.function.Function<remix.module.ModuleMetadata, java.lang.Integer>)
                    (class_6ef578fa5f951c31aee7 -> -769226)),
    GREEN(
            "Green",
            (java.util.function.Function<remix.module.ModuleMetadata, java.lang.Integer>)
                    (class_6ef578fa5f951c31aee7 -> -11751600)),
    BLUE(
            "Blue",
            (java.util.function.Function<remix.module.ModuleMetadata, java.lang.Integer>)
                    (class_6ef578fa5f951c31aee7 -> -14575885)),
    PINK(
            "Pink",
            (java.util.function.Function<remix.module.ModuleMetadata, java.lang.Integer>)
                    (class_6ef578fa5f951c31aee7 -> -618530)),
    ORANGE(
            "Orange",
            (java.util.function.Function<remix.module.ModuleMetadata, java.lang.Integer>)
                    (class_6ef578fa5f951c31aee7 -> -30720)),
    THEALTENING(
            "TheAltening",
            (java.util.function.Function<remix.module.ModuleMetadata, java.lang.Integer>)
                    (class_6ef578fa5f951c31aee7 -> -12465804)),
    RANDOM(
            "Random",
            (java.util.function.Function<remix.module.ModuleMetadata, java.lang.Integer>)
                    (ModuleMetadata::getDisplayColor)),
    RAINBOW(
            "Rainbow",
            (java.util.function.Function<remix.module.ModuleMetadata, java.lang.Integer>)
                    (class_6ef578fa5f951c31aee7 ->
                            getRainbowColor(15000.0, 1).getRGB())),
    CATEGORY(
            "Category",
            (java.util.function.Function<remix.module.ModuleMetadata, java.lang.Integer>)
                    (class_6ef578fa5f951c31aee7 ->
                            class_6ef578fa5f951c31aee7
                                    .getCategory()
                                    .getColor())),
    CANDYCANE(
            "CandyCane",
            (java.util.function.Function<remix.module.ModuleMetadata, java.lang.Integer>)
                    (class_6ef578fa5f951c31aee7 -> -43941)),
    ASTOLFO(
            "Astolfo",
            (java.util.function.Function<remix.module.ModuleMetadata, java.lang.Integer>)
                    (class_6ef578fa5f951c31aee7 -> -5798145));

    private String displayName;
    private Function<ModuleMetadata, Integer> gU;

    // region String decoding support

    // endregion

    HudColor(String displayNameValue, Function<ModuleMetadata, Integer> function) {
        this.gU = function;
        this.displayName = displayNameValue;
    }

    public int getColorForModule(ModuleMetadata moduleMetadata) {
        return this.gU.apply(moduleMetadata);
    }

    // region String decoding support

    // endregion

    public static Color getRainbowColor(double d0, int i) {
        HudMode.isNotInitialized();

        float f = (float) ((System.currentTimeMillis() + i) % d0);
        f = (float) (f / d0);

        Color color = Color.getHSBColor(f, 0.6F, 0.75F);

        return color;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables

    // endregion

}
