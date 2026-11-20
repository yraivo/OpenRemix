package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class EntityDamageSourceIndirect extends EntityDamageSource {

    private Entity indirectEntity;

    public Entity getSourceOfDamage() {
        return this.damageSourceEntity;
    }


    public ChatComponent getDeathMessage(LivingEntity livingEntity) {
        ChatComponent chatComponent =
                this.indirectEntity == null
                        ? this.damageSourceEntity.getDisplayName()
                        : this.indirectEntity.getDisplayName();
        ItemStack itemStack =
                this.indirectEntity instanceof LivingEntity
                        ? ((LivingEntity) this.indirectEntity).getHeldItem()
                        : null;
        String text = "death.attack." + this.damageType;
        String textValue = text + ".item";
        return itemStack != null
                        && itemStack.hasDisplayName()
                        && StatCollector.canTranslate(textValue)
                ? new ChatComponentTranslation(
                        textValue,
                        new Object[] {livingEntity.getDisplayName(), chatComponent, itemStack.getChatComponent()})
                : new ChatComponentTranslation(
                        text, new Object[] {livingEntity.getDisplayName(), chatComponent});
    }

    public EntityDamageSourceIndirect(String text, Entity entity, Entity bFkValue) {
        super(text, entity);
        this.indirectEntity = bFkValue;
    }

    public Entity getEntity() {
        return this.indirectEntity;
    }
}
