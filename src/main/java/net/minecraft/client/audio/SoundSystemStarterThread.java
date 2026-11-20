package net.minecraft.client.audio;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.Source;

public class SoundSystemStarterThread extends SoundSystem {
    SoundManager field_148591_a;

    public SoundSystemStarterThread(SoundManager soundManager, Object object) {
        this(soundManager);
    }

    public boolean playing(String text) {
        Object THREAD_SYNC = SoundSystemConfig.THREAD_SYNC;
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (this.soundLibrary == null) {
                return false;
            }

            Source source = (Source) this.soundLibrary.getSources().get(text);
            return source != null && (source.playing() || source.paused() || source.preLoad);
        }
    }

    private SoundSystemStarterThread(SoundManager aqLValue) {
        this.field_148591_a = aqLValue;
    }
}
