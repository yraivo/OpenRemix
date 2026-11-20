package net.minecraft.client.gui;

import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.IScoreObjectiveCriteriaEnumRenderType;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettingsGameType;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.player.AutoHypixelModule;
import remix.server.hypixel.HypixelServerDetector;

import java.util.Collection;
import java.util.List;

public class GuiPlayerTabOverlay extends Gui {
    private boolean isBeingRendered;

    private ChatComponent footer;
    private ChatComponent header;
    private MinecraftClient mc;
    private GuiIngame guiIngame;
    private long lastTimeOpened;
    public static Ordering PLAYER_ORDER;
    private static String[] packedTextFragments;

    public void renderPlayerlist(int screenWidth, Scoreboard scoreboard, ScoreObjective objective) {
        boolean legacyTabFlow = Gui.getLegacyGuiRenderMarker() != null;
        Collection<PlayerInfo> playerInfo = this.mc.player.connection.getPlayerInfoMap();
        if (this.shouldHideHypixelNpcs()) {
            playerInfo =
                    Collections2.filter(
                            playerInfo,
                            player ->
                                    player != null
                                            && player.getGameProfile().getId().version() != 2);
        }
        List<PlayerInfo> players =
                net.minecraft.client.gui.GuiPlayerTabOverlay.PLAYER_ORDER.sortedCopy(playerInfo);
        int nameWidth = 0;
        int numericScoreWidth = 0;
        boolean hearts =
                objective != null
                        && objective.getRenderType()
                                == IScoreObjectiveCriteriaEnumRenderType.HEARTS;
        for (PlayerInfo player : players) {
            nameWidth =
                    Math.max(
                            nameWidth,
                            this.mc.fontRendererObj.getStringWidth(this.getPlayerName(player)));
            if (objective != null && !hearts) {
                int score =
                        scoreboard
                                .getValueFromObjective(player.getGameProfile().getName(), objective)
                                .getScorePoints();
                numericScoreWidth =
                        Math.max(
                                numericScoreWidth,
                                this.mc.fontRendererObj.getStringWidth(" " + score));
            }
            if (legacyTabFlow) {
                break;
            }
        }

        players = players.subList(0, Math.min(players.size(), 80));
        int playerCount = players.size();
        int columnCount = 1;
        int rowsPerColumn = playerCount;
        while (rowsPerColumn > 20) {
            columnCount++;
            rowsPerColumn = (playerCount + columnCount - 1) / columnCount;
            if (legacyTabFlow) {
                break;
            }
        }

        boolean showFaces =
                this.mc.isIntegratedServerRunning()
                        || this.mc.getNetworkHandler().getNetworkManager().isEncrypted();
        int scoreWidth = objective == null || legacyTabFlow ? 0 : hearts ? 90 : numericScoreWidth;
        int columnWidth =
                Math.min(
                                columnCount * ((showFaces ? 9 : 0) + nameWidth + scoreWidth + 13),
                                screenWidth - 50)
                        / columnCount;
        int gridWidth = columnWidth * columnCount + (columnCount - 1) * 5;
        int left = screenWidth / 2 - gridWidth / 2;

        List<String> header = this.wrapTabText(this.header, screenWidth - 50);
        List<String> footer = this.wrapTabText(this.footer, screenWidth - 50);
        int overlayWidth =
                this.widestTabLine(
                        footer,
                        this.widestTabLine(header, gridWidth, legacyTabFlow),
                        legacyTabFlow);
        int y = 10;
        if (header != null) {
            y = this.drawTabText(header, screenWidth, overlayWidth, y, legacyTabFlow) + 1;
        }

        drawRect(
                screenWidth / 2 - overlayWidth / 2 - 1,
                y - 1,
                screenWidth / 2 + overlayWidth / 2 + 1,
                y + rowsPerColumn * 9,
                Integer.MIN_VALUE);
        for (int index = 0; index < playerCount; index++) {
            int column = index / rowsPerColumn;
            int row = index % rowsPerColumn;
            int cellX = left + column * (columnWidth + 5);
            this.drawPlayerCell(
                    players.get(index),
                    objective,
                    cellX,
                    y + row * 9,
                    columnWidth,
                    nameWidth,
                    scoreWidth,
                    showFaces,
                    legacyTabFlow);
            if (legacyTabFlow) {
                break;
            }
        }

        if (footer != null) {
            this.drawTabText(
                    footer, screenWidth, overlayWidth, y + rowsPerColumn * 9 + 1, legacyTabFlow);
        }
    }

