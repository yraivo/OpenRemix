package net.minecraft.entity.ai;

import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

class SpawnerMinecartLogic extends MobSpawnerBaseLogic {
    EntityMinecartMobSpawner field_98296_a;

    public World getSpawnerWorld() {
        return this.field_98296_a.world;
    }

    SpawnerMinecartLogic(EntityMinecartMobSpawner bDKValue) {
        this.field_98296_a = bDKValue;
    }

    public BlockPos getSpawnerPosition() {
        return new BlockPos(this.field_98296_a);
    }

    public void func_98267_a(int i) {
        this.field_98296_a.world.setEntityState(this.field_98296_a, (byte) i);
    }
}
