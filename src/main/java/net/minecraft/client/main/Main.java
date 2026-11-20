package net.minecraft.client.main;

import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;

import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import net.minecraft.util.Session;
import net.minecraft.entity.Entity;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.List;

public class Main {

    public static void main(String[] arguments) {
        boolean legacyLaunchFlow = GameConfiguration.b() != null;
        System.setProperty("java.net.preferIPv4Stack", "true");
        OptionParser parser = new OptionParser();
        parser.allowsUnrecognizedOptions();
        parser.accepts("demo");
        parser.accepts("fullscreen");
        parser.accepts("checkGlErrors");
        ArgumentAcceptingOptionSpec serverHostOption =
                parser.accepts("server").withRequiredArg();
        ArgumentAcceptingOptionSpec serverPortOption =
                parser
                        .accepts("port")
                        .withRequiredArg()
                        .ofType(Integer.class)
                        .defaultsTo(25565, new Integer[0]);
        ArgumentAcceptingOptionSpec gameDirectoryOption =
                parser
                        .accepts("gameDir")
                        .withRequiredArg()
                        .ofType(File.class)
                        .defaultsTo(new File("."), new File[0]);
        ArgumentAcceptingOptionSpec assetsDirectoryOption =
                parser.accepts("assetsDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec resourcePackDirectoryOption =
                parser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec proxyHostOption =
                parser.accepts("proxyHost").withRequiredArg();
        ArgumentAcceptingOptionSpec proxyPortOption =
                parser
                        .accepts("proxyPort")
                        .withRequiredArg()
                        .defaultsTo("8080", new String[0])
                        .ofType(Integer.class);
        ArgumentAcceptingOptionSpec proxyUserOption =
                parser.accepts("proxyUser").withRequiredArg();
        ArgumentAcceptingOptionSpec proxyPasswordOption =
                parser.accepts("proxyPass").withRequiredArg();
        ArgumentAcceptingOptionSpec usernameOption =
                parser
                        .accepts("username")
                        .withRequiredArg()
                        .defaultsTo("RemixTester", new String[0]);
        ArgumentAcceptingOptionSpec uuidOption =
                parser.accepts("uuid").withRequiredArg();
        ArgumentAcceptingOptionSpec accessTokenOption =
                parser.accepts("accessToken").withRequiredArg().required();
        ArgumentAcceptingOptionSpec versionOption =
                parser.accepts("version").withRequiredArg().required();
        ArgumentAcceptingOptionSpec widthOption =
                parser
                        .accepts("width")
                        .withRequiredArg()
                        .ofType(Integer.class)
                        .defaultsTo(854, new Integer[0]);
        ArgumentAcceptingOptionSpec heightOption =
                parser
                        .accepts("height")
                        .withRequiredArg()
                        .ofType(Integer.class)
                        .defaultsTo(480, new Integer[0]);
        ArgumentAcceptingOptionSpec userPropertiesOption =
                parser.accepts("userProperties").withRequiredArg().required();
        ArgumentAcceptingOptionSpec assetIndexOption =
                parser.accepts("assetIndex").withRequiredArg();
        ArgumentAcceptingOptionSpec userTypeOption =
                parser
                        .accepts("userType")
                        .withRequiredArg()
                        .defaultsTo("legacy", new String[0]);
        NonOptionArgumentSpec remainingArgsOption = parser.nonOptions();
        OptionSet options = parser.parse(arguments);
        List ignoredArguments = options.valuesOf(remainingArgsOption);
        if (!ignoredArguments.isEmpty()) {
            System.out.println("Completely ignored arguments: " + ignoredArguments);
        }

        Proxy proxy = configureProxy(options, proxyHostOption, proxyPortOption, proxyUserOption, proxyPasswordOption);

        int width = (Integer) options.valueOf(widthOption);
        int height = (Integer) options.valueOf(heightOption);
        boolean fullscreen = options.has("fullscreen");
        boolean checkGlErrors = options.has("checkGlErrors");
        boolean demo = options.has("demo");
        String version = (String) options.valueOf(versionOption);
        PropertyMap userProperties =
                (PropertyMap)
                        new GsonBuilder()
                                .registerTypeAdapter(PropertyMap.class, new Serializer())
                                .create()
                                .fromJson(
                                        (String) options.valueOf(userPropertiesOption),
                                        PropertyMap.class);
        File gameDirectory = (File) options.valueOf(gameDirectoryOption);
        File assetsDirectory =
                options.has(assetsDirectoryOption)
                        ? (File) options.valueOf(assetsDirectoryOption)
                        : new File(gameDirectory, "assets/");
        File resourcePackDirectory =
                options.has(resourcePackDirectoryOption)
                        ? (File) options.valueOf(resourcePackDirectoryOption)
                        : new File(gameDirectory, "resourcepacks/");
        String uuid =
                options.has(uuidOption)
                        ? (String) uuidOption.value(options)
                        : (String) usernameOption.value(options);
        String assetIndex =
                options.has(assetIndexOption)
                        ? (String) assetIndexOption.value(options)
                        : null;
        String serverHost = (String) options.valueOf(serverHostOption);
        Integer serverPort = (Integer) options.valueOf(serverPortOption);
        Session session =
                new Session(
                        (String) usernameOption.value(options),
                        uuid,
                        (String) accessTokenOption.value(options),
                        (String) userTypeOption.value(options));
        GameConfiguration configuration =
                new GameConfiguration(
                        new GameConfigurationUserInformation(
                                session, userProperties, proxy),
                        new GameConfigurationDisplayInformation(width, height, fullscreen, checkGlErrors),
                        new GameConfigurationFolderInformation(gameDirectory, resourcePackDirectory, assetsDirectory, assetIndex),
                        new GameConfigurationGameInformation(demo, version),
                        new GameConfigurationServerInformation(serverHost, serverPort));
        Runtime.getRuntime()
                .addShutdownHook(new ClientShutdownHookThread("Client Shutdown Thread"));
        Thread.currentThread().setName("Client thread");
        new MinecraftClient(configuration).runGame();
        if (legacyLaunchFlow) {
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }
    }

    private static Proxy configureProxy(OptionSet options, ArgumentAcceptingOptionSpec proxyHostOption,
            ArgumentAcceptingOptionSpec proxyPortOption, ArgumentAcceptingOptionSpec proxyUserOption,
            ArgumentAcceptingOptionSpec proxyPasswordOption) {
        String proxyHost = (String) options.valueOf(proxyHostOption);
        Proxy proxy = Proxy.NO_PROXY;
        if (proxyHost != null) {
            try {
                proxy =
                        new Proxy(
                                Type.SOCKS,
                                new InetSocketAddress(
                                        proxyHost,
                                        (Integer) options.valueOf(proxyPortOption)));
            } catch (Exception exception) {
            }
        }

        String proxyUser = (String) options.valueOf(proxyUserOption);
        String proxyPassword = (String) options.valueOf(proxyPasswordOption);
        if (!proxy.equals(Proxy.NO_PROXY) && hasValue(proxyUser) && hasValue(proxyPassword)) {
            Authenticator.setDefault(new ProxyPasswordAuthenticator(proxyUser, proxyPassword));
        }
        return proxy;
    }

    private static boolean hasValue(String value) {
        return value != null && !value.isEmpty();
    }
}
