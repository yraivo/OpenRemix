package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class SoundHandler implements IUpdatePlayerListBox, IResourceManagerReloadListener {
    private final SoundRegistry sndRegistry = new SoundRegistry();
    private static String[] packedTextFragments;

    private IResourceManager mcResourceManager;
    private static Logger logger;
    private static Gson GSON;
    private static ParameterizedType TYPE;
    private static int opaqueControlState;

    private SoundManager sndManager;
    public static SoundPoolEntry missing_sound;

    public void stopSound(ISound iSound) {
        this.sndManager.stopSound(iSound);
    }

    public static void setOpaqueControlState(int opaqueControlStateValue) {
        net.minecraft.client.audio.SoundHandler.opaqueControlState = opaqueControlStateValue;
    }

    static {
        net.minecraft.client.audio.SoundHandler.packedTextFragments = new String[3];
        net.minecraft.client.audio.SoundHandler.packedTextFragments[0] = new String(":");
        setOpaqueControlState(0);
        net.minecraft.client.audio.SoundHandler.logger = LogManager.getLogger();
        net.minecraft.client.audio.SoundHandler.GSON =
                new GsonBuilder()
                        .registerTypeAdapter(SoundList.class, new SoundListSerializer())
                        .create();
        net.minecraft.client.audio.SoundHandler.TYPE = new SoundHandlerTypeParameterizedType();
        net.minecraft.client.audio.SoundHandler.missing_sound =
                new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0, 0.0, false);
    }

    public void stopSounds() {
        this.sndManager.stopAllSounds();
    }

    // region String decoding support

    // endregion

    private void loadSoundResource(ResourceLocation resourceLocation, SoundList soundList) {
        int i;
        SoundEventAccessorComposite soundEventAccessorComposite;
        label121:
        {
            i = getOpaqueControlBranchValue();
            int j = !this.sndRegistry.containsKey(resourceLocation) ? 1 : 0;
            if (j == 0 && !soundList.canReplaceExisting()) {
                soundEventAccessorComposite =
                        (SoundEventAccessorComposite) this.sndRegistry.getObject(resourceLocation);
                if (i != 0) {
                    break label121;
                }
            }

            if (j == 0) {
                net.minecraft.client.audio.SoundHandler.logger.debug(
                        "Replaced sound event location {}", new Object[] {resourceLocation});
            }

            soundEventAccessorComposite =
                    new SoundEventAccessorComposite(
                            resourceLocation, 1.0, 1.0, soundList.getSoundCategory());
            this.sndRegistry.registerSound(soundEventAccessorComposite);
        }

        for (java.util.Iterator<?> __recoveryIterator171_8416 =
                        (soundList.getSoundList()).iterator();
                (__recoveryIterator171_8416).hasNext(); ) {
            SoundListSoundEntry soundListSoundEntry =
                    (SoundListSoundEntry) (__recoveryIterator171_8416).next();
            String text = soundListSoundEntry.getSoundEntryName();
            ResourceLocation resourceLocationValue = new ResourceLocation(text);
            String textValue =
                    text.contains(packedTextFragments[0])
                            ? resourceLocationValue.getResourceDomain()
                            : resourceLocation.getResourceDomain();
            Object object;
            switch (soundListSoundEntry.getSoundEntryType()) {
                case FILE:
                    ResourceLocation resourceLocationValue2 =
                            new ResourceLocation(
                                    textValue,
                                    "sounds/" + resourceLocationValue.getResourcePath() + ".ogg");
                    InputStream inputstream = null;

                    try {
                        inputstream =
                                this.mcResourceManager
                                        .getResource(resourceLocationValue2)
                                        .getInputStream();
                    } catch (FileNotFoundException filenotfoundexception) {
                        net.minecraft.client.audio.SoundHandler.logger.warn(
                                "File {} does not exist, cannot add it to event {}",
                                new Object[] {resourceLocationValue2, resourceLocation});
                        continue;
                    } catch (IOException ioexception) {
                        net.minecraft.client.audio.SoundHandler.logger.warn(
                                "Could not load sound file "
                                        + resourceLocationValue2
                                        + ", cannot add it to event "
                                        + resourceLocation,
                                ioexception);
                        continue;
                    } finally {
                        IOUtils.closeQuietly(inputstream);
                    }

                    object =
                            new SoundEventAccessor(
                                    new SoundPoolEntry(
                                            resourceLocationValue2,
                                            soundListSoundEntry.getSoundEntryPitch(),
                                            soundListSoundEntry.getSoundEntryVolume(),
                                            soundListSoundEntry.isStreaming()),
                                    soundListSoundEntry.getSoundEntryWeight());
                    if (i != 0) {
                        break;
                    }
                case SOUND_EVENT:
                    object = new SoundEventReferenceAccessor(this, textValue, soundListSoundEntry);
                    if (i == 0) {
                        throw new IllegalStateException("IN YOU FACE");
                    }
                    break;
                default:
                    throw new IllegalStateException("IN YOU FACE");
            }

            soundEventAccessorComposite.addSoundToEventPool((ISoundEventAccessor) object);
            if (i == 0) {
                break;
            }
        }
    }

    public void playDelayedSound(ISound iSound, int i) {
        this.sndManager.playDelayedSound(iSound, i);
    }

    public static int getOpaqueControlState() {
        return net.minecraft.client.audio.SoundHandler.opaqueControlState;
    }

    protected Map getSoundMap(InputStream inputstream) {
        Map map;
        try {
            map =
                    (Map)
                            net.minecraft.client.audio.SoundHandler.GSON.fromJson(
                                    new InputStreamReader(inputstream),
                                    net.minecraft.client.audio.SoundHandler.TYPE);
        } finally {
            IOUtils.closeQuietly(inputstream);
        }

        return map;
    }

    public SoundEventAccessorComposite getRandomSoundFromCategories(
            SoundCategory... soundCategoryArray) {
        ArrayList arrayList = Lists.newArrayList();
        int j = getOpaqueControlState();
        Iterator iterator = this.sndRegistry.getKeys().iterator();
        int i = j;

        while (iterator.hasNext()) {
            ResourceLocation resourceLocation = (ResourceLocation) iterator.next();
            SoundEventAccessorComposite object =
                    (SoundEventAccessorComposite) this.sndRegistry.getObject(resourceLocation);
            if (ArrayUtils.contains(soundCategoryArray, object.getSoundCategory())) {
                arrayList.add(object);
            }

            if (i != 0) {
                break;
            }
        }

        return arrayList.isEmpty()
                ? null
                : (SoundEventAccessorComposite)
                        arrayList.get(new Random().nextInt(arrayList.size()));
    }

    public void setSoundLevel(SoundCategory soundCategory, float f) {
        if (soundCategory == SoundCategory.MASTER && f <= 0.0F) {
            this.stopSounds();
        }

        this.sndManager.setSoundCategoryVolume(soundCategory, f);
    }

    // region String decoding support

    // endregion

    private static Exception a(Exception exception) {
        return exception;
    }

    public void playSound(ISound iSound) {
        this.sndManager.playSound(iSound);
    }

    public SoundHandler(IResourceManager aqsValue, GameSettings gameSettings) {
        this.mcResourceManager = aqsValue;
        this.sndManager = new SoundManager(this, gameSettings);
    }

    public void onResourceManagerReload(IResourceManager iResourceManager) {
        int j = getOpaqueControlState();
        this.sndManager.reloadSoundSystem();
        this.sndRegistry.clearMap();
        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_14318 =
                        (iResourceManager.getResourceDomains()).iterator();
                (__recoveryIterator171_14318).hasNext(); ) {
            String text = (String) (__recoveryIterator171_14318).next();
            try {
                for (java.util.Iterator<?> __recoveryIterator171_14398 =
                                (iResourceManager.getAllResources(
                                                new ResourceLocation(text, "sounds.json")))
                                        .iterator();
                        (__recoveryIterator171_14398).hasNext(); ) {
                    IResource iResource = (IResource) (__recoveryIterator171_14398).next();
                    try {
                        Map map = this.getSoundMap(iResource.getInputStream());

                        for (java.util.Iterator<?> __recoveryIterator171_14738 =
                                        (map.entrySet()).iterator();
                                (__recoveryIterator171_14738).hasNext(); ) {
                            Entry entry = (Entry) (__recoveryIterator171_14738).next();
                            this.loadSoundResource(
                                    new ResourceLocation(text, (String) entry.getKey()),
                                    (SoundList) entry.getValue());
                            if (i != 0) {
                                break;
                            }
                        }
                    } catch (RuntimeException runtimeexception) {
                        net.minecraft.client.audio.SoundHandler.logger.warn(
                                "Invalid sounds.json", runtimeexception);
                    }

                    if (i != 0) {
                        break;
                    }
                }
            } catch (IOException ioexception) {
            }

            if (i != 0) {
                break;
            }
        }
    }

    public void unloadSounds() {
        this.sndManager.unloadSoundSystem();
    }

    public void pauseSounds() {
        this.sndManager.pauseAllSounds();
    }

    public void resumeSounds() {
        this.sndManager.resumeAllSounds();
    }

    public void setListener(PlayerEntity playerEntity, float f) {
        this.sndManager.setListener(playerEntity, f);
    }

    public static SoundRegistry access$000(SoundHandler soundHandler) {
        return soundHandler.sndRegistry;
    }

    public void update() {
        this.sndManager.updateAllSounds();
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public boolean isSoundPlaying(ISound iSound) {
        return this.sndManager.isSoundPlaying(iSound);
    }

    public SoundEventAccessorComposite getSound(ResourceLocation resourceLocation) {
        return (SoundEventAccessorComposite) this.sndRegistry.getObject(resourceLocation);
    }

    public static int getOpaqueControlBranchValue() {
        int opaqueControlStateValue = getOpaqueControlState();
        return opaqueControlStateValue == 0 ? 81 : 0;
    }
}
