package net.minecraft.stats;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class Achievement extends StatBase {
    public Achievement parentAchievement;
    public int displayColumn;
    public int displayRow;

    private String achievementDescription;

    private boolean isSpecial;

    private IStatStringFormat statStringFormatter;
    public ItemStack theItemStack;

    public Achievement setStatStringFormatter(IStatStringFormat bCYValue) {
        this.statStringFormatter = bCYValue;
        return this;
    }

    public boolean getSpecial() {
        return this.isSpecial;
    }

    public String getDescription() {
        return this.statStringFormatter != null
                ? this.statStringFormatter.formatString(
                        StatCollector.translateToLocal(this.achievementDescription))
                : StatCollector.translateToLocal(this.achievementDescription);
    }

    public boolean isAchievement() {
        return true;
    }

    public Achievement setSpecial() {
        this.isSpecial = true;
        return this;
    }


    public StatBase registerStat() {
        return this.func_180788_c();
    }

    public ChatComponent getStatName() {
        ChatComponent chatComponent = super.getStatName();
        chatComponent
                .getChatStyle()
                .setColor(
                        this.getSpecial()
                                ? EnumChatFormatting.DARK_PURPLE
                                : EnumChatFormatting.GREEN);
        return chatComponent;
    }

    public Achievement func_180788_c() {
        super.registerStat();
        AchievementList.achievementList.add(this);
        return this;
    }

    public StatBase initIndependentStat() {
        return this.func_180789_a();
    }

    public Achievement func_180789_a() {
        this.isIndependent = true;
        return this;
    }

    public StatBase func_150953_b(Class oclass) {
        return this.func_180787_a(oclass);
    }

    public Achievement func_180787_a(Class oclass) {
        return (Achievement) super.func_150953_b(oclass);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Achievement(
            String text, String textValue, int i, int j, Item item, Achievement achievement) {
        this(text, textValue, i, j, new ItemStack(item), achievement);
    }

    public Achievement(
            String text,
            String textValue,
            int maxDisplayColumn,
            int j,
            ItemStack bBpValue,
            Achievement bCrValue) {
        super(text, new ChatComponentTranslation("achievement." + textValue, new Object[0]));
        this.theItemStack = bBpValue;
        this.achievementDescription = "achievement." + textValue + ".desc";
        this.displayColumn = maxDisplayColumn;
        this.displayRow = j;
        if (maxDisplayColumn < AchievementList.minDisplayColumn) {
            AchievementList.minDisplayColumn = maxDisplayColumn;
        }

        if (j < AchievementList.minDisplayRow) {
            AchievementList.minDisplayRow = j;
        }

        if (maxDisplayColumn > AchievementList.maxDisplayColumn) {
            AchievementList.maxDisplayColumn = maxDisplayColumn;
        }

        if (j > AchievementList.maxDisplayRow) {
            AchievementList.maxDisplayRow = j;
        }

        this.parentAchievement = bCrValue;
    }

    public Achievement(
            String text, String textValue, int i, int j, Block block, Achievement achievement) {
        this(text, textValue, i, j, new ItemStack(block), achievement);
    }
}
