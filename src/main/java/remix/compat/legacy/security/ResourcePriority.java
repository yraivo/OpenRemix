package remix.compat.legacy.security;

public enum ResourcePriority {
    HIGHEST(4),
    HIGH(3),
    NORMAL(2),
    LOW(1),
    LOWEST(0);

    private int rank;

    // region String decoding support

    // endregion

    ResourcePriority(int j) {
        this.rank = j;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion


    public int getRank() {
        return this.rank;
    }
}
