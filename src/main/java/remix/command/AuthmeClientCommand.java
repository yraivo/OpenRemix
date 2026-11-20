package remix.command;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import remix.command.AbstractClientCommand;
import remix.command.CommandAction;

import io.netty.buffer.Unpooled;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class AuthmeClientCommand extends AbstractClientCommand {

    public String getUsage() {
        return "authme";
    }

    public AuthmeClientCommand() {
        super("Authme", new String[] {"AuthMe", "am"});
        this.addAction(
                CommandAction.requiringArguments(
                        1,
                        astring -> {
                            ByteArrayOutputStream bytearrayoutputstream =
                                    new ByteArrayOutputStream();
                            DataOutputStream dataoutputstream =
                                    new DataOutputStream(bytearrayoutputstream);

                            try {
                                dataoutputstream.writeUTF("AutoLogin");
                                dataoutputstream.writeUTF(ec.getSession().getUsername());
                            } catch (IOException ioexception) {
                                return false;
                            }

                            PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                            packetBuffer.writeBytes(bytearrayoutputstream.toByteArray());
                            ec.player.connection.sendPacketNoEvent(
                                    new C17PacketCustomPayload("BAuthMeBridge", packetBuffer));
                            ClientCommandManager.printMessage("Done!");
                            return true;
                        }));
    }


}
