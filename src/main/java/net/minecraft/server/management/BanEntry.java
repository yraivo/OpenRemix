package net.minecraft.server.management;

import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BanEntry extends UserListEntry {

    protected String reason;

    protected Date banEndDate;
    public static SimpleDateFormat dateFormat;
    protected Date banStartDate;

    protected String bannedBy;

    protected void onSerialization(JsonObject jsonobject) {
        jsonobject.addProperty(
                "created",
                net.minecraft.server.management.BanEntry.dateFormat.format(this.banStartDate));
        jsonobject.addProperty("source", this.bannedBy);
        jsonobject.addProperty(
                "expires",
                this.banEndDate == null
                        ? "forever"
                        : net.minecraft.server.management.BanEntry.dateFormat.format(
                                this.banEndDate));
        jsonobject.addProperty("reason", this.reason);
    }

    boolean hasBanExpired() {
        return this.banEndDate == null ? false : this.banEndDate.before(new Date());
    }

    public Date getBanEndDate() {
        return this.banEndDate;
    }

    private static ParseException method_e5ed422eec646371f3de(ParseException parseexception) {
        return parseexception;
    }

    static {
        net.minecraft.server.management.BanEntry.dateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    }

    public BanEntry(Object object, Date date, String text, Date date1, String textValue) {
        super(object);
        this.banStartDate = date == null ? new Date() : date;
        this.bannedBy = text == null ? "(Unknown)" : text;
        this.banEndDate = date1;
        this.reason = textValue == null ? "Banned by an operator." : textValue;
    }

    protected BanEntry(Object object, JsonObject jsonobject) {
        super(object, jsonobject);

        Date date;
        try {
            date =
                    jsonobject.has("created")
                            ? net.minecraft.server.management.BanEntry.dateFormat.parse(
                                    jsonobject.get("created").getAsString())
                            : new Date();
        } catch (ParseException parseexception1) {
            date = new Date();
        }

        BanEntry banEntry;
        String text;
        label53:
        {
            try {
                if (false)
                    throw (java.text.ParseException)
                            null; // Original JVM checked handler; no emitted instructions.
                this.banStartDate = date;
                banEntry = this;
                if (jsonobject.has("source")) {
                    text = jsonobject.get("source").getAsString();
                    break label53;
                }
            } catch (ParseException parseexception3) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_e5ed422eec646371f3de(parseexception3));
            }

            text = "(Unknown)";
        }

        banEntry.bannedBy = text;

        Date date1;
        try {
            date1 =
                    jsonobject.has("expires")
                            ? net.minecraft.server.management.BanEntry.dateFormat.parse(
                                    jsonobject.get("expires").getAsString())
                            : null;
        } catch (ParseException parseexception) {
            date1 = null;
        }

        label45:
        {
            try {
                if (false)
                    throw (java.text.ParseException)
                            null; // Original JVM checked handler; no emitted instructions.
                this.banEndDate = date1;
                banEntry = this;
                if (jsonobject.has("reason")) {
                    text = jsonobject.get("reason").getAsString();
                    break label45;
                }
            } catch (ParseException parseexception2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_e5ed422eec646371f3de(parseexception2));
            }

            text = "Banned by an operator.";
        }

        banEntry.reason = text;
    }

    public String getBanReason() {
        return this.reason;
    }
}
