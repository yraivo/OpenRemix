package remix.compat.vecmath;

import java.io.Serializable;

public class Point3i extends Tuple3i implements Serializable {
    static final long serialVersionUID = 6149289077348153921L;

    public Point3i(int p171_0, int p171_1, int p171_2) {
        super(p171_0, p171_1, p171_2);
    }

    public Point3i(Tuple3i tuple3i) {
        super(tuple3i);
    }

    public Point3i() {}

    public Point3i(int[] aint) {
        super(aint);
    }
}