    private boolean shouldHideHypixelNpcs() {
        RemixClient.INSTANCE.getClientServices();
        AutoHypixelModule autoHypixel = ClientServices.moduleManager.autoHypixel;
        return autoHypixel.isEnabled()
                && autoHypixel.npcHiderSetting.asBoolean()
                && HypixelServerDetector.isOnHypixel();
    }

    private List<String> wrapTabText(ChatComponent component, int width) {
        return component == null
                ? null
                : this.mc.fontRendererObj.listFormattedStringToWidth(
                        component.getFormattedText(), width);
    }

    private int widestTabLine(List<String> lines, int width, boolean legacyTabFlow) {
        if (lines != null) {
            for (String line : lines) {
                width = Math.max(width, this.mc.fontRendererObj.getStringWidth(line));
                if (legacyTabFlow) {
                    break;
                }
            }
        }
        return width;
    }

    private int drawTabText(
            List<String> lines, int screenWidth, int panelWidth, int y, boolean legacyTabFlow) {
        drawRect(
                screenWidth / 2 - panelWidth / 2 - 1,
                y - 1,
                screenWidth / 2 + panelWidth / 2 + 1,
                y + lines.size() * this.mc.fontRendererObj.FONT_HEIGHT,
                Integer.MIN_VALUE);
        for (String line : lines) {
            int width = this.mc.fontRendererObj.getStringWidth(line);
            this.mc.fontRendererObj.drawStringWithShadow(line, screenWidth / 2 - width / 2, y, -1);
            y += this.mc.fontRendererObj.FONT_HEIGHT;
            if (legacyTabFlow) {
                break;
            }
        }
        return y;
    }

    private void drawPlayerCell(
            PlayerInfo player,
            ScoreObjective objective,
            int x,
            int y,
            int columnWidth,
            int nameWidth,
            int scoreWidth,
            boolean showFaces,
            boolean legacyTabFlow) {
        drawRect(x, y, x + columnWidth, y + 8, 553648127);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        String name = this.getPlayerName(player);
        int nameX = x;
        if (showFaces) {
            this.drawPlayerFace(player, x, y);
            nameX += 9;
        }
        boolean spectator = player.getGameType() == WorldSettingsGameType.SPECTATOR;
        if (spectator) {
            name = EnumChatFormatting.ITALIC + name;
            this.mc.fontRendererObj.drawStringWithShadow(name, nameX, y, -1862270977);
        }
        if (!spectator || legacyTabFlow) {
            this.mc.fontRendererObj.drawStringWithShadow(name, nameX, y, -1);
        }
        if (objective != null && !spectator && scoreWidth > 5) {
            int scoreLeft = nameX + nameWidth + 1;
            this.drawScoreboardValues(
                    objective,
                    y,
                    player.getGameProfile().getName(),
                    scoreLeft,
                    scoreLeft + scoreWidth,
                    player);
        }
        this.drawPing(columnWidth, x, y, player);
    }

