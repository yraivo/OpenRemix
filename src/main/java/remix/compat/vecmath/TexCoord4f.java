package remix.compat.vecmath;

import java.io.Serializable;

public class TexCoord4f extends Tuple4f implements Serializable {
    static final long serialVersionUID = -3517736544731446513L;

    public TexCoord4f(float p171_0, float p171_1, float p171_2, float p171_3) {
        super(p171_0, p171_1, p171_2, p171_3);
    }

    public TexCoord4f(float[] afloat) {
        super(afloat);
    }

    public TexCoord4f(Tuple4f tuple4f) {
        super(tuple4f);
    }

    public TexCoord4f(Tuple4d tuple4d) {
        super(tuple4d);
    }

    public TexCoord4f() {}

    // Recovered explicit constructors from original mapped instruction streams.
    public TexCoord4f(TexCoord4f texCoord4f) {
        super(texCoord4f);
    }
}
