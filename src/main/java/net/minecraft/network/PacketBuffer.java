package net.minecraft.network;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.ByteBufProcessor;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCounted;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.IChatComponentSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.UUID;

public class PacketBuffer extends ByteBuf {
    // Compatibility with the reviewed Netty 4.1 runtime; preserve the wrapper's existing delegation
    // style.
    @Override
    public ByteBuf touch() {
        return this.buf.touch();
    }

    @Override
    public ByteBuf touch(Object hint) {
        return this.buf.touch(hint);
    }

    @Override
    public int forEachByte(io.netty.util.ByteProcessor p) {
        return this.buf.forEachByte(p);
    }

    @Override
    public int forEachByte(int i, int n, io.netty.util.ByteProcessor p) {
        return this.buf.forEachByte(i, n, p);
    }

    @Override
    public int forEachByteDesc(io.netty.util.ByteProcessor p) {
        return this.buf.forEachByteDesc(p);
    }

    @Override
    public int forEachByteDesc(int i, int n, io.netty.util.ByteProcessor p) {
        return this.buf.forEachByteDesc(i, n, p);
    }

    @Override
    public boolean isReadOnly() {
        return this.buf.isReadOnly();
    }

    @Override
    public io.netty.buffer.ByteBuf asReadOnly() {
        return this.buf.asReadOnly();
    }

    @Override
    public short getShortLE(int p0) {
        return this.buf.getShortLE(p0);
    }

    @Override
    public int getUnsignedShortLE(int p0) {
        return this.buf.getUnsignedShortLE(p0);
    }

    @Override
    public int getMediumLE(int p0) {
        return this.buf.getMediumLE(p0);
    }

    @Override
    public int getUnsignedMediumLE(int p0) {
        return this.buf.getUnsignedMediumLE(p0);
    }

    @Override
    public int getIntLE(int p0) {
        return this.buf.getIntLE(p0);
    }

    @Override
    public long getUnsignedIntLE(int p0) {
        return this.buf.getUnsignedIntLE(p0);
    }

    @Override
    public long getLongLE(int p0) {
        return this.buf.getLongLE(p0);
    }

    @Override
    public int getBytes(int p0, java.nio.channels.FileChannel p1, long p2, int p3)
            throws java.io.IOException {
        return this.buf.getBytes(p0, p1, p2, p3);
    }

    @Override
    public java.lang.CharSequence getCharSequence(int p0, int p1, java.nio.charset.Charset p2) {
        return this.buf.getCharSequence(p0, p1, p2);
    }

    @Override
    public io.netty.buffer.ByteBuf setShortLE(int p0, int p1) {
        return this.buf.setShortLE(p0, p1);
    }

    @Override
    public io.netty.buffer.ByteBuf setMediumLE(int p0, int p1) {
        return this.buf.setMediumLE(p0, p1);
    }

    @Override
    public io.netty.buffer.ByteBuf setIntLE(int p0, int p1) {
        return this.buf.setIntLE(p0, p1);
    }

    @Override
    public io.netty.buffer.ByteBuf setLongLE(int p0, long p1) {
        return this.buf.setLongLE(p0, p1);
    }

    @Override
    public int setBytes(int p0, java.nio.channels.FileChannel p1, long p2, int p3)
            throws java.io.IOException {
        return this.buf.setBytes(p0, p1, p2, p3);
    }

    @Override
    public int setCharSequence(int p0, java.lang.CharSequence p1, java.nio.charset.Charset p2) {
        return this.buf.setCharSequence(p0, p1, p2);
    }

    @Override
    public short readShortLE() {
        return this.buf.readShortLE();
    }

    @Override
    public int readUnsignedShortLE() {
        return this.buf.readUnsignedShortLE();
    }

    @Override
    public int readMediumLE() {
        return this.buf.readMediumLE();
    }

    @Override
    public int readUnsignedMediumLE() {
        return this.buf.readUnsignedMediumLE();
    }

    @Override
    public int readIntLE() {
        return this.buf.readIntLE();
    }

    @Override
    public long readUnsignedIntLE() {
        return this.buf.readUnsignedIntLE();
    }

    @Override
    public long readLongLE() {
        return this.buf.readLongLE();
    }

