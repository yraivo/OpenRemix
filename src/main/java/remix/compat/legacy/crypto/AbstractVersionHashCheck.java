package remix.compat.legacy.crypto;

import remix.util.task.NamedValueHolder;

import net.minecraft.entity.Entity;

public abstract class AbstractVersionHashCheck {
    private String a;
    private static Entity[] b;

    public abstract Double method_77ca1ae9ef52990e8495(
            NamedValueHolder namedValueHolder);

    public String method_e0e3092a59fee58fae52() {
        return this.a;
    }

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }

    public AbstractVersionHashCheck(String text) {
        this.a = text;
    }

    static {
        if (method_da2ac5c94686ee11e9e6() != null) {
            b(new Entity[1]);
        }
    }
}
