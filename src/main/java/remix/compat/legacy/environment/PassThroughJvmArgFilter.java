package remix.compat.legacy.environment;

class PassThroughJvmArgFilter extends JvmArgFilter {
    public Object matchArgumentOrContinue(String text, Object object) {
        return object;
    }

    PassThroughJvmArgFilter() {
        super(null);
    }
}
