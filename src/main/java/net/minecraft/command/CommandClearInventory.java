package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

import java.util.List;

public class CommandClearInventory extends CommandBase {

    public int getRequiredPermissionLevel() {
        return 2;
    }

    protected String[] y() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.clear.usage";
    }

    public String getCommandName() {
        return "clear";
    }

    private static NBTException method_63611f3fd5128a257bff(
            NBTException nbtException) {
        return nbtException;
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_b417d7500ec59d69e373();
        EntityPlayerMP entityPlayerMP =
                astring.length == 0
                        ? getCommandSenderAsPlayer(commandSender)
                        : getPlayer(commandSender, astring[0]);
        Item item =
                astring.length >= 2 ? getItemByText(commandSender, astring[1]) : null;
        int i = astring.length >= 3 ? parseInt(astring[2], -1) : -1;
        int j = astring.length >= 4 ? parseInt(astring[3], -1) : -1;
        CompoundTag compoundTag = null;
        if (astring.length >= 5) {
            try {
                compoundTag =
                        JsonToNBT.getTagFromJson(buildString(astring, 4));
            } catch (NBTException nbtException) {
                throw new CommandException(
                        "commands.clear.tagError",
                        new Object[] {nbtException.getMessage()});
            }
        }

        if (astring.length >= 2 && item == null) {
            throw new CommandException(
                    "commands.clear.failure", new Object[] {entityPlayerMP.getName()});
        }

        int k = entityPlayerMP.inventory.clearMatchingItems(item, i, j, compoundTag);
        entityPlayerMP.inventoryContainer.detectAndSendChanges();
        if (!entityPlayerMP.capabilities.isCreativeMode) {
            entityPlayerMP.updateHeldItem();
        }

        commandSender.setCommandStat(CommandResultStatsType.AFFECTED_ITEMS, k);
        if (k == 0) {
            throw new CommandException(
                    "commands.clear.failure", new Object[] {entityPlayerMP.getName()});
        }

        if (j == 0) {
            commandSender.addChatMessage(
                    new ChatComponentTranslation(
                            "commands.clear.testing", new Object[] {entityPlayerMP.getName(), k}));
            if (flag) {
                return;
            }
        }

        notifyOperators(
                commandSender,
                this,
                "commands.clear.success",
                new Object[] {entityPlayerMP.getName(), k});
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(astring, this.y())
                : (astring.length == 2
                        ? getListOfStringsMatchingLastWord(astring, Item.itemRegistry.getKeys())
                        : null);
    }


}
