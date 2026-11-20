package remix.server.hypixel;

import com.google.gson.annotations.SerializedName;

public final class HypixelLocationData {
    @SerializedName("map")
    private String c;

    private static String f;

    @SerializedName("gametype")
    private String d;

    @SerializedName("server")
    private String a;

    @SerializedName("mode")
    private String b;

    private HypixelGameType hypixelGameType;

    // region String decoding support

    // endregion

    public String getGametype() {
        return this.d;
    }

    public void setHypixelGameType(HypixelGameType hypixelGameType) {
        this.hypixelGameType = hypixelGameType;
    }

    public String getServer() {
        return this.a;
    }

    public String getMode() {
        return this.b;
    }

    // region String decoding support

    // endregion

    public String q() {
        return this.c;
    }

    public HypixelLocationData() {
        this.b = f;
    }

    static {
        f = "lobby";
    }

    public HypixelGameType getHypixelGameType() {
        return this.hypixelGameType;
    }
}
