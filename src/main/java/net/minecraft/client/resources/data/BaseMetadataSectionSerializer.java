package net.minecraft.client.resources.data;

public abstract class BaseMetadataSectionSerializer implements IMetadataSectionSerializer {
    private static boolean b;

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        b = flag;
    }

    static {
        if (!method_d87039b07bd6961907f2()) {
            method_a8745b073fa8adfa1857(true);
        }
    }

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = net.minecraft.client.resources.data.BaseMetadataSectionSerializer.method_b417d7500ec59d69e373();
        return !flag;
    }

    public static boolean method_b417d7500ec59d69e373() {
        return b;
    }
}
