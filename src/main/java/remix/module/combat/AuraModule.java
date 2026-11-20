package remix.module.combat;

import net.minecraft.block.material.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.client.DiggingAction;
import net.minecraft.network.play.client.DiggingPacket;
import net.minecraft.network.play.client.HeldItemChangePacket;
import net.minecraft.network.play.client.UseEntityAction;
import net.minecraft.network.play.client.UseEntityPacket;
import net.minecraft.network.play.client.UseItemPacket;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;

import optifine.MathUtils;

import remix.client.ClientServices;
import remix.command.TargetsClientCommand;
import remix.event.InputType;
import remix.event.MotionEvent;
import remix.event.MotionPhase;
import remix.event.PacketDirection;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.event.impl.PacketEvent;
import remix.input.InputEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.world.AntiBotModule;
import remix.setting.Setting;
import remix.util.entity.RaycastHelper;
import remix.util.entity.RotationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Selects targets and runs attack/block transitions across PRE/POST motion, input and packet
 * events. The public unresolvedStateFlag has no use in this class; its original purpose is unknown.
 */
public final class AuraModule extends ModuleBase {

    private Setting targetNeutralEntities;

    private Setting switchHits;

    private Setting targetAnimals;

    private Setting randomizeAttackRate;

    private Setting attacksPerSecond;

    private Setting<AuraTargetSort> targetSorting;

    public LivingEntity currentTarget;

    private float[] rotations;

    public boolean blocking;

    private Setting checkTeams;

    private Setting checkWalls;

    private Setting useRayTrace;

    private Setting aimHeightPercent;

    private Setting rotationSmoothness;

    private Setting<AuraRotationMode> rotationMode;

    private Setting blockRange;

    private float apsNoise;

    private List<LivingEntity> targets;

    private List<LivingEntity> visitedTargets;

    private Setting<AuraBlockMode> blockMode;

    public boolean unresolvedStateFlag;

    public Setting disableOnWorldChange;

    private Setting attackRange;

    private Setting blockManualInput;

    private Setting predictTargetMotion;

    private Setting hitChancePercent;

    private Setting targetMobs;

    private Setting maxTargetAngle;

    private Setting showHitParticles;

    public Setting hitboxOffset;

    private Setting targetPlayers;

    private Setting autoBlock;

    private DiggingPacket releaseUseItemPacket;

    private int switchAttackCount;

    public Setting<AuraAttackPhase> attackPhase;

    public int attackTimerTicks;

    private Setting disableOnDeath;

    private Setting targetInvisibles;

    private Setting<AuraTargetMode> attackMode;

    private Setting stopSprintOnHit;

    public String getSuffix() {

        return ((AuraTargetMode) this.attackMode.getValue()).getDisplayName();
    }

    @Subscribe(mc = InputEvent.class)
    public void onInput(InputEvent event) {

        InputType inputType = event.getType();

        if (this.currentTarget != null
                && this.blockManualInput.asBoolean()
                && (inputType == InputType.LEFT_CLICK || inputType == InputType.RIGHT_CLICK)) {

            event.cancel();
        }
    }

    private List<LivingEntity> collectTargets(
            double range, double maxAngle, boolean excludeVisited) {

        List<LivingEntity> candidates = new ArrayList<>();

        List loadedEntities = minecraft.world.loadedEntities;

        for (int i = 0; i < loadedEntities.size(); i++) {

            Entity entity = (Entity) loadedEntities.get(i);

            if (entity instanceof LivingEntity) {

                LivingEntity livingEntity = (LivingEntity) entity;

                if (this.isValidTarget(livingEntity, range, maxAngle, excludeVisited)) {

                    candidates.add(livingEntity);
                }
            }
        }

        return candidates;
    }

