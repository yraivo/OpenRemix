package net.minecraft.client.audio;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import io.netty.util.internal.ThreadLocalRandom;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SoundManager {
    private Map delayedSounds;
    private SoundSystemStarterThread sndSystem;
    private GameSettings options;
    private int playTime = 0;
    private static Logger logger;
    private Map invPlayingSounds;

    private SoundHandler sndHandler;
    private Map playingSoundsStopTime;
    private Map playingSoundPoolEntries;

    private List tickableSounds;
    private volatile boolean loaded;
    private final Map playingSounds = HashBiMap.create();
    private static Marker LOG_MARKER;
    private Multimap categorySounds;

    public void resumeAllSounds() {
        int j = SoundHandler.getOpaqueControlBranchValue();
        Iterator iterator = this.playingSounds.keySet().iterator();
        int i = j;

        while (iterator.hasNext()) {
            String text = (String) iterator.next();
            logger.debug(LOG_MARKER, "Resuming channel {}", new Object[] {text});
            this.sndSystem.play(text);
            if (i == 0) {
                break;
            }
        }
    }

    static {
        LOG_MARKER = MarkerManager.getMarker("SOUNDS");
        logger = LogManager.getLogger();
    }

    public SoundManager(SoundHandler aqEValue, GameSettings gameSettings) {
        this.invPlayingSounds = ((BiMap) this.playingSounds).inverse();
        this.playingSoundPoolEntries = Maps.newHashMap();
        this.categorySounds = HashMultimap.create();
        this.tickableSounds = Lists.newArrayList();
        this.delayedSounds = Maps.newHashMap();
        this.playingSoundsStopTime = Maps.newHashMap();
        this.sndHandler = aqEValue;
        this.options = gameSettings;

        try {
            SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
            SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
        } catch (SoundSystemException soundsystemexception) {
            logger.error(
                    LOG_MARKER,
                    "Error linking with the LibraryJavaSound plug-in",
                    soundsystemexception);
        }
    }

    private static RuntimeException method_1a1f08aee5fe72868e61(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    public static Marker access$500() {
        return LOG_MARKER;
    }

    public void playDelayedSound(ISound iSound, int i) {
        this.delayedSounds.put(iSound, this.playTime + i);
    }

    public static SoundSystemStarterThread access$100(SoundManager soundManager) {
        return soundManager.sndSystem;
    }

    private float getNormalizedPitch(ISound iSound, SoundPoolEntry soundPoolEntry) {
        return (float) MathHelper.clamp(iSound.getPitch() * soundPoolEntry.getPitch(), 0.5, 2.0);
    }

    public void setSoundCategoryVolume(SoundCategory soundCategory, float f) {
        int opaqueControlBranchValue = SoundHandler.getOpaqueControlBranchValue();
        if (this.loaded) {
            if (soundCategory == SoundCategory.MASTER) {
                this.sndSystem.setMasterVolume(f);
                if (opaqueControlBranchValue != 0) {
                    return;
                }
            }

            for (java.util.Iterator<?> __recoveryIterator171_8294 =
                            (this.categorySounds.get(soundCategory)).iterator();
                    (__recoveryIterator171_8294).hasNext(); ) {
                String text = (String) (__recoveryIterator171_8294).next();
                {
                    ISound iSound = (ISound) this.playingSounds.get(text);
                    float normalizedVolume =
                            this.getNormalizedVolume(
                                    iSound,
                                    (SoundPoolEntry) this.playingSoundPoolEntries.get(iSound),
                                    soundCategory);
                    if (normalizedVolume <= 0.0F) {
                        this.stopSound(iSound);
                        if (!(opaqueControlBranchValue != 0)) {

                            this.sndSystem.setVolume(text, normalizedVolume);
                        }
                    } else {

                        this.sndSystem.setVolume(text, normalizedVolume);
                    }
                }

                if (opaqueControlBranchValue == 0) {
                    break;
                }
            }
        }
    }

    public boolean isSoundPlaying(ISound iSound) {
        if (!this.loaded) {
            return false;
        }

        String text = (String) this.invPlayingSounds.get(iSound);
        return text != null
                && (this.sndSystem.playing(text)
                        || this.playingSoundsStopTime.containsKey(text)
                                && (Integer) this.playingSoundsStopTime.get(text) <= this.playTime);
    }

    public static boolean access$302(SoundManager soundManager, boolean loadedValue) {
        return soundManager.loaded = loadedValue;
    }

    public void stopAllSounds() {
        int opaqueControlBranchValue = SoundHandler.getOpaqueControlBranchValue();
        if (this.loaded) {
            for (java.util.Iterator<?> __recoveryIterator171_9840 =
                            (this.playingSounds.keySet()).iterator();
                    (__recoveryIterator171_9840).hasNext(); ) {
                String text = (String) (__recoveryIterator171_9840).next();
                this.sndSystem.stop(text);
                if (opaqueControlBranchValue == 0) {
                    break;
                }
            }

            this.playingSounds.clear();
            this.delayedSounds.clear();
            this.tickableSounds.clear();
            this.categorySounds.clear();
            this.playingSoundPoolEntries.clear();
            this.playingSoundsStopTime.clear();
        }
    }

    private synchronized void loadSoundSystem() {
        if (!this.loaded) {
            try {
                new Thread(new SoundManagerLoadSoundSystemRunnable(this), "Sound Library Loader")
                        .start();
            } catch (RuntimeException runtimeexception) {
                logger.error(
                        LOG_MARKER,
                        "Error starting SoundSystem. Turning off sounds & music",
                        runtimeexception);
                this.options.setSoundLevel(SoundCategory.MASTER, 0.0F);
                this.options.saveOptions();
            }
        }
    }

    private static URL getURLForSoundResource(ResourceLocation resourceLocation) {
        String text =
                String.format(
                        "%s:%s:%s",
                        "mcsounddomain",
                        resourceLocation.getResourceDomain(),
                        resourceLocation.getResourcePath());
        SoundResourceUrlHandler soundResourceUrlHandler =
                new SoundResourceUrlHandler(resourceLocation);

        try {
            return new URL(null, text, soundResourceUrlHandler);
        } catch (MalformedURLException malformedurlexception) {
            throw new Error("TODO: Sanely handle url exception! :D");
        }
    }

    public void pauseAllSounds() {
        int j = SoundHandler.getOpaqueControlState();
        Iterator iterator = this.playingSounds.keySet().iterator();
        int i = j;

        while (iterator.hasNext()) {
            String text = (String) iterator.next();
            logger.debug(LOG_MARKER, "Pausing channel {}", new Object[] {text});
            this.sndSystem.pause(text);
            if (i != 0) {
                break;
            }
        }
    }

    public void unloadSoundSystem() {
        if (this.loaded) {
            this.stopAllSounds();
            this.sndSystem.cleanup();
            this.loaded = false;
        }
    }

    public void reloadSoundSystem() {
        this.unloadSoundSystem();
        this.loadSoundSystem();
    }

    public void playSound(ISound iSound) {
        int opaqueControlBranchValue = SoundHandler.getOpaqueControlBranchValue();
        if (this.loaded) {
            // Vanilla playback must not depend on Remix's unrelated setting state.
            {
                if (this.sndSystem.getMasterVolume() <= 0.0F) {
                    logger.debug(
                            LOG_MARKER,
                            "Skipped playing soundEvent: {}, master volume was zero",
                            new Object[] {iSound.getSoundLocation()});
                    if (opaqueControlBranchValue != 0) {
                        return;
                    }
                }

                SoundEventAccessorComposite sound =
                        this.sndHandler.getSound(iSound.getSoundLocation());
                if (sound != null) {
                    SoundPoolEntry soundPoolEntry = (SoundPoolEntry) sound.cloneEntry();
                    if (soundPoolEntry == SoundHandler.missing_sound) {
                        logger.warn(
                                LOG_MARKER,
                                "Unable to play empty soundEvent: {}",
                                new Object[] {sound.getSoundEventLocation()});
                        if (opaqueControlBranchValue != 0) {
                            return;
                        }
                    }

                    float fx = iSound.getVolume();
                    float f1 = 16.0F;
                    if (fx > 1.0F) {
                        f1 *= fx;
                    }

                    SoundCategory soundCategory = sound.getSoundCategory();
                    float normalizedVolume =
                            this.getNormalizedVolume(iSound, soundPoolEntry, soundCategory);
                    double normalizedPitch = this.getNormalizedPitch(iSound, soundPoolEntry);
                    ResourceLocation soundPoolEntryLocation =
                            soundPoolEntry.getSoundPoolEntryLocation();
                    if (iSound.getSoundLocation().getResourcePath().contains("thunder")) {}

                    if (normalizedVolume == 0.0F) {
                        logger.debug(
                                LOG_MARKER,
                                "Skipped playing sound {}, volume was zero.",
                                new Object[] {soundPoolEntryLocation});
                        if (opaqueControlBranchValue != 0) {
                            return;
                        }
                    }

                    String text;
                    label44:
                    {
                        int j = iSound.canRepeat() && iSound.getRepeatDelay() == 0 ? 1 : 0;
                        text = MathHelper.randomUuid(ThreadLocalRandom.current()).toString();
                        if (soundPoolEntry.isStreamingSound()) {
                            this.sndSystem.newStreamingSource(
                                    false,
                                    text,
                                    getURLForSoundResource(soundPoolEntryLocation),
                                    soundPoolEntryLocation.toString(),
                                    remix.compat.compiler.JvmBooleanSlots.fromInt(j),
                                    iSound.getXPosF(),
                                    iSound.getYPosF(),
                                    iSound.getZPosF(),
                                    iSound.getAttenuationType().getTypeInt(),
                                    f1);
                            if (opaqueControlBranchValue != 0) {
                                break label44;
                            }
                        }

                        this.sndSystem.newSource(
                                false,
                                text,
                                getURLForSoundResource(soundPoolEntryLocation),
                                soundPoolEntryLocation.toString(),
                                remix.compat.compiler.JvmBooleanSlots.fromInt(j),
                                iSound.getXPosF(),
                                iSound.getYPosF(),
                                iSound.getZPosF(),
                                iSound.getAttenuationType().getTypeInt(),
                                f1);
                    }

                    logger.debug(
                            LOG_MARKER,
                            "Playing sound {} for event {} as channel {}",
                            new Object[] {
                                soundPoolEntry.getSoundPoolEntryLocation(),
                                sound.getSoundEventLocation(),
                                text
                            });
                    this.sndSystem.setPitch(text, (float) normalizedPitch);
                    this.sndSystem.setVolume(text, normalizedVolume);
                    this.sndSystem.play(text);
                    this.playingSoundsStopTime.put(text, this.playTime + 20);
                    this.playingSounds.put(text, iSound);
                    this.playingSoundPoolEntries.put(iSound, soundPoolEntry);
                    if (soundCategory != SoundCategory.MASTER) {
                        this.categorySounds.put(soundCategory, text);
                    }

                    if (iSound instanceof ITickableSound) {
                        this.tickableSounds.add(iSound);
                    }
                }
            }
        }
    }

    private float getNormalizedVolume(
            ISound iSound, SoundPoolEntry soundPoolEntry, SoundCategory soundCategory) {
        return (float) MathHelper.clamp(iSound.getVolume() * soundPoolEntry.getVolume(), 0.0, 1.0)
                * this.getSoundCategoryVolume(soundCategory);
    }

    public void stopSound(ISound iSound) {
        if (this.loaded) {
            String text = (String) this.invPlayingSounds.get(iSound);
            if (text != null) {
                this.sndSystem.stop(text);
            }
        }
    }

    public void updateAllSounds() {
        int l = SoundHandler.getOpaqueControlBranchValue();
        this.playTime++;
        Set<String> playback = this.loaded && !this.playingSounds.isEmpty()
                ? this.sndSystem.requestPlaybackSnapshot() : null;
        Iterator iterator = this.tickableSounds.iterator();
        int i = l;

        while (iterator.hasNext()) {
            label70:
            {
                ITickableSound iTickableSound = (ITickableSound) iterator.next();
                iTickableSound.update();
                if (iTickableSound.isDonePlaying()) {
                    this.stopSound(iTickableSound);
                    if (i != 0) {
                        break label70;
                    }
                }

                String text = (String) this.invPlayingSounds.get(iTickableSound);
                this.sndSystem.setVolume(
                        text,
                        this.getNormalizedVolume(
                                iTickableSound,
                                (SoundPoolEntry) this.playingSoundPoolEntries.get(iTickableSound),
                                this.sndHandler
                                        .getSound(iTickableSound.getSoundLocation())
                                        .getSoundCategory()));
                this.sndSystem.setPitch(
                        text,
                        this.getNormalizedPitch(
                                iTickableSound,
                                (SoundPoolEntry) this.playingSoundPoolEntries.get(iTickableSound)));
                this.sndSystem.setPosition(
                        text,
                        iTickableSound.getXPosF(),
                        iTickableSound.getYPosF(),
                        iTickableSound.getZPosF());
            }

            if (i == 0) {
                break;
            }
        }

        iterator = this.playingSounds.entrySet().iterator();
        // An unfinished audio pass cannot tell us which sounds have stopped.
        while (playback != null && iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            String key = (String) entry.getKey();
            ISound value = (ISound) entry.getValue();
            if (!playback.contains(key)) {
                int j = (Integer) this.playingSoundsStopTime.get(key);
                if (j <= this.playTime) {
                    int k = value.getRepeatDelay();
                    if (value.canRepeat() && k > 0) {
                        this.delayedSounds.put(value, this.playTime + k);
                    }

                    iterator.remove();
                    logger.debug(
                            LOG_MARKER,
                            "Removed channel {} because it's not playing anymore",
                            new Object[] {key});
                    this.sndSystem.removeSource(key);
                    this.playingSoundsStopTime.remove(key);
                    this.playingSoundPoolEntries.remove(value);

                    try {
                        this.categorySounds.remove(
                                this.sndHandler
                                        .getSound(value.getSoundLocation())
                                        .getSoundCategory(),
                                key);
                    } catch (RuntimeException runtimeexception) {
                    }

                    if (value instanceof ITickableSound) {
                        this.tickableSounds.remove(value);
                    }
                }
            }

            if (i == 0) {
                break;
            }
        }

        Iterator iteratorValue = this.delayedSounds.entrySet().iterator();

        while (iteratorValue.hasNext()) {
            Entry entry1 = (Entry) iteratorValue.next();
            if (this.playTime >= (Integer) entry1.getValue()) {
                ISound keyValue = (ISound) entry1.getKey();
                if (keyValue instanceof ITickableSound) {
                    ((ITickableSound) keyValue).update();
                }

                this.playSound(keyValue);
                iteratorValue.remove();
            }

            if (i == 0) {
                break;
            }
        }
    }

    public static Logger access$000() {
        return logger;
    }

    public void setListener(PlayerEntity playerEntity, float f) {
        if (this.loaded && playerEntity != null) {
            float f1 =
                    playerEntity.prevRotationPitch
                            + (playerEntity.rotationPitch - playerEntity.prevRotationPitch) * f;
            float f2 =
                    playerEntity.prevRotationYaw
                            + (playerEntity.rotationYaw - playerEntity.prevRotationYaw) * f;
            double d0 = playerEntity.prevPosX + (playerEntity.posX - playerEntity.prevPosX) * f;
            double d1 =
                    playerEntity.prevPosY
                            + (playerEntity.posY - playerEntity.prevPosY) * f
                            + playerEntity.getEyeHeight();
            double d2 = playerEntity.prevPosZ + (playerEntity.posZ - playerEntity.prevPosZ) * f;
            float f3 = MathHelper.cos((f2 + 90.0F) * (float) (Math.PI / 180.0));
            float f4 = MathHelper.sin((f2 + 90.0F) * (float) (Math.PI / 180.0));
            float f5 = MathHelper.cos(-f1 * (float) (Math.PI / 180.0));
            float f6 = MathHelper.sin(-f1 * (float) (Math.PI / 180.0));
            float f7 = MathHelper.cos((-f1 + 90.0F) * (float) (Math.PI / 180.0));
            float f8 = MathHelper.sin((-f1 + 90.0F) * (float) (Math.PI / 180.0));
            float f9 = f3 * f5;
            float f10 = f4 * f5;
            float f11 = f3 * f7;
            float f12 = f4 * f7;
            this.sndSystem.setListenerPose(
                    (float) d0, (float) d1, (float) d2, f9, f6, f10, f11, f8, f12);
        }
    }

    public static GameSettings access$400(SoundManager soundManager) {
        return soundManager.options;
    }

    private float getSoundCategoryVolume(SoundCategory soundCategory) {
        return soundCategory != null && soundCategory != SoundCategory.MASTER
                ? this.options.getSoundLevel(soundCategory)
                : 1.0F;
    }

    public static SoundSystemStarterThread access$102(
            SoundManager soundManager, SoundSystemStarterThread aqCValue) {
        return soundManager.sndSystem = aqCValue;
    }
}
