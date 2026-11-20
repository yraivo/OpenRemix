package remix.module.player;

import net.minecraft.client.network.PlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEventAction;
import net.minecraft.network.Packet;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.command.ClientCommandManager;
import remix.command.ClientCommandOutput;
import remix.event.annotation.Subscribe;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.server.hypixel.HypixelGameType;
import remix.server.hypixel.HypixelLocationData;
import remix.server.hypixel.HypixelServerDetector;
import remix.setting.Setting;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/** AutoHypixel module: player events and 11 settings. */
public final class AutoHypixelModule extends ModuleBase {

    private Setting ggSetting;

    private Setting connectedMsgSetting;

    private Setting delaySetting;

    private String[] gameEndPhrases;

    private final Setting<AutoHypixelLanguage> languageEnumSetting =
            new Setting("Language", this, AutoHypixelLanguage.ENGLISH);

    public Setting pitAntiLagSetting;

    public String spectatorCommandError;

    private boolean languageUpdatePending;

    public String shoutAfterGameError;

    public String shoutBeforeGameError;

    private long shoutCooldownDeadline;

    private Pattern advertisementPattern;

    public Pattern serverTransferPattern;

    public double nameTagHeightCutoff;

    private Setting adBlockerSetting;

    private DecimalFormat cooldownFormatter;

    public Setting bountyHunterSetting;

    public Setting shoutBlockerSetting;

    private List<String> rankKeywords;

    private static String[] packedTextFragments;

    public Setting npcHiderSetting;

    private Setting playSetting;

    private List<String> beggingKeywords;

    private ScheduledExecutorService chatExecutor;

    public String shoutGameNotStartedError;

    public static final int bountyTextColor = -662245;

    private String serverTransferRegex;

