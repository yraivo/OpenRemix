package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandBaseCoordinateArg;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S08PacketPlayerPosLookEnumFlags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import java.util.EnumSet;
import java.util.List;

public class CommandTeleport extends CommandBase {

    private static String[] packedTextFragments;

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.tp.usage";
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        Entity[] entityArray = CommandBlockLogic.GU();
        if (astring.length < 1) {
            throw new WrongUsageException("commands.tp.usage", new Object[0]);
        }

        byte b0;
        Object object;
        {
            b0 = 0;
            if (astring.length != 2 && astring.length != 4 && astring.length != 6) {
                object = getCommandSenderAsPlayer(commandSender);
                if (!(entityArray == null)) {

                    object = method_824f938e7a5e1aaa9e04(commandSender, astring[0]);
                    b0 = 1;
                }
            } else {

                object = method_824f938e7a5e1aaa9e04(commandSender, astring[0]);
                b0 = 1;
            }
        }

        if (astring.length != 1 && astring.length != 2) {
            if (astring.length < b0 + 3) {
                throw new WrongUsageException("commands.tp.usage", new Object[0]);
            }

            if (((Entity) object).world == null) {
                return;
            }

            CommandBaseCoordinateArg commandBaseCoordinateArg;
            CommandBaseCoordinateArg commandBaseCoordinateArgValue;
            CommandBaseCoordinateArg commandBaseCoordinateArgValue2;
            label126:
            {
                int i = b0 + 1;
                commandBaseCoordinateArg =
                        parseCoordinate(((Entity) object).posX, astring[b0], true);
                commandBaseCoordinateArgValue =
                        parseCoordinate(
                                ((Entity) object).posY, astring[i++], 0, 0, false);
                commandBaseCoordinateArgValue2 =
                        parseCoordinate(((Entity) object).posZ, astring[i++], true);
                CommandBaseCoordinateArg commandBaseCoordinateArgValue3 =
                        parseCoordinate(
                                ((Entity) object).rotationYaw,
                                astring.length > i ? astring[i++] : packedTextFragments[0],
                                false);
                CommandBaseCoordinateArg commandBaseCoordinateArgValue4 =
                        parseCoordinate(
                                ((Entity) object).rotationPitch,
                                astring.length > i ? astring[i] : packedTextFragments[1],
                                false);
                if (object instanceof EntityPlayerMP) {
                    EnumSet enumset = EnumSet.noneOf(S08PacketPlayerPosLookEnumFlags.class);
                    if (commandBaseCoordinateArg.isRelative()) {
                        enumset.add(S08PacketPlayerPosLookEnumFlags.RELATIVE_X);
                    }

                    if (commandBaseCoordinateArgValue.isRelative()) {
                        enumset.add(S08PacketPlayerPosLookEnumFlags.RELATIVE_Y);
                    }

                    if (commandBaseCoordinateArgValue2.isRelative()) {
                        enumset.add(S08PacketPlayerPosLookEnumFlags.RELATIVE_Z);
                    }

                    if (commandBaseCoordinateArgValue4.isRelative()) {
                        enumset.add(S08PacketPlayerPosLookEnumFlags.RELATIVE_PITCH);
                    }

                    if (commandBaseCoordinateArgValue3.isRelative()) {
                        enumset.add(S08PacketPlayerPosLookEnumFlags.RELATIVE_YAW);
                    }

                    float f = (float) commandBaseCoordinateArgValue3.cl();
                    if (!commandBaseCoordinateArgValue3.isRelative()) {
                        f = MathHelper.wrapAngleTo180(f);
                    }

                    float f1 = (float) commandBaseCoordinateArgValue4.cl();
                    if (!commandBaseCoordinateArgValue4.isRelative()) {
                        f1 = MathHelper.wrapAngleTo180(f1);
                    }

                    if (f1 > 90.0F || f1 < -90.0F) {
                        f1 = MathHelper.wrapAngleTo180(180.0F - f1);
                        f = MathHelper.wrapAngleTo180(f + 180.0F);
                    }

                    ((Entity) object).mountEntity((Entity) null);
                    ((EntityPlayerMP) object)
                            .playerNetServerHandler.setPlayerLocation(
                                    commandBaseCoordinateArg.cl(),
                                    commandBaseCoordinateArgValue.cl(),
                                    commandBaseCoordinateArgValue2.cl(),
                                    f,
                                    f1,
                                    enumset);
                    ((Entity) object).setRotationYawHead(f);
                    if (entityArray == null) {
                        break label126;
                    }
                }

                float f3 = (float) MathHelper.wrapAngleTo180(commandBaseCoordinateArgValue3.fh());
                float f2 = (float) MathHelper.wrapAngleTo180(commandBaseCoordinateArgValue4.fh());
                if (f2 > 90.0F || f2 < -90.0F) {
                    f2 = MathHelper.wrapAngleTo180(180.0F - f2);
                    f3 = MathHelper.wrapAngleTo180(f3 + 180.0F);
                }

                ((Entity) object)
                        .setLocationAndAngles(
                                commandBaseCoordinateArg.fh(),
                                commandBaseCoordinateArgValue.fh(),
                                commandBaseCoordinateArgValue2.fh(),
                                f3,
                                f2);
                ((Entity) object).setRotationYawHead(f3);
            }

            notifyOperators(
                    commandSender,
                    this,
                    "commands.tp.success.coordinates",
                    new Object[] {
                        ((Entity) object).getName(),
                        commandBaseCoordinateArg.fh(),
                        commandBaseCoordinateArgValue.fh(),
                        commandBaseCoordinateArgValue2.fh()
                    });
            if (entityArray == null) {
                return;
            }
        }

        Entity entity = method_824f938e7a5e1aaa9e04(commandSender, astring[astring.length - 1]);
        if (entity.world != ((Entity) object).world) {
            throw new CommandException("commands.tp.notSameDimension", new Object[0]);
        }

        {
            ((Entity) object).mountEntity((Entity) null);
            if (object instanceof EntityPlayerMP) {
                ((EntityPlayerMP) object)
                        .playerNetServerHandler.setPlayerLocation(
                                entity.posX,
                                entity.posY,
                                entity.posZ,
                                entity.rotationYaw,
                                entity.rotationPitch);
                if (!(entityArray == null)) {

                    ((Entity) object)
                            .setLocationAndAngles(
                                    entity.posX,
                                    entity.posY,
                                    entity.posZ,
                                    entity.rotationYaw,
                                    entity.rotationPitch);
                }
            } else {

                ((Entity) object)
                        .setLocationAndAngles(
                                entity.posX,
                                entity.posY,
                                entity.posZ,
                                entity.rotationYaw,
                                entity.rotationPitch);
            }
        }

        notifyOperators(
                commandSender,
                this,
                "commands.tp.success",
                new Object[] {((Entity) object).getName(), entity.getName()});
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandName() {
        return "tp";
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return astring.length != 1 && astring.length != 2
                ? null
                : getListOfStringsMatchingLastWord(
                        astring, MinecraftServer.getServer().getAllUsernames());
    }

    static {
        net.minecraft.command.server.CommandTeleport.packedTextFragments = new String[4];
        net.minecraft.command.server.CommandTeleport.packedTextFragments[0] = new String("~");
        net.minecraft.command.server.CommandTeleport.packedTextFragments[1] = new String("~");
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }
}
