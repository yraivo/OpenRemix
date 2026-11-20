package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class GuiPageButtonListEditBoxEntry extends GuiPageButtonListGuiListEntry {
    private Predicate asB;

    public Predicate vk() {
        return this.asB;
    }

    public GuiPageButtonListEditBoxEntry(int i, String text, boolean flag, Predicate predicate) {
        super(i, text, flag);
        this.asB =
                (Predicate)
                        com.google.common.base.MoreObjects.firstNonNull(
                                predicate, Predicates.alwaysTrue());
    }
}
