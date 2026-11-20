package shadersmod.client;

public class MultiTexID {
    public int spec;
    public int base;
    public int norm;

    public MultiTexID(int baseValue, int normValue, int specValue) {
        this.base = baseValue;
        this.norm = normValue;
        this.spec = specValue;
    }
}
