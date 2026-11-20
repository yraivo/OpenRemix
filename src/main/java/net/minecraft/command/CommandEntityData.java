package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandEntityData extends CommandBase {

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (astring.length < 2) {
                throw new WrongUsageException("commands.entitydata.usage", new Object[0]);
            }
        } catch (NBTException nbtException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtException));
        }

        Entity entity = method_824f938e7a5e1aaa9e04(commandSender, astring[0]);

        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (entity instanceof PlayerEntity) {
                throw new CommandException(
                        "commands.entitydata.noPlayers", new Object[] {entity.getDisplayName()});
            }
        } catch (NBTException nbtExceptionValue) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtExceptionValue));
        }

        CompoundTag compoundTag = new CompoundTag();
        entity.writeToNBT(compoundTag);
        CompoundTag compoundTagValue = (CompoundTag) compoundTag.copy();

        CompoundTag compoundTagValue2;
        try {
            compoundTagValue2 =
                    JsonToNBT.getTagFromJson(
                            getChatComponentFromNthArg(commandSender, astring, 1)
                                    .getUnformattedText());
        } catch (NBTException nbtExceptionValue2) {
            throw new CommandException(
                    "commands.entitydata.tagError", new Object[] {nbtExceptionValue2.getMessage()});
        }

        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            compoundTagValue2.removeTag("UUIDMost");
            compoundTagValue2.removeTag("UUIDLeast");
            compoundTag.merge(compoundTagValue2);
            if (compoundTag.equals(compoundTagValue)) {
                throw new CommandException(
                        "commands.entitydata.failed", new Object[] {compoundTag.toString()});
            }
        } catch (NBTException nbtExceptionValue3) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtExceptionValue3));
        }

        entity.readFromNBT(compoundTag);
        notifyOperators(
                commandSender,
                this,
                "commands.entitydata.success",
                new Object[] {compoundTag.toString()});
    }

    private static NBTException method_63611f3fd5128a257bff(NBTException nbtException) {
        return nbtException;
    }

    public String getCommandName() {
        return "entitydata";
    }


    public int getRequiredPermissionLevel() {
        return 2;
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(
                        astring, MinecraftServer.getServer().getAllUsernames())
                : null;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.entitydata.usage";
    }
}
