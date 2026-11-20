package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandTestFor extends CommandBase {

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.testfor.usage";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (astring.length < 1) {
                throw new WrongUsageException("commands.testfor.usage", new Object[0]);
            }
        } catch (NBTException nbtException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtException));
        }

        Entity entity = method_824f938e7a5e1aaa9e04(commandSender, astring[0]);
        CompoundTag compoundTag = null;
        if (astring.length >= 2) {
            try {
                compoundTag = JsonToNBT.getTagFromJson(buildString(astring, 1));
            } catch (NBTException nbtExceptionValue) {
                throw new CommandException(
                        "commands.testfor.tagError", new Object[] {nbtExceptionValue.getMessage()});
            }
        }

        if (compoundTag != null) {
            CompoundTag compoundTagValue = new CompoundTag();

            try {
                if (false)
                    throw (net.minecraft.nbt.NBTException)
                            null; // Original JVM checked handler; no emitted instructions.
                entity.writeToNBT(compoundTagValue);
                if (!CommandTestForBlock.method_b1e94d9ac0e51bb728b9(
                        compoundTag, compoundTagValue, true)) {
                    throw new CommandException(
                            "commands.testfor.failure", new Object[] {entity.getName()});
                }
            } catch (NBTException nbtExceptionValue2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_63611f3fd5128a257bff(nbtExceptionValue2));
            }
        }

        notifyOperators(
                commandSender, this, "commands.testfor.success", new Object[] {entity.getName()});
    }

    private static NBTException method_63611f3fd5128a257bff(NBTException nbtException) {
        return nbtException;
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(
                        astring, MinecraftServer.getServer().getAllUsernames())
                : null;
    }

    public String getCommandName() {
        return "testfor";
    }


}
