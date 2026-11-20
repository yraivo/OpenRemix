package net.minecraft.entity.projectile;

import net.minecraft.entity.LivingEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class EntityPotion extends EntityThrowable {

    private ItemStack potionDamage;

    public EntityPotion(World worldValue) {
        super(worldValue);
    }

    protected float getGravityVelocity() {
        return 0.05F;
    }

    protected void onImpact(RayTraceResult rayTraceResult) {
        TileEntity.lz();

        if (!this.world.isRemote) {
            List list = ItemPotion.getPotionEffects(this.potionDamage);
            if (list != null && !list.isEmpty()) {
                BoundingBox boundingBoxValue = this.getEntityBoundingBox().expand(4.0, 2.0, 4.0);
                List listValue =
                        this.world.getEntitiesWithinAABB(LivingEntity.class, boundingBoxValue);
                if (!listValue.isEmpty()) {
                    for (java.util.Iterator<?> __recoveryIterator171_1513 = (listValue).iterator();
                            (__recoveryIterator171_1513).hasNext(); ) {
                        LivingEntity livingEntity =
                                (LivingEntity) (__recoveryIterator171_1513).next();
                        double distanceSqToEntity = this.getDistanceSqToEntity(livingEntity);
                        if (distanceSqToEntity < 16.0) {
                            double d1 = 1.0 - Math.sqrt(distanceSqToEntity) / 4.0;
                            if (livingEntity == rayTraceResult.entityHit) {
                                d1 = 1.0;
                            }

                            for (java.util.Iterator<?> __recoveryIterator171_1946 =
                                            (list).iterator();
                                    (__recoveryIterator171_1946).hasNext(); ) {
                                PotionEffect potionEffect =
                                        (PotionEffect) (__recoveryIterator171_1946).next();
                                {
                                    int potionID = potionEffect.getPotionID();
                                    if (Potion.potionTypes[potionID]
                                            .isInstant()) {
                                        Potion.potionTypes[potionID].affectEntity(
                                                this,
                                                this.getThrower(),
                                                livingEntity,
                                                potionEffect.getAmplifier(),
                                                d1);

                                        
                                    } else {

                                        int j = (int) (d1 * potionEffect.getDuration() + 0.5);
                                        if (j > 20) {
                                            livingEntity.addPotionEffect(
                                                    new PotionEffect(
                                                            potionID, j, potionEffect.getAmplifier()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            this.world.playAuxSFX(2002, new BlockPos(this), this.getPotionDamage());
            this.setDead();
        }
    }

    public EntityPotion(World worldValue, double d0, double d1, double d2, int i) {
        this(worldValue, d0, d1, d2, new ItemStack(Items.potionitem, 1, i));
    }

    protected float getVelocity() {
        return 0.5F;
    }


    public EntityPotion(World worldValue, LivingEntity livingEntity, int i) {
        this(worldValue, livingEntity, new ItemStack(Items.potionitem, 1, i));
    }

    public int getPotionDamage() {
        if (this.potionDamage == null) {
            this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
        }

        return this.potionDamage.getItemDamage();
    }

    public void setPotionDamage(int i) {
        if (this.potionDamage == null) {
            this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
        }

        this.potionDamage.setItemDamage(i);
    }

    public EntityPotion(World worldValue, LivingEntity livingEntity, ItemStack biYValue) {
        super(worldValue, livingEntity);
        this.potionDamage = biYValue;
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        {
            TileEntity.lz();

            super.readEntityFromNBT(compoundTag);

            if (compoundTag.hasKey("Potion", 10)) {
                this.potionDamage =
                        ItemStack.loadItemStackFromNBT(compoundTag.getCompoundTag("Potion"));

                
            } else {

                this.setPotionDamage(compoundTag.getInteger("potionValue"));
            }
        }

        if (this.potionDamage == null) {
            this.setDead();
        }
    }

    protected float getInaccuracy() {
        return -20.0F;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        if (this.potionDamage != null) {
            compoundTag.setTag("Potion", this.potionDamage.writeToNBT(new CompoundTag()));
        }
    }

    public EntityPotion(World worldValue, double d0, double d1, double d2, ItemStack biYValue) {
        super(worldValue, d0, d1, d2);
        this.potionDamage = biYValue;
    }
}
