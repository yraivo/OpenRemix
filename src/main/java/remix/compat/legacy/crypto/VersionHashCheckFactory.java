package remix.compat.legacy.crypto;

import remix.compat.legacy.codec.ValueConsumer;
import remix.compat.legacy.http.EmptyUriSyntaxException;
import remix.util.task.NamedValueHolder;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public final class VersionHashCheckFactory {
    public static AbstractVersionHashCheck method_0767c23f62cde9739def(
            SelfClassFileHasher selfClassFileHasher,
            NamedValueHolder namedValueHolder,
            ValueConsumer<NamedValueHolder> valueConsumer) {
        try {
            return new RemoteVersionHashCheck(
                    selfClassFileHasher.method_e0e3092a59fee58fae52());
        } catch (EmptyUriSyntaxException
                | IOException
                | NoSuchAlgorithmException class_5bf575c72420a3517cf4) {
            valueConsumer.a(namedValueHolder);
            return new FallbackVersionHashCheck();
        }
    }
}
