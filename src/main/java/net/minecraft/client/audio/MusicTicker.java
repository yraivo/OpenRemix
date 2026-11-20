package net.minecraft.client.audio;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.MathHelper;

import java.util.Random;

public class MusicTicker implements IUpdatePlayerListBox {
    private int timeUntilNextMusic;
    private ISound currentMusic;
    private final Random rand = new Random();
    private MinecraftClient mc;

    public void update() {
        MusicTickerMusicType ambientMusicType = this.mc.getAmbientMusicType();
        if (this.currentMusic != null) {
            if (!ambientMusicType.getMusicLocation().equals(this.currentMusic.getSoundLocation())) {
                this.mc.getSoundHandler().stopSound(this.currentMusic);
                this.timeUntilNextMusic =
                        MathHelper.nextIntInclusive(
                                this.rand, 0, ambientMusicType.getMinDelay() / 2);
            }

            if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic)) {
                this.currentMusic = null;
                this.timeUntilNextMusic =
                        Math.min(
                                MathHelper.nextIntInclusive(
                                        this.rand,
                                        ambientMusicType.getMinDelay(),
                                        ambientMusicType.getMaxDelay()),
                                this.timeUntilNextMusic);
            }
        }

        if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0) {
            this.currentMusic =
                    PositionedSoundRecord.createPositionedSoundRecord(
                            ambientMusicType.getMusicLocation());
            this.mc.getSoundHandler().playSound(this.currentMusic);
            this.timeUntilNextMusic = Integer.MAX_VALUE;
        }
    }

    public MusicTicker(MinecraftClient minecraftClient) {
        this.timeUntilNextMusic = 100;
        this.mc = minecraftClient;
    }
}
