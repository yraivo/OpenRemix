package remix.command;

import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentText;
import remix.command.AbstractClientCommand;
import remix.command.ClientCommand;
import remix.client.ClientServices;

import com.mojang.realmsclient.gui.ChatFormatting;

public final class ChatCommandOutput implements ClientCommandOutput {

    private static String[] packedTextFragments;

    public void printDebugMessage(String text, Object... object) {}

    public void printRawMessage(String text, Object... object) {
        int j = AbstractClientCommand.getStandardCommandFlowToken();
        ClientCommand.ec.player.addChatMessage(
                new ChatComponentText(
                        packedTextFragments[1]
                                + ClientServices.CLIENT_NAME
                                + ChatFormatting.RESET
                                + "] "
                                + text));
        int i = j;
        if (i == 0) {
            Entity.setLegacyGlobalFlowMarker(new String[1]);
        }
    }

    public void printMessage(String text, Object... object) {
        if (ClientCommand.ec.player != null) {
            ClientCommand.ec.player.addChatMessage(
                    new ChatComponentText(
                            ChatFormatting.DARK_GRAY
                                    + packedTextFragments[0]
                                    + ChatFormatting.GREEN
                                    + ClientServices.CLIENT_NAME
                                    + ChatFormatting.RESET
                                    + ChatFormatting.DARK_GRAY
                                    + "] "
                                    + ChatFormatting.WHITE
                                    + text));
        }
    }

    static {
        remix.command.ChatCommandOutput.packedTextFragments = new String[3];
        remix.command.ChatCommandOutput.packedTextFragments[0] = new String("[");
        remix.command.ChatCommandOutput.packedTextFragments[1] = new String("[");
    }
}
