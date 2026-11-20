package net.minecraft.util;

import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class JsonSerializableSet extends ForwardingSet implements IJsonSerializable {
    private final Set underlyingSet = Sets.newHashSet();

    public void fromJson(JsonElement jsonelement) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (jsonelement.isJsonArray()) {
            for (java.util.Iterator<?> __recoveryIterator171_684 =
                            (jsonelement.getAsJsonArray()).iterator();
                    (__recoveryIterator171_684).hasNext(); ) {
                JsonElement jsonelement1 = (JsonElement) (__recoveryIterator171_684).next();
                this.add(jsonelement1.getAsString());
                if (text == null) {
                    break;
                }
            }
        }
    }

    public JsonElement getSerializableElement() {
        JsonArray jsonarray = new JsonArray();
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Iterator iterator = this.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            String textValue2 = (String) iterator.next();
            jsonarray.add(new JsonPrimitive(textValue2));
            if (textValue == null) {
                break;
            }
        }

        return jsonarray;
    }

    protected Set delegate() {
        return this.underlyingSet;
    }

    protected Collection method_6b36bbf6fcb43b7d5198() {
        return this.delegate();
    }

    protected Object method_001a303128cbfb6de2c7() {
        return this.delegate();
    }

    protected java.util.Set jvm171_api_delegate_0() {
        return this.delegate();
    }
}
