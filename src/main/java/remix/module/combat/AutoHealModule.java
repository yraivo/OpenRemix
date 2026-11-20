package remix.module.combat;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.HeldItemChangePacket;
import net.minecraft.network.play.client.UseItemPacket;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import remix.module.combat.AutoHealMode;
import remix.module.combat.AutoHealThrowMode;
import remix.module.combat.AutoHealUsePhase;
import remix.util.time.ElapsedTimer;
import net.minecraft.client.gui.GuiScreen;
import remix.util.inventory.InventoryItemHelper;
import remix.util.inventory.ItemSlot;
import remix.event.MotionPhase;
import remix.util.movement.MovementPhysicsHelper;
import remix.util.movement.MovementUtils;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.util.entity.RaycastHelper;
import remix.util.entity.RotationHelper;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.client.ClientServices;
import remix.event.MotionEvent;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.hidden.InventoryActionsTrackerModule;
import remix.setting.Setting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/** AutoHeal module: motion/move/packet events and 13 settings. */
public final class AutoHealModule extends ModuleBase {
    private List<ItemSlot> goldenApples;
    private Setting noTargetSetting;
    private Setting soupsSetting;
    private Setting potionsSetting;
    private Setting slotSetting;
    private Setting gappleSetting;
    private Setting headsSetting;
    private boolean holdingServerSlot;
    private Setting grabDelaySetting;
    private Setting healDelaySetting;
    private static String[] packedTextFragments;
    private static String[] packedTextFragments2;
    private final Setting<AutoHealMode> modeSetting =
            new Setting("Mode", this, AutoHealMode.NCP);
    public boolean inventoryGrabFlag;
    private Setting healthSetting;
    private Setting jumpBoostSetting;
    private List<ItemSlot> healingPotions;
    private final Setting<AutoHealThrowMode> throwSetting =
            new Setting("Throw", this, AutoHealThrowMode.DOWN);
    private static String[] packedTextFragments3;
    private Setting fromInventorySetting;
    private ElapsedTimer heldSlotTimer;
    private float[] lastRotations;
    public boolean suppressHorizontalPotionJump;
    private int previousHotbarSlot;
    private List<ItemSlot> headItems;
    private List<ItemSlot> soups;
    private Comparator<ItemSlot> potionPriorityComparator;
    public ItemSlot selectedHealingItem;
    private List<ItemSlot> effectPotions;
    public AutoHealUsePhase usePhase;
    private Setting effectPotionsSetting;
    private Setting predictSetting;

