package remix.client.notification;

public enum NotificationPriority {
    LOW,
    NORMAL,
    HIGH;
    private static NotificationPriority[] gg;

    private static boolean legacyNotificationFlowFlag;

    public static void setLegacyNotificationFlowFlag(boolean flag) {
        remix.client.notification.NotificationPriority.legacyNotificationFlowFlag = flag;
    }

    public static boolean getLegacyNotificationFlowFlag() {
        return remix.client.notification.NotificationPriority.legacyNotificationFlowFlag;
    }

    // region String decoding support

    // endregion

    static {
        gg =
                new NotificationPriority[] {
                    remix.client.notification.NotificationPriority.LOW,
                    remix.client.notification.NotificationPriority.NORMAL,
                    remix.client.notification.NotificationPriority.HIGH
                };
    }

    public static boolean isLegacyNotificationFlowFlagUnset() {
        boolean flag = getLegacyNotificationFlowFlag();
        return !flag;
    }

    // region String decoding support

    // endregion

    private static NotificationPriority[] __recovery_values171() {
        return gg.clone();
    }

    private NotificationPriority() {
        if (ordinal() == 0) {
            initializeConstantState();
        }
    }

    // Initialize shared state before the first constant is published.
    private static void initializeConstantState() {
        setLegacyNotificationFlowFlag(true);
    }
}
