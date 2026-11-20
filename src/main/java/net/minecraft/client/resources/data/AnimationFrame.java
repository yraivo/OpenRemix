package net.minecraft.client.resources.data;

public class AnimationFrame {
    private int frameTime;
    private int frameIndex;

    public int getFrameTime() {
        return this.frameTime;
    }

    public AnimationFrame(int frameIndexValue, int j) {
        this.frameIndex = frameIndexValue;
        this.frameTime = j;
    }

    public int getFrameIndex() {
        return this.frameIndex;
    }

    public AnimationFrame(int i) {
        this(i, -1);
    }

    public boolean hasNoTime() {
        return this.frameTime == -1;
    }
}
