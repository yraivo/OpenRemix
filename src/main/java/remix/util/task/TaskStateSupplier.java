package remix.util.task;

import java.util.function.BooleanSupplier;

public interface TaskStateSupplier extends java.util.function.Supplier {
    TaskExecutionState a();

    static TaskStateSupplier a(BooleanSupplier booleansupplier) {
        return () ->
                booleansupplier.getAsBoolean()
                        ? TaskExecutionState.PASSED
                        : TaskExecutionState.FAILED;
    }

    @Override
    default Object get() {
        return this.a();
    }
}
