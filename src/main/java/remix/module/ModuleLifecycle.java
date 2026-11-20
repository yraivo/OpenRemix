package remix.module;

public interface ModuleLifecycle {
    void onDisable();

    void toggle();

    void onEnable();

    void onToggle();

    boolean isEnabled();

    boolean isDisabled();
}
