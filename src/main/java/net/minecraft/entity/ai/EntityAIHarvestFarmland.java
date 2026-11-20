package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIHarvestFarmland extends EntityAIMoveToBlock {

    private static String ks;
    private boolean hasFarmItem;
    private EntityVillager theVillager;
    private int field_179501_f;
    private boolean field_179503_e;

    // region String decoding support

    // endregion

    public void startExecuting() {
        super.startExecuting();
    }

    public boolean continueExecuting() {
        return this.field_179501_f >= 0 && super.continueExecuting();
    }

    public void resetTask() {
        super.resetTask();
    }

    public void updateTask() {
        super.updateTask();
        String text = EntityAIBase.cJ();
        this.theVillager
                .getLookHelper()
                .setLookPosition(
                        this.destinationBlock.getX() + 0.5,
                        this.destinationBlock.getY() + 1,
                        this.destinationBlock.getZ() + 0.5,
                        10.0F,
                        this.theVillager.getVerticalFaceSpeed());
        String textValue = text;
        if (this.getIsAboveDestination()) {
            label61:
            {
                World world = this.theVillager.world;
                BlockPos blockPos = this.destinationBlock.offsetUp();
                BlockState blockState = world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (this.field_179501_f == 0
                        && block instanceof BlockCrops
                        && (Integer) blockState.getValue(BlockCrops.AGE) == 7) {
                    world.destroyBlock(blockPos, true);
                    if (textValue != null) {
                        break label61;
                    }
                }

                if (this.field_179501_f == 1 && block == Blocks.air) {
                    InventoryBasic inventoryBasic = this.theVillager.getVillagerInventory();
                    int i = 0;

                    while (i < inventoryBasic.getSizeInventory()) {
                        ItemStack itemStack;
                        byte b0;
                        itemStack = inventoryBasic.getStackInSlot(i);
                        b0 = 0;
                        label53:
                        if (itemStack != null) {
                            if (itemStack.getItem() == Items.wheat_seeds) {
                                world.setBlockState(blockPos, Blocks.wheat.getDefaultState(), 3);
                                b0 = 1;
                                if (textValue != null) {
                                    break label53;
                                }
                            }

                            if (itemStack.getItem() == Items.potato) {
                                world.setBlockState(blockPos, Blocks.potatoes.getDefaultState(), 3);
                                b0 = 1;
                                if (textValue != null) {
                                    break label53;
                                }
                            }

                            if (itemStack.getItem() == Items.carrot) {
                                world.setBlockState(blockPos, Blocks.carrots.getDefaultState(), 3);
                                b0 = 1;
                            }
                        }

                        if (b0 != 0) {
                            itemStack.stackSize--;
                            if (itemStack.stackSize > 0) {
                                break;
                            }

                            inventoryBasic.setInventorySlotContents(i, (ItemStack) null);
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

            this.field_179501_f = -1;
            this.runDelay = 10;
        }
    }

    public EntityAIHarvestFarmland(EntityVillager bkjValue, double d0) {
        super(bkjValue, d0, 16);
        this.theVillager = bkjValue;
    }

    static {
        ks = "mobGriefing";
    }

    protected boolean shouldMoveTo(World world, BlockPos blockPos) {
        Block block = world.getBlockState(blockPos).getBlock();
        if (block == Blocks.farmland) {
            blockPos = blockPos.offsetUp();
            BlockState blockState = world.getBlockState(blockPos);
            block = blockState.getBlock();
            if (block instanceof BlockCrops
                    && (Integer) blockState.getValue(BlockCrops.AGE) == 7
                    && this.field_179503_e
                    && (this.field_179501_f == 0 || this.field_179501_f < 0)) {
                this.field_179501_f = 0;
                return true;
            }

            if (block == Blocks.air
                    && this.hasFarmItem
                    && (this.field_179501_f == 1 || this.field_179501_f < 0)) {
                this.field_179501_f = 1;
                return true;
            }
        }

        return false;
    }

    // region String decoding support

    // endregion

    public boolean shouldExecute() {
        if (this.runDelay <= 0) {
            if (!this.theVillager.world.getGameRules().getGameRuleBooleanValue(ks)) {
                return false;
            }

            this.field_179501_f = -1;
            this.hasFarmItem = this.theVillager.isFarmItemInInventory();
            this.field_179503_e = this.theVillager.iW();
        }

        return super.shouldExecute();
    }
}
