package remix.util.math;

public class MathUtils2 {
    public static int getAverage(int[] p171_0) {
        int i = optifine.ReflectorMethod.cR();
        if (p171_0.length <= 0) {
            return 0;
        }

        int j = 0;
        int k = 0;

        while (k < p171_0.length) {
            int l = p171_0[k];
            j += l;
            k++;
            if (i == 0) {
                break;
            }
        }

        return j / p171_0.length;
    }
}
