package net.minecraft.client.resources;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileResourcePack extends AbstractResourcePack implements Closeable {
    public static Splitter entryNameSplitter;

    private static String h;
    private ZipFile resourcePackZipFile;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private ZipFile getResourcePackZipFile() throws IOException {
        if (this.resourcePackZipFile == null) {
            this.resourcePackZipFile = new ZipFile(this.resourcePackFile);
        }

        return this.resourcePackZipFile;
    }

    public Set getResourceDomains() {
        String text = I18n.getLegacyResourceFlowMarker();

        ZipFile zipfile;
        try {
            zipfile = this.getResourcePackZipFile();
        } catch (IOException ioexception) {
            return Collections.emptySet();
        }

        Enumeration enumeration = zipfile.entries();
        HashSet hashset = Sets.newHashSet();

        while (enumeration.hasMoreElements()) {
            ZipEntry zipentry = (ZipEntry) enumeration.nextElement();
            String name = zipentry.getName();
            if (name.startsWith(h)) {
                ArrayList arrayList =
                        Lists.newArrayList(net.minecraft.client.resources.FileResourcePack.entryNameSplitter.split(name));
                if (arrayList.size() > 1) {
                    String textValue = (String) arrayList.get(1);
                    if (!textValue.equals(textValue.toLowerCase())) {
                        this.logNameNotLowercase(textValue);
                        if (!(text != null)) {

                            hashset.add(textValue);
                        }
                    } else {

                        hashset.add(textValue);
                    }
                }
            }

            if (text == null) {
                break;
            }
        }

        return hashset;
    }

    @Override
    public void close() throws IOException {
        if (this.resourcePackZipFile != null) {
            this.resourcePackZipFile.close();
            this.resourcePackZipFile = null;
        }
    }

    private static IOException method_02836a3a008661c0ef77(IOException ioexception) {
        return ioexception;
    }

    public boolean hasResourceName(String text) {
        try {
            return this.getResourcePackZipFile().getEntry(text) != null;
        } catch (IOException ioexception) {
            return false;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    static {
        h = "assets/";
        net.minecraft.client.resources.FileResourcePack.entryNameSplitter = Splitter.on('/').omitEmptyStrings().limit(3);
    }

    public FileResourcePack(File file1) {
        super(file1);
    }

    protected InputStream getInputStreamByName(String text) throws IOException {
        ZipFile zipfile = this.getResourcePackZipFile();
        ZipEntry zipentry = zipfile.getEntry(text);
        if (zipentry == null) {
            throw new ResourcePackFileNotFoundException(this.resourcePackFile, text);
        } else {
            return zipfile.getInputStream(zipentry);
        }
    }
}
