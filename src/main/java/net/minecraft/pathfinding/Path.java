package net.minecraft.pathfinding;

public class Path {
    private PathPoint[] pathPoints = new PathPoint[1024];
    private int count;

    private static String c;

    private void sortForward(int index) {
        String text = PathNavigate.lB();
        PathPoint pathPoint = this.pathPoints[index];
        String textValue = text;
        float f = pathPoint.tn;

        do {
            int j = 1 + (index << 1);
            int k = j + 1;
            if (j >= this.count && textValue != null) {
                break;
            }

            PathPoint pathPointValue;
            float f1;
            PathPoint pathPointValue2;
            float f2;
            {
                pathPointValue = this.pathPoints[j];
                f1 = pathPointValue.tn;
                if (k >= this.count) {
                    pathPointValue2 = null;
                    f2 = Float.POSITIVE_INFINITY;
                    if (!(textValue != null)) {

                        pathPointValue2 = this.pathPoints[k];
                        f2 = pathPointValue2.tn;
                    }
                } else {

                    pathPointValue2 = this.pathPoints[k];
                    f2 = pathPointValue2.tn;
                }
            }

            if (f1 < f2) {
                if (f1 >= f && textValue != null) {
                    break;
                }

                this.pathPoints[index] = pathPointValue;
                pathPointValue.index = index;
                index = j;
                if (textValue != null) {
                    continue;
                }
            }

            if (f2 >= f && textValue != null) {
                break;
            }

            this.pathPoints[index] = pathPointValue2;
            pathPointValue2.index = index;
            index = k;
        } while (textValue != null);

        this.pathPoints[index] = pathPoint;
        pathPoint.index = index;
    }

    public void changeDistance(PathPoint pathPoint, float f) {
        String text = PathNavigate.lB();
        float f1 = pathPoint.tn;
        String textValue = text;
        pathPoint.tn = f;
        if (f < f1) {
            this.sortBack(pathPoint.index);
            if (textValue != null) {
                return;
            }
        }

        this.sortForward(pathPoint.index);
    }

    static {
        c = "OW KNOWS!";
    }

    private void sortBack(int index) {
        String text = PathNavigate.lB();
        PathPoint pathPoint = this.pathPoints[index];
        String textValue = text;
        float f = pathPoint.tn;

        while (index > 0) {
            int j = index - 1 >> 1;
            PathPoint pathPointValue = this.pathPoints[j];
            if (f >= pathPointValue.tn && textValue != null) {
                break;
            }

            this.pathPoints[index] = pathPointValue;
            pathPointValue.index = index;
            index = j;
            if (textValue == null) {
                break;
            }
        }

        this.pathPoints[index] = pathPoint;
        pathPoint.index = index;
    }

    // region String decoding support

    // endregion

    public PathPoint addPoint(PathPoint pathPoint) {
        if (pathPoint.index >= 0) {
            throw new IllegalStateException(c);
        }

        if (this.count == this.pathPoints.length) {
            PathPoint[] bxjValue = new PathPoint[this.count << 1];
            System.arraycopy(this.pathPoints, 0, bxjValue, 0, this.count);
            this.pathPoints = bxjValue;
        }

        this.pathPoints[this.count] = pathPoint;
        pathPoint.index = this.count;
        this.sortBack(this.count++);
        return pathPoint;
    }

    // region String decoding support

    // endregion

    public PathPoint dequeue() {
        PathPoint pathPoint = this.pathPoints[0];
        this.pathPoints[0] = this.pathPoints[--this.count];
        this.pathPoints[this.count] = null;
        if (this.count > 0) {
            this.sortForward(0);
        }

        pathPoint.index = -1;
        return pathPoint;
    }

    public void clearPath() {
        this.count = 0;
    }

    public boolean isPathEmpty() {
        return this.count == 0;
    }

    private static IllegalStateException method_b3985d2d68c259fdadde(
            IllegalStateException illegalstateexception) {
        return illegalstateexception;
    }
}
