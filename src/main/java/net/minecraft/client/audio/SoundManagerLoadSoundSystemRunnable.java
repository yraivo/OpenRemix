package net.minecraft.client.audio;

import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundSystemStarterThread;
import net.minecraft.entity.Entity;

import paulscode.sound.SoundSystemConfig;

public class SoundManagerLoadSoundSystemRunnable implements Runnable {
    private static String b;

    SoundManager aqL;

    public SoundManagerLoadSoundSystemRunnable(SoundManager aqLValue) {
        this.aqL = aqLValue;
    }

    @Override
    public void run() {
        int j = SoundHandler.getOpaqueControlBranchValue();
        SoundSystemConfig.setLogger(new SoundManagerRunSoundSystemLogger(this));
        int i = j;
        SoundManager.access$102(this.aqL, new SoundSystemStarterThread(this.aqL, null));
        SoundManager.access$302(this.aqL, true);
        SoundManager.access$100(this.aqL)
                .setMasterVolume(
                        SoundManager.access$400(this.aqL)
                                .getSoundLevel(SoundCategory.MASTER));
        SoundManager.access$000().info(SoundManager.access$500(), b);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            SoundHandler.setOpaqueControlState(++i);
        }
    }

    // region String decoding support

    // endregion

    static {
        b = "Sound engine started";
    }

    // region String decoding support

    // endregion
}
