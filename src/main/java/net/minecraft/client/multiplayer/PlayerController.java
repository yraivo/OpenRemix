package net.minecraft.client.multiplayer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.network.ClientPlayHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.DiggingAction;
import net.minecraft.network.play.client.DiggingPacket;
import net.minecraft.network.play.client.HeldItemChangePacket;
import net.minecraft.network.play.client.UseEntityAction;
import net.minecraft.network.play.client.UseEntityPacket;
import net.minecraft.network.play.client.UseItemPacket;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettingsGameType;
import remix.event.impl.AttackEntityEvent;
import remix.event.MotionPhase;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.event.EventBus;
import remix.module.render.FreeCamModule;
import remix.module.world.InfiniteModule;

public class PlayerController {
    private int currentPlayerItem;
    public BlockPos currentBlock = new BlockPos(-1, -1, -1);
    public float curBlockDamageMP;
    private MinecraftClient mc;
    private ItemStack currentItemHittingBlock;
    private ClientPlayHandler netClientHandler;
    private float stepSoundTickCounter;
    public int blockHitDelay;
    private WorldSettingsGameType currentGameType = WorldSettingsGameType.SURVIVAL;
    private boolean isHittingBlock;

    public boolean usesSpectatorHud() {
        return this.currentGameType == WorldSettingsGameType.SPECTATOR;
    }

    public boolean clickBlock(BlockPos blockPos, Facing facing) {
        Entity[] entityArray = ClientWorld.getLegacyClientWorldFlowState();
        if (this.currentGameType.isAdventure()) {
            if (this.currentGameType == WorldSettingsGameType.SPECTATOR) {
                return false;
            }

            if (!this.mc.player.isAllowEdit()) {
                Block block = this.mc.world.getBlockState(blockPos).getBlock();
                ItemStack currentEquippedItem = this.mc.player.getCurrentEquippedItem();
                if (currentEquippedItem == null) {
                    return false;
                }

                if (!currentEquippedItem.canDestroy(block)) {
                    return false;
                }
            }
        }

        if (!this.mc.world.getWorldBorder().contains(blockPos)) {
            return false;
        }

        if (this.currentGameType.isCreative()) {
            this.netClientHandler.addToSendQueue(
                    new DiggingPacket(DiggingAction.START_DESTROY_BLOCK, blockPos, facing));
            clickBlockCreative(this.mc, this, blockPos, facing);
            this.blockHitDelay = 5;
            if (entityArray == null) {
                return true;
            }
        }

        if (!this.isHittingBlock || !this.isHittingPosition(blockPos)) {
            if (this.isHittingBlock) {
                this.netClientHandler.addToSendQueue(
                        new DiggingPacket(
                                DiggingAction.ABORT_DESTROY_BLOCK, this.currentBlock, facing));
            }

            this.netClientHandler.addToSendQueue(
                    new DiggingPacket(DiggingAction.START_DESTROY_BLOCK, blockPos, facing));
            Block blockValue = this.mc.world.getBlockState(blockPos).getBlock();
            int i = blockValue.getMaterial() != Material.AIR ? 1 : 0;
            if (i != 0 && this.curBlockDamageMP == 0.0F) {
                blockValue.onBlockClicked(this.mc.world, blockPos, this.mc.player);
            }

            if (i != 0
                    && blockValue.getPlayerRelativeBlockHardness(
                                    this.mc.player, this.mc.player.world, blockPos)
                            >= 1.0F) {
                ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
                FreeCamModule freeCam = ClientServices.moduleManager.freeCam;
                if (freeCam.isEnabled()) {
                    return false;
                }

                this.onPlayerDestroyBlock(blockPos, facing);
                if (entityArray == null) {
                    return true;
                }
            }

            this.isHittingBlock = true;
            this.currentBlock = blockPos;
            this.currentItemHittingBlock = this.mc.player.getHeldItem();
            this.curBlockDamageMP = 0.0F;
            this.stepSoundTickCounter = 0.0F;
            this.mc.world.sendBlockBreakProgress(
                    this.mc.player.getEntityId(),
                    this.currentBlock,
                    (int) (this.curBlockDamageMP * 10.0F) - 1);
        }

        return true;
    }

    public boolean shouldDrawPlayerStats() {
        return this.currentGameType.isSurvivalOrAdventure();
    }

    public PlayerController(MinecraftClient minecraftClient, ClientPlayHandler awvValue) {
        this.mc = minecraftClient;
        this.netClientHandler = awvValue;
    }

    public boolean extendedReach() {
        return this.currentGameType.isCreative();
    }