    public void onDisable() {

        super.onDisable();

        minecraft.getItemRenderer().forceBlockAnimation = false;

        if (this.blocking
                && minecraft.player != null
                && this.isHoldingSword(minecraft.player.getCurrentEquippedItem())) {

            this.sendPacket(this.releaseUseItemPacket);

            this.blocking = false;
        }

        this.currentTarget = null;
    }

    private boolean isAttackReady() {

        CriticalsModule criticals = ClientServices.moduleManager.criticals;

        AutoHealModule autoHeal = ClientServices.moduleManager.autoHeal;

        long effectiveAps =
                (long)
                        Math.max(
                                this.attacksPerSecond.asDouble()
                                        + (this.randomizeAttackRate.asBoolean()
                                                ? this.apsNoise
                                                : 0.0F),
                                1.0);

        PlayerInfo localPlayerInfo =
                minecraft.getNetworkHandler().getPlayerInfo(minecraft.player.getUniqueId());

        boolean criticalTimingAllowed =
                !criticals.isEnabled() || criticals.isAuraAttackTimingAllowed();

        AuraTargetMode mode = (AuraTargetMode) this.attackMode.getValue();

        if (mode == AuraTargetMode.TICK) {

            int pingTicks = localPlayerInfo != null ? localPlayerInfo.getResponseTime() / 50 : 0;

            return this.attackTimerTicks >= 2
                    && this.currentTarget.hurtResistantTime <= 14.0 - pingTicks;

        } else {

            long elapsedMillis = this.attackTimerTicks * 50L;

            boolean blockAllowsAttack =
                    !this.blocking
                            || this.blockMode.getValue() == AuraBlockMode.OLD
                            || this.blockMode.getValue() == AuraBlockMode.VANILLA
                            || this.blockMode.getValue() == AuraBlockMode.MMC;

            return elapsedMillis > 1000L / effectiveAps
                    && criticalTimingAllowed
                    && autoHeal.selectedHealingItem == null
                    && blockAllowsAttack;
        }
    }

    public boolean isValidTarget(
            LivingEntity target, double range, double maxAngle, boolean excludeVisited) {

        if (target == null) {

            return false;
        }

        LocalPlayer player = minecraft.player;

        AntiBotModule antiBot = ClientServices.moduleManager.antiBot;

        boolean isPlayer = target instanceof PlayerEntity;

        boolean typeAllowed =
                (isPlayer && this.targetPlayers.asBoolean())
                        || (target instanceof AnimalEntity && this.targetAnimals.asBoolean())
                        || ((target instanceof MonsterEntity
                                        || target instanceof EntityGolem
                                        || target instanceof SlimeEntity
                                        || target instanceof EntityDragon)
                                && this.targetMobs.asBoolean())
                        || ((target instanceof BatEntity
                                        || target instanceof SquidEntity
                                        || target instanceof EntityVillager)
                                && this.targetNeutralEntities.asBoolean());

        boolean baseEligible =
                target != player
                        && !target.isDead
                        && (target.getHealth() != 0.0F || Float.isNaN(target.getHealth()))
                        && (!target.isInvisible() || this.targetInvisibles.asBoolean())
                        && typeAllowed
                        && (!antiBot.isEnabled() || !antiBot.isBot(target))
                        && (!isPlayer || !ClientServices.friendManager.isFriend(target.getName()))
                        && (!excludeVisited || !this.visitedTargets.contains(target))
                        && (!RaycastHelper.isSameColoredTeam(target)
                                || !this.checkTeams.asBoolean())
                        && !Objects.equals(target.getName(), player.getName())
                        && !target.getUniqueId().toString().equals(target.getName())
                        && !target.getUniqueId().equals(player.getUniqueId())
                        && target.getEntityId() != player.getEntityId()
                        && RotationHelper.angleDifference(
                                        this.rotations[0], RotationHelper.yawToEntity(target))
                                <= maxAngle;

        if (!baseEligible) {

            return false;
        }

        BoundingBox bounds = target.getEntityBoundingBox();

        Vec3 aimPoint =
                new Vec3(
                        (bounds.minX + bounds.maxX) / 2.0,
                        bounds.minY
                                + (bounds.maxY - bounds.minY)
                                        / 100.0
                                        * this.aimHeightPercent.asDouble(),
                        (bounds.minZ + bounds.maxZ) / 2.0);

        float[] aimRotations = RotationHelper.rotationsTo(aimPoint, this.rotations, 0.0F);

        boolean rayHits = RaycastHelper.isEntityInRay(aimRotations, range, target, 1.0F);

        if (this.checkWalls.asBoolean()) {

            RayTraceResult wallRayHit = RaycastHelper.rayTrace(aimRotations, range, false, 1.0F);

            return wallRayHit != null && wallRayHit.typeOfHit == RayHitType.ENTITY && rayHits;
        }

        return rayHits;
    }

