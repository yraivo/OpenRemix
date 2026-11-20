package remix.ui.animation;

import net.minecraft.entity.Entity;

import remix.compat.vecmath.Vector2f;
import remix.util.time.ElapsedTimer;

public abstract class AbstractAnimation {
    private ElapsedTimer elapsedTimer;
    private static boolean d;
    private Vector2f vector2f;
    private float gw;

    public static boolean d() {
        return d;
    }

    public void setProgress(float f) {
        this.gw = f;
    }

    public void setVector2f(Vector2f vector2fValue) {
        this.vector2f = vector2fValue;
    }

    public static boolean e() {
        boolean flag = d();
        return !flag;
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        d = flag;
    }

    public Vector2f getVector2f() {
        return this.vector2f;
    }

    public ElapsedTimer getElapsedTimer() {
        return this.elapsedTimer;
    }

    public AbstractAnimation(float f, Vector2f vector2fValue) {
        super();
        boolean initialFlowFlag = e();

        boolean flag1 = initialFlowFlag;

        boolean flag = flag1;
        this.gw = f;
        this.vector2f = vector2fValue;
        this.elapsedTimer = new ElapsedTimer();
        if (!flag) {
            Entity.setLegacyGlobalFlowMarker(new String[3]);
        }
    }

    static {
        if (!e()) {
            method_a8745b073fa8adfa1857(true);
        }
    }

    public float aN() {
        return this.gw;
    }
}
