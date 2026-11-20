package net.minecraft.command.server;

import net.minecraft.command.server.CommandBlockLogic;

import java.util.concurrent.Callable;

public class CommandBlockLogicCommandCallable implements Callable {
    CommandBlockLogic aYD;

    @Override
    public Object call() {
        return this.method_e0e3092a59fee58fae52();
    }

    public CommandBlockLogicCommandCallable(CommandBlockLogic aYDValue) {
        this.aYD = aYDValue;
    }

    public String method_e0e3092a59fee58fae52() {
        return this.aYD.getCustomName();
    }
}
