package net.minecraft.client.audio;

public enum SoundAttenuationType {
    NONE("NONE", 0, 0),
    LINEAR("LINEAR", 1, 2);
    private static SoundAttenuationType[] aBe;

    private int type;

    public int getTypeInt() {
        return this.type;
    }

    // region String decoding support

    // endregion

    SoundAttenuationType(String text, int k, int j) {
        this.type = j;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        aBe =
                new SoundAttenuationType[] {
                    net.minecraft.client.audio.SoundAttenuationType.NONE, net.minecraft.client.audio.SoundAttenuationType.LINEAR
                };
    }
}
