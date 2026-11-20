package remix.command;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import remix.util.path.AStarPathfinder;
import remix.command.AbstractClientCommand;
import remix.command.CommandAction;
import remix.event.MotionPhase;
import remix.util.render.Render2D3DUtils;
import remix.event.impl.Render3DEvent;
import remix.event.annotation.Subscribe;
import remix.client.RemixClient;

import java.util.ArrayList;
import java.util.List;

public final class PathfindClientCommand extends AbstractClientCommand {
    private double destinationZ;
    private double destinationX;

    private List<Vec3> pathWaypoints = new ArrayList<>();
    private double destinationY;
    private static String[] packedTextFragments;

    @Subscribe(mc = Render3DEvent.class)
    public void onRender3D(Render3DEvent event) {
        boolean flag = TargetsClientCommand.isStandardTargetFlow();
        if (event.getPhase() == MotionPhase.PRE) {
            Render2D3DUtils.beginUnoccludedWorldOverlay();
            GlStateManager.color(1.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(
                    -ec.getRenderManager().getRenderOriginX(),
                    -ec.getRenderManager().getRenderOriginY(),
                    -ec.getRenderManager().getRenderOriginZ());
            GlStateManager.glLineWidth(1.0F);
            GlStateManager.ab(3);

            for (Vec3 vec3 : this.pathWaypoints) {
                GlStateManager.vertex3d(vec3.x, vec3.y, vec3.z);
                if (!flag) {
                    break;
                }
            }

            GlStateManager.mO();
            Render2D3DUtils.endUnoccludedWorldOverlay();
        }
    }

    static {
        remix.command.PathfindClientCommand.packedTextFragments = new String[3];
        remix.command.PathfindClientCommand.packedTextFragments[0] = new String("s");
    }

    public String getUsage() {
        return "PathFind <x> <y> <z>";
    }

    public PathfindClientCommand() {
        super("Pathfind", new String[] {"pathfind", "pf"});
        this.addAction(
                CommandAction.requiringArguments(
                        4,
                        astring -> {
                            RemixClient.eventBus.unregister(this);
                            RemixClient.eventBus.register(this);
                            this.destinationX = Double.parseDouble(astring[1]);
                            this.destinationY = Double.parseDouble(astring[2]);
                            this.destinationZ = Double.parseDouble(astring[3]);
                            BlockPos blockPos = new BlockPos(this.destinationX, this.destinationY, this.destinationZ);
                            AStarPathfinder aStarPathfinder =
                                    new AStarPathfinder(
                                            new BlockPos(ec.player), blockPos);
                            aStarPathfinder.findPath();
                            this.pathWaypoints = aStarPathfinder.getPathWaypoints();
                            ClientCommandManager.printMessage("Done!");
                            return true;
                        }));
        this.addNamedAction(
                "stop",
                new String[] {"stop", packedTextFragments[0]},
                astring -> {
                    RemixClient.eventBus.unregister(this);
                    return true;
                });
    }
}
