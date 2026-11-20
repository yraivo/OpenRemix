package net.minecraft.command;

import net.minecraft.command.common.CommandReplaceItem;
import net.minecraft.command.server.CommandAchievement;
import net.minecraft.command.server.CommandBanIp;
import net.minecraft.command.server.CommandBanPlayer;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.command.server.CommandBroadcast;
import net.minecraft.command.server.CommandDeOp;
import net.minecraft.command.server.CommandEmote;
import net.minecraft.command.server.CommandListBans;
import net.minecraft.command.server.CommandListPlayers;
import net.minecraft.command.server.CommandMessage;
import net.minecraft.command.server.CommandMessageRaw;
import net.minecraft.command.server.CommandOp;
import net.minecraft.command.server.CommandPardonIp;
import net.minecraft.command.server.CommandPardonPlayer;
import net.minecraft.command.server.CommandPublishLocalServer;
import net.minecraft.command.server.CommandSaveAll;
import net.minecraft.command.server.CommandSaveOff;
import net.minecraft.command.server.CommandSaveOn;
import net.minecraft.command.server.CommandScoreboard;
import net.minecraft.command.server.CommandSetBlock;
import net.minecraft.command.server.CommandSetDefaultSpawnpoint;
import net.minecraft.command.server.CommandStop;
import net.minecraft.command.server.CommandSummon;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.command.server.CommandTestFor;
import net.minecraft.command.server.CommandTestForBlock;
import net.minecraft.command.server.CommandWhitelist;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class ServerCommandManager extends CommandHandler
        implements IAdminCommand {

    public void notifyOperators(
            CommandSender commandSender,
            ICommand iCommand,
            int i,
            String text,
            Object... aobject) {
        boolean flag2 = CommandException.method_d87039b07bd6961907f2();
        byte b0 = 1;
        MinecraftServer server = MinecraftServer.getServer();
        boolean flag = flag2;
        if (!commandSender.sendCommandFeedback()) {
            b0 = 0;
        }

        ChatComponentTranslation chatComponentTranslation =
                new ChatComponentTranslation(
                        "chat.type.admin",
                        new Object[] {
                            commandSender.getName(), new ChatComponentTranslation(text, aobject)
                        });
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.GRAY);
        chatComponentTranslation.getChatStyle().setItalic(true);
        if (b0 != 0) {
            for (java.util.Iterator<?> __recoveryIterator171_2318 =
                            (server.getConfigurationManager().playerEntityList).iterator();
                    (__recoveryIterator171_2318).hasNext(); ) {
                PlayerEntity playerEntity = (PlayerEntity) (__recoveryIterator171_2318).next();
                if (playerEntity != commandSender
                        && server.getConfigurationManager()
                                .canSendCommands(playerEntity.getGameProfile())
                        && iCommand.canCommandSenderUseCommand(commandSender)) {
                    playerEntity.addChatMessage(chatComponentTranslation);
                }

                if (flag) {
                    break;
                }
            }
        }

        if (commandSender != server
                && server.worldServers[0]
                        .getGameRules()
                        .getGameRuleBooleanValue("logAdminCommands")) {
            server.addChatMessage(chatComponentTranslation);
        }

        boolean gameRuleBooleanValue =
                server.worldServers[0]
                        .getGameRules()
                        .getGameRuleBooleanValue("sendCommandFeedback");
        if (commandSender instanceof CommandBlockLogic) {
            gameRuleBooleanValue = ((CommandBlockLogic) commandSender).shouldTrackOutput();
        }

        if ((i & 1) != 1 && gameRuleBooleanValue) {
            commandSender.addChatMessage(new ChatComponentTranslation(text, aobject));
        }
    }

    public ServerCommandManager() {
        label13:
        {
            this.registerCommand(new CommandTime());
            this.registerCommand(new CommandGameMode());
            this.registerCommand(new CommandDifficulty());
            this.registerCommand(new CommandDefaultGameMode());
            this.registerCommand(new CommandKill());
            this.registerCommand(new CommandToggleDownfall());
            this.registerCommand(new CommandWeather());
            boolean flag1 = CommandException.method_d87039b07bd6961907f2();
            this.registerCommand(new CommandXP());
            this.registerCommand(new CommandTeleport());
            this.registerCommand(new CommandGive());
            this.registerCommand(new CommandReplaceItem());
            this.registerCommand(new CommandStats());
            this.registerCommand(new CommandEffect());
            this.registerCommand(new CommandEnchant());
            this.registerCommand(new CommandParticle());
            this.registerCommand(new CommandEmote());
            this.registerCommand(new CommandShowSeed());
            this.registerCommand(new CommandHelp());
            this.registerCommand(new CommandDebug());
            this.registerCommand(new CommandMessage());
            this.registerCommand(new CommandBroadcast());
            this.registerCommand(new CommandSetSpawnpoint());
            this.registerCommand(new CommandSetDefaultSpawnpoint());
            this.registerCommand(new CommandGameRule());
            this.registerCommand(new CommandClearInventory());
            this.registerCommand(new CommandTestFor());
            this.registerCommand(new CommandSpreadPlayers());
            this.registerCommand(new CommandPlaySound());
            this.registerCommand(new CommandScoreboard());
            this.registerCommand(new CommandExecuteAt());
            this.registerCommand(new CommandTrigger());
            this.registerCommand(new CommandAchievement());
            this.registerCommand(new CommandSummon());
            this.registerCommand(new CommandSetBlock());
            this.registerCommand(new CommandFill());
            this.registerCommand(new CommandClone());
            this.registerCommand(new CommandCompare());
            boolean flag = flag1;
            this.registerCommand(new CommandBlockData());
            this.registerCommand(new CommandTestForBlock());
            this.registerCommand(new CommandMessageRaw());
            this.registerCommand(new CommandWorldBorder());
            this.registerCommand(new CommandTitle());
            this.registerCommand(new CommandEntityData());
            if (MinecraftServer.getServer().isDedicatedServer()) {
                this.registerCommand(new CommandOp());
                this.registerCommand(new CommandDeOp());
                this.registerCommand(new CommandStop());
                this.registerCommand(new CommandSaveAll());
                this.registerCommand(new CommandSaveOff());
                this.registerCommand(new CommandSaveOn());
                this.registerCommand(new CommandBanIp());
                this.registerCommand(new CommandPardonIp());
                this.registerCommand(new CommandBanPlayer());
                this.registerCommand(new CommandListBans());
                this.registerCommand(new CommandPardonPlayer());
                this.registerCommand(new CommandServerKick());
                this.registerCommand(new CommandListPlayers());
                this.registerCommand(new CommandWhitelist());
                this.registerCommand(new CommandSetPlayerTimeout());
                if (!flag) {
                    break label13;
                }

                Entity.setLegacyGlobalFlowMarker(new String[2]);
            }

            this.registerCommand(new CommandPublishLocalServer());
        }

        CommandBase.setAdminCommander(this);
    }


}
