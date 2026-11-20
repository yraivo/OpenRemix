package net.minecraft.command;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.command.CommandExecuteAtProcessCommandICommandSender;

import java.util.List;

public class CommandExecuteAt extends CommandBase {

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        if (astring.length < 5) {
            throw new WrongUsageException("commands.execute.usage", new Object[0]);
        }

        Entity entity = getEntity(commandSender, astring[0], Entity.class);
        double d0 = parseDouble(entity.posX, astring[1], false);
        double d1 = parseDouble(entity.posY, astring[2], false);
        double d2 = parseDouble(entity.posZ, astring[3], false);
        BlockPos blockPos = new BlockPos(d0, d1, d2);
        byte b0 = 4;
        if ("detect".equals(astring[4]) && astring.length > 10) {
            World entityWorld = commandSender.getEntityWorld();
            double d3 = parseDouble(d0, astring[5], false);
            double d4 = parseDouble(d1, astring[6], false);
            double d5 = parseDouble(d2, astring[7], false);
            Block block = getBlockByText(commandSender, astring[8]);
            int i = parseInt(astring[9], -1, 15);
            BlockPos blockPosValue = new BlockPos(d3, d4, d5);
            BlockState blockState = entityWorld.getBlockState(blockPosValue);
            if (blockState.getBlock() != block
                    || i >= 0 && blockState.getBlock().getMetaFromState(blockState) != i) {
                throw new CommandException(
                        "commands.execute.failed", new Object[] {"detect", entity.getName()});
            }

            b0 = 10;
        }

        String text = buildString(astring, b0);
        CommandExecuteAtProcessCommandICommandSender commandExecuteAtProcessCommandICommandSender =
                new CommandExecuteAtProcessCommandICommandSender(this, entity, commandSender, blockPos, d0, d1, d2);
        ICommandManager commandManager = MinecraftServer.getServer().getCommandManager();

        try {
            int j = commandManager.executeCommand(commandExecuteAtProcessCommandICommandSender, text);
            if (j < 1) {
                throw new CommandException(
                        "commands.execute.allInvocationsFailed", new Object[] {text});
            }
        } catch (Throwable throwable) {
            throw new CommandException(
                    "commands.execute.failed", new Object[] {text, entity.getName()});
        }
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(
                        astring, MinecraftServer.getServer().getAllUsernames())
                : (astring.length > 1 && astring.length <= 4
                        ? method_cb1cf55a8a3937d053ae(astring, 1, blockPos)
                        : (astring.length > 5 && astring.length <= 8 && "detect".equals(astring[4])
                                ? method_cb1cf55a8a3937d053ae(astring, 5, blockPos)
                                : (astring.length == 9 && "detect".equals(astring[4])
                                        ? getListOfStringsMatchingLastWord(
                                                astring, Block.blockRegistry.getKeys())
                                        : null)));
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.execute.usage";
    }


    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public String getCommandName() {
        return "execute";
    }
}
