package remix.module.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

import remix.client.ClientServices;
import remix.client.notification.NotificationManager;
import remix.client.notification.NotificationPriority;
import remix.command.ClientCommandManager;
import remix.event.annotation.Subscribe;
import remix.event.impl.CameraRotationEvent;
import remix.event.impl.CameraTransformEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.render.resource.MotionBlurResourceManager;
import remix.setting.Setting;

import shadersmod.client.Shaders;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/** Camera module: render events and 20 settings. */
public final class CameraModule extends ModuleBase {

    public Setting bobbingXSetting;

    public Setting nbtViewerSetting;
    public float lockedPitch;
    public float lockedYaw;
    public ResourceLocation capeTexture;
    public Setting lockF5Setting;

    public Setting bobbingSetting;
    private float targetLeanAngle;
    public Setting capeSetting;
    public Setting noHurtCamSetting;
    public Setting scoreboardOffsetSetting;
    public Setting selectionBoxSetting;
    public Setting noLimitSetting;
    public Setting viewClipSetting;
    public Setting noScoreboardSetting;
    public Setting bobbingYSetting;
    private float smoothedLeanAngle;
    public Setting noFovSetting;
    private Map resourceDomains;
    public Setting blurAmountSetting;
    public Setting noHandSetting;
    public Setting noBlindnessSetting;
    private Setting leanSetting;
    private boolean cameraLocked;
    private ShaderGroup updatedMotionBlurGroup;
    private float updatedBlurRetention = Float.NaN;
    private long previousBlurFrameNanos;
    private ShaderUniform[] blurUniforms = new ShaderUniform[0];
    public final Setting<Boolean> adaptiveBlurSetting = new Setting<>("AdaptiveBlur", this, true);
    public final Setting motionBlurSetting = new Setting("MotionBlur", this, false);
    public Setting drugsSetting;
    public Setting noBurnEffectSetting;

    @Override
    public void onEnable() {
        super.onEnable();
        if (minecraft.ingameGUI != null
                && this.motionBlurSetting.asBoolean()
                && minecraft.world != null
                && ClientServices.commands != null
                && (minecraft.gameSettings.ofFastRender || Shaders.shaderPackLoaded)) {
            ClientCommandManager.printMessage(
                    "For motion blur to work you must have fast render off and no shaders!");
            NotificationManager.post(
                    new NotificationManager(
                            new String[] {
                                "Turn off!", "Fast render off and no shaders for motion blur!"
                            },
                            NotificationPriority.HIGH,
                            4000));
        }
    }

