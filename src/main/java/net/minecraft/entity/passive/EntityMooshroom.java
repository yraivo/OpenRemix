package net.minecraft.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityMooshroom extends EntityCow {
    private static String bfE;

    public boolean interact(PlayerEntity playerEntity) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        String textValue = text;
        if (itemStack != null && itemStack.getItem() == Items.bowl && this.getGrowingAge() >= 0) {
            if (itemStack.stackSize == 1) {
                playerEntity.inventory.setInventorySlotContents(
                        playerEntity.inventory.currentItem, new ItemStack(Items.mushroom_stew));
                return true;
            }

            if (playerEntity.inventory.addItemStackToInventory(new ItemStack(Items.mushroom_stew))
                    && !playerEntity.capabilities.isCreativeMode) {
                playerEntity.inventory.decrStackSize(playerEntity.inventory.currentItem, 1);
                return true;
            }
        }

        if (itemStack != null && itemStack.getItem() == Items.shears && this.getGrowingAge() >= 0) {
            this.setDead();
            this.world.spawnParticle(
                    EnumParticleTypes.EXPLOSION_LARGE,
                    this.posX,
                    this.posY + this.height / 2.0F,
                    this.posZ,
                    0.0,
                    0.0,
                    0.0,
                    new int[0]);
            if (!this.world.isRemote) {
                EntityCow entityCow = new EntityCow(this.world);
                entityCow.setLocationAndAngles(
                        this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                entityCow.setHealth(this.getHealth());
                entityCow.renderYawOffset = this.renderYawOffset;
                if (this.jvm171_d_45ffeb83d14f3c()) {
                    entityCow.setCustomNameTag(this.getCustomNameTag());
                }

                this.world.spawnEntityInWorld(entityCow);
                int i = 0;

                while (i < 5) {
                    this.world.spawnEntityInWorld(
                            new EntityItem(
                                    this.world,
                                    this.posX,
                                    this.posY + this.height,
                                    this.posZ,
                                    new ItemStack(Blocks.red_mushroom)));
                    i++;
                    if (textValue != null) {
                        break;
                    }
                }

                itemStack.damageItem(1, playerEntity);
                this.playSound(bfE, 1.0F, 1.0F);
            }

            return true;
        } else {
            return super.interact(playerEntity);
        }
    }

    public EntityCow method_339102e9ac32aaea0589(EntityAgeable entityAgeable) {
        return this.method_562a5940f64bc7a590e0(entityAgeable);
    }

    static {
        bfE = "mob.sheep.shear";
    }

    public EntityMooshroom(World worldValue) {
        super(worldValue);
        this.setSize(0.9F, 1.3F);
        this.spawnableBlock = Blocks.mycelium;
    }

    // region String decoding support

    // endregion

    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        return this.method_562a5940f64bc7a590e0(entityAgeable);
    }

    // region String decoding support

    // endregion

    public EntityMooshroom method_562a5940f64bc7a590e0(EntityAgeable entityAgeable) {
        return new EntityMooshroom(this.world);
    }
}
