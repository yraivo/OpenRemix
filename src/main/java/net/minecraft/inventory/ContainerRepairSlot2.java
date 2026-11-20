package net.minecraft.inventory;

import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerRepairSlot2 extends Slot {
    ContainerRepair buh;
    BlockPos aOj;
    World bui;

    public ContainerRepairSlot2(
            ContainerRepair buhValue,
            IInventory iInventory,
            int i,
            int j,
            int k,
            World buiValue,
            BlockPos aOjValue) {
        super(iInventory, i, j, k);
        this.buh = buhValue;
        this.bui = buiValue;
        this.aOj = aOjValue;
    }

    public boolean canTakeStack(PlayerEntity playerEntity) {
        return (playerEntity.capabilities.isCreativeMode
                        || playerEntity.experienceLevel >= this.buh.maximumCost)
                && this.buh.maximumCost > 0
                && this.getHasStack();
    }

    public void onPickupFromSlot(PlayerEntity playerEntity, ItemStack itemStack) {
        int i = Container.cR();
        if (!playerEntity.capabilities.isCreativeMode) {
            playerEntity.addExperienceLevel(-this.buh.maximumCost);
        }

        label48:
        {
            ContainerRepair.access$000(this.buh).setInventorySlotContents(0, (ItemStack) null);
            if (ContainerRepair.access$100(this.buh) > 0) {
                {
                    ItemStack itemStackValue =
                            ContainerRepair.access$000(this.buh).getStackInSlot(1);
                    if (itemStackValue != null
                            && itemStackValue.stackSize > ContainerRepair.access$100(this.buh)) {
                        itemStackValue.stackSize =
                                itemStackValue.stackSize - ContainerRepair.access$100(this.buh);
                        ContainerRepair.access$000(this.buh)
                                .setInventorySlotContents(1, itemStackValue);
                        if (!(i == 0)) {

                            ContainerRepair.access$000(this.buh)
                                    .setInventorySlotContents(1, (ItemStack) null);
                        }
                    } else {

                        ContainerRepair.access$000(this.buh)
                                .setInventorySlotContents(1, (ItemStack) null);
                    }
                }

                if (i == 0) {
                    break label48;
                }
            }

            ContainerRepair.access$000(this.buh).setInventorySlotContents(1, (ItemStack) null);
        }

        this.buh.maximumCost = 0;
        BlockState blockState = this.bui.getBlockState(this.aOj);
        if (!playerEntity.capabilities.isCreativeMode
                && !this.bui.isRemote
                && blockState.getBlock() == Blocks.anvil
                && playerEntity.getRNG().nextFloat() < 0.12F) {
            {
                int j = (Integer) blockState.getValue(BlockAnvil.DAMAGE);
                if (++j > 2) {
                    this.bui.setBlockToAir(this.aOj);
                    this.bui.playAuxSFX(1020, this.aOj, 0);
                    if (!(i == 0)) {

                        this.bui.setBlockState(this.aOj, blockState.withProperty(BlockAnvil.DAMAGE, j), 2);
                        this.bui.playAuxSFX(1021, this.aOj, 0);
                    }
                } else {

                    this.bui.setBlockState(this.aOj, blockState.withProperty(BlockAnvil.DAMAGE, j), 2);
                    this.bui.playAuxSFX(1021, this.aOj, 0);
                }
            }

            if (i == 0) {
                return;
            }
        }

        if (!this.bui.isRemote) {
            this.bui.playAuxSFX(1021, this.aOj, 0);
        }
    }

    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }
}
