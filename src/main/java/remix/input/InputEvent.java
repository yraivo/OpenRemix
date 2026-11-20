package remix.input;

import remix.event.CancellableClientEvent;
import remix.event.InputType;

public class InputEvent extends CancellableClientEvent {
    private char character;
    private int keyCode;
    private int rightClickDelay;
    private InputType type;

    public InputEvent(InputType typeValue) {
        this.character = ' ';
        this.type = typeValue;
        this.keyCode = typeValue.getDefaultKeyCode();
    }

    public InputType getType() {
        return this.type;
    }

    public char getCharacter() {
        return this.character;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public int getRightClickDelay() {
        return this.rightClickDelay;
    }

    public InputEvent(int i) {
        this(InputType.RIGHT_CLICK);
        this.rightClickDelay = i;
    }

    public InputEvent(int keyCodeValue, char c0) {
        this.keyCode = keyCodeValue;
        this.character = c0;
        this.type = InputType.KEY_PRESS;
    }

    public void setRightClickDelay(int i) {
        this.rightClickDelay = i;
    }
}
