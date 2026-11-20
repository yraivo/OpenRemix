package net.minecraft.client.resources;

import com.google.common.collect.Sets;

import org.apache.commons.io.filefilter.DirectoryFileFilter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class FolderResourcePack extends AbstractResourcePack {

    private static String d;

    @Override
    protected boolean hasResourceName(String text) {
        File candidate = remix.security.SafeResourcePaths.insideOrNull(this.resourcePackFile, text);
        return candidate != null && candidate.isFile();
    }

    static {
        d = "assets/";
    }

    public FolderResourcePack(File file1) {
        super(file1);
    }

    public Set getResourceDomains() {
        String text = I18n.getLegacyResourceFlowMarker();
        HashSet hashset = Sets.newHashSet();
        String textValue = text;
        File file1 = remix.security.SafeResourcePaths.insideOrNull(this.resourcePackFile, d);
        if (file1 != null && file1.isDirectory()) {
            for (File file2 : file1.listFiles((java.io.FileFilter) DirectoryFileFilter.DIRECTORY)) {
                {
                    String textValue2 = getRelativeName(file1, file2);
                    if (!textValue2.equals(textValue2.toLowerCase())) {
                        this.logNameNotLowercase(textValue2);
                        if (!(textValue != null)) {

                            hashset.add(textValue2.substring(0, textValue2.length() - 1));
                        }
                    } else {

                        hashset.add(textValue2.substring(0, textValue2.length() - 1));
                    }
                }

                if (textValue == null) {
                    break;
                }
            }
        }

        return hashset;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    @Override
    protected InputStream getInputStreamByName(String text) throws IOException {
        return new BufferedInputStream(
                new FileInputStream(
                        remix.security.SafeResourcePaths.inside(this.resourcePackFile, text)));
    }
}
