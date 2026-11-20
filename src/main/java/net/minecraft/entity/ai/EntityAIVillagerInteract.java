package net.minecraft.entity.ai;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class EntityAIVillagerInteract extends EntityAIWatchClosest2 {
    private int interactionDelay;
    private EntityVillager villager;

    public void startExecuting() {
        String text = EntityAIBase.cJ();
        super.startExecuting();
        String textValue = text;
        if (this.villager.canAbondonItems()
                && this.closestEntity instanceof EntityVillager
                && ((EntityVillager) this.closestEntity).iW()) {
            this.interactionDelay = 10;
            if (textValue != null) {
                return;
            }
        }

        this.interactionDelay = 0;
    }

    public void updateTask() {
        String text = EntityAIBase.cJ();
        super.updateTask();
        String textValue = text;
        if (this.interactionDelay > 0) {
            this.interactionDelay--;
            if (this.interactionDelay == 0) {
                InventoryBasic inventoryBasic = this.villager.getVillagerInventory();
                int i = 0;

                while (i < inventoryBasic.getSizeInventory()) {
                    ItemStack itemStack = inventoryBasic.getStackInSlot(i);
                    ItemStack itemStackValue = null;
                    if (itemStack != null) {
                        Item item = itemStack.getItem();
                        if ((item == Items.bread || item == Items.potato || item == Items.carrot)
                                && itemStack.stackSize > 3) {
                            int l = itemStack.stackSize / 2;
                            itemStack.stackSize -= l;
                            itemStackValue = new ItemStack(item, l, itemStack.getItemDamage());
                        } else if (item == Items.wheat && itemStack.stackSize > 5) {
                            int j = itemStack.stackSize / 2 / 3 * 3;
                            int k = j / 3;
                            itemStack.stackSize -= j;
                            itemStackValue = new ItemStack(Items.bread, k, 0);
                        }

                        if (itemStack.stackSize <= 0) {
                            inventoryBasic.setInventorySlotContents(i, (ItemStack) null);
                        }
                    }

                    if (itemStackValue != null) {
                        double d0 = this.villager.posY - 0.3F + this.villager.getEyeHeight();
                        EntityItem entityItem =
                                new EntityItem(
                                        this.villager.world,
                                        this.villager.posX,
                                        d0,
                                        this.villager.posZ,
                                        itemStackValue);
                        float f = 0.3F;
                        float beO = this.villager.rotationYawHead;
                        float rotationPitch = this.villager.rotationPitch;
                        entityItem.motionX =
                                -MathHelper.sin(beO / 180.0F * (float) Math.PI)
                                        * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI)
                                        * f;
                        entityItem.motionZ =
                                MathHelper.cos(beO / 180.0F * (float) Math.PI)
                                        * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI)
                                        * f;
                        entityItem.motionY =
                                -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI) * f
                                        + 0.1F;
                        entityItem.setDefaultPickupDelay();
                        this.villager.world.spawnEntityInWorld(entityItem);
                        if (textValue != null) {
                            break;
                        }
                    }

                    i++;
                    if (textValue == null) {
                        break;
                    }
                }
            }
        }
    }

    public EntityAIVillagerInteract(EntityVillager bjHValue) {
        super(bjHValue, EntityVillager.class, 3.0F, 0.02F);
        this.villager = bjHValue;
    }
}
