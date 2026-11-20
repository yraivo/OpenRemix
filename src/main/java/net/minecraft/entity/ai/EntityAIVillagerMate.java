package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class EntityAIVillagerMate extends EntityAIBase {
    private World worldObj;
    private EntityVillager mate;
    Village villageObj;
    private int matingTimeout;
    private EntityVillager villagerObj;

    public void startExecuting() {
        this.matingTimeout = 300;
        this.villagerObj.setMating(true);
    }

    public boolean shouldExecute() {
        if (this.villagerObj.getGrowingAge() != 0) {
            return false;
        }

        if (this.villagerObj.getRNG().nextInt(500) != 0) {
            return false;
        }

        this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos(this.villagerObj), 0);
        if (this.villageObj == null) {
            return false;
        }

        if (this.checkSufficientDoorsPresentForNewVillager() && this.villagerObj.getIsWillingToMate(true)) {
            Entity entity =
                    this.worldObj.findNearestEntityWithinAABB(
                            EntityVillager.class,
                            this.villagerObj.getEntityBoundingBox().expand(8.0, 3.0, 8.0),
                            this.villagerObj);
            if (entity == null) {
                return false;
            }

            this.mate = (EntityVillager) entity;
            return this.mate.getGrowingAge() == 0 && this.mate.getIsWillingToMate(true);
        } else {
            return false;
        }
    }

    private void giveBirth() {
        EntityVillager entityVillager = this.villagerObj.func_180488_b(this.mate);
        this.mate.setGrowingAge(6000);
        this.villagerObj.setGrowingAge(6000);
        this.mate.setIsWillingToMate(false);
        this.villagerObj.setIsWillingToMate(false);
        entityVillager.setGrowingAge(-24000);
        entityVillager.setLocationAndAngles(
                this.villagerObj.posX, this.villagerObj.posY, this.villagerObj.posZ, 0.0F, 0.0F);
        this.worldObj.spawnEntityInWorld(entityVillager);
        this.worldObj.setEntityState(entityVillager, (byte) 12);
    }

    public void resetTask() {
        this.villageObj = null;
        this.mate = null;
        this.villagerObj.setMating(false);
    }

    public boolean continueExecuting() {
        return this.matingTimeout >= 0
                && this.checkSufficientDoorsPresentForNewVillager()
                && this.villagerObj.getGrowingAge() == 0
                && this.villagerObj.getIsWillingToMate(false);
    }

    public EntityAIVillagerMate(EntityVillager bhkValue) {
        this.villagerObj = bhkValue;
        this.worldObj = bhkValue.world;
        this.setMutexBits(3);
    }

    public void updateTask() {
        {
            String text = EntityAIBase.cJ();
            this.matingTimeout--;
            String textValue = text;
            this.villagerObj.getLookHelper().setLookPositionWithEntity(this.mate, 10.0F, 30.0F);
            if (this.villagerObj.getDistanceSqToEntity(this.mate) > 2.25) {
                this.villagerObj.getNavigator().tryMoveToEntityLiving(this.mate, 0.25);
                if (!(textValue != null)) {

                    if (this.matingTimeout == 0 && this.mate.isMating()) {
                        this.giveBirth();
                    }
                }
            } else {

                if (this.matingTimeout == 0 && this.mate.isMating()) {
                    this.giveBirth();
                }
            }
        }

        if (this.villagerObj.getRNG().nextInt(35) == 0) {
            this.worldObj.setEntityState(this.villagerObj, (byte) 12);
        }
    }

    private boolean checkSufficientDoorsPresentForNewVillager() {
        if (!this.villageObj.isMatingSeason()) {
            return false;
        }

        int i = (int) (this.villageObj.getNumVillageDoors() * 0.35);
        return this.villageObj.getNumVillagers() < i;
    }
}
