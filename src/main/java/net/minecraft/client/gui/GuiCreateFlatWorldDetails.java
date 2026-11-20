package net.minecraft.client.gui;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.FlatLayerInfo;

class GuiCreateFlatWorldDetails extends GuiSlot {

    GuiCreateFlatWorld arp;

    public int x;

    private void method_09907449d9597b32aae9(int i, int j, ItemStack itemStack) {
        this.method_6b802a8dba45a02bfb5a(i + 1, j + 1);
        GlStateManager.enableRescaleNormal();
        if (itemStack != null && itemStack.getItem() != null) {
            RenderHelper.enableGUIStandardItemLighting();
            this.arp.itemRender.renderItemModelIntoGUI(itemStack, i + 2, j + 2);
            RenderHelper.disableStandardItemLighting();
        }

        GlStateManager.disableRescaleNormal();
    }

    protected int getScrollBarX() {
        return this.width - 70;
    }

    private void method_ef7b4c24eda6910a0350(int i, int j, int k, int l) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Gui.statIcons);

        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(
                i + 0, j + 18, this.arp.zLevel, (k + 0) * 0.0078125F, (l + 18) * 0.0078125F);
        worldRenderer.addVertexWithUV(
                i + 18, j + 18, this.arp.zLevel, (k + 18) * 0.0078125F, (l + 18) * 0.0078125F);
        worldRenderer.addVertexWithUV(
                i + 18, j + 0, this.arp.zLevel, (k + 18) * 0.0078125F, (l + 0) * 0.0078125F);
        worldRenderer.addVertexWithUV(
                i + 0, j + 0, this.arp.zLevel, (k + 0) * 0.0078125F, (l + 0) * 0.0078125F);
        instance.draw();
    }

    protected void drawBackground() {}

    protected void elementClicked(int i, boolean flag, int j, int k) {
        this.x = i;
        this.arp.ha();
    }

    protected int getSize() {
        return GuiCreateFlatWorld.access$400(this.arp).getFlatLayers().size();
    }

    protected void drawSlot(int i, int j, int k, int l, int i1, int j1) {
        FlatLayerInfo flatLayerInfo =
                (FlatLayerInfo)
                        GuiCreateFlatWorld.access$400(this.arp)
                                .getFlatLayers()
                                .get(
                                        GuiCreateFlatWorld.access$400(this.arp)
                                                        .getFlatLayers()
                                                        .size()
                                                - i
                                                - 1);
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        BlockState blockState = flatLayerInfo.ID();
        String[] astring = astring1;
        Block block = blockState.getBlock();
        Item item = Item.getItemFromBlock(block);
        ItemStack itemStack =
                block != Blocks.air && item != null
                        ? new ItemStack(item, 1, block.getMetaFromState(blockState))
                        : null;
        String text = itemStack == null ? "Air" : item.getItemStackDisplayName(itemStack);
        if (item == null) {
            label44:
            {
                if (block != Blocks.water && block != Blocks.flowing_water) {
                    if (block != Blocks.lava && block != Blocks.flowing_lava) {
                        break label44;
                    }

                    item = Items.lava_bucket;
                    if (astring == null) {
                        break label44;
                    }
                }

                item = Items.water_bucket;
            }

            if (item != null) {
                itemStack = new ItemStack(item, 1, block.getMetaFromState(blockState));
                text = block.getLocalizedName();
            }
        }

        String textValue;
        label55:
        {
            this.method_09907449d9597b32aae9(j, k, itemStack);
            this.arp.fontRendererObj.drawString(text, j + 18 + 5, k + 3, 16777215);
            if (i == 0) {
                textValue =
                        I18n.format(
                                "createWorld.customize.flat.layer.top",
                                new Object[] {flatLayerInfo.getLayerCount()});
                if (astring == null) {
                    break label55;
                }
            }

            if (i == GuiCreateFlatWorld.access$400(this.arp).getFlatLayers().size() - 1) {
                textValue =
                        I18n.format(
                                "createWorld.customize.flat.layer.bottom",
                                new Object[] {flatLayerInfo.getLayerCount()});
                if (astring == null) {
                    break label55;
                }
            }

            textValue =
                    I18n.format(
                            "createWorld.customize.flat.layer",
                            new Object[] {flatLayerInfo.getLayerCount()});
        }

        this.arp.fontRendererObj.drawString(
                textValue,
                j + 2 + 213 - this.arp.fontRendererObj.getStringWidth(textValue),
                k + 3,
                16777215);
    }

    private void method_6b802a8dba45a02bfb5a(int i, int j) {
        this.method_ef7b4c24eda6910a0350(i, j, 0, 0);
    }

    public GuiCreateFlatWorldDetails(GuiCreateFlatWorld arpValue) {
        super(arpValue.mc, arpValue.width, arpValue.height, 43, arpValue.height - 60, 24);
        this.arp = arpValue;
        this.x = -1;
    }


    protected boolean isSelected(int i) {
        return i == this.x;
    }
}
