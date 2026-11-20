package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import com.google.common.base.Predicate;

public class IEntitySelectorArmoredMob implements Predicate {
    private ItemStack armor;

    public boolean method_aae6a50880e58b2e8640(Entity entity) {
        if (!entity.isEntityAlive()) {
            return false;
        }

        if (!(entity instanceof LivingEntity)) {
            return false;
        }

        LivingEntity livingEntity = (LivingEntity) entity;
        return livingEntity.getEquipmentInSlot(EntityLiving.getArmorPosition(this.armor)) != null
                ? false
                : (livingEntity instanceof EntityLiving
                        ? ((EntityLiving) livingEntity).canPickUpLoot()
                        : (livingEntity instanceof EntityArmorStand
                                ? true
                                : livingEntity instanceof PlayerEntity));
    }

    public IEntitySelectorArmoredMob(ItemStack itemStack) {
        this.armor = itemStack;
    }

    public boolean apply(Object object) {
        return this.method_aae6a50880e58b2e8640((Entity) object);
    }
}
