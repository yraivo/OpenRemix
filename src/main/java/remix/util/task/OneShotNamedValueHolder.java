package remix.util.task;

import remix.compat.legacy.security.SecurityCheckRule;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;

public class OneShotNamedValueHolder extends remix.util.task.NamedValueHolder {
    private Object mj;
    private boolean e;
    public static final FluentListWrapper bSR = FluentCollections.ax();

    public Object getValue() {
        if (this.e) {
            return this.mj;
        }

        this.e = true;
        if (this instanceof SecurityCheckRule) {
            bSR.add(this);
        }

        return (java.lang.Object) super.getValue();
    }

    public OneShotNamedValueHolder(Object object, Object object1, String text) {
        super(object, text);
        this.mj = (java.lang.Object) object1;
    }
}
