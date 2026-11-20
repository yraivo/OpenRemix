package net.minecraft.entity.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.RayTraceResult;
import net.minecraft.world.World;

public class EntityExpBottle extends EntityThrowable {
    public EntityExpBottle(World worldValue, LivingEntity livingEntity) {
        super(worldValue, livingEntity);
    }

    public EntityExpBottle(World worldValue, double d0, double d1, double d2) {
        super(worldValue, d0, d1, d2);
    }

    protected float getGravityVelocity() {
        return 0.07F;
    }

    protected void onImpact(RayTraceResult rayTraceResult) {
        boolean flag = EntityMinecartEnumMinecartType.d();
        if (!this.world.isRemote) {
            this.world.playAuxSFX(2002, new BlockPos(this), 0);
            int i = 3 + this.world.rand.nextInt(5) + this.world.rand.nextInt(5);

            while (i > 0) {
                int j = EntityXPOrb.method_c640c0ab3979b883b99e(i);
                i -= j;
                this.world.spawnEntityInWorld(
                        new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
                if (flag) {
                    break;
                }
            }

            this.setDead();
        }
    }

    protected float getVelocity() {
        return 0.7F;
    }

    public EntityExpBottle(World worldValue) {
        super(worldValue);
    }

    protected float getInaccuracy() {
        return -20.0F;
    }
}
