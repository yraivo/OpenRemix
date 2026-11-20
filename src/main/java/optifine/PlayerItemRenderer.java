package optifine;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class PlayerItemRenderer {
    private int attachTo = 0;
    private ModelRenderer modelRenderer;
    private float scaleFactor = 0.0F;

    public void render(ModelBiped modelBiped, float p171_1) {
        net.minecraft.client.model.ModelRenderer modelRenderer =
                optifine.PlayerItemModel.getAttachModel(modelBiped, this.attachTo);
        if (modelRenderer != null) {
            modelRenderer.postRender(p171_1);
        }

        this.modelRenderer.render(p171_1 * this.scaleFactor);
    }

    public ModelRenderer getModelRenderer() {
        return this.modelRenderer;
    }

    public PlayerItemRenderer(
            int attachToValue, float scaleFactorValue, ModelRenderer modelRenderer) {
        this.attachTo = attachToValue;
        this.scaleFactor = scaleFactorValue;
        this.modelRenderer = modelRenderer;
    }
}