    @Override
    public io.netty.buffer.ByteBuf readRetainedSlice(int p0) {
        return this.buf.readRetainedSlice(p0);
    }

    @Override
    public java.lang.CharSequence readCharSequence(int p0, java.nio.charset.Charset p1) {
        return this.buf.readCharSequence(p0, p1);
    }

    @Override
    public int readBytes(java.nio.channels.FileChannel p0, long p1, int p2)
            throws java.io.IOException {
        return this.buf.readBytes(p0, p1, p2);
    }

    @Override
    public io.netty.buffer.ByteBuf writeShortLE(int p0) {
        return this.buf.writeShortLE(p0);
    }

    @Override
    public io.netty.buffer.ByteBuf writeMediumLE(int p0) {
        return this.buf.writeMediumLE(p0);
    }

    @Override
    public io.netty.buffer.ByteBuf writeIntLE(int p0) {
        return this.buf.writeIntLE(p0);
    }

    @Override
    public io.netty.buffer.ByteBuf writeLongLE(long p0) {
        return this.buf.writeLongLE(p0);
    }

    @Override
    public int writeBytes(java.nio.channels.FileChannel p0, long p1, int p2)
            throws java.io.IOException {
        return this.buf.writeBytes(p0, p1, p2);
    }

    @Override
    public int writeCharSequence(java.lang.CharSequence p0, java.nio.charset.Charset p1) {
        return this.buf.writeCharSequence(p0, p1);
    }

    @Override
    public io.netty.buffer.ByteBuf retainedSlice() {
        return this.buf.retainedSlice();
    }

    @Override
    public io.netty.buffer.ByteBuf retainedSlice(int p0, int p1) {
        return this.buf.retainedSlice(p0, p1);
    }

    @Override
    public io.netty.buffer.ByteBuf retainedDuplicate() {
        return this.buf.retainedDuplicate();
    }

    private static String[] packedTextFragments;
    private ByteBuf buf;

    private static String legacyPacketBufferFlowMarker;

    public ByteBuf setBytes(int p171_0, ByteBuf p171_1) {
        return this.buf.setBytes(p171_0, p171_1);
    }

    public ByteBuf readBytes(ByteBuf p171_0, int p171_1) {
        return this.buf.readBytes(p171_0, p171_1);
    }

    public ByteBuf writeShort(int p171_0) {
        return this.buf.writeShort(p171_0);
    }

    public ByteBuffer nioBuffer(int p171_0, int p171_1) {
        return this.buf.nioBuffer(p171_0, p171_1);
    }

    public ByteBuf getBytes(int p171_0, ByteBuf p171_1, int p171_2, int p171_3) {
        return this.buf.getBytes(p171_0, p171_1, p171_2, p171_3);
    }

    public ByteBuf writeZero(int p171_0) {
        return this.buf.writeZero(p171_0);
    }

    public ByteBuf readSlice(int p171_0) {
        return this.buf.readSlice(p171_0);
    }

    public short readUnsignedByte() {
        return this.buf.readUnsignedByte();
    }

    public int bytesBefore(byte p171_0) {
        return this.buf.bytesBefore(p171_0);
    }

