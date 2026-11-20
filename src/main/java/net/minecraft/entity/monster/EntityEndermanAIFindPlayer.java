package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Collections;
import java.util.List;

class EntityEndermanAIFindPlayer extends EntityAINearestAttackableTarget {
    private static String s;

    private PlayerEntity player;
    EntityEnderman bky;
    private int field_179451_i;
    private int field_179450_h;
    private EntityEnderman enderman;

    public void updateTask() {
        int i = EntitySpiderGroupData.method_7eec03da329a017ec143();
        if (this.player != null) {
            if (--this.field_179450_h > 0) {
                return;
            }

            this.targetEntity = this.player;
            this.player = null;
            super.startExecuting();
            this.enderman.playSound(s, 1.0F, 1.0F);
            this.enderman.setScreaming(true);
            IAttributeInstance entityAttribute =
                    this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            entityAttribute.applyModifier(EntityEnderman.access$000());
            if (i != 0) {
                return;
            }
        }

        if (this.targetEntity != null) {
            if (this.targetEntity instanceof PlayerEntity
                    && EntityEnderman.access$100(this.enderman, (PlayerEntity) this.targetEntity)) {
                if (this.targetEntity.getDistanceSqToEntity(this.enderman) < 16.0) {
                    this.enderman.teleportRandomly();
                }

                this.field_179451_i = 0;
                if (!(i != 0)) {

                    if (this.targetEntity.getDistanceSqToEntity(this.enderman) > 256.0
                            && this.field_179451_i++ >= 30
                            && this.enderman.teleportToEntity(this.targetEntity)) {
                        this.field_179451_i = 0;
                    }
                }
            } else {

                if (this.targetEntity.getDistanceSqToEntity(this.enderman) > 256.0
                        && this.field_179451_i++ >= 30
                        && this.enderman.teleportToEntity(this.targetEntity)) {
                    this.field_179451_i = 0;
                }
            }
        }

        super.updateTask();
    }

    public void startExecuting() {
        this.field_179450_h = 5;
        this.field_179451_i = 0;
    }

    public EntityEndermanAIFindPlayer(EntityEnderman bkyValue) {
        super(bkyValue, PlayerEntity.class, true);
        this.bky = bkyValue;
        this.enderman = this.bky;
    }

    public boolean shouldExecute() {
        double targetDistance = this.getTargetDistance();
        List entitiesWithinAABB =
                this.taskOwner.world.getEntitiesWithinAABB(
                        PlayerEntity.class,
                        this.taskOwner
                                .getEntityBoundingBox()
                                .expand(targetDistance, 4.0, targetDistance),
                        this.targetEntitySelector);
        Collections.sort(entitiesWithinAABB, this.theNearestAttackableTargetSorter);
        if (entitiesWithinAABB.isEmpty()) {
            return false;
        }

        this.player = (PlayerEntity) entitiesWithinAABB.get(0);
        return true;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        s = "mob.endermen.stare";
    }

    public void resetTask() {
        this.player = null;
        this.enderman.setScreaming(false);
        IAttributeInstance entityAttribute =
                this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        entityAttribute.removeModifier(EntityEnderman.access$000());
        super.resetTask();
    }

    public boolean continueExecuting() {
        if (this.player != null) {
            if (!EntityEnderman.access$100(this.enderman, this.player)) {
                return false;
            }

            EntityEnderman.access$202(this.enderman, true);
            this.enderman.faceEntity(this.player, 10.0F, 10.0F);
            return true;
        } else {
            return super.continueExecuting();
        }
    }
}
