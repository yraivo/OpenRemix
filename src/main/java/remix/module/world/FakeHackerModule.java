package remix.module.world;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import remix.module.world.FakeHackerTypeMode;
import remix.event.InputType;
import remix.event.MotionPhase;
import remix.event.impl.PacketEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.command.ClientCommandManager;
import remix.event.MotionEvent;
import remix.input.InputEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;

/** FakeHacker module: world events and 5 settings. */
public final class FakeHackerModule extends ModuleBase {
    private float[] savedTargetRotations;
    public String targetLabel;
    private Setting knockbackSetting;
    private Setting damageSetting;

    private static String[] packedTextFragments;
    public PlayerEntity previousTarget;
    private final Setting<FakeHackerTypeMode> typeSetting =
            new Setting("Type", this, FakeHackerTypeMode.HEADLESS);

    private Deque<S14PacketEntity> bufferedEntityUpdates;
    private boolean releaseEntityUpdates;
    private Setting attackMeOnlySetting;
    public PlayerEntity selectedTarget;
    private final Setting instructionsSetting = new Setting("Instructions", this, true);

    private void faceEntity(
            LivingEntity sourceEntity,
            LivingEntity targetEntity,
            float maxYawChange,
            float maxPitchChange) {
        double deltaPosX = targetEntity.posX - sourceEntity.posX;
        double deltaPosZ = targetEntity.posZ - sourceEntity.posZ;
        double d2 =
                targetEntity.posY
                        + targetEntity.getEyeHeight()
                        - (sourceEntity.posY + sourceEntity.getEyeHeight());
        double d3 = MathHelper.sqrt(deltaPosX * deltaPosX + deltaPosZ * deltaPosZ);
        float f2 = (float) (Math.atan2(deltaPosZ, deltaPosX) * 180.0 / Math.PI) - 90.0F;
        float beO = this.approachAngle(sourceEntity.rotationYaw, f2, maxYawChange);
        float f4 = (float) (-(Math.atan2(d2, d3) * 180.0 / Math.PI));
        sourceEntity.rotationYaw = beO;
        sourceEntity.rotationYawHead = beO;
        sourceEntity.rotationPitch =
                this.approachAngle(sourceEntity.rotationPitch, f4, maxPitchChange);
    }

    @Subscribe(mc = PacketEvent.class)
    private void onPacket(PacketEvent event) {
        Packet packet = event.getPacket();
        if (this.selectedTarget != null) {
            FakeHackerTypeMode type = (FakeHackerTypeMode) this.typeSetting.getValue();
            if (packet instanceof S14PacketEntity) {
                S14PacketEntity packetValue = (S14PacketEntity) event.getPacket();
                if (packetValue.getEntityId() == this.selectedTarget.getEntityId()) {
                    if (type == FakeHackerTypeMode.HEADLESS) {
                        this.savedTargetRotations = new float[] {packetValue.yaw, packetValue.pitch};
                    } else if (type == FakeHackerTypeMode.BLINK) {
                        if (!this.releaseEntityUpdates) {
                            this.bufferedEntityUpdates.add(packetValue);
                            event.cancel();
                            return;
                        }
                        this.restoreTargetState(this.selectedTarget);
                        this.releaseEntityUpdates = false;
                    }
                }
            }

            if (packet instanceof S0BPacketAnimation) {
                S0BPacketAnimation packetValue2 = (S0BPacketAnimation) event.getPacket();
                if (type == FakeHackerTypeMode.NO_SWING
                        && packetValue2.getEntityID() == this.selectedTarget.getEntityId()) {
                    event.cancel();
                }
            }
        }
    }

    public String getSuffix() {
        return this.targetLabel;
    }

    public void selectTarget(PlayerEntity target) {
        this.previousTarget = this.selectedTarget;
        if (this.previousTarget != null) {
            this.restoreTargetState(this.previousTarget);
        }

        this.selectedTarget = target;
    }

    private float approachAngle(float current, float target, float maxChange) {
        float f3 = MathHelper.wrapAngleTo180(target - current);
        if (f3 > maxChange) {
            f3 = maxChange;
        }

        if (f3 < -maxChange) {
            f3 = -maxChange;
        }

        return current + f3;
    }

    // region Static initialization and string tables
    static {
        remix.module.world.FakeHackerModule.packedTextFragments = new String[3];
        remix.module.world.FakeHackerModule.packedTextFragments[0] = new String("\"");
    }

    // endregion

    public void onEnable() {
        super.onEnable();
        this.releaseEntityUpdates = false;
        this.targetLabel = "Searching...";
        this.bufferedEntityUpdates.clear();
        GuiNewChat chatGUI = minecraft.ingameGUI.getChatGUI();
        if (chatGUI != null
                && ClientServices.commands != null
                && this.instructionsSetting.asBoolean()) {
            ClientCommandManager.printMessage(
                    "To select your target either use .fh comamnd or right click over them");
        }
    }

    public void onDisable() {
        super.onDisable();
        this.releaseEntityUpdates = true;
        this.targetLabel = "Searching...";
        if (this.selectedTarget != null) {
            this.restoreTargetState(this.selectedTarget);
            this.selectedTarget = null;
        }
    }

