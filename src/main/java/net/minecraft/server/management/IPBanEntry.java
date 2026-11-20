package net.minecraft.server.management;

import com.google.gson.JsonObject;

import java.util.Date;

public class IPBanEntry extends BanEntry {

    public IPBanEntry(JsonObject jsonobject) {
        super(getIPFromJson(jsonobject), jsonobject);
    }

    public IPBanEntry(String text, Date date, String textValue, Date date1, String textValue2) {
        super(text, date, textValue, date1, textValue2);
    }

    public IPBanEntry(String text) {
        this(text, (Date) null, (String) null, (Date) null, (String) null);
    }

    protected void onSerialization(JsonObject jsonobject) {
        if (this.getValue() != null) {
            jsonobject.addProperty("ip", (String) this.getValue());
            super.onSerialization(jsonobject);
        }
    }

    private static String getIPFromJson(JsonObject jsonobject) {
        return jsonobject.has("ip") ? jsonobject.get("ip").getAsString() : null;
    }


}
