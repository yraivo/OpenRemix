package remix.ui.hud;

import remix.setting.SettingOption;
import remix.util.function.QuadFunction;
import remix.util.render.Render2D3DUtils;

import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;
import remix.module.ModuleMetadata;
import remix.ui.font.ClientFontRenderer;

public enum HudSorting implements SettingOption {
    ALPHABET(
            "Alphabet",
            (remix.util.function.QuadFunction<
                            remix.module.ModuleMetadata,
                            remix.module.ModuleMetadata,
                            remix.ui.hud.HudColor,
                            remix.ui.font.ClientFontRenderer,
                            java.lang.Integer>)
                    ((class_6ef578fa5f951c31aee7,
                            class_6ef578fa5f951c31aee71,
                            class_8ea66f30a59f38a9b117,
                            class_134a537fe0710949f617) -> {
                        String sx = StringUtils.stripControlCodes(class_6ef578fa5f951c31aee7.getFormattedName());
                        String s1x = StringUtils.stripControlCodes(class_6ef578fa5f951c31aee71.getFormattedName());
                        return sx.compareTo(s1x);
                    })),
    LENGTH(
            "Length",
            (remix.util.function.QuadFunction<
                            remix.module.ModuleMetadata,
                            remix.module.ModuleMetadata,
                            remix.ui.hud.HudColor,
                            remix.ui.font.ClientFontRenderer,
                            java.lang.Integer>)
                    ((class_6ef578fa5f951c31aee7,
                            class_6ef578fa5f951c31aee71,
                            class_8ea66f30a59f38a9b117,
                            class_134a537fe0710949f617) -> {
                        float f =
                                class_134a537fe0710949f617.getStringWidth(
                                        class_6ef578fa5f951c31aee7.getFormattedName());
                        float f1 =
                                class_134a537fe0710949f617.getStringWidth(
                                        class_6ef578fa5f951c31aee71.getFormattedName());
                        return -Float.compare(f, f1);
                    })),
    INVERSE(
            "Inverse",
            (remix.util.function.QuadFunction<
                            remix.module.ModuleMetadata,
                            remix.module.ModuleMetadata,
                            remix.ui.hud.HudColor,
                            remix.ui.font.ClientFontRenderer,
                            java.lang.Integer>)
                    ((class_6ef578fa5f951c31aee7,
                            class_6ef578fa5f951c31aee71,
                            class_8ea66f30a59f38a9b117,
                            class_134a537fe0710949f617) -> {
                        float f =
                                class_134a537fe0710949f617.getStringWidth(
                                        class_6ef578fa5f951c31aee7.getFormattedName());
                        float f1 =
                                class_134a537fe0710949f617.getStringWidth(
                                        class_6ef578fa5f951c31aee71.getFormattedName());
                        return Float.compare(f, f1);
                    })),
    RED(
            "Red",
            (remix.util.function.QuadFunction<
                            remix.module.ModuleMetadata,
                            remix.module.ModuleMetadata,
                            remix.ui.hud.HudColor,
                            remix.ui.font.ClientFontRenderer,
                            java.lang.Integer>)
                    ((class_6ef578fa5f951c31aee7,
                            class_6ef578fa5f951c31aee71,
                            class_8ea66f30a59f38a9b117,
                            class_134a537fe0710949f617) -> {
                        int ix =
                                class_8ea66f30a59f38a9b117.getColorForModule(
                                        class_6ef578fa5f951c31aee7);
                        int jx =
                                class_8ea66f30a59f38a9b117.getColorForModule(
                                        class_6ef578fa5f951c31aee71);
                        float[] afloat = Render2D3DUtils.getRGB(ix);
                        float[] afloat1 =
                                Render2D3DUtils.getRGB(jx);
                        float f = afloat[0] - afloat[1] - afloat[2];
                        float f1 = afloat1[0] - afloat1[1] - afloat1[2];
                        return -Float.compare(f, f1);
                    })),
    GREEN(
            "Green",
            (remix.util.function.QuadFunction<
                            remix.module.ModuleMetadata,
                            remix.module.ModuleMetadata,
                            remix.ui.hud.HudColor,
                            remix.ui.font.ClientFontRenderer,
                            java.lang.Integer>)
                    ((class_6ef578fa5f951c31aee7,
                            class_6ef578fa5f951c31aee71,
                            class_8ea66f30a59f38a9b117,
                            class_134a537fe0710949f617) -> {
                        int ix =
                                class_8ea66f30a59f38a9b117.getColorForModule(
                                        class_6ef578fa5f951c31aee7);
                        int jx =
                                class_8ea66f30a59f38a9b117.getColorForModule(
                                        class_6ef578fa5f951c31aee71);
                        float[] afloat = Render2D3DUtils.getRGB(ix);
                        float[] afloat1 =
                                Render2D3DUtils.getRGB(jx);
                        float f = afloat[1] - afloat[0] - afloat[2];
                        float f1 = afloat1[1] - afloat1[0] - afloat1[2];
                        return -Float.compare(f, f1);
                    })),
    BLUE(
            "Blue",
            (remix.util.function.QuadFunction<
                            remix.module.ModuleMetadata,
                            remix.module.ModuleMetadata,
                            remix.ui.hud.HudColor,
                            remix.ui.font.ClientFontRenderer,
                            java.lang.Integer>)
                    ((class_6ef578fa5f951c31aee7,
                            class_6ef578fa5f951c31aee71,
                            class_8ea66f30a59f38a9b117,
                            class_134a537fe0710949f617) -> {
                        int l1 =
                                class_8ea66f30a59f38a9b117.getColorForModule(
                                        class_6ef578fa5f951c31aee7);
                        int i2 =
                                class_8ea66f30a59f38a9b117.getColorForModule(
                                        class_6ef578fa5f951c31aee71);
                        float[] afloat = Render2D3DUtils.getRGB(l1);
                        float[] afloat1 =
                                Render2D3DUtils.getRGB(i2);
                        float f = afloat[2] - afloat[0] - afloat[1];
                        float f1 = afloat1[2] - afloat1[0] - afloat1[1];
                        return -Float.compare(f, f1);
                    }));

    private String displayName;

    private QuadFunction<
                    ModuleMetadata, ModuleMetadata, HudColor, ClientFontRenderer, Integer>
            hm;

    // region String decoding support

    // endregion

    public String getDisplayName() {
        return this.displayName;
    }

    // region String decoding support

    // endregion

    public int compare(
            ModuleMetadata moduleMetadata,
            ModuleMetadata moduleMetadataValue,
            HudColor hudColor,
            ClientFontRenderer clientFontRenderer) {
        boolean flag = HudMode.isNotInitialized();
        int i =
                (Integer)
                        this.hm.a(
                                moduleMetadata, moduleMetadataValue, hudColor, clientFontRenderer);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            HudMode.setInitialized(!flag);
        }

        return i;
    }

    // region Static initialization and string tables

    // endregion

    // region String decoding support

    // endregion

    HudSorting(
            String displayNameValue,
            QuadFunction<
                            ModuleMetadata, ModuleMetadata, HudColor, ClientFontRenderer, Integer>
                    quadFunction) {
        this.hm = quadFunction;
        this.displayName = displayNameValue;
    }
}
