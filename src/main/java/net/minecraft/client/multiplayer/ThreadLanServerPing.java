package net.minecraft.client.multiplayer;

import net.minecraft.entity.Entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLanServerPing extends Thread {

    private DatagramSocket socket;
    private static AtomicInteger field_148658_a;
    private String address;
    private static Logger logger;
    private String motd;
    private boolean isStopping = true;

    static {
        net.minecraft.client.multiplayer.ThreadLanServerPing.field_148658_a = new AtomicInteger(0);
        net.minecraft.client.multiplayer.ThreadLanServerPing.logger = LogManager.getLogger();
    }

    public static String getPingResponse(String text, String textValue) {
        return "[MOTD]" + text + "[/MOTD][AD]" + textValue + "[/AD]";
    }

    @Override
    public void interrupt() {
        super.interrupt();
        this.isStopping = false;
    }

    public static String a(String text) {
        int i = text.indexOf("[MOTD]");
        if (i < 0) {
            return "missing no";
        }

        int j = text.indexOf("[/MOTD]", i + "[MOTD]".length());
        return j < i ? "missing no" : text.substring(i + "[MOTD]".length(), j);
    }

    public static String method_0442347320b0b56aa8a0(String text) {
        int i = text.indexOf("[/MOTD]");
        if (i < 0) {
            return null;
        }

        int j = text.indexOf("[/MOTD]", i + "[/MOTD]".length());
        if (j >= 0) {
            return null;
        }

        int k = text.indexOf("[AD]", i + "[/MOTD]".length());
        if (k < 0) {
            return null;
        }

        int l = text.indexOf("[/AD]", k + "[AD]".length());
        return l < k ? null : text.substring(k + "[AD]".length(), l);
    }

    @Override
    public void run() {
        String text = getPingResponse(this.motd, this.address);
        Entity[] entityArray = ClientWorld.getLegacyClientWorldFlowState();
        byte[] abyte = text.getBytes();
        Entity[] entityArrayValue = entityArray;

        while (true) {
            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (this.isInterrupted() || !this.isStopping) {
                    break;
                }
            } catch (IOException ioexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception1));
            }

            try {
                InetAddress inetaddress = InetAddress.getByName("224.0.2.60");
                DatagramPacket datagrampacket =
                        new DatagramPacket(abyte, abyte.length, inetaddress, 4445);
                this.socket.send(datagrampacket);
            } catch (IOException ioexception) {
                net.minecraft.client.multiplayer.ThreadLanServerPing.logger.warn(
                        "LanServerPinger: " + ioexception.getMessage());
                break;
            }

            try {

                sleep(1500L);
            } catch (InterruptedException interruptedexception) {
                if (entityArrayValue != null) {
                    break;
                }
            }
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public ThreadLanServerPing(String text, String textValue) throws IOException {
        super(
                "LanServerPinger #"
                        + net.minecraft.client.multiplayer.ThreadLanServerPing.field_148658_a
                                .incrementAndGet());
        this.motd = text;
        this.address = textValue;
        this.setDaemon(true);
        this.socket = new DatagramSocket();
    }
}
