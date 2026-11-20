package net.minecraft.client.gui.spectator.categories;

import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;

import java.util.List;

public class SpectatorDetails {
    private int c;
    private List uQ;
    private ISpectatorMenuView auO;

    public ISpectatorMenuObject T(int i) {
        return i >= 0 && i < this.uQ.size()
                ? (ISpectatorMenuObject)
                        com.google.common.base.MoreObjects.firstNonNull(
                                this.uQ.get(i), SpectatorMenu.auD)
                : SpectatorMenu.auD;
    }

    public int method_9b417d27949cba33a0d0() {
        return this.c;
    }

    public SpectatorDetails(ISpectatorMenuView auOValue, List list, int i) {
        this.auO = auOValue;
        this.uQ = list;
        this.c = i;
    }
}
