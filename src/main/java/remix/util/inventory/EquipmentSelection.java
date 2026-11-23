package remix.util.inventory;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/** Rebuilt for one inventory decision, never retained as a valid result across clicks or ticks. */
public final class EquipmentSelection {
    private final ItemStack[] armor = new ItemStack[4];
    private final ItemStack[] tools = new ItemStack[5];

    public void clear() {
        Arrays.fill(this.armor, null);
        Arrays.fill(this.tools, null);
    }

    public void capturePlayer(Container player, Comparator<ItemStack> tool,
            Comparator<ItemStack> weapon, Comparator<ItemStack> bow) {
        this.clear();
        this.scanArmor(player);
        this.scanTools(player, true, tool, weapon, bow);
    }

    public void captureCombined(Container player, Container chest, Comparator<ItemStack> tool,
            Comparator<ItemStack> weapon, Comparator<ItemStack> bow) {
        this.clear();
        this.scanArmor(player);
        this.scanArmor(chest);
        this.scanTools(player, false, tool, weapon, bow);
        this.scanTools(chest, false, tool, weapon, bow);
    }

    private void scanArmor(Container container) {
        for (Object object : container.inventorySlots) {
            Slot slot = (Slot) object;
            if (!slot.getHasStack()) continue;
            ItemStack stack = slot.getStack();
            if (!(stack.getItem() instanceof ItemArmor)) continue;
            int type = ((ItemArmor) stack.getItem()).armorType;
            ItemStack previous = this.armor[type];
            if (previous == null || InventoryItemHelper.getArmorProtection(stack)
                    > InventoryItemHelper.getArmorProtection(previous)) this.armor[type] = stack;
        }
    }

    private void scanTools(Container container, boolean reverse, Comparator<ItemStack> tool,
            Comparator<ItemStack> weapon, Comparator<ItemStack> bow) {
        List<?> slots = container.inventorySlots;
        int size = slots.size();
        for (int offset = 0; offset < size; offset++) {
            Slot slot = (Slot) slots.get(reverse ? size - 1 - offset : offset);
            ItemStack stack = slot.getStack();
            if (stack == null) continue;
            Class<?> type = stack.getItem().getClass();
            int index;
            Comparator<ItemStack> comparator;
            if (type == ItemSpade.class) { index = 0; comparator = tool; }
            else if (type == ItemPickaxe.class) { index = 1; comparator = tool; }
            else if (type == ItemAxe.class) { index = 2; comparator = tool; }
            else if (type == SwordItem.class) { index = 3; comparator = weapon; }
            else if (type == ItemBow.class) { index = 4; comparator = bow; }
            else continue;
            ItemStack previous = this.tools[index];
            int comparison = comparator.compare(stack, previous);
            if (comparison > 0 || comparison == 0
                    && InventoryItemHelper.getEnchantmentScore(stack)
                            > InventoryItemHelper.getEnchantmentScore(previous)) this.tools[index] = stack;
        }
    }

    public boolean contains(ItemStack stack) {
        if (stack == null) return false;
        for (ItemStack tool : this.tools) if (stack == tool) return true;
        for (ItemStack piece : this.armor) if (stack.equals(piece)) return true;
        return false;
    }
}
