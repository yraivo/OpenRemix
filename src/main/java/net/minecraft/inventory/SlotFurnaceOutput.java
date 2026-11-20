package net.minecraft.inventory;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.MathHelper;

public class SlotFurnaceOutput extends Slot {
    private PlayerEntity thePlayer;
    private int Ao;

    public void onPickupFromSlot(PlayerEntity playerEntity, ItemStack itemStack) {
        this.onCrafting(itemStack);
        super.onPickupFromSlot(playerEntity, itemStack);
    }

    protected void onCrafting(ItemStack itemStack) {
        int i1 = Container.aW();
        itemStack.onCrafting(this.thePlayer.world, this.thePlayer, this.Ao);
        int i = i1;
        if (!this.thePlayer.world.isRemote) {
            int j;
            {
                j = this.Ao;
                float f = FurnaceRecipes.instance().getSmeltingExperience(itemStack);
                if (f == 0.0F) {
                    j = 0;
                    if (!(i != 0)) {

                        if (f < 1.0F) {
                            int k = MathHelper.floor(j * f);
                            if (k < MathHelper.ceil(j * f) && Math.random() < j * f - k) {
                                k++;
                            }

                            j = k;
                        }
                    }
                } else {

                    if (f < 1.0F) {
                        int k = MathHelper.floor(j * f);
                        if (k < MathHelper.ceil(j * f) && Math.random() < j * f - k) {
                            k++;
                        }

                        j = k;
                    }
                }
            }

            while (j > 0) {
                int l = EntityXPOrb.method_c640c0ab3979b883b99e(j);
                j -= l;
                this.thePlayer.world.spawnEntityInWorld(
                        new EntityXPOrb(
                                this.thePlayer.world,
                                this.thePlayer.posX,
                                this.thePlayer.posY + 0.5,
                                this.thePlayer.posZ + 0.5,
                                l));
                if (i == 0) {
                    break;
                }
            }
        }

        this.Ao = 0;
        if (itemStack.getItem() == Items.iron_ingot) {
            this.thePlayer.triggerAchievement(AchievementList.acquireIron);
        }

        if (itemStack.getItem() == Items.cooked_fish) {
            this.thePlayer.triggerAchievement(AchievementList.cookFish);
        }
    }

    protected void onCrafting(ItemStack itemStack, int p171_1) {
        this.Ao += p171_1;
        this.onCrafting(itemStack);
    }

    public ItemStack decrStackSize(int p171_0) {
        if (this.getHasStack()) {
            this.Ao = this.Ao + java.lang.Math.min(p171_0, this.getStack().stackSize);
        }

        return super.decrStackSize(p171_0);
    }

    public SlotFurnaceOutput(
            PlayerEntity btkValue, IInventory iInventory, int p171_2, int p171_3, int p171_4) {
        super(iInventory, p171_2, p171_3, p171_4);
        this.thePlayer = btkValue;
    }

    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }
}
