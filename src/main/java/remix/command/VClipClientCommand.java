package remix.command;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.network.play.client.PlayerPositionPacket;
import remix.command.AbstractClientCommand;
import remix.command.CommandAction;

import org.apache.commons.lang3.math.NumberUtils;

public final class VClipClientCommand extends AbstractClientCommand {


    public String getUsage() {
        return "vclip <amount>";
    }

    public VClipClientCommand() {
        super("VClip", new String[] {"vclip", "vc"});
        this.addAction(
                CommandAction.requiringArguments(
                        2,
                        astring -> {
                            boolean flag1 = TargetsClientCommand.usesLegacyTargetFlow();
                            LocalPlayer player = ec.player;
                            boolean flag = flag1;
                            if (astring.length == 2) {
                                String text = astring[1];
                                if (NumberUtils.isNumber(text)) {
                                    double d1 = Double.parseDouble(text);
                                    player.setPosition(player.posX, player.posY + d1, player.posZ);
                                    return true;
                                }

                                if (!flag) {
                                    return false;
                                }
                            }

                            if (astring.length == 3) {
                                String textValue = astring[1];
                                if (NumberUtils.isNumber(textValue)) {
                                    double d0 = Double.parseDouble(textValue);
                                    player.setPosition(player.posX, player.posY + d0, player.posZ);
                                    ec.getNetworkHandler()
                                            .sendPacketNoEvent(
                                                    new PlayerPositionPacket(
                                                            player.posX,
                                                            player.posY,
                                                            player.posZ,
                                                            true));
                                    ec.getNetworkHandler()
                                            .sendPacketNoEvent(
                                                    new PlayerPositionPacket(
                                                            Double.POSITIVE_INFINITY,
                                                            Double.POSITIVE_INFINITY,
                                                            Double.POSITIVE_INFINITY,
                                                            true));
                                    return true;
                                }
                            }

                            return false;
                        }));
    }
}
