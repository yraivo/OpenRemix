package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public enum MusicTickerMusicType {
    MENU("MENU", 0, new ResourceLocation("minecraft:music.menu"), 20, 600),
    GAME("GAME", 1, new ResourceLocation("minecraft:music.game"), 12000, 24000),
    CREATIVE("CREATIVE", 2, new ResourceLocation("minecraft:music.game.creative"), 1200, 3600),
    CREDITS(
            "CREDITS",
            3,
            new ResourceLocation("minecraft:music.game.end.credits"),
            Integer.MAX_VALUE,
            Integer.MAX_VALUE),
    NETHER("NETHER", 4, new ResourceLocation("minecraft:music.game.nether"), 1200, 3600),
    END_BOSS("END_BOSS", 5, new ResourceLocation("minecraft:music.game.end.dragon"), 0, 0),
    END("END", 6, new ResourceLocation("minecraft:music.game.end"), 6000, 24000);

    private static MusicTickerMusicType[] aCR;

    private int minDelay;
    private int maxDelay;
    private ResourceLocation musicLocation;

    // region String decoding support

    // endregion

    MusicTickerMusicType(String text, int l, ResourceLocation resourceLocation, int j, int k) {
        this.musicLocation = resourceLocation;
        this.minDelay = j;
        this.maxDelay = k;
    }

    // region String decoding support

    // endregion

    public int getMaxDelay() {
        return this.maxDelay;
    }

    static {
        aCR =
                new MusicTickerMusicType[] {
                    net.minecraft.client.audio.MusicTickerMusicType.MENU,
                    net.minecraft.client.audio.MusicTickerMusicType.GAME,
                    net.minecraft.client.audio.MusicTickerMusicType.CREATIVE,
                    net.minecraft.client.audio.MusicTickerMusicType.CREDITS,
                    net.minecraft.client.audio.MusicTickerMusicType.NETHER,
                    net.minecraft.client.audio.MusicTickerMusicType.END_BOSS,
                    net.minecraft.client.audio.MusicTickerMusicType.END
                };
    }

    public ResourceLocation getMusicLocation() {
        return this.musicLocation;
    }

    // region String decoding support

    // endregion

    public int getMinDelay() {
        return this.minDelay;
    }
}