    public int readVarIntFromBuffer() {
        int i = 0;
        int j = 0;

        byte b0;
        do {
            b0 = this.readByte();
            i |= (b0 & 127) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((b0 & 128) == 128);

        return i;
    }

    public int readBytes(GatheringByteChannel gatheringByteChannel, int p171_1) throws IOException {
        return this.buf.readBytes(gatheringByteChannel, p171_1);
    }

    public int indexOf(int p171_0, int p171_1, byte p171_2) {
        return this.buf.indexOf(p171_0, p171_1, p171_2);
    }

    public void writeBlockPos(BlockPos blockPos) {
        this.writeLong(blockPos.toLong());
    }

    public int getInt(int p171_0) {
        return this.buf.getInt(p171_0);
    }

    public ByteBuf retain() {
        return this.buf.retain();
    }

    public ByteBuf readBytes(byte[] p171_0, int p171_1, int p171_2) {
        return this.buf.readBytes(p171_0, p171_1, p171_2);
    }

    public ByteBuf markWriterIndex() {
        return this.buf.markWriterIndex();
    }

    public ByteBuf writeByte(int p171_0) {
        return this.buf.writeByte(p171_0);
    }

    public boolean isWritable() {
        return this.buf.isWritable();
    }

    public int getMedium(int p171_0) {
        return this.buf.getMedium(p171_0);
    }

    public boolean equals(Object p171_0) {
        return this.buf.equals(p171_0);
    }

    public int ensureWritable(int p171_0, boolean p171_1) {
        return this.buf.ensureWritable(p171_0, p171_1);
    }

    public long getUnsignedInt(int p171_0) {
        return this.buf.getUnsignedInt(p171_0);
    }

    public CompoundTag readNBTTagCompoundFromBuffer() throws IOException {
        int i = this.readerIndex();
        byte b0 = this.readByte();
        if (b0 == 0) {
            return null;
        }

        this.readerIndex(i);
        ByteBufInputStream bytebufinputstream = new ByteBufInputStream(this);

        return CompressedStreamTools.read(bytebufinputstream, new NBTSizeTracker(2097152L));
    }

    public ByteBuf order(ByteOrder byteOrder) {
        return this.buf.order(byteOrder);
    }

    public ByteBuf markReaderIndex() {
        return this.buf.markReaderIndex();
    }

    public boolean isDirect() {
        return this.buf.isDirect();
    }

    public ByteBuf resetWriterIndex() {
        return this.buf.resetWriterIndex();
    }

    public ByteBuf writeBytes(ByteBuf p171_0, int p171_1) {
        return this.buf.writeBytes(p171_0, p171_1);
    }

    public PacketBuffer writeString(String text) {
        byte[] abyte = text.getBytes(Charsets.UTF_8);
        if (abyte.length > 32767) {
            throw new EncoderException(
                    "String too big (was "
                            + text.length()
                            + " bytes encoded, max "
                            + 32767
                            + packedTextFragments[2]);
        }

        this.writeVarIntToBuffer(abyte.length);
        this.writeBytes(abyte);
        return this;
    }

    public int forEachByte(ByteBufProcessor p171_0) {
        return this.buf.forEachByte(p171_0);
    }

    public static int getVarIntSize(int value) {
        for (int bytes = 1; bytes < 5; bytes++) {
            if ((value & (-1 << (bytes * 7))) == 0) {
                return bytes;
            }
        }
        return 5;
    }

    public ByteBuf slice() {
        return this.buf.slice();
    }

    public ByteBuf readBytes(byte[] p171_0) {
        return this.buf.readBytes(p171_0);
    }

    public int readInt() {
        return this.buf.readInt();
    }

    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        return this.buf.readBytes(byteBuffer);
    }

    public ByteBuf writeBytes(byte[] p171_0) {
        return this.buf.writeBytes(p171_0);
    }

    public boolean hasMemoryAddress() {
        return this.buf.hasMemoryAddress();
    }

    public void writeByteArray(byte[] p171_0) {
        this.writeVarIntToBuffer(p171_0.length);
        this.writeBytes(p171_0);
    }

    public long getLong(int p171_0) {
        return this.buf.getLong(p171_0);
    }

    public ByteBuf writeBoolean(boolean p171_0) {
        return this.buf.writeBoolean(p171_0);
    }

    public float readFloat() {
        return this.buf.readFloat();
    }

    public ByteBuf copy() {
        return this.buf.copy();
    }

    public int readMedium() {
        return this.buf.readMedium();
    }

    public ByteBuf setBytes(int p171_0, ByteBuf p171_1, int p171_2) {
        return this.buf.setBytes(p171_0, p171_1, p171_2);
    }

    public ByteBuffer nioBuffer() {
        return this.buf.nioBuffer();
    }

    public ByteBuf slice(int p171_0, int p171_1) {
        return this.buf.slice(p171_0, p171_1);
    }

    public ByteBuf copy(int p171_0, int p171_1) {
        return this.buf.copy(p171_0, p171_1);
    }

    public byte getByte(int p171_0) {
        return this.buf.getByte(p171_0);
    }

    public int jvm171_compareTo_99f205c1af0d(Object object) {
        return this.compareTo((ByteBuf) object);
    }

    public int readableBytes() {
        return this.buf.readableBytes();
    }

    public int getBytes(int p171_0, GatheringByteChannel gatheringByteChannel, int p171_2)
            throws IOException {
        return this.buf.getBytes(p171_0, gatheringByteChannel, p171_2);
    }

