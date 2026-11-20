package remix.module.world;

import net.minecraft.network.play.client.DiggingPacket;
import net.minecraft.network.play.client.PlayerPositionLookPacket;
import net.minecraft.network.play.client.UseEntityPacket;
import net.minecraft.network.play.client.UseItemPacket;
import net.minecraft.util.BlockPos;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import remix.util.path.AStarPathfinder;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;



import com.google.common.collect.Lists;



import java.util.List;



/** Infinite module: world events and 0 settings. */

public final class InfiniteModule extends ModuleBase {

    private static String moduleLabel;

    private BlockPos interactionDestination;



    public InfiniteModule() {

        super(moduleLabel, ModuleDefaults.ofCategory(ModuleCategory.WORLD));

    }



    // region String decoding support



    // endregion



    // region String decoding support



    // endregion



    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        RayTraceResult objectMouseOver = minecraft.objectMouseOver;

        if (objectMouseOver.typeOfHit == RayHitType.ENTITY) {

            this.interactionDestination = objectMouseOver.entityHit.getPosition();

        }



        if (objectMouseOver.typeOfHit == RayHitType.BLOCK) {

            this.interactionDestination = objectMouseOver.getBlockPos().offsetUp(1);

        }

    }




    // region Static initialization and string tables

    static {

        moduleLabel = "Infinite";

    }



    // endregion



    @Subscribe(mc = PacketEvent.class)

    public void onPacket(PacketEvent event) {

        if (event.getDirection() != PacketDirection.RECEIVED

                && this.interactionDestination != null

                && (event.getPacket() instanceof UseEntityPacket

                        || event.getPacket() instanceof UseItemPacket

                        || event.getPacket() instanceof DiggingPacket)) {

            AStarPathfinder pathfinder =

                    new AStarPathfinder(

                            new BlockPos(minecraft.player), this.interactionDestination);

            pathfinder.findPath();

            List list = pathfinder.getPathWaypoints();

            if (event.getDirection() == PacketDirection.SEND) {

                for (Object item : list) {

                    Vec3 vec3 = (Vec3) item;

                    this.sendPacketWithoutEvent(

                            new PlayerPositionLookPacket(

                                    vec3.x,

                                    vec3.y,

                                    vec3.z,

                                    minecraft.player.rotationYaw,

                                    minecraft.player.rotationPitch,

                                    true));

                }

                return;

            }



            for (Object item : Lists.reverse(list)) {

                Vec3 vec3Value = (Vec3) item;

                this.sendPacketWithoutEvent(

                        new PlayerPositionLookPacket(

                                vec3Value.x,

                                vec3Value.y,

                                vec3Value.z,

                                minecraft.player.rotationYaw,

                                minecraft.player.rotationPitch,

                                true));

            }

        }

    }

}

