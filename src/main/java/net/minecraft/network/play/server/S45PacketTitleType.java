package net.minecraft.network.play.server;

public enum S45PacketTitleType {
    TITLE("TITLE", 0),
    SUBTITLE("SUBTITLE", 1),
    TIMES("TIMES", 2),
    CLEAR("CLEAR", 3),
    RESET("RESET", 4);
    private static S45PacketTitleType[] acH;

    public static String[] getNames() {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        String[] astring = new String[values().length];
        int i = 0;
        S45PacketTitleType[] s45PacketTitleTypeArray = values();
        int[] aint = aint1;

        for (S45PacketTitleType s45PacketTitleType : s45PacketTitleTypeArray) {
            astring[i++] = s45PacketTitleType.name().toLowerCase();
            if (aint != null) {
                break;
            }
        }

        return astring;
    }

    static {
        acH =
                new S45PacketTitleType[] {
                    net.minecraft.network.play.server.S45PacketTitleType.TITLE,
                    net.minecraft.network.play.server.S45PacketTitleType.SUBTITLE,
                    net.minecraft.network.play.server.S45PacketTitleType.TIMES,
                    net.minecraft.network.play.server.S45PacketTitleType.CLEAR,
                    net.minecraft.network.play.server.S45PacketTitleType.RESET
                };
    }

    S45PacketTitleType(String text, int j) {}

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public static S45PacketTitleType R(String text) {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        S45PacketTitleType[] s45PacketTitleTypeArray = values();
        int length = s45PacketTitleTypeArray.length;
        int[] aint = aint1;
        int j = 0;

        while (j < length) {
            S45PacketTitleType s45PacketTitleType = s45PacketTitleTypeArray[j];
            if (s45PacketTitleType.name().equalsIgnoreCase(text)) {
                return s45PacketTitleType;
            }

            j++;
            if (aint != null) {
                break;
            }
        }

        return net.minecraft.network.play.server.S45PacketTitleType.TITLE;
    }
}
