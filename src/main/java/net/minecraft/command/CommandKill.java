package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandKill extends CommandBase {

    public String getCommandName() {
        return "kill";
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        boolean flag = CommandException.method_d87039b07bd6961907f2();
        if (astring.length == 0) {
            EntityPlayerMP entityPlayerMP = getCommandSenderAsPlayer(commandSender);
            entityPlayerMP.onKillCommand();
            notifyOperators(
                    commandSender,
                    this,
                    "commands.kill.successful",
                    new Object[] {entityPlayerMP.getDisplayName()});
            if (!flag) {
                return;
            }
        }

        Entity entity = method_824f938e7a5e1aaa9e04(commandSender, astring[0]);
        entity.onKillCommand();
        notifyOperators(
                commandSender,
                this,
                "commands.kill.successful",
                new Object[] {entity.getDisplayName()});
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.kill.usage";
    }

    public int getRequiredPermissionLevel() {
        return 2;
    }


}
