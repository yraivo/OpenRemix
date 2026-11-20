package net.minecraft.entity.projectile;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.RayTraceResult;
import net.minecraft.world.World;

public class EntityEgg extends EntityThrowable {
    protected void onImpact(RayTraceResult rayTraceResult) {
        TileEntity.lz();

        if (rayTraceResult.entityHit != null) {
            rayTraceResult.entityHit.attackEntityFrom(
                    DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }

        if (!this.world.isRemote && this.rand.nextInt(8) == 0) {
            byte b0 = 1;
            if (this.rand.nextInt(32) == 0) {
                b0 = 4;
            }

            int i = 0;

            while (i < b0) {
                EntityChicken entityChicken = new EntityChicken(this.world);
                entityChicken.setGrowingAge(-24000);
                entityChicken.setLocationAndAngles(
                        this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                this.world.spawnEntityInWorld(entityChicken);
                i++;
            }
        }

        int j = 0;

        while (j < 8) {
            this.world.spawnParticle(
                    EnumParticleTypes.ITEM_CRACK,
                    this.posX,
                    this.posY,
                    this.posZ,
                    (this.rand.nextFloat() - 0.5) * 0.08,
                    (this.rand.nextFloat() - 0.5) * 0.08,
                    (this.rand.nextFloat() - 0.5) * 0.08,
                    new int[] {Item.getIdFromItem(Items.egg)});
            j++;
        }

        if (!this.world.isRemote) {
            this.setDead();
        }
    }

    public EntityEgg(World worldValue, LivingEntity livingEntity) {
        super(worldValue, livingEntity);
    }

    public EntityEgg(World worldValue, double d0, double d1, double d2) {
        super(worldValue, d0, d1, d2);
    }

    public EntityEgg(World worldValue) {
        super(worldValue);
    }
}
