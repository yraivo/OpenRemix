package net.minecraft.command;

public interface IAdminCommand {
    void notifyOperators(
            CommandSender commandSender,
            ICommand iCommand,
            int i,
            String text,
            Object... object);
}
