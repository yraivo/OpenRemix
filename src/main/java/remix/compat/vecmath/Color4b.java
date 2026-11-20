package remix.compat.vecmath;

import java.awt.Color;
import java.io.Serializable;

public class Color4b extends Tuple4b implements Serializable {
    static final long serialVersionUID = -105080578052502155L;

    public Color4b(Tuple4b tuple4b) {
        super(tuple4b);
    }

    public Color4b(Color color) {
        super(
                (byte) color.getRed(),
                (byte) color.getGreen(),
                (byte) color.getBlue(),
                (byte) color.getAlpha());
    }

    public Color4b() {}

    public final void set(Color color) {
        this.x = (byte) color.getRed();
        this.y = (byte) color.getGreen();
        this.z = (byte) color.getBlue();
        this.w = (byte) color.getAlpha();
    }

    public final Color get() {
        int i = this.x & 255;
        int j = this.y & 255;
        int k = this.z & 255;
        int l = this.w & 255;
        return new java.awt.Color(i, j, k, l);
    }

    public Color4b(byte[] abyte) {
        super(abyte);
    }

    public Color4b(byte p171_0, byte p171_1, byte p171_2, byte p171_3) {
        super(p171_0, p171_1, p171_2, p171_3);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Color4b(Color4b color4b) {
        super(color4b);
    }
}
