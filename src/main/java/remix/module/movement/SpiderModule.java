package remix.module.movement;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.util.BoundingBox;
import remix.event.impl.BlockCollisionEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.event.MoveEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;



/** Changes collision bounds for GetInside and adds upward motion while horizontally colliding. unusedCollisionFlag has no reads or writes in this class; the Move handler is empty. */

public final class SpiderModule extends ModuleBase {



    private final Setting getInsideSetting = new Setting("GetInside", this, false);

    private boolean unusedCollisionFlag;



    @Subscribe(mc = BlockCollisionEvent.class)

    public void onBlockCollision(BlockCollisionEvent event) {

        BoundingBox bounds = event.getBounds();

        if (this.getInsideSetting.asBoolean()

                && bounds != null

                && bounds.maxY > minecraft.player.posY) {

            event.setBounds(bounds.contract(1.0E-10, 0.0, 1.0E-10));

        }

    }



    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        LocalPlayer player = minecraft.player;



        if (player.collidedHorizontally) {

            if (this.getInsideSetting.asBoolean()) {

                if (!player.onGround) {

                    return;

                }



                player.jump();



                return;

            }



            if (player.motionY <= 0.0) {

                player.motionY = 0.36815;

            }

        }

    }






    public SpiderModule() {

        super("Spider", ModuleDefaults.ofCategory(ModuleCategory.MOVEMENT));

    }



    @Subscribe(mc = MoveEvent.class)

    public void onMove(MoveEvent event) {}

}

