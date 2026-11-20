package net.minecraft.server.management;

import net.minecraft.server.management.UserListSaveFileFormatParameterizedType;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class UserList {
    private static ParameterizedType saveFileFormat;

    private boolean lanServer;

    private final Map values = Maps.newHashMap();
    protected Gson gson;
    protected static Logger logger;
    private File saveFile;

    public void setLanServer(boolean lanServerValue) {
        this.lanServer = lanServerValue;
    }

    public void addEntry(UserListEntry userListEntry) {
        this.values.put(this.getObjectKey(userListEntry.getValue()), userListEntry);

        try {
            this.writeChanges();
        } catch (IOException ioexception) {
            net.minecraft.server.management.UserList.logger.warn("Could not save the list after adding a user.", ioexception);
        }
    }

    protected boolean hasEntry(Object object) {
        return this.values.containsKey(this.getObjectKey(object));
    }

    public boolean isLanServer() {
        return this.lanServer;
    }

    protected UserListEntry createEntry(JsonObject jsonobject) {
        return new UserListEntry(null, jsonobject);
    }

    private void removeExpired() {
        ServerConfigurationManager.sN();

        ArrayList arrayList = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_4922 = (this.values.values()).iterator();
                (__recoveryIterator171_4922).hasNext(); ) {
            UserListEntry userListEntry = (UserListEntry) (__recoveryIterator171_4922).next();
            if (userListEntry.hasBanExpired()) {
                arrayList.add(userListEntry.getValue());
            }
        }

        for (Object object : arrayList) {
            this.values.remove(object);
        }
    }

    public String[] getKeys() {
        return ((java.lang.String[]) this.values.keySet().toArray(new String[this.values.size()]));
    }

    public void removeEntry(Object object) {
        this.values.remove(this.getObjectKey(object));

        try {
            this.writeChanges();
        } catch (IOException ioexception) {
            net.minecraft.server.management.UserList.logger.warn(
                    "Could not save the list after removing a user.", ioexception);
        }
    }

    public void writeChanges() throws IOException {
        Collection collection = this.values.values();
        String text = this.gson.toJson(collection);
        BufferedWriter bufferedwriter = null;

        try {
            bufferedwriter = Files.newWriter(this.saveFile, Charsets.UTF_8);
            bufferedwriter.write(text);
        } finally {
            IOUtils.closeQuietly(bufferedwriter);
        }
    }

    protected String getObjectKey(Object object) {
        return object.toString();
    }

    protected Map getValues() {
        return this.values;
    }

    public UserListEntry getEntry(Object object) {
        this.removeExpired();
        return (UserListEntry) this.values.get(this.getObjectKey(object));
    }

    static {
        net.minecraft.server.management.UserList.logger = LogManager.getLogger();
        net.minecraft.server.management.UserList.saveFileFormat = new UserListSaveFileFormatParameterizedType();
    }

    public UserList(File file1) {
        this.lanServer = true;
        this.saveFile = file1;
        GsonBuilder gsonbuilder = new GsonBuilder().setPrettyPrinting();
        gsonbuilder.registerTypeHierarchyAdapter(
                UserListEntry.class, new UserListSerializer(this, null));
        this.gson = gsonbuilder.create();
    }
}
