package remix.event.subscription;

public enum SubscriptionMemberKind {
    METHOD,
    FIELD;
    private static SubscriptionMemberKind[] caC;

    private static boolean b;

    public static boolean method_b417d7500ec59d69e373() {
        return b;
    }

    static {
        caC =
                new SubscriptionMemberKind[] {
                    remix.event.subscription.SubscriptionMemberKind.METHOD,
                    remix.event.subscription.SubscriptionMemberKind.FIELD
                };
    }

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = method_b417d7500ec59d69e373();
        return !flag;
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        b = flag;
    }

    private static SubscriptionMemberKind[] __recovery_values171() {
        return caC.clone();
    }

    private SubscriptionMemberKind() {
        if (ordinal() == 0) {
            initializeConstantState();
        }
    }

    // Initialize shared state before the first constant is published.
    private static void initializeConstantState() {
        method_a8745b073fa8adfa1857(true);
    }
}
