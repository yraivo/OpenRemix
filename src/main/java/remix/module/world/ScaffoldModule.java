package remix.module.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.HeldItemChangePacket;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;

import optifine.MathUtils;

import org.lwjgl.input.Keyboard;

import remix.client.RemixClient;
import remix.event.InputType;
import remix.event.MotionEvent;
import remix.event.MotionPhase;
import remix.event.PacketDirection;
import remix.event.annotation.Subscribe;
import remix.event.impl.ClientTickEvent;
import remix.event.impl.PacketEvent;
import remix.event.impl.Render2DEvent;
import remix.event.impl.SafeWalkEvent;
import remix.event.impl.SprintStateEvent;
import remix.input.InputEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;
import remix.util.entity.RaycastHelper;
import remix.util.entity.RotationHelper;
import remix.util.inventory.InventoryItemHelper;
import remix.util.inventory.ItemSlot;
import remix.util.movement.MovementPhysicsHelper;
import remix.util.movement.MovementUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Scaffold module: automatically places blocks under or ahead of the player while walking, jumping,
 * towering, or moving down stairs. Supports SCAN and RAYTRACE modes.
 */
public final class ScaffoldModule extends ModuleBase {

    private Setting downStairSetting;
    private int ticksSincePlacement;
    private Setting delaySetting;
    private Setting sneakSetting;
    private Setting smoothnessSetting;
    private Setting keepYSetting;
    private List<ItemSlot> availableBlockStacks;
    private int serverHeldSlot;
    private Setting extendSetting;
    private float[] placementRotations;
    private Setting drawAmountSetting;
    private Setting towerSetting;

    private Setting fromInventorySetting;
    private RayTraceResult placementHit;
    private double savedPlacementY;
    private Setting jumpStairSetting;
    private Setting randomizeSetting;

    private Setting rayTraceSetting;
    private Setting blockSlotSetting;
    private Setting keepRotationSetting;

    private Setting<ScaffoldMode> modeSetting;
    private Setting biggestStackSetting;
    private Setting safewalkSetting;
    private boolean unusedPlacementParity;
    private Setting stopSprintSetting;

    public ScaffoldModule() {
        super("Scaffold", ModuleDefaults.ofCategory(ModuleCategory.WORLD));

        this.modeSetting = new Setting("Mode", this, ScaffoldMode.SCAN);
        this.extendSetting = new Setting("Extend", this, 0.0, 0.0, 6.0, 0.1);
        this.blockSlotSetting = new Setting("BlockSlot", this, 9.0, 1.0, 9.0, 1.0);
        this.delaySetting = new Setting("Delay", this, 0.0, 0.0, 1000.0, 50.0);
        this.smoothnessSetting = new Setting("Smoothness", this, 0.0, 0.0, 100.0, 1.0);
        this.fromInventorySetting = new Setting("FromInventory", this, false);
        this.keepRotationSetting = new Setting("KeepRotation", this, true);
        this.sneakSetting = new Setting("Sneak", this, false);
        this.biggestStackSetting = new Setting("BiggestStack", this, true);
        this.downStairSetting = new Setting("DownStair", this, true);
        this.towerSetting = new Setting("Tower", this, true);
        this.drawAmountSetting = new Setting("DrawAmount", this, true);
        this.keepYSetting = new Setting("KeepY", this, false);
        this.jumpStairSetting = new Setting("JumpStair", this, false);
        this.rayTraceSetting = new Setting("RayTrace", this, false);
        this.randomizeSetting = new Setting("Randomize", this, false);
        this.safewalkSetting = new Setting("Safewalk", this, false);
        this.stopSprintSetting = new Setting("StopSprint", this, false);
        this.availableBlockStacks = new ArrayList<>();
        this.placementRotations = new float[2];
    }

    @Override
    public void onEnable() {
        if (minecraft.player != null) {
            this.serverHeldSlot = minecraft.player.inventory.currentItem;
            this.placementHit = null;
            this.savedPlacementY = minecraft.player.posY;
        }

        this.unusedPlacementParity = true;
        this.ticksSincePlacement = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (minecraft.player != null
                && this.serverHeldSlot != minecraft.player.inventory.currentItem) {
            this.serverHeldSlot = minecraft.player.inventory.currentItem;
            this.sendPacketWithoutEvent(new HeldItemChangePacket(this.serverHeldSlot));
        }

        this.placementHit = null;
        super.onDisable();
    }

