package remix.compat.vecmath;

import java.io.Serializable;

public class Point2i extends Tuple2i implements Serializable {
    static final long serialVersionUID = 9208072376494084954L;

    public Point2i(int p171_0, int p171_1) {
        super(p171_0, p171_1);
    }

    public Point2i(int[] aint) {
        super(aint);
    }

    public Point2i(Tuple2i tuple2i) {
        super(tuple2i);
    }

    public Point2i() {}
}
