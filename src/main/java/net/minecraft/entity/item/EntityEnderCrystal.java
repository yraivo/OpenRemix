package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

public class EntityEnderCrystal extends Entity {
    public int innerRotation;
    public int health;

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.innerRotation++;
        this.dataWatcher.updateObject(8, this.health);
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY);
        int k = MathHelper.floor(this.posZ);
        if (this.world.provider instanceof WorldProviderEnd
                && this.world.getBlockState(new BlockPos(i, j, k)).getBlock() != Blocks.fire) {
            this.world.setBlockState(new BlockPos(i, j, k), Blocks.fire.getDefaultState());
        }
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    protected void readEntityFromNBT(CompoundTag compoundTag) {}

    public EntityEnderCrystal(World worldValue, double p171_1, double p171_2, double p171_3) {
        this(worldValue);
        this.setPosition(p171_1, p171_2, p171_3);
    }

    public boolean attackEntityFrom(DamageSource damageSource, float p171_1) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        if (!this.isDead && !this.world.isRemote) {
            this.health = 0;
            if (this.health <= 0) {
                this.setDead();
                if (!this.world.isRemote) {
                    this.world.createExplosion(
                            (net.minecraft.entity.Entity) null, this.posX, this.posY, this.posZ, 6.0F, true);
                }
            }
        }

        return true;
    }

    protected void entityInit() {
        this.dataWatcher.addObject(8, this.health);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public EntityEnderCrystal(World worldValue) {
        super(worldValue);
        this.preventEntitySpawning = true;
        this.setSize(2.0F, 2.0F);
        this.health = 5;
        this.innerRotation = ((int) (((java.util.Random) (this.rand)).nextInt(100000)));
    }

    protected void writeEntityToNBT(CompoundTag compoundTag) {}
}
