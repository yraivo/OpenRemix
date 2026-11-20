package net.minecraft.client.resources;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtendedIGuiListEntry;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public abstract class ResourcePackListEntry implements GuiListExtendedIGuiListEntry {

    protected MinecraftClient mc;
    protected GuiScreenResourcePacks resourcePacksGUI;
    private static ResourceLocation RESOURCE_PACKS_TEXTURE;

    static {
        net.minecraft.client.resources.ResourcePackListEntry.RESOURCE_PACKS_TEXTURE = new ResourceLocation("textures/gui/resource_packs.png");
    }

    public void drawEntry(int i2, int i, int j, int j2, int k2, int k, int l, boolean flag) {
        String text;
        String textValue = I18n.getLegacyResourceFlowMarker();
        this.cj();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        text = textValue;
        Gui.drawModalRectWithCustomSizedTexture(i, j, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
        label80:
        if ((this.mc.gameSettings.touchscreen || flag) && this.d()) {
            this.mc.getTextureManager().bindTexture(net.minecraft.client.resources.ResourcePackListEntry.RESOURCE_PACKS_TEXTURE);
            Gui.drawRect(i, j, i + 32, j + 32, -1601138544);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int j1 = k - i;
            int i1 = l - j;
            if (this.e()) {
                if (j1 < 32) {
                    Gui.drawModalRectWithCustomSizedTexture(
                            i, j, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    if (text != null) {
                        break label80;
                    }

                    Entity.setLegacyGlobalFlowMarker(new String[2]);
                }

                Gui.drawModalRectWithCustomSizedTexture(i, j, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                if (text != null) {
                    break label80;
                }
            }

            if (this.f()) {
                if (j1 < 16) {
                    Gui.drawModalRectWithCustomSizedTexture(
                            i, j, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    if (!(text != null)) {

                        Gui.drawModalRectWithCustomSizedTexture(i, j, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                } else {

                    Gui.drawModalRectWithCustomSizedTexture(i, j, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                }
            }

            if (this.fo()) {
                if (j1 < 32 && j1 > 16 && i1 < 16) {
                    Gui.drawModalRectWithCustomSizedTexture(
                            i, j, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    if (!(text != null)) {

                        Gui.drawModalRectWithCustomSizedTexture(i, j, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                } else {

                    Gui.drawModalRectWithCustomSizedTexture(i, j, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                }
            }

            if (this.aL()) {
                if (j1 < 32 && j1 > 16 && i1 > 16) {
                    Gui.drawModalRectWithCustomSizedTexture(
                            i, j, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                    if (!(text != null)) {

                        Gui.drawModalRectWithCustomSizedTexture(i, j, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                    }
                } else {

                    Gui.drawModalRectWithCustomSizedTexture(i, j, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                }
            }
        }

        String textValue2 = this.method_c60693f457aa70be7160();
        int l1 = this.mc.fontRendererObj.getStringWidth(textValue2);
        if (l1 > 157) {
            textValue2 =
                    this.mc.fontRendererObj.trimStringToWidth(
                                    textValue2, 157 - this.mc.fontRendererObj.getStringWidth("..."))
                            + "...";
        }

        this.mc.fontRendererObj.drawStringWithShadow(textValue2, i + 32 + 2, j + 1, 16777215);
        List list =
                this.mc.fontRendererObj.listFormattedStringToWidth(
                        this.method_e0e3092a59fee58fae52(), 157);
        int k1 = 0;

        while (k1 < 2 && k1 < list.size()) {
            this.mc.fontRendererObj.drawStringWithShadow(
                    (String) list.get(k1), i + 32 + 2, j + 12 + 10 * k1, 8421504);
            k1++;
            if (text == null) {
                break;
            }
        }
    }

    protected boolean e() {
        return !this.resourcePacksGUI.hasResourcePackEntry(this);
    }

    protected abstract String method_c60693f457aa70be7160();

    public void mouseReleased(int i, int j, int k, int l, int i1, int j1) {}

    protected boolean fo() {
        java.util.List list = this.resourcePacksGUI.getListContaining(this);
        int i = list.indexOf(this);
        return i > 0 && ((net.minecraft.client.resources.ResourcePackListEntry) list.get(i - 1)).d();
    }

    protected abstract String method_e0e3092a59fee58fae52();

    protected boolean d() {
        return true;
    }

    protected boolean aL() {
        java.util.List list = this.resourcePacksGUI.getListContaining(this);
        int i = list.indexOf(this);
        return i >= 0
                && i < list.size() - 1
                && ((net.minecraft.client.resources.ResourcePackListEntry) list.get(i + 1)).d();
    }

    public void setSelected(int i, int j, int k) {}

    protected abstract void cj();

    public ResourcePackListEntry(GuiScreenResourcePacks aKhValue) {
        this.resourcePacksGUI = aKhValue;
        this.mc = net.minecraft.client.MinecraftClient.getMinecraft();
    }

    public boolean mousePressed(
            int p171_0, int p171_1, int p171_2, int p171_3, int p171_4, int p171_5) {
        if (this.d() && p171_4 <= 32) {
            if (this.e()) {
                this.resourcePacksGUI.getListContaining(this).remove(this);
                this.resourcePacksGUI.getSelectedResourcePacks().add(0, this);
                this.resourcePacksGUI.markChanged();
                return true;
            }

            if (p171_4 < 16 && this.f()) {
                this.resourcePacksGUI.getListContaining(this).remove(this);
                this.resourcePacksGUI.getAvailableResourcePacks().add(0, this);
                this.resourcePacksGUI.markChanged();
                return true;
            }

            if (p171_4 > 16 && p171_5 < 16 && this.fo()) {
                java.util.List listValue = this.resourcePacksGUI.getListContaining(this);
                int l1 = listValue.indexOf(this);
                listValue.remove(this);
                listValue.add(l1 - 1, this);
                this.resourcePacksGUI.markChanged();
                return true;
            }

            if (p171_4 > 16 && p171_5 > 16 && this.aL()) {
                java.util.List list = this.resourcePacksGUI.getListContaining(this);
                int k1 = list.indexOf(this);
                list.remove(this);
                list.add(k1 + 1, this);
                this.resourcePacksGUI.markChanged();
                return true;
            }
        }

        return false;
    }

    protected boolean f() {
        return this.resourcePacksGUI.hasResourcePackEntry(this);
    }
}
