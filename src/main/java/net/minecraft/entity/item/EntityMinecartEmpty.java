package net.minecraft.entity.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class EntityMinecartEmpty extends EntityMinecart {
    public EntityMinecartEmpty(World worldValue, double p171_1, double p171_2, double p171_3) {
        super(worldValue, p171_1, p171_2, p171_3);
    }

    public EntityMinecartEmpty(World worldValue) {
        super(worldValue);
    }

    public EntityMinecartEnumMinecartType getMinecartType() {
        return EntityMinecartEnumMinecartType.RIDEABLE;
    }

    public void onActivatorRailPass(int p171_0, int p171_1, int p171_2, boolean p171_3) {
        if (p171_3) {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity((net.minecraft.entity.Entity) null);
            }

            if (this.getRollingAmplitude() == 0) {
                this.setRollingDirection(-this.getRollingDirection());
                this.setRollingAmplitude(10);
                this.setDamage(50.0F);
                this.setBeenAttacked();
            }
        }
    }

    public boolean interactFirst(PlayerEntity playerEntity) {
        if (this.riddenByEntity != null
                && this.riddenByEntity instanceof net.minecraft.entity.player.PlayerEntity
                && this.riddenByEntity != playerEntity) {
            return true;
        }

        if (this.riddenByEntity != null && this.riddenByEntity != playerEntity) {
            return false;
        }

        if (!this.world.isRemote) {
            playerEntity.mountEntity(this);
        }

        return true;
    }
}
