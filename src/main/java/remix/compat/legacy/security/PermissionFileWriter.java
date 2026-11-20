package remix.compat.legacy.security;

import remix.compat.legacy.auth.ClientIdentityToken;
import remix.compat.legacy.auth.LicenseSession;
import remix.compat.legacy.codec.CipherKeySelector;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PermissionFileWriter {
    private LicenseSession licenseSession;
    private static String permissionFileSuffix;

    private static String[] packedTextFragments;
    private File file;

    public PermissionFileWriter(LicenseSession bTKValue, File file1) {
        this.licenseSession = bTKValue;
        this.file = file1;
    }

    static {
        remix.compat.legacy.security.PermissionFileWriter.packedTextFragments = new String[4];
        remix.compat.legacy.security.PermissionFileWriter.packedTextFragments[0] = new String("-");
        remix.compat.legacy.security.PermissionFileWriter.packedTextFragments[1] = new String("-");
        remix.compat.legacy.security.PermissionFileWriter.packedTextFragments[2] = new String("-");
        remix.compat.legacy.security.PermissionFileWriter.permissionFileSuffix =
                new String(".permission");
    }

    public void writePermissionFiles() throws Exception {
        boolean flag1 = ResourcePermission.isLegacyPermissionFlow();
        Iterator iterator = ((Map) this.licenseSession.Zy().Qu()).entrySet().iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            String key = (String) entry.getKey();
            ResourcePermission value = (ResourcePermission) entry.getValue();
            File file1 =
                    new File(
                            this.file.getPath()
                                    + File.separator
                                    + key
                                    + packedTextFragments[0]
                                    + value.getPermissionName()
                                    + remix.compat.legacy.security.PermissionFileWriter
                                            .permissionFileSuffix);

            try {
                file1.deleteOnExit();
                if (file1.exists()) {
                    throw new FileAlreadyExistsException(new String("Invalid permission files"));
                }
            } catch (Exception exception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(exception);
            }

            ClientIdentityToken clientIdentityToken = this.licenseSession.getClientIdentityToken();
            CipherKeySelector cipherKeySelector = clientIdentityToken.getCipherKeySelector();
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append(clientIdentityToken.hashCode());
            stringbuilder.append(packedTextFragments[1]);
            stringbuilder.append(value.hashCode());
            stringbuilder.append(packedTextFragments[2]);
            stringbuilder.append(cipherKeySelector.hashCode());
            byte[] abyte = stringbuilder.toString().getBytes(StandardCharsets.UTF_8);
            String text = clientIdentityToken.getCipherKeySelector().method_c60693f457aa70be7160();
            SecretKeySpec secretkeyspec =
                    new SecretKeySpec(
                            clientIdentityToken
                                    .method_c60693f457aa70be7160()
                                    .substring(0, 16)
                                    .getBytes(),
                            text);
            Cipher cipher = Cipher.getInstance(text);
            cipher.init(1, secretkeyspec);
            byte[] abyte1 = cipher.doFinal(abyte);
            FileOutputStream fileoutputstream = new FileOutputStream(file1);
            fileoutputstream.write(abyte1);
            fileoutputstream.close();
            if (flag) {
                break;
            }
        }
    }
}
