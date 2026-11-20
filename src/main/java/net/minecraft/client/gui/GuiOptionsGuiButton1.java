package net.minecraft.client.gui;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;

public class GuiOptionsGuiButton1 extends GuiButton {
    GuiOptions are;

    public GuiOptionsGuiButton1(
            GuiOptions areValue, int i, int j, int k, int l, int i1, String text) {
        super(i, j, k, l, i1, text);
        this.are = areValue;
    }

    public void playPressSound(SoundHandler soundHandler) {
        SoundEventAccessorComposite soundEventAccessorComposite =
                soundHandler.getRandomSoundFromCategories(
                        new SoundCategory[] {
                            SoundCategory.ANIMALS,
                            SoundCategory.BLOCKS,
                            SoundCategory.MOBS,
                            SoundCategory.PLAYERS,
                            SoundCategory.WEATHER
                        });
        if (soundEventAccessorComposite != null) {
            soundHandler.playSound(
                    PositionedSoundRecord.createPositionedSoundRecord(
                            soundEventAccessorComposite.getSoundEventLocation(), 0.5F));
        }
    }
}
