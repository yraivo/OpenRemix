package remix.compat.vecmath;

import java.io.Serializable;

public class Point4i extends Tuple4i implements Serializable {
    static final long serialVersionUID = 620124780244617983L;

    public Point4i() {}

    public Point4i(int p171_0, int p171_1, int p171_2, int p171_3) {
        super(p171_0, p171_1, p171_2, p171_3);
    }

    public Point4i(Tuple4i tuple4i) {
        super(tuple4i);
    }

    public Point4i(int[] aint) {
        super(aint);
    }
}
