package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.List;

public class CommandPlaySound extends CommandBase {

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length == 2
                ? getListOfStringsMatchingLastWord(
                        astring, MinecraftServer.getServer().getAllUsernames())
                : (astring.length > 2 && astring.length <= 5
                        ? method_cb1cf55a8a3937d053ae(astring, 2, blockPos)
                        : null);
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 1;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.playsound.usage";
    }

    public String getCommandName() {
        return "playsound";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        if (astring.length < 2) {
            throw new WrongUsageException(
                    this.getCommandUsage(commandSender), new Object[0]);
        }

        byte b0 = 0;
        int i = b0 + 1;
        String text = astring[b0];
        EntityPlayerMP entityPlayerMP = getPlayer(commandSender, astring[i++]);
        Vec3 positionVector = commandSender.getPositionVector();
        double d0 = positionVector.x;
        if (astring.length > i) {
            d0 = parseDouble(d0, astring[i++], true);
        }

        double d1 = positionVector.y;
        if (astring.length > i) {
            d1 = parseDouble(d1, astring[i++], 0, 0, false);
        }

        double d2 = positionVector.z;
        if (astring.length > i) {
            d2 = parseDouble(d2, astring[i++], true);
        }

        double d3 = 1.0;
        if (astring.length > i) {
            d3 = parseDouble(astring[i++], 0.0, Float.MAX_VALUE);
        }

        double d4 = 1.0;
        if (astring.length > i) {
            d4 = parseDouble(astring[i++], 0.0, 2.0);
        }

        double d5 = 0.0;
        if (astring.length > i) {
            d5 = parseDouble(astring[i], 0.0, 1.0);
        }

        double d6 = d3 > 1.0 ? d3 * 16.0 : 16.0;
        double distance = entityPlayerMP.getDistance(d0, d1, d2);
        if (distance > d6) {
            if (d5 <= 0.0) {
                throw new CommandException(
                        "commands.playsound.playerTooFar", new Object[] {entityPlayerMP.getName()});
            }

            double d8 = d0 - entityPlayerMP.posX;
            double d9 = d1 - entityPlayerMP.posY;
            double d10 = d2 - entityPlayerMP.posZ;
            double d11 = Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
            if (d11 > 0.0) {
                d0 = entityPlayerMP.posX + d8 / d11 * 2.0;
                d1 = entityPlayerMP.posY + d9 / d11 * 2.0;
                d2 = entityPlayerMP.posZ + d10 / d11 * 2.0;
            }

            d3 = d5;
        }

        entityPlayerMP.playerNetServerHandler.sendPacket(
                new S29PacketSoundEffect(text, d0, d1, d2, (float) d3, (float) d4));
        notifyOperators(
                commandSender,
                this,
                "commands.playsound.success",
                new Object[] {text, entityPlayerMP.getName()});
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }


}
