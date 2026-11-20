package remix.command;

import net.minecraft.entity.Entity;

import java.util.function.Function;

public class CallbackCommandAction implements CommandAction {
    private static Entity[] iT;
    private String name;
    private String[] aliases;
    private Function<String[], Boolean> callback;

    public boolean execute(String[] astring) {
        return this.callback.apply(astring);
    }

    public String getName() {
        return this.name;
    }

    static {
        if (method_da2ac5c94686ee11e9e6() == null) {
            b(new Entity[1]);
        }
    }

    public CallbackCommandAction(
            String text, String[] astring, Function<String[], Boolean> function) {
        super();

        this.name = text;
        this.aliases = astring;
        this.callback = function;
    }

    public static void b(Entity[] entityArray) {
        iT = entityArray;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return iT;
    }
}