    public void attackEntity(PlayerEntity playerEntity, Entity entity) {
        AttackEntityEvent attackEntityEvent = new AttackEntityEvent(MotionPhase.PRE, entity);
        EventBus amg = RemixClient.eventBus;
        amg.post(attackEntityEvent);
        if (attackEntityEvent.isCancelled()) {
            amg.post(new AttackEntityEvent(MotionPhase.POST, entity));
        } else {
            this.syncCurrentPlayItem();
            this.netClientHandler.addToSendQueue(
                    new UseEntityPacket(attackEntityEvent.getEntity(), UseEntityAction.ATTACK));
            if (this.currentGameType != WorldSettingsGameType.SPECTATOR) {
                playerEntity.attackTargetEntityWithCurrentItem(attackEntityEvent.getEntity());
            }

            amg.post(new AttackEntityEvent(MotionPhase.POST, entity));
        }
    }

    public void resetBlockRemoving() {
        if (this.isHittingBlock) {
            this.netClientHandler.addToSendQueue(
                    new DiggingPacket(
                            DiggingAction.ABORT_DESTROY_BLOCK, this.currentBlock, Facing.DOWN));
            this.isHittingBlock = false;
            this.curBlockDamageMP = 0.0F;
            this.mc.world.sendBlockBreakProgress(
                    this.mc.player.getEntityId(), this.currentBlock, -1);
        }
    }

    public void sendSlotPacket(ItemStack itemStack, int i) {
        if (this.currentGameType.isCreative()) {
            this.netClientHandler.addToSendQueue(
                    new C10PacketCreativeInventoryAction(i, itemStack));
        }
    }

    public float getBlockReachDistance() {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        InfiniteModule infinite = ClientServices.moduleManager.infinite;
        return infinite.isEnabled() ? 250.0F : (this.currentGameType.isCreative() ? 5.0F : 4.5F);
    }

    public boolean interactWithEntitySendPacket(PlayerEntity playerEntity, Entity entity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(new UseEntityPacket(entity, UseEntityAction.INTERACT));
        return this.currentGameType != WorldSettingsGameType.SPECTATOR
                && playerEntity.interactWith(entity);
    }

    public boolean interactWithEntityAt(
            PlayerEntity playerEntity, Entity entity, RayTraceResult rayTraceResult) {
        this.syncCurrentPlayItem();
        Vec3 vec3 =
                new Vec3(
                        rayTraceResult.hitVec.x - entity.posX,
                        rayTraceResult.hitVec.y - entity.posY,
                        rayTraceResult.hitVec.z - entity.posZ);
        this.netClientHandler.addToSendQueue(new UseEntityPacket(entity, vec3));
        return this.currentGameType != WorldSettingsGameType.SPECTATOR
                && entity.interactAt(playerEntity, vec3);
    }

    public boolean shouldDrawExperience() {
        return this.currentGameType.isSurvivalOrAdventure();
    }

