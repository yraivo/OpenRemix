package remix.compat.vecmath;

import java.io.Serializable;

public class TexCoord3f extends Tuple3f implements Serializable {
    static final long serialVersionUID = -3517736544731446513L;

    public TexCoord3f(Tuple3f tuple3f) {
        super(tuple3f);
    }

    public TexCoord3f() {}

    public TexCoord3f(float[] afloat) {
        super(afloat);
    }

    public TexCoord3f(float p171_0, float p171_1, float p171_2) {
        super(p171_0, p171_1, p171_2);
    }

    public TexCoord3f(Tuple3d tuple3d) {
        super(tuple3d);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public TexCoord3f(TexCoord3f texCoord3f) {
        super(texCoord3f);
    }
}
