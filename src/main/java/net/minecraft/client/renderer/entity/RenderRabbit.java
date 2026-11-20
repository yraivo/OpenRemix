package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class RenderRabbit extends RenderLiving {
    private static ResourceLocation CAERBANNOG;
    private static ResourceLocation TOAST;
    private static ResourceLocation BROWN;
    private static ResourceLocation WHITE_SPLOTCHED;

    private static ResourceLocation WHITE;
    private static ResourceLocation BLACK;
    private static ResourceLocation GOLD;

    private static ResourceLocation SALT;

    static {
        net.minecraft.client.renderer.entity.RenderRabbit.BROWN = new ResourceLocation("textures/entity/rabbit/brown.png");
        net.minecraft.client.renderer.entity.RenderRabbit.WHITE = new ResourceLocation("textures/entity/rabbit/white.png");
        net.minecraft.client.renderer.entity.RenderRabbit.BLACK = new ResourceLocation("textures/entity/rabbit/black.png");
        net.minecraft.client.renderer.entity.RenderRabbit.SALT = new ResourceLocation("textures/entity/rabbit/gold.png");
        net.minecraft.client.renderer.entity.RenderRabbit.WHITE_SPLOTCHED =
                new ResourceLocation("textures/entity/rabbit/salt.png");
        net.minecraft.client.renderer.entity.RenderRabbit.GOLD =
                new ResourceLocation("textures/entity/rabbit/white_splotched.png");
        net.minecraft.client.renderer.entity.RenderRabbit.TOAST = new ResourceLocation("textures/entity/rabbit/toast.png");
        net.minecraft.client.renderer.entity.RenderRabbit.CAERBANNOG =
                new ResourceLocation("textures/entity/rabbit/caerbannog.png");
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_2629218812d3491f6ad0((EntityRabbit) entity);
    }

    protected ResourceLocation method_2629218812d3491f6ad0(EntityRabbit entityRabbit) {
        String text = EnumChatFormatting.getTextWithoutFormattingCodes(entityRabbit.getName());
        if (text != null && text.equals("Toast")) {
            return net.minecraft.client.renderer.entity.RenderRabbit.TOAST;
        }

        switch (entityRabbit.getRabbitType()) {
            case 0:
            default:
                return net.minecraft.client.renderer.entity.RenderRabbit.BROWN;
            case 1:
                return net.minecraft.client.renderer.entity.RenderRabbit.WHITE;
            case 2:
                return net.minecraft.client.renderer.entity.RenderRabbit.BLACK;
            case 3:
                return net.minecraft.client.renderer.entity.RenderRabbit.GOLD;
            case 4:
                return net.minecraft.client.renderer.entity.RenderRabbit.SALT;
            case 5:
                return net.minecraft.client.renderer.entity.RenderRabbit.WHITE_SPLOTCHED;
            case 99:
                return net.minecraft.client.renderer.entity.RenderRabbit.CAERBANNOG;
        }
    }

    public RenderRabbit(RenderManager renderManagerValue, ModelBase modelBase, float f) {
        super(renderManagerValue, modelBase, f);
    }
}
