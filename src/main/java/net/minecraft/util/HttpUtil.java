package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import net.minecraft.server.MinecraftServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpUtil {
    private static Logger logger;
    public static ListeningExecutorService field_180193_a;
    private static String[] packedTextFragments;
    private static AtomicInteger downloadThreadsStarted;

    public static ListenableFuture downloadResourcePack(
            File file1, String text, Map map, int i, IProgressUpdate iProgressUpdate, Proxy proxy) {
        return net.minecraft.util.HttpUtil.field_180193_a.submit(
                new ResourcePackDownloadTask(iProgressUpdate, text, proxy, map, file1, i));
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public static int getSuitableLanPort() throws IOException {
        ServerSocket serversocket = null;

        int i;
        try {
            serversocket = new ServerSocket(0);
            i = serversocket.getLocalPort();
        } finally {
            try {
                if (serversocket != null) {
                    serversocket.close();
                }
            } catch (IOException ioexception) {
            }
        }

        return i;
    }

    public static String buildPostString(Map map) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        StringBuilder stringbuilder = new StringBuilder();
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_5388 = (map.entrySet()).iterator();
                (__recoveryIterator171_5388).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_5388).next();
            try {
                if (false)
                    throw (java.io.UnsupportedEncodingException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (stringbuilder.length() > 0) {
                    stringbuilder.append('&');
                }
            } catch (UnsupportedEncodingException unsupportedencodingexception2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(unsupportedencodingexception2));
            }

            try {
                stringbuilder.append(URLEncoder.encode((String) entry.getKey(), "UTF-8"));
            } catch (UnsupportedEncodingException unsupportedencodingexception1) {
                unsupportedencodingexception1.printStackTrace();
            }

            if (entry.getValue() != null) {
                stringbuilder.append('=');

                try {
                    stringbuilder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
                } catch (UnsupportedEncodingException unsupportedencodingexception) {
                    unsupportedencodingexception.printStackTrace();
                }
            }

            if (textValue == null) {
                break;
            }
        }

        return stringbuilder.toString();
    }

    public static Logger access$000() {
        return net.minecraft.util.HttpUtil.logger;
    }

    public static String postMap(URL url, Map map, boolean flag) {
        return post(url, buildPostString(map), flag);
    }

    public static String get(URL url) throws IOException {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
        httpurlconnection.setRequestMethod("GET");
        BufferedReader bufferedreader =
                new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
        StringBuilder stringbuilder = new StringBuilder();
        String textValue = text;

        String textValue2;
        while ((textValue2 = bufferedreader.readLine()) != null) {
            stringbuilder.append(textValue2);
            stringbuilder.append('\r');
            if (textValue == null) {
                break;
            }
        }

        bufferedreader.close();
        return stringbuilder.toString();
    }

    private static String post(URL url, String text, boolean flag) {
        String textValue = Vec3i.getLegacyCoordinateFlowMarker();

        try {
            Proxy proxy1;
            label46:
            {
                try {
                    if (MinecraftServer.getServer() == null) {
                        proxy1 = null;
                        break label46;
                    }
                } catch (Exception exception2) {
                    throw remix.compat.compiler.JvmThrowTypes.flow(a(exception2));
                }

                proxy1 = MinecraftServer.getServer().getServerProxy();
            }

            Proxy proxy = proxy1;
            if (proxy == null) {
                proxy = Proxy.NO_PROXY;
            }

            HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection(proxy);
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setRequestProperty(
                    "Content-Type", "application/x-www-form-urlencoded");
            httpurlconnection.setRequestProperty(
                    "Content-Length", packedTextFragments[0] + text.getBytes().length);
            httpurlconnection.setRequestProperty("Content-Language", "en-US");
            httpurlconnection.setUseCaches(false);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(true);
            DataOutputStream dataoutputstream =
                    new DataOutputStream(httpurlconnection.getOutputStream());
            dataoutputstream.writeBytes(text);
            dataoutputstream.flush();
            dataoutputstream.close();
            BufferedReader bufferedreader =
                    new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
            StringBuffer stringbuffer = new StringBuffer();

            String textValue2;
            while ((textValue2 = bufferedreader.readLine()) != null) {
                stringbuffer.append(textValue2);
                stringbuffer.append('\r');
                if (textValue == null) {
                    break;
                }
            }

            bufferedreader.close();
            return stringbuffer.toString();
        } catch (Exception exception3) {
            Exception exception = exception3;

            try {
                if (!flag) {
                    net.minecraft.util.HttpUtil.logger.error("Could not post to " + url, exception);
                }
            } catch (Exception exception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(exception1));
            }

            return packedTextFragments[1];
        }
    }

    static {
        net.minecraft.util.HttpUtil.packedTextFragments = new String[4];
        net.minecraft.util.HttpUtil.packedTextFragments[0] = new String("");
        net.minecraft.util.HttpUtil.packedTextFragments[1] = new String("");
        net.minecraft.util.HttpUtil.field_180193_a =
                MoreExecutors.listeningDecorator(
                        Executors.newCachedThreadPool(
                                new ThreadFactoryBuilder()
                                        .setDaemon(true)
                                        .setNameFormat("Downloader %d")
                                        .build()));
        net.minecraft.util.HttpUtil.downloadThreadsStarted = new AtomicInteger(0);
        net.minecraft.util.HttpUtil.logger = LogManager.getLogger();
    }
}