    @Subscribe(mc = InputEvent.class)
    public void onInput(InputEvent event) {
        InputType type = event.getType();
        if (type == InputType.RIGHT_CLICK) {
            RayTraceResult objectMouseOver = minecraft.objectMouseOver;
            Entity entityHit = objectMouseOver.entityHit;
            if (objectMouseOver.typeOfHit == RayHitType.ENTITY
                    && entityHit instanceof PlayerEntity) {
                if (this.selectedTarget == null) {
                    this.selectTarget((PlayerEntity) entityHit);
                    if (this.instructionsSetting.asBoolean()) {
                        ClientCommandManager.printMessage(
                                "Target set to: "
                                        + this.selectedTarget.getName()
                                        + ". To unselect, click the player again");
                    }
                    return;
                }

                if (this.selectedTarget == entityHit) {
                    this.selectedTarget = null;
                    if (this.instructionsSetting.asBoolean()) {
                        ClientCommandManager.printMessage("Target unselected");
                    }
                    return;
                }

                this.selectTarget((PlayerEntity) entityHit);
                if (this.instructionsSetting.asBoolean()) {
                    ClientCommandManager.printMessage(
                            "Target set from \""
                                    + this.previousTarget.getName()
                                    + "\" to \""
                                    + this.selectedTarget.getName()
                                    + packedTextFragments[0]);
                }
            }
        }
    }

    public FakeHackerModule() {
        super("FakeHacker", ModuleDefaults.ofCategory(ModuleCategory.WORLD));
        this.damageSetting = new Setting("Damage", this, false);
        this.knockbackSetting = new Setting("Knockback", this, false);
        this.attackMeOnlySetting = new Setting("AttackMeOnly", this, false);
        this.bufferedEntityUpdates = new ArrayDeque<>();
    }

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {
        if (this.selectedTarget != null && event.getPhase() == MotionPhase.PRE) {
            this.targetLabel = this.selectedTarget.getName();
            FakeHackerTypeMode type = (FakeHackerTypeMode) this.typeSetting.getValue();

            if (type == FakeHackerTypeMode.SNEAK) {
                this.selectedTarget.setSneaking(true);
            } else if (type == FakeHackerTypeMode.BLINK) {
                if (!this.releaseEntityUpdates && this.timer.hasElapsed(500L)) {
                    this.releaseEntityUpdates = true;
                    this.timer.reset();
                }
            } else if (type == FakeHackerTypeMode.HEADLESS) {
                this.selectedTarget.rotationPitch = -180.0F;
            } else if (type == FakeHackerTypeMode.KILL_AURA) {
                LivingEntity attackTarget = minecraft.player;
                if (!this.attackMeOnlySetting.asBoolean()) {
                    ArrayList<LivingEntity> candidates = new ArrayList<>();
                    for (Object obj : minecraft.world.loadedEntities) {
                        Entity entity = (Entity) obj;
                        if (entity instanceof LivingEntity) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            if (livingEntity instanceof PlayerEntity
                                    && livingEntity != this.selectedTarget
                                    && !livingEntity.isDead
                                    && livingEntity != minecraft.player
                                    && livingEntity.getDistanceToEntity(this.selectedTarget) < 4.5) {
                                candidates.add(livingEntity);
                            }
                        }
                    }

                    candidates.sort(Comparator.comparingDouble(
                            target -> target.getDistanceToEntity(this.selectedTarget)));
                    attackTarget = candidates.isEmpty() ? null : candidates.get(0);
                }

                if (attackTarget != null && attackTarget.getDistanceToEntity(this.selectedTarget) < 4.5) {
                    this.faceEntity(this.selectedTarget, attackTarget, 10.0F, 10.0F);
                    this.selectedTarget.swingItem();

                    if (!minecraft.playerController.isInCreativeMode()
                            && this.damageSetting.asBoolean()
                            && attackTarget instanceof LocalPlayer) {
                        if (this.timer.hasElapsed(500L)) {
                            float health = minecraft.player.getHealth();
                            if (Math.round(health) > 2) {
                                if (this.knockbackSetting.asBoolean()) {
                                    minecraft.player.addVelocity(
                                            -MathHelper.sin(
                                                            this.selectedTarget.rotationYaw
                                                                    * (float) Math.PI
                                                                    / 180.0F)
                                                    * 0.5F,
                                            0.1,
                                            MathHelper.cos(
                                                            this.selectedTarget.rotationYaw
                                                                    * (float) Math.PI
                                                                    / 180.0F)
                                                    * 0.5F);
                                }

                                minecraft.player.performHurtAnimation();
                                minecraft.player.setHealth(minecraft.player.getHealth() - 1.0F);
                            }

                            this.timer.reset();
                        }
                    }
                }
            }
        }
    }

    private void restoreTargetState(PlayerEntity target) {
        FakeHackerTypeMode type = (FakeHackerTypeMode) this.typeSetting.getValue();
        if (type == FakeHackerTypeMode.BLINK) {
            while (!this.bufferedEntityUpdates.isEmpty()) {
                minecraft.world.sendQueue.handleEntityMovement(this.bufferedEntityUpdates.poll());
            }
        } else if (type == FakeHackerTypeMode.HEADLESS) {
            if (this.savedTargetRotations != null) {
                target.rotationYaw = this.savedTargetRotations[0];
                target.rotationYawHead = this.savedTargetRotations[0];
                target.rotationPitch = this.savedTargetRotations[1];
            }
        } else if (type == FakeHackerTypeMode.SNEAK) {
            target.setSneaking(false);
        }
    }
}
