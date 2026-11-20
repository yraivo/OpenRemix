package net.minecraft.entity.item;

public enum EntityPaintingEnumArt {
    KEBAB("KEBAB", 0, "Kebab", 16, 16, 0, 0),
    AZTEC("AZTEC", 1, "Aztec", 16, 16, 16, 0),
    ALBAN("ALBAN", 2, "Alban", 16, 16, 32, 0),
    AZTEC_2("AZTEC_2", 3, "Aztec2", 16, 16, 48, 0),
    BOMB("BOMB", 4, "Bomb", 16, 16, 64, 0),
    PLANT("PLANT", 5, "Plant", 16, 16, 80, 0),
    WASTELAND("WASTELAND", 6, "Wasteland", 16, 16, 96, 0),
    POOL("POOL", 7, "Pool", 32, 16, 0, 32),
    COURBET("COURBET", 8, "Courbet", 32, 16, 32, 32),
    SEA("SEA", 9, "Sea", 32, 16, 64, 32),
    SUNSET("SUNSET", 10, "Sunset", 32, 16, 96, 32),
    CREEBET("CREEBET", 11, "Creebet", 32, 16, 128, 32),
    WANDERER("WANDERER", 12, "Wanderer", 16, 32, 0, 64),
    GRAHAM("GRAHAM", 13, "Graham", 16, 32, 16, 64),
    MATCH("MATCH", 14, "Match", 32, 32, 0, 128),
    BUST("BUST", 15, "Bust", 32, 32, 32, 128),
    STAGE("STAGE", 16, "Stage", 32, 32, 64, 128),
    VOID("VOID", 17, "Void", 32, 32, 96, 128),
    SKULL_AND_ROSES("SKULL_AND_ROSES", 18, "SkullAndRoses", 32, 32, 128, 128),
    WITHER("WITHER", 19, "Wither", 32, 32, 160, 128),
    FIGHTERS("FIGHTERS", 20, "Fighters", 64, 32, 0, 96),
    POINTER("POINTER", 21, "Pointer", 64, 64, 0, 192),
    PIGSCENE("PIGSCENE", 22, "Pigscene", 64, 64, 64, 192),
    BURNING_SKULL("BURNING_SKULL", 23, "BurningSkull", 64, 64, 128, 192),
    SKELETON("SKELETON", 24, "Skeleton", 64, 48, 192, 64),
    DONKEY_KONG("DONKEY_KONG", 25, "DonkeyKong", 64, 48, 192, 112);
    private static EntityPaintingEnumArt[] afS;

    public static int field_180001_A;
    public int sizeY;
    public String title;
    public int offsetX;
    public int offsetY;
    public int sizeX;

    // region String decoding support

    // endregion

    EntityPaintingEnumArt(
            String text, int j1, String titleValue, int j, int k, int l, int afRValue) {
        this.title = titleValue;
        this.sizeX = j;
        this.sizeY = k;
        this.offsetX = l;
        this.offsetY = afRValue;
    }

    // region String decoding support

    // endregion

    static {
        afS =
                new EntityPaintingEnumArt[] {
                    net.minecraft.entity.item.EntityPaintingEnumArt.KEBAB,
                    net.minecraft.entity.item.EntityPaintingEnumArt.AZTEC,
                    net.minecraft.entity.item.EntityPaintingEnumArt.ALBAN,
                    net.minecraft.entity.item.EntityPaintingEnumArt.AZTEC_2,
                    net.minecraft.entity.item.EntityPaintingEnumArt.BOMB,
                    net.minecraft.entity.item.EntityPaintingEnumArt.PLANT,
                    net.minecraft.entity.item.EntityPaintingEnumArt.WASTELAND,
                    net.minecraft.entity.item.EntityPaintingEnumArt.POOL,
                    net.minecraft.entity.item.EntityPaintingEnumArt.COURBET,
                    net.minecraft.entity.item.EntityPaintingEnumArt.SEA,
                    net.minecraft.entity.item.EntityPaintingEnumArt.SUNSET,
                    net.minecraft.entity.item.EntityPaintingEnumArt.CREEBET,
                    net.minecraft.entity.item.EntityPaintingEnumArt.WANDERER,
                    net.minecraft.entity.item.EntityPaintingEnumArt.GRAHAM,
                    net.minecraft.entity.item.EntityPaintingEnumArt.MATCH,
                    net.minecraft.entity.item.EntityPaintingEnumArt.BUST,
                    net.minecraft.entity.item.EntityPaintingEnumArt.STAGE,
                    net.minecraft.entity.item.EntityPaintingEnumArt.VOID,
                    net.minecraft.entity.item.EntityPaintingEnumArt.SKULL_AND_ROSES,
                    net.minecraft.entity.item.EntityPaintingEnumArt.WITHER,
                    net.minecraft.entity.item.EntityPaintingEnumArt.FIGHTERS,
                    net.minecraft.entity.item.EntityPaintingEnumArt.POINTER,
                    net.minecraft.entity.item.EntityPaintingEnumArt.PIGSCENE,
                    net.minecraft.entity.item.EntityPaintingEnumArt.BURNING_SKULL,
                    net.minecraft.entity.item.EntityPaintingEnumArt.SKELETON,
                    net.minecraft.entity.item.EntityPaintingEnumArt.DONKEY_KONG
                };
        net.minecraft.entity.item.EntityPaintingEnumArt.field_180001_A = "SkullAndRoses".length();
    }
}
