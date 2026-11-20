package remix.compat.vecmath;

import java.awt.Color;
import java.io.Serializable;

public class Color3b extends Tuple3b implements Serializable {
    static final long serialVersionUID = 6632576088353444794L;

    public Color3b(Tuple3b tuple3b) {
        super(tuple3b);
    }

    public final void set(Color color) {
        this.x = (byte) color.getRed();
        this.y = (byte) color.getGreen();
        this.z = (byte) color.getBlue();
    }

    public Color3b(byte b0, byte b1, byte b2) {
        super(b0, b1, b2);
    }

    public Color3b(byte[] abyte) {
        super(abyte);
    }

    public final Color get() {
        int i = this.x & 255;
        int j = this.y & 255;
        int k = this.z & 255;
        return new Color(i, j, k);
    }

    public Color3b() {}

    public Color3b(Color color) {
        super((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Color3b(Color3b color3b) {
        super(color3b);
    }
}
