package net.minecraft.client.renderer.block.model;

import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.ResourceLocation;

public class ModelBlockDefinitionVariant {
    private int weight;
    private boolean uvLock;
    private ModelRotation modelRotation;
    private ResourceLocation modelLocation;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ModelBlockDefinitionVariant)) {
            return false;
        }

        ModelBlockDefinitionVariant modelBlockDefinitionVariant =
                (ModelBlockDefinitionVariant) object;
        return this.modelLocation.equals(modelBlockDefinitionVariant.modelLocation)
                && this.modelRotation == modelBlockDefinitionVariant.modelRotation
                && this.uvLock == modelBlockDefinitionVariant.uvLock;
    }

    public ModelRotation getRotation() {
        return this.modelRotation;
    }

    @Override
    public int hashCode() {
        int i = this.modelLocation.hashCode();
        i = 31 * i + (this.modelRotation != null ? this.modelRotation.hashCode() : 0);
        return 31 * i + (this.uvLock ? 1 : 0);
    }

    public ModelBlockDefinitionVariant(
            ResourceLocation resourceLocation,
            ModelRotation aBYValue,
            boolean uvLockValue,
            int weightValue) {
        this.modelLocation = resourceLocation;
        this.modelRotation = aBYValue;
        this.uvLock = uvLockValue;
        this.weight = weightValue;
    }

    public boolean isUvLocked() {
        return this.uvLock;
    }

    public int getWeight() {
        return this.weight;
    }

    public ResourceLocation getModelLocation() {
        return this.modelLocation;
    }
}
