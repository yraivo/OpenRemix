package remix.module.player;

import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityActionAction;
import net.minecraft.util.MathHelper;

import optifine.MathUtils;

import remix.client.ClientServices;
import remix.event.MotionEvent;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.render.RotationModule;
import remix.setting.Setting;

/** Derp module: player events and 6 settings. */
public final class DerpModule extends ModuleBase {

    private final Setting<DerpMode> modeSetting = new Setting("Mode", this, DerpMode.RANDOM);

    private Setting skinBlinkSetting;

    private Setting headlessSetting;

    private final Setting speedSetting = new Setting("Speed", this, 5.0, 1.0, 180.0, 1.0);

    private final Setting headSetting = new Setting("Head", this, true);

    private Setting sneakSetting;

    private float spinYaw;

    public DerpModule() {

        super("Derp", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));

        this.skinBlinkSetting = new Setting("SkinBlink", this, true);

        this.headlessSetting = new Setting("Headless", this, true);

        this.sneakSetting = new Setting("Sneak", this, true);
    }

    public void onDisable() {

        super.onDisable();

        this.spinYaw = 0.0F;

        if (this.sneakSetting.asBoolean()) {

            this.sendPacketWithoutEvent(
                    new C0BPacketEntityAction(
                            minecraft.player, C0BPacketEntityActionAction.STOP_SNEAKING));
        }
    }





    @Subscribe(mc = MotionEvent.class, priority = SubscriberPriority.LOWEST)
    public void onMotion(MotionEvent event) {

        RotationModule rotation = ClientServices.moduleManager.rotation;

        switch (event.getPhase()) {
            case PRE:
                if (this.skinBlinkSetting.asBoolean()) {

                    minecraft.gameSettings.setModelPartEnabled(
                            EnumPlayerModelParts.HAT, MathUtils.random.nextBoolean());

                    minecraft.gameSettings.setModelPartEnabled(
                            EnumPlayerModelParts.JACKET, MathUtils.random.nextBoolean());

                    minecraft.gameSettings.setModelPartEnabled(
                            EnumPlayerModelParts.LEFT_PANTS_LEG, MathUtils.random.nextBoolean());

                    minecraft.gameSettings.setModelPartEnabled(
                            EnumPlayerModelParts.RIGHT_PANTS_LEG, MathUtils.random.nextBoolean());

                    minecraft.gameSettings.setModelPartEnabled(
                            EnumPlayerModelParts.LEFT_SLEEVE, MathUtils.random.nextBoolean());

                    minecraft.gameSettings.setModelPartEnabled(
                            EnumPlayerModelParts.RIGHT_SLEEVE, MathUtils.random.nextBoolean());
                }

                if (this.headSetting.asBoolean()) {

                    switch (((DerpMode) this.modeSetting.getValue())) {
                        case RANDOM:
                            event.yaw =
                                    minecraft.player.rotationYaw
                                            + MathHelper.wrapAngleTo180(
                                                    MathUtils.getRandomFloat(-180.0F, 180.0F)
                                                            - minecraft.player.rotationYaw);

                            event.pitch = MathUtils.getRandomFloat(-90.0F, 90.0F);

                            break;

                        case ROTATE:
                            event.yaw = event.yaw + this.spinYaw;

                            event.pitch = 0.0F;

                            this.spinYaw = (float) (this.spinYaw + this.speedSetting.asDouble());
                    }
                }

                if (this.headlessSetting.asBoolean()) {

                    event.pitch = 180.0F;
                }

                if (this.sneakSetting.asBoolean() && rotation.sneaking) {

                    this.sendPacketWithoutEvent(
                            new C0BPacketEntityAction(
                                    minecraft.player, C0BPacketEntityActionAction.STOP_SNEAKING));

                    rotation.sneaking = true;

                    break;
                }

                rotation.sneaking = false;

                break;

            case POST:
                if (this.sneakSetting.asBoolean() && minecraft.player.ticksExisted % 2 == 0) {

                    this.sendPacketWithoutEvent(
                            new C0BPacketEntityAction(
                                    minecraft.player, C0BPacketEntityActionAction.START_SNEAKING));

                    rotation.sneaking = true;

                    return;
                }

                rotation.sneaking = false;
        }
    }


}
