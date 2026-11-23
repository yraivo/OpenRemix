package remix.ui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;

import remix.module.ModuleBase;
import remix.module.ModuleManager;
import remix.module.ModuleMetadata;
import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.IdentityHashMap;
import java.util.Map;

public final class ArrayListHudElement implements HudElement {
    private Setting tpsSetting;
    public int visibleChatLines = 0;
    private Setting uidSetting;
    private Setting<HudSorting> sortingSetting;

    private Setting coordinatesSetting;
    private Setting<HudArrayMode> arrayModeSetting;
    private Setting<HudPosition> positionSetting;
    private Setting<HudColor> colorSetting;
    private Setting speedSetting;
    private ClientFontRenderer clientFontRenderer;

    private Setting serverSetting;
    private Setting tabGuiSetting;
    private Setting alphaSetting;
    private Setting fpsSetting;

    private final List<ModuleMetadata> visibleModules = new ArrayList<>();
    private final Map<ModuleMetadata, RenderEntry> frameEntries = new IdentityHashMap<>();

    private static final class RenderEntry {
        final String name;
        final String sortName;
        final int width;

        RenderEntry(String name, int width, String sortName) {
            this.name = name;
            this.width = width;
            this.sortName = sortName;
        }
    }

    private List<ModuleMetadata> collectVisibleEnabledModules() {
        WatermarkHudElement.isWatermarkEnabled();
        this.visibleModules.clear();
        this.frameEntries.clear();
        boolean alphabet = this.sortingSetting.getValue() == HudSorting.ALPHABET;
        for (ModuleBase module : ModuleManager.modules) {
            if (!module.isEnabled() || !module.isVisible()) continue;
            String name = module.getFormattedName();
            this.visibleModules.add(module);
            this.frameEntries.put(module, new RenderEntry(name,
                    this.clientFontRenderer.getStringWidth(name),
                    alphabet ? StringUtils.stripControlCodes(name) : name));
        }
        return this.visibleModules;
    }

    private int compareEntries(ModuleMetadata first, ModuleMetadata second) {
        RenderEntry a = this.frameEntries.get(first);
        RenderEntry b = this.frameEntries.get(second);
        switch (this.sortingSetting.getValue()) {
            case LENGTH: return Integer.compare(b.width, a.width);
            case INVERSE: return Integer.compare(a.width, b.width);
            case ALPHABET: return a.sortName.compareTo(b.sortName);
            default:
                return this.sortingSetting.getValue().compare(first, second,
                        this.colorSetting.getValue(), this.clientFontRenderer);
        }
    }

