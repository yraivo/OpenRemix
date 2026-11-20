package net.minecraft.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAgeable extends EntityCreature {

    private static int aIj;

    private float ageHeight;
    protected int field_175502_b;
    private float ageWidth = -1.0F;
    protected int growingAge;

    protected int field_175503_c;

    public static int cR() {
        int i = aW();
        return i == 0 ? 81 : 0;
    }

    protected final void setSize(float bbLValue, float bbHValue) {
        int i = this.ageWidth > 0.0F ? 1 : 0;
        this.ageWidth = bbLValue;
        this.ageHeight = bbHValue;
        if (i == 0) {
            this.setScale(1.0F);
        }
    }

    public static int aW() {
        return aIj;
    }

    public void setGrowingAge(int bfpValue) {
        this.dataWatcher.updateObject(12, (byte) MathHelper.clamp(bfpValue, -1, 1));
        this.growingAge = bfpValue;
        this.setScaleForAge(this.isChild());
    }

    protected void onGrowingAdult() {}

    // region String decoding support

    // endregion

    public abstract EntityAgeable createChild(EntityAgeable entityAgeable);

    public boolean interact(PlayerEntity playerEntity) {
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        if (itemStack != null && itemStack.getItem() == Items.spawn_egg) {
            if (!this.world.isRemote) {
                Class oclass = EntityList.getClassFromID(itemStack.getItemDamage());
                if (oclass != null && this.getClass() == oclass) {
                    EntityAgeable child = this.createChild(this);
                    if (child != null) {
                        child.setGrowingAge(-24000);
                        child.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
                        this.world.spawnEntityInWorld(child);
                        if (itemStack.hasDisplayName()) {
                            child.setCustomNameTag(itemStack.getDisplayName());
                        }

                        if (!playerEntity.capabilities.isCreativeMode) {
                            itemStack.stackSize--;
                            if (itemStack.stackSize <= 0) {
                                playerEntity.inventory.setInventorySlotContents(
                                        playerEntity.inventory.currentItem, (ItemStack) null);
                            }
                        }
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public void onLivingUpdate() {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        super.onLivingUpdate();
        String[] astring = astring1;
        if (this.world.isRemote) {
            if (this.field_175503_c > 0) {
                if (this.field_175503_c % 4 == 0) {
                    this.world.spawnParticle(
                            EnumParticleTypes.VILLAGER_HAPPY,
                            this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                            this.posY + 0.5 + this.rand.nextFloat() * this.height,
                            this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                }

                this.field_175503_c--;
            }

            this.setScaleForAge(this.isChild());
            if (astring == null) {
                return;
            }
        }

        int growingAge = this.getGrowingAge();
        if (growingAge < 0) {
            this.setGrowingAge(++growingAge);
            if (growingAge != 0) {
                return;
            }

            this.onGrowingAdult();
            if (astring == null) {
                return;
            }
        }

        if (growingAge > 0) {
            this.setGrowingAge(--growingAge);
        }
    }

    public static void method_0fee73c27cd31764df6c(int aIjValue) {
        aIj = aIjValue;
    }

    // region String decoding support

    // endregion

    public EntityAgeable(World worldValue) {
        super(worldValue);
    }

    public void addGrowth(int i) {
        this.func_175501_a(i, false);
    }

    protected final void setScale(float f) {
        super.setSize(this.ageWidth * f, this.ageHeight * f);
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.setGrowingAge(compoundTag.getInteger("Age"));
        this.field_175502_b = compoundTag.getInteger("ForcedAge");
    }

    public boolean isChild() {
        return this.getGrowingAge() < 0;
    }

    // region String decoding support

    // endregion

    public int getGrowingAge() {
        return this.world.isRemote ? this.dataWatcher.getWatchableObjectByte(12) : this.growingAge;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(12, (byte) 0);
    }

    static {
        method_0fee73c27cd31764df6c(23);
    }

    public void setScaleForAge(boolean flag) {
        this.setScale(flag ? 0.5F : 1.0F);
    }

    public void func_175501_a(int i, boolean flag) {
        int j = this.getGrowingAge();
        int k = j;
        j += i * 20;
        if (j > 0) {
            j = 0;
            if (k < 0) {
                this.onGrowingAdult();
            }
        }

        int l = j - k;
        this.setGrowingAge(j);
        if (flag) {
            this.field_175502_b += l;
            if (this.field_175503_c == 0) {
                this.field_175503_c = 40;
            }
        }

        if (this.getGrowingAge() == 0) {
            this.setGrowingAge(this.field_175502_b);
        }
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setInteger("Age", this.getGrowingAge());
        compoundTag.setInteger("ForcedAge", this.field_175502_b);
    }
}
