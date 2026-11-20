package remix.util.path;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;

public class AStarPathfinder {
    private List<PathNode> openNodes;
    private BlockPos destination;
    private PathNode originNode;
    private long searchStartedMillis;
    private final List<PathNode> pathNodes = new ArrayList<>();
    private List<PathNode> closedNodes;
    private static int legacyTraversalMarker;
    private BlockPos origin;
    private static final long SEARCH_BUDGET_MILLIS = 20L;

    public void findPath() {
        this.seedSearchFromDestination();
        if (PathBlockValidator.isPassable(new BlockPos(this.origin)) && PathBlockValidator.isPassable(this.destination)) {
            while (!this.openNodes.isEmpty()) {
                if (this.advanceSearchOrStop()) {
                    break;
                }
            }
            this.collectPathNodes();
        }
    }

    public static void setLegacyTraversalMarker(int i) {
        remix.util.path.AStarPathfinder.legacyTraversalMarker = i;
    }

    private void seedSearchFromDestination() {
        PathNode destinationNode = new PathNode(this.destination.getX(), this.destination.getY(), this.destination.getZ());
        destinationNode.setCost(-1.0);
        this.openNodes.add(destinationNode);
        this.searchStartedMillis = System.currentTimeMillis();
    }

    public List<PathNode> getPathNodes() {
        return this.pathNodes;
    }

    public AStarPathfinder(BlockPos origin, BlockPos destination) {
        boolean firstNodeOnly = getTraversalModeToken() == 0;
        this.openNodes = new ArrayList<>();
        this.closedNodes = new ArrayList<>();
        this.destination = destination;
        this.origin = origin;
        if (firstNodeOnly) {
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }
    }

    public List<Vec3> getPathWaypoints() {
        List<PathNode> path = this.pathNodes;
        boolean firstNodeOnly = getLegacyTraversalMarker() != 0;
        List<Vec3> waypoints = new ArrayList<>();
        for (int index = 0, size = path.size(); index < size; index++) {
            PathNode node = path.get(index);
            waypoints.add(new Vec3(node.getX() + 0.5, node.getY(), node.getZ() + 0.5));
            if (firstNodeOnly) {
                break;
            }
        }
        return waypoints;
    }

    public static int getLegacyTraversalMarker() {
        return remix.util.path.AStarPathfinder.legacyTraversalMarker;
    }

    private boolean isNodeClosed(PathNode candidate) {
        for (PathNode closed : this.closedNodes) {
            if (candidate.getX() == closed.getX() && candidate.getY() == closed.getY() && candidate.getZ() == closed.getZ()) {
                return true;
            }
        }
        return false;
    }

    private boolean advanceSearchOrStop() {
        boolean firstNodeOnly = getTraversalModeToken() == 0;
        double originX = this.origin.getX();
        double originY = this.origin.getY();
        double originZ = this.origin.getZ();
        if (this.destination.getX() == originX && this.destination.getY() == originY && this.destination.getZ() == originZ) {
            return true;
        }
        if (System.currentTimeMillis() - this.searchStartedMillis >= remix.util.path.AStarPathfinder.SEARCH_BUDGET_MILLIS) {
            return true;
        }

        int bestIndex = this.selectOpenNode(firstNodeOnly);
        this.closedNodes.add(this.openNodes.get(bestIndex));
        this.openNodes.remove(bestIndex);
        PathNode parent = this.closedNodes.get(this.closedNodes.size() - 1);
        double x = parent.getX();
        double y = parent.getY();
        double z = parent.getZ();
        for (double neighborX = x - 1.0; neighborX <= x + 1.0; neighborX++) {
            if (this.enqueuePathNeighbor(parent, neighborX, y, z, originX, originY, originZ)) {
                return true;
            }
            if (firstNodeOnly) {
                break;
            }
        }
        for (double neighborZ = z - 1.0; neighborZ <= z + 1.0; neighborZ++) {
            if (this.enqueuePathNeighbor(parent, x, y, neighborZ, originX, originY, originZ)) {
                return true;
            }
            if (firstNodeOnly) {
                break;
            }
        }
        for (double neighborY = y - 1.0; neighborY <= y + 1.0; neighborY++) {
            if (this.enqueuePathNeighbor(parent, x, neighborY, z, originX, originY, originZ)) {
                return true;
            }
            if (firstNodeOnly) {
                break;
            }
        }
        return false;
    }

    private int selectOpenNode(boolean firstNodeOnly) {
        int best = -1;
        for (int index = 0; index < this.openNodes.size(); index++) {
            if (best == -1 || this.openNodes.get(index).getCost() < this.openNodes.get(best).getCost()) {
                best = index;
            }
            if (firstNodeOnly) {
                break;
            }
        }
        return best;
    }

    private boolean enqueuePathNeighbor(PathNode parent, double x, double y, double z,
            double originX, double originY, double originZ) {
        PathNode candidate = new PathNode(x, y, z);
        BlockPos position = new BlockPos(x, y, z);
        if (!PathBlockValidator.isPassable(position) || this.isNodeClosed(candidate)) {
            return false;
        }
        candidate.setCost(PathBlockValidator.manhattanDistance(new BlockPos(originX, originY, originZ), position));
        candidate.setParentNode(parent);
        this.openNodes.add(candidate);
        if (x == originX && y == originY && z == originZ) {
            this.originNode = candidate;
            return true;
        }
        return false;
    }

    private void collectPathNodes() {
        boolean firstParentOnly = getTraversalModeToken() == 0;
        if (this.originNode != null) {
            PathNode node = this.originNode;
            this.pathNodes.add(node);
            while ((node = node.getParentNode()) != null) {
                this.pathNodes.add(node);
                if (firstParentOnly) {
                    break;
                }
            }
        }
    }

    public static int getTraversalModeToken() {
        int i = getLegacyTraversalMarker();
        return i == 0 ? 8 : 0;
    }

    static {
        if (getLegacyTraversalMarker() != 0) {
            setLegacyTraversalMarker(92);
        }
    }
}