    private void attackTarget(LivingEntity target) {

        LocalPlayer player = minecraft.player;

        boolean criticalParticleAllowed =
                player.fallDistance > 0.0F
                        && !player.isOnLadder()
                        && !player.isInWater()
                        && !player.isPotionActive(Potion.BLINDNESS)
                        && player.ridingEntity == null;

        boolean enchantmentParticleAllowed =
                EnchantmentHelper.getEnchantmentDamageBonus(
                                player.getCurrentEquippedItem(), target.getCreatureAttribute())
                        > 0.0F;

        if (this.useRayTrace.asBoolean()) {

            RayTraceResult rayHit =
                    RaycastHelper.rayTrace(
                            this.rotations,
                            this.attackRange.asDouble(),
                            !this.checkWalls.asBoolean(),
                            1.0F);

            if (rayHit == null) {

                player.swingItem();

                this.attackTimerTicks = 0;

                return;
            }

            if (!(rayHit.entityHit instanceof LivingEntity)) {

                if (rayHit.typeOfHit == RayHitType.BLOCK) {

                    player.swingItem();

                    BlockPos hitBlockPosition = rayHit.getBlockPos();

                    if (minecraft.world.getBlockState(hitBlockPosition).getBlock().getMaterial()
                            != Material.AIR) {

                        minecraft.playerController.clickBlock(hitBlockPosition, rayHit.sideHit);
                    }

                    this.attackTimerTicks = 0;

                } else {

                    player.swingItem();

                    this.attackTimerTicks = 0;
                }

                return;
            }

            target = (LivingEntity) rayHit.entityHit;
        }

        if (this.showHitParticles.asBoolean()) {

            if (criticalParticleAllowed) {

                player.onCriticalHit(target);
            }

            if (enchantmentParticleAllowed) {

                player.onEnchantmentCritical(target);
            }
        }

        if (MathUtils.random.nextDouble() <= this.hitChancePercent.asDouble() / 100.0) {

            if (this.blocking && this.blockMode.getValue() == AuraBlockMode.OLD) {

                this.sendPacket(this.releaseUseItemPacket);
            }

            if (this.blockMode.getValue() == AuraBlockMode.MMC) {

                this.blocking = false;
            }

            if (this.attackMode.getValue() == AuraTargetMode.MULTI) {

                for (LivingEntity multiTarget : this.targets) {

                    player.swingItem();

                    this.sendPacket(new UseEntityPacket(multiTarget, UseEntityAction.ATTACK));
                }

            } else {

                player.swingItem();

                this.sendPacket(new UseEntityPacket(target, UseEntityAction.ATTACK));
            }

            if (this.stopSprintOnHit.asBoolean()) {

                int knockbackStrength = EnchantmentHelper.getKnockbackModifier(player);

                if (player.isSprinting()) {

                    knockbackStrength++;
                }

                if (knockbackStrength > 0) {

                    player.motionX *= 0.6;

                    player.motionZ *= 0.6;

                    player.setSprinting(false);
                }
            }

            if (this.attackMode.getValue() == AuraTargetMode.SWITCH) {

                this.switchAttackCount++;
            }

        } else {

            player.swingItem();
        }

        this.apsNoise = MathUtils.getRandomFloat(-2.5F, 2.5F);

        this.attackTimerTicks = 0;
    }

