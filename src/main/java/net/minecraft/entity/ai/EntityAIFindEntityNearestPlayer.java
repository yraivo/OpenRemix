package net.minecraft.entity.ai;

import com.google.common.base.Predicate;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class EntityAIFindEntityNearestPlayer extends EntityAIBase {
    private static Logger field_179436_a;
    private static String h;
    private EntityLiving field_179434_b;
    private Predicate field_179435_c;
    private LivingEntity field_179433_e;

    private EntityAINearestAttackableTargetSorter field_179432_d;

    public static EntityLiving access$000(
            EntityAIFindEntityNearestPlayer entityAIFindEntityNearestPlayer) {
        return entityAIFindEntityNearestPlayer.field_179434_b;
    }

    static {
        h = "Use NearestAttackableTargetGoal.class for PathfinerMob mobs!";
        net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer.field_179436_a =
                LogManager.getLogger();
    }

    public void startExecuting() {
        this.field_179434_b.setAttackTarget(this.field_179433_e);
        super.startExecuting();
    }

    // region String decoding support

    // endregion

    public EntityAIFindEntityNearestPlayer(EntityLiving bheValue) {
        this.field_179434_b = bheValue;
        if (bheValue instanceof EntityCreature) {
            net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer.field_179436_a.warn(h);
        }

        this.field_179435_c = new EntityPredicate(this);
        this.field_179432_d = new EntityAINearestAttackableTargetSorter(bheValue);
    }

    public void resetTask() {
        this.field_179434_b.setAttackTarget((LivingEntity) null);
        super.startExecuting();
    }

    public double func_179431_f() {
        IAttributeInstance entityAttribute =
                this.field_179434_b.getEntityAttribute(SharedMonsterAttributes.followRange);
        return entityAttribute == null ? 16.0 : entityAttribute.getAttributeValue();
    }

    // region String decoding support

    // endregion

    public boolean continueExecuting() {
        LivingEntity livingEntity = this.field_179434_b.getAttackTarget();
        if (livingEntity == null) {
            return false;
        }

        if (!livingEntity.isEntityAlive()) {
            return false;
        }

        Team team = this.field_179434_b.getTeam();
        Team teamValue = livingEntity.getTeam();
        if (team != null && teamValue == team) {
            return false;
        }

        double d0 = this.func_179431_f();
        return this.field_179434_b.getDistanceSqToEntity(livingEntity) > d0 * d0
                ? false
                : !(livingEntity instanceof EntityPlayerMP)
                        || !((EntityPlayerMP) livingEntity).theItemInWorldManager.isCreative();
    }

    public boolean shouldExecute() {
        double d0 = this.func_179431_f();
        List entitiesWithinAABB =
                this.field_179434_b.world.getEntitiesWithinAABB(
                        PlayerEntity.class,
                        this.field_179434_b.getEntityBoundingBox().expand(d0, 4.0, d0),
                        this.field_179435_c);
        Collections.sort(entitiesWithinAABB, this.field_179432_d);
        if (entitiesWithinAABB.isEmpty()) {
            return false;
        }

        this.field_179433_e = (LivingEntity) entitiesWithinAABB.get(0);
        return true;
    }

    // Helper used only by this class.
    public static class EntityPredicate implements com.google.common.base.Predicate {
        net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer bkd;

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }

        public EntityPredicate(net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer bkdValue) {
            this.bkd = bkdValue;
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            if (!(entity instanceof net.minecraft.entity.player.PlayerEntity)) {
                return false;
            }

            double d0 = this.bkd.func_179431_f();
            if (entity.isSneaking()) {
                d0 *= 0.8F;
            }

            if (entity.isInvisible()) {
                float armorVisibility =
                        ((net.minecraft.entity.player.PlayerEntity) entity).getArmorVisibility();
                if (armorVisibility < 0.1F) {
                    armorVisibility = 0.1F;
                }

                d0 *= 0.7F * armorVisibility;
            }

            return entity.getDistanceToEntity(
                                    net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer
                                            .access$000(this.bkd))
                            > d0
                    ? false
                    : net.minecraft.entity.ai.EntityAITarget.isSuitableTarget(
                            net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer.access$000(
                                    this.bkd),
                            (net.minecraft.entity.LivingEntity) entity,
                            false,
                            true);
        }
    }
}
