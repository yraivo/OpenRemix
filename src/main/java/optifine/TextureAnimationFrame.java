package optifine;

public class TextureAnimationFrame {
    public int duration;
    public int counter;
    public int index = 0;

    public TextureAnimationFrame(int indexValue, int durationValue) {
        this.index = indexValue;
        this.duration = durationValue;
    }
}
