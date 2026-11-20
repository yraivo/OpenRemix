package net.minecraft.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

class TileSpawnerLogic extends MobSpawnerBaseLogic {
    TileEntityMobSpawner field_150825_a;

    public void setRandomEntity(
            MobSpawnerBaseLogicWeightedRandomMinecart mobSpawnerBaseLogicWeightedRandomMinecart) {
        super.setRandomEntity(mobSpawnerBaseLogicWeightedRandomMinecart);
        if (this.getSpawnerWorld() != null) {
            this.getSpawnerWorld().markBlockForUpdate(this.field_150825_a.pos);
        }
    }

    TileSpawnerLogic(TileEntityMobSpawner bDLValue) {
        this.field_150825_a = bDLValue;
    }

    public World getSpawnerWorld() {
        return this.field_150825_a.worldObj;
    }

    public BlockPos getSpawnerPosition() {
        return this.field_150825_a.pos;
    }

    public void func_98267_a(int i) {
        this.field_150825_a.worldObj.addBlockEvent(
                this.field_150825_a.pos, Blocks.mob_spawner, i, 0);
    }
}
