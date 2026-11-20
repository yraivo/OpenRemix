package remix.util.network;

import io.netty.channel.Channel;
import io.netty.channel.socket.oio.OioSocketChannel;

import java.lang.reflect.Method;
import java.net.Proxy;
import java.net.Socket;

public class OioProxyChannelFactory implements io.netty.bootstrap.ChannelFactory {

    private Proxy proxy;
    private ProxyType proxyType;

    public OioProxyChannelFactory(Proxy proxy, ProxyType proxyType) {
        this.proxy = proxy;
        this.proxyType = proxyType;
    }


    public Channel newChannel() {
        return this.eH();
    }

    public OioProxyChannelFactory(Proxy proxy) {
        this.proxy = proxy;
    }

    public OioSocketChannel eH() {
        Socket socket = new Socket(this.proxy);
        if (this.proxyType.equals(ProxyType.SOCKS4)) {
            try {
                Method method = socket.getClass().getDeclaredMethod("getImpl");
                method.setAccessible(true);
                Object object = method.invoke(socket);
                method = object.getClass().getDeclaredMethod("setV4");
                method.setAccessible(true);
                method.invoke(object);
                return new OioSocketChannel(socket);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return new OioSocketChannel(socket);
    }
}
