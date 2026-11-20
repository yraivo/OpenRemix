package remix.compat.legacy.crypto;

import remix.compat.legacy.http.CodeSourceWrapper;
import remix.compat.legacy.http.EmptyUriSyntaxException;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public final class SelfClassFileHasher {
    private ClassFileHasher classFileHasher;

    public ClassFileHasher getClassFileHasher() {
        return this.classFileHasher;
    }

    public SelfClassFileHasher(ClassFileHasher bPSValue) {
        this.classFileHasher = bPSValue;
    }

    public String method_e0e3092a59fee58fae52()
            throws EmptyUriSyntaxException, IOException, NoSuchAlgorithmException {
        return this.classFileHasher.method_e773bbfafdde98105a2f(
                new File(new CodeSourceWrapper(this.classFileHasher.getClass()).aaA()));
    }
}
