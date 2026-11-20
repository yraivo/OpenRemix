package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.PlayerMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldSettingsGameType;

import java.util.Collection;
import java.util.List;

public class TeleportToPlayer implements ISpectatorMenuObject, ISpectatorMenuView {
    private List uQ;
    private static Ordering auP;

    public ChatComponent getSpectatorName() {
        return new ChatComponentText("Teleport to player");
    }

    public List i() {
        return this.uQ;
    }

    static {
        auP = Ordering.from(new PlayerInfoComparator());
    }

    public TeleportToPlayer() {
        this(auP.sortedCopy(MinecraftClient.getMinecraft().getNetworkHandler().getPlayerInfoMap()));
    }

    public ChatComponent vT() {
        return new ChatComponentText("Select a player to teleport to");
    }

    public void method_22ae0a8170d5c831fff3(SpectatorMenu spectatorMenu) {
        spectatorMenu.method_9f34d2c8942297ad165c(this);
    }

    public void method_8b0a960c841ff568c052(float f, int i) {
        MinecraftClient.getMinecraft().getTextureManager().bindTexture(GuiSpectator.sJ);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, 16, 16, 256.0F, 256.0F);
    }

    public boolean method_b417d7500ec59d69e373() {
        return !this.uQ.isEmpty();
    }

    public TeleportToPlayer(Collection collection) {
        super();
        int initialFlowToken = TeleportToTeam.method_7eec03da329a017ec143();

        int j = initialFlowToken;

        this.uQ = Lists.newArrayList();
        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_26649 =
                        (auP.sortedCopy(collection)).iterator();
                (__recoveryIterator171_26649).hasNext(); ) {
            PlayerInfo playerInfo = (PlayerInfo) (__recoveryIterator171_26649).next();
            if (playerInfo.getGameType() != WorldSettingsGameType.SPECTATOR) {
                this.uQ.add(new PlayerMenuObject(playerInfo.getGameProfile()));
            }

            if (i != 0) {
                break;
            }
        }
    }

    // Helper used only by this class.
    public static final class PlayerInfoComparator implements java.util.Comparator {
        public int method_892133e38a80d4f268f8(
                net.minecraft.client.network.PlayerInfo playerInfo,
                net.minecraft.client.network.PlayerInfo playerInfoValue) {
            return com.google.common.collect.ComparisonChain.start()
                    .compare(
                            playerInfo.getGameProfile().getId(),
                            playerInfoValue.getGameProfile().getId())
                    .result();
        }

        @java.lang.Override
        public int compare(java.lang.Object p171_0, java.lang.Object p171_1) {
            return this.method_892133e38a80d4f268f8(
                    (net.minecraft.client.network.PlayerInfo) p171_0,
                    (net.minecraft.client.network.PlayerInfo) p171_1);
        }
    }
}
