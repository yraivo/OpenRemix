package net.minecraft.client.renderer.block.model;

final class ModelBlockBookkeep {
    public ModelBlock model;
    public ModelBlock modelExt;
    ModelBlock azm;

    private ModelBlockBookkeep(ModelBlock azkValue) {
        this.azm = azkValue;
        this.model = azkValue;
    }

    ModelBlockBookkeep(ModelBlock modelBlock, Object object) {
        this(modelBlock);
    }
}
