package net.minecraft.entity.ai;

import net.minecraft.entity.passive.AnimalEntity;

import java.util.List;

public class EntityAIFollowParent extends EntityAIBase {
    private int delayCounter;
    AnimalEntity parentAnimal;
    double moveSpeed;
    AnimalEntity childAnimal;

    public boolean shouldExecute() {
        String text = EntityAIBase.cJ();
        if (this.childAnimal.getGrowingAge() >= 0) {
            return false;
        }

        List entitiesWithinAABB =
                this.childAnimal.world.getEntitiesWithinAABB(
                        this.childAnimal.getClass(),
                        this.childAnimal.getEntityBoundingBox().expand(8.0, 4.0, 8.0));
        AnimalEntity bhqValue = null;
        double MAX_VALUE = Double.MAX_VALUE;

        for (java.util.Iterator<?> __recoveryIterator171_794 = (entitiesWithinAABB).iterator();
                (__recoveryIterator171_794).hasNext(); ) {
            AnimalEntity animalEntity = (AnimalEntity) (__recoveryIterator171_794).next();
            if (animalEntity.getGrowingAge() >= 0) {
                double distanceSqToEntity = this.childAnimal.getDistanceSqToEntity(animalEntity);
                if (distanceSqToEntity <= MAX_VALUE) {
                    MAX_VALUE = distanceSqToEntity;
                    bhqValue = animalEntity;
                }
            }

            if (text == null) {
                break;
            }
        }

        if (bhqValue == null) {
            return false;
        }

        if (MAX_VALUE < 9.0) {
            return false;
        }

        this.parentAnimal = bhqValue;
        return true;
    }

    public void updateTask() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = 10;
            this.childAnimal
                    .getNavigator()
                    .tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
        }
    }

    public EntityAIFollowParent(AnimalEntity bhrValue, double moveSpeedValue) {
        this.childAnimal = bhrValue;
        this.moveSpeed = moveSpeedValue;
    }

    public boolean continueExecuting() {
        if (this.childAnimal.getGrowingAge() >= 0) {
            return false;
        }

        if (!this.parentAnimal.isEntityAlive()) {
            return false;
        }

        double distanceSqToEntity = this.childAnimal.getDistanceSqToEntity(this.parentAnimal);
        return distanceSqToEntity >= 9.0 && distanceSqToEntity <= 256.0;
    }

    public void resetTask() {
        this.parentAnimal = null;
    }

    public void startExecuting() {
        this.delayCounter = 0;
    }
}
