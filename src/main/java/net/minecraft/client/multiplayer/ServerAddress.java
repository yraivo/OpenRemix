package net.minecraft.client.multiplayer;

import net.minecraft.entity.Entity;

import java.net.IDN;
import java.util.Hashtable;

import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

public class ServerAddress {

    private static String[] packedTextFragments;
    private String ipAddress;
    private int serverPort;

    static {
        net.minecraft.client.multiplayer.ServerAddress.packedTextFragments = new String[8];
        net.minecraft.client.multiplayer.ServerAddress.packedTextFragments[0] = new String(":");
        net.minecraft.client.multiplayer.ServerAddress.packedTextFragments[1] = new String("[");
        net.minecraft.client.multiplayer.ServerAddress.packedTextFragments[2] = new String("]");
        net.minecraft.client.multiplayer.ServerAddress.packedTextFragments[3] = new String(":");
        net.minecraft.client.multiplayer.ServerAddress.packedTextFragments[4] = new String("1");
        net.minecraft.client.multiplayer.ServerAddress.packedTextFragments[5] = new String(" ");
    }

    public String getIP() {
        return IDN.toASCII(this.ipAddress);
    }

    private ServerAddress(String text, int serverPortValue) {
        this.ipAddress = text;
        this.serverPort = serverPortValue;
    }

    private static String[] getServerAddress(String text) {
        try {

            Class.forName("com.sun.jndi.dns.DnsContextFactory");
            Hashtable hashtable = new Hashtable();
            hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            hashtable.put("java.naming.provider.url", "dns:");
            hashtable.put("com.sun.jndi.dns.timeout.retries", packedTextFragments[4]);
            InitialDirContext initialdircontext = new InitialDirContext(hashtable);
            Attributes attributes =
                    initialdircontext.getAttributes(
                            "_minecraft._tcp." + text, new String[] {"SRV"});
            String[] astring =
                    attributes.get("srv").get().toString().split(packedTextFragments[5], 4);
            return new String[] {astring[3], astring[2]};
        } catch (Throwable throwable) {
            return new String[] {text, Integer.toString(25565)};
        }
    }

    public int getPort() {
        return this.serverPort;
    }

    public static ServerAddress fromString(String text) {
        Entity[] entityArray = ClientWorld.getLegacyClientWorldFlowState();
        if (text == null) {
            return null;
        }

        String[] astring = text.split(packedTextFragments[0]);
        if (text.startsWith(packedTextFragments[1])) {
            int i = text.indexOf(packedTextFragments[2]);
            if (i > 0) {
                String textValue = text.substring(1, i);
                String textValue2 = text.substring(i + 1).trim();
                if (textValue2.startsWith(packedTextFragments[3]) && textValue2.length() > 0) {
                    textValue2 = textValue2.substring(1);
                    astring = new String[] {textValue, textValue2};
                    if (!(entityArray == null)) {

                        astring = new String[] {textValue};
                    }
                } else {

                    astring = new String[] {textValue};
                }
            }
        }

        if (astring.length > 2) {
            astring = new String[] {text};
        }

        String textValue3 = astring[0];
        int j = astring.length > 1 ? parseIntWithDefault(astring[1], 25565) : 25565;
        if (j == 25565) {
            String[] astring1 = getServerAddress(textValue3);
            textValue3 = astring1[0];
            j = parseIntWithDefault(astring1[1], 25565);
        }

        return new ServerAddress(textValue3, j);
    }

    private static int parseIntWithDefault(String text, int i) {
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception exception) {
            return i;
        }
    }
}