    private boolean isHealingSplashPotion(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemPotion && ItemPotion.isSplash(itemStack.getMetadata())) {
            for (Object obj : ItemPotion.getPotionEffects(itemStack)) {
                PotionEffect potionEffect = (PotionEffect) obj;
                if (Potion.potionTypes[potionEffect.getPotionID()] == Potion.HEAL) {
                    return true;
                }
            }
        }
        return false;
    }

    // region Static initialization and string tables
    static {
        remix.module.combat.AutoHealModule.packedTextFragments = new String[3];
        remix.module.combat.AutoHealModule.packedTextFragments[0] = new String("neS2zc");
    }

    // endregion

    private boolean isGoldenApple(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemAppleGold;
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() == PacketDirection.SEND
                && event.getPacket() instanceof HeldItemChangePacket
                && this.holdingServerSlot) {
            event.setCancelled(true);
        }
    }

    public AutoHealModule() {
        super("AutoHeal", ModuleDefaults.ofCategory(ModuleCategory.COMBAT));
        this.healDelaySetting = new Setting("HealDelay", this, 300.0, 0.0, 1000.0, 50.0);
        this.grabDelaySetting = new Setting("GrabDelay", this, 300.0, 0.0, 1000.0, 50.0);
        this.healthSetting = new Setting("Health", this, 13.0, 0.0, 20.0, 1.0);
        this.slotSetting = new Setting("Slot", this, 9.0, 1.0, 9.0, 1.0);
        this.fromInventorySetting = new Setting("FromInventory", this, true);
        this.predictSetting = new Setting("Predict", this, true);
        this.noTargetSetting = new Setting("NoTarget", this, true);
        this.potionsSetting = new Setting("Potions", this, true);
        this.soupsSetting = new Setting("Soups", this, true);
        this.headsSetting = new Setting("Heads", this, true);
        this.effectPotionsSetting = new Setting("EffectPotions", this, true);
        this.gappleSetting = new Setting("Gapple", this, true);
        this.jumpBoostSetting = new Setting("JumpBoost", this, true);
        this.potionPriorityComparator =
                Comparator.comparingInt(
                        itemSlot -> -this.scoreMissingPotionEffects(itemSlot.itemStack));
        this.heldSlotTimer = new ElapsedTimer();
        this.setName("Auto Heal");
    }

    private boolean hasEqualOrStrongerEffect(PotionEffect candidateEffect) {
        for (Object obj : minecraft.player.getActivePotionEffects()) {
            PotionEffect activeEffect = (PotionEffect) obj;
            if (activeEffect.getEffectName().equals(candidateEffect.getEffectName())
                    && activeEffect.getAmplifier() >= candidateEffect.getAmplifier()) {
                return true;
            }
        }
        return false;
    }

    @Subscribe(mc = MotionEvent.class, priority = SubscriberPriority.HIGHEST)
    public void onMotion(MotionEvent event) {
        LocalPlayer player = minecraft.player;
        InventoryPlayer inv = player.inventory;
        AutoHealThrowMode throwMode = this.throwSetting.getValue();

        if (event.getPhase() == MotionPhase.PRE) {
            if (player.onGround) {
                this.suppressHorizontalPotionJump = false;
            }

            GuiScreen currentScreen = minecraft.currentScreen;
            if (this.timer.hasElapsed((long) this.healDelaySetting.asDouble())
                    && player.onGround
                    && (minecraft.inGameHasFocus || currentScreen instanceof GuiInventory)
                    && !ClientServices.moduleManager.scaffold.isEnabled()
                    && (!this.noTargetSetting.asBoolean()
                            || ClientServices.moduleManager.aura.currentTarget == null)) {

                GuiInventory guiInventory = new GuiInventory(player);
                this.collectHealingItems();
                double healthThreshold = this.healthSetting.asDouble();
                double currentHealth = player.getHealth() + player.getAbsorptionAmount();

                if (currentHealth <= healthThreshold) {
                    if (this.potionsSetting.asBoolean() && !this.healingPotions.isEmpty()) {
                        this.selectedHealingItem = this.healingPotions.get(0);
                    } else if (this.soupsSetting.asBoolean() && !this.soups.isEmpty()) {
                        this.selectedHealingItem = this.soups.get(0);
                    } else if (this.headsSetting.asBoolean() && !this.headItems.isEmpty()) {
                        this.selectedHealingItem = this.headItems.get(0);
                    } else if (this.gappleSetting.asBoolean() && !this.goldenApples.isEmpty()) {
                        this.selectedHealingItem = this.goldenApples.get(0);
                    }
                }

                if (this.selectedHealingItem == null
                        && this.effectPotionsSetting.asBoolean()
                        && !this.effectPotions.isEmpty()) {
                    this.effectPotions.sort(this.potionPriorityComparator);
                    this.selectedHealingItem = this.effectPotions.get(0);
                }

                if (this.selectedHealingItem != null) {
                    if (this.selectedHealingItem.slotIndex < 36) {
                        if (!InventoryActionsTrackerModule.inventoryActionTimer.hasElapsed(
                                (long) this.grabDelaySetting.asDouble())) {
                            this.selectedHealingItem = null;
                            return;
                        }

                        if (!InventoryActionsTrackerModule.inventoryOpenStatusSent) {
                            this.inventoryGrabFlag = true;
                        }

                        InventoryItemHelper.clickSlot(
                                guiInventory,
                                this.selectedHealingItem.slotIndex,
                                (int) this.slotSetting.asDouble() - 1,
                                2);
                        this.selectedHealingItem.slotIndex =
                                35 + (int) this.slotSetting.asDouble();
                        if (this.modeSetting.getValue() == AutoHealMode.LEGIT) {
                            this.usePhase = AutoHealUsePhase.SELECT_SLOT;
                            return;
                        }
                    }

                    if (this.selectedHealingItem.itemStack.getItem() instanceof ItemPotion
                            && ItemPotion.isSplash(this.selectedHealingItem.itemStack.getMetadata())
                            && (this.modeSetting.getValue() != AutoHealMode.LEGIT
                                    || this.usePhase == AutoHealUsePhase.SELECT_SLOT)) {
                        if (throwMode == AutoHealThrowMode.DOWN) {
                            if (!MovementUtils.hasMoved(player, 0.0)
                                    || !this.predictSetting.asBoolean()) {
                                event.setPitch(90.0F);
                            } else {
                                float[] rots =
                                        RotationHelper.rotationsToPoint(
                                                MovementPhysicsHelper.interpolateEntityPosition(
                                                        player, 3.0F));
                                event.setYaw(rots[0]);
                                event.setPitch(rots[1]);
                            }
                        } else {
                            event.setPitch(-90.0F);
                        }
                    }

                    this.timer.reset();
                }
            }

            if (this.selectedHealingItem != null
                    && this.selectedHealingItem.itemStack != null
                    && this.modeSetting.getValue() == AutoHealMode.LEGIT) {
                int slot = this.selectedHealingItem.slotIndex;
                AuraModule aura = ClientServices.moduleManager.aura;
                if (aura.isEnabled()) {
                    aura.attackTimerTicks = -1;
                }

                if (this.usePhase == AutoHealUsePhase.SELECT_SLOT) {
                    this.previousHotbarSlot = inv.currentItem;
                    inv.currentItem = slot - 36;
                    minecraft.playerController.updateController();
                    this.usePhase = AutoHealUsePhase.USE_ITEM;
                } else if (this.usePhase == AutoHealUsePhase.USE_ITEM) {
                    if (throwMode == AutoHealThrowMode.JUMP
                            && this.isHealingSplashPotion(this.selectedHealingItem.itemStack)) {
                        player.motionY = 0.42F;
                        this.suppressHorizontalPotionJump = true;
                    }

                    minecraft.objectMouseOver =
                            RaycastHelper.rayTrace(this.lastRotations, 4.5, false, 1.0F);
                    boolean holdingGapple =
                            this.isGoldenApple(this.selectedHealingItem.itemStack)
                                    && minecraft.player.getCurrentEquippedItem()
                                            == this.selectedHealingItem.itemStack;
                    if (holdingGapple) {
                        minecraft.gameSettings.keyBindUseItem.pressed = true;
                        this.usePhase = AutoHealUsePhase.USE_ITEM;
                    } else {
                        minecraft.rightClickMouse();
                        this.usePhase = AutoHealUsePhase.RESTORE_SLOT;
                    }
                } else if (this.usePhase == AutoHealUsePhase.RESTORE_SLOT) {
                    minecraft.gameSettings.keyBindUseItem.pressed = false;
                    inv.currentItem = this.previousHotbarSlot;
                    this.selectedHealingItem = null;
                    this.timer.reset();
                    this.usePhase = AutoHealUsePhase.SELECT_SLOT;
                }
            }

            this.lastRotations = new float[] {event.getYaw(), event.getPitch()};
        } else if (event.getPhase() == MotionPhase.POST) {
            int currentSlot = inv.currentItem;
            if (this.selectedHealingItem != null
                    && this.selectedHealingItem.itemStack != null
                    && this.modeSetting.getValue() != AutoHealMode.LEGIT) {
                if (!this.holdingServerSlot) {
                    int slotIndex = this.selectedHealingItem.slotIndex;
                    ItemStack itemStack = this.selectedHealingItem.itemStack;
                    if (slotIndex - 36 != currentSlot) {
                        this.sendPacketWithoutEvent(new HeldItemChangePacket(slotIndex - 36));
                        minecraft.playerController.updateController();
                        this.sendPacket(new UseItemPacket(itemStack));
                        boolean holdingGapple =
                                this.isGoldenApple(this.selectedHealingItem.itemStack)
                                        && this.selectedHealingItem.itemStack
                                                == minecraft.player.inventory.getStackInSlot(
                                                        slotIndex - 36);
                        if (holdingGapple) {
                            this.holdingServerSlot = true;
                            this.heldSlotTimer.reset();
                            return;
                        }

                        this.sendPacketWithoutEvent(new HeldItemChangePacket(currentSlot));
                    } else {
                        this.sendPacket(new UseItemPacket(itemStack));
                    }

                    if (throwMode == AutoHealThrowMode.JUMP
                            && this.isHealingSplashPotion(this.selectedHealingItem.itemStack)) {
                        player.motionY = MovementUtils.getJumpMotion();
                        this.suppressHorizontalPotionJump = true;
                    }

                    this.selectedHealingItem = null;
                    return;
                }

                boolean itemChanged =
                        this.selectedHealingItem.itemStack
                                != minecraft.player.inventory.getStackInSlot(
                                        this.selectedHealingItem.slotIndex - 36);
                if (this.heldSlotTimer.elapsedAndReset(2000L) || itemChanged) {
                    this.sendPacketWithoutEvent(new HeldItemChangePacket(currentSlot));
                    this.holdingServerSlot = false;
                }
            }
        }
    }

    private boolean isUsefulEffectPotion(ItemStack itemStack) {
        if (this.isInvalidItemStack(itemStack)) {
            return false;
        }

        int score = 0;
        if (itemStack.getItem() instanceof ItemPotion && ItemPotion.isSplash(itemStack.getMetadata())) {
            LocalPlayer player = minecraft.player;
            List list = ItemPotion.getPotionEffects(itemStack);
            boolean jumpBoost = this.jumpBoostSetting.asBoolean();
            Potion[] potionTypes = Potion.potionTypes;
            int regenId = Potion.REGENERATION.id;
            int jumpId = Potion.JUMP.id;

            for (Object obj : list) {
                PotionEffect potionEffect = (PotionEffect) obj;
                int id = potionEffect.getPotionID();
                if (!this.hasEqualOrStrongerEffect(potionEffect)
                        && (id != regenId || player.getHealth() < player.getMaxHealth())) {
                    if (potionTypes[id].isBadEffect()) {
                        score--;
                    } else if (id == jumpId && !jumpBoost) {
                        score = -69;
                    } else {
                        score = 1;
                    }
                }
            }
        }

        return score > 0;
    }

    private void collectHealingItems() {
        List<ItemSlot> healingPotionItems = this.healingPotions = new ArrayList<>();
        List<ItemSlot> soupItems = this.soups = new ArrayList<>();
        List<ItemSlot> headItems = this.headItems = new ArrayList<>();
        List<ItemSlot> effectPotionItems = this.effectPotions = new ArrayList<>();
        List<ItemSlot> goldenAppleItems = this.goldenApples = new ArrayList<>();

        boolean includePotions = this.potionsSetting.asBoolean();
        boolean includeSoups = this.soupsSetting.asBoolean();
        boolean includeHeads = this.headsSetting.asBoolean();
        boolean includeEffects = this.effectPotionsSetting.asBoolean();
        boolean includeGoldenApples = this.gappleSetting.asBoolean();
        int firstSlot = this.fromInventorySetting.asBoolean() ? 9 : 36;
        Container container = minecraft.player.inventoryContainer;

        for (int slotIndex = 44; slotIndex >= firstSlot; slotIndex--) {
            ItemStack itemStack = container.getSlot(slotIndex).getStack();
            if (this.isInvalidItemStack(itemStack)) {
                continue;
            }

            boolean healingSplashPotion = this.isHealingSplashPotion(itemStack);
            if (includePotions && healingSplashPotion) {
                healingPotionItems.add(new ItemSlot(itemStack, slotIndex));
            } else if (includeSoups && this.isSoup(itemStack)) {
                soupItems.add(new ItemSlot(itemStack, slotIndex));
            } else if (includeHeads && this.isHeadItem(itemStack)) {
                headItems.add(new ItemSlot(itemStack, slotIndex));
            } else if (includeEffects && this.isUsefulEffectPotion(itemStack) && !healingSplashPotion) {
                effectPotionItems.add(new ItemSlot(itemStack, slotIndex));
            } else if (includeGoldenApples && this.isGoldenApple(itemStack)) {
                goldenAppleItems.add(new ItemSlot(itemStack, slotIndex));
            }
        }
    }

    private boolean isSoup(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemSoup;
    }

    public void onEnable() {
        this.usePhase = AutoHealUsePhase.SELECT_SLOT;
        this.holdingServerSlot = false;
        super.onEnable();
    }

    @Subscribe(mc = MoveEvent.class, priority = SubscriberPriority.HIGH)
    public void onMove(MoveEvent event) {
        if (event.getPhase() == MotionPhase.PRE && this.suppressHorizontalPotionJump) {
            if (event.getY() > 0.0) {
                event.setX(0.0);
                event.setZ(0.0);
                return;
            }

            this.suppressHorizontalPotionJump = false;
        }
    }

    private boolean isInvalidItemStack(ItemStack itemStack) {
        if (itemStack != null && itemStack.stackSize > 0) {
            Item item = itemStack.getItem();
            if (item != null && item != Item.getItemFromBlock(Blocks.air)) {
                int metadata = itemStack.getMetadata();
                return metadata < -32768 || metadata > 65535;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private int scoreMissingPotionEffects(ItemStack itemStack) {
        if (this.isInvalidItemStack(itemStack)) {
            return 0;
        }

        int score = 0;
        if (itemStack.getItem() instanceof ItemPotion && ItemPotion.isSplash(itemStack.getMetadata())) {
            Potion[] potionTypes = Potion.potionTypes;
            for (Object obj : ItemPotion.getPotionEffects(itemStack)) {
                PotionEffect potionEffect = (PotionEffect) obj;
                if (!this.hasEqualOrStrongerEffect(potionEffect)) {
                    if (potionTypes[potionEffect.getPotionID()].isBadEffect()) {
                        score -= potionEffect.getAmplifier();
                    } else {
                        score = potionEffect.getAmplifier();
                    }
                }
            }
        }

        return score;
    }

    private boolean isHeadItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemSkull;
    }
}
