package remix.ui.hud;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import remix.ui.hud.HudElement;
import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;

import java.util.Collection;

public final class PotionEffectsHudElement implements HudElement {
    private Setting serverSetting;
    private Setting uidSetting;

    private ClientFontRenderer clientFontRenderer;


    private String formatPotionEffectLabel(PotionEffect potionEffect, Potion potion) {
        WatermarkHudElement.isWatermarkDisabled();

        StringBuilder stringbuilder =
                new StringBuilder(I18n.format(potion.getName(), new Object[0]));
        int amplifier = potionEffect.getAmplifier();

        switch (amplifier) {
            case 1:
                stringbuilder.append(" II");

                break;

            case 2:
                stringbuilder.append(" III");

                break;

            case 3:
                stringbuilder.append(" IV");
        }

        stringbuilder.append("\u00a7f: \u00a77");
        stringbuilder.append(Potion.getDurationString(potionEffect));
        return stringbuilder.toString();
    }

    public PotionEffectsHudElement(
            ClientFontRenderer clientFontRenderer, Setting setting, Setting settingValue) {
        this.serverSetting = setting;
        this.uidSetting = settingValue;
        this.clientFontRenderer = clientFontRenderer;
    }

    public void render(ScaledResolution scaledResolution) {
        WatermarkHudElement.isWatermarkDisabled();

        float fx = ec.currentScreen instanceof GuiChat ? 36.5F : 24.0F;
        if (!this.serverSetting.asBoolean() && !this.uidSetting.asBoolean()) {
            fx -= 12.0F;
        }

        Collection activePotionEffects = ec.player.getActivePotionEffects();
        if (!activePotionEffects.isEmpty()) {
            Potion[] bzT = Potion.potionTypes;
            int width = scaledResolution.getWidth();
            int j = scaledResolution.getHeight() - 4;

            for (java.util.Iterator<?> __recoveryIterator171_28531 =
                            (activePotionEffects).iterator();
                    (__recoveryIterator171_28531).hasNext(); ) {
                PotionEffect potionEffect = (PotionEffect) (__recoveryIterator171_28531).next();
                Potion potion = bzT[potionEffect.getPotionID()];
                String text = this.formatPotionEffectLabel(potionEffect, potion);
                this.clientFontRenderer.drawStringWithShadow(
                        text,
                        width - this.clientFontRenderer.getStringWidth(text),
                        j - fx,
                        potion.getLiquidColor());
                fx += 12.0F;
            }
        }
    }
}