    @Subscribe(mc = CameraTransformEvent.class)
    public void onCameraTransform(CameraTransformEvent event) {
        if (this.leanSetting.asBoolean()) {
            GlStateManager.rotate(this.smoothedLeanAngle, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(-this.smoothedLeanAngle / 40.0F, 0.0F, 0.0F);
        }
    }

    @Subscribe(mc = CameraRotationEvent.class)
    public void onCameraRotation(CameraRotationEvent event) {
        if (this.lockF5Setting.asBoolean()) {
            if (minecraft.gameSettings.thirdPersonView > 0) {
                if (!this.cameraLocked) {
                    this.lockedYaw = event.yaw;
                    this.lockedPitch = event.pitch;
                    this.cameraLocked = true;
                }

                event.yaw = this.lockedYaw;
                event.pitch = this.lockedPitch;
            } else {
                this.cameraLocked = false;
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.stopMotionBlur();
        if (this.cameraLocked) {
            if (minecraft.player != null) {
                minecraft.player.rotationYaw = this.lockedYaw + 180.0F;
                minecraft.player.rotationPitch = this.lockedPitch;
            }
            this.cameraLocked = false;
        }
    }

    public CameraModule() {
        super("Camera", new ModuleDefaults(ModuleCategory.RENDER).visible(false));
        this.blurAmountSetting = new Setting("BlurAmount", this, 2.0, 0.0, 7.0, 1.0);
        this.viewClipSetting = new Setting("ViewClip", this, true);
        this.leanSetting = new Setting("Lean", this, false);
        this.lockF5Setting = new Setting("LockF5", this, false);
        this.drugsSetting = new Setting("Drugs", this, false);
        this.selectionBoxSetting = new Setting("SelectionBox", this, true);
        this.bobbingSetting = new Setting("Bobbing", this, false);
        this.bobbingXSetting = new Setting("BobbingX", this, 4.0, 4.0, 12.0, 1.0);
        this.bobbingYSetting = new Setting("BobbingY", this, 4.0, 4.0, 12.0, 1.0);
        this.noScoreboardSetting = new Setting("NoScoreboard", this, false);
        this.scoreboardOffsetSetting =
                new Setting("Scoreboard Offset", this, 0.0, -200.0, 200.0, 1.0);
        this.noBurnEffectSetting = new Setting("NoBurnEffect", this, true);
        this.noBlindnessSetting = new Setting("NoBlindness", this, false);
        this.noLimitSetting = new Setting("NoLimit", this, false);
        this.noHurtCamSetting = new Setting("NoHurtCam", this, false);
        this.noHandSetting = new Setting("NoHand", this, false);
        this.noFovSetting = new Setting("NoFov", this, false);
        this.capeSetting = new Setting("Cape", this, false);
        this.nbtViewerSetting = new Setting("NBTViewer", this, false);
        this.capeTexture = new ResourceLocation("remix/cape.png");
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (minecraft.player == null || minecraft.world == null) {
            this.stopMotionBlur();
            return;
        }
        if (this.noBlindnessSetting.asBoolean()
                && minecraft.player.isPotionActive(Potion.BLINDNESS)) {
            minecraft.player.removePotionEffect(Potion.BLINDNESS.getId());
        }

        if (this.leanSetting.asBoolean()) {
            boolean left = Keyboard.isKeyDown(203);
            boolean right = Keyboard.isKeyDown(205);
            if (left) {
                if (this.targetLeanAngle > -30.0F) {
                    this.targetLeanAngle -= 3.0F;
                }
            } else if (right) {
                if (this.targetLeanAngle < 30.0F) {
                    this.targetLeanAngle += 3.0F;
                }
            } else {
                if (this.targetLeanAngle > 0.0F) {
                    this.targetLeanAngle -= 3.0F;
                } else if (this.targetLeanAngle < 0.0F) {
                    this.targetLeanAngle += 3.0F;
                }
            }

            this.smoothedLeanAngle += (this.targetLeanAngle - this.smoothedLeanAngle) / 10.0F;
        }

        if (this.motionBlurSetting.asBoolean()
                && !minecraft.gameSettings.ofFastRender
                && !Shaders.shaderPackLoaded) {
            // Resource reload clears the domain map. Register the generated JSON before loading it.
            this.ensureMotionBlurResources();
            if (!minecraft.entityRenderer.isShaderActive()
                    && minecraft.world != null
                    && !minecraft.gameSettings.ofFastRender) {
                minecraft.entityRenderer.loadShader(
                        new ResourceLocation("motionblur", "motionblur"));
            }
            return;
        }

        this.stopMotionBlur();
    }

    /** Invoked immediately before this group's render pass; independent of game Timer/tick speed. */
    public void prepareMotionBlurFrame(ShaderGroup group, long frameNanos) {
        if (group == null || !"motionblur:motionblur".equals(group.getShaderGroupName())) return;
        if (group != this.updatedMotionBlurGroup) {
            List<ShaderUniform> uniforms = new ArrayList<>();
            for (Shader shader : group.getShaders()) {
                ShaderUniform uniform = shader.getShaderManager().ar("Phosphor");
                if (uniform != null) uniforms.add(uniform);
            }
            this.blurUniforms = uniforms.toArray(new ShaderUniform[uniforms.size()]);
            this.updatedMotionBlurGroup = group;
            this.updatedBlurRetention = Float.NaN;
            this.previousBlurFrameNanos = 0L;
        }
        long elapsed = this.previousBlurFrameNanos == 0L ? 0L : frameNanos - this.previousBlurFrameNanos;
        this.previousBlurFrameNanos = frameNanos;
        float retention = this.adaptiveBlurSetting.asBoolean()
                ? motionBlurRetention(this.blurAmountSetting.asDouble(), elapsed)
                : motionBlurRetention(this.blurAmountSetting.asDouble(), 0L);
        if (retention == this.updatedBlurRetention) return;
        for (ShaderUniform uniform : this.blurUniforms) uniform.set(retention, retention, retention);
        this.updatedBlurRetention = retention;
    }

    /** Preserve the amount's appearance at 120 FPS, and discard history after a 100 ms stall. */
    public static float motionBlurRetention(double amount, long elapsedNanos) {
        double retention = 0.7 + amount / 100.0 * 3.0 - 0.01;
        if (elapsedNanos >= 100_000_000L) return 0.0F;
        return (float) (elapsedNanos <= 0L ? retention
                : Math.pow(retention, elapsedNanos * 120.0 / 1_000_000_000.0));
    }

    private void ensureMotionBlurResources() {
        this.resourceDomains =
                ((SimpleReloadableResourceManager) minecraft.mcResourceManager)
                        .domainResourceManagers;
        if (!this.resourceDomains.containsKey("motionblur")) {
            this.resourceDomains.put("motionblur", new MotionBlurResourceManager(this));
        }
    }

    private void stopMotionBlur() {
        if (minecraft.entityRenderer != null
                && minecraft.entityRenderer.theShaderGroup != null
                && minecraft
                        .entityRenderer
                        .theShaderGroup
                        .getShaderGroupName()
                        .equals("motionblur:motionblur")) {
            minecraft.entityRenderer.stopUseShader();
        }
        this.updatedMotionBlurGroup = null;
        this.updatedBlurRetention = Float.NaN;
        this.previousBlurFrameNanos = 0L;
        if (this.blurUniforms.length != 0) this.blurUniforms = new ShaderUniform[0];
    }
}
