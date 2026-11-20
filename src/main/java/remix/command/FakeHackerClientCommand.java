package remix.command;

import net.minecraft.entity.player.PlayerEntity;
import remix.command.AbstractClientCommand;
import remix.command.CommandAction;
import remix.client.ClientServices;
import remix.client.RemixClient;

public final class FakeHackerClientCommand extends AbstractClientCommand {


    public String getUsage() {
        return "fh <name>";
    }

    public FakeHackerClientCommand() {
        super("FakeHacker", new String[] {"fh"});
        this.addAction(
                CommandAction.requiringArguments(
                        2,
                        astring -> {
                            String text = astring[1];
                            PlayerEntity playerEntity = null;

                            for (PlayerEntity playerEntityValue : ec.world.playerEntities) {
                                String name = playerEntityValue.getName();
                                if (name.equals(text)) {
                                    int i =
                                            playerEntityValue != ec.player
                                                            && !name.equals(ec.player.getName())
                                                    ? 0
                                                    : 1;
                                    if (i != 0) {
                                        ClientCommandManager.printMessage(
                                                "You can't target yourself! Dummy");
                                        return true;
                                    }

                                    playerEntity = playerEntityValue;
                                    break;
                                }
                            }

                            if (playerEntity == null) {
                                ClientCommandManager.printMessage(
                                        "Couldn't find anyone called \"" + text + "\" ");
                                return false;
                            } else {
                                ClientServices clientServices =
                                        (ClientServices) RemixClient.INSTANCE.getClientServices();
                                ClientServices.moduleManager.fakeHacker.selectTarget(playerEntity);
                                ClientCommandManager.printMessage(
                                        "Successfully set fake hacker target to \"" + text + "\" ");
                                return true;
                            }
                        }));
    }
}
