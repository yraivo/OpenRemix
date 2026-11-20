package remix.security;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

/** Resolve untrusted pack-relative names without escaping their explicitly selected root. */
public final class SafeResourcePaths {
    private SafeResourcePaths() {}

    public static File inside(File root, String name) throws IOException {
        if (root == null || name == null || name.indexOf('\0') >= 0)
            throw new IOException("Invalid relative resource path");
        String normalized = name.replace('\\', '/');
        if (normalized.startsWith("/") || normalized.indexOf(':') >= 0)
            throw new IOException("Absolute resource path is not permitted");
        for (String segment : normalized.split("/", -1)) {
            String stem = segment.split("\\.", 2)[0].toUpperCase(Locale.ROOT);
            if (stem.matches("CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9]"))
                throw new IOException("Device resource path is not permitted");
        }
        Path base = root.getCanonicalFile().toPath();
        File file = new File(root, normalized).getCanonicalFile();
        Path candidate = file.toPath();
        if (candidate.equals(base) || !candidate.startsWith(base))
            throw new IOException("Resource path escapes its root");
        return file;
    }

    public static File insideOrNull(File root, String name) {
        try {
            return inside(root, name);
        } catch (IOException | SecurityException failure) {
            return null;
        }
    }
}
