package net.minecraft.world;

import net.minecraft.nbt.CompoundTag;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class GameRules {
    private static String[] packedTextFragments;

    private TreeMap theGameRules = new TreeMap();

    public String[] getRules() {
        return ((java.lang.String[]) this.theGameRules.keySet().toArray(new String[0]));
    }

    public String getString(String text) {
        GameRulesValue gameRulesValue = (GameRulesValue) this.theGameRules.get(text);
        return gameRulesValue != null
                ? gameRulesValue.getGameRuleStringValue()
                : packedTextFragments[1];
    }

    static {
        net.minecraft.world.GameRules.packedTextFragments = new String[4];
        net.minecraft.world.GameRules.packedTextFragments[0] = new String("3");
        net.minecraft.world.GameRules.packedTextFragments[1] = new String("");
    }

    public void setOrCreateGameRule(String text, String textValue) {
        World.getLegacyWorldFlowMarker();

        GameRulesValue gameRulesValue = (GameRulesValue) this.theGameRules.get(text);

        if (gameRulesValue != null) {
            gameRulesValue.setValue(textValue);

            return;
        }

        this.addGameRule(text, textValue, GameRulesValueType.ANY_VALUE);
    }

    public int getInt(String text) {
        GameRulesValue gameRulesValue = (GameRulesValue) this.theGameRules.get(text);
        return gameRulesValue != null ? gameRulesValue.getInt() : 0;
    }

    public void addGameRule(String text, String textValue, GameRulesValueType gameRulesValueType) {
        this.theGameRules.put(text, new GameRulesValue(textValue, gameRulesValueType));
    }

    public void readFromNBT(CompoundTag compoundTag) {
        World.getLegacyWorldFlowMarker();

        Set set = compoundTag.getKeySet();
        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            String text = (String) iterator.next();
            String textValue = compoundTag.getString(text);
            this.setOrCreateGameRule(text, textValue);
        }
    }

    public boolean areSameType(String text, GameRulesValueType gameRulesValueType) {
        GameRulesValue gameRulesValue = (GameRulesValue) this.theGameRules.get(text);
        return gameRulesValue != null
                && (gameRulesValue.getType() == gameRulesValueType
                        || gameRulesValueType == GameRulesValueType.ANY_VALUE);
    }

    public boolean hasRule(String text) {
        return this.theGameRules.containsKey(text);
    }

    public boolean getGameRuleBooleanValue(String text) {
        GameRulesValue gameRulesValue = (GameRulesValue) this.theGameRules.get(text);
        return gameRulesValue != null ? gameRulesValue.getGameRuleBooleanValue() : false;
    }

    public GameRules() {
        this.addGameRule("doFireTick", "true", GameRulesValueType.BOOLEAN_VALUE);
        this.addGameRule("mobGriefing", "true", GameRulesValueType.BOOLEAN_VALUE);
        this.addGameRule("keepInventory", "false", GameRulesValueType.BOOLEAN_VALUE);
        this.addGameRule("doMobSpawning", "true", GameRulesValueType.BOOLEAN_VALUE);
        this.addGameRule("doMobLoot", "true", GameRulesValueType.BOOLEAN_VALUE);
        this.addGameRule("doTileDrops", "true", GameRulesValueType.BOOLEAN_VALUE);
        this.addGameRule("commandBlockOutput", "true", GameRulesValueType.BOOLEAN_VALUE);
        this.addGameRule("naturalRegeneration", "true", GameRulesValueType.BOOLEAN_VALUE);
        this.addGameRule("doDaylightCycle", "true", GameRulesValueType.BOOLEAN_VALUE);
        this.addGameRule("logAdminCommands", "true", GameRulesValueType.BOOLEAN_VALUE);
        this.addGameRule("showDeathMessages", "true", GameRulesValueType.BOOLEAN_VALUE);
        this.addGameRule(
                "randomTickSpeed", packedTextFragments[0], GameRulesValueType.NUMERICAL_VALUE);
        this.addGameRule("sendCommandFeedback", "true", GameRulesValueType.BOOLEAN_VALUE);
        this.addGameRule("reducedDebugInfo", "false", GameRulesValueType.BOOLEAN_VALUE);
    }

    public CompoundTag writeGameRulesToNBT() {
        World.getLegacyWorldFlowMarker();

        CompoundTag compoundTag = new CompoundTag();
        Iterator iterator = this.theGameRules.keySet().iterator();

        while (iterator.hasNext()) {
            String text = (String) iterator.next();
            GameRulesValue gameRulesValue = (GameRulesValue) this.theGameRules.get(text);
            compoundTag.setString(text, gameRulesValue.getGameRuleStringValue());
        }

        return compoundTag;
    }
}
