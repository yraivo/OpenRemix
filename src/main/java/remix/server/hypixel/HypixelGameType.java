package remix.server.hypixel;

import remix.util.time.ElapsedTimer;

public enum HypixelGameType {
    UNKNOWN(initializeConstantState()),
    BED_WARS("BEDWARS"),
    SKY_WARS("SKYWARS"),
    PROTOTYPE("PROTOTYPE"),
    SKYBLOCK("SKYBLOCK"),
    MAIN("MAIN"),
    MURDER_MYSTERY("MURDER_MYSTERY"),
    HOUSING("HOUSING"),
    ARCADE_GAMES("ARCADE"),
    BUILD_BATTLE("BUILD_BATTLE"),
    DUELS("DUELS"),
    PIT("PIT"),
    UHC_CHAMPIONS("UHC"),
    SPEED_UHC("SPEED_UHC"),
    TNT_GAMES("TNTGAMES"),
    CLASSIC_GAMES("LEGACY"),
    COPS_AND_CRIMS("MCGO"),
    BLITZ_SG("SURVIVAL_GAMES"),
    MEGA_WALLS("WALLS3"),
    SMASH_HEROES("SUPER_SMASH"),
    WARLORDS("BATTLEGROUND");
    private static HypixelGameType[] cv;

    private static HypixelGameType[] ci;

    private String a;
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    public String getGameName() {
        return this.a;
    }

    // region String decoding support

    // endregion

    public static HypixelGameType fromGameName(String text) {
        ElapsedTimer.fM();

        HypixelGameType[] hypixelGameTypeArray = ci;

        for (HypixelGameType hypixelGameType : hypixelGameTypeArray) {
            if (hypixelGameType.a.equals(text)) {
                return hypixelGameType;
            }
        }

        return remix.server.hypixel.HypixelGameType.UNKNOWN;
    }

    HypixelGameType(String text) {
        this.a = text;
    }

    static {
        cv =
                new HypixelGameType[] {
                    remix.server.hypixel.HypixelGameType.UNKNOWN,
                    remix.server.hypixel.HypixelGameType.BED_WARS,
                    remix.server.hypixel.HypixelGameType.SKY_WARS,
                    remix.server.hypixel.HypixelGameType.PROTOTYPE,
                    remix.server.hypixel.HypixelGameType.SKYBLOCK,
                    remix.server.hypixel.HypixelGameType.MAIN,
                    remix.server.hypixel.HypixelGameType.MURDER_MYSTERY,
                    remix.server.hypixel.HypixelGameType.HOUSING,
                    remix.server.hypixel.HypixelGameType.ARCADE_GAMES,
                    remix.server.hypixel.HypixelGameType.BUILD_BATTLE,
                    remix.server.hypixel.HypixelGameType.DUELS,
                    remix.server.hypixel.HypixelGameType.PIT,
                    remix.server.hypixel.HypixelGameType.UHC_CHAMPIONS,
                    remix.server.hypixel.HypixelGameType.SPEED_UHC,
                    remix.server.hypixel.HypixelGameType.TNT_GAMES,
                    remix.server.hypixel.HypixelGameType.CLASSIC_GAMES,
                    remix.server.hypixel.HypixelGameType.COPS_AND_CRIMS,
                    remix.server.hypixel.HypixelGameType.BLITZ_SG,
                    remix.server.hypixel.HypixelGameType.MEGA_WALLS,
                    remix.server.hypixel.HypixelGameType.SMASH_HEROES,
                    remix.server.hypixel.HypixelGameType.WARLORDS
                };
        ci = values();
    }

    // region String decoding support

    // endregion

    private static HypixelGameType[] __recovery_values171() {
        return cv.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        remix.server.hypixel.HypixelGameType.packedTextFragments = new String[3];
        remix.server.hypixel.HypixelGameType.packedTextFragments[2] = new String("");
        return remix.server.hypixel.HypixelGameType.packedTextFragments[2];
    }
}