    private void drawPlayerFace(PlayerInfo player, int x, int y) {
        this.mc.getTextureManager().bindTexture(player.getLocationSkin());
        Gui.drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8.0F, 8.0F, 8.0F, 8.0F, 64.0F, 64.0F);
        PlayerEntity entity = this.mc.world.getPlayerEntityByUUID(player.getGameProfile().getId());
        if (entity != null && entity.isWearing(EnumPlayerModelParts.HAT)) {
            Gui.drawScaledCustomSizeModalRect(
                    x, y, 40.0F, 8.0F, 8.0F, 8.0F, 8.0F, 8.0F, 64.0F, 64.0F);
        }
    }

    public void updatePlayerList(boolean isBeingRenderedValue) {
        if (isBeingRenderedValue && !this.isBeingRendered) {
            this.lastTimeOpened = MinecraftClient.getSystemTime();
        }

        this.isBeingRendered = isBeingRenderedValue;
    }

    public String getPlayerName(PlayerInfo playerInfo) {
        return playerInfo.getDisplayName() != null
                ? playerInfo.getDisplayName().getFormattedText()
                : ScorePlayerTeam.formatPlayerName(
                        playerInfo.getPlayerTeam(), playerInfo.getGameProfile().getName());
    }

    static {
        net.minecraft.client.gui.GuiPlayerTabOverlay.packedTextFragments = new String[4];
        net.minecraft.client.gui.GuiPlayerTabOverlay.packedTextFragments[0] = new String(" ");
        net.minecraft.client.gui.GuiPlayerTabOverlay.packedTextFragments[1] = new String("");
        net.minecraft.client.gui.GuiPlayerTabOverlay.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.GuiPlayerTabOverlay.PLAYER_ORDER =
                Ordering.from(new PlayerComparator(null));
    }

    public void setFooter(ChatComponent apdValue) {
        this.footer = apdValue;
    }

    public void setHeader(ChatComponent apeValue) {
        this.header = apeValue;
    }

    public void drawPing(int columnWidth, int x, int y, PlayerInfo player) {
        boolean legacyPingFlow = Gui.getLegacyGuiRenderMarker() != null;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Gui.icons);
        int latency = player.getResponseTime();
        int iconRow = 4;
        if (!legacyPingFlow) {
            if (latency < 0) {
                iconRow = 5;
            } else if (latency < 150) {
                iconRow = 0;
            } else if (latency < 300) {
                iconRow = 1;
            } else if (latency < 600) {
                iconRow = 2;
            } else if (latency < 1000) {
                iconRow = 3;
            }
        }

        this.zLevel += 100.0F;
        this.drawTexturedModalRect(x + columnWidth - 11, y, 0, 176 + iconRow * 8, 10, 8);
        this.zLevel -= 100.0F;
    }

    public GuiPlayerTabOverlay(MinecraftClient aoXValue, GuiIngame apfValue) {
        this.mc = aoXValue;
        this.guiIngame = apfValue;
    }

    private void drawScoreboardValues(
            ScoreObjective objective,
            int y,
            String playerName,
            int left,
            int right,
            PlayerInfo player) {
        boolean legacyScoreFlow = Gui.getLegacyGuiRenderMarker() != null;
        int score =
                objective
                        .getScoreboard()
                        .getValueFromObjective(playerName, objective)
                        .getScorePoints();
        if (objective.getRenderType() == IScoreObjectiveCriteriaEnumRenderType.HEARTS) {
            this.drawHealthScore(player, score, left, right, y, legacyScoreFlow);
            if (!legacyScoreFlow) {
                return;
            }
        }

        String text = EnumChatFormatting.YELLOW.toString() + score;
        this.mc.fontRendererObj.drawStringWithShadow(
                text, right - this.mc.fontRendererObj.getStringWidth(text), y, 16777215);
    }

    private void drawHealthScore(
            PlayerInfo player, int health, int left, int right, int y, boolean legacyScoreFlow) {
        this.mc.getTextureManager().bindTexture(Gui.icons);
        this.updateHealthScoreBlink(player, health, legacyScoreFlow);
        int displayHealth = player.getDisplayHealth();
        int visibleHearts = MathHelper.ceil(Math.max(health, displayHealth) / 2.0F);
        int heartSlots =
                Math.max(
                        MathHelper.ceil(health / 2),
                        Math.max(MathHelper.ceil(displayHealth / 2), 10));
        long blinkUntilTick = player.getHealthBlinkUntilTick();
        int updateTick = this.guiIngame.getUpdateCounter();
        boolean blinking =
                blinkUntilTick > updateTick && (blinkUntilTick - updateTick) / 3L % 2L == 1L;

        if (visibleHearts <= 0) {
            return;
        }
        float spacing = Math.min((float) (right - left - 4) / heartSlots, 9.0F);
        if (spacing > 3.0F) {
            this.drawScoreHearts(
                    health,
                    displayHealth,
                    visibleHearts,
                    heartSlots,
                    left,
                    y,
                    spacing,
                    blinking,
                    legacyScoreFlow);
            if (!legacyScoreFlow) {
                return;
            }
        }
        float healthFraction = MathHelper.clamp(health / 20.0F, 0.0F, 1.0F);
        int color =
                (int) ((1.0F - healthFraction) * 255.0F) << 16
                        | (int) (healthFraction * 255.0F) << 8;
        String text = String.valueOf(health / 2.0F);
        if (right - this.mc.fontRendererObj.getStringWidth(text + "hp") >= left) {
            text += "hp";
        }
        this.mc.fontRendererObj.drawStringWithShadow(
                text,
                (right + left) / 2 - this.mc.fontRendererObj.getStringWidth(text) / 2,
                y,
                color);
    }

    private void updateHealthScoreBlink(PlayerInfo player, int health, boolean legacyScoreFlow) {
        if (this.lastTimeOpened == player.getLastTabOpenMillis()) {
            boolean healthDecreased = health < player.getLastHealth();
            if (healthDecreased) {
                player.setLastHealthChangeMillis(MinecraftClient.getSystemTime());
                player.setHealthBlinkUntilTick(this.guiIngame.getUpdateCounter() + 20);
            }
            if ((!healthDecreased || legacyScoreFlow) && health > player.getLastHealth()) {
                player.setLastHealthChangeMillis(MinecraftClient.getSystemTime());
                player.setHealthBlinkUntilTick(this.guiIngame.getUpdateCounter() + 10);
            }
        }

        long timeSinceChange = MinecraftClient.getSystemTime() - player.getLastHealthChangeMillis();
        if (timeSinceChange > 1000L || this.lastTimeOpened != player.getLastTabOpenMillis()) {
            player.setLastHealth(health);
            player.setDisplayHealth(health);
            player.setLastHealthChangeMillis(MinecraftClient.getSystemTime());
        }
        player.setLastTabOpenMillis(this.lastTimeOpened);
        player.setLastHealth(health);
    }

    private void drawScoreHearts(
            int health,
            int displayHealth,
            int visibleHearts,
            int heartSlots,
            int left,
            int y,
            float spacing,
            boolean blinking,
            boolean legacyScoreFlow) {
        int backgroundU = blinking ? 25 : 16;
        for (int index = visibleHearts; index < heartSlots; index++) {
            this.drawTexturedModalRect(left + index * spacing, y, backgroundU, 0, 9, 9);
            if (legacyScoreFlow) {
                break;
            }
        }

        for (int index = 0; index < visibleHearts; index++) {
            float x = left + index * spacing;
            int halfHeart = index * 2 + 1;
            this.drawTexturedModalRect(x, y, backgroundU, 0, 9, 9);
            if (blinking) {
                if (halfHeart < displayHealth) {
                    this.drawTexturedModalRect(x, y, 70, 0, 9, 9);
                } else if (halfHeart == displayHealth) {
                    this.drawTexturedModalRect(x, y, 79, 0, 9, 9);
                }
            }
            if (halfHeart < health) {
                this.drawTexturedModalRect(x, y, index >= 10 ? 160 : 52, 0, 9, 9);
            } else if (halfHeart == health) {
                this.drawTexturedModalRect(x, y, index >= 10 ? 169 : 61, 0, 9, 9);
            }
            if (legacyScoreFlow) {
                break;
            }
        }
    }

    // Helper used only by this class.
    static class PlayerComparator implements java.util.Comparator {
        private static java.lang.String[] packedTextFragments;

        PlayerComparator(java.lang.Object object) {
            this();
        }

        @java.lang.Override
        public int compare(java.lang.Object object, java.lang.Object object1) {
            return this.method_892133e38a80d4f268f8(
                    (net.minecraft.client.network.PlayerInfo) object,
                    (net.minecraft.client.network.PlayerInfo) object1);
        }

        // region String decoding support

        // endregion

        static {
            packedTextFragments = new java.lang.String[2];
            unpackLiteralFragments();
        }

        private PlayerComparator() {}

        // region String decoding support

        // endregion

        public int method_892133e38a80d4f268f8(
                net.minecraft.client.network.PlayerInfo playerInfo,
                net.minecraft.client.network.PlayerInfo playerInfoValue) {
            net.minecraft.scoreboard.ScorePlayerTeam scorePlayerTeam = playerInfo.getPlayerTeam();
            net.minecraft.scoreboard.ScorePlayerTeam scorePlayerTeamValue =
                    playerInfoValue.getPlayerTeam();
            return com.google.common.collect.ComparisonChain.start()
                    .compareTrueFirst(
                            playerInfo.getGameType()
                                    != net.minecraft.world.WorldSettingsGameType.SPECTATOR,
                            playerInfoValue.getGameType()
                                    != net.minecraft.world.WorldSettingsGameType.SPECTATOR)
                    .compare(
                            scorePlayerTeam != null
                                    ? scorePlayerTeam.getRegisteredName()
                                    : packedTextFragments[0],
                            scorePlayerTeamValue != null
                                    ? scorePlayerTeamValue.getRegisteredName()
                                    : packedTextFragments[1])
                    .compare(
                            playerInfo.getGameProfile().getName(),
                            playerInfoValue.getGameProfile().getName())
                    .result();
        }

        // region String decoding support
        private static void unpackLiteralFragments() {
            net.minecraft.client.gui.GuiPlayerTabOverlay.PlayerComparator.packedTextFragments[0] =
                    new java.lang.String("");
            net.minecraft.client.gui.GuiPlayerTabOverlay.PlayerComparator.packedTextFragments[1] =
                    new java.lang.String("");
        }
        // endregion
    }
}
