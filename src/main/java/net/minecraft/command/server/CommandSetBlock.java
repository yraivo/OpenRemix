package net.minecraft.command.server;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStatsType;
import net.minecraft.command.CommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class CommandSetBlock extends CommandBase {

    private static String[] packedTextFragments;

    static {
        net.minecraft.command.server.CommandSetBlock.packedTextFragments = new String[5];
        net.minecraft.command.server.CommandSetBlock.packedTextFragments[0] = new String("x");
        net.minecraft.command.server.CommandSetBlock.packedTextFragments[1] = new String("y");
        net.minecraft.command.server.CommandSetBlock.packedTextFragments[2] = new String("z");
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length > 0 && astring.length <= 3
                ? method_cb1cf55a8a3937d053ae(astring, 0, blockPos)
                : (astring.length == 4
                        ? getListOfStringsMatchingLastWord(astring, Block.blockRegistry.getKeys())
                        : (astring.length == 6
                                ? getListOfStringsMatchingLastWord(
                                        astring, new String[] {"replace", "destroy", "keep"})
                                : null));
    }

    private static NBTException method_63611f3fd5128a257bff(
            NBTException nbtException) {
        return nbtException;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.setblock.usage";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandName() {
        return "setblock";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        if (astring.length < 4) {
            throw new WrongUsageException("commands.setblock.usage", new Object[0]);
        }

        commandSender.setCommandStat(CommandResultStatsType.AFFECTED_BLOCKS, 0);
        BlockPos blockPos = parseBlockPos(commandSender, astring, 0, false);
        Block block =
                CommandBase.getBlockByText(commandSender, astring[3]);
        int i = 0;
        if (astring.length >= 5) {
            i = parseInt(astring[4], 0, 15);
        }

        World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(blockPos)) {
            throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
        }

        CompoundTag compoundTag = new CompoundTag();
        byte b0 = 0;
        if (astring.length >= 7 && block.hasTileEntity()) {
            String text =
                    getChatComponentFromNthArg(commandSender, astring, 6).getUnformattedText();

            try {
                compoundTag = JsonToNBT.getTagFromJson(text);
                b0 = 1;
            } catch (NBTException nbtException) {
                throw new CommandException(
                        "commands.setblock.tagError",
                        new Object[] {nbtException.getMessage()});
            }
        }

        if (astring.length >= 6) {
            if (astring[5].equals("destroy")) {
                entityWorld.destroyBlock(blockPos, true);
                if (block == Blocks.air) {
                    notifyOperators(
                            commandSender, this, "commands.setblock.success", new Object[0]);
                    return;
                }
            } else if (astring[5].equals("keep") && !entityWorld.isAirBlock(blockPos)) {
                throw new CommandException("commands.setblock.noChange", new Object[0]);
            }
        }

        TileEntity tileEntity = entityWorld.getTileEntity(blockPos);
        if (tileEntity != null) {
            if (tileEntity instanceof IInventory) {
                ((IInventory) tileEntity).clearInventory();
            }

            entityWorld.setBlockState(
                    blockPos, Blocks.air.getDefaultState(), block == Blocks.air ? 2 : 4);
        }

        BlockState blockState = block.getStateFromMeta(i);
        if (!entityWorld.setBlockState(blockPos, blockState, 2)) {
            throw new CommandException("commands.setblock.noChange", new Object[0]);
        }

        if (b0 != 0) {
            TileEntity tileEntityValue = entityWorld.getTileEntity(blockPos);
            if (tileEntityValue != null) {
                compoundTag.setInteger(packedTextFragments[0], blockPos.getX());
                compoundTag.setInteger(packedTextFragments[1], blockPos.getY());
                compoundTag.setInteger(packedTextFragments[2], blockPos.getZ());
                tileEntityValue.readFromNBT(compoundTag);
            }
        }

        entityWorld.notifyNeighborsRespectDebug(blockPos, blockState.getBlock());
        commandSender.setCommandStat(CommandResultStatsType.AFFECTED_BLOCKS, 1);
        notifyOperators(
                commandSender, this, "commands.setblock.success", new Object[0]);
    }
}
