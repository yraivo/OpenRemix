package net.minecraft.client.resources.data;

import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ChatStyleSerializer;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponentSerializer;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class IMetadataSerializer {
    private GsonBuilder gsonBuilder;
    private static String[] packedTextFragments;
    private IRegistry metadataSectionSerializerRegistry;

    private Gson gson;

    public void registerMetadataSectionType(
            IMetadataSectionSerializer iMetadataSectionSerializer, Class oclass) {
        this.metadataSectionSerializerRegistry.putObject(
                iMetadataSectionSerializer.getSectionName(),
                new IMetadataSerializerRegistration(this, iMetadataSectionSerializer, oclass, null));
        this.gsonBuilder.registerTypeAdapter(oclass, iMetadataSectionSerializer);
        this.gson = null;
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public IMetadataSerializer() {
        boolean flag1 = BaseMetadataSectionSerializer.method_b417d7500ec59d69e373();
        this.metadataSectionSerializerRegistry = new RegistrySimple();
        this.gsonBuilder = new GsonBuilder();
        this.gsonBuilder.registerTypeHierarchyAdapter(
                ChatComponent.class, new IChatComponentSerializer());
        boolean flag = flag1;
        this.gsonBuilder.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyleSerializer());
        this.gsonBuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            BaseMetadataSectionSerializer.method_a8745b073fa8adfa1857(!flag);
        }
    }

    public IMetadataSection parseMetadataSection(String text, JsonObject jsonobject) {
        if (text == null) {
            throw new IllegalArgumentException("Metadata section name cannot be null");
        } else if (!jsonobject.has(text)) {
            return null;
        } else if (!jsonobject.get(text).isJsonObject()) {
            throw new IllegalArgumentException(
                    "Invalid metadata for '"
                            + text
                            + "' - expected object, found "
                            + jsonobject.get(text));
        } else {
            IMetadataSerializerRegistration object =
                    (IMetadataSerializerRegistration)
                            this.metadataSectionSerializerRegistry.getObject(text);
            if (object == null) {
                throw new IllegalArgumentException(
                        "Don't know how to handle metadata section '"
                                + text
                                + packedTextFragments[0]);
            } else {
                return (IMetadataSection)
                        this.getGson().fromJson(jsonobject.getAsJsonObject(text), object.aHC);
            }
        }
    }

    private Gson getGson() {
        if (this.gson == null) {
            this.gson = this.gsonBuilder.create();
        }

        return this.gson;
    }

    static {
        net.minecraft.client.resources.data.IMetadataSerializer.packedTextFragments = new String[3];
        net.minecraft.client.resources.data.IMetadataSerializer.packedTextFragments[0] = new String("'");
    }
}
