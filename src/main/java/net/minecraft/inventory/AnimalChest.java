package net.minecraft.inventory;

import net.minecraft.util.ChatComponent;

public class AnimalChest extends InventoryBasic {
    public AnimalChest(String text, int p171_1) {
        super(text, false, p171_1);
    }

    public AnimalChest(ChatComponent chatComponent, int p171_1) {
        super(chatComponent, p171_1);
    }
}