    public boolean sendUseItem(PlayerEntity playerEntity, World world, ItemStack itemStack) {
        if (this.currentGameType == WorldSettingsGameType.SPECTATOR) {
            return false;
        }

        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(
                new UseItemPacket(playerEntity.inventory.getCurrentItem()));
        int stackSize = itemStack.stackSize;
        ItemStack itemStackValue = itemStack.useItemRightClick(world, playerEntity);
        if (itemStackValue != itemStack
                || itemStackValue != null && itemStackValue.stackSize != stackSize) {
            playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem] =
                    itemStackValue;
            if (itemStackValue.stackSize == 0) {
                playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem] = null;
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean isHittingPosition(BlockPos blockPos) {
        ItemStack heldItem = this.mc.player.getHeldItem();
        int i = this.currentItemHittingBlock == null && heldItem == null ? 1 : 0;
        if (this.currentItemHittingBlock != null && heldItem != null) {
            i =
                    heldItem.getItem() != this.currentItemHittingBlock.getItem()
                                    || !ItemStack.areItemStackTagsEqual(
                                            heldItem, this.currentItemHittingBlock)
                                    || !heldItem.isItemStackDamageable()
                                            && heldItem.getItemDamage()
                                                    != this.currentItemHittingBlock.getItemDamage()
                            ? 0
                            : 1;
        }

        return blockPos.equals(this.currentBlock) && i != 0;
    }

    private void syncCurrentPlayItem() {
        int currentPlayerItemValue = this.mc.player.inventory.currentItem;
        if (currentPlayerItemValue != this.currentPlayerItem) {
            this.currentPlayerItem = currentPlayerItemValue;
            this.netClientHandler.addToSendQueue(new HeldItemChangePacket(this.currentPlayerItem));
        }
    }

    public void sendPacketDropItem(ItemStack itemStack) {
        if (this.currentGameType.isCreative() && itemStack != null) {
            this.netClientHandler.addToSendQueue(
                    new C10PacketCreativeInventoryAction(-1, itemStack));
        }
    }

    public WorldSettingsGameType getCurrentGameType() {
        return this.currentGameType;
    }

    public boolean isRidingHorse() {
        return this.mc.player.isRiding() && this.mc.player.ridingEntity instanceof EntityHorse;
    }

    public boolean getIsHittingBlock() {
        return this.isHittingBlock;
    }

    public void onStoppedUsingItem(PlayerEntity playerEntity) {
        this.syncCurrentPlayItem();
        this.netClientHandler.addToSendQueue(
                new DiggingPacket(DiggingAction.RELEASE_USE_ITEM, BlockPos.ORIGIN, Facing.DOWN));
        playerEntity.stopUsingItem();
    }

    public boolean isNotCreative() {
        return !this.currentGameType.isCreative();
    }

    public boolean onPlayerDamageBlock(BlockPos blockPos, Facing facing) {
        this.syncCurrentPlayItem();
        if (this.blockHitDelay > 0) {
            this.blockHitDelay--;
            return true;
        }

        if (this.currentGameType.isCreative()
                && this.mc.world.getWorldBorder().contains(blockPos)) {
            this.blockHitDelay = 5;
            this.netClientHandler.addToSendQueue(
                    new DiggingPacket(DiggingAction.START_DESTROY_BLOCK, blockPos, facing));
            clickBlockCreative(this.mc, this, blockPos, facing);
            return true;
        }

        if (this.isHittingPosition(blockPos)) {
            Block block = this.mc.world.getBlockState(blockPos).getBlock();
            if (block.getMaterial() == Material.AIR) {
                this.isHittingBlock = false;
                return false;
            }

            this.curBlockDamageMP =
                    this.curBlockDamageMP
                            + block.getPlayerRelativeBlockHardness(
                                    this.mc.player, this.mc.player.world, blockPos);
            if (this.stepSoundTickCounter % 4.0F == 0.0F) {
                this.mc
                        .getSoundHandler()
                        .playSound(
                                new PositionedSoundRecord(
                                        new ResourceLocation(block.stepSound.getStepSound()),
                                        (block.stepSound.getVolume() + 1.0F) / 8.0F,
                                        block.stepSound.getFrequency() * 0.5F,
                                        blockPos.getX() + 0.5F,
                                        blockPos.getY() + 0.5F,
                                        blockPos.getZ() + 0.5F));
            }

            this.stepSoundTickCounter++;
            if (this.curBlockDamageMP >= 1.0F) {
                this.isHittingBlock = false;
                this.netClientHandler.addToSendQueue(
                        new DiggingPacket(DiggingAction.STOP_DESTROY_BLOCK, blockPos, facing));
                this.onPlayerDestroyBlock(blockPos, facing);
                this.curBlockDamageMP = 0.0F;
                this.stepSoundTickCounter = 0.0F;
                this.blockHitDelay = 5;
            }

            this.mc.world.sendBlockBreakProgress(
                    this.mc.player.getEntityId(),
                    this.currentBlock,
                    (int) (this.curBlockDamageMP * 10.0F) - 1);
            return true;
        } else {
            return this.clickBlock(blockPos, facing);
        }
    }

    public void setPlayerCapabilities(PlayerEntity playerEntity) {
        this.currentGameType.configurePlayerCapabilities(playerEntity.capabilities);
    }

    public LocalPlayer createLocalPlayer(World world, StatFileWriter statFileWriter) {
        return new LocalPlayer(this.mc, world, this.netClientHandler, statFileWriter);
    }

    public void updateController() {
        Entity[] entityArray = ClientWorld.getLegacyClientWorldFlowState();
        this.syncCurrentPlayItem();
        Entity[] entityArrayValue = entityArray;
        if (this.netClientHandler.getNetworkManager().isChannelOpen()) {
            this.netClientHandler.getNetworkManager().processReceivedPackets();
            if (entityArrayValue == null) {
                return;
            }
        }

        this.netClientHandler.getNetworkManager().checkDisconnected();
    }

    public void sendEnchantPacket(int i, int j) {
        this.netClientHandler.addToSendQueue(new C11PacketEnchantItem(i, j));
    }

    public static void clickBlockCreative(
            MinecraftClient minecraftClient,
            PlayerController playerController,
            BlockPos blockPos,
            Facing facing) {
        if (!minecraftClient.world.extinguishFire(minecraftClient.player, blockPos, facing)) {
            playerController.onPlayerDestroyBlock(blockPos, facing);
        }
    }

    public void setGameType(WorldSettingsGameType awwValue) {
        this.currentGameType = awwValue;
        this.currentGameType.configurePlayerCapabilities(this.mc.player.capabilities);
    }

    public boolean onPlayerRightClick(
            LocalPlayer localPlayer,
            ClientWorld clientWorld,
            ItemStack itemStack,
            BlockPos blockPos,
            Facing facing,
            Vec3 vec3) {
        this.syncCurrentPlayItem();
        float f = (float) (vec3.x - blockPos.getX());
        float f1 = (float) (vec3.y - blockPos.getY());
        float f2 = (float) (vec3.z - blockPos.getZ());
        byte b0 = 0;
        if (!this.mc.world.getWorldBorder().contains(blockPos)) {
            return false;
        }

        if (this.currentGameType != WorldSettingsGameType.SPECTATOR) {
            BlockState blockState = clientWorld.getBlockState(blockPos);
            if ((!localPlayer.isSneaking() || itemStack == null)
                    && blockState
                            .getBlock()
                            .onBlockActivated(
                                    clientWorld,
                                    blockPos,
                                    blockState,
                                    localPlayer,
                                    facing,
                                    f,
                                    f1,
                                    f2)) {
                b0 = 1;
            }

            if (b0 == 0 && itemStack != null && itemStack.getItem() instanceof ItemBlock) {
                ItemBlock item = (ItemBlock) itemStack.getItem();
                if (!item.canPlaceBlockOnSide(
                        clientWorld, blockPos, facing, localPlayer, itemStack)) {
                    return false;
                }
            }
        }

        this.netClientHandler.addToSendQueue(
                new UseItemPacket(blockPos, facing.getIndex(), itemStack, f, f1, f2));
        if (b0 != 0 || this.currentGameType == WorldSettingsGameType.SPECTATOR) {
            return true;
        } else if (itemStack == null) {
            return false;
        } else if (this.currentGameType.isCreative()) {
            int itemDamage = itemStack.getItemDamage();
            int j = itemStack.stackSize;
            boolean flag =
                    itemStack.onItemUse(localPlayer, clientWorld, blockPos, facing, f, f1, f2);
            itemStack.setItemDamage(itemDamage);
            itemStack.stackSize = j;
            return flag;
        } else {
            return itemStack.onItemUse(localPlayer, clientWorld, blockPos, facing, f, f1, f2);
        }
    }

    public boolean isInCreativeMode() {
        return this.currentGameType.isCreative();
    }

    public boolean isSpectatorMode() {
        return this.currentGameType == WorldSettingsGameType.SPECTATOR;
    }

    public boolean onPlayerDestroyBlock(BlockPos blockPos, Facing facing) {
        if (this.currentGameType.isAdventure()) {
            if (this.currentGameType == WorldSettingsGameType.SPECTATOR) {
                return false;
            }

            if (!this.mc.player.isAllowEdit()) {
                Block block = this.mc.world.getBlockState(blockPos).getBlock();
                ItemStack currentEquippedItem = this.mc.player.getCurrentEquippedItem();
                if (currentEquippedItem == null) {
                    return false;
                }

                if (!currentEquippedItem.canDestroy(block)) {
                    return false;
                }
            }
        }

        if (this.currentGameType.isCreative()
                && this.mc.player.getHeldItem() != null
                && this.mc.player.getHeldItem().getItem() instanceof SwordItem) {
            return false;
        }

        ClientWorld world = this.mc.world;
        BlockState blockState = world.getBlockState(blockPos);
        Block blockValue = blockState.getBlock();
        if (blockValue.getMaterial() == Material.AIR) {
            return false;
        }

        world.playAuxSFX(2001, blockPos, Block.getStateId(blockState));
        boolean flag = world.setBlockToAir(blockPos);
        if (flag) {
            blockValue.onBlockDestroyedByPlayer(world, blockPos, blockState);
        }

        this.currentBlock = new BlockPos(this.currentBlock.getX(), -1, this.currentBlock.getZ());
        if (!this.currentGameType.isCreative()) {
            ItemStack currentEquippedItemValue = this.mc.player.getCurrentEquippedItem();
            if (currentEquippedItemValue != null) {
                currentEquippedItemValue.onBlockDestroyed(
                        world, blockValue, blockPos, this.mc.player);
                if (currentEquippedItemValue.stackSize == 0) {
                    this.mc.player.destroyCurrentEquippedItem();
                }
            }
        }

        return flag;
    }

    public ItemStack windowClick(int i, int j, int k, int l, PlayerEntity playerEntity) {
        short short1 = playerEntity.openContainer.getNextTransactionID(playerEntity.inventory);
        ItemStack itemStack = playerEntity.openContainer.slotClick(j, k, l, playerEntity);
        this.netClientHandler.addToSendQueue(
                new C0EPacketClickWindow(i, j, k, l, itemStack, short1));
        return itemStack;
    }

    public void flipPlayer(PlayerEntity playerEntity) {
        playerEntity.rotationYaw = -180.0F;
    }
}
