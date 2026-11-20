package remix.event.annotation;

public enum SubscriberPriority {
    HIGHEST(4),
    HIGH(3),
    NORMAL(2),
    LOW(1),
    LOWEST(0);

    public int level;

    SubscriberPriority(int j) {
        this.level = j;
    }

    public int getLevel() {
        return this.level;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion


}
