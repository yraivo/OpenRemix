package net.minecraft.command;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

public class CommandParticle extends CommandBase {

    private static String[] packedTextFragments;

    @Override
    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 1
                ? getListOfStringsMatchingLastWord(astring, EnumParticleTypes.getParticleNames())
                : (astring.length > 1 && astring.length <= 4
                        ? method_cb1cf55a8a3937d053ae(astring, 1, blockPos)
                        : (astring.length == 9
                                ? getListOfStringsMatchingLastWord(astring, "normal", "force")
                                : null));
    }

    public String getCommandName() {
        return "particle";
    }

    private static NumberFormatException method_a0514df28616b2633822(
            NumberFormatException numberformatexception) {
        return numberformatexception;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_b417d7500ec59d69e373();
        if (astring.length < 8) {
            throw new WrongUsageException("commands.particle.usage", new Object[0]);
        }

        byte b0 = 0;
        EnumParticleTypes enumParticleTypes = null;

        for (EnumParticleTypes enumParticleTypesValue : EnumParticleTypes.values()) {
            if (enumParticleTypesValue.hasArguments()) {
                if (astring[0].startsWith(enumParticleTypesValue.getParticleName())) {
                    b0 = 1;
                    enumParticleTypes = enumParticleTypesValue;
                    break;
                }
            } else if (astring[0].equals(enumParticleTypesValue.getParticleName())) {
                b0 = 1;
                enumParticleTypes = enumParticleTypesValue;
                break;
            }
        }

        if (b0 == 0) {
            throw new CommandException("commands.particle.notFound", new Object[] {astring[0]});
        }

        String text = astring[0];
        Vec3 positionVector = commandSender.getPositionVector();
        double d0 = (float) parseDouble(positionVector.x, astring[1], true);
        double d1 = (float) parseDouble(positionVector.y, astring[2], true);
        double d2 = (float) parseDouble(positionVector.z, astring[3], true);
        double d3 = (float) parseDouble(astring[4]);
        double d4 = (float) parseDouble(astring[5]);
        double d5 = (float) parseDouble(astring[6]);
        double d6 = (float) parseDouble(astring[7]);
        int i = 0;
        if (astring.length > 8) {
            i = parseInt(astring[8], 0);
        }

        byte b1 = 0;
        if (astring.length > 9 && "force".equals(astring[9])) {
            b1 = 1;
        }

        World entityWorld = commandSender.getEntityWorld();
        if (entityWorld instanceof WorldServer) {
            WorldServer worldServer = (WorldServer) entityWorld;
            int[] aint = new int[enumParticleTypes.getArgumentCount()];
            if (enumParticleTypes.hasArguments()) {
                String[] astring1 = astring[0].split(packedTextFragments[0], 3);
                int j = 1;

                while (j < astring1.length) {
                    try {
                        aint[j - 1] = Integer.parseInt(astring1[j]);
                    } catch (NumberFormatException numberformatexception) {
                        throw new CommandException(
                                "commands.particle.notFound", new Object[] {astring[0]});
                    }

                    j++;
                    if (!flag) {
                        break;
                    }
                }
            }

            worldServer.spawnParticle(
                    enumParticleTypes,
                    remix.compat.compiler.JvmBooleanSlots.fromInt(b1),
                    d0,
                    d1,
                    d2,
                    i,
                    d3,
                    d4,
                    d5,
                    d6,
                    aint);
            notifyOperators(commandSender, this, "commands.particle.success", text, Math.max(i, 1));
        }
    }

    static {
        net.minecraft.command.CommandParticle.packedTextFragments = new String[3];
        net.minecraft.command.CommandParticle.packedTextFragments[0] = new String("_");
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.particle.usage";
    }
}
