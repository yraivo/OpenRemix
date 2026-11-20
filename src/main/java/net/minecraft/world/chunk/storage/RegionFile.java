package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;

import net.minecraft.server.MinecraftServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class RegionFile {
    private int[] chunkTimestamps;

    private static byte[] emptySector;
    private int sizeDelta;
    private RandomAccessFile dataFile;
    private int[] offsets;
    private long lastModified;
    private File fileName;
    private static String te;
    private List sectorFree;

    private void setChunkTimestamp(int p171_0, int p171_1, int p171_2) throws IOException {
        this.chunkTimestamps[p171_0 + p171_1 * 32] = p171_2;
        this.dataFile.seek(4096 + (p171_0 + p171_1 * 32) * 4);
        this.dataFile.writeInt(p171_2);
    }

    public synchronized DataInputStream getChunkDataInputStream(int p171_0, int p171_1) {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Checked exception carried by the original JVM handler.
            if (this.outOfBounds(p171_0, p171_1)) {
                return null;
            }
        } catch (java.io.IOException ioexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.world.chunk.storage.RegionFile.a(ioexception1));
        }

        try {
            int k = this.getOffset(p171_0, p171_1);
            if (k == 0) {
                return null;
            } else {
                int l = k >> 8;
                int i1 = k & 0xFF;
                if (l + i1 > this.sectorFree.size()) {
                    return null;
                } else {
                    this.dataFile.seek(l * 4096);
                    int j1 = this.dataFile.readInt();
                    if (j1 > 4096 * i1) {
                        return null;
                    } else if (j1 <= 0) {
                        return null;
                    } else {
                        byte b0 = this.dataFile.readByte();
                        if (b0 == 1) {
                            byte[] abyte1 = new byte[j1 - 1];
                            this.dataFile.read(abyte1);
                            return new java.io.DataInputStream(
                                    new java.io.BufferedInputStream(
                                            new java.util.zip.GZIPInputStream(
                                                    new java.io.ByteArrayInputStream(abyte1))));
                        } else if (b0 == 2) {
                            byte[] abyte = new byte[j1 - 1];
                            this.dataFile.read(abyte);
                            return new java.io.DataInputStream(
                                    new java.io.BufferedInputStream(
                                            new java.util.zip.InflaterInputStream(
                                                    new java.io.ByteArrayInputStream(abyte))));
                        } else {
                            return null;
                        }
                    }
                }
            }
        } catch (java.io.IOException ioexception) {
            return null;
        }
    }

    public DataOutputStream getChunkDataOutputStream(int p171_0, int p171_1) {
        return this.outOfBounds(p171_0, p171_1)
                ? null
                : new java.io.DataOutputStream(
                        new java.util.zip.DeflaterOutputStream(
                                new net.minecraft.world.chunk.storage.RegionFileChunkBuffer(
                                        this, p171_0, p171_1)));
    }

    static {
        te = "rw";
        net.minecraft.world.chunk.storage.RegionFile.emptySector = new byte[4096];
    }

    private int getOffset(int p171_0, int p171_1) {
        return this.offsets[p171_0 + p171_1 * 32];
    }

    protected synchronized void write(int i, int j, byte[] abyte, int k) {
        String text = ExtendedBlockStorage.cJ();

        try {
            int l = this.getOffset(i, j);
            int i1 = l >> 8;
            int j1 = l & 0xFF;
            int k1 = (k + 5) / 4096 + 1;
            if (k1 >= 256) {
                return;
            }

            label106:
            {
                if (i1 != 0 && j1 == k1) {
                    this.write(i1, abyte, k);
                    if (text != null) {
                        break label106;
                    }
                }

                int l1 = 0;

                while (l1 < j1) {
                    this.sectorFree.set(i1 + l1, true);
                    l1++;
                    if (text == null) {
                        break;
                    }
                }

                l1 = this.sectorFree.indexOf(true);
                int i2 = 0;
                if (l1 != -1) {
                    int j2 = l1;

                    while (j2 < this.sectorFree.size()) {
                        label85:
                        {
                            if (i2 != 0) {
                                if ((Boolean) this.sectorFree.get(j2)) {
                                    i2++;
                                    if (text != null) {
                                        break label85;
                                    }
                                }

                                i2 = 0;
                                if (text != null) {
                                    break label85;
                                }
                            }

                            if ((Boolean) this.sectorFree.get(j2)) {
                                l1 = j2;
                                i2 = 1;
                            }
                        }

                        if (i2 >= k1 && text != null) {
                            break;
                        }

                        j2++;
                        if (text == null) {
                            break;
                        }
                    }
                }

                if (i2 >= k1) {
                    i1 = l1;
                    this.setOffset(i, j, l1 << 8 | k1);
                    int k2 = 0;

                    while (k2 < k1) {
                        this.sectorFree.set(i1 + k2, false);
                        k2++;
                        if (text == null) {
                            break;
                        }
                    }

                    this.write(i1, abyte, k);
                    if (text != null) {
                        break label106;
                    }
                }

                this.dataFile.seek(this.dataFile.length());
                i1 = this.sectorFree.size();
                int l2 = 0;

                while (l2 < k1) {
                    this.dataFile.write(net.minecraft.world.chunk.storage.RegionFile.emptySector);
                    this.sectorFree.add(false);
                    l2++;
                    if (text == null) {
                        break;
                    }
                }

                this.sizeDelta += 4096 * k1;
                this.write(i1, abyte, k);
                this.setOffset(i, j, i1 << 8 | k1);
            }

            long currentTimeMillis = MinecraftServer.getCurrentTimeMillis();

            this.setChunkTimestamp(i, j, (int) (currentTimeMillis / 1000L));
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    // region String decoding support

    // endregion

    private boolean outOfBounds(int p171_0, int p171_1) {
        return p171_0 < 0 || p171_0 >= 32 || p171_1 < 0 || p171_1 >= 32;
    }

    private void write(int p171_0, byte[] p171_1, int p171_2) throws IOException {
        this.dataFile.seek(p171_0 * 4096);
        this.dataFile.writeInt(p171_2 + 1);
        this.dataFile.writeByte(2);
        this.dataFile.write(p171_1, 0, p171_2);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void method_8d9b536bcdc9991c48fb() throws IOException {
        try {
            if (this.dataFile != null) {
                this.dataFile.close();
            }
        } catch (java.io.IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.world.chunk.storage.RegionFile.a(ioexception));
        }
    }

    // region String decoding support

    // endregion

    private void setOffset(int p171_0, int p171_1, int p171_2) throws IOException {
        this.offsets[p171_0 + p171_1 * 32] = p171_2;
        this.dataFile.seek((p171_0 + p171_1 * 32) * 4);
        this.dataFile.writeInt(p171_2);
    }

    public RegionFile(File file1) {
        super();
        String initialFlowMarker = ExtendedBlockStorage.cJ();

        String text = initialFlowMarker;

        this.offsets = new int[1024];
        String textValue = text;
        this.chunkTimestamps = new int[1024];
        this.fileName = file1;
        this.sizeDelta = 0;

        try {
            if (file1.exists()) {
                this.lastModified = file1.lastModified();
            }

            this.dataFile = new RandomAccessFile(file1, te);
            long l1 = this.dataFile.length();

            if (l1 < 4096L) {
                int i = 0;

                while (i < 1024) {
                    this.dataFile.writeInt(0);
                    i++;
                    if (textValue == null) {
                        break;
                    }
                }

                i = 0;

                while (i < 1024) {
                    this.dataFile.writeInt(0);
                    i++;
                    if (textValue == null) {
                        break;
                    }
                }

                this.sizeDelta += 8192;
            }

            long i2 = this.dataFile.length();

            long j2 = i2 & 4095L;

            if (j2 != 0L) {
                int i1 = 0;

                do {
                    long k2 = i1;
                    long l2 = this.dataFile.length();

                    if (k2 >= (l2 & 4095L)) {
                        break;
                    }

                    this.dataFile.write(0);
                    i1++;
                } while (textValue != null);
            }

            int j1 = (int) this.dataFile.length() / 4096;
            this.sectorFree = Lists.newArrayListWithCapacity(j1);
            int j = 0;

            while (j < j1) {
                this.sectorFree.add(true);
                j++;
                if (textValue == null) {
                    break;
                }
            }

            this.sectorFree.set(0, false);
            this.sectorFree.set(1, false);

            this.dataFile.seek(0L);
            j = 0;

            while (j < 1024) {
                int k = this.dataFile.readInt();
                this.offsets[j] = k;
                if (k != 0 && (k >> 8) + (k & 0xFF) <= this.sectorFree.size()) {
                    int l = 0;

                    while (l < (k & 0xFF)) {
                        this.sectorFree.set((k >> 8) + l, false);
                        l++;
                        if (textValue == null) {
                            break;
                        }
                    }
                }

                j++;
                if (textValue == null) {
                    break;
                }
            }

            j = 0;

            while (j < 1024) {
                int k1 = this.dataFile.readInt();
                this.chunkTimestamps[j] = k1;
                j++;
                if (textValue == null) {
                    break;
                }
            }
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
    }

    public boolean isChunkSaved(int p171_0, int p171_1) {
        return this.getOffset(p171_0, p171_1) != 0;
    }
}
