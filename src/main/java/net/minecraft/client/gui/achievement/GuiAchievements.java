package net.minecraft.client.gui.achievement;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatusEnumState;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.Random;

public class GuiAchievements extends GuiScreen implements IProgressMeter {
    private static int x;
    protected double ajc;
    private static ResourceLocation ACHIEVEMENT_BACKGROUND;
    protected int xZ;
    protected GuiScreen parentScreen;
    private static int w;

    protected float atb;
    protected double lo;
    private boolean loadingAchievements;

    private int lF;

    protected int C = 256;
    protected double jY;
    protected int E;
    private StatFileWriter statFileWriter;
    private static int yK;
    protected int D = 202;
    protected double ate;
    protected double aji;
    private static int y;
    protected double oY;

    protected void drawAchievementScreen(int i, int j, float f) {
        int k = MathHelper.floor(this.lo + (this.oY - this.lo) * f);
        Entity[] entityArray = ResourceLocation.cB();
        int l = MathHelper.floor(this.aji + (this.ajc - this.aji) * f);
        Entity[] entityArrayValue = entityArray;
        if (k < w) {
            k = w;
        }

        if (l < x) {
            l = x;
        }

        if (k >= y) {
            k = y - 1;
        }

        if (l >= yK) {
            l = yK - 1;
        }

        int i1 = (this.width - this.C) / 2;
        int j1 = (this.height - this.D) / 2;
        int k1 = i1 + 16;
        int l1 = j1 + 17;
        this.zLevel = 0.0F;
        GlStateManager.depthFunc(518);
        GlStateManager.pushMatrix();
        GlStateManager.translate(k1, l1, -200.0F);
        GlStateManager.scale(1.0F / this.atb, 1.0F / this.atb, 0.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        int i2 = k + 288 >> 4;
        int j2 = l + 288 >> 4;
        int k2 = (k + 288) % 16;
        int l2 = (l + 288) % 16;

        Random random = new Random();
        float f1 = 16.0F / this.atb;
        float f2 = 16.0F / this.atb;
        int i3 = 0;

        while (i3 * f1 - l2 < 155.0F) {
            float f3 = 0.6F - (j2 + i3) / 25.0F * 0.3F;
            GlStateManager.color(f3, f3, f3, 1.0F);
            int j3 = 0;

            while (true) {
                if (j3 * f2 - k2 < 224.0F) {
                    TextureAtlasSprite textureAtlasSprite;
                    {
                        random.setSeed(
                                this.mc.getSession().getPlayerID().hashCode()
                                        + i2
                                        + j3
                                        + (j2 + i3) * 16);
                        int k3 = random.nextInt(1 + j2 + i3) + (j2 + i3) / 2;
                        textureAtlasSprite = this.method_8c2a085b29b5a8f0f07d(Blocks.sand);
                        if (k3 <= 37 && j2 + i3 != 35) {
                            if (k3 == 22) {
                                if (random.nextInt(2) == 0) {
                                    textureAtlasSprite =
                                            this.method_8c2a085b29b5a8f0f07d(Blocks.diamond_ore);

                                    
                                } else {

                                    textureAtlasSprite =
                                            this.method_8c2a085b29b5a8f0f07d(Blocks.redstone_ore);


                                }
                            } else {

                                if (k3 == 10) {
                                    textureAtlasSprite =
                                            this.method_8c2a085b29b5a8f0f07d(Blocks.iron_ore);


                                } else {

                                    if (k3 == 8) {
                                        textureAtlasSprite =
                                                this.method_8c2a085b29b5a8f0f07d(Blocks.coal_ore);


                                    } else {

                                        if (k3 > 4) {
                                            textureAtlasSprite = this.method_8c2a085b29b5a8f0f07d(Blocks.stone);


                                        } else {

                                            if (!(k3 <= 0)) {

                                                textureAtlasSprite = this.method_8c2a085b29b5a8f0f07d(Blocks.dirt);


                                            }
                                        }
                                    }
                                }
                            }
                        } else {

                            Block blA = Blocks.bedrock;
                            textureAtlasSprite = this.method_8c2a085b29b5a8f0f07d(blA);
                        }
                    }

                    this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                    this.drawTexturedModalRect(
                            j3 * 16 - k2, i3 * 16 - l2, textureAtlasSprite, 16, 16);
                    j3++;

                    continue;
                }

                i3++;

                break;
            }
        }

        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.achievement.GuiAchievements.ACHIEVEMENT_BACKGROUND);
        i3 = 0;

        while (i3 < AchievementList.achievementList.size()) {
            Achievement achievement = (Achievement) AchievementList.achievementList.get(i3);
            if (achievement.parentAchievement != null) {
                int k4 = achievement.displayColumn * 24 - k + 11;
                int l4 = achievement.displayRow * 24 - l + 11;
                int i4 = achievement.parentAchievement.displayColumn * 24 - k + 11;
                int l3 = achievement.parentAchievement.displayRow * 24 - l + 11;
                boolean hasAchievementUnlocked =
                        this.statFileWriter.hasAchievementUnlocked(achievement);
                boolean flag1 = this.statFileWriter.canUnlockAchievement(achievement);
                int j5 = this.statFileWriter.method_177bf7e342bbdacda4b7(achievement);
                if (j5 <= 4) {
                    int j6;
                    {
                        j6 = -16777216;
                        if (hasAchievementUnlocked) {
                            j6 = -6250336;

                            
                        } else {

                            if (flag1) {
                                j6 = -16711936;
                            }
                        }
                    }

                    this.method_f418b0f77f1897435d21(k4, i4, l4, j6);
                    this.method_366505046587ede28c94(i4, l4, l3, j6);
                    if (k4 > i4) {
                        this.drawTexturedModalRect(k4 - 11 - 7, l4 - 5, 114, 234, 7, 11);

                        
                    } else {

                        if (k4 < i4) {
                            this.drawTexturedModalRect(k4 + 11, l4 - 5, 107, 234, 7, 11);


                        } else {

                            if (l4 > l3) {
                                this.drawTexturedModalRect(k4 - 5, l4 - 11 - 7, 96, 234, 11, 7);


                            } else {

                                if (l4 < l3) {
                                    this.drawTexturedModalRect(k4 - 5, l4 + 11, 96, 241, 11, 7);
                                }
                            }
                        }
                    }
                }
            }

            i3++;
        }

        Achievement achievementValue = null;
        float f5 = (i - k1) * this.atb;
        float f6 = (j - l1) * this.atb;
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        int i5 = 0;

        while (i5 < AchievementList.achievementList.size()) {
            Achievement achievementValue2 = (Achievement) AchievementList.achievementList.get(i5);
            int l6 = achievementValue2.displayColumn * 24 - k;
            int j7 = achievementValue2.displayRow * 24 - l;
            label174:
            if (l6 >= -24 && j7 >= -24 && l6 <= 224.0F * this.atb && j7 <= 155.0F * this.atb) {
                {
                    int l7 = this.statFileWriter.method_177bf7e342bbdacda4b7(achievementValue2);
                    if (this.statFileWriter.hasAchievementUnlocked(achievementValue2)) {
                        float f4 = 0.75F;
                        GlStateManager.color(f4, f4, f4, 1.0F);

                        
                    } else {

                        if (this.statFileWriter.canUnlockAchievement(achievementValue2)) {
                            float f7 = 1.0F;
                            GlStateManager.color(f7, f7, f7, 1.0F);


                        } else {

                            if (l7 < 3) {
                                float f8 = 0.3F;
                                GlStateManager.color(f8, f8, f8, 1.0F);


                            } else {

                                if (l7 == 3) {
                                    float f9 = 0.2F;
                                    GlStateManager.color(f9, f9, f9, 1.0F);


                                } else {

                                    if (l7 != 4 && entityArrayValue != null) {
                                        break label174;
                                    }

                                    float f10 = 0.1F;
                                    GlStateManager.color(f10, f10, f10, 1.0F);
                                }
                            }
                        }
                    }
                }

                {
                    this.mc
                            .getTextureManager()
                            .bindTexture(net.minecraft.client.gui.achievement.GuiAchievements.ACHIEVEMENT_BACKGROUND);
                    if (achievementValue2.getSpecial()) {
                        this.drawTexturedModalRect(l6 - 2, j7 - 2, 26, 202, 26, 26);

                        
                    } else {

                        this.drawTexturedModalRect(l6 - 2, j7 - 2, 0, 202, 26, 26);
                    }
                }

                if (!this.statFileWriter.canUnlockAchievement(achievementValue2)) {
                    float f11 = 0.1F;
                    GlStateManager.color(f11, f11, f11, 1.0F);
                    this.itemRender.func_175039_a(false);
                }

                GlStateManager.enableLighting();
                GlStateManager.enableCull();
                this.itemRender.renderItemAndEffectIntoGUI(
                        achievementValue2.theItemStack, l6 + 3, j7 + 3);
                GlStateManager.blendFunc(770, 771);
                GlStateManager.disableLighting();
                if (!this.statFileWriter.canUnlockAchievement(achievementValue2)) {
                    this.itemRender.func_175039_a(true);
                }

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                if (f5 >= l6 && f5 <= l6 + 22 && f6 >= j7 && f6 <= j7 + 22) {
                    achievementValue = achievementValue2;
                }
            }

            i5++;
        }

        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.achievement.GuiAchievements.ACHIEVEMENT_BACKGROUND);
        this.drawTexturedModalRect(i1, j1, 0, 0, this.C, this.D);
        this.zLevel = 0.0F;
        GlStateManager.depthFunc(515);
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        super.drawScreen(i, j, f);
        if (achievementValue != null) {
            int i7;
            int k7;
            String text;
            {
                text = achievementValue.getStatName().getUnformattedText();
                String textValue = achievementValue.getDescription();
                i7 = i + 12;
                k7 = j - 4;
                int i8 = this.statFileWriter.method_177bf7e342bbdacda4b7(achievementValue);
                if (this.statFileWriter.canUnlockAchievement(achievementValue)) {
                    int k5 = Math.max(this.fontRendererObj.getStringWidth(text), 120);
                    int k6 = this.fontRendererObj.splitStringWidth(textValue, k5);
                    if (this.statFileWriter.hasAchievementUnlocked(achievementValue)) {
                        k6 += 12;
                    }

                    this.drawGradientRect(
                            i7 - 3,
                            k7 - 3,
                            i7 + k5 + 3,
                            k7 + k6 + 3 + 12,
                            -1073741824,
                            -1073741824);
                    this.fontRendererObj.drawSplitString(textValue, i7, k7 + 12, k5, -6250336);
                    if (!(!this.statFileWriter.hasAchievementUnlocked(achievementValue))) {

                        this.fontRendererObj.drawStringWithShadow(
                                I18n.format("achievement.taken", new Object[0]),
                                i7,
                                k7 + k6 + 4,
                                -7302913);


                    }
                } else {

                    if (i8 == 3) {
                        text = I18n.format("achievement.unknown", new Object[0]);
                        int l5 = Math.max(this.fontRendererObj.getStringWidth(text), 120);
                        String textValue2 =
                                new ChatComponentTranslation(
                                                "achievement.requires",
                                                new Object[] {
                                                    achievementValue.parentAchievement.getStatName()
                                                })
                                        .getUnformattedText();
                        int j4 = this.fontRendererObj.splitStringWidth(textValue2, l5);
                        this.drawGradientRect(
                                i7 - 3,
                                k7 - 3,
                                i7 + l5 + 3,
                                k7 + j4 + 12 + 3,
                                -1073741824,
                                -1073741824);
                        this.fontRendererObj.drawSplitString(textValue2, i7, k7 + 12, l5, -9416624);


                    } else {

                        if (i8 < 3) {
                            int i6 = Math.max(this.fontRendererObj.getStringWidth(text), 120);
                            String textValue3 =
                                    new ChatComponentTranslation(
                                                    "achievement.requires",
                                                    new Object[] {
                                                        achievementValue.parentAchievement.getStatName()
                                                    })
                                            .getUnformattedText();
                            int j8 = this.fontRendererObj.splitStringWidth(textValue3, i6);
                            this.drawGradientRect(
                                    i7 - 3,
                                    k7 - 3,
                                    i7 + i6 + 3,
                                    k7 + j8 + 12 + 3,
                                    -1073741824,
                                    -1073741824);
                            this.fontRendererObj.drawSplitString(textValue3, i7, k7 + 12, i6, -9416624);


                        } else {

                            text = null;
                        }
                    }
                }
            }

            if (text != null) {
                this.fontRendererObj.drawStringWithShadow(
                        text,
                        i7,
                        k7,
                        this.statFileWriter.canUnlockAchievement(achievementValue)
                                ? (achievementValue.getSpecial() ? -128 : -1)
                                : (achievementValue.getSpecial() ? -8355776 : -8355712));
            }
        }

        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        RenderHelper.disableStandardItemLighting();
    }

    protected void drawTitle() {
        int i = (this.width - this.C) / 2;
        int j = (this.height - this.D) / 2;
        this.fontRendererObj.drawString(
                I18n.format("gui.achievements", new Object[0]), i + 15, j + 5, 4210752);
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (!this.loadingAchievements && guiButton.id == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    private TextureAtlasSprite method_8c2a085b29b5a8f0f07d(Block block) {
        return MinecraftClient.getMinecraft()
                .getBlockRendererDispatcher()
                .getBlockModelShapes()
                .getTexture(block.getDefaultState());
    }

    public void initGui() {
        this.mc
                .getNetworkHandler()
                .addToSendQueue(
                        new C16PacketClientStatus(C16PacketClientStatusEnumState.REQUEST_STATS));
        this.buttonList.clear();
        this.buttonList.add(
                new GuiOptionButton(
                        1,
                        this.width / 2 + 24,
                        this.height / 2 + 74,
                        80,
                        20,
                        I18n.format("gui.done", new Object[0])));
    }

    public void drawScreen(int i, int j, float f) {
        ResourceLocation.cB();

        if (this.loadingAchievements) {
            this.drawDefaultBackground();
            this.drawCenteredString(
                    this.fontRendererObj,
                    I18n.format("multiplayer.downloadingStats", new Object[0]),
                    this.width / 2,
                    this.height / 2,
                    16777215);
            FontRenderer apkValue = this.fontRendererObj;
            String[] astring = net.minecraft.client.gui.IProgressMeter.lanSearchStates;
            long l1 = MinecraftClient.getSystemTime();

            this.drawCenteredString(
                    apkValue,
                    astring[(int) (l1 / 150L % net.minecraft.client.gui.IProgressMeter.lanSearchStates.length)],
                    this.width / 2,
                    this.height / 2 + this.fontRendererObj.FONT_HEIGHT * 2,
                    16777215);

            return;
        }

        {
            if (Mouse.isButtonDown(0)) {
                int k = (this.width - this.C) / 2;
                int l = (this.height - this.D) / 2;
                int i1 = k + 8;
                int j1 = l + 17;
                if ((this.lF == 0 || this.lF == 1)
                        && i >= i1
                        && i < i1 + 224
                        && j >= j1
                        && j < j1 + 155) {
                    {
                        if (this.lF == 0) {
                            this.lF = 1;

                            
                        } else {

                            this.oY = this.oY - (i - this.E) * this.atb;
                            this.ajc = this.ajc - (j - this.xZ) * this.atb;
                            this.jY = this.lo = this.oY;
                            this.ate = this.aji = this.ajc;
                        }
                    }

                    this.E = i;
                    this.xZ = j;
                }

                
            } else {

                this.lF = 0;
            }
        }

        float f4;
        {
            int k1 = Mouse.getDWheel();
            f4 = this.atb;
            if (k1 < 0) {
                this.atb += 0.25F;

                
            } else {

                if (k1 > 0) {
                    this.atb -= 0.25F;
                }
            }
        }

        this.atb = MathHelper.clamp(this.atb, 1.0F, 2.0F);
        if (this.atb != f4) {
            float f5 = f4 - this.atb;
            float f6 = f4 * this.C;
            float f1 = f4 * this.D;
            float f2 = this.atb * this.C;
            float f3 = this.atb * this.D;
            this.oY -= (f2 - f6) * 0.5F;
            this.ajc -= (f3 - f1) * 0.5F;
            this.jY = this.lo = this.oY;
            this.ate = this.aji = this.ajc;
        }

        if (this.jY < w) {
            this.jY = w;
        }

        if (this.ate < x) {
            this.ate = x;
        }

        if (this.jY >= y) {
            this.jY = y - 1;
        }

        if (this.ate >= yK) {
            this.ate = yK - 1;
        }

        this.drawDefaultBackground();
        this.drawAchievementScreen(i, j, f);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        this.drawTitle();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    public void doneLoading() {
        if (this.loadingAchievements) {
            this.loadingAchievements = false;
        }
    }

    public GuiAchievements(GuiScreen guiScreen, StatFileWriter atdValue) {
        this.atb = 1.0F;
        this.loadingAchievements = true;
        this.parentScreen = guiScreen;
        this.statFileWriter = atdValue;
        short short1 = 141;
        short short2 = 141;
        this.lo =
                this.oY =
                        this.jY =
                                AchievementList.openInventory.displayColumn * 24 - short1 / 2 - 12;
        this.aji = this.ajc = this.ate = AchievementList.openInventory.displayRow * 24 - short2 / 2;
    }

    public boolean doesGuiPauseGame() {
        return !this.loadingAchievements;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void updateScreen() {
        ResourceLocation.cB();

        if (!this.loadingAchievements) {
            this.lo = this.oY;
            this.aji = this.ajc;
            double d0 = this.jY - this.oY;
            double d1 = this.ate - this.ajc;
            if (d0 * d0 + d1 * d1 < 4.0) {
                this.oY += d0;
                this.ajc += d1;

                return;
            }

            this.oY += d0 * 0.85;
            this.ajc += d1 * 0.85;
        }
    }

    protected void keyTyped(char c0, int i) throws IOException {
        ResourceLocation.cB();

        if (i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.displayGuiScreen((GuiScreen) null);
            this.mc.setIngameFocus();

            return;
        }

        super.keyTyped(c0, i);
    }

    static {
        w = AchievementList.minDisplayColumn * 24 - 112;
        x = AchievementList.minDisplayRow * 24 - 112;
        y = AchievementList.maxDisplayColumn * 24 - 77;
        yK = AchievementList.maxDisplayRow * 24 - 77;
        net.minecraft.client.gui.achievement.GuiAchievements.ACHIEVEMENT_BACKGROUND =
                new ResourceLocation("textures/gui/achievement/achievement_background.png");
    }
}
