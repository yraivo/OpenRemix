package net.minecraft.client.gui.inventory;

import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CreativeCrafting implements ICrafting {
    private MinecraftClient mc;
    private static int[] legacyCreativeFlowMarker;

    public static int[] getLegacyCreativeFlowMarker() {
        return net.minecraft.client.gui.inventory.CreativeCrafting.legacyCreativeFlowMarker;
    }

    public void sendSlotContents(Container container, int i, ItemStack itemStack) {
        this.mc.playerController.sendSlotPacket(itemStack, i);
    }

    public CreativeCrafting(MinecraftClient minecraftClient) {
        this.mc = minecraftClient;
    }

    public void updateCraftingInventory(Container container, List list) {}

    public static void setLegacyCreativeFlowMarker(int[] aint) {
        net.minecraft.client.gui.inventory.CreativeCrafting.legacyCreativeFlowMarker = aint;
    }

    static {
        if (getLegacyCreativeFlowMarker() != null) {
            setLegacyCreativeFlowMarker(new int[3]);
        }
    }

    public void sendProgressBarUpdate(Container container, int i, int j) {}

    public void func_175173_a(Container container, IInventory iInventory) {}
}
