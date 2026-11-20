package net.minecraft.util;

import net.minecraft.util.HttpUtil;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.Vec3i;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Map.Entry;

public final class ResourcePackDownloadTask implements Runnable {

    int f;
    Map aaV;
    private static String[] packedTextFragments;
    String b;

    IProgressUpdate bFt;
    Proxy Bh;
    File aKr;

    public ResourcePackDownloadTask(
            IProgressUpdate bFtValue, String text, Proxy proxy, Map map, File file1, int i) {
        this.bFt = bFtValue;
        this.b = text;
        this.Bh = proxy;
        this.aaV = map;
        this.aKr = file1;
        this.f = i;
    }

    @Override
    public void run() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        InputStream inputstream = null;
        String textValue = text;
        DataOutputStream dataoutputstream = null;
        if (this.bFt != null) {
            this.bFt.resetProgressAndMessage("Downloading Resource Pack");
            this.bFt.displayLoadingString("Making Request...");
        }

        try {
            byte[] abyte = new byte[4096];
            URL url = new URL(this.b);
            URLConnection urlconnection = url.openConnection(this.Bh);
            float fx = 0.0F;
            float f1 = this.aaV.entrySet().size();

            for (java.util.Iterator<?> __recoveryIterator171_4547 =
                            (this.aaV.entrySet()).iterator();
                    (__recoveryIterator171_4547).hasNext(); ) {
                Entry entry = (Entry) (__recoveryIterator171_4547).next();
                urlconnection.setRequestProperty(
                        (String) entry.getKey(), (String) entry.getValue());
                if (this.bFt != null) {
                    this.bFt.setLoadingProgress((int) (++fx / f1 * 100.0F));
                }

                if (textValue == null) {
                    break;
                }
            }

            inputstream = urlconnection.getInputStream();
            f1 = urlconnection.getContentLength();
            int k = urlconnection.getContentLength();
            if (this.bFt != null) {
                this.bFt.displayLoadingString(
                        String.format("Downloading file (%.2f MB)...", f1 / 1000.0F / 1000.0F));
            }

            {
                if (this.aKr.exists()) {
                    long i = this.aKr.length();
                    if (i == k) {
                        if (this.bFt != null) {
                            this.bFt.setDoneWorking();
                        }

                        return;
                    }

                    HttpUtil.access$000()
                            .warn(
                                    "Deleting "
                                            + this.aKr
                                            + " as it does not match what we currently have ("
                                            + k
                                            + " vs our "
                                            + i
                                            + ").");
                    FileUtils.deleteQuietly(this.aKr);
                    if (!(textValue != null)) {

                        if (this.aKr.getParentFile() != null) {
                            this.aKr.getParentFile().mkdirs();
                        }
                    }
                } else {

                    if (this.aKr.getParentFile() != null) {
                        this.aKr.getParentFile().mkdirs();
                    }
                }
            }

            dataoutputstream = new DataOutputStream(new FileOutputStream(this.aKr));
            if (this.f > 0 && f1 > this.f) {
                if (this.bFt != null) {
                    this.bFt.setDoneWorking();
                }

                throw new IOException(
                        "Filesize is bigger than maximum allowed (file is "
                                + fx
                                + ", limit is "
                                + this.f
                                + packedTextFragments[0]);
            } else {

                int j;
                while ((j = inputstream.read(abyte)) >= 0) {
                    fx += j;
                    if (this.bFt != null) {
                        this.bFt.setLoadingProgress((int) (fx / f1 * 100.0F));
                    }

                    if (this.f > 0 && fx > this.f) {
                        if (this.bFt != null) {
                            this.bFt.setDoneWorking();
                        }

                        throw new IOException(
                                "Filesize was bigger than maximum allowed (got >= "
                                        + fx
                                        + ", limit was "
                                        + this.f
                                        + packedTextFragments[1]);
                    }

                    dataoutputstream.write(abyte, 0, j);
                    if (textValue == null) {
                        break;
                    }
                }

                if (this.bFt != null) {
                    this.bFt.setDoneWorking();
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputstream);
            IOUtils.closeQuietly(dataoutputstream);
        }
    }

    static {
        net.minecraft.util.ResourcePackDownloadTask.packedTextFragments = new String[4];
        net.minecraft.util.ResourcePackDownloadTask.packedTextFragments[0] = new String(")");
        net.minecraft.util.ResourcePackDownloadTask.packedTextFragments[1] = new String(")");
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }
}
