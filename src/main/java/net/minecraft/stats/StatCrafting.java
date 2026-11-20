package net.minecraft.stats;

import net.minecraft.item.Item;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.util.ChatComponent;

public class StatCrafting extends StatBase {
    private Item bpt;

    public Item qT() {
        return this.bpt;
    }

    public StatCrafting(String text, String textValue, ChatComponent chatComponent, Item bptValue) {
        super(text + textValue, chatComponent);
        this.bpt = bptValue;
        int idFromItem = Item.getIdFromItem(bptValue);
        if (idFromItem != 0) {
            IScoreObjectiveCriteria.INSTANCES.put(text + idFromItem, this.func_150952_k());
        }
    }
}
