package net.minecraft.client.gui.spectator.categories;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

public class TeleportToTeam implements ISpectatorMenuObject, ISpectatorMenuView {
    private static int cz;
    private final List dB = Lists.newArrayList();

    public boolean method_b417d7500ec59d69e373() {
        for (java.util.Iterator<?> __recoveryIterator171_821 = (this.dB).iterator();
                (__recoveryIterator171_821).hasNext(); ) {
            ISpectatorMenuObject iSpectatorMenuObject =
                    (ISpectatorMenuObject) (__recoveryIterator171_821).next();
            if (iSpectatorMenuObject.method_b417d7500ec59d69e373()) {
                return true;
            }
        }

        return false;
    }

    public List i() {
        return this.dB;
    }

    public static void b(int i) {
        cz = i;
    }

    public ChatComponent getSpectatorName() {
        return new ChatComponentText("Teleport to team member");
    }

    // region String decoding support

    // endregion

    public void method_8b0a960c841ff568c052(float f, int i) {
        MinecraftClient.getMinecraft().getTextureManager().bindTexture(GuiSpectator.sJ);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 16.0F, 0.0F, 16, 16, 256.0F, 256.0F);
    }

    public static int method_beea3e16fe3ec0995eb9() {
        int i = method_7eec03da329a017ec143();
        return i == 0 ? 7 : 0;
    }

    static {
        b(0);
    }

    public void method_22ae0a8170d5c831fff3(SpectatorMenu spectatorMenu) {
        spectatorMenu.method_9f34d2c8942297ad165c(this);
    }

    // region String decoding support

    // endregion

    public TeleportToTeam() {
        int j = method_7eec03da329a017ec143();
        MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
        Iterator iterator = minecraftClient.world.getScoreboard().getTeams().iterator();
        int i = j;

        while (iterator.hasNext()) {
            ScorePlayerTeam scorePlayerTeam = (ScorePlayerTeam) iterator.next();
            this.dB.add(new TeleportToTeamTeamSelectionObject(this, scorePlayerTeam));
            if (i != 0) {
                break;
            }
        }
    }

    public ChatComponent vT() {
        return new ChatComponentText("Select a team to teleport to");
    }

    public static int method_7eec03da329a017ec143() {
        return cz;
    }
}
