package net.minecraft.server.management;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S38PacketPlayerListItemAction;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettingsGameType;

public class ItemInWorldManager {
    private int initialDamage;
    public EntityPlayerMP thisPlayerMP;
    private int durabilityRemainingOnBlock;
    private WorldSettingsGameType gameType = WorldSettingsGameType.NOT_SET;
    private int initialBlockDamage;
    public World theWorld;
    private BlockPos field_180241_i;
    private boolean isDestroyingBlock;
    private boolean receivedFinishDiggingPacket;
    private BlockPos field_180240_f;
    private int curblockDamage;

    public void cancelDestroyingBlock() {
        this.isDestroyingBlock = false;
        this.theWorld.sendBlockBreakProgress(
                this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
    }

    public boolean tryUseItem(PlayerEntity playerEntity, World world, ItemStack itemStack) {
        if (this.gameType == WorldSettingsGameType.SPECTATOR) {
            return false;
        }

        int stackSize = itemStack.stackSize;
        int j = itemStack.getItemDamage();
        ItemStack itemStackValue = itemStack.useItemRightClick(world, playerEntity);
        if (itemStackValue != itemStack
                || itemStackValue != null
                        && (itemStackValue.stackSize != stackSize
                                || itemStackValue.getMaxItemUseDuration() > 0
                                || itemStackValue.getItemDamage() != j)) {
            playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem] =
                    itemStackValue;
            if (this.isCreative()) {
                itemStackValue.stackSize = stackSize;
                if (itemStackValue.isItemStackDamageable()) {
                    itemStackValue.setItemDamage(j);
                }
            }

            if (itemStackValue.stackSize == 0) {
                playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem] = null;
            }

            if (!playerEntity.isUsingItem()) {
                ((EntityPlayerMP) playerEntity)
                        .sendContainerToPlayer(playerEntity.inventoryContainer);
            }

            return true;
        } else {
            return false;
        }
    }

    public ItemInWorldManager(World world) {
        this.field_180240_f = BlockPos.ORIGIN;
        this.field_180241_i = BlockPos.ORIGIN;
        this.durabilityRemainingOnBlock = -1;
        this.theWorld = world;
    }

    public void updateBlockRemoving() {
        ServerConfigurationManager.kr();

        this.curblockDamage++;

        if (this.receivedFinishDiggingPacket) {
            {
                int j = this.curblockDamage - this.initialBlockDamage;
                Block block = this.theWorld.getBlockState(this.field_180241_i).getBlock();
                if (block.getMaterial() == Material.AIR) {
                    this.receivedFinishDiggingPacket = false;

                    
                } else {

                    float f =
                            block.getPlayerRelativeBlockHardness(
                                            this.thisPlayerMP,
                                            this.thisPlayerMP.world,
                                            this.field_180241_i)
                                    * (j + 1);
                    int durabilityRemainingOnBlockValue = (int) (f * 10.0F);
                    if (durabilityRemainingOnBlockValue != this.durabilityRemainingOnBlock) {
                        this.theWorld.sendBlockBreakProgress(
                                this.thisPlayerMP.getEntityId(),
                                this.field_180241_i,
                                durabilityRemainingOnBlockValue);
                        this.durabilityRemainingOnBlock = durabilityRemainingOnBlockValue;
                    }

                    if (f >= 1.0F) {
                        this.receivedFinishDiggingPacket = false;
                        this.tryHarvestBlock(this.field_180241_i);
                    }
                }
            }

            return;
        }

        if (this.isDestroyingBlock) {
            Block blockValue = this.theWorld.getBlockState(this.field_180240_f).getBlock();
            if (blockValue.getMaterial() == Material.AIR) {
                this.theWorld.sendBlockBreakProgress(
                        this.thisPlayerMP.getEntityId(), this.field_180240_f, -1);
                this.durabilityRemainingOnBlock = -1;
                this.isDestroyingBlock = false;

                return;
            }

            int l = this.curblockDamage - this.initialDamage;
            float f1 =
                    blockValue.getPlayerRelativeBlockHardness(
                                    this.thisPlayerMP, this.thisPlayerMP.world, this.field_180241_i)
                            * (l + 1);
            int k = (int) (f1 * 10.0F);
            if (k != this.durabilityRemainingOnBlock) {
                this.theWorld.sendBlockBreakProgress(
                        this.thisPlayerMP.getEntityId(), this.field_180240_f, k);
                this.durabilityRemainingOnBlock = k;
            }
        }
    }

    public void setGameType(WorldSettingsGameType bwKValue) {
        this.gameType = bwKValue;
        bwKValue.configurePlayerCapabilities(this.thisPlayerMP.capabilities);
        this.thisPlayerMP.sendPlayerAbilities();
        this.thisPlayerMP
                .mcServer
                .getConfigurationManager()
                .sendPacketToAllPlayers(
                        new S38PacketPlayerListItem(
                                S38PacketPlayerListItemAction.UPDATE_GAME_MODE,
                                new EntityPlayerMP[] {this.thisPlayerMP}));
    }

    public boolean survivalOrAdventure() {
        return this.gameType.isSurvivalOrAdventure();
    }

    public void setWorld(WorldServer theWorldValue) {
        this.theWorld = theWorldValue;
    }

    public void initializeGameType(WorldSettingsGameType bwKValue) {
        if (this.gameType == WorldSettingsGameType.NOT_SET) {
            this.gameType = bwKValue;
        }

        this.setGameType(this.gameType);
    }

    public boolean isCreative() {
        return this.gameType.isCreative();
    }

    public boolean tryHarvestBlock(BlockPos blockPos) {
        ServerConfigurationManager.kr();

        if (this.gameType.isCreative()
                && this.thisPlayerMP.getHeldItem() != null
                && this.thisPlayerMP.getHeldItem().getItem() instanceof SwordItem) {
            return false;
        }

        BlockState blockState = this.theWorld.getBlockState(blockPos);
        TileEntity tileEntity = this.theWorld.getTileEntity(blockPos);
        if (this.gameType.isAdventure()) {
            if (this.gameType == WorldSettingsGameType.SPECTATOR) {
                return false;
            }

            if (!this.thisPlayerMP.isAllowEdit()) {
                ItemStack currentEquippedItem = this.thisPlayerMP.getCurrentEquippedItem();
                if (currentEquippedItem == null) {
                    return false;
                }

                if (!currentEquippedItem.canDestroy(blockState.getBlock())) {
                    return false;
                }
            }
        }

        this.theWorld.playAuxSFXAtEntity(
                this.thisPlayerMP, 2001, blockPos, Block.getStateId(blockState));
        boolean flag2 = this.removeBlock(blockPos);
        if (this.isCreative()) {
            this.thisPlayerMP.playerNetServerHandler.sendPacket(
                    new S23PacketBlockChange(this.theWorld, blockPos));

            return flag2;
        }

        ItemStack currentEquippedItemValue = this.thisPlayerMP.getCurrentEquippedItem();
        boolean flag1 = this.thisPlayerMP.canHarvestBlock(blockState.getBlock());
        if (currentEquippedItemValue != null) {
            currentEquippedItemValue.onBlockDestroyed(
                    this.theWorld, blockState.getBlock(), blockPos, this.thisPlayerMP);
            if (currentEquippedItemValue.stackSize == 0) {
                this.thisPlayerMP.destroyCurrentEquippedItem();
            }
        }

        if (flag2 && flag1) {
            blockState
                    .getBlock()
                    .harvestBlock(
                            this.theWorld, this.thisPlayerMP, blockPos, blockState, tileEntity);
        }

        return flag2;
    }

    public WorldSettingsGameType getGameType() {
        return this.gameType;
    }

    public boolean activateBlockOrUseItem(
            PlayerEntity playerEntity,
            World world,
            ItemStack itemStack,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        ServerConfigurationManager.sN();

        if (this.gameType == WorldSettingsGameType.SPECTATOR) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof ILockableContainer) {
                Block block = world.getBlockState(blockPos).getBlock();
                ILockableContainer iLockableContainer = (ILockableContainer) tileEntity;
                if (iLockableContainer instanceof TileEntityChest && block instanceof BlockChest) {
                    iLockableContainer = ((BlockChest) block).getLockableContainer(world, blockPos);
                }

                if (iLockableContainer != null) {
                    playerEntity.displayGUIChest(iLockableContainer);
                    return true;
                }

                return false;
            }

            if (tileEntity instanceof IInventory) {
                playerEntity.displayGUIChest((IInventory) tileEntity);
                return true;
            } else {
                return false;
            }
        } else {
            if (!playerEntity.isSneaking() || playerEntity.getHeldItem() == null) {
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState
                        .getBlock()
                        .onBlockActivated(
                                world, blockPos, blockState, playerEntity, facing, f, f1, f2)) {
                    return true;
                }
            }

            if (itemStack == null) {
                return false;
            } else if (this.isCreative()) {
                int j = itemStack.getItemDamage();
                int stackSize = itemStack.stackSize;
                boolean flag1 =
                        itemStack.onItemUse(playerEntity, world, blockPos, facing, f, f1, f2);
                itemStack.setItemDamage(j);
                itemStack.stackSize = stackSize;
                return flag1;
            } else {
                return itemStack.onItemUse(playerEntity, world, blockPos, facing, f, f1, f2);
            }
        }
    }

    private boolean removeBlock(BlockPos blockPos) {
        net.minecraft.block.state.BlockState blockState = this.theWorld.getBlockState(blockPos);
        blockState
                .getBlock()
                .onBlockHarvested(this.theWorld, blockPos, blockState, this.thisPlayerMP);
        boolean flag = this.theWorld.setBlockToAir(blockPos);
        if (flag) {
            blockState.getBlock().onBlockDestroyedByPlayer(this.theWorld, blockPos, blockState);
        }

        return flag;
    }

    public void onBlockClicked(BlockPos aWGValue, Facing facing) {
        ServerConfigurationManager.sN();

        if (this.isCreative()) {
            if (this.theWorld.extinguishFire((PlayerEntity) null, aWGValue, facing)) {
                return;
            }

            this.tryHarvestBlock(aWGValue);

            return;
        }

        Block block = this.theWorld.getBlockState(aWGValue).getBlock();
        if (this.gameType.isAdventure()) {
            if (this.gameType == WorldSettingsGameType.SPECTATOR) {
                return;
            }

            if (!this.thisPlayerMP.isAllowEdit()) {
                ItemStack currentEquippedItem = this.thisPlayerMP.getCurrentEquippedItem();
                if (currentEquippedItem == null) {
                    return;
                }

                if (!currentEquippedItem.canDestroy(block)) {
                    return;
                }
            }
        }

        this.theWorld.extinguishFire((PlayerEntity) null, aWGValue, facing);
        this.initialDamage = this.curblockDamage;
        float f = 1.0F;
        if (block.getMaterial() != Material.AIR) {
            block.onBlockClicked(this.theWorld, aWGValue, this.thisPlayerMP);
            f =
                    block.getPlayerRelativeBlockHardness(
                            this.thisPlayerMP, this.thisPlayerMP.world, aWGValue);
        }

        if (block.getMaterial() != Material.AIR && f >= 1.0F) {
            this.tryHarvestBlock(aWGValue);

            return;
        }

        this.isDestroyingBlock = true;
        this.field_180240_f = aWGValue;
        int durabilityRemainingOnBlockValue = (int) (f * 10.0F);
        this.theWorld.sendBlockBreakProgress(
                this.thisPlayerMP.getEntityId(), aWGValue, durabilityRemainingOnBlockValue);
        this.durabilityRemainingOnBlock = durabilityRemainingOnBlockValue;
    }

    public void blockRemoving(BlockPos byXValue) {
        ServerConfigurationManager.kr();

        if (byXValue.equals(this.field_180240_f)) {
            int i = this.curblockDamage - this.initialDamage;
            Block block = this.theWorld.getBlockState(byXValue).getBlock();
            if (block.getMaterial() != Material.AIR) {
                float f =
                        block.getPlayerRelativeBlockHardness(
                                        this.thisPlayerMP, this.thisPlayerMP.world, byXValue)
                                * (i + 1);
                if (f >= 0.7F) {
                    this.isDestroyingBlock = false;
                    this.theWorld.sendBlockBreakProgress(
                            this.thisPlayerMP.getEntityId(), byXValue, -1);
                    this.tryHarvestBlock(byXValue);

                    return;
                }

                if (!this.receivedFinishDiggingPacket) {
                    this.isDestroyingBlock = false;
                    this.receivedFinishDiggingPacket = true;
                    this.field_180241_i = byXValue;
                    this.initialBlockDamage = this.initialDamage;
                }
            }
        }
    }
}
