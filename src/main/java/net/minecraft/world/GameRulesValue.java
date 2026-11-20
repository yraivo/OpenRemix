package net.minecraft.world;

class GameRulesValue {
    private boolean valueBoolean;

    private double valueDouble;
    private String valueString;
    private int valueInteger;
    private GameRulesValueType type;

    public int getInt() {
        return this.valueInteger;
    }

    public GameRulesValue(String text, GameRulesValueType bFNValue) {
        this.type = bFNValue;
        this.setValue(text);
    }

    public boolean getGameRuleBooleanValue() {
        return this.valueBoolean;
    }

    public void setValue(String text) {
        this.valueString = text;
        if (text != null) {
            if (text.equals("false")) {
                this.valueBoolean = false;
                return;
            }

            if (text.equals("true")) {
                this.valueBoolean = true;
                return;
            }
        }

        this.valueBoolean = Boolean.parseBoolean(text);
        this.valueInteger = this.valueBoolean ? 1 : 0;

        try {
            this.valueInteger = Integer.parseInt(text);
        } catch (NumberFormatException numberformatexception1) {
        }

        try {
            this.valueDouble = Double.parseDouble(text);
        } catch (NumberFormatException numberformatexception) {
        }
    }

    private static NumberFormatException method_a0514df28616b2633822(
            NumberFormatException numberformatexception) {
        return numberformatexception;
    }


    public String getGameRuleStringValue() {
        return this.valueString;
    }

    public GameRulesValueType getType() {
        return this.type;
    }
}