    @Subscribe(mc = InputEvent.class)
    public void onInput(InputEvent event) {
        if (event.getType() == InputType.KEY_PRESS
                && this.downStairSetting.asBoolean()
                && minecraft.inGameHasFocus
                && Keyboard.isKeyDown(minecraft.gameSettings.keyBindSneak.getKeyCode())
                && event.getKeyCode() == minecraft.gameSettings.keyBindSneak.getKeyCode()) {
            minecraft.gameSettings.keyBindSneak.pressed = false;
        }
    }

    @Subscribe(mc = SafeWalkEvent.class)
    public void onSafeWalk(SafeWalkEvent event) {
        if (this.safewalkSetting.asBoolean()) {
            event.setCancelled(true);
        }
    }

    @Subscribe(mc = ClientTickEvent.class)
    public void onClientTick(ClientTickEvent event) {
        if (minecraft.player == null
                || minecraft.world == null
                || minecraft.playerController == null) {
            this.placementHit = null;
            return;
        }

        this.ticksSincePlacement++;
        ItemStack itemStack = minecraft.player.inventory.getStackInSlot(this.serverHeldSlot);
        if (itemStack != null
                && this.ticksSincePlacement * 50 >= this.delaySetting.asDouble()
                && this.placementHit != null) {

            if (this.rayTraceSetting.asBoolean()) {
                double blockReachDistance = minecraft.playerController.getBlockReachDistance();
                RayTraceResult verifiedHit =
                        RaycastHelper.rayTrace(
                                this.placementRotations, blockReachDistance, false, 1.0F);
                if (verifiedHit == null
                        || verifiedHit.sideHit != this.placementHit.sideHit
                        || !this.sameBlockPosition(
                                verifiedHit.getBlockPos(), this.placementHit.getBlockPos())) {
                    return;
                }

                this.placementHit = verifiedHit;
            }

            if (this.sneakSetting.asBoolean()) {
                if (!minecraft.player.isSneaking()) {
                    minecraft.gameSettings.keyBindSneak.pressed = true;
                    return;
                }

                minecraft.gameSettings.keyBindSneak.pressed = false;
            }

            if (this.placementHit != null
                    && minecraft.playerController.onPlayerRightClick(
                            minecraft.player,
                            minecraft.world,
                            itemStack,
                            this.placementHit.getBlockPos(),
                            this.placementHit.sideHit,
                            this.placementHit.hitVec)) {

                if (this.placementHit.sideHit == Facing.UP
                        && minecraft.gameSettings.keyBindJump.isKeyDown()
                        && minecraft.world.checkBlockCollision(
                                minecraft
                                        .player
                                        .getEntityBoundingBox()
                                        .addCoord(0.0, -0.0626, 0.0))) {
                    if (this.towerSetting.asBoolean() && !MovementUtils.isMoving()) {
                        minecraft.player.motionY = this.getTowerVelocity();
                        MovementUtils.setSpeed(0.0);
                    } else if (this.jumpStairSetting.asBoolean() && MovementUtils.isMoving()) {
                        minecraft.player.motionY = this.getTowerVelocity();
                    }
                }

                this.sendPacket(new C0APacketAnimation());
                this.ticksSincePlacement = 0;
            }
        }

        this.placementHit = null;
    }

    private double getTowerVelocity() {
        return MovementUtils.getJumpMotion() - 4.54352838557992E-4;
    }

    private int countAvailableBlocks() {
        int total = 0;
        for (ItemSlot itemSlot : this.availableBlockStacks) {
            total += itemSlot.itemStack.stackSize;
        }
        return total;
    }

    private Vec3 findExtendedPlacementPoint(Vec3 origin, double maxDistance) {
        for (double d = 0.0; d < maxDistance; d += this.extendSetting.getIncrement()) {
            Vec3 offsetPoint = MovementPhysicsHelper.offsetDirectional(origin, d);
            if (this.isReplaceablePosition(new BlockPos(offsetPoint))) {
                return offsetPoint;
            }
        }
        return origin;
    }

