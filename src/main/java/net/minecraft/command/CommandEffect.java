package net.minecraft.command;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

import java.util.List;

public class CommandEffect extends CommandBase {

    public String getCommandName() {
        return "effect";
    }

    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    @Override
    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(astring, this.getAllUsernames())
                : (astring.length == 2
                        ? getListOfStringsMatchingLastWord(astring, Potion.y())
                        : (astring.length == 5
                                ? getListOfStringsMatchingLastWord(astring, "true", "false")
                                : null));
    }


    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_b417d7500ec59d69e373();
        if (astring.length < 2) {
            throw new WrongUsageException("commands.effect.usage", new Object[0]);
        }

        LivingEntity livingEntity =
                (LivingEntity) getEntity(commandSender, astring[0], LivingEntity.class);
        if (astring[1].equals("clear")) {
            if (livingEntity.getActivePotionEffects().isEmpty()) {
                throw new CommandException(
                        "commands.effect.failure.notActive.all",
                        new Object[] {livingEntity.getName()});
            }

            livingEntity.nD();
            notifyOperators(
                    commandSender,
                    this,
                    "commands.effect.success.removed.all",
                    livingEntity.getName());
            if (flag) {
                return;
            }
        }

        int i;
        try {
            i = parseInt(astring[1], 1);
        } catch (NumberInvalidException numberInvalidException) {
            Potion potion = Potion.getPotionFromResourceLocation(astring[1]);
            if (potion == null) {
                throw numberInvalidException;
            }

            i = potion.id;
        }

        int j = 600;
        int l = 30;
        int k = 0;
        if (i >= 0 && i < Potion.potionTypes.length && Potion.potionTypes[i] != null) {
            Potion potionValue;
            label84:
            {
                potionValue = Potion.potionTypes[i];
                if (astring.length >= 3) {
                    l = parseInt(astring[2], 0, 1000000);
                    if (potionValue.isInstant()) {
                        j = l;
                        if (flag) {
                            break label84;
                        }
                    }

                    j = l * 20;
                    if (flag) {
                        break label84;
                    }
                }

                if (potionValue.isInstant()) {
                    j = 1;
                }
            }

            if (astring.length >= 4) {
                k = parseInt(astring[3], 0, 255);
            }

            byte b0 = 1;
            if (astring.length >= 5 && "true".equalsIgnoreCase(astring[4])) {
                b0 = 0;
            }

            label101:
            {
                if (l > 0) {
                    PotionEffect potionEffect =
                            new PotionEffect(
                                    i,
                                    j,
                                    k,
                                    false,
                                    remix.compat.compiler.JvmBooleanSlots.fromInt(b0));
                    livingEntity.addPotionEffect(potionEffect);
                    notifyOperators(
                            commandSender,
                            this,
                            "commands.effect.success",
                            new ChatComponentTranslation(potionEffect.getEffectName()),
                            i,
                            k,
                            livingEntity.getName(),
                            l);
                    if (flag) {
                        break label101;
                    }
                }

                if (!livingEntity.isPotionActive(i)) {
                    throw new CommandException(
                            "commands.effect.failure.notActive",
                            new Object[] {
                                new ChatComponentTranslation(potionValue.getName()),
                                livingEntity.getName()
                            });
                }

                livingEntity.removePotionEffect(i);
                notifyOperators(
                        commandSender,
                        this,
                        "commands.effect.success.removed",
                        new ChatComponentTranslation(potionValue.getName()),
                        livingEntity.getName());
                if (!flag) {
                    throw new CommandException(
                            "commands.effect.failure.notActive",
                            new Object[] {
                                new ChatComponentTranslation(potionValue.getName()),
                                livingEntity.getName()
                            });
                }
            }

            if (flag) {
                return;
            }
        }

        throw new NumberInvalidException("commands.effect.notFound", new Object[] {i});
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.effect.usage";
    }

    private static NumberInvalidException method_9f78e2366144040b606d(
            NumberInvalidException numberInvalidException) {
        return numberInvalidException;
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }
}
