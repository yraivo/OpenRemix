package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class EntityDamageSource extends DamageSource {

    private boolean isThornsDamage = false;
    protected Entity damageSourceEntity;

    public Entity getEntity() {
        return this.damageSourceEntity;
    }

    public boolean isDifficultyScaled() {
        return this.damageSourceEntity != null
                && this.damageSourceEntity instanceof LivingEntity
                && !(this.damageSourceEntity instanceof PlayerEntity);
    }


    public EntityDamageSource(String text, Entity amHValue) {
        super(text);
        this.damageSourceEntity = amHValue;
    }

    public EntityDamageSource setIsThornsDamage() {
        this.isThornsDamage = true;
        return this;
    }

    public ChatComponent getDeathMessage(LivingEntity livingEntity) {
        ItemStack itemStack =
                this.damageSourceEntity instanceof LivingEntity
                        ? ((LivingEntity) this.damageSourceEntity).getHeldItem()
                        : null;
        String text = "death.attack." + this.damageType;
        String textValue = text + ".item";
        return itemStack != null
                        && itemStack.hasDisplayName()
                        && StatCollector.canTranslate(textValue)
                ? new ChatComponentTranslation(
                        textValue,
                        new Object[] {
                            livingEntity.getDisplayName(),
                            this.damageSourceEntity.getDisplayName(),
                            itemStack.getChatComponent()
                        })
                : new ChatComponentTranslation(
                        text,
                        new Object[] {
                            livingEntity.getDisplayName(), this.damageSourceEntity.getDisplayName()
                        });
    }

    public boolean getIsThornsDamage() {
        return this.isThornsDamage;
    }
}