    private boolean sameBlockPosition(BlockPos left, BlockPos right) {
        return left.getX() == right.getX()
                && left.getY() == right.getY()
                && left.getZ() == right.getZ();
    }

    @Subscribe(mc = Render2DEvent.class)
    public void onRender2D(Render2DEvent event) {
        if (minecraft.player == null
                || minecraft.world == null
                || minecraft.playerController == null) {
            return;
        }
        if (this.drawAmountSetting.asBoolean()) {
            int blockCount = this.countAvailableBlocks();
            String text =
                    minecraft.playerController.isInCreativeMode()
                            ? "Unlimited!"
                            : String.format(
                                    "%s %s", blockCount, blockCount != 1 ? "blocks" : "block");
            RemixClient.fonts.comfortaa18.drawStringWithShadow(
                    text, event.getWidth() / 2 + 7, event.getHeight() / 2 - 2, -1);
        }
    }

    private boolean isReplaceablePosition(BlockPos position) {
        Block block = minecraft.world.getBlockState(position).getBlock();
        return block instanceof BlockAir
                || block instanceof BlockLiquid
                || block instanceof BlockBush
                || block instanceof BlockSnow
                        && (Integer)
                                        minecraft
                                                .world
                                                .getBlockState(position)
                                                .getValue(BlockSnow.LAYERS_PROP)
                                == 1;
    }

    private boolean isSupportMaterialAllowed(Block block) {
        return block.getMaterial() != Material.AIR;
    }

    private Vec3 randomizeHitPoint(RayTraceResult hit) {
        float offsetRange = 0.1F;
        Vec3 hitVec = hit.hitVec;
        BlockPos blockPos = hit.getBlockPos();
        BlockState blockState = minecraft.world.getBlockState(blockPos);
        BoundingBox boundingBox =
                blockState
                        .getBlock()
                        .getCollisionBoundingBox(minecraft.world, blockPos, blockState);
        // Selection/ray hits may belong to blocks with no collision surface, such as torches.
        if (boundingBox == null) {
            return hitVec;
        }
        double x =
                hitVec.x != boundingBox.minX && hitVec.x != boundingBox.maxX
                        ? Math.min(
                                Math.max(
                                        hitVec.x
                                                + MathUtils.getRandomFloat(
                                                        -offsetRange, offsetRange),
                                        boundingBox.minX),
                                boundingBox.maxX)
                        : hitVec.x;
        double y =
                hitVec.y != boundingBox.minY && hitVec.y != boundingBox.maxY
                        ? Math.min(
                                Math.max(
                                        hitVec.y
                                                + MathUtils.getRandomFloat(
                                                        -offsetRange, offsetRange),
                                        boundingBox.minY),
                                boundingBox.maxY)
                        : hitVec.y;
        double z =
                hitVec.z != boundingBox.minZ && hitVec.z != boundingBox.maxZ
                        ? Math.min(
                                Math.max(
                                        hitVec.z
                                                + MathUtils.getRandomFloat(
                                                        -offsetRange, offsetRange),
                                        boundingBox.minZ),
                                boundingBox.maxZ)
                        : hitVec.z;
        return new Vec3(x, y, z);
    }

