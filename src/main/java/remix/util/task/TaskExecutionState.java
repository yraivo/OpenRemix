package remix.util.task;

public enum TaskExecutionState {
    PROCESSING,
    LOADING,
    CONFIRMED,
    TRUE,
    LOST,
    MIDDLE,
    PASSED,
    FALSE,
    UNDEFINED,
    FAILED;

    public boolean fo() {
        return this == remix.util.task.TaskExecutionState.FAILED;
    }

    // region String decoding support

    // endregion

    public boolean e() {
        return this == remix.util.task.TaskExecutionState.PROCESSING;
    }

    public boolean f() {
        return this == remix.util.task.TaskExecutionState.MIDDLE;
    }

    public boolean method_d87039b07bd6961907f2() {
        return this == remix.util.task.TaskExecutionState.LOADING;
    }

    public boolean d() {
        return this == remix.util.task.TaskExecutionState.PASSED;
    }

    public boolean aL() {
        return this == remix.util.task.TaskExecutionState.LOST;
    }

    // region String decoding support

    // endregion

    public boolean method_b417d7500ec59d69e373() {
        return this == remix.util.task.TaskExecutionState.FALSE;
    }

    // region String decoding support

    // endregion

    public boolean aY() {
        return this == remix.util.task.TaskExecutionState.TRUE;
    }


    private TaskExecutionState() {}
}
