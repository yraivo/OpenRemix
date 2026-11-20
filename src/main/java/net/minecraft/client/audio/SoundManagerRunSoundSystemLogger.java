package net.minecraft.client.audio;

import net.minecraft.client.audio.SoundManager;

import paulscode.sound.SoundSystemLogger;

class SoundManagerRunSoundSystemLogger extends SoundSystemLogger {
    private static String b;
    SoundManagerLoadSoundSystemRunnable aOM;
    private static String[] packedTextFragments;

    SoundManagerRunSoundSystemLogger(SoundManagerLoadSoundSystemRunnable aOMValue) {
        this.aOM = aOMValue;
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.audio.SoundManagerRunSoundSystemLogger.packedTextFragments = new String[2];
        net.minecraft.client.audio.SoundManagerRunSoundSystemLogger.packedTextFragments[0] = new String("'");
        net.minecraft.client.audio.SoundManagerRunSoundSystemLogger.packedTextFragments[1] =
                new String("PE'3g\u0014:{\u001760tG 5\u0010");
        b = "Error in class '";
    }

    // region String decoding support

    // endregion

    public void importantMessage(String text, int i) {
        if (!text.isEmpty()) {
            SoundManager.access$000().warn(text);
        }
    }

    public void errorMessage(String text, String textValue, int i) {
        if (!textValue.isEmpty()) {
            SoundManager.access$000().error(b + text + packedTextFragments[0]);
            SoundManager.access$000().error(textValue);
        }
    }

    public void message(String text, int i) {
        if (!text.isEmpty()) {
            SoundManager.access$000().info(text);
        }
    }
}
