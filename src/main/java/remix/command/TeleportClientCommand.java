package remix.command;

import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import remix.util.path.AStarPathfinder;
import remix.command.AbstractClientCommand;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.annotation.Subscribe;
import remix.client.RemixClient;

import java.util.List;

public final class TeleportClientCommand extends AbstractClientCommand {
    private double destinationY;

    private double destinationZ;

    private double destinationX;

    @Override
    public boolean execute(String[] arguments) {
        boolean standardFlow = TargetsClientCommand.isStandardTargetFlow();
        if (arguments.length != 4 && arguments.length != 5) {
            return false;
        }

        this.destinationX = Double.parseDouble(arguments[1]);
        this.destinationY = Double.parseDouble(arguments[2]);
        this.destinationZ = Double.parseDouble(arguments[3]);
        if (arguments.length == 5) {
            this.teleportAlongPath(standardFlow);
            if (standardFlow) {
                return true;
            }
        }

        RemixClient.eventBus.register(this);
        ec.getNetworkHandler().sendPacketNoEvent(
                new PlayerPositionPacket(this.destinationX + 11.0, this.destinationY + 11.0, this.destinationZ + 11.0, true));
        return true;
    }

    private void teleportAlongPath(boolean standardFlow) {
        BlockPos destination = new BlockPos(this.destinationX, this.destinationY, this.destinationZ);
        if (ec.world.isBlockLoaded(destination)) {
            AStarPathfinder pathfinder = new AStarPathfinder(new BlockPos(ec.player), destination);
            pathfinder.findPath();
            List<Vec3> waypoints = pathfinder.getPathWaypoints();
            if (!waypoints.isEmpty()) {
                for (Vec3 waypoint : waypoints) {
                    ec.getNetworkHandler().sendPacketNoEvent(
                            new PlayerPositionPacket(waypoint.x, waypoint.y, waypoint.z, true));
                    if (!standardFlow) {
                        break;
                    }
                }
                ec.player.setPosition(this.destinationX, this.destinationY, this.destinationZ);
            }
            if (waypoints.isEmpty() || !standardFlow) {
                ClientCommandManager.printMessage("Failed to generate Path!");
            }
            if (standardFlow) {
                return;
            }
        }
        ClientCommandManager.printMessage("That position is not loaded in!");
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (event.getDirection() == PacketDirection.RECEIVED
                && event.getPacket() instanceof S08PacketPlayerPosLook) {
            ec.getNetworkHandler()
                    .sendPacketNoEvent(
                            new PlayerPositionPacket(this.destinationX, this.destinationY, this.destinationZ, true));
            ec.getNetworkHandler()
                    .sendPacketNoEvent(
                            new PlayerPositionPacket(
                                    this.destinationX + 11.0, this.destinationY + 11.0, this.destinationZ + 11.0, true));
            RemixClient.eventBus.unregister(this);
        }
    }

    public TeleportClientCommand() {
        super("Teleport", new String[] {"teleport", "tp"});
    }

    public String getUsage() {
        return "teleport <x> <y> <z>";
    }
}
