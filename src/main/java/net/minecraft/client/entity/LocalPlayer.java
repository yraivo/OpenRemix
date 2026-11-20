package net.minecraft.client.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.network.ClientPlayHandler;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityActionAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatusEnumState;
import net.minecraft.network.play.client.DiggingAction;
import net.minecraft.network.play.client.DiggingPacket;
import net.minecraft.network.play.client.PlayerLookPacket;
import net.minecraft.network.play.client.PlayerMovementPacket;
import net.minecraft.network.play.client.PlayerPositionLookPacket;
import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.event.MotionEvent;
import remix.event.MotionPhase;
import remix.event.MoveEvent;
import remix.event.impl.EntityPushEvent;
import remix.event.impl.ItemSlowdownEvent;
import remix.event.impl.JumpEvent;
import remix.event.impl.MovementHeadingEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.impl.PushOutOfBlocksEvent;
import remix.module.exploit.SignControlModule;
import remix.module.movement.SprintModule;
import remix.module.player.NoSwingModule;
import remix.module.render.CameraModule;
import remix.util.movement.MovementPhysicsHelper;

public class LocalPlayer extends AbstractClientPlayer {
    private double lastReportedY;
    private static int[] legacyLocalPlayerMarker;

    private boolean serverHealthInitialized;
    private double lastReportedZ;
    protected int sprintToggleTimer;
    private int horseJumpPowerCounter;
    private static String[] packedTextFragments;
    public float renderArmYaw;
    private StatFileWriter statWriter;
    private boolean lastReportedSprinting;
    private String clientBrand;
    private float lastReportedYaw;
    protected MinecraftClient mc;
    public ClientPlayHandler connection;
    private float lastReportedPitch;
    public float prevTimeInPortal;
    public float renderArmPitch;
    public int sprintingTicksLeft;
    private double lastReportedX;
    public float prevRenderArmPitch;
    private int positionUpdateTicks;
    public float timeInPortal;
    private float horseJumpPower;
    public float prevRenderArmYaw;
    public MovementInput movementInput;
    private boolean lastReportedSneaking;

    public static void setLegacyLocalPlayerMarker(int[] aint) {
        net.minecraft.client.entity.LocalPlayer.legacyLocalPlayerMarker = aint;
    }

    public boolean isUsingItem() {
        return super.isUsingItem();
    }

    public void onUpdate() {
        if (!this.isCurrentPlayerContext()
                || !this.world.isBlockLoaded(new BlockPos(this.posX, 0.0, this.posZ))) {
            return;
        }
        RemixClient.eventBus.post(new PlayerUpdateEvent());
        if (!this.isCurrentPlayerContext()) {
            return;
        }
        super.onUpdate();
        if (!this.isCurrentPlayerContext()) {
            return;
        }
        if (this.isRiding()) {
            this.connection.addToSendQueue(
                    new PlayerLookPacket(this.rotationYaw, this.rotationPitch, this.onGround));
            this.connection.addToSendQueue(
                    new C0CPacketInput(
                            this.moveStrafing,
                            this.moveForward,
                            this.movementInput.jump,
                            this.movementInput.sneak));
        } else {
            this.sendWalkingUpdate();
        }
    }

    public LocalPlayer(
            MinecraftClient aNSValue,
            World worldValue,
            ClientPlayHandler connectionValue,
            StatFileWriter aNPValue) {
        super(worldValue, connectionValue.getGameProfile());
        this.connection = connectionValue;
        this.statWriter = aNPValue;
        this.mc = aNSValue;
        this.dimension = 0;
    }

    public void sendPlayerAbilities() {
        this.connection.addToSendQueue(new C13PacketPlayerAbilities(this.capabilities));
    }