    private boolean isHoldingSword(ItemStack itemStack) {

        return itemStack != null && itemStack.getItem() instanceof SwordItem;
    }

    public static MinecraftClient getMinecraft() {

        return minecraft;
    }

    public AuraModule() {
        super("Aura", ModuleDefaults.ofCategory(ModuleCategory.COMBAT));

        this.attackMode = new Setting("Mode", this, AuraTargetMode.SWITCH);

        this.attackPhase = new Setting("Event", this, AuraAttackPhase.POST);

        this.targetSorting = new Setting("Sorting", this, AuraTargetSort.ANGLE);

        this.blockMode = new Setting("Block", this, AuraBlockMode.NCP);

        this.rotationMode = new Setting("Rotation", this, AuraRotationMode.SILENT);

        this.attacksPerSecond = new Setting("APS", this, 13.0, 1.0, 20.0, 1.0);

        this.attackRange = new Setting("Range", this, 4.25, 1.0, 6.0, 0.05);

        this.blockRange = new Setting("BlockRange", this, 6.0, 1.0, 8.0, 0.05);

        this.switchHits = new Setting("SwitchHits", this, 3.0, 1.0, 20.0, 1.0);

        this.hitChancePercent = new Setting("HitRate", this, 100.0, 0.0, 100.0, 1.0);

        this.rotationSmoothness = new Setting("Smoothness", this, 0.0, 0.0, 100.0, 1.0);

        this.maxTargetAngle = new Setting("Angle", this, 180.0, 0.0, 180.0, 1.0);

        this.aimHeightPercent = new Setting("AimY", this, 50.0, 0.0, 100.0, 1.0);

        this.hitboxOffset = new Setting("HitboxOffset", this, 0.35, 0.0, 1.0, 0.1);

        this.targetPlayers = new Setting("Players", this, true);

        this.targetMobs = new Setting("Mobs", this, false);

        this.targetAnimals = new Setting("Animals", this, false);

        this.targetNeutralEntities = new Setting("Neutral", this, false);

        this.checkTeams = new Setting("Teams", this, false);

        this.targetInvisibles = new Setting("Invisibles", this, false);

        this.autoBlock = new Setting("AutoBlock", this, true);

        this.disableOnDeath = new Setting("DisableOnDeath", this, false);

        this.disableOnWorldChange = new Setting("DisableOnWorldChange", this, false);

        this.blockManualInput = new Setting("BlockInput", this, true);

        this.stopSprintOnHit = new Setting("StopSprint", this, true);

        this.predictTargetMotion = new Setting("Predict", this, false);

        this.useRayTrace = new Setting("RayTrace", this, false);

        this.checkWalls = new Setting("WallCheck", this, false);

        this.randomizeAttackRate = new Setting("Randomize", this, false);

        this.showHitParticles = new Setting("Particles", this, false);

        this.releaseUseItemPacket =
                new DiggingPacket(DiggingAction.RELEASE_USE_ITEM, BlockPos.ORIGIN, Facing.DOWN);

        this.rotations = new float[2];

        this.targets = new ArrayList<>();

        this.visitedTargets = new ArrayList<>();
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {

        if (event.getDirection() == PacketDirection.SEND) {

            if (event.getPacket() instanceof UseItemPacket) {

                UseItemPacket useItemPacket = (UseItemPacket) event.getPacket();

                if (useItemPacket.getPosition() == BlockPos.ORIGIN) {

                    this.blocking = true;
                }
            }

            if (event.getPacket() instanceof DiggingPacket) {

                DiggingPacket diggingPacket = (DiggingPacket) event.getPacket();

                if (diggingPacket == this.releaseUseItemPacket) {

                    this.blocking = false;
                }
            }

            if (event.getPacket() instanceof HeldItemChangePacket) {

                this.blocking = false;
            }
        }
    }

    @Subscribe(mc = MotionEvent.class, priority = SubscriberPriority.HIGH)
    public void onMotion(MotionEvent event) {

        LocalPlayer player = minecraft.player;

        if (this.disableOnDeath.asBoolean()
                && (player == null
                        || !player.isEntityAlive()
                        || player.isDead
                        || player.getHealth() <= 0.0F)) {

            this.toggle();

            return;
        }

        List blockCandidates = this.collectTargets(this.blockRange.asDouble(), 180.0, false);

        double attackRangeWithOffset = this.attackRange.asDouble() + this.hitboxOffset.asDouble();

        double maxAngle = this.maxTargetAngle.asDouble();

        BlockPos useItemPosition = BlockPos.ORIGIN;

        ItemStack heldItem = player.getCurrentEquippedItem();

        AuraBlockMode blockingMode = (AuraBlockMode) this.blockMode.getValue();

        boolean autoBlockEnabled = this.autoBlock.asBoolean();

        boolean canAutoBlock =
                autoBlockEnabled && this.isHoldingSword(heldItem) && !blockCandidates.isEmpty();

        if (event.getPhase() == MotionPhase.PRE) {

            this.attackTimerTicks++;

            if (!canAutoBlock && this.blocking && blockingMode != AuraBlockMode.FAKE) {

                this.sendPacket(this.releaseUseItemPacket);

                return;
            }

            if (autoBlockEnabled && this.isHoldingSword(heldItem)) {

                if (blockingMode == AuraBlockMode.HYPIXEL || blockingMode == AuraBlockMode.NCP) {

                    if (this.blocking) {

                        this.sendPacket(this.releaseUseItemPacket);
                    }

                } else if (blockingMode == AuraBlockMode.OLD) {

                    if (!this.blocking && canAutoBlock && minecraft.objectMouseOver != null) {

                        this.sendPacket(
                                new UseItemPacket(
                                        useItemPosition, 255, heldItem, 0.0F, 0.0F, 0.0F));
                    }

                } else if (blockingMode == AuraBlockMode.MMC) {

                    if (!this.blocking && canAutoBlock) {

                        this.sendPacket(
                                new UseItemPacket(
                                        useItemPosition, 255, heldItem, 0.0F, 0.0F, 0.0F));
                    }

                } else if (blockingMode == AuraBlockMode.LEGIT) {

                    if (this.blocking) {

                        this.sendPacket(this.releaseUseItemPacket);
                    }

                    if (canAutoBlock) {

                        this.sendPacket(new UseItemPacket(heldItem));
                    }
                }
            }

            if (this.switchAttackCount >= this.switchHits.asDouble()) {

                this.visitedTargets.add(this.currentTarget);

                this.currentTarget = null;

                this.switchAttackCount = 0;
            }

            this.targets =
                    this.collectTargets(
                            attackRangeWithOffset,
                            maxAngle,
                            this.attackMode.getValue() == AuraTargetMode.SWITCH);

            if (this.targets.isEmpty() && !this.visitedTargets.isEmpty()) {

                this.targets = this.collectTargets(attackRangeWithOffset, maxAngle, false);

                this.visitedTargets.clear();
            }

            if (!this.targets.isEmpty()) {

                AuraTargetSort sortMode = (AuraTargetSort) this.targetSorting.getValue();

                ArrayList<LivingEntity> priorityTargets = new ArrayList<>();

                for (LivingEntity candidate : this.targets) {

                    if (TargetsClientCommand.isTarget(candidate)) {

                        priorityTargets.add(candidate);
                    }
                }

                if (priorityTargets.size() == 1) {

                    this.targets = priorityTargets;

                    this.currentTarget = this.targets.get(0);

                } else {

                    if (!priorityTargets.isEmpty()) {

                        this.targets = priorityTargets;
                    }

                    this.targets.sort((livingEntity, livingEntityValue) ->
                            sortMode.compareTargets(
                                    livingEntity, livingEntityValue, this.rotations));

                    if (this.attackMode.getValue() == AuraTargetMode.PRIORITY
                            || !this.isValidTarget(
                                    this.currentTarget, attackRangeWithOffset, maxAngle, false)) {

                        this.currentTarget = this.targets.get(0);
                    }
                }

                BoundingBox targetBounds = this.currentTarget.getEntityBoundingBox();

                Vec3 aimPoint =
                        new Vec3(
                                (targetBounds.minX + targetBounds.maxX) / 2.0,
                                targetBounds.minY
                                        + (targetBounds.maxY - targetBounds.minY)
                                                / 100.0
                                                * this.aimHeightPercent.asDouble(),
                                (targetBounds.minZ + targetBounds.maxZ) / 2.0);

                if (this.predictTargetMotion.asBoolean()) {

                    aimPoint.addVector(
                            this.currentTarget.motionX,
                            this.currentTarget.motionY,
                            this.currentTarget.motionZ);
                }

                int smoothness = (int) this.rotationSmoothness.asDouble();

                float[] baseRotations =
                        this.rotationMode.getValue() != AuraRotationMode.SILENT
                                ? new float[] {player.rotationYaw, player.rotationPitch}
                                : this.rotations;

                float[] nextRotations =
                        RotationHelper.rotationsTo(aimPoint, baseRotations, smoothness);

                AuraRotationMode rotMode = (AuraRotationMode) this.rotationMode.getValue();

                if (rotMode == AuraRotationMode.SILENT) {

                    event.setYaw(nextRotations[0]);

                    event.setPitch(nextRotations[1]);

                } else if (rotMode == AuraRotationMode.LOCK_VIEW) {

                    minecraft.player.rotationYaw = nextRotations[0];

                    minecraft.player.rotationPitch = nextRotations[1];
                }

                if (AuraAttackPhase.getMotionPhase((AuraAttackPhase) this.attackPhase.getValue())
                                == event.getPhase()
                        && this.isAttackReady()) {

                    this.attackTarget(this.currentTarget);
                }

            } else {

                this.currentTarget = null;
            }

            this.rotations = new float[] {event.getYaw(), event.getPitch()};

        } else if (event.getPhase() == MotionPhase.POST) {

            if (AuraAttackPhase.getMotionPhase((AuraAttackPhase) this.attackPhase.getValue())
                            == event.getPhase()
                    && this.isValidTarget(
                            this.currentTarget,
                            attackRangeWithOffset,
                            maxAngle,
                            this.attackMode.getValue() == AuraTargetMode.SWITCH
                                    && !this.targets.isEmpty())
                    && this.isAttackReady()) {

                this.attackTarget(this.currentTarget);
            }

            if (canAutoBlock) {

                if (blockingMode == AuraBlockMode.NCP || blockingMode == AuraBlockMode.OLD) {

                    if (!this.blocking) {

                        this.sendPacket(
                                new UseItemPacket(useItemPosition, -1, null, 0.0F, 0.0F, 0.0F));
                    }

                } else if (blockingMode == AuraBlockMode.HYPIXEL) {

                    if (!this.blocking) {

                        this.sendPacket(
                                new UseItemPacket(useItemPosition, 255, null, 0.0F, 0.0F, 0.0F));
                    }

                } else if (blockingMode == AuraBlockMode.VANILLA) {

                    if (!this.blocking) {

                        this.sendPacket(
                                new UseItemPacket(useItemPosition, -1, heldItem, 0.0F, 0.0F, 0.0F));
                    }
                }
            }

            minecraft.getItemRenderer().forceBlockAnimation =
                    this.blocking || (blockingMode == AuraBlockMode.FAKE && canAutoBlock);
        }
    }
}
