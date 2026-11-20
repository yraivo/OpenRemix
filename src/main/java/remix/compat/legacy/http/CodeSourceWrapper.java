package remix.compat.legacy.http;

import net.minecraft.entity.Entity;

import remix.compat.legacy.crypto.ClassFileHasher;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;

public final class CodeSourceWrapper {
    private CodeSource codeSource;

    public CodeSourceWrapper(CodeSource codesource) {
        this.codeSource = codesource;
    }

    public CodeSource getCodeSource() {
        return this.codeSource;
    }

    public CodeSourceWrapper(Class<?> oclass) {
        this(oclass, ClassFileHasher.method_d87039b07bd6961907f2());
    }

    private CodeSourceWrapper(Class<?> oclass, boolean initialFlowFlag) {
        this(new ProtectionDomainWrapper(oclass.getProtectionDomain()).aaE().getCodeSource());

        boolean flag1 = initialFlowFlag;

        boolean flag = flag1;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ClassFileHasher.method_a8745b073fa8adfa1857(!flag);
        }
    }

    public URI aaA() throws EmptyUriSyntaxException {
        try {
            return this.aaB().toURI();
        } catch (URISyntaxException urisyntaxexception) {
            throw new EmptyUriSyntaxException();
        }
    }

    public URL aaB() {
        return this.codeSource.getLocation();
    }

    public String d() {
        return this.aaB().getPath();
    }
}
