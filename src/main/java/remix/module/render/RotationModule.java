package remix.module.render;

import remix.event.impl.ModelRotationEvent;
import remix.event.MotionPhase;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.event.MotionEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;



/** Rotation module: render events and 1 settings. */

public final class RotationModule extends ModuleBase {



    public boolean usingItem;

    public float serverYaw;



    public float serverPitch;

    public float previousServerYaw;

    private Setting lockViewSetting = new Setting("LockView", this, false);



    public float previousServerPitch;

    public boolean sneaking;



    public RotationModule() {

        super(

                "Rotation",

                new ModuleDefaults(ModuleCategory.RENDER).visible(false));

        this.setName("Server Side Rotation");

    }






    @Subscribe(mc = MotionEvent.class, priority = SubscriberPriority.HIGHEST)

    public void onMotion(MotionEvent event) {

        if (event.getPhase() == MotionPhase.PRE) {

            this.serverYaw = event.getYaw();

            this.serverPitch = event.getPitch();

            this.previousServerYaw = event.getPreviousYaw();

            this.previousServerPitch = event.getPreviousPitch();

            this.sneaking = minecraft.player.isSneaking();

            this.usingItem = minecraft.player.isUsingItem() || minecraft.getItemRenderer().forceBlockAnimation;

            if (this.lockViewSetting.asBoolean()) {

                minecraft.player.rotationYaw = this.serverYaw;

                minecraft.player.rotationPitch = this.serverPitch;

            }

        }

    }



    @Subscribe(mc = ModelRotationEvent.class)

    public void onModelRotation(ModelRotationEvent event) {

        if (event.getEntity() == minecraft.getRenderViewEntity()) {

            event.setHeadYaw(this.serverYaw);

            event.setPreviousHeadYaw(this.previousServerYaw);

            event.setBodyYaw(this.serverYaw);

            event.setPreviousBodyYaw(this.previousServerYaw);

            event.setPitch(this.serverPitch);

            event.setPreviousPitch(this.previousServerPitch);

            event.setSneaking(this.sneaking || minecraft.player.isSneaking());

            event.setUsingItem(this.usingItem);

        }

    }

}

