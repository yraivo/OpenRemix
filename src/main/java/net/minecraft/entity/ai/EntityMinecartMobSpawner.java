package net.minecraft.entity.ai;

import net.minecraft.block.state.BlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartEnumMinecartType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.world.World;

public class EntityMinecartMobSpawner extends EntityMinecart {
    private final MobSpawnerBaseLogic mobSpawnerLogic = new SpawnerMinecartLogic(this);

    public MobSpawnerBaseLogic getMobSpawnerLogic() {
        return this.mobSpawnerLogic;
    }

    public void handleHealthUpdate(byte b0) {
        this.mobSpawnerLogic.setDelayToMin(b0);
    }

    public EntityMinecartEnumMinecartType getMinecartType() {
        return EntityMinecartEnumMinecartType.SPAWNER;
    }

    protected void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        this.mobSpawnerLogic.writeToNBT(compoundTag);
    }

    public EntityMinecartMobSpawner(World worldValue, double d0, double d1, double d2) {
        super(worldValue, d0, d1, d2);
    }

    public BlockState getDefaultDisplayTile() {
        return Blocks.mob_spawner.getDefaultState();
    }

    protected void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.mobSpawnerLogic.readFromNBT(compoundTag);
    }

    public EntityMinecartMobSpawner(World worldValue) {
        super(worldValue);
    }

    public void onUpdate() {
        super.onUpdate();
        this.mobSpawnerLogic.updateSpawner();
    }
}
