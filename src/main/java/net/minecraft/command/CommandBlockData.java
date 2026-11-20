package net.minecraft.command;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class CommandBlockData extends CommandBase {

    private static String[] packedTextFragments;

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length > 0 && astring.length <= 3
                ? method_cb1cf55a8a3937d053ae(astring, 0, blockPos)
                : null;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (astring.length < 4) {
                throw new WrongUsageException("commands.blockdata.usage", new Object[0]);
            }
        } catch (NBTException nbtException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtException));
        }

        commandSender.setCommandStat(CommandResultStatsType.AFFECTED_BLOCKS, 0);
        BlockPos blockPos = parseBlockPos(commandSender, astring, 0, false);
        World entityWorld = commandSender.getEntityWorld();

        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (!entityWorld.isBlockLoaded(blockPos)) {
                throw new CommandException("commands.blockdata.outOfWorld", new Object[0]);
            }
        } catch (NBTException nbtExceptionValue) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtExceptionValue));
        }

        TileEntity tileEntity = entityWorld.getTileEntity(blockPos);

        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (tileEntity == null) {
                throw new CommandException("commands.blockdata.notValid", new Object[0]);
            }
        } catch (NBTException nbtExceptionValue2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtExceptionValue2));
        }

        CompoundTag compoundTag = new CompoundTag();
        tileEntity.writeToNBT(compoundTag);
        CompoundTag compoundTagValue = (CompoundTag) compoundTag.copy();

        CompoundTag compoundTagValue2;
        try {
            compoundTagValue2 =
                    JsonToNBT.getTagFromJson(
                            getChatComponentFromNthArg(commandSender, astring, 3)
                                    .getUnformattedText());
        } catch (NBTException nbtExceptionValue3) {
            throw new CommandException(
                    "commands.blockdata.tagError", new Object[] {nbtExceptionValue3.getMessage()});
        }

        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            compoundTag.merge(compoundTagValue2);
            compoundTag.setInteger(packedTextFragments[0], blockPos.getX());
            compoundTag.setInteger(packedTextFragments[1], blockPos.getY());
            compoundTag.setInteger(packedTextFragments[2], blockPos.getZ());
            if (compoundTag.equals(compoundTagValue)) {
                throw new CommandException(
                        "commands.blockdata.failed", new Object[] {compoundTag.toString()});
            }
        } catch (NBTException nbtExceptionValue4) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtExceptionValue4));
        }

        tileEntity.readFromNBT(compoundTag);
        tileEntity.markDirty();
        entityWorld.markBlockForUpdate(blockPos);
        commandSender.setCommandStat(CommandResultStatsType.AFFECTED_BLOCKS, 1);
        notifyOperators(
                commandSender,
                this,
                "commands.blockdata.success",
                new Object[] {compoundTag.toString()});
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.blockdata.usage";
    }

    static {
        net.minecraft.command.CommandBlockData.packedTextFragments = new String[5];
        net.minecraft.command.CommandBlockData.packedTextFragments[0] = new String("x");
        net.minecraft.command.CommandBlockData.packedTextFragments[1] = new String("y");
        net.minecraft.command.CommandBlockData.packedTextFragments[2] = new String("z");
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandName() {
        return "blockdata";
    }

    private static NBTException method_63611f3fd5128a257bff(NBTException nbtException) {
        return nbtException;
    }
}
