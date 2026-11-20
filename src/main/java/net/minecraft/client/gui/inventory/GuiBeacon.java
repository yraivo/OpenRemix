package net.minecraft.client.gui.inventory;

import io.netty.buffer.Unpooled;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GuiBeacon extends GuiContainer {
    private GuiBeaconConfirmButton beaconConfirmButton;
    private boolean buttonsNotDrawn;

    private IInventory tileBeacon;

    private static Logger logger;
    private static ResourceLocation beaconGuiTextures;

    static ResourceLocation access$000() {
        return net.minecraft.client.gui.inventory.GuiBeacon.beaconGuiTextures;
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(
                this.beaconConfirmButton =
                        new GuiBeaconConfirmButton(
                                this, -1, this.guiLeft + 164, this.guiTop + 107));
        this.buttonList.add(
                new GuiBeaconCancelButton(this, -2, this.guiLeft + 190, this.guiTop + 107));
        this.buttonsNotDrawn = true;
        this.beaconConfirmButton.enabled = false;
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        int[] aint = CreativeCrafting.getLegacyCreativeFlowMarker();
        if (guiButton.id == -2) {
            this.mc.displayGuiScreen((GuiScreen) null);
            if (aint == null) {
                return;
            }
        }

        if (guiButton.id == -1) {
            String text = "MC|Beacon";
            PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeInt(this.tileBeacon.getField(1));
            packetBuffer.writeInt(this.tileBeacon.getField(2));
            this.mc
                    .getNetworkHandler()
                    .addToSendQueue(new C17PacketCustomPayload(text, packetBuffer));
            this.mc.displayGuiScreen((GuiScreen) null);
            if (aint == null) {
                return;
            }
        }

        if (guiButton instanceof GuiBeaconPowerButton) {
            if (((GuiBeaconPowerButton) guiButton).method_b417d7500ec59d69e373()) {
                return;
            }

            {
                int j = guiButton.id;
                int k = j & 0xFF;
                int i = j >> 8;
                if (i < 3) {
                    this.tileBeacon.setField(1, k);
                    if (!(aint == null)) {

                        this.tileBeacon.setField(2, k);
                    }
                } else {

                    this.tileBeacon.setField(2, k);
                }
            }

            this.buttonList.clear();
            this.initGui();
            this.updateScreen();
        }
    }

    public void updateScreen() {
        super.updateScreen();
        int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
        int field = this.tileBeacon.getField(0);
        int j = this.tileBeacon.getField(1);
        int[] aint = aint1;
        int k = this.tileBeacon.getField(2);
        if (this.buttonsNotDrawn && field >= 0) {
            this.buttonsNotDrawn = false;
            int l1 = 0;

            while (l1 <= 2) {
                int l = TileEntityBeacon.effectsList[l1].length;
                int i1 = l * 22 + (l - 1) * 2;
                int j1 = 0;

                while (j1 < l) {
                    {
                        int k1 = TileEntityBeacon.effectsList[l1][j1].id;
                        GuiBeaconPowerButton guiBeaconPowerButton =
                                new GuiBeaconPowerButton(
                                        this,
                                        l1 << 8 | k1,
                                        this.guiLeft + 76 + j1 * 24 - i1 / 2,
                                        this.guiTop + 22 + l1 * 25,
                                        k1,
                                        l1);
                        this.buttonList.add(guiBeaconPowerButton);
                        if (l1 >= field) {
                            guiBeaconPowerButton.enabled = false;
                            if (!(aint == null)) {

                                if (k1 == j) {
                                    guiBeaconPowerButton.method_5a0449839d28b4a71843(true);
                                }
                            }
                        } else {

                            if (k1 == j) {
                                guiBeaconPowerButton.method_5a0449839d28b4a71843(true);
                            }
                        }
                    }

                    j1++;
                    if (aint != null) {
                        break;
                    }
                }

                l1++;
                if (aint != null) {
                    break;
                }
            }

            byte b0 = 3;
            int i2 = TileEntityBeacon.effectsList[b0].length + 1;
            int j2 = i2 * 22 + (i2 - 1) * 2;
            int k2 = 0;

            while (k2 < i2 - 1) {
                {
                    int l2 = TileEntityBeacon.effectsList[b0][k2].id;
                    GuiBeaconPowerButton guiBeaconPowerButtonValue =
                            new GuiBeaconPowerButton(
                                    this,
                                    b0 << 8 | l2,
                                    this.guiLeft + 167 + k2 * 24 - j2 / 2,
                                    this.guiTop + 47,
                                    l2,
                                    b0);
                    this.buttonList.add(guiBeaconPowerButtonValue);
                    if (b0 >= field) {
                        guiBeaconPowerButtonValue.enabled = false;
                        if (!(aint == null)) {

                            if (l2 == k) {
                                guiBeaconPowerButtonValue.method_5a0449839d28b4a71843(true);
                            }
                        }
                    } else {

                        if (l2 == k) {
                            guiBeaconPowerButtonValue.method_5a0449839d28b4a71843(true);
                        }
                    }
                }

                k2++;
                if (aint != null) {
                    break;
                }
            }

            if (j > 0) {
                GuiBeaconPowerButton guiBeaconPowerButtonValue2 =
                        new GuiBeaconPowerButton(
                                this,
                                b0 << 8 | j,
                                this.guiLeft + 167 + (i2 - 1) * 24 - j2 / 2,
                                this.guiTop + 47,
                                j,
                                b0);
                this.buttonList.add(guiBeaconPowerButtonValue2);
                if (b0 >= field) {
                    guiBeaconPowerButtonValue2.enabled = false;
                    if (!(aint == null)) {

                        if (j == k) {
                            guiBeaconPowerButtonValue2.method_5a0449839d28b4a71843(true);
                        }
                    }
                } else {

                    if (j == k) {
                        guiBeaconPowerButtonValue2.method_5a0449839d28b4a71843(true);
                    }
                }
            }
        }

        this.beaconConfirmButton.enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.tileBeacon.getStackInSlot(0) != null && j > 0 ? 1 : 0));
    }

    public GuiBeacon(InventoryPlayer inventoryPlayer, IInventory atMValue) {
        super(new ContainerBeacon(inventoryPlayer, atMValue));
        this.tileBeacon = atMValue;
        this.xSize = 230;
        this.ySize = 219;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int k, int l) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc
                .getTextureManager()
                .bindTexture(net.minecraft.client.gui.inventory.GuiBeacon.beaconGuiTextures);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        this.itemRender.zLevel = 100.0F;
        this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.emerald), i + 42, j + 109);
        this.itemRender.renderItemAndEffectIntoGUI(
                new ItemStack(Items.diamond), i + 42 + 22, j + 109);
        this.itemRender.renderItemAndEffectIntoGUI(
                new ItemStack(Items.gold_ingot), i + 42 + 44, j + 109);
        this.itemRender.renderItemAndEffectIntoGUI(
                new ItemStack(Items.iron_ingot), i + 42 + 66, j + 109);
        this.itemRender.zLevel = 0.0F;
    }

    static void method_8bb211027a591da84ca5(GuiBeacon guiBeacon, String text, int i, int j) {
        guiBeacon.drawCreativeTabHoveringText(text, i, j);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    static {
        net.minecraft.client.gui.inventory.GuiBeacon.logger = LogManager.getLogger();
        net.minecraft.client.gui.inventory.GuiBeacon.beaconGuiTextures =
                new ResourceLocation("textures/gui/container/beacon.png");
    }

    protected void drawGuiContainerForegroundLayer(int i, int j) {
        int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
        RenderHelper.disableStandardItemLighting();
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("tile.beacon.primary", new Object[0]),
                62,
                10,
                14737632);
        int[] aint = aint1;
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("tile.beacon.secondary", new Object[0]),
                169,
                10,
                14737632);

        for (GuiButton guiButton : this.buttonList) {
            if (guiButton.isMouseOver()) {
                guiButton.drawButtonForegroundLayer(i - this.guiLeft, j - this.guiTop);
                if (aint == null) {
                    break;
                }
            }

            if (aint != null) {
                break;
            }
        }

        RenderHelper.enableGUIStandardItemLighting();
    }

    static void method_2b4ff8d0b3c834b025bc(GuiBeacon guiBeacon, String text, int i, int j) {
        guiBeacon.drawCreativeTabHoveringText(text, i, j);
    }

    static void method_dd4129e37ab6b15f1271(GuiBeacon guiBeacon, String text, int i, int j) {
        guiBeacon.drawCreativeTabHoveringText(text, i, j);
    }
}
