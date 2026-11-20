package net.minecraft.client.audio;

import net.minecraft.client.audio.SoundHandler;

public enum SoundListSoundEntryType {
    FILE("FILE", 0, "file"),
    SOUND_EVENT("SOUND_EVENT", 1, "event");

    private static SoundListSoundEntryType[] aGT;

    private String a;

    static {
        aGT =
                new SoundListSoundEntryType[] {
                    net.minecraft.client.audio.SoundListSoundEntryType.FILE, net.minecraft.client.audio.SoundListSoundEntryType.SOUND_EVENT
                };
    }

    // region String decoding support

    // endregion

    SoundListSoundEntryType(String text, int j, String textValue) {
        this.a = textValue;
    }

    public static SoundListSoundEntryType an(String text) {
        int l = SoundHandler.getOpaqueControlState();
        SoundListSoundEntryType[] soundListSoundEntryTypeArray = values();
        int j = soundListSoundEntryTypeArray.length;
        int i = l;
        int k = 0;

        while (k < j) {
            SoundListSoundEntryType soundListSoundEntryType = soundListSoundEntryTypeArray[k];
            if (soundListSoundEntryType.a.equals(text)) {
                return soundListSoundEntryType;
            }

            k++;
            if (i != 0) {
                break;
            }
        }

        return null;
    }

    // region String decoding support

    // endregion

}