    public ByteBufAllocator alloc() {
        return this.buf.alloc();
    }

    public byte[] readByteArray() {
        byte[] abyte = new byte[this.readVarIntFromBuffer()];
        this.readBytes(abyte);
        return abyte;
    }

    public ReferenceCounted retainReferenceCounted(int i) {
        return this.retain(i);
    }

    public ByteBuf getBytes(int p171_0, ByteBuf p171_1, int p171_2) {
        return this.buf.getBytes(p171_0, p171_1, p171_2);
    }

    public ByteBuf setBytes(int p171_0, ByteBuf p171_1, int p171_2, int p171_3) {
        return this.buf.setBytes(p171_0, p171_1, p171_2, p171_3);
    }

    public long readUnsignedInt() {
        return this.buf.readUnsignedInt();
    }

    public ChatComponent readChatComponent() {
        return IChatComponentSerializer.jsonToComponent(this.readStringFromBuffer(32767));
    }

    public long readVarLong() {

        long i = 0L;
        int j = 0;

        byte b0;
        do {
            b0 = this.readByte();
            i |= (long) (b0 & 127) << j++ * 7;
            if (j > 10) {
                throw new RuntimeException("VarLong too big");
            }
        } while ((b0 & 128) == 128);

        return i;
    }

    public ByteBuf setZero(int p171_0, int p171_1) {
        return this.buf.setZero(p171_0, p171_1);
    }

    public ByteBuf writeBytes(byte[] p171_0, int p171_1, int p171_2) {
        return this.buf.writeBytes(p171_0, p171_1, p171_2);
    }

    public void writeItemStackToBuffer(ItemStack itemStack) {
        String text = getLegacyPacketBufferFlowMarker();
        if (itemStack == null) {
            this.writeShort(-1);
            if (text != null) {
                return;
            }
        }

        this.writeShort(Item.getIdFromItem(itemStack.getItem()));
        this.writeByte(itemStack.stackSize);
        this.writeShort(itemStack.getItemDamage());
        CompoundTag compoundTag = null;
        if (itemStack.getItem().isDamageable() || itemStack.getItem().aR()) {
            compoundTag = itemStack.getTag();
        }

        this.writeNBTTagCompoundToBuffer(compoundTag);
    }

    public void writeUuid(UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }

    public ByteBuf setMedium(int p171_0, int p171_1) {
        return this.buf.setMedium(p171_0, p171_1);
    }

    // region Static initialization and string tables
    static {
        net.minecraft.network.PacketBuffer.packedTextFragments = new String[6];
        net.minecraft.network.PacketBuffer.packedTextFragments[0] = new String(")");
        net.minecraft.network.PacketBuffer.packedTextFragments[1] = new String(")");
        net.minecraft.network.PacketBuffer.packedTextFragments[2] = new String(")");
        net.minecraft.network.PacketBuffer.packedTextFragments[4] = new String("QDzMPc");
        String s2 = packedTextFragments[4];
        setLegacyPacketBufferFlowMarker(s2);
    }

    // endregion

    public ByteBuf getBytes(int p171_0, OutputStream outputStream, int p171_2) throws IOException {
        return this.buf.getBytes(p171_0, outputStream, p171_2);
    }

    public boolean hasArray() {
        return this.buf.hasArray();
    }

    public String toString(Charset charset) {
        return this.buf.toString(charset);
    }

    public ByteBuf discardSomeReadBytes() {
        return this.buf.discardSomeReadBytes();
    }

    public boolean readBoolean() {
        return this.buf.readBoolean();
    }

    public void writeVarLong(long i) {
        String text = getLegacyPacketBufferFlowMarker();

        do {

            long j = i & -128L;

            if (j == 0L) {
                break;
            }

            this.writeByte((int) (i & 127L) | 128);
            i >>>= 7;
        } while (text != null);

        this.writeByte((int) i);
    }

    public char getChar(int p171_0) {
        return this.buf.getChar(p171_0);
    }

    public int setBytes(int p171_0, ScatteringByteChannel scatteringByteChannel, int p171_2)
            throws IOException {
        return this.buf.setBytes(p171_0, scatteringByteChannel, p171_2);
    }