    @Subscribe(mc = SprintStateEvent.class)
    public void onSprintState(SprintStateEvent event) {
        if (this.stopSprintSetting.asBoolean()
                && event.isSprinting()
                && RotationHelper.angleDifference(
                                MovementPhysicsHelper.getMoveDirectionYaw(),
                                this.placementRotations[0])
                        > 45.0) {
            event.setSprinting(false);
        }
    }

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {
        if (event.getPhase() != MotionPhase.PRE) {
            return;
        }
        if (minecraft.player == null
                || minecraft.world == null
                || minecraft.playerController == null) {
            this.placementHit = null;
            return;
        }
        ItemStack itemStack = minecraft.player.inventory.getStackInSlot(this.serverHeldSlot);

        this.availableBlockStacks = this.collectBlockStacks(36, 44);
        if (!this.availableBlockStacks.isEmpty()) {
            this.availableBlockStacks.sort(
                    Comparator.<ItemSlot>comparingDouble(slot -> -slot.itemStack.stackSize));
        }

        if (this.fromInventorySetting.asBoolean()) {
            List<ItemSlot> inventoryBlocks = this.collectBlockStacks(9, 35);
            if (!inventoryBlocks.isEmpty() && this.availableBlockStacks.isEmpty()) {
                GuiInventory guiInventory = new GuiInventory(minecraft.player);
                inventoryBlocks.sort(
                        Comparator.<ItemSlot>comparingDouble(slot -> -slot.itemStack.stackSize));
                ItemSlot bestInventorySlot = inventoryBlocks.get(0);
                if (minecraft.player.inventory.currentItem != this.serverHeldSlot) {
                    InventoryItemHelper.clickSlot(
                            guiInventory, bestInventorySlot.slotIndex, this.serverHeldSlot, 2);
                    bestInventorySlot.slotIndex = 36 + this.serverHeldSlot;
                } else {
                    int targetHotbarIndex = (int) this.blockSlotSetting.asDouble() - 1;
                    InventoryItemHelper.clickSlot(
                            guiInventory, bestInventorySlot.slotIndex, targetHotbarIndex, 2);
                    bestInventorySlot.slotIndex = 36 + targetHotbarIndex;
                }
            }

            this.availableBlockStacks.addAll(inventoryBlocks);
        }

        if ((itemStack == null
                        || !InventoryItemHelper.isBlock(itemStack.getItem())
                        || this.biggestStackSetting.asBoolean())
                && !this.availableBlockStacks.isEmpty()) {
            ItemSlot bestAvailableSlot = this.availableBlockStacks.get(0);
            if (this.serverHeldSlot != bestAvailableSlot.slotIndex - 36) {
                this.serverHeldSlot = bestAvailableSlot.slotIndex - 36;
                this.sendPacketWithoutEvent(new HeldItemChangePacket(this.serverHeldSlot));
            }
        }

        if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
            boolean downStair =
                    this.downStairSetting.asBoolean()
                            && Keyboard.isKeyDown(minecraft.gameSettings.keyBindSneak.getKeyCode())
                            && minecraft.player.motionY < 0.0;
            boolean jumpStair =
                    this.jumpStairSetting.asBoolean()
                            && minecraft.gameSettings.keyBindJump.isKeyDown()
                            && MovementUtils.isMoving();

            double targetY = (int) minecraft.player.posY;
            if (downStair) {
                targetY -= 1.5;
            } else {
                targetY -= 0.5;
            }

            if (this.keepYSetting.asBoolean()
                    && MovementUtils.isMoving()
                    && !jumpStair
                    && !downStair) {
                if (minecraft.player.onGround
                        || Math.abs(minecraft.player.posY - this.savedPlacementY) > 5.0) {
                    this.savedPlacementY = minecraft.player.posY - 0.5;
                }
                targetY = this.savedPlacementY;
            } else if (jumpStair) {
                this.savedPlacementY = minecraft.player.posY - 0.5;
            }

            double extendDistance =
                    this.towerSetting.asBoolean()
                                    && minecraft.gameSettings.keyBindJump.isKeyDown()
                                    && !MovementUtils.isMoving()
                            ? 0.0
                            : this.extendSetting.asDouble();

            Vec3 extendedPlacementPoint =
                    this.findExtendedPlacementPoint(
                            new Vec3(minecraft.player.posX, targetY, minecraft.player.posZ),
                            extendDistance);

            if (this.keepRotationSetting.asBoolean()) {
                event.setYaw(this.placementRotations[0]);
                event.setPitch(this.placementRotations[1]);
            }

            if (this.isReplaceablePosition(new BlockPos(extendedPlacementPoint))) {
                this.placementHit = this.findPlacementHit(extendedPlacementPoint);
                if (this.placementHit != null && this.randomizeSetting.asBoolean()) {
                    this.placementHit.hitVec = this.randomizeHitPoint(this.placementHit);
                }
            }

            if (this.placementHit != null) {
                float[] currentRotations = new float[] {event.getYaw(), event.getPitch()};
                float[] targetRotations =
                        RotationHelper.rotationsTo(
                                this.placementHit.hitVec,
                                currentRotations,
                                (float) this.smoothnessSetting.asDouble());
                event.setYaw(targetRotations[0] + ThreadLocalRandom.current().nextFloat());
                event.setPitch(targetRotations[1]);
                this.unusedPlacementParity = !this.unusedPlacementParity;
            }
        }

