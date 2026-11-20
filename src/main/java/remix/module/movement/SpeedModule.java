package remix.module.movement;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.MathHelper;
import optifine.MathUtils;
import remix.event.impl.JumpEvent;
import remix.event.MotionPhase;
import remix.util.movement.MovementUtils;
import remix.event.impl.PlayerUpdateEvent;
import remix.module.movement.SpeedMode;
import remix.event.impl.StepEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.event.MotionEvent;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.world.ScaffoldModule;
import remix.setting.Setting;

import java.util.LinkedList;
import java.util.List;

/**
 * Speed module: uses mode-specific horizontal acceleration/decay, jump offsets, and timer adjustments.
 * Modes supported: BHOP, LOWHOP, HYPIXEL, OLDPORT, OLDHOP, MINEPLEX, ONGROUND, GUARDIAN, VANILLAHOP, VANILLA, AAC.
 */
public final class SpeedModule extends ModuleBase {

    private boolean groundSpoofPhase;
    private double lastHorizontalDistance;
    private double moveSpeed;
    private Setting vanillaSpeedSetting;

    private boolean wasOnGround;
    private double groundLaunchDistance;

    private final Setting<SpeedMode> modeSetting = new Setting("Mode", this, SpeedMode.BHOP);
    private int boostElapsedTicks;

    public SpeedModule() {
        super("Speed", new ModuleDefaults(ModuleCategory.MOVEMENT).keyBind(48));
        this.vanillaSpeedSetting = new Setting("VanillaSpeed", this, 5.0, 0.0, 9.9, 0.1);
    }

    @Override
    public void onEnable() {
        if (minecraft.player != null) {
            this.moveSpeed =
                    this.lastHorizontalDistance =
                            this.groundLaunchDistance = MovementUtils.getSpeedWithPotion();
        }

        this.groundSpoofPhase = false;
        this.wasOnGround = false;
        this.boostElapsedTicks = 9;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        minecraft.timer.timerSpeed = 1.0F;
        super.onDisable();
    }

    @Override
    public String getSuffix() {
        return ((SpeedMode) this.modeSetting.getValue()).getDisplayName();
    }

    @Subscribe(mc = StepEvent.class)
    public void onStep(StepEvent event) {
        if (event.getPhase() == MotionPhase.PRE) {
            SpeedMode mode = (SpeedMode) this.modeSetting.getValue();
            if (mode == SpeedMode.ONGROUND) {
                event.setCancelled(true);
            }
            return;
        }

        this.boostElapsedTicks += 5;
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        LocalPlayer player = minecraft.player;
        double deltaX = player.posX - player.prevPosX;
        double deltaZ = player.posZ - player.prevPosZ;
        this.lastHorizontalDistance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
    }

    @Subscribe(mc = JumpEvent.class)
    public void onJump(JumpEvent event) {
        SpeedMode mode = (SpeedMode) this.modeSetting.getValue();
        if (mode != SpeedMode.VANILLA && mode != SpeedMode.VANILLAHOP && mode != SpeedMode.AAC) {
            event.setCancelled(true);
        }
    }

