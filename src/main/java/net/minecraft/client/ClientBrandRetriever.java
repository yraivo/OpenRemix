package net.minecraft.client;

public class ClientBrandRetriever {
    private static String a;

    public static String getClientModName() {
        return a;
    }

    // region String decoding support

    // endregion

    static {
        a = "fml, forge";
    }

    // region String decoding support

    // endregion
}
