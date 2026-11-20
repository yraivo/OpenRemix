package net.minecraft.client.player.inventory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Container;
import net.minecraft.util.ChatComponent;
import net.minecraft.world.IInteractionObject;

public class LocalBlockIntercommunication implements IInteractionObject {
    private String guiID;
    private ChatComponent displayName;
    private static int c;

    public static void b(int i) {
        c = i;
    }

    public LocalBlockIntercommunication(String text, ChatComponent chatComponent) {
        super();
        int initialFlowToken = method_7eec03da329a017ec143();

        int j = initialFlowToken;

        this.guiID = text;
        this.displayName = chatComponent;
        int i = j;

        try {
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                b(++i);
            }
        } catch (UnsupportedOperationException unsupportedoperationexception) {
            throw method_1524377ab05941cf1076(unsupportedoperationexception);
        }
    }

    public static int method_7eec03da329a017ec143() {
        return c;
    }

    static {
        if (method_7eec03da329a017ec143() == 0) {
            b(2);
        }
    }

    public String getName() {
        return this.displayName.getUnformattedText();
    }

    public String getGuiID() {
        return this.guiID;
    }

    public static int method_beea3e16fe3ec0995eb9() {
        int i = method_7eec03da329a017ec143();

        try {
            return i == 0 ? 122 : 0;
        } catch (UnsupportedOperationException unsupportedoperationexception) {
            throw method_1524377ab05941cf1076(unsupportedoperationexception);
        }
    }

    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        throw new UnsupportedOperationException();
    }

    public ChatComponent getDisplayName() {
        return this.displayName;
    }

    public boolean hasCustomName() {
        return true;
    }

    private static UnsupportedOperationException method_1524377ab05941cf1076(
            UnsupportedOperationException unsupportedoperationexception) {
        return unsupportedoperationexception;
    }
}
