package net.minecraft.command;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandEnchant extends CommandBase {

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.enchant.usage";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_b417d7500ec59d69e373();

        try {
            if (false)
                throw (net.minecraft.command.NumberInvalidException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (astring.length < 2) {
                throw new WrongUsageException("commands.enchant.usage", new Object[0]);
            }
        } catch (NumberInvalidException numberInvalidException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_9f78e2366144040b606d(numberInvalidException));
        }

        EntityPlayerMP entityPlayerMP = getPlayer(commandSender, astring[0]);
        commandSender.setCommandStat(CommandResultStatsType.AFFECTED_ITEMS, 0);

        int i;
        try {
            i = parseInt(astring[1], 0);
        } catch (NumberInvalidException numberInvalidExceptionValue) {
            NumberInvalidException numberInvalidExceptionValue2 = numberInvalidExceptionValue;
            Enchantment enchantment = Enchantment.getEnchantmentByLocation(astring[1]);

            try {
                if (enchantment == null) {
                    throw numberInvalidExceptionValue2;
                }
            } catch (NumberInvalidException numberInvalidExceptionValue3) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_9f78e2366144040b606d(numberInvalidExceptionValue3));
            }

            i = enchantment.effectId;
        }

        int k = 1;
        ItemStack currentEquippedItem = entityPlayerMP.getCurrentEquippedItem();

        try {
            if (currentEquippedItem == null) {
                throw new CommandException("commands.enchant.noItem", new Object[0]);
            }
        } catch (NumberInvalidException numberInvalidExceptionValue4) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_9f78e2366144040b606d(numberInvalidExceptionValue4));
        }

        Enchantment enchantmentValue = Enchantment.getEnchantmentById(i);

        try {
            if (enchantmentValue == null) {
                throw new NumberInvalidException("commands.enchant.notFound", new Object[] {i});
            }
        } catch (NumberInvalidException numberInvalidExceptionValue5) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_9f78e2366144040b606d(numberInvalidExceptionValue5));
        }

        try {
            if (!enchantmentValue.canApply(currentEquippedItem)) {
                throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
            }
        } catch (NumberInvalidException numberInvalidExceptionValue6) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_9f78e2366144040b606d(numberInvalidExceptionValue6));
        }

        if (astring.length >= 3) {
            k =
                    parseInt(
                            astring[2],
                            enchantmentValue.getMinLevel(),
                            enchantmentValue.getMaxLevel());
        }

        if (currentEquippedItem.hasTagCompound()) {
            NBTTagList nbtTagList = currentEquippedItem.getEnchantmentTagList();
            if (nbtTagList != null) {
                int j = 0;

                while (j < nbtTagList.tagCount()) {
                    short short1 = nbtTagList.getCompoundTagAt(j).getShort("id");
                    if (Enchantment.getEnchantmentById(short1) != null) {
                        Enchantment enchantmentValue2 = Enchantment.getEnchantmentById(short1);

                        try {
                            if (!enchantmentValue2.canApplyTogether(enchantmentValue)) {
                                throw new CommandException(
                                        "commands.enchant.cantCombine",
                                        new Object[] {
                                            enchantmentValue.getTranslatedName(k),
                                            enchantmentValue2.getTranslatedName(
                                                    nbtTagList.getCompoundTagAt(j).getShort("lvl"))
                                        });
                            }
                        } catch (NumberInvalidException numberInvalidExceptionValue7) {
                            throw remix.compat.compiler.JvmThrowTypes.flow(
                                    method_9f78e2366144040b606d(numberInvalidExceptionValue7));
                        }
                    }

                    j++;
                    if (!flag) {
                        break;
                    }
                }
            }
        }

        currentEquippedItem.addEnchantment(enchantmentValue, k);
        notifyOperators(commandSender, this, "commands.enchant.success", new Object[0]);
        commandSender.setCommandStat(CommandResultStatsType.AFFECTED_ITEMS, 1);
    }

    private static NumberInvalidException method_9f78e2366144040b606d(
            NumberInvalidException numberInvalidException) {
        return numberInvalidException;
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }


    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }

    public String getCommandName() {
        return "enchant";
    }

    protected String[] getListOfPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(astring, this.getListOfPlayers())
                : (astring.length == 2
                        ? getListOfStringsMatchingLastWord(astring, Enchantment.y())
                        : null);
    }
}
