package remix.client;

import java.io.File;

/** Optional launcher-selected sharing of the server list and local resource packs. */
public final class ClientPaths {
    private ClientPaths() {}

    private static File sharedRoot() {
        String path = System.getProperty("remix.sharedGameDir");
        return path == null || path.trim().isEmpty() ? null : new File(path).getAbsoluteFile();
    }

    public static File serverListDirectory(File localGameDirectory) {
        File shared = sharedRoot();
        return shared == null ? localGameDirectory : shared;
    }

    public static File resourcePackDirectory(File localPackDirectory) {
        File shared = sharedRoot();
        return shared == null ? localPackDirectory : new File(shared, "resourcepacks");
    }
}