    @Subscribe(mc = MoveEvent.class)
    public void onMove(MoveEvent event) {
        if (event.getPhase() != MotionPhase.PRE) {
            return;
        }

        LocalPlayer player = minecraft.player;
        boolean onGround = player.onGround;
        ScaffoldModule scaffold = ClientServices.moduleManager.scaffold;
        double groundBoostMultiplier = MathUtils.getRandomFloat(2.145F, 2.149F);
        double frictionDecayOffset = MathUtils.getRandomDouble(1.0E-6, 1.0E-5);

        if (MovementUtils.isMoving()) {
            SpeedMode mode = (SpeedMode) this.modeSetting.getValue();
            switch (mode) {
                case BHOP: {
                    if (onGround && this.boostElapsedTicks > 8) {
                        BlockPos blockBelowPos =
                                new BlockPos(
                                        MathHelper.floor(player.posX),
                                        MathHelper.floor(player.getEntityBoundingBox().minY) - 1,
                                        MathHelper.floor(player.posZ));
                        player.motionY =
                                MovementUtils.getJumpMotion()
                                        - MathUtils.getRandomFloat(0.01F, 0.02F);
                        event.setY(player.motionY);
                        this.moveSpeed =
                                Math.min(
                                                this.lastHorizontalDistance,
                                                MovementUtils.getSpeedWithPotion())
                                        * groundBoostMultiplier;
                        if (minecraft.world.getBlockState(blockBelowPos).getBlock().slipperiness
                                == 0.98F) {
                            this.moveSpeed *= 2.5;
                        }
                        this.boostElapsedTicks = 0;
                    } else if (this.wasOnGround) {
                        this.moveSpeed =
                                this.lastHorizontalDistance
                                        - 0.66
                                                * (this.lastHorizontalDistance
                                                        - MovementUtils.getSpeedWithPotion())
                                        - frictionDecayOffset;
                    } else {
                        LinkedList<Double> candidates = new LinkedList<>();
                        candidates.add(
                                this.lastHorizontalDistance - this.lastHorizontalDistance / 160.0);
                        candidates.add(
                                this.lastHorizontalDistance
                                        - (this.lastHorizontalDistance
                                                        - MovementUtils.getSpeedWithPotion())
                                                / 33.3);
                        candidates.add(
                                this.lastHorizontalDistance
                                        - (this.lastHorizontalDistance
                                                        - MovementUtils.getSpeedWithPotion())
                                                * 0.020000000000000018);
                        candidates.sort(Double::compare);
                        this.moveSpeed = candidates.get(0) - 1.0E-5;
                        this.moveSpeed =
                                Math.max(this.moveSpeed, MovementUtils.getSpeedWithPotion());
                    }
                    MovementUtils.setHorizontalMotion(event, this.moveSpeed);
                    break;
                }

                case LOWHOP:
                    if (this.wasOnGround
                            && !minecraft.gameSettings.keyBindJump.isKeyDown()
                            && !minecraft.player.collidedHorizontally) {
                        event.setY(minecraft.player.motionY = -0.14);
                    }
                    // Falls through into shared HYPIXEL / LOWHOP acceleration logic below:
                case HYPIXEL: {
                    minecraft.timer.timerSpeed = 1.0F;
                    if (onGround
                            && (!scaffold.isEnabled()
                                    || this.boostElapsedTicks > 8
                                    || mode == SpeedMode.LOWHOP)) {
                        if (this.wasOnGround) {
                            BlockPos blockBelowPos =
                                    new BlockPos(
                                            MathHelper.floor(player.posX),
                                            MathHelper.floor(
                                                            player.getEntityBoundingBox().minY)
                                                    - 0.005,
                                            MathHelper.floor(player.posZ));
                            player.motionY =
                                    MovementUtils.getJumpMotion()
                                            - MathUtils.getRandomFloat(0.01F, 0.02F);
                            event.setY(player.motionY);
                            this.moveSpeed = MovementUtils.getSpeedWithPotion() * groundBoostMultiplier;
                            if (minecraft.world.getBlockState(blockBelowPos).getBlock().slipperiness
                                    == 0.98F) {
                                this.moveSpeed *= 2.5;
                            }
                            this.boostElapsedTicks = 0;
                        } else {
                            minecraft.timer.timerSpeed = 2.5F;
                            this.moveSpeed = MovementUtils.getSpeedWithPotion();
                        }
                    } else if (this.wasOnGround) {
                        this.moveSpeed =
                                this.lastHorizontalDistance
                                        - 0.66
                                                * (this.lastHorizontalDistance
                                                        - MovementUtils.getSpeedWithPotion())
                                        - frictionDecayOffset;
                    } else {
                        LinkedList<Double> candidates = new LinkedList<>();
                        candidates.add(
                                this.lastHorizontalDistance - this.lastHorizontalDistance / 160.0);
                        candidates.add(
                                this.lastHorizontalDistance
                                        - (this.lastHorizontalDistance
                                                        - MovementUtils.getSpeedWithPotion())
                                                / 33.3);
                        candidates.add(
                                this.lastHorizontalDistance
                                        - (this.lastHorizontalDistance
                                                        - MovementUtils.getSpeedWithPotion())
                                                * 0.020000000000000018);
                        candidates.sort(Double::compare);
                        this.moveSpeed = candidates.get(0) - 1.0E-5;
                        this.moveSpeed =
                                Math.max(this.moveSpeed, MovementUtils.getSpeedWithPotion());
                    }
                    MovementUtils.setHorizontalMotion(event, this.moveSpeed);
                    break;
                }

                case OLDPORT: {
                    double jumpMotion = MovementUtils.getJumpMotion();
                    if (!onGround && event.getY() > -jumpMotion) {
                        event.setY(player.motionY = -jumpMotion);
                    }
                    // Falls through into shared OLDHOP logic:
                }
                case OLDHOP: {
                    if (onGround) {
                        event.setY(player.motionY = MovementUtils.getJumpMotion() - 0.02F);
                        this.moveSpeed = this.lastHorizontalDistance * 2.145;
                    } else if (this.wasOnGround) {
                        this.moveSpeed =
                                this.lastHorizontalDistance
                                        - 0.66
                                                * (this.lastHorizontalDistance
                                                        - MovementUtils.getSpeedWithPotion())
                                        - 1.0E-5;
                    } else {
                        this.moveSpeed =
                                this.lastHorizontalDistance
                                        - this.lastHorizontalDistance / 160.0
                                        - 1.0E-5;
                        this.moveSpeed =
                                Math.max(this.moveSpeed, MovementUtils.getSpeedWithPotion());
                    }
                    MovementUtils.setHorizontalMotion(event, this.moveSpeed);
                    break;
                }

                case MINEPLEX: {
                    minecraft.timer.timerSpeed = 1.0F;
                    if (onGround) {
                        event.setY(minecraft.player.motionY = MovementUtils.getJumpMotion());
                        minecraft.timer.timerSpeed = 3.0F;
                        this.groundLaunchDistance = this.lastHorizontalDistance;
                        this.moveSpeed = 0.0;
                    } else if (this.wasOnGround) {
                        this.moveSpeed =
                                Math.max(
                                        Math.min(
                                                this.groundLaunchDistance
                                                        + MovementUtils.getJumpMotion(),
                                                this.vanillaSpeedSetting.asDouble()),
                                        this.lastHorizontalDistance * 2.5);
                    } else {
                        this.moveSpeed = this.lastHorizontalDistance * 0.985;
                        this.moveSpeed =
                                Math.max(this.moveSpeed, MovementUtils.getSpeedWithPotion());
                    }

                    if (!onGround) {
                        if (player.fallDistance <= 0.0F) {
                            player.motionY += 0.038;
                            event.setY(player.motionY);
                        } else if (player.fallDistance < 1.2) {
                            player.motionY += 0.032;
                            event.setY(player.motionY);
                        }
                    }
                    MovementUtils.setHorizontalMotion(event, this.moveSpeed);
                    break;
                }

                case ONGROUND: {
                    if (onGround) {
                        if (this.groundSpoofPhase) {
                            this.moveSpeed = this.lastHorizontalDistance * 2.145;
                        } else {
                            this.moveSpeed =
                                    this.lastHorizontalDistance
                                            - 0.66
                                                    * (this.lastHorizontalDistance
                                                            - MovementUtils.getSpeedWithPotion())
                                            - 1.0E-5;
                        }
                        MovementUtils.setHorizontalMotion(event, this.moveSpeed);
                    }
                    break;
                }

                case GUARDIAN: {
                    if (onGround) {
                        event.setY(player.motionY = 0.08F);
                        this.moveSpeed = MovementUtils.getSpeedWithPotion() * 6.0;
                    } else {
                        this.moveSpeed = MovementUtils.getSpeedWithPotion();
                    }
                    MovementUtils.setHorizontalMotion(event, this.moveSpeed);
                    break;
                }

                case VANILLAHOP: {
                    if (player.onGround) {
                        event.setY(player.motionY = MovementUtils.getJumpMotion());
                    }
                    this.moveSpeed = this.vanillaSpeedSetting.asDouble();
                    MovementUtils.setHorizontalMotion(event, this.moveSpeed);
                    break;
                }

                case VANILLA: {
                    this.moveSpeed = this.vanillaSpeedSetting.asDouble();
                    MovementUtils.setHorizontalMotion(event, this.moveSpeed);
                    break;
                }

                case AAC:
                default:
                    break;
            }
        }

        this.wasOnGround = onGround;
        this.boostElapsedTicks++;
    }

