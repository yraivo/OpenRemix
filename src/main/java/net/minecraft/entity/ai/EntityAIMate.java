package net.minecraft.entity.ai;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class EntityAIMate extends EntityAIBase {
    int spawnBabyDelay;
    double moveSpeed;
    private AnimalEntity theAnimal;
    private static String h;
    World theWorld;
    private AnimalEntity targetMate;

    private void spawnBaby() {
        String text = EntityAIBase.cJ();
        EntityAgeable child = this.theAnimal.createChild(this.targetMate);
        String textValue = text;
        if (child != null) {
            PlayerEntity playerEntity = this.theAnimal.getPlayerInLove();
            if (playerEntity == null && this.targetMate.getPlayerInLove() != null) {
                playerEntity = this.targetMate.getPlayerInLove();
            }

            if (playerEntity != null) {
                playerEntity.triggerAchievement(StatList.animalsBredStat);
                if (this.theAnimal instanceof EntityCow) {
                    playerEntity.triggerAchievement(AchievementList.breedCow);
                }
            }

            this.theAnimal.setGrowingAge(6000);
            this.targetMate.setGrowingAge(6000);
            this.theAnimal.resetInLove();
            this.targetMate.resetInLove();
            child.setGrowingAge(-24000);
            child.setLocationAndAngles(
                    this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0F, 0.0F);
            this.theWorld.spawnEntityInWorld(child);
            Random random = this.theAnimal.getRNG();
            int i = 0;

            while (i < 7) {
                double d0 = random.nextGaussian() * 0.02;
                double d1 = random.nextGaussian() * 0.02;
                double d2 = random.nextGaussian() * 0.02;
                this.theWorld.spawnParticle(
                        EnumParticleTypes.HEART,
                        this.theAnimal.posX
                                + random.nextFloat() * this.theAnimal.width * 2.0F
                                - this.theAnimal.width,
                        this.theAnimal.posY + 0.5 + random.nextFloat() * this.theAnimal.height,
                        this.theAnimal.posZ
                                + random.nextFloat() * this.theAnimal.width * 2.0F
                                - this.theAnimal.width,
                        d0,
                        d1,
                        d2);
                i++;
                if (textValue == null) {
                    break;
                }
            }

            if (this.theWorld.getGameRules().getGameRuleBooleanValue(h)) {
                this.theWorld.spawnEntityInWorld(
                        new EntityXPOrb(
                                this.theWorld,
                                this.theAnimal.posX,
                                this.theAnimal.posY,
                                this.theAnimal.posZ,
                                random.nextInt(7) + 1));
            }
        }
    }

    static {
        h = "doMobLoot";
    }

    public void resetTask() {
        this.targetMate = null;
        this.spawnBabyDelay = 0;
    }

    public boolean shouldExecute() {
        if (!this.theAnimal.isInLove()) {
            return false;
        }

        this.targetMate = this.getNearbyMate();
        return this.targetMate != null;
    }

    public boolean continueExecuting() {
        return this.targetMate.isEntityAlive()
                && this.targetMate.isInLove()
                && this.spawnBabyDelay < 60;
    }

    public void updateTask() {
        this.theAnimal
                .getLookHelper()
                .setLookPositionWithEntity(
                        this.targetMate, 10.0F, this.theAnimal.getVerticalFaceSpeed());
        this.theAnimal.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
        this.spawnBabyDelay++;
        if (this.spawnBabyDelay >= 60
                && this.theAnimal.getDistanceSqToEntity(this.targetMate) < 9.0) {
            this.spawnBaby();
        }
    }

    public EntityAIMate(AnimalEntity bhrValue, double moveSpeedValue) {
        this.theAnimal = bhrValue;
        this.theWorld = bhrValue.world;
        this.moveSpeed = moveSpeedValue;
        this.setMutexBits(3);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private AnimalEntity getNearbyMate() {
        String text = EntityAIBase.cJ();
        float fx = 8.0F;
        List entitiesWithinAABB =
                this.theWorld.getEntitiesWithinAABB(
                        this.theAnimal.getClass(),
                        this.theAnimal.getEntityBoundingBox().expand(fx, fx, fx));
        String textValue = text;
        double MAX_VALUE = Double.MAX_VALUE;
        AnimalEntity animalEntity = null;

        for (java.util.Iterator<?> __recoveryIterator171_7194 = (entitiesWithinAABB).iterator();
                (__recoveryIterator171_7194).hasNext(); ) {
            AnimalEntity animalEntityValue = (AnimalEntity) (__recoveryIterator171_7194).next();
            if (this.theAnimal.canMateWith(animalEntityValue)
                    && this.theAnimal.getDistanceSqToEntity(animalEntityValue) < MAX_VALUE) {
                animalEntity = animalEntityValue;
                MAX_VALUE = this.theAnimal.getDistanceSqToEntity(animalEntityValue);
            }

            if (textValue == null) {
                break;
            }
        }

        return animalEntity;
    }
}
