package net.minecraft.client.player.inventory;

import com.google.common.collect.Maps;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.ChatComponent;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

import java.util.Map;

public class ContainerLocalMenu extends InventoryBasic implements ILockableContainer {
    private Map dP;
    private String guiID;

    @Override
    public boolean isLocked() {
        return false;
    }

    private static UnsupportedOperationException method_1524377ab05941cf1076(
            UnsupportedOperationException unsupportedoperationexception) {
        return unsupportedoperationexception;
    }

    public void setField(int p171_0, int p171_1) {
        this.dP.put(p171_0, p171_1);
    }

    @Override
    public void setLockCode(LockCode lockCode) {}

    public String getGuiID() {
        return this.guiID;
    }

    public int getField(int p171_0) {
        try {
            if (this.dP.containsKey(p171_0)) {
                return (java.lang.Integer) this.dP.get(p171_0);
            }
        } catch (java.lang.UnsupportedOperationException unsupportedoperationexception) {
            throw net.minecraft.client.player.inventory.ContainerLocalMenu
                    .method_1524377ab05941cf1076(unsupportedoperationexception);
        }

        return 0;
    }

    public int getFieldCount() {
        return this.dP.size();
    }

    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LockCode getLockCode() {
        return LockCode.EMPTY_CODE;
    }

    public ContainerLocalMenu(String text, ChatComponent chatComponent, int i) {
        this(text, chatComponent, i, LocalBlockIntercommunication.method_7eec03da329a017ec143());
    }

    private ContainerLocalMenu(
            String text, ChatComponent chatComponent, int i, int initialFlowToken) {
        super(chatComponent, i);

        int k = initialFlowToken;

        int j = k;

        try {
            this.dP = Maps.newHashMap();
            this.guiID = text;
            if (j == 0) {
                Entity.setLegacyGlobalFlowMarker(new String[3]);
            }
        } catch (UnsupportedOperationException unsupportedoperationexception) {
            throw method_1524377ab05941cf1076(unsupportedoperationexception);
        }
    }
}
