package net.minecraft.entity.monster;

import com.google.common.base.Predicate;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class EntityIronGolemAINearestAttackableTargetNonCreeper
        extends EntityAINearestAttackableTarget {
    public static double method_b2a4465ce98b9922c9d9(
            EntityIronGolemAINearestAttackableTargetNonCreeper
                    entityIronGolemAINearestAttackableTargetNonCreeper) {
        return entityIronGolemAINearestAttackableTargetNonCreeper.getTargetDistance();
    }

    public EntityIronGolemAINearestAttackableTargetNonCreeper(
            EntityCreature entityCreature,
            Class type,
            int p171_2,
            boolean p171_3,
            boolean p171_4,
            Predicate p171_5) {
        super(entityCreature, type, p171_2, p171_3, p171_4, p171_5);
        this.targetEntitySelector = new TargetEntitySelectorPredicate(this, p171_5, entityCreature);
    }

    public static boolean method_3ab6b7804e6b504af01c(
            EntityIronGolemAINearestAttackableTargetNonCreeper p171_0,
            LivingEntity livingEntity,
            boolean p171_2) {
        return p171_0.isSuitableTarget(livingEntity, p171_2);
    }

    // Helper used only by this class.
    public static class TargetEntitySelectorPredicate implements com.google.common.base.Predicate {
        com.google.common.base.Predicate aVj;
        net.minecraft.entity.EntityCreature bsU;
        net.minecraft.entity.monster.EntityIronGolemAINearestAttackableTargetNonCreeper bsV;

        public TargetEntitySelectorPredicate(
                net.minecraft.entity.monster.EntityIronGolemAINearestAttackableTargetNonCreeper
                        bsVValue,
                com.google.common.base.Predicate predicate,
                net.minecraft.entity.EntityCreature bsUValue) {
            this.bsV = bsVValue;
            this.aVj = predicate;
            this.bsU = bsUValue;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_17c8fea8d6cc28fb72ce((net.minecraft.entity.LivingEntity) object);
        }

        public boolean method_17c8fea8d6cc28fb72ce(net.minecraft.entity.LivingEntity livingEntity) {
            if (this.aVj != null && !this.aVj.apply(livingEntity)) {
                return false;
            }

            if (livingEntity instanceof net.minecraft.entity.monster.EntityCreeper) {
                return false;
            }

            if (livingEntity instanceof net.minecraft.entity.player.PlayerEntity) {
                double d0 =
                        net.minecraft.entity.monster
                                .EntityIronGolemAINearestAttackableTargetNonCreeper
                                .method_b2a4465ce98b9922c9d9(this.bsV);
                if (livingEntity.isSneaking()) {
                    d0 *= 0.8F;
                }

                if (livingEntity.isInvisible()) {
                    float armorVisibility =
                            ((net.minecraft.entity.player.PlayerEntity) livingEntity)
                                    .getArmorVisibility();
                    if (armorVisibility < 0.1F) {
                        armorVisibility = 0.1F;
                    }

                    d0 *= 0.7F * armorVisibility;
                }

                if (livingEntity.getDistanceToEntity(this.bsU) > d0) {
                    return false;
                }
            }

            return net.minecraft.entity.monster.EntityIronGolemAINearestAttackableTargetNonCreeper
                    .method_3ab6b7804e6b504af01c(this.bsV, livingEntity, false);
        }
    }
}
