package remix.security;

import java.util.regex.Pattern;

/** Bounds server-provided automatic replay to one server command. */
public final class ServerCommandPolicy {
    private static final Pattern PLAY = Pattern.compile("/play [A-Za-z0-9_]{1,64}");

    private ServerCommandPolicy() {}

    public static boolean isAllowedPlayCommand(
            boolean runCommand, String command, String localPrefix) {
        if (!runCommand || command == null || localPrefix == null) return false;
        if (!localPrefix.isEmpty() && command.startsWith(localPrefix)) return false;
        return PLAY.matcher(command).matches();
    }
}
