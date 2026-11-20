package net.minecraft.client.gui.spectator;

import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.client.gui.spectator.SpectatorMenuISpectatorMenuObject1;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class SpectatorMenu {
    public static final ISpectatorMenuObject auD = new SpectatorMenuISpectatorMenuObject1();
    private int mu;
    private static String[] packedTextFragments;
    private int tB;
    private static final ISpectatorMenuObject auF = new SpectatorMenuMoveMenuObject(-1, true);
    private static final ISpectatorMenuObject auG = new SpectatorMenuMoveMenuObject(1, true);
    private static final ISpectatorMenuObject auH = new SpectatorMenuEndSpectatorObject(null);
    private static String vI;
    private final List vl = Lists.newArrayList();
    private ISpectatorMenuView auI = new BaseSpectatorGroup();
    private ISpectatorMenuReciepient auJ;
    private static final ISpectatorMenuObject auK = new SpectatorMenuMoveMenuObject(1, false);

    public void b(int i) {
        String text = cI();
        ISpectatorMenuObject iSpectatorMenuObject = this.T(i);
        String textValue = text;
        if (iSpectatorMenuObject != auD) {
            if (this.mu == i && iSpectatorMenuObject.method_b417d7500ec59d69e373()) {
                iSpectatorMenuObject.method_22ae0a8170d5c831fff3(this);
                if (textValue != null) {
                    return;
                }
            }

            this.mu = i;
        }
    }

    public int cR() {
        return this.mu;
    }

    public ISpectatorMenuObject vU() {
        return this.T(this.mu);
    }

    public SpectatorMenu(ISpectatorMenuReciepient auJValue) {
        this.mu = -1;
        this.auJ = auJValue;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        String s = packedTextFragments[0];
        b(s);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.gui.spectator.SpectatorMenu.packedTextFragments[0] = new String("tojXnc");
    }

    // endregion

    public SpectatorDetails vW() {
        return new SpectatorDetails(this.auI, this.i(), this.mu);
    }

    public static void b(String text) {
        vI = text;
    }

    public List i() {
        String text = cI();
        ArrayList arrayList = Lists.newArrayList();
        int i = 0;
        String textValue = text;

        while (i <= 8) {
            arrayList.add(this.T(i));
            i++;
            if (textValue == null) {
                break;
            }
        }

        return arrayList;
    }

    public void l() {
        this.auJ.method_22ae0a8170d5c831fff3(this);
    }

    static int access$112(SpectatorMenu spectatorMenu, int i) {
        return spectatorMenu.tB = i;
    }

    public ISpectatorMenuObject T(int i) {
        int j = i + this.tB * 6;
        return this.tB > 0 && i == 0
                ? auF
                : (i == 7
                        ? (j < this.auI.i().size() ? auG : auK)
                        : (i == 8
                                ? auH
                                : (j >= 0 && j < this.auI.i().size()
                                        ? (ISpectatorMenuObject)
                                                com.google.common.base.MoreObjects.firstNonNull(
                                                        this.auI.i().get(j), auD)
                                        : auD)));
    }

    public static String cI() {
        return vI;
    }

    // region String decoding support

    // endregion

    public ISpectatorMenuView vX() {
        return this.auI;
    }

    public void method_9f34d2c8942297ad165c(ISpectatorMenuView auIValue) {
        this.vl.add(this.vW());
        this.auI = auIValue;
        this.mu = -1;
        this.tB = 0;
    }
}
