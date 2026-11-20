package net.minecraft.client.gui;

import net.minecraft.block.material.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayHitType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.border.WorldBorder;
import optifine.CustomColors;
import optifine.OptiFineConfig;
import net.minecraft.client.gui.GuiIngameRenderScoreboardPredicate;
import remix.event.impl.Render2DEvent;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.hidden.MiniWitherStatusModule;
import remix.module.render.CameraModule;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class GuiIngame extends Gui {
    private int titleStayTicks;
    public float prevVignetteBrightness;
    private String titleText;
    private final Random rand = new Random();
    private int recordPlayingUpFor;
    private boolean recordIsPlaying;

    private static ResourceLocation vignetteTexPath;
    private GuiStreamIndicator streamIndicator;
    private String subtitleText;
    private long lastSystemTime;
    private int remainingHighlightTicks;
    private int titleFadeOutTicks;
    private MinecraftClient mc;
    private static ResourceLocation widgetsTexPath;
    private int updateCounter;
    private GuiOverlayDebug overlayDebug;
    private int lastPlayerHealth;
    private GuiPlayerTabOverlay overlayPlayerList;
    private String recordPlaying;
    private static ResourceLocation pumpkinBlurTexPath;

    private RenderItem itemRenderer;
    private ItemStack highlightingItemStack;
    private int titleTicksRemaining;
    private GuiSpectator spectatorGui;
    private static String[] packedTextFragments;
    private GuiNewChat persistantChatGUI;
    private long healthUpdateCounter;
    private int playerHealth;
    private int titleFadeInTicks;

    private void renderPlayerStats(ScaledResolution resolution) {
        boolean legacyHudPass = Gui.getLegacyGuiRenderMarker() != null;
        if (!(this.mc.getRenderViewEntity() instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity) this.mc.getRenderViewEntity();
        int health = MathHelper.ceil(player.getHealth());
        boolean flashPreviousHealth = this.healthUpdateCounter > this.updateCounter
                && (this.healthUpdateCounter - this.updateCounter) / 3L % 2L == 1L;
        this.updateHudHealthTimers(player, health);
        int previousHealth = this.lastPlayerHealth;
        this.rand.setSeed(this.updateCounter * 312871);

        FoodStats food = player.getFoodStats();
        int foodLevel = food.getFoodLevel();
        IAttributeInstance maxHealthAttribute = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        int leftX = resolution.getWidth() / 2 - 91;
        int rightX = resolution.getWidth() / 2 + 91;
        int baselineY = resolution.getHeight() - 39;
        float maxHealth = (float) maxHealthAttribute.getAttributeValue();
        float absorption = player.getAbsorptionAmount();
        int heartRows = MathHelper.ceil((maxHealth + absorption) / 2.0F / 10.0F);
        int rowSpacing = Math.max(10 - (heartRows - 2), 3);
        int armorY = baselineY - (heartRows - 1) * rowSpacing - 10;
        int armor = player.getTotalArmorValue();
        int raisedHeart = -1;
        if (player.isPotionActive(Potion.REGENERATION)) {
            raisedHeart = this.updateCounter % MathHelper.ceil(maxHealth + 5.0F);
        }

        this.mc.mcProfiler.startSection("armor");
        this.drawArmorIcons(leftX, armorY, armor, legacyHudPass);

        this.mc.mcProfiler.endStartSection("health");
        this.drawHealthIcons(player, leftX, baselineY, rowSpacing, health, previousHealth,
                maxHealth, absorption, raisedHeart, flashPreviousHealth, legacyHudPass);

        Entity mount = player.ridingEntity;
        if (mount == null) {
            this.mc.mcProfiler.endStartSection("food");
            this.drawFoodIcons(player, rightX, baselineY, foodLevel, legacyHudPass);
        } else if (mount instanceof LivingEntity) {
            this.mc.mcProfiler.endStartSection("mountHealth");
            this.drawMountHealthIcons((LivingEntity) mount, rightX, baselineY, legacyHudPass);
        }

        this.mc.mcProfiler.endStartSection("air");
        if (player.isInsideOfMaterial(Material.water)) {
            this.drawAirIcons(this.mc.player.getAir(), rightX, armorY, legacyHudPass);
        }
        this.mc.mcProfiler.endSection();
    }

    private void updateHudHealthTimers(PlayerEntity player, int health) {
        if (player.hurtResistantTime > 0) {
            if (health < this.playerHealth) {
                this.lastSystemTime = MinecraftClient.getSystemTime();
                this.healthUpdateCounter = this.updateCounter + 20;
            } else if (health > this.playerHealth) {
                this.lastSystemTime = MinecraftClient.getSystemTime();
                this.healthUpdateCounter = this.updateCounter + 10;
            }
        }

        if (MinecraftClient.getSystemTime() - this.lastSystemTime > 1000L) {
            this.lastPlayerHealth = health;
            this.lastSystemTime = MinecraftClient.getSystemTime();
        }
        this.playerHealth = health;
    }

    private void drawArmorIcons(int leftX, int armorY, int armor, boolean legacyHudPass) {
        for (int icon = 0; icon < 10; icon++) {
            if (armor > 0) {
                int units = icon * 2 + 1;
                int textureX = units < armor ? 34 : units == armor ? 25 : 16;
                this.drawTexturedModalRect(leftX + icon * 8, armorY, textureX, 9, 9, 9);
            }
            if (legacyHudPass) {
                break;
            }
        }
    }

    private void drawHealthIcons(PlayerEntity player, int leftX, int baselineY, int rowSpacing,
                                 int health, int previousHealth, float maxHealth, float absorption,
                                 int raisedHeart, boolean flashPreviousHealth, boolean legacyHudPass) {
        float remainingAbsorption = absorption;
        for (int heart = MathHelper.ceil((maxHealth + absorption) / 2.0F) - 1; heart >= 0; heart--) {
            int heartTextureX = 16;
            boolean poisoned = player.isPotionActive(Potion.POISON);
            if (poisoned) {
                heartTextureX += 36;
            }
            if ((!poisoned || legacyHudPass) && player.isPotionActive(Potion.WITHER)) {
                heartTextureX += 72;
            }

            int row = MathHelper.ceil((heart + 1) / 10.0F) - 1;
            int x = leftX + heart % 10 * 8;
            int y = baselineY - row * rowSpacing;
            if (health <= 4) {
                y += this.rand.nextInt(2);
            }
            if (heart == raisedHeart) {
                y -= 2;
            }

            ClientServices services = (ClientServices) RemixClient.INSTANCE.getClientServices();
            MiniWitherStatusModule objective = ClientServices.moduleManager.miniWitherStatus;
            boolean hardcoreHearts = player.world.getWorldInfo().isHardcoreModeEnabled() || objective.objectiveDestroyed;
            int textureY = hardcoreHearts ? 45 : 0;
            this.drawTexturedModalRect(x, y, flashPreviousHealth ? 25 : 16, textureY, 9, 9);

            int units = heart * 2 + 1;
            if (flashPreviousHealth && units <= previousHealth) {
                this.drawTexturedModalRect(x, y, heartTextureX + (units < previousHealth ? 54 : 63), textureY, 9, 9);
            }

            boolean drawAbsorption = remainingAbsorption > 0.0F;
            if (drawAbsorption) {
                boolean halfAbsorption = remainingAbsorption == absorption && absorption % 2.0F == 1.0F;
                if (halfAbsorption) {
                    this.drawTexturedModalRect(x, y, heartTextureX + 153, textureY, 9, 9);
                }
                if (!halfAbsorption || legacyHudPass) {
                    this.drawTexturedModalRect(x, y, heartTextureX + 144, textureY, 9, 9);
                }
                remainingAbsorption -= 2.0F;
            }
            if ((!drawAbsorption || legacyHudPass) && units <= health) {
                this.drawTexturedModalRect(x, y, heartTextureX + (units < health ? 36 : 45), textureY, 9, 9);
            }

            if (legacyHudPass) {
                break;
            }
        }
    }

    private void drawFoodIcons(PlayerEntity player, int rightX, int baselineY, int foodLevel, boolean legacyHudPass) {
        for (int icon = 0; icon < 10; icon++) {
            int y = baselineY;
            boolean hungry = player.isPotionActive(Potion.HUNGER);
            int foodTextureX = hungry ? 52 : 16;
            if (player.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (foodLevel * 3 + 1) == 0) {
                y = baselineY + (this.rand.nextInt(3) - 1);
            }
            int x = rightX - icon * 8 - 9;
            this.drawTexturedModalRect(x, y, hungry ? 16 + 13 * 9 : 16, 27, 9, 9);
            int units = icon * 2 + 1;
            if (units <= foodLevel) {
                this.drawTexturedModalRect(x, y, foodTextureX + (units < foodLevel ? 36 : 45), 27, 9, 9);
            }
            if (legacyHudPass) {
                break;
            }
        }
    }

    private void drawMountHealthIcons(LivingEntity mount, int rightX, int baselineY, boolean legacyHudPass) {
        int health = (int) Math.ceil(mount.getHealth());
        int heartsLeft = Math.min((int) (mount.getMaxHealth() + 0.5F) / 2, 30);
        for (int row = 0; heartsLeft > 0; row++) {
            int heartsInRow = Math.min(heartsLeft, 10);
            heartsLeft -= heartsInRow;
            int y = baselineY - row * 10;
            for (int icon = 0; icon < heartsInRow; icon++) {
                int x = rightX - icon * 8 - 9;
                int units = icon * 2 + 1 + row * 20;
                this.drawTexturedModalRect(x, y, 52, 9, 9, 9);
                if (units <= health) {
                    this.drawTexturedModalRect(x, y, units < health ? 88 : 97, 9, 9, 9);
                }
                if (legacyHudPass) {
                    break;
                }
            }
            if (legacyHudPass) {
                break;
            }
        }
    }

    private void drawAirIcons(int air, int rightX, int armorY, boolean legacyHudPass) {
        int fullBubbles = MathHelper.ceil((air - 2) * 10.0 / 300.0);
        int totalBubbles = MathHelper.ceil(air * 10.0 / 300.0);
        for (int icon = 0; icon < totalBubbles; icon++) {
            int x = rightX - icon * 8 - 9;
            if (icon < fullBubbles) {
                this.drawTexturedModalRect(x, armorY, 16, 18, 9, 9);
            }
            if (icon >= fullBubbles || legacyHudPass) {
                this.drawTexturedModalRect(x, armorY, 25, 18, 9, 9);
            }
            if (legacyHudPass) {
                break;
            }
        }
    }

    public void setRecordPlaying(ChatComponent chatComponent, boolean flag) {
        this.setRecordPlaying(chatComponent.getUnformattedText(), flag);
    }

    private void renderScoreboard(ScoreObjective objective, ScaledResolution resolution) {
        boolean legacyScoreboardFlow = Gui.getLegacyGuiRenderMarker() != null;
        Scoreboard scoreboard = objective.getScoreboard();
        Collection<Score> allScores = scoreboard.getSortedScores(objective);
        GuiIngameRenderScoreboardPredicate visibleScore = new GuiIngameRenderScoreboardPredicate(this);
        List<Score> rows = new ArrayList<>();
        for (Score score : allScores) {
            if (visibleScore.hasVisibleScoreName(score)) {
                rows.add(score);
            }
        }
        if (rows.size() > 15 && !legacyScoreboardFlow) {
            // Keep the Minecraft 1.8 slice based on the pre-filter count.
            rows = Lists.newArrayList(Iterables.skip(rows, allScores.size() - 15));
        }

        FontRenderer font = this.getFontRenderer();
        String title = objective.getDisplayName();
        int width = font.getStringWidth(title);
        for (Score row : rows) {
            String line = formatScoreboardPlayer(scoreboard, row) + ": " + EnumChatFormatting.RED + row.getScorePoints();
            width = Math.max(width, font.getStringWidth(line));
            if (legacyScoreboardFlow) {
                break;
            }
        }

        RemixClient.INSTANCE.getClientServices();
        CameraModule camera = ClientServices.moduleManager.camera;
        int cameraOffset = (int) camera.scoreboardOffsetSetting.asDouble();
        int rowHeight = font.FONT_HEIGHT;
        int bottom = resolution.getHeight() / 2 - cameraOffset + rows.size() * rowHeight / 3;
        int left = resolution.getWidth() - width - 3;
        int right = resolution.getWidth() - 1;
        for (int index = 0; index < rows.size(); index++) {
            Score row = rows.get(index);
            int y = bottom - (index + 1) * rowHeight;
            String score = EnumChatFormatting.RED.toString() + row.getScorePoints();
            drawRect(left - 2, y, right, y + rowHeight, 1342177280);
            font.drawString(formatScoreboardPlayer(scoreboard, row), left, y, 553648127);
            font.drawString(score, right - font.getStringWidth(score), y, 553648127);
            if (index + 1 == rows.size()) {
                this.drawScoreboardTitle(font, title, left, right, width, y);
            }
            if (legacyScoreboardFlow) {
                break;
            }
        }
    }

    private static String formatScoreboardPlayer(Scoreboard scoreboard, Score row) {
        String name = row.getPlayerName();
        return ScorePlayerTeam.formatPlayerName(scoreboard.getPlayersTeam(name), name);
    }

    private void drawScoreboardTitle(FontRenderer font, String title, int left, int right, int width, int y) {
        drawRect(left - 2, y - font.FONT_HEIGHT - 1, right, y - 1, 1610612736);
        drawRect(left - 2, y - 1, right, y, 1342177280);
        font.drawString(title, left + width / 2 - font.getStringWidth(title) / 2, y - font.FONT_HEIGHT, 553648127);
    }

    public void updateTick() {
        boolean legacyFlow = Gui.getLegacyGuiRenderMarker() != null;
        if (this.recordPlayingUpFor > 0) {
            this.recordPlayingUpFor--;
        }
        if (this.titleTicksRemaining > 0) {
            this.titleTicksRemaining--;
            if (this.titleTicksRemaining <= 0) {
                this.titleText = packedTextFragments[6];
                this.subtitleText = packedTextFragments[7];
            }
        }

        this.updateCounter++;
        this.streamIndicator.updatePulse();
        if (this.mc.player != null) {
            ItemStack heldItem = this.mc.player.inventory.getCurrentItem();
            this.updateHeldItemHighlight(heldItem, legacyFlow);
            this.highlightingItemStack = heldItem;
        }
    }

    private void updateHeldItemHighlight(ItemStack heldItem, boolean legacyFlow) {
        if (heldItem == null) {
            this.remainingHighlightTicks = 0;
            if (!legacyFlow) {
                return;
            }
        }

        ItemStack previous = this.highlightingItemStack;
        if (previous != null && heldItem.getItem() == previous.getItem()
                && ItemStack.areItemStackTagsEqual(heldItem, previous)
                && (heldItem.isItemStackDamageable() || heldItem.getItemDamage() == previous.getItemDamage())) {
            if (this.remainingHighlightTicks <= 0) {
                return;
            }
            this.remainingHighlightTicks--;
            if (!legacyFlow) {
                return;
            }
        }
        this.remainingHighlightTicks = 40;
    }

    public GuiNewChat getChatGUI() {
        return this.persistantChatGUI;
    }

    public void setRecordPlaying(String text, boolean recordIsPlayingValue) {
        this.recordPlaying = text;
        this.recordPlayingUpFor = 60;
        this.recordIsPlaying = recordIsPlayingValue;
    }

    public void renderStreamIndicator(ScaledResolution scaledResolution) {
        this.streamIndicator.render(scaledResolution.getWidth() - 10, 10);
    }

    static {
        net.minecraft.client.gui.GuiIngame.packedTextFragments = new String[12];
        net.minecraft.client.gui.GuiIngame.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiIngame.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.GuiIngame.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.GuiIngame.packedTextFragments[3] = new String("");
        net.minecraft.client.gui.GuiIngame.packedTextFragments[4] = new String("");
        net.minecraft.client.gui.GuiIngame.packedTextFragments[5] = new String("");
        net.minecraft.client.gui.GuiIngame.packedTextFragments[6] = new String("");
        net.minecraft.client.gui.GuiIngame.packedTextFragments[7] = new String("");
        net.minecraft.client.gui.GuiIngame.packedTextFragments[8] = new String("");
        net.minecraft.client.gui.GuiIngame.packedTextFragments[9] = new String("");
        net.minecraft.client.gui.GuiIngame.vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
        net.minecraft.client.gui.GuiIngame.widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
        net.minecraft.client.gui.GuiIngame.pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
    }

    protected void renderHotbar(ScaledResolution scaledResolution, float f) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.mc.getRenderViewEntity() instanceof PlayerEntity) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.GuiIngame.widgetsTexPath);
            PlayerEntity playerEntity = (PlayerEntity) this.mc.getRenderViewEntity();
            int i = scaledResolution.getWidth() / 2;
            float zLevelValue = this.zLevel;
            this.zLevel = -90.0F;
            this.drawTexturedModalRect(i - 91, scaledResolution.getHeight() - 22, 0, 0, 182, 22);
            this.drawTexturedModalRect(
                    i - 91 - 1 + playerEntity.inventory.currentItem * 20,
                    scaledResolution.getHeight() - 22 - 1,
                    0,
                    22,
                    24,
                    22);
            this.zLevel = zLevelValue;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();
            int j = 0;

            while (j < 9) {
                int k = scaledResolution.getWidth() / 2 - 90 + j * 20 + 2;
                int l = scaledResolution.getHeight() - 16 - 3;
                this.renderHotbarItem(j, k, l, f, playerEntity);
                j++;
                if (astring != null) {
                    break;
                }
            }

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }

    public void displayTitle(String text, String textValue, int i, int j, int k) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (text == null && textValue == null && i < 0 && j < 0 && k < 0) {
            this.titleText = packedTextFragments[8];
            this.subtitleText = packedTextFragments[9];
            this.titleTicksRemaining = 0;
            if (astring == null) {
                return;
            }
        }

        if (text != null) {
            this.titleText = text;
            this.titleTicksRemaining = this.titleFadeInTicks + this.titleStayTicks + this.titleFadeOutTicks;
            if (astring == null) {
                return;
            }
        }

        if (textValue != null) {
            this.subtitleText = textValue;
            if (astring == null) {
                return;
            }
        }

        if (i >= 0) {
            this.titleFadeInTicks = i;
        }

        if (j >= 0) {
            this.titleStayTicks = j;
        }

        if (k >= 0) {
            this.titleFadeOutTicks = k;
        }

        if (this.titleTicksRemaining > 0) {
            this.titleTicksRemaining = this.titleFadeInTicks + this.titleStayTicks + this.titleFadeOutTicks;
        }
    }

    private void renderBossHealth() {
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
            BossStatus.statusBarTime--;
            FontRenderer anp = this.mc.fontRendererObj;
            ScaledResolution scaledResolution =
                    new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            int width = scaledResolution.getWidth();
            short short1 = 182;
            int j = width / 2 - short1 / 2;
            int k = (int) (BossStatus.healthScale * (short1 + 1));
            byte b0 = 12;
            this.drawTexturedModalRect(j, b0, 0, 74, short1, 5);
            this.drawTexturedModalRect(j, b0, 0, 74, short1, 5);
            if (k > 0) {
                this.drawTexturedModalRect(j, b0, 0, 79, k, 5);
            }

            String text = BossStatus.bossName;
            int l = 16777215;
            if (OptiFineConfig.isCustomColors()) {
                l = CustomColors.method_46b7d87f71f96e2a0dba(l);
            }

            this.getFontRenderer()
                    .drawStringWithShadow(
                            text,
                            width / 2 - this.getFontRenderer().getStringWidth(text) / 2,
                            b0 - 10,
                            l);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.Gui.icons);
        }
    }

    private void renderPortalOverlay(float f, ScaledResolution scaledResolution) {
        if (f < 1.0F) {
            f *= f;
            f *= f;
            f = f * 0.8F + 0.2F;
        }

        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, f);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        TextureAtlasSprite textureAtlasSprite =
                this.mc
                        .getBlockRendererDispatcher()
                        .getBlockModelShapes()
                        .getTexture(Blocks.portal.getDefaultState());
        float minU = textureAtlasSprite.getMinU();
        float minV = textureAtlasSprite.getMinV();
        float maxU = textureAtlasSprite.getMaxU();
        float maxV = textureAtlasSprite.getMaxV();
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(0.0, scaledResolution.getHeight(), -90.0, minU, maxV);
        worldRenderer.addVertexWithUV(
                scaledResolution.getWidth(), scaledResolution.getHeight(), -90.0, maxU, maxV);
        worldRenderer.addVertexWithUV(scaledResolution.getWidth(), 0.0, -90.0, maxU, minV);
        worldRenderer.addVertexWithUV(0.0, 0.0, -90.0, minU, minV);
        instance.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public GuiIngame(MinecraftClient amBValue) {
        this.recordPlaying = packedTextFragments[0];
        this.prevVignetteBrightness = 1.0F;
        this.titleText = packedTextFragments[1];
        this.subtitleText = packedTextFragments[2];
        this.playerHealth = 0;
        this.lastPlayerHealth = 0;

        this.lastSystemTime = 0L;

        this.healthUpdateCounter = 0L;
        this.mc = amBValue;
        this.itemRenderer = amBValue.getRenderItem();
        this.overlayDebug = new GuiOverlayDebug(amBValue);
        this.spectatorGui = new GuiSpectator(amBValue);
        this.persistantChatGUI = new GuiNewChat(amBValue);
        this.streamIndicator = new GuiStreamIndicator(amBValue);
        this.overlayPlayerList = new GuiPlayerTabOverlay(amBValue, this);
        this.resetTitleTimings();
    }

    public void renderDemoTimer(ScaledResolution resolution) {
        this.mc.mcProfiler.startSection("demo");
        boolean legacyFlow = Gui.getLegacyGuiRenderMarker() != null;
        long elapsedTicks = this.mc.world.getTotalWorldTime();
        String message = packedTextFragments[4];
        if (elapsedTicks >= 120500L) {
            message = I18n.format("demo.demoExpired", new Object[0]);
        }
        if (elapsedTicks < 120500L || legacyFlow) {
            String timeLeft = StringUtils.ticksToElapsedTime((int) (120500L - this.mc.world.getTotalWorldTime()));
            message = I18n.format("demo.remainingTime", new Object[] {timeLeft});
        }

        int width = this.getFontRenderer().getStringWidth(message);
        this.getFontRenderer().drawStringWithShadow(message, resolution.getWidth() - width - 10, 5.0F, 16777215);
        this.mc.mcProfiler.endSection();
    }

    public void setRecordPlayingMessage(String text) {
        this.setRecordPlaying(I18n.format("record.nowPlaying", new Object[] {text}), true);
    }

    public FontRenderer getFontRenderer() {
        return this.mc.fontRendererObj;
    }

    public void renderHeldItemTooltip(ScaledResolution scaledResolution) {
        this.mc.mcProfiler.startSection("toolHighlight");
        if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
            Object displayName = this.highlightingItemStack.getDisplayName();
            if (this.highlightingItemStack.hasDisplayName()) {
                displayName = EnumChatFormatting.ITALIC + (String) displayName;
            }

            int i =
                    (scaledResolution.getWidth()
                                    - this.getFontRenderer().getStringWidth((String) displayName))
                            / 2;
            int j = scaledResolution.getHeight() - 59;
            if (!this.mc.playerController.shouldDrawPlayerStats()) {
                j += 14;
            }

            int k = (int) (this.remainingHighlightTicks * 256.0F / 10.0F);
            if (k > 255) {
                k = 255;
            }

            if (k > 0) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                this.getFontRenderer()
                        .drawStringWithShadow((String) displayName, i, j, 16777215 + (k << 24));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }

        this.mc.mcProfiler.endSection();
    }

    public int getUpdateCounter() {
        return this.updateCounter;
    }

    public GuiSpectator getSpectatorGui() {
        return this.spectatorGui;
    }

    public void renderGameOverlay(float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        boolean legacyOverlayFallthrough = Gui.getLegacyGuiRenderMarker() != null;
        int screenWidth = scaledResolution.getWidth();
        int screenHeight = scaledResolution.getHeight();
        this.mc.entityRenderer.setupOverlayRendering();
        GlStateManager.enableBlend();
        boolean drawVignette = OptiFineConfig.isVignetteEnabled();
        if (drawVignette) {
            this.renderVignette(this.mc.player.getBrightness(partialTicks), scaledResolution);
        }
        if (!drawVignette || legacyOverlayFallthrough) {
            GlStateManager.enableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        }

        ItemStack itemStack = this.mc.player.inventory.armorItemInSlot(3);
        if (this.mc.gameSettings.thirdPersonView == 0
                && itemStack != null
                && itemStack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            this.renderPumpkinOverlay(scaledResolution);
        }

        if (!this.mc.player.isPotionActive(Potion.CONFUSION)) {
            float portalOverlayStrength = this.mc.player.prevTimeInPortal + (this.mc.player.timeInPortal - this.mc.player.prevTimeInPortal) * partialTicks;
            if (portalOverlayStrength > 0.0F) {
                this.renderPortalOverlay(portalOverlayStrength, scaledResolution);
            }
        }

        boolean spectator = this.mc.playerController.usesSpectatorHud();
        if (spectator) {
            this.spectatorGui.renderSpectatorHotbar(scaledResolution, partialTicks);
        }
        if (!spectator || legacyOverlayFallthrough) {
            this.renderHotbar(scaledResolution, partialTicks);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.Gui.icons);
        GlStateManager.enableBlend();
        if (this.showCrosshair() && this.mc.gameSettings.thirdPersonView < 1) {
            GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
            GlStateManager.enableAlpha();
            this.drawTexturedModalRect(screenWidth / 2 - 7, screenHeight / 2 - 7, 0, 0, 16, 16);
        }

        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        this.mc.mcProfiler.startSection("bossHealth");
        this.renderBossHealth();
        this.mc.mcProfiler.endSection();
        if (this.mc.playerController.shouldDrawPlayerStats()) {
            this.renderPlayerStats(scaledResolution);
        }

        GlStateManager.disableBlend();
        if (this.mc.player.getSleepTimer() > 0) {
            this.mc.mcProfiler.startSection("sleep");
            GlStateManager.disableDepth();
            GlStateManager.disableAlpha();
            int l = this.mc.player.getSleepTimer();
            float sleepFade = l / 100.0F;
            if (sleepFade > 1.0F) {
                sleepFade = 1.0F - (l - 100) / 10.0F;
            }

            int k = (int) (220.0F * sleepFade) << 24 | 1052704;
            drawRect(0.0, 0.0, screenWidth, screenHeight, k);
            GlStateManager.enableAlpha();
            GlStateManager.enableDepth();
            this.mc.mcProfiler.endSection();
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int barX = screenWidth / 2 - 91;
        boolean ridingHorse = this.mc.player.isRidingHorse();
        if (ridingHorse) {
            this.renderHorseJumpBar(scaledResolution, barX);
        }
        if ((!ridingHorse || legacyOverlayFallthrough) && this.mc.playerController.shouldDrawExperience()) {
            this.renderExpBar(scaledResolution, barX);
        }

        boolean renderItemTooltip = this.mc.gameSettings.heldItemTooltips
                && !this.mc.playerController.usesSpectatorHud();
        if (renderItemTooltip) {
            this.renderHeldItemTooltip(scaledResolution);
        }
        if ((!renderItemTooltip || legacyOverlayFallthrough) && this.mc.player.isSpectator()) {
            this.spectatorGui.renderSpectatorSelectionTooltip(scaledResolution);
        }

        if (this.mc.isDemo()) {
            this.renderDemoTimer(scaledResolution);
        }

        if (this.mc.gameSettings.showDebugInfo) {
            this.overlayDebug.renderDebugInfo(scaledResolution);
        }

        if (this.recordPlayingUpFor > 0) {
            this.mc.mcProfiler.startSection("overlayMessage");
            float recordTicksLeft = this.recordPlayingUpFor - partialTicks;
            int k1 = (int) (recordTicksLeft * 255.0F / 20.0F);
            if (k1 > 255) {
                k1 = 255;
            }

            if (k1 > 8) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(screenWidth / 2, screenHeight - 68, 0.0F);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                int i1 = 16777215;
                if (this.recordIsPlaying) {
                    i1 = Color.HSBtoRGB(recordTicksLeft / 50.0F, 0.7F, 0.6F) & 16777215;
                }

                this.getFontRenderer()
                        .drawString(
                                this.recordPlaying,
                                -this.getFontRenderer().getStringWidth(this.recordPlaying) / 2,
                                -4,
                                i1 + (k1 << 24 & 0xFF000000));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            this.mc.mcProfiler.endSection();
        }

        if (this.titleTicksRemaining > 0) {
            this.mc.mcProfiler.startSection("titleAndSubtitle");
            float titleTicksLeft = this.titleTicksRemaining - partialTicks;
            int l1 = 255;
            if (this.titleTicksRemaining > this.titleFadeOutTicks + this.titleStayTicks) {
                float f2 = this.titleFadeInTicks + this.titleStayTicks + this.titleFadeOutTicks - titleTicksLeft;
                l1 = (int) (f2 * 255.0F / this.titleFadeInTicks);
            }

            if (this.titleTicksRemaining <= this.titleFadeOutTicks) {
                l1 = (int) (titleTicksLeft * 255.0F / this.titleFadeOutTicks);
            }

            l1 = MathHelper.clamp(l1, 0, 255);
            if (l1 > 8) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(screenWidth / 2, screenHeight / 2, 0.0F);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.pushMatrix();
                GlStateManager.scale(4.0F, 4.0F, 4.0F);
                int j2 = l1 << 24 & 0xFF000000;
                this.getFontRenderer()
                        .drawString(
                                this.titleText,
                                -this.getFontRenderer().getStringWidth(this.titleText) / 2,
                                -10.0F,
                                16777215 | j2,
                                true);
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.scale(2.0F, 2.0F, 2.0F);
                this.getFontRenderer()
                        .drawString(
                                this.subtitleText,
                                -this.getFontRenderer().getStringWidth(this.subtitleText) / 2,
                                5.0F,
                                16777215 | j2,
                                true);
                GlStateManager.popMatrix();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }

            this.mc.mcProfiler.endSection();
        }

        Scoreboard scoreboard = this.mc.world.getScoreboard();
        ScoreObjective scoreObjective = null;
        ScorePlayerTeam playersTeam = scoreboard.getPlayersTeam(this.mc.player.getName());
        if (playersTeam != null) {
            int j1 = playersTeam.getChatFormat().getColorIndex();
            if (j1 >= 0) {
                scoreObjective = scoreboard.getObjectiveInDisplaySlot(3 + j1);
            }
        }

        ScoreObjective scoreObjectiveValue =
                scoreObjective != null ? scoreObjective : scoreboard.getObjectiveInDisplaySlot(1);
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        CameraModule camera = ClientServices.moduleManager.camera;
        if (scoreObjectiveValue != null
                && (!camera.isEnabled() || !camera.noScoreboardSetting.asBoolean())) {
            this.renderScoreboard(scoreObjectiveValue, scaledResolution);
        }

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, screenHeight - 48, 0.0F);
        this.mc.mcProfiler.startSection("chat");
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.mcProfiler.endSection();
        GlStateManager.popMatrix();

        ScoreObjective tabObjective = scoreboard.getObjectiveInDisplaySlot(0);
        boolean showPlayerList = this.mc.gameSettings.keyBindPlayerList.isKeyDown()
                && (!this.mc.isIntegratedServerRunning()
                        || this.mc.player.connection.getPlayerInfoMap().size() > 1
                        || tabObjective != null);
        if (showPlayerList) {
            this.overlayPlayerList.updatePlayerList(true);
            this.overlayPlayerList.renderPlayerlist(screenWidth, scoreboard, tabObjective);
        }
        if (!showPlayerList || legacyOverlayFallthrough) {
            this.overlayPlayerList.updatePlayerList(false);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
        RemixClient.eventBus.post(new Render2DEvent(scaledResolution));
    }

    public void resetTitleTimings() {
        this.titleFadeInTicks = 10;
        this.titleStayTicks = 70;
        this.titleFadeOutTicks = 20;
    }

    protected boolean showCrosshair() {
        if (this.mc.gameSettings.showDebugInfo
                && !this.mc.player.hasReducedDebug()
                && !this.mc.gameSettings.reducedDebugInfo) {
            return false;
        }

        if (this.mc.playerController.usesSpectatorHud()) {
            if (this.mc.pointedEntity != null) {
                return true;
            }

            if (this.mc.objectMouseOver != null
                    && this.mc.objectMouseOver.typeOfHit == RayHitType.BLOCK) {
                BlockPos blockPos = this.mc.objectMouseOver.getBlockPos();
                if (this.mc.world.getTileEntity(blockPos) instanceof IInventory) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }

    private void renderVignette(float brightness, ScaledResolution resolution) {
        boolean legacyFlow = Gui.getLegacyGuiRenderMarker() != null;
        if (!OptiFineConfig.isVignetteEnabled()) {
            return;
        }

        float darkness = MathHelper.clamp(1.0F - brightness, 0.0F, 1.0F);
        float borderWarning = this.getBorderWarningIntensity();
        if (legacyFlow) {
            borderWarning = 0.0F;
        }
        this.prevVignetteBrightness = (float) (this.prevVignetteBrightness
                + (darkness - this.prevVignetteBrightness) * 0.01);
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);
        if (borderWarning > 0.0F) {
            GlStateManager.color(0.0F, borderWarning, borderWarning, 1.0F);
        } else {
            GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
        }

        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.GuiIngame.vignetteTexPath);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(0.0, resolution.getHeight(), -90.0, 0.0, 1.0);
        worldRenderer.addVertexWithUV(
                resolution.getWidth(), resolution.getHeight(), -90.0, 1.0, 1.0);
        worldRenderer.addVertexWithUV(resolution.getWidth(), 0.0, -90.0, 1.0, 0.0);
        worldRenderer.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        instance.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }

    private float getBorderWarningIntensity() {
        WorldBorder border = this.mc.world.getWorldBorder();
        float distance = (float) border.getClosestDistance(this.mc.player);
        double movementWarning = Math.min(border.getResizeSpeed() * border.getWarningTime() * 1000.0,
                Math.abs(border.getTargetSize() - border.getDiameter()));
        double warningDistance = Math.max(border.getWarningDistance(), movementWarning);
        return distance < warningDistance ? 1.0F - (float) (distance / warningDistance) : 0.0F;
    }

    public void renderExpBar(ScaledResolution scaledResolution, int i) {
        this.mc.mcProfiler.startSection("expBar");
        this.mc.getTextureManager().bindTexture(Gui.icons);
        int j = this.mc.player.xpBarCap();
        if (j > 0) {
            short short1 = 182;
            int l = (int) (this.mc.player.experience * (short1 + 1));
            int k = scaledResolution.getHeight() - 32 + 3;
            this.drawTexturedModalRect(i, k, 0, 64, short1, 5);
            if (l > 0) {
                this.drawTexturedModalRect(i, k, 0, 69, l, 5);
            }
        }

        this.mc.mcProfiler.endSection();
        if (this.mc.player.experienceLevel > 0) {
            this.mc.mcProfiler.startSection("expLevel");
            int k1 = 8453920;
            if (OptiFineConfig.isCustomColors()) {
                k1 = CustomColors.getDurabilityColor(k1);
            }

            String text = packedTextFragments[3] + this.mc.player.experienceLevel;
            int j1 =
                    (scaledResolution.getWidth() - this.getFontRenderer().getStringWidth(text)) / 2;
            int i1 = scaledResolution.getHeight() - 31 - 4;

            this.getFontRenderer().drawString(text, j1 + 1, i1, 0);
            this.getFontRenderer().drawString(text, j1 - 1, i1, 0);
            this.getFontRenderer().drawString(text, j1, i1 + 1, 0);
            this.getFontRenderer().drawString(text, j1, i1 - 1, 0);
            this.getFontRenderer().drawString(text, j1, i1, k1);
            this.mc.mcProfiler.endSection();
        }
    }

    private void renderHotbarItem(
            int i, int j, int k, float f, PlayerEntity playerEntity) {
        ItemStack itemStack = playerEntity.inventory.mainInventory[i];
        if (itemStack != null) {
            float f1 = itemStack.animationsToGo - f;
            if (f1 > 0.0F) {
                GlStateManager.pushMatrix();
                float f2 = 1.0F + f1 / 5.0F;
                GlStateManager.translate(j + 8, k + 12, 0.0F);
                GlStateManager.scale(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate(-(j + 8), -(k + 12), 0.0F);
            }

            this.itemRenderer.renderItemAndEffectIntoGUI(itemStack, j, k);
            if (f1 > 0.0F) {
                GlStateManager.popMatrix();
            }

            this.itemRenderer.renderItemOverlays(this.mc.fontRendererObj, itemStack, j, k);
        }
    }

    public GuiPlayerTabOverlay getTabList() {
        return this.overlayPlayerList;
    }

    public void renderHorseJumpBar(ScaledResolution scaledResolution, int i) {
        this.mc.mcProfiler.startSection("jumpBar");
        this.mc.getTextureManager().bindTexture(Gui.icons);
        float horseJumpPower = this.mc.player.getHorseJumpPower();
        short short1 = 182;
        int j = (int) (horseJumpPower * (short1 + 1));
        int k = scaledResolution.getHeight() - 32 + 3;
        this.drawTexturedModalRect(i, k, 0, 84, short1, 5);
        if (j > 0) {
            this.drawTexturedModalRect(i, k, 0, 89, j, 5);
        }

        this.mc.mcProfiler.endSection();
    }

    private void renderPumpkinOverlay(ScaledResolution scaledResolution) {
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        this.mc.getTextureManager().bindTexture(net.minecraft.client.gui.GuiIngame.pumpkinBlurTexPath);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(0.0, scaledResolution.getHeight(), -90.0, 0.0, 1.0);
        worldRenderer.addVertexWithUV(
                scaledResolution.getWidth(), scaledResolution.getHeight(), -90.0, 1.0, 1.0);
        worldRenderer.addVertexWithUV(scaledResolution.getWidth(), 0.0, -90.0, 1.0, 0.0);
        worldRenderer.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        instance.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
