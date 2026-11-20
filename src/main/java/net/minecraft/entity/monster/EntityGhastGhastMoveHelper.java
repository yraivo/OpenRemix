package net.minecraft.entity.monster;

import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.MathHelper;

class EntityGhastGhastMoveHelper extends EntityMoveHelper {
    EntityGhast bjr;
    private int courseChangeCooldown;
    private EntityGhast parentEntity;

    public void onUpdateMoveHelper() {
        int i = EntitySpiderGroupData.method_7eec03da329a017ec143();
        if (this.update) {
            double deltaPosX = this.posX - this.parentEntity.posX;
            double deltaPosY = this.posY - this.parentEntity.posY;
            double deltaPosZ = this.posZ - this.parentEntity.posZ;
            double d3 = deltaPosX * deltaPosX + deltaPosY * deltaPosY + deltaPosZ * deltaPosZ;
            if (this.courseChangeCooldown-- <= 0) {
                this.courseChangeCooldown =
                        this.courseChangeCooldown + this.parentEntity.getRNG().nextInt(5) + 2;
                d3 = MathHelper.sqrt(d3);
                if (this.isNotColliding(this.posX, this.posY, this.posZ, d3)) {
                    this.parentEntity.motionX += deltaPosX / d3 * 0.1;
                    this.parentEntity.motionY += deltaPosY / d3 * 0.1;
                    this.parentEntity.motionZ += deltaPosZ / d3 * 0.1;
                    if (i != 0) {
                        return;
                    }
                }

                this.update = false;
            }
        }
    }

    private boolean isNotColliding(double d0, double d1, double d2, double d3) {
        double d4 = (d0 - this.parentEntity.posX) / d3;
        double d5 = (d1 - this.parentEntity.posY) / d3;
        int k = EntitySpiderGroupData.method_7eec03da329a017ec143();
        double d6 = (d2 - this.parentEntity.posZ) / d3;
        BoundingBox boundingBox = this.parentEntity.getEntityBoundingBox();
        int j = 1;
        int i = k;

        while (j < d3) {
            boundingBox = boundingBox.offset(d4, d5, d6);
            if (!this.parentEntity.world.getCollidingBoundingBoxes(this.parentEntity, boundingBox).isEmpty()) {
                return false;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return true;
    }

    public EntityGhastGhastMoveHelper(EntityGhast bjrValue) {
        super(bjrValue);
        this.bjr = bjrValue;
        this.parentEntity = this.bjr;
    }
}
