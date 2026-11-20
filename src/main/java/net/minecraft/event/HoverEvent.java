package net.minecraft.event;

import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponent;

public class HoverEvent {

    private ChatComponent value;
    private HoverEventAction action;

    public ChatComponent getValue() {
        return this.value;
    }

    public HoverEvent(HoverEventAction bldValue, ChatComponent aqMValue) {
        this.action = bldValue;
        this.value = aqMValue;
    }

    @Override
    public String toString() {
        int[] aint = ClickEvent.c();
        String text = "HoverEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ClickEvent.b(new int[4]);
        }

        return text;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object != null && this.getClass() == object.getClass()) {
            HoverEvent hoverEvent = (HoverEvent) object;
            if (this.action != hoverEvent.action) {
                return false;
            }

            if (this.value != null) {
                if (!this.value.equals(hoverEvent.value)) {
                    return false;
                }
            } else if (hoverEvent.value != null) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int[] aint1 = ClickEvent.c();
        int i = this.action.hashCode();
        int[] aint = aint1;
        i = 31 * i + (this.value != null ? this.value.hashCode() : 0);
        if (aint != null) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }

        return i;
    }


    public HoverEventAction getAction() {
        return this.action;
    }
}