    public void render(ScaledResolution scaledResolution) {
        boolean flag2 = WatermarkHudElement.isWatermarkDisabled();
        List<ModuleMetadata> list = this.collectVisibleEnabledModules();
        list.sort(this::compareEntries);
        float heightValue = this.clientFontRenderer.getHeight();
        float f1 = heightValue + 4.0F;
        float width = scaledResolution.getWidth();
        float height = scaledResolution.getHeight();
        int i = -15592942;
        boolean flag = flag2;
        long j = MinecraftClient.getSystemTime();
        float f4 = 0.0F;
        boolean flag1 = ec.currentScreen instanceof GuiChat;
        float f5 = flag1 ? 24.5F : 12.0F;
        HudPosition value = (HudPosition) this.positionSetting.getValue();
        switch (value) {
            case TOP_RIGHT:
                break;

            case BOTTOM_RIGHT:
                f4 = height - f1;
                if (this.speedSetting.asBoolean()) {
                    f4 -= f5;
                }

                if (!this.uidSetting.asBoolean() && !this.serverSetting.asBoolean()) {
                    break;
                }

                f4 -= f5;

                break;

            case TOP_LEFT:
                f4 = ec.gameSettings.ofShowFps ? 26.0F : 21.0F;
                if (!this.tabGuiSetting.asBoolean()) {
                    break;
                }

                f4 += 85.0F;

                break;

            case BOTTOM_LEFT:
                int k = list.size() > 1 && this.visibleChatLines > 0 ? 1 : 0;
                f4 = height - f1 - (k == 0 ? 2 : ec.fontRendererObj.FONT_HEIGHT + (flag1 ? -8 : 4));
                if (this.coordinatesSetting.asBoolean()
                        || this.fpsSetting.asBoolean()
                        || this.tpsSetting.asBoolean()) {
                    f4 -= f5;
                }

                if (k != 0) {
                    f4 -= this.visibleChatLines * ec.fontRendererObj.FONT_HEIGHT + 4;
                }
        }

        int j1 = (int) System.currentTimeMillis() / 10;
        byte b0 = -10;
        int l = 0;

        while (l < list.size()) {
            ModuleMetadata moduleMetadataValue2;
            int i1;
            {
                moduleMetadataValue2 = (ModuleMetadata) list.get(l);
                i1 =
                        ((HudColor) this.colorSetting.getValue())
                                .getColorForModule(moduleMetadataValue2);
                if (this.colorSetting.getValue() == HudColor.RAINBOW) {
                    i1 = HudColor.getRainbowColor(3000.0, 120 * (list.size() - 1 - l)).getRGB();

                } else {

                    if (this.colorSetting.getValue() == HudColor.CANDYCANE) {
                        long k1 = System.currentTimeMillis();

                        long l1 = k1 % 1000L;

                        i1 = (l1 > 500L ? l : l + 1) % 2 == 0 ? -43941 : -1;

                    } else {

                        if (this.colorSetting.getValue() == HudColor.ASTOLFO) {
                            i1 =
                                    this.interpolateOscillatingColor(
                                                    new Color(255, 60, 234),
                                                    new Color(27, 179, 255),
                                                    Math.abs(j1) / 100.0)
                                            .getRGB();
                            j1 += b0;
                        }
                    }
                }
            }

            if (this.alphaSetting.asBoolean()) {
                float f6 =
                        1.0F
                                - MathHelper.abs(
                                        MathHelper.sin(
                                                        (float) j
                                                                % 2000.0F
                                                                / 2000.0F
                                                                * (float) Math.PI
                                                                * 2.0F)
                                                * 0.35F);
                Color color = new Color(i1);
                i1 =
                        new Color(
                                        (int) (color.getRed() * f6),
                                        (int) (color.getGreen() * f6),
                                        (int) (color.getBlue() * f6))
                                .getRGB();

                j -= 100L;
            }

            RenderEntry entry = this.frameEntries.get(moduleMetadataValue2);
            String formattedName = entry.name;
            float stringWidth = entry.width;
            float f7;
            float f8;
            switch (value) {
                case TOP_RIGHT:
                    f8 = width - stringWidth;
                    f7 = 1.0F;

                    break;

                case BOTTOM_RIGHT:
                    f8 = width - stringWidth;
                    f7 = 1.0F;

                    break;

                case TOP_LEFT:
                    f8 = 0.0F;
                    f7 = -1.0F;

                    break;

                case BOTTOM_LEFT:
                    f8 = 0.0F;
                    f7 = -1.0F;

                    break;

                default:
                    f8 = width - stringWidth;
                    f7 = 1.0F;
            }

            HudArrayMode valueValue = (HudArrayMode) this.arrayModeSetting.getValue();
            switch (valueValue) {
                case SIMPLE:
                    this.clientFontRenderer.drawStringWithShadow(
                            formattedName, f8 - 3.0F * f7, f4 + f1 / 2.0F - heightValue / 2.0F, i1);

                    break;

                case WHITE:
                    this.clientFontRenderer.drawStringWithShadow(
                            formattedName, f8 - 3.0F * f7, f4 + f1 / 2.0F - heightValue / 2.0F, -1);

                    break;

                case DARK:
                    this.clientFontRenderer.drawStringWithShadow(
                            formattedName.replace("\u00a77", "\u00a78"),
                            f8 - 3.0F * f7,
                            f4 + f1 / 2.0F - heightValue / 2.0F,
                            Color.GRAY.getRGB());

                    break;

                case FLAT:
                case TRANSPARENT:
                    byte b1 = 0;
                    float f9 = 0.0F;
                    float f10 = width;
                    float f11 = 0.0F;
                    float f12 = f8;
                    switch (value) {
                        case TOP_RIGHT:
                            break;

                        case BOTTOM_RIGHT:
                            f9 = -f1 - 1.0F;

                            break;

                        case TOP_LEFT:
                            f10 = 0.0F;
                            f11 = 0.0F;
                            f12 += stringWidth;
                            b1 = 1;

                            break;

                        case BOTTOM_LEFT:
                            f10 = 0.0F;
                            f11 = 0.0F;
                            f12 += stringWidth;
                            f9 = -f1 - 1.0F;
                            b1 = 1;
                    }

                    {
                        float f13 = b1 != 0 ? f12 + 7.0F : f12 - 7.0F;
                        Gui.drawRect(
                                f12 - 6.0F * f7,
                                f4,
                                f10,
                                f4 + f1,
                                valueValue == HudArrayMode.FLAT ? i : 2013265920);
                        Gui.drawRect(f12 - 7.0F * f7, f4, f12 - 6.0F * f7, f4 + f1, i1);
                        if (l != list.size() - 1) {
                            float f14 =
                                    b1 != 0
                                            ? 0
                                                    + this.frameEntries.get(list.get(l + 1)).width
                                                    + 6
                                            : scaledResolution.getWidth()
                                                    - this.frameEntries.get(list.get(l + 1)).width
                                                    - 6;
                            Gui.drawRect(f13, f4 + f1 + f9, f14, f4 + f1 + 1.0F + f9, i1);

                        } else {

                            Gui.drawRect(
                                    f13,
                                    f4 + f1 + f9,
                                    b1 != 0 ? 0.0 : scaledResolution.getWidth(),
                                    f4 + (f1 + 1.0F) + f9,
                                    i1);
                        }
                    }

                    this.clientFontRenderer.drawStringWithShadow(
                            formattedName, f8 - 3.0F * f7, f4 + f1 / 2.0F - heightValue / 2.0F, i1);

                    break;

                case SIDEBAR:
                    float f16 = 0.0F;
                    switch (value) {
                        case TOP_RIGHT:
                            f16 = width;

                            break;

                        case BOTTOM_RIGHT:
                            f16 = width;

                            break;

                        case TOP_LEFT:
                            f16 = 1.0F;

                            break;

                        case BOTTOM_LEFT:
                            f16 = 1.0F;

                            break;

                        default:
                            f16 = 1.0F;
                    }

                    Gui.drawRect(f8 - 8.0F, f4, f16 + stringWidth + 5.0F, f4 + f1, 1426063360);
                    Gui.drawRect(f16 - 1.0F, f4, f16, f4 + f1, i1);
                    this.clientFontRenderer.drawStringWithShadow(
                            formattedName, f8 - 4.5F * f7, f4 + f1 / 2.0F - heightValue / 2.0F, i1);
            }

            f4 += value.getDirectionalYOffset(f1);
            l++;
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            WatermarkHudElement.setWatermarkEnabled(!flag);
        }
    }