    @Subscribe(mc = MotionEvent.class)
    public void onMotion(MotionEvent event) {
        if (event.getPhase() != MotionPhase.PRE) {
            return;
        }

        LocalPlayer player = minecraft.player;
        SpeedMode mode = (SpeedMode) this.modeSetting.getValue();

        switch (mode) {
            case HYPIXEL:
                if (minecraft.player.onGround && this.wasOnGround) {
                    event.onGround = false;
                }
                return;

            case ONGROUND: {
                if (!MovementUtils.isMoving() || !player.onGround) {
                    return;
                }

                if (this.groundSpoofPhase) {
                    double jumpOffset = MovementUtils.getJumpMotion();
                    List collidingBoundingBoxes =
                            minecraft.world.getCollidingBoundingBoxes(
                                    player,
                                    player.getEntityBoundingBox()
                                            .offsetBounds(0.0, 0.0, 0.0, 0.0, jumpOffset, 0.0));
                    if (!collidingBoundingBoxes.isEmpty()) {
                        double lowestMinY = -1.0;
                        for (Object boxObj : collidingBoundingBoxes) {
                            BoundingBox boundingBox = (BoundingBox) boxObj;
                            if (boundingBox.minY < lowestMinY || lowestMinY == -1.0) {
                                lowestMinY = boundingBox.minY;
                            }
                        }
                        jumpOffset = lowestMinY - player.getEntityBoundingBox().maxY;
                    }

                    event.setY(event.getY() + jumpOffset);
                    event.onGround = false;
                }

                this.groundSpoofPhase = !this.groundSpoofPhase;
                return;
            }

            case AAC: {
                if (MovementUtils.isMoving()) {
                    if (player.onGround) {
                        player.jump();
                        return;
                    }
                    player.motionY -= 0.01495;
                }
                return;
            }

            default:
                return;
        }
    }
}