    private Setting languageBooleanSetting;





    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {

        Packet packet = event.getPacket();

        switch (event.getDirection()) {
            case RECEIVED:
                if (packet instanceof S02PacketChat) {

                    S02PacketChat s02PacketChat = (S02PacketChat) packet;

                    ChatComponent chatComponent = s02PacketChat.getChatComponent();

                    if (chatComponent != null) {

                        if (this.shoutBlockerSetting.asBoolean()) {

                            String text = chatComponent.getFormattedText();

                            ClientServices clientServices =
                                    (ClientServices) RemixClient.INSTANCE.getClientServices();

                            HypixelLocationData location =
                                    ClientServices.moduleManager.hypixelLocrawListener
                                            .getCurrentLocation();

                            if (location != null
                                    && (location.getHypixelGameType() == HypixelGameType.SKY_WARS
                                                    && text.equals(this.shoutGameNotStartedError)
                                            || text.equals(this.shoutAfterGameError)
                                            || text.equals(this.shoutBeforeGameError)
                                            || text.equals(this.spectatorCommandError))) {

                                this.shoutCooldownDeadline = 0L;
                            }
                        }

                        String textValue = chatComponent.getUnformattedText();

                        if (this.connectedMsgSetting.asBoolean()
                                && this.serverTransferPattern.matcher(textValue).matches()) {

                            event.cancel();
                        }

                        if (this.adBlockerSetting.asBoolean()) {

                            String textValue2 = textValue.toLowerCase(Locale.ENGLISH);

                            if (this.advertisementPattern.matcher(textValue2).find(0)) {

                                event.cancel();

                                return;
                            }

                            for (String textValue3 : this.beggingKeywords) {

                                if (textValue2.contains(textValue3)) {

                                    for (String textValue4 : this.rankKeywords) {

                                        if (textValue2.contains(textValue4)) {

                                            event.cancel();

                                            break;
                                        }
                                    }

                                    break;
                                }
                            }
                        }

                        if (this.ggSetting.asBoolean()) {

                            String textValue5 = textValue;

                            byte b0 = 0;

                            for (String textValue6 : this.gameEndPhrases) {

                                if (textValue5.contains(textValue6)) {

                                    b0 = 1;

                                    break;
                                }
                            }

                            if (textValue5.startsWith(packedTextFragments[0]) && b0 != 0) {

                                ScheduledExecutorService scheduledexecutorservice =
                                        this.chatExecutor;

                                Runnable runnable = () -> minecraft.player.sendChatMessage("gg");

                                long delay = (long) this.delaySetting.asDouble();

                                scheduledexecutorservice.schedule(
                                        runnable, delay / 2L, TimeUnit.MILLISECONDS);
                            }
                        }

                        if (this.playSetting.asBoolean()) {

                            List siblings = chatComponent.getSiblings();

                            int j = siblings.size();

                            if (j == 2) {

                                String textValue7 =
                                        ((ChatComponentText) siblings.get(0))
                                                .getChatComponentText_TextValue();

                                ChatComponentText chatComponentText =
                                        (ChatComponentText) siblings.get(j - 1);

                                if (textValue7.equalsIgnoreCase("Want to play again?")) {

                                    ChatStyle replayStyle171 = chatComponentText.getChatStyle();

                                    ClickEvent replayClick171 =
                                            replayStyle171 == null
                                                    ? null
                                                    : replayStyle171.getChatClickEvent();

                                    if (replayClick171 != null) {

                                        String replayCommand171 = replayClick171.getValue();

                                        boolean replayRunCommand171 =
                                                replayClick171.getAction()
                                                        == ClickEventAction.RUN_COMMAND;

                                        String replayPrefix171 =
                                                ((ClientServices)
                                                                RemixClient.INSTANCE
                                                                        .getClientServices())
                                                        .commandPrefix;

                                        if (remix.security.ServerCommandPolicy.isAllowedPlayCommand(
                                                replayRunCommand171,
                                                replayCommand171,
                                                replayPrefix171)) {

                                            this.chatExecutor.schedule(
                                                    () -> {
                                                        ChatStyle currentStyle171 =
                                                                chatComponentText.getChatStyle();

                                                        ClickEvent currentClick171 =
                                                                currentStyle171 == null
                                                                        ? null
                                                                        : currentStyle171
                                                                                .getChatClickEvent();

                                                        if (currentClick171 != null) {

                                                            String currentCommand171 =
                                                                    currentClick171.getValue();

                                                            String currentPrefix171 =
                                                                    ((ClientServices)
                                                                                    RemixClient
                                                                                            .INSTANCE
                                                                                            .getClientServices())
                                                                            .commandPrefix;

                                                            if (remix.security.ServerCommandPolicy
                                                                    .isAllowedPlayCommand(
                                                                            currentClick171
                                                                                            .getAction()
                                                                                    == ClickEventAction
                                                                                            .RUN_COMMAND,
                                                                            currentCommand171,
                                                                            currentPrefix171)) {

                                                                minecraft.player.sendChatMessage(
                                                                        currentCommand171);
                                                            }
                                                        }
                                                    },
                                                    (long) this.delaySetting.asDouble(),
                                                    TimeUnit.MILLISECONDS);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    break;
                }

                if (!(packet instanceof S03PacketEnableCompression)
                        || !this.languageBooleanSetting.asBoolean()) {

                    break;
                }

                this.languageUpdatePending = true;

                break;

            case SEND:
                if (this.shoutBlockerSetting.asBoolean()
                        && packet instanceof C01PacketChatMessage) {

                    C01PacketChatMessage c01PacketChatMessage = (C01PacketChatMessage) packet;

                    String textValue8 = c01PacketChatMessage.getMessage();

                    if (textValue8.startsWith("/shout ")) {

                        if (this.shoutCooldownDeadline < System.currentTimeMillis()) {

                            long l = System.currentTimeMillis();

                            long j1 = this.getShoutCooldownSeconds();

                            this.shoutCooldownDeadline = l + j1 * 1000L;

                            return;
                        }

                        long k = this.shoutCooldownDeadline - System.currentTimeMillis();

                        long i = k / 1000L;

                        ClientCommandOutput output = ClientCommandManager.output;

                        StringBuilder stringbuilder =
                                new StringBuilder()
                                        .append(
                                                "\u00a7eShout"
                                                    + " command"
                                                    + " is on"
                                                    + " cooldown."
                                                    + " Please"
                                                    + " wait"
                                                    + " " /* "§eShout command is on cooldown. Please wait " */)
                                        .append(this.cooldownFormatter.format(i))
                                        .append(" more second");

                        output.printMessage(
                                stringbuilder
                                        .append(i == 1L ? packedTextFragments[1] : "s.")
                                        .toString(),
                                new Object[0]);

                        event.cancel();
                    }
                }
        }
    }

    public int parseBounty(String displayText) {

        try {

            if (displayText == null || displayText.isEmpty()) {

                return -1;
            }

        } catch (Exception exception2) {

            throw remix.compat.compiler.JvmThrowTypes.flow(preserveException(exception2));
        }

        int i = displayText.lastIndexOf(packedTextFragments[2]);

        if (i != -1) {

            displayText = displayText.substring(i + 1);

            int j = displayText.length() - 1;

            try {

                if (displayText.isEmpty() || displayText.charAt(j) != 'g') {

                    return -1;
                }

            } catch (Exception exception1) {

                throw remix.compat.compiler.JvmThrowTypes.flow(preserveException(exception1));
            }

            int k = displayText.lastIndexOf("\u00a76\u00a7l" /* "§6§l" */);

            if (k != -1) {

                k += "\u00a76\u00a7l" /* "§6§l" */.length();

                String textValue = displayText.substring(k, j);

                try {

                    return Integer.parseInt(textValue);

                } catch (Exception exception) {

                }
            }
        }

        return -1;
    }





    private long getShoutCooldownSeconds() {

        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();

        HypixelLocationData location =
                ClientServices.moduleManager.hypixelLocrawListener.getCurrentLocation();

        if (location != null) {

            String text = location.getMode();

            if (!"LOBBY".equals(text)) {

                switch (location.getHypixelGameType()) {
                    case BED_WARS:
                        if (!text.equals("BEDWARS_EIGHT_ONE")) {

                            return 60L;
                        }

                        break;

                    case SKY_WARS:
                        long j = -3775490796801632138L;

                        return 3L;

                    case ARCADE_GAMES:
                        if (text.equals("PVP_CTW")) {

                            return 10L;
                        }

                        break;

                    case UHC_CHAMPIONS:
                        if (text.equals("TEAMS")) {

                            return 90L;
                        }
                }
            }
        }

        return 0L;
    }


    // region Static initialization and string tables

    static {
        remix.module.player.AutoHypixelModule.packedTextFragments = new String[4];

        remix.module.player.AutoHypixelModule.packedTextFragments[0] = new String(" ");

        remix.module.player.AutoHypixelModule.packedTextFragments[1] = new String(".");

        remix.module.player.AutoHypixelModule.packedTextFragments[2] = new String(" ");
    }

    // endregion

    private static Exception preserveException(Exception exception) {

        return exception;
    }

    public boolean hasBounty(Entity entity) {

        return this.getBounty(entity) != -1;
    }

    public AutoHypixelModule() {

        super("AutoHypixel", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));

        this.delaySetting = new Setting("Delay", this, 3000.0, 0.0, 5000.0, 50.0);

        this.playSetting = new Setting("Play", this, true);

        this.bountyHunterSetting = new Setting("BountyHunter", this, true);

        this.languageBooleanSetting = new Setting("Language", this, true);

        this.connectedMsgSetting = new Setting("ConnectedMsg", this, false);

        this.adBlockerSetting = new Setting("AdBlocker", this, false);

        this.npcHiderSetting = new Setting("NPCHider", this, false);

        this.pitAntiLagSetting = new Setting("PitAntiLag", this, false);

        this.shoutBlockerSetting = new Setting("ShoutBlocker", this, false);

        this.ggSetting = new Setting("GG", this, true);

        this.spectatorCommandError = "You are not allowed to use commands as a spectator!";

        this.shoutGameNotStartedError = "You can't shout until the game has started!";

        this.shoutBeforeGameError = "You can't use /shout before the game has started.";

        this.shoutAfterGameError = "You can't use /shout after the game has finished.";

        this.advertisementPattern =
                Pattern.compile(
                        "(?:/?)(((party join|join party)|p join|(guild join)|(join guild)|g join)"
                            + " \\w{1,16})|(twitch.tv)|(youtube.com|youtu.be)|(/(visit|ah)"
                            + " \\w{1,16}|(visit"
                            + " /\\w{1,16})|(/gift))" /* "(?:/?)(((party join|join party)|p join|(guild join)|(join guild)|g join) ＼＼w{1,16})|(twitch.tv)|(youtube.com|youtu.be)|(/(visit|ah) ＼＼w{1,16}|(visit /＼＼w{1,16})|(/gift))" */,
                        2);

        this.shoutCooldownDeadline = 0L;

        this.cooldownFormatter = new DecimalFormat("#.#");

        this.rankKeywords = Arrays.asList("mvp", "vip");

        this.beggingKeywords = Arrays.asList("give", "please", "pls", "plz", "gift");

        this.chatExecutor = Executors.newSingleThreadScheduledExecutor();

        this.gameEndPhrases =
                new String[] {
                    "1st Killer - ",
                    "1st Place - ",
                    "Winner: ",
                    " - Damage Dealt - ",
                    "Winning Team -",
                    "1st - ",
                    "Winners: ",
                    "Winner: ",
                    "Winning Team: ",
                    " won the game!",
                    "Top Seeker: ",
                    "1st Place: ",
                    "Last team standing!",
                    "Winner #1 (",
                    "Top Survivors",
                    "Winners - ",
                    "Sumo Duel -"
                };

        this.setName("Auto Hypixel");

        this.setVisible(false);

        this.serverTransferRegex =
                "You are"
                    + " currently"
                    + " connected"
                    + " to server"
                    + " \\S+|Sending"
                    + " to server"
                    + " \\S+\\.{3}" /* "You are currently connected to server ＼＼S+|Sending to server ＼＼S+＼＼.{3}" */;

        this.serverTransferPattern = Pattern.compile(this.serverTransferRegex);
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {

        if (this.languageBooleanSetting.asBoolean()
                && this.languageUpdatePending
                && HypixelServerDetector.isOnHypixel()) {

            minecraft.player.sendChatMessage(
                    "/language "
                            + ((AutoHypixelLanguage) this.languageEnumSetting.getValue())
                                    .toString());

            this.languageUpdatePending = false;
        }
    }

    public int getBounty(Entity entity) {

        PlayerInfo playerInfo = minecraft.getNetworkHandler().getPlayerInfo(entity.getUniqueId());

        if (playerInfo == null) {

            return -1;
        }

        ScorePlayerTeam scorePlayerTeam = playerInfo.getPlayerTeam();

        return scorePlayerTeam == null ? -1 : this.parseBounty(scorePlayerTeam.getColorSuffix());
    }
}
