package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import org.lwjgl.util.glu.Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GuiEnchantment extends GuiContainer {
    private static ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE;
    public int Kz;
    private ContainerEnchantment container;
    ItemStack atC;
    private IWorldNameable atD;
    public float atE;
    private Random random = new Random();
    private InventoryPlayer playerInventory;
    public float atH;
    private static String[] packedTextFragments;
    public float atJ;
    private static ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE;

    public float Mb;
    public float Oc;

    private static ModelBook MODEL_BOOK;
    public float KR;

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.GuiEnchantment.ENCHANTMENT_TABLE_GUI_TEXTURE);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        GlStateManager.pushMatrix();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        ScaledResolution scaledResolution =
                new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.viewport(
                (scaledResolution.getWidth() - 320) / 2 * scaledResolution.getScaleFactor(),
                (scaledResolution.getHeight() - 240) / 2 * scaledResolution.getScaleFactor(),
                320 * scaledResolution.getScaleFactor(),
                240 * scaledResolution.getScaleFactor());
        GlStateManager.translate(-0.34F, 0.23F, 0.0F);
        Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
        float f1 = 1.0F;
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(0.0F, 3.3F, -16.0F);
        GlStateManager.scale(f1, f1, f1);
        float f2 = 5.0F;
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        GlStateManager.scale(f2, f2, f2);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        String[] astring = astring1;
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.GuiEnchantment.ENCHANTMENT_TABLE_BOOK_TEXTURE);
        GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
        float f3 = this.Mb + (this.KR - this.Mb) * f;
        GlStateManager.translate((1.0F - f3) * 0.2F, (1.0F - f3) * 0.1F, (1.0F - f3) * 0.25F);
        GlStateManager.rotate(-(1.0F - f3) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        float f4 = this.atJ + (this.atE - this.atJ) * f + 0.25F;
        float f5 = this.atJ + (this.atE - this.atJ) * f + 0.75F;
        f4 = (f4 - MathHelper.truncateDoubleToInt(f4)) * 1.6F - 0.3F;
        f5 = (f5 - MathHelper.truncateDoubleToInt(f5)) * 1.6F - 0.3F;
        if (f4 < 0.0F) {
            f4 = 0.0F;
        }

        if (f5 < 0.0F) {
            f5 = 0.0F;
        }

        if (f4 > 1.0F) {
            f4 = 1.0F;
        }

        if (f5 > 1.0F) {
            f5 = 1.0F;
        }

        GlStateManager.enableRescaleNormal();
        net.minecraft.client.gui.GuiEnchantment.MODEL_BOOK.render((Entity) null, 0.0F, f4, f5, f3, 0.0F, 0.0625F);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.matrixMode(5889);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        EnchantmentNameParts.getInstance().reseedRandomGenerator(this.container.xpSeed);
        int i1 = this.container.getLapisAmount();
        int j1 = 0;

        while (j1 < 3) {
            label73:
            {
                int k1 = k + 60;
                int l1 = k1 + 20;
                byte b0 = 86;
                String text = EnchantmentNameParts.getInstance().generateNewRandomName();
                this.zLevel = 0.0F;
                this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.GuiEnchantment.ENCHANTMENT_TABLE_GUI_TEXTURE);
                int i2 = this.container.enchantLevels[j1];
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                if (i2 == 0) {
                    this.drawTexturedModalRect(k1, l + 14 + 19 * j1, 0, 185, 108, 19);
                    if (astring == null) {
                        break label73;
                    }
                }

                String textValue;
                int j2;
                label75:
                {
                    textValue = packedTextFragments[0] + i2;
                    FontRenderer amc = this.mc.standardGalacticFontRenderer;
                    j2 = 6839882;
                    if ((i1 < j1 + 1 || this.mc.player.experienceLevel < i2)
                            && !this.mc.player.capabilities.isCreativeMode) {
                        this.drawTexturedModalRect(k1, l + 14 + 19 * j1, 0, 185, 108, 19);
                        this.drawTexturedModalRect(k1 + 1, l + 15 + 19 * j1, 16 * j1, 239, 16, 16);
                        amc.drawSplitString(text, l1, l + 16 + 19 * j1, b0, (j2 & 16711422) >> 1);
                        j2 = 4226832;
                        if (astring == null) {
                            break label75;
                        }
                    }

                    {
                        int k2 = i - (k + 60);
                        int l2 = j - (l + 14 + 19 * j1);
                        if (k2 >= 0 && l2 >= 0 && k2 < 108 && l2 < 19) {
                            this.drawTexturedModalRect(k1, l + 14 + 19 * j1, 0, 204, 108, 19);
                            j2 = 16777088;
                            if (!(astring == null)) {

                                this.drawTexturedModalRect(k1, l + 14 + 19 * j1, 0, 166, 108, 19);
                            }
                        } else {

                            this.drawTexturedModalRect(k1, l + 14 + 19 * j1, 0, 166, 108, 19);
                        }
                    }

                    this.drawTexturedModalRect(k1 + 1, l + 15 + 19 * j1, 16 * j1, 223, 16, 16);
                    amc.drawSplitString(text, l1, l + 16 + 19 * j1, b0, j2);
                    j2 = 8453920;
                }

                FontRenderer anp = this.mc.fontRendererObj;
                anp.drawStringWithShadow(
                        textValue,
                        l1 + 86 - anp.getStringWidth(textValue),
                        l + 16 + 19 * j1 + 7,
                        j2);
            }

            j1++;
            if (astring != null) {
                break;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            Gui.setLegacyGuiRenderMarker(new String[1]);
        }
    }

    public void method_784e95624d6d70fe7f50() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        ItemStack atCValue = this.inventorySlots.getSlot(0).getStack();
        String[] astring = astring1;
        if (!ItemStack.areItemStacksEqual(atCValue, this.atC)) {
            this.atC = atCValue;

            do {
                this.atH = this.atH + (this.random.nextInt(4) - this.random.nextInt(4));
            } while (this.atE <= this.atH + 1.0F && this.atE >= this.atH - 1.0F);
        }

        this.Kz++;
        this.atJ = this.atE;
        this.Mb = this.KR;
        byte b0 = 0;
        int i = 0;

        while (i < 3) {
            if (this.container.enchantLevels[i] != 0) {
                b0 = 1;
            }

            i++;
            if (astring != null) {
                break;
            }
        }

        {
            if (b0 != 0) {
                this.KR += 0.2F;
                if (!(astring == null)) {

                    this.KR -= 0.2F;
                }
            } else {

                this.KR -= 0.2F;
            }
        }

        this.KR = MathHelper.clamp(this.KR, 0.0F, 1.0F);
        float f1 = (this.atH - this.atE) * 0.4F;
        float f = 0.2F;
        float f2 = MathHelper.clamp(f1, -f, f);
        this.Oc = this.Oc + (f2 - this.Oc) * 0.9F;
        this.atE = this.atE + this.Oc;
    }

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.fontRendererObj.drawString(
                this.atD.getDisplayName().getUnformattedText(), 12, 5, 4210752);
        this.fontRendererObj.drawString(
                this.playerInventory.getDisplayName().getUnformattedText(),
                8,
                this.ySize - 96 + 2,
                4210752);
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        super.mouseClicked(i, j, k);
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int l = (this.width - this.xSize) / 2;
        int i1 = (this.height - this.ySize) / 2;
        String[] astring = astring1;
        int j1 = 0;

        while (j1 < 3) {
            int k1 = i - (l + 60);
            int l1 = j - (i1 + 14 + 19 * j1);
            if (k1 >= 0
                    && l1 >= 0
                    && k1 < 108
                    && l1 < 19
                    && this.container.enchantItem(this.mc.player, j1)) {
                this.mc.playerController.sendEnchantPacket(this.container.windowId, j1);
            }

            j1++;
            if (astring != null) {
                break;
            }
        }
    }

    static {
        net.minecraft.client.gui.GuiEnchantment.packedTextFragments = new String[8];
        net.minecraft.client.gui.GuiEnchantment.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiEnchantment.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.GuiEnchantment.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.GuiEnchantment.packedTextFragments[3] = new String("");
        net.minecraft.client.gui.GuiEnchantment.packedTextFragments[4] = new String("");
        net.minecraft.client.gui.GuiEnchantment.packedTextFragments[5] = new String("");
        net.minecraft.client.gui.GuiEnchantment.ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
        net.minecraft.client.gui.GuiEnchantment.ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
        net.minecraft.client.gui.GuiEnchantment.MODEL_BOOK = new ModelBook();
    }

    public void drawScreen(int i, int j, float f) {
        super.drawScreen(i, j, f);
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        boolean isCreativeMode = this.mc.player.capabilities.isCreativeMode;
        String[] astring = astring1;
        int k = this.container.getLapisAmount();
        int l = 0;

        while (l < 3) {
            int i1 = this.container.enchantLevels[l];
            int j1 = this.container.field_178151_h[l];
            int k1 = l + 1;
            if (this.isPointInRegion(60, 14 + 19 * l, 108, 17, i, j) && i1 > 0 && j1 >= 0) {
                ArrayList arrayList = Lists.newArrayList();
                if (j1 >= 0 && Enchantment.getEnchantmentById(j1 & 0xFF) != null) {
                    String text =
                            Enchantment.getEnchantmentById(j1 & 0xFF)
                                    .getTranslatedName((j1 & 0xFF00) >> 8);
                    arrayList.add(
                            EnumChatFormatting.WHITE.toString()
                                    + EnumChatFormatting.ITALIC.toString()
                                    + I18n.format("container.enchant.clue", new Object[] {text}));
                }

                label41:
                if (!isCreativeMode) {
                    if (j1 >= 0) {
                        arrayList.add(packedTextFragments[1]);
                    }

                    if (this.mc.player.experienceLevel < i1) {
                        arrayList.add(
                                EnumChatFormatting.RED.toString()
                                        + "Level Requirement: "
                                        + this.container.enchantLevels[l]);
                        if (astring == null) {
                            break label41;
                        }
                    }

                    String textValue;
                    {
                        textValue = packedTextFragments[2];
                        if (k1 == 1) {
                            textValue = I18n.format("container.enchant.lapis.one", new Object[0]);
                            if (!(astring == null)) {

                                textValue = I18n.format("container.enchant.lapis.many", new Object[] {k1});
                            }
                        } else {

                            textValue = I18n.format("container.enchant.lapis.many", new Object[] {k1});
                        }
                    }

                    {
                        if (k >= k1) {
                            arrayList.add(
                                    EnumChatFormatting.GRAY.toString()
                                            + packedTextFragments[3]
                                            + textValue);
                            if (!(astring == null)) {

                                arrayList.add(
                                        EnumChatFormatting.RED.toString()
                                                + packedTextFragments[4]
                                                + textValue);
                            }
                        } else {

                            arrayList.add(
                                    EnumChatFormatting.RED.toString()
                                            + packedTextFragments[4]
                                            + textValue);
                        }
                    }

                    {
                        if (k1 == 1) {
                            textValue = I18n.format("container.enchant.level.one", new Object[0]);
                            if (!(astring == null)) {

                                textValue = I18n.format("container.enchant.level.many", new Object[] {k1});
                            }
                        } else {

                            textValue = I18n.format("container.enchant.level.many", new Object[] {k1});
                        }
                    }

                    arrayList.add(
                            EnumChatFormatting.GRAY.toString()
                                    + packedTextFragments[5]
                                    + textValue);
                }

                this.drawHoveringText(arrayList, i, j);
                if (astring == null) {
                    break;
                }
            }

            l++;
            if (astring != null) {
                break;
            }
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public GuiEnchantment(InventoryPlayer atGValue, World world, IWorldNameable atDValue) {
        super(new ContainerEnchantment(atGValue, world));
        this.playerInventory = atGValue;
        this.container = (ContainerEnchantment) this.inventorySlots;
        this.atD = atDValue;
    }

    public void updateScreen() {
        super.updateScreen();
        this.method_784e95624d6d70fe7f50();
    }
}
