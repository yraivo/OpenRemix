package net.minecraft.entity.player;

public enum EntityPlayerEnumStatus {
    OK("OK", 0),
    NOT_POSSIBLE_HERE("NOT_POSSIBLE_HERE", 1),
    NOT_POSSIBLE_NOW("NOT_POSSIBLE_NOW", 2),
    TOO_FAR_AWAY("TOO_FAR_AWAY", 3),
    OTHER_PROBLEM("OTHER_PROBLEM", 4),
    NOT_SAFE("NOT_SAFE", 5);
    private static EntityPlayerEnumStatus[] agu;

    static {
        agu =
                new EntityPlayerEnumStatus[] {
                    net.minecraft.entity.player.EntityPlayerEnumStatus.OK,
                    net.minecraft.entity.player.EntityPlayerEnumStatus.NOT_POSSIBLE_HERE,
                    net.minecraft.entity.player.EntityPlayerEnumStatus.NOT_POSSIBLE_NOW,
                    net.minecraft.entity.player.EntityPlayerEnumStatus.TOO_FAR_AWAY,
                    net.minecraft.entity.player.EntityPlayerEnumStatus.OTHER_PROBLEM,
                    net.minecraft.entity.player.EntityPlayerEnumStatus.NOT_SAFE
                };
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    EntityPlayerEnumStatus(String text, int j) {}

    // region String decoding support

    // endregion

}
