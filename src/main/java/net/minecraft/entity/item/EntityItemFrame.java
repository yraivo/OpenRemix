package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class EntityItemFrame extends EntityHanging {
    private float itemDropChance = 1.0F;

    public void writeEntityToNBT(CompoundTag compoundTag) {
        if (this.getDisplayedItem() != null) {
            compoundTag.setTag("Item", this.getDisplayedItem().writeToNBT(new CompoundTag()));
            compoundTag.setByte("ItemRotation", (byte) this.getRotation());
            compoundTag.setFloat("ItemDropChance", this.itemDropChance);
        }

        super.writeEntityToNBT(compoundTag);
    }

    protected void entityInit() {
        this.getDataWatcher().addObjectByDataType(8, 5);
        this.getDataWatcher().addObject(9, (byte) 0);
    }

    private void func_174865_a(int i, boolean flag) {
        this.getDataWatcher().updateObject(9, (byte) (i % 8));
        if (flag && this.hangingPosition != null) {
            this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.air);
        }
    }

    public void dropItemOrSelf(Entity entity, boolean flag) {
        if (this.world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            ItemStack itemStack = this.getDisplayedItem();
            if (entity instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) entity;
                if (playerEntity.capabilities.isCreativeMode) {
                    this.removeFrameFromMap(itemStack);
                    return;
                }
            }

            if (flag) {
                this.entityDropItem(new ItemStack(Items.item_frame), 0.0F);
            }

            if (itemStack != null && this.rand.nextFloat() < this.itemDropChance) {
                itemStack = itemStack.copy();
                this.removeFrameFromMap(itemStack);
                this.entityDropItem(itemStack, 0.0F);
            }
        }
    }

    public EntityItemFrame(World worldValue) {
        super(worldValue);
    }

    private void removeFrameFromMap(ItemStack itemStack) {
        if (itemStack != null) {
            if (itemStack.getItem() == Items.filled_map) {
                MapData mapData = ((ItemMap) itemStack.getItem()).getMapData(itemStack, this.world);
                mapData.playersVisibleOnMap.remove("frame-" + this.getEntityId());
            }

            itemStack.setItemFrame((EntityItemFrame) null);
        }
    }

    public void setDisplayedItem(ItemStack itemStack) {
        this.setDisplayedItemWithUpdate(itemStack, true);
    }

    public void onBroken(Entity entity) {
        this.dropItemOrSelf(entity, true);
    }

    public boolean interactFirst(PlayerEntity playerEntity) {
        boolean flag = EntityMinecartEnumMinecartType.d();
        if (this.getDisplayedItem() == null) {
            ItemStack heldItem = playerEntity.getHeldItem();
            if (heldItem != null && !this.world.isRemote) {
                this.setDisplayedItem(heldItem);
                if (!playerEntity.capabilities.isCreativeMode && --heldItem.stackSize <= 0) {
                    playerEntity.inventory.setInventorySlotContents(
                            playerEntity.inventory.currentItem, (ItemStack) null);
                }
            }

            if (!flag) {
                return true;
            }
        }

        if (!this.world.isRemote) {
            this.setItemRotation(this.getRotation() + 1);
        }

        return true;
    }

    public boolean isInRangeToRenderDist(double d0) {
        double d1 = 16.0;
        d1 *= 64.0 * this.renderDistanceWeight;
        return d0 < d1 * d1;
    }

    public void setItemRotation(int i) {
        this.func_174865_a(i, true);
    }

    public ItemStack getDisplayedItem() {
        return this.getDataWatcher().getWatchableObjectItemStack(8);
    }

    public float getCollisionBorderSize() {
        return 0.0F;
    }


    public void readEntityFromNBT(CompoundTag compoundTag) {
        CompoundTag compoundTagValue = compoundTag.getCompoundTag("Item");
        if (compoundTagValue != null && !compoundTagValue.hasNoTags()) {
            this.setDisplayedItemWithUpdate(
                    ItemStack.loadItemStackFromNBT(compoundTagValue), false);
            this.func_174865_a(compoundTag.getByte("ItemRotation"), false);
            if (compoundTag.hasKey("ItemDropChance", 99)) {
                this.itemDropChance = compoundTag.getFloat("ItemDropChance");
            }

            if (compoundTag.hasKey("Direction")) {
                this.func_174865_a(this.getRotation() * 2, false);
            }
        }

        super.readEntityFromNBT(compoundTag);
    }

    public int getHeightPixels() {
        return 12;
    }

    public int getRotation() {
        return this.getDataWatcher().getWatchableObjectByte(9);
    }

    private void setDisplayedItemWithUpdate(ItemStack itemStack, boolean flag) {
        if (itemStack != null) {
            itemStack = itemStack.copy();
            itemStack.stackSize = 1;
            itemStack.setItemFrame(this);
        }

        this.getDataWatcher().updateObject(8, itemStack);
        this.getDataWatcher().setObjectWatched(8);
        if (flag && this.hangingPosition != null) {
            this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.air);
        }
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        if (!damageSource.isExplosion() && this.getDisplayedItem() != null) {
            if (!this.world.isRemote) {
                this.dropItemOrSelf(damageSource.getEntity(), false);
                this.setDisplayedItem((ItemStack) null);
            }

            return true;
        } else {
            return super.attackEntityFrom(damageSource, f);
        }
    }

    public int f() {
        return this.getDisplayedItem() == null ? 0 : this.getRotation() % 8 + 1;
    }

    public EntityItemFrame(World worldValue, BlockPos blockPos, Facing facing) {
        super(worldValue, blockPos);
        this.updateFacingWithBoundingBox(facing);
    }

    public int getWidthPixels() {
        return 12;
    }
}
