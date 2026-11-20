package net.minecraft.command;

public enum CommandResultStatsType {
    SUCCESS_COUNT("SUCCESS_COUNT", 0, 0, "SuccessCount"),
    AFFECTED_BLOCKS("AFFECTED_BLOCKS", 1, 1, "AffectedBlocks"),
    AFFECTED_ENTITIES("AFFECTED_ENTITIES", 2, 2, "AffectedEntities"),
    AFFECTED_ITEMS("AFFECTED_ITEMS", 3, 3, "AffectedItems"),
    QUERY_RESULT("QUERY_RESULT", 4, 4, "QueryResult");

    private static CommandResultStatsType[] aev;

    int typeID;

    String typeName;

    public String getTypeName() {
        return this.typeName;
    }

    public static String[] getTypeNames() {
        String[] astring = new String[values().length];
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        int i = 0;
        boolean flag = flag1;

        for (CommandResultStatsType commandResultStatsType : values()) {
            astring[i++] = commandResultStatsType.getTypeName();
            if (!flag) {
                break;
            }
        }

        return astring;
    }

    public static CommandResultStatsType V(String text) {
        boolean flag1 = CommandException.method_d87039b07bd6961907f2();
        CommandResultStatsType[] commandResultStatsTypeArray = values();
        boolean flag = flag1;

        for (CommandResultStatsType commandResultStatsType : commandResultStatsTypeArray) {
            if (commandResultStatsType.getTypeName().equals(text)) {
                return commandResultStatsType;
            }

            if (flag) {
                break;
            }
        }

        return null;
    }

    public int getTypeID() {
        return this.typeID;
    }

    CommandResultStatsType(String text, int k, int j, String textValue) {
        this.typeID = j;
        this.typeName = textValue;
    }

    // region String decoding support

    // endregion

    static {
        aev =
                new CommandResultStatsType[] {
                    net.minecraft.command.CommandResultStatsType.SUCCESS_COUNT,
                    net.minecraft.command.CommandResultStatsType.AFFECTED_BLOCKS,
                    net.minecraft.command.CommandResultStatsType.AFFECTED_ENTITIES,
                    net.minecraft.command.CommandResultStatsType.AFFECTED_ITEMS,
                    net.minecraft.command.CommandResultStatsType.QUERY_RESULT
                };
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
