package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityCaveSpider extends EntitySpider {
    public boolean attackEntityAsMob(Entity entity) {
        int i = EntitySpiderGroupData.method_7eec03da329a017ec143();
        if (!super.attackEntityAsMob(entity)) {
            return false;
        }

        if (entity instanceof LivingEntity) {
            byte b0;
            {
                b0 = 0;
                if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
                    b0 = 7;
                    if (!(i != 0)) {

                        if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                            b0 = 15;
                        }
                    }
                } else {

                    if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                        b0 = 15;
                    }
                }
            }

            if (b0 > 0) {
                ((LivingEntity) entity)
                        .addPotionEffect(new PotionEffect(Potion.POISON.id, b0 * 20, 0));
            }
        }

        return true;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0);
    }

    public float getEyeHeight() {
        return 0.45F;
    }

    public EntityCaveSpider(World worldValue) {
        super(worldValue);
        this.setSize(0.7F, 0.5F);
    }

    public IEntityLivingData onInitialSpawn(
            DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
        return iEntityLivingData;
    }
}
