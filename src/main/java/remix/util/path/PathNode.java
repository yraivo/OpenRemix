package remix.util.path;

public class PathNode {
    private double y;
    private double cost;
    private PathNode parentNode;
    private double z;
    private double x;

    public PathNode getParentNode() {
        return this.parentNode;
    }

    public double getCost() {
        return this.cost;
    }

    public double getX() {
        return this.x;
    }

    public void setY(double d0) {
        this.y = d0;
    }

    public double getY() {
        return this.y;
    }

    PathNode(double d0, double d1, double d2) {
        this.x = d0;
        this.y = d1;
        this.z = d2;
    }

    public double getZ() {
        return this.z;
    }

    public void setParentNode(
            PathNode parentNodeValue) {
        this.parentNode = parentNodeValue;
    }

    public void setZ(int i) {
        this.z = i;
    }

    public void setX(int i) {
        this.x = i;
    }

    public void setCost(double d0) {
        this.cost = d0;
    }
}
