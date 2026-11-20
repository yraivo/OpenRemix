package remix.event.impl;

import remix.event.CancellableEvent;

public class TextRenderEvent extends CancellableEvent {
    private String text;

    public void setText(String textValue) {
        this.text = textValue;
    }

    public TextRenderEvent(String textValue) {
        this.text = textValue;
    }

    public String getText() {
        return this.text;
    }
}
