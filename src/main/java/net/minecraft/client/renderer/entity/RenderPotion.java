package net.minecraft.client.renderer.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RenderPotion extends RenderSnowball {
    public ItemStack method_2b542f67197ea3b4b424(EntityPotion entityPotion) {
        return new ItemStack(this.aBh, 1, entityPotion.getPotionDamage());
    }

    public RenderPotion(RenderManager renderManagerValue, RenderItem renderItem) {
        super(renderManagerValue, Items.potionitem, renderItem);
    }

    public ItemStack method_563ca4ec0415e2d82e9d(Entity entity) {
        return this.method_2b542f67197ea3b4b424((net.minecraft.entity.projectile.EntityPotion) entity);
    }
}
