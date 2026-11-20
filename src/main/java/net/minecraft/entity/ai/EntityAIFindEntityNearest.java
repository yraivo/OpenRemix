package net.minecraft.entity.ai;

import com.google.common.base.Predicate;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class EntityAIFindEntityNearest extends EntityAIBase {
    private Predicate field_179443_c;
    private EntityLiving field_179442_b;
    private static Logger field_179444_a;
    private LivingEntity field_179441_e;

    private Class field_179439_f;
    private static String te;
    private EntityAINearestAttackableTargetSorter field_179440_d;

    public void startExecuting() {
        this.field_179442_b.setAttackTarget(this.field_179441_e);
        super.startExecuting();
    }

    // region String decoding support

    // endregion

    public void resetTask() {
        this.field_179442_b.setAttackTarget((LivingEntity) null);
        super.startExecuting();
    }

    public boolean shouldExecute() {
        double d0 = this.func_179438_f();
        List entitiesWithinAABB =
                this.field_179442_b.world.getEntitiesWithinAABB(
                        this.field_179439_f,
                        this.field_179442_b.getEntityBoundingBox().expand(d0, 4.0, d0),
                        this.field_179443_c);
        Collections.sort(entitiesWithinAABB, this.field_179440_d);
        if (entitiesWithinAABB.isEmpty()) {
            return false;
        }

        this.field_179441_e = (LivingEntity) entitiesWithinAABB.get(0);
        return true;
    }

    static {
        te = "Use NearestAttackableTargetGoal.class for PathfinerMob mobs!";
        net.minecraft.entity.ai.EntityAIFindEntityNearest.field_179444_a = LogManager.getLogger();
    }

    public boolean continueExecuting() {
        LivingEntity livingEntity = this.field_179442_b.getAttackTarget();
        if (livingEntity == null) {
            return false;
        }

        if (!livingEntity.isEntityAlive()) {
            return false;
        }

        double d0 = this.func_179438_f();
        return this.field_179442_b.getDistanceSqToEntity(livingEntity) > d0 * d0
                ? false
                : !(livingEntity instanceof EntityPlayerMP)
                        || !((EntityPlayerMP) livingEntity).theItemInWorldManager.isCreative();
    }

    // region String decoding support

    // endregion

    public double func_179438_f() {
        IAttributeInstance entityAttribute =
                this.field_179442_b.getEntityAttribute(SharedMonsterAttributes.followRange);
        return entityAttribute == null ? 16.0 : entityAttribute.getAttributeValue();
    }

    public static EntityLiving access$000(EntityAIFindEntityNearest entityAIFindEntityNearest) {
        return entityAIFindEntityNearest.field_179442_b;
    }

    public EntityAIFindEntityNearest(EntityLiving bheValue, Class oclass) {
        this.field_179442_b = bheValue;
        this.field_179439_f = oclass;
        if (bheValue instanceof EntityCreature) {
            net.minecraft.entity.ai.EntityAIFindEntityNearest.field_179444_a.warn(te);
        }

        this.field_179443_c = new LivingEntityPredicate(this);
        this.field_179440_d = new EntityAINearestAttackableTargetSorter(bheValue);
    }

    // Helper used only by this class.
    public static class LivingEntityPredicate implements com.google.common.base.Predicate {
        net.minecraft.entity.ai.EntityAIFindEntityNearest bkc;

        public LivingEntityPredicate(net.minecraft.entity.ai.EntityAIFindEntityNearest bkcValue) {
            this.bkc = bkcValue;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_17c8fea8d6cc28fb72ce((net.minecraft.entity.LivingEntity) object);
        }

        public boolean method_17c8fea8d6cc28fb72ce(net.minecraft.entity.LivingEntity livingEntity) {
            double d0 = this.bkc.func_179438_f();
            if (livingEntity.isSneaking()) {
                d0 *= 0.8F;
            }

            return livingEntity.isInvisible()
                    ? false
                    : (livingEntity.getDistanceToEntity(
                                            net.minecraft.entity.ai.EntityAIFindEntityNearest
                                                    .access$000(this.bkc))
                                    > d0
                            ? false
                            : net.minecraft.entity.ai.EntityAITarget.isSuitableTarget(
                                    net.minecraft.entity.ai.EntityAIFindEntityNearest.access$000(
                                            this.bkc),
                                    livingEntity,
                                    false,
                                    true));
        }
    }
}
