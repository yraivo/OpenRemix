package remix.util.io;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/** Local-only UI actions. Accepts typed paths and never constructs an OS shell command. */
public final class FileActions {
    private FileActions() {}

    public static void openDirectory(File directory) throws IOException {
        if (directory == null) throw new IOException("Missing directory");
        File real = directory.getCanonicalFile();
        if (!real.isDirectory()) throw new IOException("Not a directory");
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
            throw new IOException("Directory opening is unavailable on this desktop");
        Desktop.getDesktop().open(real);
    }

    public static boolean deleteWorld(File saves, String name) throws IOException {
        if (saves == null
                || name == null
                || name.length() == 0
                || name.equals(".")
                || name.equals("..")
                || name.indexOf('/') >= 0
                || name.indexOf('\\') >= 0
                || name.indexOf(':') >= 0
                || name.indexOf('\0') >= 0) throw new IOException("Invalid world folder name");
        final Path root = saves.toPath().toRealPath();
        final Path target = root.resolve(name).normalize();
        if (!root.equals(target.getParent()) || Files.isSymbolicLink(target))
            throw new IOException("World folder escapes the saves directory");
        if (!Files.exists(target, LinkOption.NOFOLLOW_LINKS)) return true;
        if (!Files.isDirectory(target, LinkOption.NOFOLLOW_LINKS)
                || !target.toRealPath().startsWith(root))
            throw new IOException("World folder is not an ordinary directory");
        Files.walkFileTree(
                target,
                EnumSet.noneOf(FileVisitOption.class),
                Integer.MAX_VALUE,
                new SimpleFileVisitor<Path>() {
                    private void ordinary(Path path) throws IOException {
                        if (!path.toRealPath().startsWith(root))
                            throw new IOException("Refusing an outside path");
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                            throws IOException {
                        if (Files.isSymbolicLink(dir))
                            throw new IOException("Refusing a directory link");
                        ordinary(dir);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException {
                        if (!attrs.isSymbolicLink()) {
                            if (attrs.isOther() && Files.isDirectory(file))
                                throw new IOException("Refusing a directory junction");
                            ordinary(file);
                        }
                        // A symbolic link itself may be deleted, but its target is never visited.
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException failure)
                            throws IOException {
                        if (failure != null) throw failure;
                        ordinary(dir);
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
        return !Files.exists(target, LinkOption.NOFOLLOW_LINKS);
    }
}
