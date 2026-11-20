package net.minecraft.client.gui.spectator;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.spectator.categories.TeleportToPlayer;
import net.minecraft.client.gui.spectator.categories.TeleportToTeam;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class BaseSpectatorGroup implements ISpectatorMenuView {
    private static String[] packedTextFragments;
    private static String b;
    private List dB;

    public List i() {
        return this.dB;
    }

    static {
        net.minecraft.client.gui.spectator.BaseSpectatorGroup.packedTextFragments = new String[2];
        net.minecraft.client.gui.spectator.BaseSpectatorGroup.packedTextFragments[0] =
                new String("Fehhpb");
        net.minecraft.client.gui.spectator.BaseSpectatorGroup.packedTextFragments[1] =
                new String(
                        "\u007fZ\n"
                            + "h\u0017Q;\u000fC\n"
                            + "bD\u00055\u000f[\n"
                            + "w\u0001\u0012.\u000fIOx\u000b\u001c7NF\u000b7D\u00104K\b\u000e|\u0005\u00184\u000f\\\u0000;\u0011\u0002?\u000fA\u001b5");
        b = "Press a key to select a command, and again to use it.";
    }

    // region String decoding support

    // endregion

    public BaseSpectatorGroup() {
        super();
        SpectatorMenu.cI();

        this.dB = Lists.newArrayList();
        this.dB.add(new TeleportToPlayer());
        this.dB.add(new TeleportToTeam());
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            SpectatorMenu.b(packedTextFragments[0]);
        }
    }

    // region String decoding support

    // endregion

    public ChatComponent vT() {
        return new ChatComponentText(b);
    }

    // region String decoding support

    // endregion
}
