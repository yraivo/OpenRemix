package net.minecraft.stats;

import net.minecraft.entity.Entity;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEventAction;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.stats.StatBaseDistanceStatTypeIStatType;
import net.minecraft.stats.StatBaseIStatType4;
import net.minecraft.stats.StatBaseSimpleStatTypeIStatType;
import net.minecraft.stats.StatBaseTimeStatTypeIStatType;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class StatBase {
    private IStatType type;
    public boolean isIndependent;
    private IScoreObjectiveCriteria field_150957_c;
    private static DecimalFormat decimalFormat;
    private static NumberFormat numberFormat;
    private static Entity[] bCQ;
    public static IStatType distanceStatType;

    private static String[] packedTextFragments;
    private Class field_150956_d;
    public static IStatType simpleStatType;
    public String statId;
    public static IStatType timeStatType;
    public static IStatType bCW;
    private ChatComponent statName;

    public StatBase(String text, ChatComponent chatComponent) {
        this(text, chatComponent, net.minecraft.stats.StatBase.simpleStatType);
    }

    @Override
    public String toString() {
        Entity[] entityArray = GU();

        try {
            String text =
                    "Stat{id="
                            + this.statId
                            + ", nameId="
                            + this.statName
                            + ", awardLocallyOnly="
                            + this.isIndependent
                            + ", formatter="
                            + this.type
                            + ", objectiveCriteria="
                            + this.field_150957_c
                            + '}';
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                b(new Entity[3]);
            }

            return text;
        } catch (RuntimeException runtimeexception) {
            throw method_1a1f08aee5fe72868e61(runtimeexception);
        }
    }

    public StatBase initIndependentStat() {
        this.isIndependent = true;
        return this;
    }

    @Override
    public int hashCode() {
        return this.statId.hashCode();
    }

    public Class func_150954_l() {
        return this.field_150956_d;
    }

    public ChatComponent func_150955_j() {
        ChatComponent chatComponent = this.getStatName();
        ChatComponent chatComponentValue =
                new ChatComponentText(packedTextFragments[0])
                        .appendSibling(chatComponent)
                        .appendText(packedTextFragments[1]);
        chatComponentValue.setChatStyle(chatComponent.getChatStyle());
        return chatComponentValue;
    }

    // region String decoding support

    // endregion

    public static Entity[] GU() {
        return bCQ;
    }

    @Override
    public boolean equals(Object object) {
        try {
            if (this == object) {
                return true;
            }
        } catch (RuntimeException runtimeexception) {
            throw method_1a1f08aee5fe72868e61(runtimeexception);
        }

        try {
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
        } catch (RuntimeException runtimeexception1) {
            throw method_1a1f08aee5fe72868e61(runtimeexception1);
        }

        StatBase statBase = (StatBase) object;
        return this.statId.equals(statBase.statId);
    }

    // region String decoding support

    // endregion

    public ChatComponent getStatName() {
        ChatComponent chatComponent = this.statName.createCopy();
        chatComponent.getChatStyle().setColor(EnumChatFormatting.GRAY);
        chatComponent
                .getChatStyle()
                .setChatHoverEvent(
                        new HoverEvent(
                                HoverEventAction.SHOW_ACHIEVEMENT,
                                new ChatComponentText(this.statId)));
        return chatComponent;
    }

    private static RuntimeException method_1a1f08aee5fe72868e61(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    static {
        net.minecraft.stats.StatBase.packedTextFragments = new String[4];
        net.minecraft.stats.StatBase.packedTextFragments[0] = new String("[");
        net.minecraft.stats.StatBase.packedTextFragments[1] = new String("]");
        b(null);
        net.minecraft.stats.StatBase.numberFormat = NumberFormat.getIntegerInstance(Locale.US);
        net.minecraft.stats.StatBase.simpleStatType = new StatBaseSimpleStatTypeIStatType();
        net.minecraft.stats.StatBase.decimalFormat = new DecimalFormat("########0.00");
        net.minecraft.stats.StatBase.timeStatType = new StatBaseTimeStatTypeIStatType();
        net.minecraft.stats.StatBase.distanceStatType = new StatBaseDistanceStatTypeIStatType();
        bCW = new StatBaseIStatType4();
    }

    // region String decoding support

    // endregion

    public static DecimalFormat access$100() {
        return net.minecraft.stats.StatBase.decimalFormat;
    }

    public IScoreObjectiveCriteria func_150952_k() {
        return this.field_150957_c;
    }

    public String format(int i) {
        return this.type.format(i);
    }

    public StatBase func_150953_b(Class oclass) {
        this.field_150956_d = oclass;
        return this;
    }

    public static void b(Entity[] bCQValue) {
        bCQ = bCQValue;
    }

    public boolean isAchievement() {
        return false;
    }

    public StatBase registerStat() {
        try {
            if (StatList.oneShotStats.containsKey(this.statId)) {
                throw new RuntimeException(
                        "Duplicate stat id: \""
                                + ((StatBase) StatList.oneShotStats.get(this.statId)).statName
                                + "\" and \""
                                + this.statName
                                + "\" at id "
                                + this.statId);
            }
        } catch (RuntimeException runtimeexception) {
            throw method_1a1f08aee5fe72868e61(runtimeexception);
        }

        StatList.allStats.add(this);
        StatList.oneShotStats.put(this.statId, this);
        return this;
    }

    public StatBase(String text, ChatComponent aqMValue, IStatType bCNValue) {
        this.statId = text;
        this.statName = aqMValue;
        this.type = bCNValue;
        this.field_150957_c = new ObjectiveStat(this);
        IScoreObjectiveCriteria.INSTANCES.put(this.field_150957_c.getName(), this.field_150957_c);
    }

    public static NumberFormat access$000() {
        return net.minecraft.stats.StatBase.numberFormat;
    }
}
