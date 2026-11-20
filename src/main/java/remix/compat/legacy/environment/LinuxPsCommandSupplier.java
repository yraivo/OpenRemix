package remix.compat.legacy.environment;

import remix.util.task.NamedValueHolder;

public final class LinuxPsCommandSupplier extends NamedValueHolder {


    public LinuxPsCommandSupplier() {
        super(new String("ps -e"), new String("Linux"));
    }
}
