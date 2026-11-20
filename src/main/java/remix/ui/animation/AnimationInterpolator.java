package remix.ui.animation;

import net.minecraft.entity.Entity;

import remix.compat.vecmath.Vector2f;
import remix.util.time.ElapsedTimer;

public class AnimationInterpolator extends AbstractAnimation {
    private Vector2f initialTarget;
    private Vector2f initialPosition;
    private Vector2f targetPosition;

    public void reset() {
        this.setTargetPosition(this.initialTarget);
        this.setVector2f(this.initialPosition);
    }

    public void setTargetPosition(Vector2f vector2f) {
        this.targetPosition = vector2f;
    }

    public AnimationInterpolator(float f, Vector2f vector2f, Vector2f vector2fValue) {
        this(f, vector2f, vector2fValue, AbstractAnimation.d());
    }

    private AnimationInterpolator(
            float f, Vector2f vector2f, Vector2f vector2fValue, boolean initialFlowFlag) {
        super(f, vector2f);

        boolean flag1 = initialFlowFlag;

        boolean flag = flag1;
        this.initialPosition = vector2f;
        this.initialTarget = vector2fValue;
        this.targetPosition = vector2fValue;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            AbstractAnimation.method_a8745b073fa8adfa1857(!flag);
        }
    }

    public float stepY() {
        ElapsedTimer elapsedTimer = this.getElapsedTimer();

        return !elapsedTimer.elapsedAndReset(16L)
                ? this.getVector2f().y
                : (this.getVector2f().y =
                        this.getVector2f().y
                                + (this.getTargetPosition().y - this.getVector2f().y) / this.aN());
    }

    public Vector2f getTargetPosition() {
        return this.targetPosition;
    }

    public boolean isAtY(float f) {
        return Math.round(this.getVector2f().y) == f;
    }

    public float stepX() {
        ElapsedTimer elapsedTimer = this.getElapsedTimer();

        return !elapsedTimer.elapsedAndReset(16L)
                ? this.getVector2f().x
                : (this.getVector2f().x =
                        this.getVector2f().x
                                + (this.getTargetPosition().x - this.getVector2f().x) / this.aN());
    }

    public boolean isAtX(float f) {
        return Math.round(this.getVector2f().x) == Math.round(f);
    }
}
