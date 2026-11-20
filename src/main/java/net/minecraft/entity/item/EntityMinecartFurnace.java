package net.minecraft.entity.item;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartFurnace extends EntityMinecart {
    public double pushX;

    private int fuel;
    public double pushZ;

    public EntityMinecartFurnace(World worldValue, double d0, double d1, double d2) {
        super(worldValue, d0, d1, d2);
    }

    public EntityMinecartEnumMinecartType getMinecartType() {
        return EntityMinecartEnumMinecartType.FURNACE;
    }

    public boolean interactFirst(PlayerEntity playerEntity) {
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        if (itemStack != null && itemStack.getItem() == Items.coal) {
            if (!playerEntity.capabilities.isCreativeMode && --itemStack.stackSize == 0) {
                playerEntity.inventory.setInventorySlotContents(
                        playerEntity.inventory.currentItem, (ItemStack) null);
            }

            this.fuel += 3600;
        }

        this.pushX = this.posX - playerEntity.posX;
        this.pushZ = this.posZ - playerEntity.posZ;
        return true;
    }

    public void killMinecart(DamageSource damageSource) {
        super.killMinecart(damageSource);
        if (!damageSource.isExplosion()) {
            this.entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0F);
        }
    }

    protected void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.pushX = compoundTag.getDouble("PushX");
        this.pushZ = compoundTag.getDouble("PushZ");
        this.fuel = compoundTag.getShort("Fuel");
    }

    protected double getMaximumSpeed() {
        return 0.2;
    }

    protected boolean isMinecartPowered() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public EntityMinecartFurnace(World worldValue) {
        super(worldValue);
    }

    protected void setMinecartPowered(boolean flag) {
        boolean flag1 = EntityMinecartEnumMinecartType.d();
        if (flag) {
            this.dataWatcher.updateObject(
                    16, (byte) (this.dataWatcher.getWatchableObjectByte(16) | 1));
            if (!flag1) {
                return;
            }
        }

        this.dataWatcher.updateObject(
                16, (byte) (this.dataWatcher.getWatchableObjectByte(16) & -2));
    }

    public BlockState getDefaultDisplayTile() {
        return (this.isMinecartPowered() ? Blocks.lit_furnace : Blocks.furnace)
                .getDefaultState()
                .withProperty(BlockFurnace.FACING, Facing.NORTH);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte) 0));
    }


    public void onUpdate() {
        super.onUpdate();
        if (this.fuel > 0) {
            this.fuel--;
        }

        if (this.fuel <= 0) {
            this.pushX = this.pushZ = 0.0;
        }

        this.setMinecartPowered(this.fuel > 0);
        if (this.isMinecartPowered() && this.rand.nextInt(4) == 0) {
            this.world.spawnParticle(
                    EnumParticleTypes.SMOKE_LARGE,
                    this.posX,
                    this.posY + 0.8,
                    this.posZ,
                    0.0,
                    0.0,
                    0.0,
                    new int[0]);
        }
    }

    protected void applyDrag() {
        {
            boolean flag1 = EntityMinecartEnumMinecartType.d();
            double d0 = this.pushX * this.pushX + this.pushZ * this.pushZ;
            boolean flag = flag1;
            if (d0 > 1.0E-4) {
                d0 = MathHelper.sqrt(d0);
                this.pushX /= d0;
                this.pushZ /= d0;
                double d1 = 1.0;
                this.motionX *= 0.8F;
                this.motionY *= 0.0;
                this.motionZ *= 0.8F;
                this.motionX = this.motionX + this.pushX * d1;
                this.motionZ = this.motionZ + this.pushZ * d1;
                if (!(!flag)) {

                    this.motionX *= 0.98F;
                    this.motionY *= 0.0;
                    this.motionZ *= 0.98F;
                }
            } else {

                this.motionX *= 0.98F;
                this.motionY *= 0.0;
                this.motionZ *= 0.98F;
            }
        }

        super.applyDrag();
    }

    protected void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setDouble("PushX", this.pushX);
        compoundTag.setDouble("PushZ", this.pushZ);
        compoundTag.setShort("Fuel", (short) this.fuel);
    }

    protected void method_b304a2560f61dd0c2107(BlockPos blockPos, BlockState blockState) {
        super.method_b304a2560f61dd0c2107(blockPos, blockState);
        boolean flag1 = EntityMinecartEnumMinecartType.d();
        double d0 = this.pushX * this.pushX + this.pushZ * this.pushZ;
        boolean flag = flag1;
        if (d0 > 1.0E-4 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001) {
            d0 = MathHelper.sqrt(d0);
            this.pushX /= d0;
            this.pushZ /= d0;
            if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0) {
                this.pushX = 0.0;
                this.pushZ = 0.0;
                if (!flag) {
                    return;
                }
            }

            double d1 = d0 / this.getMaximumSpeed();
            this.pushX *= d1;
            this.pushZ *= d1;
        }
    }
}
