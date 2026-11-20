package net.minecraft.client.gui;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatusEnumState;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

public class GuiWinGame extends GuiScreen {
    private int yK;
    private static ResourceLocation MINECRAFT_LOGO;
    private float asV = 0.5F;

    private static Logger logger;
    private static ResourceLocation VIGNETTE_TEXTURE;
    private int B;

    private static String[] packedTextFragments;
    private List rn;

    public boolean doesGuiPauseGame() {
        return true;
    }

    protected void keyTyped(char c0, int i) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (i == 1) {
                this.sendRespawnPacket();
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    private void sendRespawnPacket() {
        this.mc.player.connection.addToSendQueue(
                new C16PacketClientStatus(C16PacketClientStatusEnumState.PERFORM_RESPAWN));
        this.mc.displayGuiScreen((GuiScreen) null);
    }

    static {
        net.minecraft.client.gui.GuiWinGame.packedTextFragments = new String[8];
        net.minecraft.client.gui.GuiWinGame.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiWinGame.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.GuiWinGame.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.GuiWinGame.packedTextFragments[3] = new String("");
        net.minecraft.client.gui.GuiWinGame.packedTextFragments[4] = new String("\t");
        net.minecraft.client.gui.GuiWinGame.packedTextFragments[5] = new String("");
        net.minecraft.client.gui.GuiWinGame.logger = LogManager.getLogger();
        net.minecraft.client.gui.GuiWinGame.MINECRAFT_LOGO =
                new ResourceLocation("textures/gui/title/minecraft.png");
        net.minecraft.client.gui.GuiWinGame.VIGNETTE_TEXTURE =
                new ResourceLocation("textures/misc/vignette.png");
    }

    public void updateScreen() {
        this.yK++;
        float f = (this.B + this.height + this.height + 24) / this.asV;
        if (this.yK > f) {
            this.sendRespawnPacket();
        }
    }

    public void initGui() {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.rn == null) {
            this.rn = Lists.newArrayList();

            try {
                String text = packedTextFragments[0];
                String textValue =
                        packedTextFragments[1]
                                + EnumChatFormatting.WHITE
                                + EnumChatFormatting.OBFUSCATED
                                + EnumChatFormatting.GREEN
                                + EnumChatFormatting.AQUA;
                short short1 = 274;
                BufferedReader bufferedreader =
                        new BufferedReader(
                                new InputStreamReader(
                                        this.mc
                                                .getResourceManager()
                                                .getResource(new ResourceLocation("texts/end.txt"))
                                                .getInputStream(),
                                        Charsets.UTF_8));

                Random random = new Random(8124371L);

                while ((text = bufferedreader.readLine()) != null) {
                    text = text.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());

                    while (text.contains(textValue)) {
                        int i = text.indexOf(textValue);
                        Object object = text.substring(0, i);
                        String textValue2 = text.substring(i + textValue.length());
                        text =
                                (String) object
                                        + EnumChatFormatting.WHITE
                                        + EnumChatFormatting.OBFUSCATED
                                        + "XXXXXXXX".substring(0, random.nextInt(4) + 3)
                                        + textValue2;
                        if (astring != null) {
                            break;
                        }
                    }

                    this.rn.addAll(
                            this.mc.fontRendererObj.listFormattedStringToWidth(text, short1));
                    this.rn.add(packedTextFragments[2]);
                    if (astring != null) {
                        break;
                    }
                }

                int j = 0;

                while (j < 8) {
                    this.rn.add(packedTextFragments[3]);
                    j++;
                    if (astring != null) {
                        break;
                    }
                }

                bufferedreader =
                        new BufferedReader(
                                new InputStreamReader(
                                        this.mc
                                                .getResourceManager()
                                                .getResource(
                                                        new ResourceLocation("texts/credits.txt"))
                                                .getInputStream(),
                                        Charsets.UTF_8));

                while ((text = bufferedreader.readLine()) != null) {
                    text = text.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());
                    text = text.replaceAll(packedTextFragments[4], "    ");
                    this.rn.addAll(
                            this.mc.fontRendererObj.listFormattedStringToWidth(text, short1));
                    this.rn.add(packedTextFragments[5]);
                    if (astring != null) {
                        break;
                    }
                }

                this.B = this.rn.size() * 12;
            } catch (Exception exception) {
                net.minecraft.client.gui.GuiWinGame.logger.error(
                        "Couldn't load credits", exception);
            }
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void drawScreen(int i, int j, float f) {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.drawWinGameScreen(i, j, f);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        short short1 = 274;
        int k = this.width / 2 - short1 / 2;
        int l = this.height + 50;
        float f1 = -(this.yK + f) * this.asV;
        GlStateManager.pushMatrix();
        String[] astring = astring1;
        GlStateManager.translate(0.0F, f1, 0.0F);
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.GuiWinGame.MINECRAFT_LOGO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(k, l, 0, 0, 155, 44);
        this.drawTexturedModalRect(k + 155, l, 0, 45, 155, 44);
        worldRenderer.setOpaqueVertexColorFromRgb(16777215);
        int i1 = l + 200;
        int j1 = 0;

        while (j1 < this.rn.size()) {
            if (j1 == this.rn.size() - 1) {
                float f2 = i1 + f1 - (this.height / 2 - 6);
                if (f2 < 0.0F) {
                    GlStateManager.translate(0.0F, -f2, 0.0F);
                }
            }

            if (i1 + f1 + 12.0F + 8.0F > 0.0F && i1 + f1 < this.height) {
                String text = (String) this.rn.get(j1);
                if (text.startsWith("[C]")) {
                    this.fontRendererObj.drawStringWithShadow(
                            text.substring(3),
                            k
                                    + (short1
                                                    - this.fontRendererObj.getStringWidth(
                                                            text.substring(3)))
                                            / 2,
                            i1,
                            16777215);
                    if (!(astring == null)) {

                        long l1 = j1;

                        this.fontRendererObj.fontRandom.setSeed(l1 * 4238972211L + this.yK / 4);
                        this.fontRendererObj.drawStringWithShadow(text, k, i1, 16777215);
                    }
                } else {

                    long l1 = j1;

                    this.fontRendererObj.fontRandom.setSeed(l1 * 4238972211L + this.yK / 4);
                    this.fontRendererObj.drawStringWithShadow(text, k, i1, 16777215);
                }
            }

            i1 += 12;
            j1++;
            if (astring != null) {
                break;
            }
        }

        GlStateManager.popMatrix();
        this.mc
                .getTextureManager()
                .bindTexture(net.minecraft.client.gui.GuiWinGame.VIGNETTE_TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(0, 769);
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexColorFromFloats(1.0F, 1.0F, 1.0F, 1.0F);
        j1 = this.width;
        int k1 = this.height;
        worldRenderer.addVertexWithUV(0.0, k1, this.zLevel, 0.0, 1.0);
        worldRenderer.addVertexWithUV(j1, k1, this.zLevel, 1.0, 1.0);
        worldRenderer.addVertexWithUV(j1, 0.0, this.zLevel, 1.0, 0.0);
        worldRenderer.addVertexWithUV(0.0, 0.0, this.zLevel, 0.0, 0.0);
        instance.draw();
        GlStateManager.disableBlend();
        super.drawScreen(i, j, f);
    }

    private void drawWinGameScreen(int j, int k, float f) {
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexColorFromFloats(1.0F, 1.0F, 1.0F, 1.0F);
        int widthValue = this.width;
        float f1 = 0.0F - (this.yK + f) * 0.5F * this.asV;
        float f2 = this.height - (this.yK + f) * 0.5F * this.asV;
        float f3 = 0.015625F;
        float f4 = (this.yK + f - 0.0F) * 0.02F;
        float f5 = (this.B + this.height + this.height + 24) / this.asV;
        float f6 = (f5 - 20.0F - (this.yK + f)) * 0.005F;
        if (f6 < f4) {
            f4 = f6;
        }

        if (f4 > 1.0F) {
            f4 = 1.0F;
        }

        f4 *= f4;
        f4 = f4 * 96.0F / 255.0F;
        worldRenderer.setOpaqueVertexColorFromFloats(f4, f4, f4);
        worldRenderer.addVertexWithUV(0.0, this.height, this.zLevel, 0.0, f1 * f3);
        worldRenderer.addVertexWithUV(
                widthValue, this.height, this.zLevel, widthValue * f3, f1 * f3);
        worldRenderer.addVertexWithUV(widthValue, 0.0, this.zLevel, widthValue * f3, f2 * f3);
        worldRenderer.addVertexWithUV(0.0, 0.0, this.zLevel, 0.0, f2 * f3);
        instance.draw();
    }
}
