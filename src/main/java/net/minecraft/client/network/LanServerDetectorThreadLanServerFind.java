package net.minecraft.client.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

public class LanServerDetectorThreadLanServerFind extends Thread {

    private MulticastSocket socket;

    private LanServerDetectorLanServerList localServerList;
    private InetAddress broadcastAddress;

    @Override
    public void run() {
        ClientPlayHandler.getLegacyPlayHandlerFlowMarker();

        byte[] abyte = new byte[1024];

        while (!this.isInterrupted()) {
            DatagramPacket datagrampacket = new DatagramPacket(abyte, abyte.length);

            try {
                this.socket.receive(datagrampacket);
            } catch (SocketTimeoutException sockettimeoutexception) {
                continue;
            } catch (IOException ioexception1) {
                LanServerDetector.access$100().error("Couldn't ping server", ioexception1);

                break;
            }

            String text =
                    new String(
                            datagrampacket.getData(),
                            datagrampacket.getOffset(),
                            datagrampacket.getLength());
            LanServerDetector.access$100().debug(datagrampacket.getAddress() + ": " + text);
            this.localServerList.method_e6e1733e9b2e1aff79b2(text, datagrampacket.getAddress());
        }

        try {
            this.socket.leaveGroup(this.broadcastAddress);
        } catch (IOException ioexception) {
        }

        this.socket.close();
    }

    public LanServerDetectorThreadLanServerFind(LanServerDetectorLanServerList aODValue)
            throws IOException {
        super("LanServerDetector #" + LanServerDetector.access$000().incrementAndGet());
        this.localServerList = aODValue;
        this.setDaemon(true);
        this.socket = new MulticastSocket(4445);
        this.broadcastAddress = InetAddress.getByName("224.0.2.60");
        this.socket.setSoTimeout(5000);
        this.socket.joinGroup(this.broadcastAddress);
    }


}
