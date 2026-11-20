package net.minecraft.client.renderer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.Collection;

public abstract class InventoryEffectRenderer extends GuiContainer {

    private static String[] packedTextFragments;
    private boolean hasActivePotionEffects;

    private void drawActivePotionEffects() {
        int j = this.guiLeft - 124;
        int k = this.guiTop;

        int j1 = WorldRenderer.getLegacyRenderFlowMarker();
        Collection activePotionEffects = this.mc.player.getActivePotionEffects();
        int i = j1;
        if (!activePotionEffects.isEmpty()) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            int l = 33;
            if (activePotionEffects.size() > 5) {
                l = 132 / (activePotionEffects.size() - 1);
            }

            for (java.util.Iterator<?> __recoveryIterator171_1144 =
                            (this.mc.player.getActivePotionEffects()).iterator();
                    (__recoveryIterator171_1144).hasNext(); ) {
                PotionEffect potionEffect = (PotionEffect) (__recoveryIterator171_1144).next();
                Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.inventory.GuiContainer.inventoryBackground);
                this.drawTexturedModalRect(j, k, 0, 166, 140, 32);
                if (potion.hasStatusIcon()) {
                    int statusIconIndex = potion.getStatusIconIndex();
                    this.drawTexturedModalRect(
                            j + 6,
                            k + 7,
                            0 + statusIconIndex % 8 * 18,
                            198 + statusIconIndex / 8 * 18,
                            18,
                            18);
                }

                String text;
                label44:
                {
                    text = I18n.format(potion.getName(), new Object[0]);
                    if (potionEffect.getAmplifier() == 1) {
                        text =
                                text
                                        + packedTextFragments[0]
                                        + I18n.format("enchantment.level.2", new Object[0]);
                        if (i != 0) {
                            break label44;
                        }
                    }

                    if (potionEffect.getAmplifier() == 2) {
                        text =
                                text
                                        + packedTextFragments[1]
                                        + I18n.format("enchantment.level.3", new Object[0]);
                        if (i != 0) {
                            break label44;
                        }
                    }

                    if (potionEffect.getAmplifier() == 3) {
                        text =
                                text
                                        + packedTextFragments[2]
                                        + I18n.format("enchantment.level.4", new Object[0]);
                    }
                }

                this.fontRendererObj.drawStringWithShadow(text, j + 10 + 18, k + 6, 16777215);
                String textValue = Potion.getDurationString(potionEffect);
                this.fontRendererObj.drawStringWithShadow(
                        textValue, j + 10 + 18, k + 6 + 10, 8355711);
                k += l;
                if (i == 0) {
                    break;
                }
            }
        }
    }

    protected void updateActivePotionEffects() {
        int i = WorldRenderer.getLegacyRenderFlowMarker();
        if (!this.mc.player.getActivePotionEffects().isEmpty()) {
            this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
            this.hasActivePotionEffects = true;
            if (i != 0) {
                return;
            }
        }

        this.guiLeft = (this.width - this.xSize) / 2;
        this.hasActivePotionEffects = false;
    }

    static {
        net.minecraft.client.renderer.InventoryEffectRenderer.packedTextFragments = new String[4];
        net.minecraft.client.renderer.InventoryEffectRenderer.packedTextFragments[0] = new String(" ");
        net.minecraft.client.renderer.InventoryEffectRenderer.packedTextFragments[1] = new String(" ");
        net.minecraft.client.renderer.InventoryEffectRenderer.packedTextFragments[2] = new String(" ");
    }

    public void initGui() {
        super.initGui();
        this.updateActivePotionEffects();
    }

    public void drawScreen(int i, int j, float f) {
        super.drawScreen(i, j, f);
        if (this.hasActivePotionEffects) {
            this.drawActivePotionEffects();
        }
    }

    public InventoryEffectRenderer(Container container) {
        super(container);
    }
}
