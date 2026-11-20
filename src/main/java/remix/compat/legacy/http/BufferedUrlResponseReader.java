package remix.compat.legacy.http;

import net.minecraft.entity.Entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;

public class BufferedUrlResponseReader implements UrlResponseReader {
    private static String[] packedTextFragments;
    private UrlStreamSelector urlStreamSelector;
    private static final String a = System.lineSeparator();

    // region String decoding support

    // endregion

    public String method_3ae681d954869fc1a1b0(URLConnection urlconnection, boolean flag)
            throws IOException {
        HttpResponseData.kM();

        StringBuilder stringbuilder = new StringBuilder();

        InputStream inputstream = this.urlStreamSelector.method_735770407f173dba28d4(urlconnection);
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
        Throwable throwable = null;

        while (true) {
            boolean flag1 = false /* VF: Semaphore variable */;

            try {
                flag1 = true;
                String text;
                if ((text = bufferedreader.readLine()) != null) {
                    stringbuilder.append(text);
                    if (!flag) {
                        continue;
                    }

                    stringbuilder.append(a);

                    continue;
                }

                flag1 = false;
                break;
            } catch (Throwable throwable3) {
                throwable = throwable3;
                throw throwable3;
            } finally {
                if (flag1) {
                    label80:
                    if (bufferedreader != null) {
                        if (throwable != null) {
                            try {
                                bufferedreader.close();
                                break label80;
                            } catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);

                                break label80;
                            }
                        }

                        bufferedreader.close();
                    }
                }
            }
        }

        if (bufferedreader != null) {
            if (throwable != null) {
                try {
                    bufferedreader.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
            } else {
                bufferedreader.close();
            }
        }

        byte b0 = 13;
        char c0 = (char) b0;
        String textValue =
                stringbuilder.toString().replace(String.valueOf(c0), packedTextFragments[0]);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            HttpResponseData.b(new String[3]);
        }

        return textValue;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.http.BufferedUrlResponseReader.packedTextFragments[0] = new String("");
    }

    // endregion

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public BufferedUrlResponseReader(UrlStreamSelector bOJValue) {
        this.urlStreamSelector = bOJValue;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }
}