    public ArrayListHudElement(
            Setting<HudArrayMode> arrayModeSetting,
            Setting<HudPosition> positionSetting,
            Setting tabGuiSetting,
            Setting speedSetting,
            Setting serverSetting,
            Setting uidSetting,
            Setting coordinatesSetting,
            Setting fpsSetting,
            Setting tpsSetting,
            Setting<HudColor> colorSetting,
            Setting<HudSorting> sortingSetting,
            Setting alphaSetting,
            ClientFontRenderer clientFontRenderer) {
        this.arrayModeSetting = arrayModeSetting;
        this.positionSetting = positionSetting;
        this.tabGuiSetting = tabGuiSetting;
        this.speedSetting = speedSetting;
        this.serverSetting = serverSetting;
        this.uidSetting = uidSetting;
        this.coordinatesSetting = coordinatesSetting;
        this.fpsSetting = fpsSetting;
        this.tpsSetting = tpsSetting;
        this.sortingSetting = sortingSetting;
        this.colorSetting = colorSetting;
        this.alphaSetting = alphaSetting;
        this.clientFontRenderer = clientFontRenderer;
    }


    private Color interpolateOscillatingColor(Color color, Color color1, double d0) {
        if (d0 > 1.0) {
            double d1 = d0 % 1.0;
            int i = (int) d0;
            d0 = i % 2 == 0 ? d1 : 1.0 - d1;
        }

        double d2 = 1.0 - d0;
        int l = (int) (color.getRed() * d2 + color1.getRed() * d0);
        int j = (int) (color.getGreen() * d2 + color1.getGreen() * d0);
        int k = (int) (color.getBlue() * d2 + color1.getBlue() * d0);
        return new Color(l, j, k);
    }
}