    public ByteBuf writeChar(int p171_0) {
        return this.buf.writeChar(p171_0);
    }

    public boolean isReadable(int p171_0) {
        return this.buf.isReadable(p171_0);
    }

    // region String decoding support

    // endregion

    public int bytesBefore(int p171_0, byte p171_1) {
        return this.buf.bytesBefore(p171_0, p171_1);
    }

    public int readUnsignedShort() {
        return this.buf.readUnsignedShort();
    }

    public ByteBuffer[] nioBuffers() {
        return this.buf.nioBuffers();
    }

    public UUID readUuid() {
        return new java.util.UUID(this.readLong(), this.readLong());
    }

    public void writeNBTTagCompoundToBuffer(CompoundTag compoundTag) {
        String text = getLegacyPacketBufferFlowMarker();
        if (compoundTag == null) {
            this.writeByte(0);
            if (text != null) {
                return;
            }
        }

        try {
            CompressedStreamTools.write(compoundTag, new ByteBufOutputStream(this));
        } catch (IOException ioexception) {
            throw new EncoderException(ioexception);
        }
    }

    public char readChar() {
        return this.buf.readChar();
    }

    public int forEachByteDesc(int p171_0, int p171_1, ByteBufProcessor p171_2) {
        return this.buf.forEachByteDesc(p171_0, p171_1, p171_2);
    }

    public ByteBuf duplicate() {
        return this.buf.duplicate();
    }

    public long memoryAddress() {
        return this.buf.memoryAddress();
    }

    public Enum readEnumValue(Class type) {
        return ((java.lang.Enum[]) type.getEnumConstants())[this.readVarIntFromBuffer()];
    }

    public ReferenceCounted retainReferenceCounted() {
        return this.retain();
    }

    public ByteBuf setChar(int p171_0, int p171_1) {
        return this.buf.setChar(p171_0, p171_1);
    }

    public boolean release(int p171_0) {
        return this.buf.release(p171_0);
    }

    public ByteBuf writeFloat(float p171_0) {
        return this.buf.writeFloat(p171_0);
    }

    public int writeBytes(InputStream inputStream, int p171_1) throws IOException {
        return this.buf.writeBytes(inputStream, p171_1);
    }

    public ByteBuf writeLong(long p171_0) {
        return this.buf.writeLong(p171_0);
    }

    public ByteBuf retain(int increment) {
        return this.buf.retain(increment);
    }

    public String toString() {
        return this.buf.toString();
    }

    public boolean getBoolean(int p171_0) {
        return this.buf.getBoolean(p171_0);
    }

    public ByteBuf writeMedium(int p171_0) {
        return this.buf.writeMedium(p171_0);
    }

    public ByteBuf writeBytes(ByteBuf p171_0, int p171_1, int p171_2) {
        return this.buf.writeBytes(p171_0, p171_1, p171_2);
    }

    public ByteBuf resetReaderIndex() {
        return this.buf.resetReaderIndex();
    }

    public ByteBuf setShort(int p171_0, int p171_1) {
        return this.buf.setShort(p171_0, p171_1);
    }

    public int compareTo(ByteBuf p171_0) {
        return this.buf.compareTo(p171_0);
    }

    public int capacity() {
        return this.buf.capacity();
    }

