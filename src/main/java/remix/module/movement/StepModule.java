package remix.module.movement;

import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.util.BoundingBox;
import remix.event.MotionPhase;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.impl.StepEvent;
import remix.module.movement.StepMode;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/** Captures pre-step and target bounds and generates the selected step-offset sequence. MOTION consumes offsets across movement events; other modes send the offsets as packets and restore the timer afterwards. */
public final class StepModule extends ModuleBase {
    private static final double[] NO_OFFSETS = new double[0];
    private static final double[] STEP_1_5 = {0.425, 0.753, 1.001, 1.084, 1.006};
    private static final double[] STEP_2 = {0.425, 0.821, 0.709, 0.599, 1.022, 1.372, 1.652};
    private static final double[] STEP_2_05 = {0.425, 0.821, 0.709, 0.599, 1.022, 1.372, 1.652, 1.869};
    private static final double[] STEP_2_5 = {0.425, 0.821, 0.709, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907};
    private final double[] smallStepOffsets = new double[2];
    private boolean timerRestorePending;
    private final Setting<StepMode> modeSetting = new Setting("Mode", this, StepMode.NCP);
    private BoundingBox preStepBounds;
    private double[] stepOffsets;

    private int groundTicks;
    private Setting delaySetting;

    private Setting timerSetting;
    private final Setting heightSetting = new Setting("Height", this, 1.0, 1.0, 10.0, 0.5);

    private int offsetIndex;
    private BoundingBox targetStepBounds;

    @Subscribe(mc = MoveEvent.class)
    public void onMove(MoveEvent event) {
        if (event.getPhase() == MotionPhase.PRE
                && this.modeSetting.getValue() == StepMode.MOTION
                && this.stepOffsets.length != 0) {
            if (this.offsetIndex != this.stepOffsets.length) {
                double motionY =
                        this.offsetIndex != 0
                                ? this.stepOffsets[this.offsetIndex]
                                        - this.stepOffsets[this.offsetIndex - 1]
                                : this.stepOffsets[this.offsetIndex];
                event.setX(0.0);
                event.setY(minecraft.player.motionY = motionY);
                event.setZ(0.0);
                this.offsetIndex++;

                return;
            }

            event.setY(minecraft.player.motionY = 0.0);
            minecraft.player.setBoundingBox(this.targetStepBounds);
            this.stepOffsets = NO_OFFSETS;
            this.offsetIndex = 0;
        }
    }

    public String getSuffix() {
        return ((StepMode) this.modeSetting.getValue()).getDisplayName();
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (this.modeSetting.getValue() != StepMode.MOTION && this.timerRestorePending) {
            minecraft.timer.timerSpeed = 1.0F;
            this.timerRestorePending = false;
        }

        if (minecraft.player.onGround) {
            this.groundTicks++;

            return;
        }

        this.groundTicks = 0;
    }

    @Subscribe(mc = StepEvent.class)
    public void onStep(StepEvent event) {
        if (event.getPhase() == MotionPhase.PRE) {
            if (!minecraft.gameSettings.keyBindJump.pressed
                    && this.groundTicks > 0
                    && !ClientServices.moduleManager.speed.isEnabled()
                    && super.timer.elapsedAndReset((long) this.delaySetting.asDouble())) {
                event.setHeight((float) this.heightSetting.asDouble());
                if (this.modeSetting.getValue() == StepMode.MOTION) {
                    this.preStepBounds = minecraft.player.getEntityBoundingBox();
                }
            }
        } else if (event.getPhase() == MotionPhase.POST) {
            if (this.modeSetting.getValue() == StepMode.NCP
                    || this.modeSetting.getValue() == StepMode.MOTION) {
                float height = event.getHeight();
                if (!minecraft.gameSettings.keyBindJump.pressed
                        && this.groundTicks > 0
                        && height > 0.6
                        && height <= this.heightSetting.asDouble()
                        && !ClientServices.moduleManager.speed.isEnabled()) {
                    double[] adouble = this.offsetsForHeight(height);

                    if (this.modeSetting.getValue() == StepMode.MOTION) {
                        this.targetStepBounds = minecraft.player.getEntityBoundingBox();
                        minecraft.player.setBoundingBox(this.preStepBounds);
                        event.setCancelled(true);
                        this.stepOffsets = adouble;
                        this.offsetIndex = 0;
                        return;
                    }

                    if (adouble.length != 0) {
                        if (this.timerSetting.asBoolean()) {
                            minecraft.timer.timerSpeed = 1.0F / (adouble.length + 1);
                        }

                        this.timerRestorePending = true;

                        for (double d0 : adouble) {
                            this.sendPacket(
                                    new PlayerPositionPacket(
                                            minecraft.player.posX,
                                            minecraft.player.posY + d0,
                                            minecraft.player.posZ,
                                            false));
                        }
                    }
                }
            }
        }
    }

    private double[] offsetsForHeight(float height) {
        if (height <= 1.0625) {
            this.smallStepOffsets[0] = height * 0.42F;
            this.smallStepOffsets[1] = height * 0.75F;
            return this.smallStepOffsets;
        }
        if (height <= 1.5625) return STEP_1_5;
        if (height < 2.0F) return STEP_2;
        if (height < 2.0625) return STEP_2_05;
        if (height <= 2.5625) return STEP_2_5;
        return NO_OFFSETS;
    }

    public StepModule() {
        super("Step", ModuleDefaults.ofCategory(ModuleCategory.MOVEMENT));
        this.delaySetting = new Setting("Delay", this, 0.0, 0.0, 1000.0, 50.0);
        this.timerSetting = new Setting("Timer", this, true);
        this.stepOffsets = NO_OFFSETS;
    }
}
