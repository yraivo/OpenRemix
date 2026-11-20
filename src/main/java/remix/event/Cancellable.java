package remix.event;

public interface Cancellable {
    void cancel();

    void setCancelled(boolean flag);

    boolean isCancelled();
}