    public int refCnt() {
        return this.buf.refCnt();
    }

    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int p171_1)
            throws IOException {
        return this.buf.writeBytes(scatteringByteChannel, p171_1);
    }

    public double getDouble(int p171_0) {
        return this.buf.getDouble(p171_0);
    }

    public ByteBuf clear() {
        return this.buf.clear();
    }

    public ByteBuf writeInt(int p171_0) {
        return this.buf.writeInt(p171_0);
    }

    public ByteBuf setLong(int p171_0, long p171_1) {
        return this.buf.setLong(p171_0, p171_1);
    }

    public ByteBuffer internalNioBuffer(int p171_0, int p171_1) {
        return this.buf.internalNioBuffer(p171_0, p171_1);
    }

    public ByteBuf setBytes(int p171_0, byte[] p171_1) {
        return this.buf.setBytes(p171_0, p171_1);
    }

    public int readUnsignedMedium() {
        return this.buf.readUnsignedMedium();
    }

    public ByteBuf capacity(int p171_0) {
        return this.buf.capacity(p171_0);
    }

    public int maxWritableBytes() {
        return this.buf.maxWritableBytes();
    }

    public int readerIndex() {
        return this.buf.readerIndex();
    }

    public int writableBytes() {
        return this.buf.writableBytes();
    }

    public ByteBuf writerIndex(int p171_0) {
        return this.buf.writerIndex(p171_0);
    }

    public ByteBuf setBytes(int p171_0, byte[] p171_1, int p171_2, int p171_3) {
        return this.buf.setBytes(p171_0, p171_1, p171_2, p171_3);
    }

    public int maxCapacity() {
        return this.buf.maxCapacity();
    }

    public ByteBuf skipBytes(int p171_0) {
        return this.buf.skipBytes(p171_0);
    }

    public int forEachByte(int p171_0, int p171_1, ByteBufProcessor p171_2) {
        return this.buf.forEachByte(p171_0, p171_1, p171_2);
    }

    public double readDouble() {
        return this.buf.readDouble();
    }

    public ItemStack readItemStackFromBuffer() throws IOException {
        ItemStack itemStack = null;
        short short1 = this.readShort();
        if (short1 >= 0) {
            byte b0 = this.readByte();
            short short2 = this.readShort();
            itemStack = new ItemStack(Item.getItemById(short1), b0, short2);
            itemStack.setTag(this.readNBTTagCompoundFromBuffer());
        }

        return itemStack;
    }

    public int arrayOffset() {
        return this.buf.arrayOffset();
    }

    public byte readByte() {
        return this.buf.readByte();
    }

    public BlockPos readBlockPos() {
        return BlockPos.fromLong(this.readLong());
    }

    public short readShort() {
        return this.buf.readShort();
    }

    public int getUnsignedShort(int p171_0) {
        return this.buf.getUnsignedShort(p171_0);
    }

    public ByteBuf unwrap() {
        return this.buf.unwrap();
    }

    public ByteBuf getBytes(int p171_0, byte[] p171_1) {
        return this.buf.getBytes(p171_0, p171_1);
    }

    public ByteBuf setIndex(int p171_0, int p171_1) {
        return this.buf.setIndex(p171_0, p171_1);
    }

    public ByteBuf writeBytes(ByteBuf p171_0) {
        return this.buf.writeBytes(p171_0);
    }

    public ByteBuf readerIndex(int p171_0) {
        return this.buf.readerIndex(p171_0);
    }

    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        return this.buf.writeBytes(byteBuffer);
    }

    public boolean isWritable(int p171_0) {
        return this.buf.isWritable(p171_0);
    }

    public static void setLegacyPacketBufferFlowMarker(String text) {
        net.minecraft.network.PacketBuffer.legacyPacketBufferFlowMarker = text;
    }

    public ByteBuf setBytes(int p171_0, ByteBuffer byteBuffer) {
        return this.buf.setBytes(p171_0, byteBuffer);
    }

    public int setBytes(int p171_0, InputStream inputStream, int p171_2) throws IOException {
        return this.buf.setBytes(p171_0, inputStream, p171_2);
    }

    public ByteBuf writeDouble(double p171_0) {
        return this.buf.writeDouble(p171_0);
    }

    public boolean isReadable() {
        return this.buf.isReadable();
    }

    public float getFloat(int p171_0) {
        return this.buf.getFloat(p171_0);
    }

    public ByteOrder order() {
        return this.buf.order();
    }

    public short getUnsignedByte(int p171_0) {
        return this.buf.getUnsignedByte(p171_0);
    }

    public long readLong() {
        return this.buf.readLong();
    }

    public ByteBuf setInt(int p171_0, int p171_1) {
        return this.buf.setInt(p171_0, p171_1);
    }

    public int hashCode() {
        return this.buf.hashCode();
    }

    public int bytesBefore(int p171_0, int p171_1, byte p171_2) {
        return this.buf.bytesBefore(p171_0, p171_1, p171_2);
    }

    public ByteBuf readBytes(int p171_0) {
        return this.buf.readBytes(p171_0);
    }

    public ByteBuf setBoolean(int p171_0, boolean p171_1) {
        return this.buf.setBoolean(p171_0, p171_1);
    }

    public void writeChatComponent(ChatComponent chatComponent) {
        this.writeString(IChatComponentSerializer.componentToJson(chatComponent));
    }

    public ByteBuf discardReadBytes() {
        return this.buf.discardReadBytes();
    }

    public ByteBuf setByte(int p171_0, int p171_1) {
        return this.buf.setByte(p171_0, p171_1);
    }

    public String toString(int p171_0, int p171_1, Charset charset) {
        return this.buf.toString(p171_0, p171_1, charset);
    }

    public ByteBuf readBytes(OutputStream outputStream, int p171_1) throws IOException {
        return this.buf.readBytes(outputStream, p171_1);
    }

    public void writeVarIntToBuffer(int value) {
        while ((value & ~0x7F) != 0) {
            this.writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }
        this.writeByte(value);
    }

    public ByteBuf setDouble(int p171_0, double p171_1) {
        return this.buf.setDouble(p171_0, p171_1);
    }

    public boolean release() {
        return this.buf.release();
    }

    public int writerIndex() {
        return this.buf.writerIndex();
    }

    public static String getLegacyPacketBufferFlowMarker() {
        return net.minecraft.network.PacketBuffer.legacyPacketBufferFlowMarker;
    }

    public ByteBuf readBytes(ByteBuf p171_0, int p171_1, int p171_2) {
        return this.buf.readBytes(p171_0, p171_1, p171_2);
    }

    public ByteBuf ensureWritable(int p171_0) {
        return this.buf.ensureWritable(p171_0);
    }

    public String readStringFromBuffer(int i) {
        int j = this.readVarIntFromBuffer();
        if (j > i * 4) {
            throw new DecoderException(
                    "The received encoded string buffer length is longer than maximum allowed ("
                            + j
                            + " > "
                            + i * 4
                            + packedTextFragments[0]);
        } else if (j < 0) {
            throw new DecoderException(
                    "The received encoded string buffer length is less than zero! Weird string!");
        } else {
            String text =
                    new String(
                            remix.compat.library.LegacyLibraryAdapters.readBytes(this, j),
                            Charsets.UTF_8);
            if (text.length() > i) {
                throw new DecoderException(
                        "The received string length is longer than maximum allowed ("
                                + j
                                + " > "
                                + i
                                + packedTextFragments[1]);
            } else {
                return text;
            }
        }
    }

    public void writeEnumValue(Enum enumValue) {
        this.writeVarIntToBuffer(enumValue.ordinal());
    }

    public ByteBuf getBytes(int p171_0, ByteBuffer byteBuffer) {
        return this.buf.getBytes(p171_0, byteBuffer);
    }

    public ByteBuf getBytes(int p171_0, byte[] p171_1, int p171_2, int p171_3) {
        return this.buf.getBytes(p171_0, p171_1, p171_2, p171_3);
    }

    public ByteBuf readBytes(ByteBuf p171_0) {
        return this.buf.readBytes(p171_0);
    }

    // region String decoding support

    // endregion

    public ByteBuf getBytes(int p171_0, ByteBuf p171_1) {
        return this.buf.getBytes(p171_0, p171_1);
    }

    public int nioBufferCount() {
        return this.buf.nioBufferCount();
    }

    public byte[] array() {
        return this.buf.array();
    }

    public int forEachByteDesc(ByteBufProcessor p171_0) {
        return this.buf.forEachByteDesc(p171_0);
    }

    public ByteBuffer[] nioBuffers(int p171_0, int p171_1) {
        return this.buf.nioBuffers(p171_0, p171_1);
    }

    public PacketBuffer(ByteBuf aZiValue) {
        this.buf = aZiValue;
    }

    public ByteBuf setFloat(int p171_0, float p171_1) {
        return this.buf.setFloat(p171_0, p171_1);
    }

    public short getShort(int p171_0) {
        return this.buf.getShort(p171_0);
    }

    public int getUnsignedMedium(int p171_0) {
        return this.buf.getUnsignedMedium(p171_0);
    }

    // Compatibility entry points consumed by the runtime remapper.
    public io.netty.buffer.ByteBuf jvm171_api_retain_0() {
        return this.retain();
    }

    public io.netty.buffer.ByteBuf jvm171_api_retain_1(int amount) {
        return this.retain(amount);
    }
}
