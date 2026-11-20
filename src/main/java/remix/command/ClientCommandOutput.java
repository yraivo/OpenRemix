package remix.command;

public interface ClientCommandOutput {
    void printMessage(String text, Object... object);

    void printDebugMessage(String text, Object... object);

    void printRawMessage(String text, Object... object);
}
