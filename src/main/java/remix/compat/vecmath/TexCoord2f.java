package remix.compat.vecmath;

import java.io.Serializable;

public class TexCoord2f extends Tuple2f implements Serializable {
    static final long serialVersionUID = 7998248474800032487L;

    public TexCoord2f(float p171_0, float p171_1) {
        super(p171_0, p171_1);
    }

    public TexCoord2f(float[] afloat) {
        super(afloat);
    }

    public TexCoord2f() {}

    public TexCoord2f(Tuple2f tuple2f) {
        super(tuple2f);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public TexCoord2f(TexCoord2f texCoord2f) {
        super(texCoord2f);
    }
}
