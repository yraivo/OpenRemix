package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuReciepient;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiSpectator extends Gui implements ISpectatorMenuReciepient {
    private static ResourceLocation dz;
    private SpectatorMenu ase;

    public static ResourceLocation sJ;
    private long vZ;
    private MinecraftClient asg;

    private float aK() {
        long j = this.vZ - MinecraftClient.getSystemTime();

        long i = j + 5000L;
        return MathHelper.clamp((float) i / 2000.0F, 0.0F, 1.0F);
    }

    public void method_22ae0a8170d5c831fff3(SpectatorMenu spectatorMenu) {
        this.ase = null;

        this.vZ = 0L;
    }

    public void renderSpectatorSelectionTooltip(ScaledResolution scaledResolution) {
        int i = (int) (this.aK() * 255.0F);
        if (i > 3 && this.ase != null) {
            ISpectatorMenuObject iSpectatorMenuObject = this.ase.vU();
            String text =
                    iSpectatorMenuObject != SpectatorMenu.auD
                            ? iSpectatorMenuObject.getSpectatorName().getFormattedText()
                            : this.ase.vX().vT().getFormattedText();
            if (text != null) {
                int j =
                        (scaledResolution.getWidth()
                                        - this.asg.fontRendererObj.getStringWidth(text))
                                / 2;
                int k = scaledResolution.getHeight() - 35;
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                this.asg.fontRendererObj.drawStringWithShadow(text, j, k, 16777215 + (i << 24));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
    }

    public void openOrConfirmMenu() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.vZ = MinecraftClient.getSystemTime();
        String[] astring = astring1;
        if (this.isMenuOpen()) {
            int i = this.ase.cR();
            if (i != -1) {
                this.ase.b(i);
            }

            if (astring == null) {
                return;
            }
        }

        this.ase = new SpectatorMenu(this);
    }

    public boolean isMenuOpen() {
        return this.ase != null;
    }

    private void method_94dc1240a13d113c07b5(
            int i, int j, float f, float f1, ISpectatorMenuObject iSpectatorMenuObject) {
        this.asg.getTextureManager().bindTexture(sJ);
        if (iSpectatorMenuObject != SpectatorMenu.auD) {
            int k = (int) (f1 * 255.0F);
            GlStateManager.pushMatrix();
            GlStateManager.translate(j, f, 0.0F);
            float f2 = iSpectatorMenuObject.method_b417d7500ec59d69e373() ? 1.0F : 0.25F;
            GlStateManager.color(f2, f2, f2, f1);
            iSpectatorMenuObject.method_8b0a960c841ff568c052(f2, k);
            GlStateManager.popMatrix();
            String text =
                    String.valueOf(
                            GameSettings.getKeyDisplayString(
                                    this.asg.gameSettings.keyBindsHotbar[i].getKeyCode()));
            if (k > 3 && iSpectatorMenuObject.method_b417d7500ec59d69e373()) {
                this.asg.fontRendererObj.drawStringWithShadow(
                        text,
                        j + 19 - 2 - this.asg.fontRendererObj.getStringWidth(text),
                        f + 6.0F + 3.0F,
                        16777215 + (k << 24));
            }
        }
    }

    static {
        dz = new ResourceLocation("textures/gui/widgets.png");
        sJ = new ResourceLocation("textures/gui/spectator_widgets.png");
    }

    public void renderSpectatorHotbar(ScaledResolution scaledResolution, float f3) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.ase != null) {
            float f = this.aK();
            if (f <= 0.0F) {
                this.ase.l();
                if (astring == null) {
                    return;
                }
            }

            int i = scaledResolution.getWidth() / 2;
            float zLevelValue = this.zLevel;
            this.zLevel = -90.0F;
            float f2 = scaledResolution.getHeight() - 22.0F * f;
            SpectatorDetails spectatorDetails = this.ase.vW();
            this.method_46fcc6ac20850b42922e(scaledResolution, f, i, f2, spectatorDetails);
            this.zLevel = zLevelValue;
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public void selectHotbarSlot(int i) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.vZ = MinecraftClient.getSystemTime();
        String[] astring = astring1;
        if (this.ase != null) {
            this.ase.b(i);
            if (astring == null) {
                return;
            }
        }

        this.ase = new SpectatorMenu(this);
    }

    protected void method_46fcc6ac20850b42922e(
            ScaledResolution scaledResolution,
            float f,
            int i,
            float f1,
            SpectatorDetails spectatorDetails) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        GlStateManager.color(1.0F, 1.0F, 1.0F, f);
        String[] astring = astring1;
        this.asg.getTextureManager().bindTexture(dz);
        this.drawTexturedModalRect(i - 91, f1, 0, 0, 182, 22);
        if (spectatorDetails.method_9b417d27949cba33a0d0() >= 0) {
            this.drawTexturedModalRect(
                    i - 91 - 1 + spectatorDetails.method_9b417d27949cba33a0d0() * 20,
                    f1 - 1.0F,
                    0,
                    22,
                    24,
                    22);
        }

        RenderHelper.enableGUIStandardItemLighting();
        int j = 0;

        while (j < 9) {
            this.method_94dc1240a13d113c07b5(
                    j,
                    scaledResolution.getWidth() / 2 - 90 + j * 20 + 2,
                    f1 + 3.0F,
                    f,
                    spectatorDetails.T(j));
            j++;
            if (astring != null) {
                break;
            }
        }

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }

    public void scrollMenu(int i) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int j = this.ase.cR() + i;
        String[] astring = astring1;

        while (j >= 0
                && j <= 8
                && (this.ase.T(j) == SpectatorMenu.auD
                        || !this.ase.T(j).method_b417d7500ec59d69e373())) {
            j += i;
            if (astring != null) {
                break;
            }
        }

        if (j >= 0 && j <= 8) {
            this.ase.b(j);
            this.vZ = MinecraftClient.getSystemTime();
        }
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public GuiSpectator(MinecraftClient asgValue) {
        super();
        this.asg = asgValue;
    }
}
