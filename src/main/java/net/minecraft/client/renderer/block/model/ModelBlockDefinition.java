package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ModelBlockDefinition {
    private Map mapVariants;
    static final Gson GSON =
            new GsonBuilder()
                    .registerTypeAdapter(
                            ModelBlockDefinition.class, new ModelBlockDefinitionDeserializer())
                    .registerTypeAdapter(
                            ModelBlockDefinitionVariant.class,
                            new ModelBlockDefinitionVariantDeserializer())
                    .create();

    public ModelBlockDefinition(Collection collection) {
        super();
        int initialFlowToken = BakedQuad.cM();

        int j = initialFlowToken;

        int i = j;
        this.mapVariants = Maps.newHashMap();

        for (java.util.Iterator<?> __recoveryIterator171_1144 = (collection).iterator();
                (__recoveryIterator171_1144).hasNext(); ) {
            ModelBlockDefinitionVariants modelBlockDefinitionVariants =
                    (ModelBlockDefinitionVariants) (__recoveryIterator171_1144).next();
            this.mapVariants.put(
                    ModelBlockDefinitionVariants.access$000(modelBlockDefinitionVariants),
                    modelBlockDefinitionVariants);
            if (i == 0) {
                break;
            }
        }
    }

    public ModelBlockDefinitionVariants getVariants(String text) {
        ModelBlockDefinitionVariants modelBlockDefinitionVariants =
                (ModelBlockDefinitionVariants) this.mapVariants.get(text);

        try {
            if (modelBlockDefinitionVariants == null) {
                throw new ModelBlockDefinitionMissingVariantException(this);
            } else {
                return modelBlockDefinitionVariants;
            }
        } catch (
                ModelBlockDefinitionMissingVariantException
                        modelBlockDefinitionMissingVariantException) {
            throw method_05a88eec56c4fe6d05dd(modelBlockDefinitionMissingVariantException);
        }
    }

    @Override
    public int hashCode() {
        return this.mapVariants.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        try {
            if (this == object) {
                return true;
            }
        } catch (
                ModelBlockDefinitionMissingVariantException
                        modelBlockDefinitionMissingVariantException) {
            throw method_05a88eec56c4fe6d05dd(modelBlockDefinitionMissingVariantException);
        }

        if (object instanceof ModelBlockDefinition) {
            ModelBlockDefinition modelBlockDefinition = (ModelBlockDefinition) object;
            return this.mapVariants.equals(modelBlockDefinition.mapVariants);
        } else {
            return false;
        }
    }

    public static ModelBlockDefinition parseFromReader(Reader reader) {
        return (ModelBlockDefinition)
                net.minecraft.client.renderer.block.model.ModelBlockDefinition.GSON.fromJson(
                        reader, ModelBlockDefinition.class);
    }

    public ModelBlockDefinition(List list) {
        super();
        int initialFlowToken = BakedQuad.eR();

        int j = initialFlowToken;

        this.mapVariants = Maps.newHashMap();
        Iterator iterator = list.iterator();
        int i = j;

        while (iterator.hasNext()) {
            ModelBlockDefinition modelBlockDefinition = (ModelBlockDefinition) iterator.next();
            this.mapVariants.putAll(modelBlockDefinition.mapVariants);
            if (i != 0) {
                break;
            }
        }
    }

    private static ModelBlockDefinitionMissingVariantException method_05a88eec56c4fe6d05dd(
            ModelBlockDefinitionMissingVariantException
                    modelBlockDefinitionMissingVariantException) {
        return modelBlockDefinitionMissingVariantException;
    }
}