        this.placementRotations = new float[] {event.getYaw(), event.getPitch()};
    }

    private List<ItemSlot> collectBlockStacks(int minSlot, int maxSlot) {
        List<ItemSlot> list = new ArrayList<>();
        for (int slot = maxSlot; slot >= minSlot; slot--) {
            ItemStack stack = minecraft.player.inventoryContainer.getSlot(slot).getStack();
            if (stack != null
                    && stack.stackSize > 0
                    && InventoryItemHelper.isBlock(stack.getItem())) {
                list.add(new ItemSlot(stack, slot));
            }
        }
        return list;
    }

    private RayTraceResult findPlacementHit(Vec3 origin) {
        Vec3 targetVec = origin;
        boolean downStair =
                this.downStairSetting.asBoolean()
                        && Keyboard.isKeyDown(minecraft.gameSettings.keyBindSneak.getKeyCode())
                        && minecraft.player.motionY < 0.0;
        boolean jumpStair =
                this.jumpStairSetting.asBoolean()
                        && minecraft.gameSettings.keyBindJump.isKeyDown()
                        && MovementUtils.isMoving();

        ScaffoldMode mode = (ScaffoldMode) this.modeSetting.getValue();
        switch (mode) {
            case SCAN:
                {
                    double[] yOffsets;
                    if (downStair) {
                        yOffsets = new double[] {1.0, 0.0, -1.0};
                    } else if (minecraft.player.isPotionActive(Potion.JUMP)) {
                        yOffsets = new double[] {0.0, -1.0, -2.0};
                    } else {
                        yOffsets = new double[] {0.0, -1.0};
                    }

                    if (!this.isReplaceablePosition(
                            new BlockPos(targetVec.addVector(0.0, -1.0, 0.0)))) {
                        targetVec = targetVec.addVector(0.0, -1.0, 0.0);
                    } else {
                        double[][] horizontalOffsets = {
                            {0.0, 0.0},
                            {1.0, 0.0},
                            {0.0, 1.0},
                            {-1.0, 0.0},
                            {0.0, -1.0},
                            {1.0, 1.0},
                            {1.0, -1.0},
                            {-1.0, -1.0},
                            {-1.0, 1.0}
                        };
                        boolean found = false;
                        for (double yOff : yOffsets) {
                            for (double[] hOff : horizontalOffsets) {
                                Vec3 candidate = targetVec.addVector(hOff[0], yOff, hOff[1]);
                                if (!this.isReplaceablePosition(new BlockPos(candidate))) {
                                    targetVec = candidate;
                                    found = true;
                                    break;
                                }
                            }
                            if (found) {
                                break;
                            }
                        }
                    }
                    break;
                }

            case RAYTRACE:
                {
                    double blockReachDistance = minecraft.playerController.getBlockReachDistance();
                    double deltaY = minecraft.player.prevPosY - minecraft.player.posY;
                    if (this.keepYSetting.asBoolean() && MovementUtils.isMoving() && !jumpStair) {
                        deltaY = 0.0;
                    }
                    if (downStair) {
                        deltaY = 0.25;
                    }

                    Vec3 delta =
                            new Vec3(
                                    minecraft.player.prevPosX - minecraft.player.posX,
                                    deltaY,
                                    minecraft.player.prevPosZ - minecraft.player.posZ);
                    Vec3 reachDelta =
                            new Vec3(
                                    delta.x * blockReachDistance,
                                    delta.y * blockReachDistance,
                                    delta.z * blockReachDistance);
                    targetVec = origin.add(reachDelta);
                    break;
                }
        }

        RayTraceResult rayTraceResult = minecraft.world.rayTraceBlocks(origin, targetVec);
        if (rayTraceResult != null && rayTraceResult.typeOfHit == RayHitType.BLOCK) {
            Block block = minecraft.world.getBlockState(rayTraceResult.getBlockPos()).getBlock();
            if (this.isSupportMaterialAllowed(block)) {
                return rayTraceResult;
            }
        }

        return null;
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() == PacketDirection.SEND
                && event.getPacket() instanceof HeldItemChangePacket) {
            event.setCancelled(true);
        }
    }
}
