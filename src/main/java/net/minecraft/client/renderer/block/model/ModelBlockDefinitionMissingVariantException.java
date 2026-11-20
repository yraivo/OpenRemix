package net.minecraft.client.renderer.block.model;

public class ModelBlockDefinitionMissingVariantException extends RuntimeException {
    ModelBlockDefinition aOw;

    public ModelBlockDefinitionMissingVariantException(ModelBlockDefinition aOwValue) {
        this.aOw = aOwValue;
    }
}
