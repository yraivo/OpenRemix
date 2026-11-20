package remix.command;

import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.util.Vec3;
import remix.command.AbstractClientCommand;
import remix.command.CommandAction;
import remix.util.movement.MovementPhysicsHelper;

import org.apache.commons.lang3.math.NumberUtils;

public final class HClipClientCommand extends AbstractClientCommand {


    public HClipClientCommand() {
        super("HClip", new String[] {"hclip", "hc"});
        this.addAction(
                CommandAction.requiringArguments(
                        2,
                        astring -> {
                            String text = astring[1];
                            if (NumberUtils.isNumber(text)) {
                                double d0 = Double.parseDouble(text);
                                Vec3 vec3 =
                                        MovementPhysicsHelper.offsetDirectional(
                                                ec.player.getPositionVector(), d0);
                                ec.player.setPosition(vec3.x, vec3.y, vec3.z);
                                if (astring.length == 3) {
                                    ec.getNetworkHandler()
                                            .sendPacketNoEvent(
                                                    new PlayerPositionPacket(
                                                            ec.player.posX,
                                                            ec.player.posY - 0.005,
                                                            ec.player.posZ,
                                                            true));
                                }
                            }

                            return true;
                        }));
    }

    public String getUsage() {
        return "hclip <amount>";
    }
}