    public void onEnchantmentCritical(Entity entity) {
        this.mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT_MAGIC);
    }

    public void moveEntity(double d0, double d1, double d2) {
        MoveEvent moveEvent = new MoveEvent(d0, d1, d2);
        RemixClient.eventBus.post(moveEvent);
        super.moveEntity(moveEvent.getX(), moveEvent.getY(), moveEvent.getZ());
        RemixClient.eventBus.post(new MoveEvent());
    }

    public void sendChatMessage(String text) {
        this.connection.addToSendQueue(
                new net.minecraft.network.play.client.C01PacketChatMessage(text));
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        return false;
    }

    public void swingItem() {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        NoSwingModule noSwing = ClientServices.moduleManager.noSwing;
        int i = noSwing.isEnabled() && noSwing.shouldSuppressSwing() ? 1 : 0;
        if (i == 0) {
            super.swingItem();
        }

        this.connection.addToSendQueue(new C0APacketAnimation());
    }

    public void moveEntityWithHeading(float strafe, float forward) {
        MovementHeadingEvent input = new MovementHeadingEvent(forward, strafe);
        RemixClient.eventBus.post(input);
        if (input.isCancelled()) {
            return;
        }

        super.moveEntityWithHeading(input.getStrafe(), input.getForward());
        RemixClient.eventBus.post(new MovementHeadingEvent());
    }

    public EntityItem dropOneItem(boolean flag) {
        DiggingAction diggingAction = flag ? DiggingAction.DROP_ALL_ITEMS : DiggingAction.DROP_ITEM;
        this.connection.addToSendQueue(
                new DiggingPacket(diggingAction, BlockPos.ORIGIN, Facing.DOWN));
        return null;
    }

    public void applyEntityCollision(Entity entity) {
        EntityPushEvent entityPushEvent = new EntityPushEvent(this);
        RemixClient.eventBus.post(entityPushEvent);
        if (!entityPushEvent.isCancelled()) {
            super.applyEntityCollision(entity);
        }
    }

    public void jump() {
        JumpEvent jumpEvent = new JumpEvent();
        RemixClient.eventBus.post(jumpEvent);
        if (!jumpEvent.isCancelled()) {
            this.motionY = this.getJumpUpwardsMotion();
            if (this.isPotionActive(Potion.JUMP)) {
                this.motionY =
                        this.motionY
                                + (this.getActivePotionEffect(Potion.JUMP).getAmplifier() + 1)
                                        * 0.1F;
            }

            if (this.isSprinting()) {
                ClientServices clientServices =
                        (ClientServices) RemixClient.INSTANCE.getClientServices();
                SprintModule sprint = ClientServices.moduleManager.sprint;
                float f =
                        (sprint.multiJumpSetting.asBoolean()
                                        ? MovementPhysicsHelper.getMoveDirectionYaw()
                                        : this.rotationYaw)
                                * (float) (Math.PI / 180.0);
                this.motionX = this.motionX - MathHelper.sin(f) * 0.2F;
                this.motionZ = this.motionZ + MathHelper.cos(f) * 0.2F;
            }

            this.isAirBorne = true;
        }
    }

    public void closeScreenAndDropStack() {
        this.inventory.setItemStack(null);
        super.closeScreen();
        this.mc.displayGuiScreen(null);
    }

    public void setPlayerSPHealth(float serverHealth) {
        boolean legacyFlow = GuiScreen.getLegacyScreenFlowMarker() != null;
        if (this.serverHealthInitialized) {
            this.applyServerHealthDelta(serverHealth, legacyFlow);
            if (!legacyFlow) {
                return;
            }
        }
        this.setHealth(serverHealth);
        this.serverHealthInitialized = true;
    }

    private void applyServerHealthDelta(float serverHealth, boolean legacyFlow) {
        float damage = this.getHealth() - serverHealth;
        if (damage <= 0.0F) {
            this.setHealth(serverHealth);
            if (!(damage < 0.0F)) {
                return;
            }
            this.hurtResistantTime = this.maxHurtResistantTime / 2;
            if (!legacyFlow) {
                return;
            }
        }

        this.lastDamage = damage;
        this.setHealth(this.getHealth());
        this.hurtResistantTime = this.maxHurtResistantTime;
        this.damageEntity(DamageSource.generic, damage);
        this.hurtTime = this.maxHurtTime = 10;
    }

    public void addChatComponentMessage(ChatComponent chatComponent) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }

    public BlockPos getPosition() {
        return new net.minecraft.util.BlockPos(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5);
    }

    public boolean isSneaking() {
        boolean flag = (this.movementInput != null && this.movementInput.sneak);
        return (flag) && !this.sleeping;
    }

    public void displayGui(IInteractionObject iInteractionObject) {
        String text = GuiScreen.getLegacyScreenFlowMarker();
        String textValue = iInteractionObject.getGuiID();
        String textValue2 = text;
        if ("minecraft:crafting_table".equals(textValue)) {
            this.mc.displayGuiScreen(new GuiCrafting(this.inventory, this.world));
            if (textValue2 == null) {
                return;
            }
        }

        if ("minecraft:enchanting_table".equals(textValue)) {
            this.mc.displayGuiScreen(
                    new GuiEnchantment(this.inventory, this.world, iInteractionObject));
            if (textValue2 == null) {
                return;
            }
        }

        if ("minecraft:anvil".equals(textValue)) {
            this.mc.displayGuiScreen(new GuiRepair(this.inventory, this.world));
        }
    }

    public void onCriticalHit(Entity entity) {
        this.mc.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT);
    }

    public void setXPStats(float bcRValue, int bcsValue, int bdLValue) {
        this.experience = bcRValue;
        this.experienceTotal = bcsValue;
        this.experienceLevel = bdLValue;
    }

    public void displayGUIChest(IInventory iInventory) {
        String text = GuiScreen.getLegacyScreenFlowMarker();
        String textValue =
                iInventory instanceof IInteractionObject
                        ? ((IInteractionObject) iInventory).getGuiID()
                        : "minecraft:container";
        if ("minecraft:chest".equals(textValue)) {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, iInventory));
            if (text == null) {
                return;
            }
        }

        if ("minecraft:hopper".equals(textValue)) {
            this.mc.displayGuiScreen(new GuiHopper(this.inventory, iInventory));
            if (text == null) {
                return;
            }
        }

        if ("minecraft:furnace".equals(textValue)) {
            this.mc.displayGuiScreen(new GuiFurnace(this.inventory, iInventory));
            if (text == null) {
                return;
            }
        }

        if ("minecraft:brewing_stand".equals(textValue)) {
            this.mc.displayGuiScreen(new GuiBrewingStand(this.inventory, iInventory));
            if (text == null) {
                return;
            }
        }

        if ("minecraft:beacon".equals(textValue)) {
            this.mc.displayGuiScreen(new GuiBeacon(this.inventory, iInventory));
            if (text == null) {
                return;
            }
        }

        if (!"minecraft:dispenser".equals(textValue) && !"minecraft:dropper".equals(textValue)) {
            this.mc.displayGuiScreen(new GuiChest(this.inventory, iInventory));
            if (text == null) {
                return;
            }
        }

        this.mc.displayGuiScreen(new GuiDispenser(this.inventory, iInventory));
    }

    public void sendHorseInventory() {
        this.connection.addToSendQueue(
                new C0BPacketEntityAction(this, C0BPacketEntityActionAction.OPEN_INVENTORY));
    }

    public boolean isRidingHorse() {
        return this.ridingEntity != null
                && this.ridingEntity instanceof net.minecraft.entity.passive.EntityHorse
                && ((net.minecraft.entity.passive.EntityHorse) this.ridingEntity).isHorseSaddled();
    }

    // region String decoding support

    // endregion

    private boolean isCurrentPlayerContext() {
        return this.mc != null
                && this.mc.player == this
                && this.mc.world == this.world
                && this.connection != null;
    }

    public void sendWalkingUpdate() {
        if (!this.isCurrentPlayerContext()) {
            return;
        }
        MotionEvent motion =
                new MotionEvent(
                        this.rotationYaw,
                        this.rotationPitch,
                        this.lastReportedYaw,
                        this.lastReportedPitch,
                        this.posX,
                        this.getEntityBoundingBox().minY,
                        this.posZ,
                        this.onGround);
        RemixClient.eventBus.post(motion);
        if (!this.isCurrentPlayerContext()) {
            return;
        }
        if (motion.isCancelled()) {
            RemixClient.eventBus.post(new MotionEvent(MotionPhase.POST));
            return;
        }

        boolean sprinting = this.isSprinting();
        if (sprinting != this.lastReportedSprinting) {
            this.connection.addToSendQueue(
                    new C0BPacketEntityAction(
                            this,
                            sprinting
                                    ? C0BPacketEntityActionAction.START_SPRINTING
                                    : C0BPacketEntityActionAction.STOP_SPRINTING));
            this.lastReportedSprinting = sprinting;
        }
        boolean sneaking = this.isSneaking();
        if (sneaking != this.lastReportedSneaking) {
            this.connection.addToSendQueue(
                    new C0BPacketEntityAction(
                            this,
                            sneaking
                                    ? C0BPacketEntityActionAction.START_SNEAKING
                                    : C0BPacketEntityActionAction.STOP_SNEAKING));
            this.lastReportedSneaking = sneaking;
        }

        if (this.isCurrentViewEntity()) {
            double x = motion.x, y = motion.y, z = motion.z;
            float yaw = motion.getYaw(), pitch = motion.getPitch();
            double dx = x - this.lastReportedX,
                    dy = y - this.lastReportedY,
                    dz = z - this.lastReportedZ;
            boolean moved = dx * dx + dy * dy + dz * dz > 9.0E-4 || this.positionUpdateTicks >= 20;
            boolean turned = yaw != motion.getPreviousYaw() || pitch != motion.getPreviousPitch();
            boolean grounded = motion.onGround;
            Packet update;
            if (this.ridingEntity != null) {
                update =
                        new PlayerPositionLookPacket(
                                this.motionX, -999.0, this.motionZ, yaw, pitch, grounded);
                moved = false;
            } else if ((moved && turned) || motion.shouldForcePositionAndRotationPacket()) {
                update = new PlayerPositionLookPacket(x, y, z, yaw, pitch, grounded);
            } else if (moved) {
                update = new PlayerPositionPacket(x, y, z, grounded);
            } else if (turned) {
                update = new PlayerLookPacket(yaw, pitch, grounded);
            } else {
                update = new PlayerMovementPacket(grounded);
            }
            this.connection.addToSendQueue(update);

            this.positionUpdateTicks++;
            if (moved) {
                this.lastReportedX = x;
                this.lastReportedY = y;
                this.lastReportedZ = z;
                this.positionUpdateTicks = 0;
            }
            if (turned) {
                this.lastReportedYaw = yaw;
                this.lastReportedPitch = pitch;
            }
        }
        RemixClient.eventBus.post(new MotionEvent(MotionPhase.POST));
    }

    public void displayGUIBook(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item == Items.writable_book) {
            this.mc.displayGuiScreen(new GuiScreenBook(this, itemStack, true));
        }
    }

    public boolean isLocalUser() {
        return true;
    }

    public void addStat(StatBase statBase, int p171_1) {
        if (statBase != null && statBase.isIndependent) {
            super.addStat(statBase, p171_1);
        }
    }

    public void heal(float f) {}

    public void playSound(String text, float p171_1, float p171_2) {
        this.world.playSound(this.posX, this.posY, this.posZ, text, p171_1, p171_2, false);
    }

    public static int[] getLegacyLocalPlayerMarker() {
        return net.minecraft.client.entity.LocalPlayer.legacyLocalPlayerMarker;
    }

    public void onLivingUpdate() {
        boolean legacyMovementBranches = GuiScreen.getLegacyScreenFlowMarker() != null;
        if (this.sprintingTicksLeft > 0) {
            this.sprintingTicksLeft--;
            if (this.sprintingTicksLeft == 0) {
                this.setSprinting(false);
            }
        }

        if (this.sprintToggleTimer > 0) {
            this.sprintToggleTimer--;
        }

        this.updatePortalOverlay(legacyMovementBranches);

        if (this.timeUntilPortal > 0) {
            this.timeUntilPortal--;
        }

        boolean wasJumping = this.movementInput.jump;
        boolean wasSneaking = this.movementInput.sneak;
        boolean wasMovingForward = this.movementInput.moveForward >= 0.8F;
        this.movementInput.updatePlayerMoveState();
        if ((this.isUsingItem()
                        || MinecraftClient.getMinecraft().getItemRenderer().forceBlockAnimation)
                && !this.isRiding()) {
            double d0 = 0.2F;
            ItemSlowdownEvent itemSlowdownEvent = new ItemSlowdownEvent(d0);
            RemixClient.eventBus.post(itemSlowdownEvent);
            if (!itemSlowdownEvent.isCancelled()) {
                this.movementInput.moveStrafe =
                        (float) (this.movementInput.moveStrafe * itemSlowdownEvent.getMultiplier());
                this.movementInput.moveForward =
                        (float)
                                (this.movementInput.moveForward
                                        * itemSlowdownEvent.getMultiplier());
                this.sprintToggleTimer = 0;
            }
        }

        this.pushOutOfBlocks(
                this.posX - this.width * 0.35,
                this.getEntityBoundingBox().minY + 0.5,
                this.posZ + this.width * 0.35);
        this.pushOutOfBlocks(
                this.posX - this.width * 0.35,
                this.getEntityBoundingBox().minY + 0.5,
                this.posZ - this.width * 0.35);
        this.pushOutOfBlocks(
                this.posX + this.width * 0.35,
                this.getEntityBoundingBox().minY + 0.5,
                this.posZ - this.width * 0.35);
        this.pushOutOfBlocks(
                this.posX + this.width * 0.35,
                this.getEntityBoundingBox().minY + 0.5,
                this.posZ + this.width * 0.35);
        this.updateSprintingFromInput(wasSneaking, wasMovingForward, legacyMovementBranches);
        this.updateFlightToggle(wasJumping, legacyMovementBranches);

        if (this.capabilities.isFlying && this.isCurrentViewEntity()) {
            if (this.movementInput.sneak) {
                this.motionY = this.motionY - this.capabilities.getFlySpeed() * 3.0F;
            }

            if (this.movementInput.jump) {
                this.motionY = this.motionY + this.capabilities.getFlySpeed() * 3.0F;
            }
        }

        this.updateHorseJumpCharge(wasJumping, legacyMovementBranches);

        super.onLivingUpdate();
        if (this.onGround
                && this.capabilities.isFlying
                && !this.mc.playerController.isSpectatorMode()) {
            this.capabilities.isFlying = false;
            this.sendPlayerAbilities();
        }
    }

    private void updatePortalOverlay(boolean legacyMovementBranches) {
        this.prevTimeInPortal = this.timeInPortal;
        if (this.inPortal) {
            if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
                this.mc.displayGuiScreen(null);
            }
            if (this.timeInPortal == 0.0F) {
                this.mc
                        .getSoundHandler()
                        .playSound(
                                PositionedSoundRecord.createPositionedSoundRecord(
                                        new ResourceLocation("portal.trigger"),
                                        this.rand.nextFloat() * 0.4F + 0.8F));
            }
            this.timeInPortal += 0.0125F;
            if (this.timeInPortal >= 1.0F) {
                this.timeInPortal = 1.0F;
            }
            this.inPortal = false;
            if (!legacyMovementBranches) {
                return;
            }
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }

        if (this.isPotionActive(Potion.CONFUSION)
                && this.getActivePotionEffect(Potion.CONFUSION).getDuration() > 60) {
            this.timeInPortal += 0.006666667F;
            if (!(this.timeInPortal > 1.0F)) {
                return;
            }
            this.timeInPortal = 1.0F;
            if (!legacyMovementBranches) {
                return;
            }
        }
        if (this.timeInPortal > 0.0F) {
            this.timeInPortal -= 0.05F;
        }
        if (this.timeInPortal < 0.0F) {
            this.timeInPortal = 0.0F;
        }
    }

    private void updateSprintingFromInput(
            boolean wasSneaking, boolean wasMovingForward, boolean legacyMovementBranches) {
        boolean canSprint =
                this.getFoodStats().getFoodLevel() > 6.0F || this.capabilities.allowFlying;
        float sprintThreshold = 0.8F;
        if (this.onGround
                && !wasSneaking
                && !wasMovingForward
                && this.movementInput.moveForward >= sprintThreshold
                && !this.isSprinting()
                && canSprint
                && !this.isUsingItem()
                && !this.isPotionActive(Potion.BLINDNESS)) {
            boolean waitingForSecondTap =
                    this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown();
            if (waitingForSecondTap) {
                this.sprintToggleTimer = 7;
            }
            if (!waitingForSecondTap || legacyMovementBranches) {
                this.setSprinting(true);
            }
        }

        if (!this.isSprinting()
                && this.movementInput.moveForward >= sprintThreshold
                && canSprint
                && !this.isUsingItem()
                && !this.isPotionActive(Potion.BLINDNESS)
                && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            this.setSprinting(true);
        }
        if (this.isSprinting()
                && (this.movementInput.moveForward < sprintThreshold
                        || this.collidedHorizontally
                        || !canSprint)) {
            this.setSprinting(false);
        }
    }

    private void updateFlightToggle(boolean wasJumping, boolean legacyMovementBranches) {
        if (!this.capabilities.allowFlying) {
            return;
        }
        if (this.mc.playerController.isSpectatorMode()) {
            if (this.capabilities.isFlying) {
                return;
            }
            this.capabilities.isFlying = true;
            this.sendPlayerAbilities();
            if (!legacyMovementBranches) {
                return;
            }
        }

        if (!wasJumping && this.movementInput.jump) {
            if (this.flyToggleTimer == 0) {
                this.flyToggleTimer = 7;
                if (!legacyMovementBranches) {
                    return;
                }
            }
            this.capabilities.isFlying = !this.capabilities.isFlying;
            this.sendPlayerAbilities();
            this.flyToggleTimer = 0;
        }
    }

    private void updateHorseJumpCharge(boolean wasJumping, boolean legacyMovementBranches) {
        if (!this.isRidingHorse()) {
            this.horseJumpPower = 0.0F;
            return;
        }
        if (this.horseJumpPowerCounter < 0 && ++this.horseJumpPowerCounter == 0) {
            this.horseJumpPower = 0.0F;
        }
        if (wasJumping && !this.movementInput.jump) {
            this.horseJumpPowerCounter = -10;
            this.sendHorseJump();
            if (!legacyMovementBranches) {
                return;
            }
        }
        if (!wasJumping && this.movementInput.jump) {
            this.horseJumpPowerCounter = 0;
            this.horseJumpPower = 0.0F;
            if (!legacyMovementBranches) {
                return;
            }
        }
        if (!wasJumping) {
            return;
        }

        this.horseJumpPowerCounter++;
        if (this.horseJumpPowerCounter < 10) {
            this.horseJumpPower = this.horseJumpPowerCounter * 0.1F;
            if (!legacyMovementBranches) {
                return;
            }
        }
        this.horseJumpPower = 0.8F + 2.0F / (this.horseJumpPowerCounter - 9) * 0.1F;
        if (legacyMovementBranches) {
            this.horseJumpPower = 0.0F;
        }
    }

    public void turn(float mouseYaw, float mousePitch) {
        GuiScreen.getLegacyScreenFlowMarker();
        RemixClient.INSTANCE.getClientServices();
        CameraModule camera = ClientServices.moduleManager.camera;
        float oldPitch = this.rotationPitch;
        float oldYaw = this.rotationYaw;
        double yawDelta = mouseYaw * 0.15;
        double pitchDelta = mousePitch * 0.15;
        boolean lockPlayerView =
                camera.isEnabled()
                        && camera.lockF5Setting.asBoolean()
                        && this.mc.gameSettings.thirdPersonView > 0;
        if (!lockPlayerView) {
            this.rotationYaw = (float) (this.rotationYaw + yawDelta);
            this.rotationPitch = (float) (this.rotationPitch - pitchDelta);
        }

        camera.lockedYaw = (float) (camera.lockedYaw + yawDelta);
        camera.lockedPitch = (float) (camera.lockedPitch - pitchDelta);
        camera.lockedPitch = MathHelper.clamp(camera.lockedPitch, -90.0F, 90.0F);
        if (!camera.isEnabled() || !camera.noLimitSetting.asBoolean()) {
            this.rotationPitch = MathHelper.clamp(this.rotationPitch, -90.0F, 90.0F);
        }

        this.prevRotationPitch += this.rotationPitch - oldPitch;
        this.prevRotationYaw += this.rotationYaw - oldYaw;
        // Keep the original linkage between the global marker and screen flow.
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            GuiScreen.setLegacyScreenFlowMarker(packedTextFragments[0]);
        }
    }

    // region Static initialization and string tables
    static {
        net.minecraft.client.entity.LocalPlayer.packedTextFragments = new String[3];
        net.minecraft.client.entity.LocalPlayer.packedTextFragments[0] = new String("FYviCb");
        int[] aint = new int[2];
        setLegacyLocalPlayerMarker(aint);
    }

    // endregion

    protected boolean isCurrentViewEntity() {
        return this.mc.getRenderViewEntity() == this;
    }

    public void addChatMessage(ChatComponent chatComponent) {
        this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }

    protected void damageEntity(DamageSource damageSource, float p171_1) {
        if (!this.isEntityInvulnerable(damageSource)) {
            this.setHealth(this.getHealth() - p171_1);
        }
    }

    public String getClientBrand() {
        return this.clientBrand;
    }

    public float getHorseJumpPower() {
        return this.horseJumpPower;
    }

    public void mountEntity(Entity entity) {
        super.mountEntity(entity);
        if (entity instanceof EntityMinecart) {
            this.mc
                    .getSoundHandler()
                    .playSound(new MovingSoundMinecartRiding(this, (EntityMinecart) entity));
        }
    }

    public void updateEntityActionState() {
        super.updateEntityActionState();
        if (this.isCurrentViewEntity()) {
            this.moveStrafing = this.movementInput.moveStrafe;
            this.moveForward = this.movementInput.moveForward;
            this.isJumping = this.movementInput.jump;
            this.prevRenderArmYaw = this.renderArmYaw;
            this.prevRenderArmPitch = this.renderArmPitch;
            this.renderArmPitch =
                    (float)
                            (this.renderArmPitch
                                    + (this.rotationPitch - this.renderArmPitch) * 0.5);
            this.renderArmYaw =
                    (float) (this.renderArmYaw + (this.rotationYaw - this.renderArmYaw) * 0.5);
        }
    }

    public StatFileWriter getStatFileWriter() {
        return this.statWriter;
    }

    public boolean canCommandSenderUseCommand(int p171_0, String text) {
        return p171_0 <= 0;
    }

    public void openEditCommandBlock(CommandBlockLogic commandBlockLogic) {
        this.mc.displayGuiScreen(new GuiCommandBlock(commandBlockLogic));
    }

    private boolean isOpenBlockSpace(BlockPos blockPos) {
        return !this.world.getBlockState(blockPos).getBlock().isNormalCube()
                && !this.world.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube();
    }

    public void closeScreen() {
        this.connection.addToSendQueue(new C0DPacketCloseWindow(this.openContainer.windowId));
        this.closeScreenAndDropStack();
    }

    public void openEditSign(TileEntitySign tileEntitySign) {
        String text = GuiScreen.getLegacyScreenFlowMarker();
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        String textValue = text;
        SignControlModule signControl = ClientServices.moduleManager.signControl;
        if (signControl.isEnabled()) {
            this.mc
                    .getNetworkHandler()
                    .addToSendQueue(
                            new C12PacketUpdateSign(
                                    tileEntitySign.getPos(), tileEntitySign.signText));
            tileEntitySign.setEditable(true);
            if (textValue == null) {
                return;
            }
        }

        this.mc.displayGuiScreen(new GuiEditSign(tileEntitySign));
    }

    protected boolean pushOutOfBlocks(double d0, double d1, double d2) {
        PushOutOfBlocksEvent pushOutOfBlocksEvent = new PushOutOfBlocksEvent(this);
        RemixClient.eventBus.post(pushOutOfBlocksEvent);
        if (!this.noClip && !pushOutOfBlocksEvent.isCancelled()) {
            BlockPos blockPos = new BlockPos(d0, d1, d2);
            double d3 = d0 - blockPos.getX();
            double d4 = d2 - blockPos.getZ();
            if (!this.isOpenBlockSpace(blockPos)) {
                byte b0 = -1;
                double d5 = 9999.0;
                if (this.isOpenBlockSpace(blockPos.offsetWest()) && d3 < d5) {
                    d5 = d3;
                    b0 = 0;
                }

                if (this.isOpenBlockSpace(blockPos.offsetEast()) && 1.0 - d3 < d5) {
                    d5 = 1.0 - d3;
                    b0 = 1;
                }

                if (this.isOpenBlockSpace(blockPos.offsetNorth()) && d4 < d5) {
                    d5 = d4;
                    b0 = 4;
                }

                if (this.isOpenBlockSpace(blockPos.offsetSouth()) && 1.0 - d4 < d5) {
                    d5 = 1.0 - d4;
                    b0 = 5;
                }

                float motionZValue = 0.1F;
                if (b0 == 0) {
                    this.motionX = -motionZValue;
                }

                if (b0 == 1) {
                    this.motionX = motionZValue;
                }

                if (b0 == 4) {
                    this.motionZ = -motionZValue;
                }

                if (b0 == 5) {
                    this.motionZ = motionZValue;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    protected void joinEntityItemWithWorld(EntityItem entityItem) {}

    public void displayVillagerTradeGui(IMerchant iMerchant) {
        this.mc.displayGuiScreen(new GuiMerchant(this.inventory, iMerchant, this.world));
    }

    public void setSprinting(boolean p171_0) {
        super.setSprinting(p171_0);
        this.sprintingTicksLeft = p171_0 ? 600 : 0;
    }

    public void setClientBrand(String aNQValue) {
        this.clientBrand = aNQValue;
    }

    public void respawnPlayer() {
        this.connection.addToSendQueue(
                new C16PacketClientStatus(C16PacketClientStatusEnumState.PERFORM_RESPAWN));
    }

    protected void sendHorseJump() {
        this.connection.addToSendQueue(
                new C0BPacketEntityAction(
                        this,
                        C0BPacketEntityActionAction.RIDING_JUMP,
                        (int) (this.getHorseJumpPower() * 100.0F)));
    }

    public boolean isServerWorld() {
        return true;
    }

    public void displayGUIHorse(EntityHorse entityHorse, IInventory iInventory) {
        this.mc.displayGuiScreen(
                new GuiScreenHorseInventory(this.inventory, iInventory, entityHorse));
    }
}
