package net.minecraft.event;

public class ClickEvent {
    private String value;

    private static int[] k;
    private ClickEventAction action;

    // region String decoding support

    // endregion

    public static int[] c() {
        return k;
    }

    public ClickEvent(ClickEventAction blbValue, String text) {
        this.action = blbValue;
        this.value = text;
    }

    public String getValue() {
        return this.value;
    }

    static {
        b(null);
    }

    public ClickEventAction getAction() {
        return this.action;
    }

    @Override
    public String toString() {
        return "ClickEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
    }

    public static void b(int[] aint) {
        k = aint;
    }

    // region String decoding support

    // endregion

    @Override
    public int hashCode() {
        int i = this.action.hashCode();
        return 31 * i + (this.value != null ? this.value.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object != null && this.getClass() == object.getClass()) {
            ClickEvent clickEvent = (ClickEvent) object;
            if (this.action != clickEvent.action) {
                return false;
            }

            if (this.value != null) {
                if (!this.value.equals(clickEvent.value)) {
                    return false;
                }
            } else if (clickEvent.value != null) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }
}
