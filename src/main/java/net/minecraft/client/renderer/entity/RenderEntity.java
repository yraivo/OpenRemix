package net.minecraft.client.renderer.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntity extends Render {
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }

    public RenderEntity(RenderManager renderManagerValue) {
        super(renderManagerValue);
    }

    public void doRender(
            Entity entity,
            double p171_1,
            double p171_2,
            double p171_3,
            float p171_4,
            float p171_5) {
        net.minecraft.client.renderer.GlStateManager.pushMatrix();
        net.minecraft.client.renderer.entity.Render.renderOffsetAABB(
                entity.getEntityBoundingBox(),
                p171_1 - entity.lastTickPosX,
                p171_2 - entity.lastTickPosY,
                p171_3 - entity.lastTickPosZ);
        net.minecraft.client.renderer.GlStateManager.popMatrix();
        super.doRender(entity, p171_1, p171_2, p171_3, p171_4, p171_5);
    }
}
