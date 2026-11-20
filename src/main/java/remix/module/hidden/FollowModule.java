package remix.module.hidden;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import remix.event.MotionPhase;
import remix.util.movement.MovementPhysicsHelper;
import remix.util.entity.RotationHelper;
import remix.event.annotation.Subscribe;
import remix.event.MotionEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;



/** Follow module: hidden events and 0 settings. */

public final class FollowModule extends ModuleBase {

    public BlockPos destination;

    private static String moduleLabel;



    // region String decoding support



    // endregion



    @Subscribe(mc = MotionEvent.class)

    public void onMotion(MotionEvent event) {

        if (event.getPhase() == MotionPhase.PRE) {

            if (this.destination != null) {

                this.destination =

                        new BlockPos(

                                this.destination.getX(),

                                minecraft.player.posY,

                                this.destination.getZ());

                if (minecraft.player.getDistanceSq(this.destination) > 10.0) {

                    minecraft.player.rotationYaw =

                            RotationHelper.rotationsToPoint(new Vec3(this.destination))[0];

                    Vec3 vec3 =

                            MovementPhysicsHelper.offsetDirectional(

                                    minecraft.player.getPositionVector(), 4.5);

                    minecraft.player.setPosition(vec3.x, vec3.y, vec3.z);

                    return;

                }



                this.toggle();

                return;

            }



            this.toggle();

        }

    }



    public void onDisable() {

        this.destination = null;

        super.onDisable();

    }




    // region Static initialization and string tables

    static {

        moduleLabel = "Follow";

    }



    // endregion



    public FollowModule() {

        super(

                moduleLabel,

                new ModuleDefaults(ModuleCategory.HIDDEN).keyBind(41));

    }



    // region String decoding support



    // endregion

}

