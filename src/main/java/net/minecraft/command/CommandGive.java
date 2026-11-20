package net.minecraft.command;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandGive extends CommandBase {

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(astring, this.getPlayers())
                : (astring.length == 2
                        ? getListOfStringsMatchingLastWord(astring, Item.itemRegistry.getKeys())
                        : null);
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.give.usage";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_b417d7500ec59d69e373();

        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (astring.length < 2) {
                throw new WrongUsageException("commands.give.usage", new Object[0]);
            }
        } catch (NBTException nbtException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtException));
        }

        EntityPlayerMP entityPlayerMP = getPlayer(commandSender, astring[0]);
        Item item = getItemByText(commandSender, astring[1]);

        int k;
        label90:
        {
            try {
                if (false)
                    throw (net.minecraft.nbt.NBTException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (astring.length >= 3) {
                    k = parseInt(astring[2], 1, 64);
                    break label90;
                }
            } catch (NBTException nbtExceptionValue) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_63611f3fd5128a257bff(nbtExceptionValue));
            }

            k = 1;
        }

        int i = k;

        label82:
        {
            try {
                if (false)
                    throw (net.minecraft.nbt.NBTException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (astring.length >= 4) {
                    k = parseInt(astring[3]);
                    break label82;
                }
            } catch (NBTException nbtExceptionValue2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_63611f3fd5128a257bff(nbtExceptionValue2));
            }

            k = 0;
        }

        int j = k;
        ItemStack itemStack = new ItemStack(item, i, j);
        if (astring.length >= 5) {
            String text =
                    getChatComponentFromNthArg(commandSender, astring, 4).getUnformattedText();

            try {
                itemStack.setTag(JsonToNBT.getTagFromJson(text));
            } catch (NBTException nbtExceptionValue3) {
                throw new CommandException(
                        "commands.give.tagError", new Object[] {nbtExceptionValue3.getMessage()});
            }
        }

        boolean flag1 = entityPlayerMP.inventory.addItemStackToInventory(itemStack);

        try {
            if (false)
                throw (net.minecraft.nbt.NBTException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (flag1) {
                entityPlayerMP.world.playSoundAtEntity(
                        entityPlayerMP,
                        "random.pop",
                        0.2F,
                        ((entityPlayerMP.getRNG().nextFloat() - entityPlayerMP.getRNG().nextFloat())
                                                * 0.7F
                                        + 1.0F)
                                * 2.0F);
                entityPlayerMP.inventoryContainer.detectAndSendChanges();
            }
        } catch (NBTException nbtExceptionValue4) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_63611f3fd5128a257bff(nbtExceptionValue4));
        }

        label101:
        {
            {
                boolean runGuardedCode;
                try {
                    if (false)
                        throw (net.minecraft.nbt.NBTException)
                                null; // Original JVM checked handler; no emitted instructions.
                    runGuardedCode = !(!flag1 || itemStack.stackSize > 0);
                } catch (NBTException nbtExceptionValue5) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(
                            method_63611f3fd5128a257bff(nbtExceptionValue5));
                }
                if (runGuardedCode) {

                    itemStack.stackSize = 1;
                    commandSender.setCommandStat(CommandResultStatsType.AFFECTED_ITEMS, i);
                    EntityItem entityItem =
                            entityPlayerMP.dropPlayerItemWithRandomChoice(itemStack, false);

                    try {
                        if (false)
                            throw (net.minecraft.nbt.NBTException)
                                    null; // Original JVM checked handler; no emitted instructions.
                        if (entityItem == null) {
                            break label101;
                        }

                        entityItem.func_174870_v();
                        if (flag) {
                            break label101;
                        }
                    } catch (NBTException nbtExceptionValue6) {
                        throw remix.compat.compiler.JvmThrowTypes.flow(
                                method_63611f3fd5128a257bff(nbtExceptionValue6));
                    }
                }
            }

            commandSender.setCommandStat(
                    CommandResultStatsType.AFFECTED_ITEMS, i - itemStack.stackSize);
            EntityItem entityItemValue =
                    entityPlayerMP.dropPlayerItemWithRandomChoice(itemStack, false);

            try {
                if (false)
                    throw (net.minecraft.nbt.NBTException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (entityItemValue != null) {
                    entityItemValue.setNoPickupDelay();
                    entityItemValue.setOwner(entityPlayerMP.getName());
                }
            } catch (NBTException nbtExceptionValue7) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_63611f3fd5128a257bff(nbtExceptionValue7));
            }
        }

        notifyOperators(
                commandSender,
                this,
                "commands.give.success",
                new Object[] {itemStack.getChatComponent(), i, entityPlayerMP.getName()});
    }

    private static NBTException method_63611f3fd5128a257bff(NBTException nbtException) {
        return nbtException;
    }


    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }

    public String getCommandName() {
        return "give";
    }

    protected String[] getPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }
}
