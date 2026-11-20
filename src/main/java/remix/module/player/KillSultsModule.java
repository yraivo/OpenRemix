package remix.module.player;

import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.command.ClientCommandManager;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/** KillSults module: player events and 2 settings. */
public final class KillSultsModule extends ModuleBase {
    private String[] killMessagePatterns;
    private static ScheduledExecutorService messageExecutor;
    private int unusedCounter;
    public static List<String> remainingMessages;
    public static List<String> messageTemplates;
    private final Setting delaySetting = new Setting("Delay", this, 3000.0, 0.0, 5000.0, 50.0);
    private static String[] packedTextFragments;
    private final Setting allowRepetitionsSetting = new Setting("AllowRepetitions", this, true);

    @Override
    public void onEnable() {
        super.onEnable();
        GuiNewChat chatGUI = minecraft.ingameGUI.getChatGUI();
        if (minecraft.world != null
                && minecraft.player != null
                && chatGUI != null
                && ClientServices.commands != null) {
            ClientCommandManager.printMessage(
                    "Don't forget that you can use the .kst command to add your own killsults!");
        }
    }

    static {
        packedTextFragments = new String[3];
        packedTextFragments[0] = " ";
        packedTextFragments[1] = "";
        remainingMessages = new ArrayList<>();
        messageTemplates = new ArrayList<>();
        messageExecutor = Executors.newSingleThreadScheduledExecutor();
        remainingMessages.addAll(
                Arrays.asList(
                        "Do you practise being this ugly %s?",
                        "Buy Remix-Client, slave . . .",
                        "So hows it like being in spectator, %s?",
                        "Did your parents ever ask you to run away from home %s?",
                        "Is your ass jealous of the amount of shit that just came out of your mouth %s?",
                        "How did you get here %s? Did someone leave your cage open?",
                        "Keep barking %s, someday u might say something intelligent.",
                        "I can understand why your parents abused you %s.",
                        "%s I'm gonna rip you apart, tear out your heart and then bringing back california 2."));
        messageTemplates.addAll(remainingMessages);
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() == PacketDirection.SENT
                && event.getPacket() instanceof C01PacketChatMessage) {
            this.timer.reset();
        }

        if (event.getDirection() == PacketDirection.RECEIVED
                && event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            List siblings = packet.getChatComponent().getSiblings();
            if (siblings.size() == 4) {
                String victimName =
                        ((ChatComponentText) siblings.get(siblings.size() - 4))
                                .getChatComponentText_TextValue()
                                .replace(" ", "");
                String deathPrefix =
                        ((ChatComponentText) siblings.get(siblings.size() - 3))
                                .getChatComponentText_TextValue();
                String killerName =
                        ((ChatComponentText) siblings.get(siblings.size() - 2))
                                .getChatComponentText_TextValue();
                if (deathPrefix.endsWith("by ")
                        && killerName.equalsIgnoreCase(minecraft.player.getName())) {
                    boolean allowRepetitions = this.allowRepetitionsSetting.asBoolean();
                    if (!allowRepetitions && remainingMessages.isEmpty()) {
                        remainingMessages = new ArrayList<>(messageTemplates);
                    }

                    int i = ThreadLocalRandom.current().nextInt(remainingMessages.size());
                    String template = remainingMessages.get(i);
                    String message = String.format(template, victimName);
                    if (!this.timer.hasElapsed((long) this.delaySetting.asDouble())) {
                        Runnable runnable = () -> minecraft.player.sendChatMessage(message);
                        messageExecutor.schedule(
                                runnable,
                                (long) this.delaySetting.asDouble() - this.timer.elapsedMillis(),
                                TimeUnit.MILLISECONDS);
                    } else {
                        minecraft.player.sendChatMessage(message);
                        this.timer.reset();
                    }

                    if (!allowRepetitions) {
                        remainingMessages.remove(i);
                    }
                }
            }
        }
    }

    public KillSultsModule() {
        super("KillSults", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));
        this.killMessagePatterns =
                new String[] {
                    " was struck down by %s.",
                    " was filled full of lead by %s.",
                    " was thrown into the void by %s.",
                    " was killed by %s."
                };
        this.setName("Kill Sult");
    }
}
