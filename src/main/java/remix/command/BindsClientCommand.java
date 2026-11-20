package remix.command;

import remix.command.AbstractClientCommand;
import remix.command.CommandAction;
import remix.module.ModuleBase;
import remix.module.ModuleManager;

import com.mojang.realmsclient.gui.ChatFormatting;

import org.lwjgl.input.Keyboard;

public final class BindsClientCommand extends AbstractClientCommand {
    private static String[] packedTextFragments;

    public String getUsage() {
        return "binds";
    }

    public BindsClientCommand() {
        super("Binds", new String[] {"Binds"});
        this.addAction(
                CommandAction.requiringArguments(
                        1,
                        astring -> {
                            boolean flag1 = TargetsClientCommand.usesLegacyTargetFlow();
                            ClientCommandManager.printMessage(
                                    packedTextFragments[0]
                                            + ChatFormatting.GREEN
                                            + ChatFormatting.BOLD
                                            + "-------------------");
                            ClientCommandManager.printMessage(
                                    packedTextFragments[1]
                                            + ChatFormatting.GREEN
                                            + ChatFormatting.BOLD
                                            + "|          Binds          |");
                            ClientCommandManager.printMessage(
                                    packedTextFragments[2]
                                            + ChatFormatting.GREEN
                                            + ChatFormatting.BOLD
                                            + "-------------------");
                            boolean flag = flag1;

                            for (ModuleBase moduleBase : ModuleManager.modules) {
                                ClientCommandManager.printMessage(
                                        moduleBase.getDisplayName()
                                                + " - "
                                                + Keyboard.getKeyName(moduleBase.getKeyBind()));
                                if (flag) {
                                    break;
                                }
                            }

                            ClientCommandManager.printMessage(
                                    packedTextFragments[3]
                                            + ChatFormatting.GREEN
                                            + ChatFormatting.BOLD
                                            + "-------------------");
                            return true;
                        }));
    }

    static {
        remix.command.BindsClientCommand.packedTextFragments = new String[6];
        remix.command.BindsClientCommand.packedTextFragments[0] = new String("");
        remix.command.BindsClientCommand.packedTextFragments[1] = new String("");
        remix.command.BindsClientCommand.packedTextFragments[2] = new String("");
        remix.command.BindsClientCommand.packedTextFragments[3] = new String("");
    }
}
