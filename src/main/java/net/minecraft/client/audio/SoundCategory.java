package net.minecraft.client.audio;

import com.google.common.collect.Maps;

import java.util.Map;

public enum SoundCategory {
    MASTER("MASTER", 0, "master", 0),
    MUSIC("MUSIC", 1, "music", 1),
    RECORDS("RECORDS", 2, "record", 2),
    WEATHER("WEATHER", 3, "weather", 3),
    BLOCKS("BLOCKS", 4, "block", 4),
    MOBS("MOBS", 5, "hostile", 5),
    ANIMALS("ANIMALS", 6, "neutral", 6),
    PLAYERS("PLAYERS", 7, "player", 7),
    AMBIENT("AMBIENT", 8, "ambient", 8);

    private static SoundCategory[] aEI;

    private int categoryId;
    private String categoryName;
    private static Map NAME_CATEGORY_MAP;
    private static Map ID_CATEGORY_MAP;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getCategoryName() {
        return this.categoryName;
    }

    public static SoundCategory getCategory(String text) {
        return (SoundCategory) net.minecraft.client.audio.SoundCategory.NAME_CATEGORY_MAP.get(text);
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    static {
        aEI =
                new SoundCategory[] {
                    net.minecraft.client.audio.SoundCategory.MASTER,
                    net.minecraft.client.audio.SoundCategory.MUSIC,
                    net.minecraft.client.audio.SoundCategory.RECORDS,
                    net.minecraft.client.audio.SoundCategory.WEATHER,
                    net.minecraft.client.audio.SoundCategory.BLOCKS,
                    net.minecraft.client.audio.SoundCategory.MOBS,
                    net.minecraft.client.audio.SoundCategory.ANIMALS,
                    net.minecraft.client.audio.SoundCategory.PLAYERS,
                    net.minecraft.client.audio.SoundCategory.AMBIENT
                };
        net.minecraft.client.audio.SoundCategory.NAME_CATEGORY_MAP = Maps.newHashMap();
        net.minecraft.client.audio.SoundCategory.ID_CATEGORY_MAP = Maps.newHashMap();
        for (SoundCategory class_577222bf6c2e55d711dd : values()) {
            if (net.minecraft.client.audio.SoundCategory.NAME_CATEGORY_MAP.containsKey(class_577222bf6c2e55d711dd.getCategoryName())
                    || net.minecraft.client.audio.SoundCategory.ID_CATEGORY_MAP.containsKey(class_577222bf6c2e55d711dd.getCategoryId())) {
                throw new Error(
                        "Clash in Sound Category ID & Name pools! Cannot insert "
                                + class_577222bf6c2e55d711dd);
            }

            net.minecraft.client.audio.SoundCategory.NAME_CATEGORY_MAP.put(class_577222bf6c2e55d711dd.getCategoryName(), class_577222bf6c2e55d711dd);
            net.minecraft.client.audio.SoundCategory.ID_CATEGORY_MAP.put(class_577222bf6c2e55d711dd.getCategoryId(), class_577222bf6c2e55d711dd);
        }
    }

    SoundCategory(String text, int k, String textValue, int j) {
        this.categoryName = textValue;
        this.categoryId = j;
    }
}
