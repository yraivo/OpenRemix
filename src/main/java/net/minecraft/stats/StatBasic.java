package net.minecraft.stats;

import net.minecraft.util.ChatComponent;

public class StatBasic extends StatBase {
    public StatBasic(String text, ChatComponent chatComponent) {
        super(text, chatComponent);
    }

    public StatBase registerStat() {
        super.registerStat();
        net.minecraft.stats.StatList.generalStats.add(this);
        return this;
    }

    public StatBasic(
            String text, ChatComponent chatComponent, IStatType iStatType) {
        super(text, chatComponent, iStatType);
    }
}
