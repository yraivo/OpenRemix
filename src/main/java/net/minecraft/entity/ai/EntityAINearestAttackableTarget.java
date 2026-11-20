package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.LivingEntity;

import java.util.Collections;
import java.util.List;

public class EntityAINearestAttackableTarget extends EntityAITarget {
    private int targetChance;
    protected Class targetClass;
    protected EntityAINearestAttackableTargetSorter theNearestAttackableTargetSorter;
    protected LivingEntity targetEntity;
    protected Predicate targetEntitySelector;

    public EntityAINearestAttackableTarget(
            EntityCreature entityCreature,
            Class oclass,
            int targetChanceValue,
            boolean flag,
            boolean flag1,
            Predicate predicate) {
        super(entityCreature, flag, flag1);
        this.targetClass = oclass;
        this.targetChance = targetChanceValue;
        this.theNearestAttackableTargetSorter =
                new EntityAINearestAttackableTargetSorter(entityCreature);
        this.setMutexBits(1);
        this.targetEntitySelector = new NearestAttackableTargetPredicate(this, predicate);
    }

    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }

    public EntityAINearestAttackableTarget(
            EntityCreature entityCreature, Class oclass, boolean flag, boolean flag1) {
        this(entityCreature, oclass, 10, flag, flag1, (Predicate) null);
    }

    public EntityAINearestAttackableTarget(
            EntityCreature entityCreature, Class oclass, boolean flag) {
        this(entityCreature, oclass, flag, false);
    }

    public boolean shouldExecute() {
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            return false;
        }

        double targetDistance = this.getTargetDistance();
        List entitiesWithinAABB =
                this.taskOwner.world.getEntitiesWithinAABB(
                        this.targetClass,
                        this.taskOwner
                                .getEntityBoundingBox()
                                .expand(targetDistance, 4.0, targetDistance),
                        Predicates.and(this.targetEntitySelector, IEntitySelector.NOT_SPECTATING));
        Collections.sort(entitiesWithinAABB, this.theNearestAttackableTargetSorter);
        if (entitiesWithinAABB.isEmpty()) {
            return false;
        }

        this.targetEntity = (LivingEntity) entitiesWithinAABB.get(0);
        return true;
    }

    // Helper used only by this class.
    static class NearestAttackableTargetPredicate implements com.google.common.base.Predicate {
        com.google.common.base.Predicate field_111103_c;
        net.minecraft.entity.ai.EntityAINearestAttackableTarget field_111102_d;

        public boolean func_179878_a(net.minecraft.entity.LivingEntity livingEntity) {
            if (this.field_111103_c != null && !this.field_111103_c.apply(livingEntity)) {
                return false;
            }

            if (livingEntity instanceof net.minecraft.entity.player.PlayerEntity) {
                double targetDistance = this.field_111102_d.getTargetDistance();
                if (livingEntity.isSneaking()) {
                    targetDistance *= 0.8F;
                }

                if (livingEntity.isInvisible()) {
                    float armorVisibility =
                            ((net.minecraft.entity.player.PlayerEntity) livingEntity)
                                    .getArmorVisibility();
                    if (armorVisibility < 0.1F) {
                        armorVisibility = 0.1F;
                    }

                    targetDistance *= 0.7F * armorVisibility;
                }

                if (livingEntity.getDistanceToEntity(this.field_111102_d.taskOwner)
                        > targetDistance) {
                    return false;
                }
            }

            return this.field_111102_d.isSuitableTarget(livingEntity, false);
        }

        NearestAttackableTargetPredicate(
                net.minecraft.entity.ai.EntityAINearestAttackableTarget bkeValue,
                com.google.common.base.Predicate predicate) {
            this.field_111102_d = bkeValue;
            this.field_111103_c = predicate;
        }

        public boolean apply(java.lang.Object object) {
            return this.func_179878_a((net.minecraft.entity.LivingEntity) object);
        }
    }
}
