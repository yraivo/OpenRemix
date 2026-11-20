package optifine;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.LivingEntity;

import java.util.Map;
import java.util.Set;

public class PlayerItemsLayer implements LayerRenderer {
    private RenderPlayer renderPlayer = null;

    private static String b;

    public PlayerItemsLayer(RenderPlayer renderPlayer) {
        this.renderPlayer = renderPlayer;
    }

    public boolean shouldCombineTextures() {
        return false;
    }

    // region String decoding support

    // endregion

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f2,
            float f3,
            float f,
            float f4,
            float f5,
            float f6,
            float f1) {
        this.renderEquippedItems(livingEntity, f1, f);
    }

    // region String decoding support

    // endregion

    public static void register(Map map) {
        int j = ReflectorMethod.f();
        Set set = map.keySet();
        int i = j;
        byte b0 = 0;

        for (Object object : set) {
            Object object1 = map.get(object);
            if (object1 instanceof RenderPlayer) {
                RenderPlayer renderPlayer = (RenderPlayer) object1;
                renderPlayer.addLayer(new PlayerItemsLayer(renderPlayer));
                b0 = 1;
            }

            if (i != 0) {
                break;
            }
        }

        if (b0 == 0) {
            OptiFineConfig.warn(b);
        }
    }

    static {
        b = "PlayerItemsLayer not registered";
    }

    protected void renderEquippedItems(LivingEntity livingEntity, float f, float f1) {
        if (OptiFineConfig.isShowCapes() && livingEntity instanceof AbstractClientPlayer) {
            AbstractClientPlayer abstractClientPlayer =
                    (AbstractClientPlayer) livingEntity;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableRescaleNormal();
            ModelBiped mainModel = (ModelBiped) this.renderPlayer.getMainModel();
            PlayerConfigurations.renderPlayerItems(
                    mainModel, abstractClientPlayer, f, f1);
        }
    }
}
