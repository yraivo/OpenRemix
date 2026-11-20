package remix.compat.vecmath;

class VecMathUtil {
    private VecMathUtil() {}

    static final long method_610bb001c28d547cfb8f(long i, long j) {

        i *= 31L;
        return i + j;
    }

    static final int hashFinish(long i) {
        return (int) (i ^ i >> 32);
    }

    static final long hashFloatToIntBits(long i, float f) {

        i *= 31L;
        return f == 0.0F ? i : i + Float.floatToIntBits(f);
    }

    static final long hashDoubleToLongBits(long i, double d0) {

        i *= 31L;
        return d0 == 0.0 ? i : i + Double.doubleToLongBits(d0);
    }
}
