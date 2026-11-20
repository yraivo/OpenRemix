package remix.client;

import net.minecraft.entity.Entity;
import remix.compat.legacy.auth.ClientAuthCoordinator;
import remix.compat.legacy.security.DecryptionGuardCondition;
import remix.compat.legacy.codec.DefaultByteCipherRegistry;
import remix.compat.legacy.auth.DefaultClientAuthCoordinator;
import remix.compat.legacy.codec.DefaultEncodedStageEvaluator;
import remix.compat.legacy.codec.DefaultEncodedStageSequence;
import remix.compat.legacy.codec.DefaultEncodedValueCodec;
import remix.compat.legacy.http.DualEndpointProfileFetcher;
import remix.compat.legacy.codec.EncodedValueCodec;
import remix.compat.legacy.codec.EncodedValueCodecConfig;
import remix.compat.legacy.lifecycle.EncryptedClientServicesProvider;
import remix.util.collection.FluentListWrapper;
import remix.event.InputType;
import remix.compat.legacy.auth.LicenseSessionBuilder;
import remix.event.discovery.MemberDiscoveryStrategy;
import remix.event.discovery.MemberValidator;
import remix.compat.legacy.script.NashornScriptService;
import remix.compat.legacy.http.PastebinProfilePasteEndpoint;
import remix.compat.legacy.auth.RemoteAuthService;
import remix.compat.legacy.security.SecurityManagerRegistry;
import remix.compat.legacy.codec.StepByteTransformStage;
import remix.compat.legacy.codec.StringCharsetByteSerializer;
import remix.compat.legacy.codec.StringEncodedSequenceParser;
import remix.compat.legacy.http.TeknikProfilePasteEndpoint;
import remix.compat.legacy.auth.UidFileStore;
import remix.event.EventBus;
import remix.input.KeyAction;
import remix.ui.font.ClientFontManager;
import remix.ui.screen.RemixMainMenuScreen;

import remix.event.subscription.SubscriptionInvocationStrategy;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import javax.script.ScriptException;

public final class RemixClient extends AbstractClientBootstrap<ClientServices> {
    public static RemixClient INSTANCE;
    private static Entity[] legacyClientStateMarker;
    public static RemixMainMenuScreen Qo;
    public static ClientFontManager fonts;
    public static EventBus eventBus;
    private Type[] Qr;
    public LicenseSessionBuilder Qs;

    public static UidFileStore uidFileStore;

    public static EncodedValueCodec<DefaultEncodedStageSequence, String> encodedStringCodec;

    // region Static initialization and string tables
    static {
        Entity[] initialFlowMarker = new Entity[4];
        setLegacyClientStateMarker(initialFlowMarker);
        Charset charset = StandardCharsets.UTF_8;
        remix.client.RemixClient.encodedStringCodec =
                new DefaultEncodedValueCodec(
                        EncodedValueCodecConfig.newBuilder()
                                .addTransformStage(
                                        new StepByteTransformStage(0, 0, false))
                                .withByteSerializer(
                                        StringCharsetByteSerializer.forCharset(
                                                charset))
                                .withCipherRegistry(
                                        new DefaultByteCipherRegistry(
                                                ((remix.compat.legacy.codec.DecodedByteDeserializer<
                                                                java.lang.String>)
                                                        ((decodedBytes, serializer) -> {
                                                            Charset charset2 = charset;
                                                            if (serializer
                                                                    instanceof
                                                                    StringCharsetByteSerializer) {
                                                                StringCharsetByteSerializer
                                                                        charsetSerializer =
                                                                                (StringCharsetByteSerializer)
                                                                                        serializer;
                                                                charset2 =
                                                                        charsetSerializer
                                                                                .getCharset();
                                                            }

                                                            return new String(decodedBytes, charset2);
                                                        }))))
                                .withSequenceParser(new StringEncodedSequenceParser())
                                .withStageEvaluator(new DefaultEncodedStageEvaluator())
                                .withAlternateBlockOrder()
                                .withRepeatCounts()
                                .withDifferenceIndexLimit(6)
                                .withSecureRandom()
                                .build());
        try {
            NashornScriptService.bYK.initializeLegacyScriptBridge();
        } catch (ScriptException scriptexception) {
        }
        remix.client.RemixClient.uidFileStore = new UidFileStore();
        eventBus =
                EventBus.builder()
                        .discoveryStrategy(MemberDiscoveryStrategy.afI())
                        .memberValidator(MemberValidator.afL())
                        .invocationStrategy(
                                SubscriptionInvocationStrategy.METHOD_LAMBDA_INVOCATION)
                        .build();
        remix.client.RemixClient.fonts = new ClientFontManager();
        INSTANCE = new RemixClient(new DefaultClientAuthCoordinator());
    }

    // endregion

    public static Entity[] getLegacyClientStateMarker() {
        return remix.client.RemixClient.legacyClientStateMarker;
    }

    public void handleLegacyRemoteLicenseCallback(String text) {
        throw new SecurityException(
                "Legacy remote license callbacks are disabled; use the local startup path");
    }

    public void handleLegacyLicenseFailure() {
        throw new SecurityException("Destructive license-failure callback is disabled");
    }

    public static void setLegacyClientStateMarker(Entity[] entityArray) {
        remix.client.RemixClient.legacyClientStateMarker = entityArray;
    }

    protected RemixClient(ClientAuthCoordinator clientAuthCoordinator) {
        super(
                new EncryptedClientServicesProvider(
                        new ClientServices(), new DecryptionGuardCondition[0]),
                clientAuthCoordinator,
                new DualEndpointProfileFetcher(
                        RemoteAuthService.INSTANCE,
                        new PastebinProfilePasteEndpoint(
                                (remotePasteEndpoint, licenseProfileRecord) -> {
                                    PastebinProfilePasteEndpoint pastebinProfilePasteEndpoint =
                                            (PastebinProfilePasteEndpoint) remotePasteEndpoint;
                                    int[] aint1 = ClientServices.getLegacyServiceMarker();
                                    FluentListWrapper fluentListWrapper =
                                            pastebinProfilePasteEndpoint.getFluentListWrapper();
                                    int[] aint = aint1;

                                    for (java.util.Iterator<?> __recoveryIterator171_34886 =
                                                    (fluentListWrapper.asList()).iterator();
                                            (__recoveryIterator171_34886).hasNext(); ) {
                                        String text = (String) (__recoveryIterator171_34886).next();
                                        if (!text.equals(
                                                        remix.client.RemixClient.encodedStringCodec
                                                                .getDecoder()
                                                                .decode(
                                                                        "=(:1'3:1'2:1':2':'2:2'1:'1:1'5:1'1:':':1'2:':4')#[49, 43, 15, 11, -45, -100](:':3':'1:1':1'3:'3:':':1'2:2':'1:3':':1':'4:'1:'2:3'2)#[63, 27, -8, -15, -70, -125](:':':':'1:':'1:'3:'3:1':1'1:':':':':':'1:':)#[37, -5, -47, -51, -76, -83](':'2:':1':':':':2':'1:1'1:1':':':1':'2:'2)#[27, 22, -22, -25, -54, -75](':3':1'2:':'4:1'1:'1:':':':':'2:1'2:':)#[21, 10, -13, -49, -58, -92](.2\".\"1.2\"1.\".\"2.\".1\"1.2\"1.1\"1.\".4\".\".3\".\"3.\"2.1\".\".\".\".\"1.1\"1.\".\"1.4)#[37, -14, -23, -66, -123, -151](:2':1'1:1'2:'3:1':':':':':4'1:3':1'1:2':'3:'3:':3'2:'1:'2:':'1:)#[50, 22, -23, -75, -76, -89]('5:2'1:':'1:'1:'3:'1:1'1:'2:'1:'2:':'1:':2':':':2':2':'2:2'2:':1':1');" /* "=(:1'3:1'2:1':2':'2:2'1:'1:1'5:1'1:':':1'2:':4')#[49, 43, 15, 11, -45, -100](:':3':'1:1':1'3:'3:':':1'2:2':'1:3':':1':'4:'1:'2:3'2)#[63, 27, -8, -15, -70, -125](:':':':'1:':'1:'3:'3:1':1'1:':':':':':'1:':)#[37, -5, -47, -51, -76, -83](':'2:':1':':':':2':'1:1'1:1':':':1':'2:'2)#[27, 22, -22, -25, -54, -75](':3':1'2:':'4:1'1:'1:':':':':'2:1'2:':)#[21, 10, -13, -49, -58, -92](.2＼".＼"1.2＼"1.＼".＼"2.＼".1＼"1.2＼"1.1＼"1.＼".4＼".＼".3＼".＼"3.＼"2.1＼".＼".＼".＼".＼"1.1＼"1.＼".＼"1.4)#[37, -14, -23, -66, -123, -151](:2':1'1:1'2:'3:1':':':':':4'1:3':1'1:2':'3:'3:':3'2:'1:'2:':'1:)#[50, 22, -23, -75, -76, -89]('5:2'1:':'1:'1:'3:'1:1'1:'2:'1:'2:':'1:':2':':':2':2':'2:2'2:':1':1');" */))
                                                && !text.equals(
                                                        remix.client.RemixClient.encodedStringCodec
                                                                .getDecoder()
                                                                .decode(
                                                                        "=('1:'2:3':'3:1'3:':2'4:'2:':1)#[31, -2, -32, -56, -84, -114]('2:':'2:':1':2':2':2':':1'6:'3:'4)#[35, 34, -11, -54, -66, -66](:2':'3:'1:':'1:'3:'1:1'1:'2:'1:'1:1'2:1':1':1'1:'2:1':'1:)#[60, 29, 4, 4, -7, -60](':6':'3:1'1:':'1:'2:'1:3'1:1':1':1':)#[44, 37, 28, -5, -18, -62](:1':1':6':1'1:':'1:3':'1:'5:2'4:':2':1')#[22, -12, -45, -69, -98, -105](:'2:':':'4:':1':':':1':3':1'2:':1'1:'2:4':1'2)#[51, 14, 13, -39, -82, -98](':1'2:2'3:6':'1:'1:1':':'2:':'3:':1'1)#[43, 5, -34, -75, -107, -117](':':'1:'1:':1'5:2'1:1'6:'1:1':'2:1':':'1:':2'2:'1);"))
                                                && !text.equals(
                                                        remix.client.RemixClient.encodedStringCodec
                                                                .getDecoder()
                                                                .decode(
                                                                        "=(':1'4:'5:1'1:'3:':'1:':3'1)#[35, 1, -26, -52, -58, -84](:':1':'3:1':':':':'1:':'1:'3:1':':4)#[13, 2, -11, -44, -51, -77](:1':4'1:'7:1':1':'1:':'2:2'1:':1'1:':4'1:1':1'2)#[50, 11, -5, -20, -30, -73]('8:1':1':'1:':'1:6'2:1'2:1'2:3')#[28, 13, -12, -35, -43, -75]('2:4':2'2:':1'1:':'1:1'4:2'1:)#[3, -20, -58, -77, -86, -119](.1\".1\".1\".1\".\"3.1\".1\".\"1.\".\".\"1.1\".\"2.\"1.4\".\".\".\".\".\"1.1\"4.\".2\".1\".\"1.3)#[34, -34, -40, -89, -158, -212]('2:1'2:3':3'4:1':':1'2:':':1'2:':':2':1'1:4)#[43, 4, -43, -65, -90, -98](:'1:1':':2'1:':'1:'5:':':6'1:'1:3'1:1':':'4:':':4'4:2'4:1');" /* "=(':1'4:'5:1'1:'3:':'1:':3'1)#[35, 1, -26, -52, -58, -84](:':1':'3:1':':':':'1:':'1:'3:1':':4)#[13, 2, -11, -44, -51, -77](:1':4'1:'7:1':1':'1:':'2:2'1:':1'1:':4'1:1':1'2)#[50, 11, -5, -20, -30, -73]('8:1':1':'1:':'1:6'2:1'2:1'2:3')#[28, 13, -12, -35, -43, -75]('2:4':2'2:':1'1:':'1:1'4:2'1:)#[3, -20, -58, -77, -86, -119](.1＼".1＼".1＼".1＼".＼"3.1＼".1＼".＼"1.＼".＼".＼"1.1＼".＼"2.＼"1.4＼".＼".＼".＼".＼".＼"1.1＼"4.＼".2＼".1＼".＼"1.3)#[34, -34, -40, -89, -158, -212]('2:1'2:3':3'4:1':':1'2:':':1'2:':':2':1'1:4)#[43, 4, -43, -65, -90, -98](:'1:1':':2'1:':'1:'5:':':6'1:'1:3'1:1':':'4:':':4'4:2'4:1');" */))
                                                && (!text.equals(
                                                                remix.client.RemixClient.encodedStringCodec
                                                                        .getDecoder()
                                                                        .decode(
                                                                                "=('1:':':1'1:1':':1':':1'1:2':':4':'1:1'3)#[27, -1, 34, -7, -18, -39](:1':':':1':1'1:4':':3'1:':4'1:1':'1:1':'3:'1:'1)#[34, 30, 28, -15, -15, -27](:3':'2:1'3:':1'1:1'1:4'2:)#[16, -1, 1, -16, -30, -41](':'1:1'1:':'4:1'1:6'1:'2:'1:1':4'1:'1:':'2:1'2:1':'1:2':':':1)#[31, -28, -78, -106, -140, -148]('1:1':3':2'2:1':':':'1:)#[16, 1, -6, -18, -24, -34](':3':1'5:'7:4':2'1:6':)#[5, -7, -22, -36, -45, -79](:2'2:1':1':'2:1':1'4:'1:1':2'1:')#[6, -2, -31, -34, -38, -49]('1:1'3:'1:1'2:':'1:'2:':2':11':':':2':3':1':'1:1':4'3:'5);"))
                                                        || aint != null)) {
                                            return false;
                                        }
                                    }

                                    return pastebinProfilePasteEndpoint
                                            .fluentListWrapper
                                            .asList()
                                            .contains(licenseProfileRecord);
                                },
                                new String[] {
                                    (String)
                                            remix.client.RemixClient.encodedStringCodec
                                                    .getDecoder()
                                                    .decode(
                                                            "=(:1'3:1'2:1':2':'2:2'1:'1:1'5:1'1:':':1'2:':4')#[49, 43, 15, 11, -45, -100](:':3':'1:1':1'3:'3:':':1'2:2':'1:3':':1':'4:'1:'2:3'2)#[63, 27, -8, -15, -70, -125](:':':':'1:':'1:'3:'3:1':1'1:':':':':':'1:':)#[37, -5, -47, -51, -76, -83](':'2:':1':':':':2':'1:1'1:1':':':1':'2:'2)#[27, 22, -22, -25, -54, -75](':3':1'2:':'4:1'1:'1:':':':':'2:1'2:':)#[21, 10, -13, -49, -58, -92](.2\".\"1.2\"1.\".\"2.\".1\"1.2\"1.1\"1.\".4\".\".3\".\"3.\"2.1\".\".\".\".\"1.1\"1.\".\"1.4)#[37, -14, -23, -66, -123, -151](:2':1'1:1'2:'3:1':':':':':4'1:3':1'1:2':'3:'3:':3'2:'1:'2:':'1:)#[50, 22, -23, -75, -76, -89]('5:2'1:':'1:'1:'3:'1:1'1:'2:'1:'2:':'1:':2':':':2':2':'2:2'2:':1':1');" /* "=(:1'3:1'2:1':2':'2:2'1:'1:1'5:1'1:':':1'2:':4')#[49, 43, 15, 11, -45, -100](:':3':'1:1':1'3:'3:':':1'2:2':'1:3':':1':'4:'1:'2:3'2)#[63, 27, -8, -15, -70, -125](:':':':'1:':'1:'3:'3:1':1'1:':':':':':'1:':)#[37, -5, -47, -51, -76, -83](':'2:':1':':':':2':'1:1'1:1':':':1':'2:'2)#[27, 22, -22, -25, -54, -75](':3':1'2:':'4:1'1:'1:':':':':'2:1'2:':)#[21, 10, -13, -49, -58, -92](.2＼".＼"1.2＼"1.＼".＼"2.＼".1＼"1.2＼"1.1＼"1.＼".4＼".＼".3＼".＼"3.＼"2.1＼".＼".＼".＼".＼"1.1＼"1.＼".＼"1.4)#[37, -14, -23, -66, -123, -151](:2':1'1:1'2:'3:1':':':':':4'1:3':1'1:2':'3:'3:':3'2:'1:'2:':'1:)#[50, 22, -23, -75, -76, -89]('5:2'1:':'1:'1:'3:'1:1'1:'2:'1:'2:':'1:':2':':':2':2':'2:2'2:':1':1');" */),
                                    (String)
                                            remix.client.RemixClient.encodedStringCodec
                                                    .getDecoder()
                                                    .decode(
                                                            "=('1:'2:3':'3:1'3:':2'4:'2:':1)#[31, -2, -32, -56, -84, -114]('2:':'2:':1':2':2':2':':1'6:'3:'4)#[35, 34, -11, -54, -66, -66](:2':'3:'1:':'1:'3:'1:1'1:'2:'1:'1:1'2:1':1':1'1:'2:1':'1:)#[60, 29, 4, 4, -7, -60](':6':'3:1'1:':'1:'2:'1:3'1:1':1':1':)#[44, 37, 28, -5, -18, -62](:1':1':6':1'1:':'1:3':'1:'5:2'4:':2':1')#[22, -12, -45, -69, -98, -105](:'2:':':'4:':1':':':1':3':1'2:':1'1:'2:4':1'2)#[51, 14, 13, -39, -82, -98](':1'2:2'3:6':'1:'1:1':':'2:':'3:':1'1)#[43, 5, -34, -75, -107, -117](':':'1:'1:':1'5:2'1:1'6:'1:1':'2:1':':'1:':2'2:'1);"),
                                    (String)
                                            remix.client.RemixClient.encodedStringCodec
                                                    .getDecoder()
                                                    .decode(
                                                            "=(':1'4:'5:1'1:'3:':'1:':3'1)#[35, 1, -26, -52, -58, -84](:':1':'3:1':':':':'1:':'1:'3:1':':4)#[13, 2, -11, -44, -51, -77](:1':4'1:'7:1':1':'1:':'2:2'1:':1'1:':4'1:1':1'2)#[50, 11, -5, -20, -30, -73]('8:1':1':'1:':'1:6'2:1'2:1'2:3')#[28, 13, -12, -35, -43, -75]('2:4':2'2:':1'1:':'1:1'4:2'1:)#[3, -20, -58, -77, -86, -119](.1\".1\".1\".1\".\"3.1\".1\".\"1.\".\".\"1.1\".\"2.\"1.4\".\".\".\".\".\"1.1\"4.\".2\".1\".\"1.3)#[34, -34, -40, -89, -158, -212]('2:1'2:3':3'4:1':':1'2:':':1'2:':':2':1'1:4)#[43, 4, -43, -65, -90, -98](:'1:1':':2'1:':'1:'5:':':6'1:'1:3'1:1':':'4:':':4'4:2'4:1');" /* "=(':1'4:'5:1'1:'3:':'1:':3'1)#[35, 1, -26, -52, -58, -84](:':1':'3:1':':':':'1:':'1:'3:1':':4)#[13, 2, -11, -44, -51, -77](:1':4'1:'7:1':1':'1:':'2:2'1:':1'1:':4'1:1':1'2)#[50, 11, -5, -20, -30, -73]('8:1':1':'1:':'1:6'2:1'2:1'2:3')#[28, 13, -12, -35, -43, -75]('2:4':2'2:':1'1:':'1:1'4:2'1:)#[3, -20, -58, -77, -86, -119](.1＼".1＼".1＼".1＼".＼"3.1＼".1＼".＼"1.＼".＼".＼"1.1＼".＼"2.＼"1.4＼".＼".＼".＼".＼".＼"1.1＼"4.＼".2＼".1＼".＼"1.3)#[34, -34, -40, -89, -158, -212]('2:1'2:3':3'4:1':':1'2:':':1'2:':':2':1'1:4)#[43, 4, -43, -65, -90, -98](:'1:1':':2'1:':'1:'5:':':6'1:'1:3'1:1':':'4:':':4'4:2'4:1');" */),
                                    (String)
                                            remix.client.RemixClient.encodedStringCodec
                                                    .getDecoder()
                                                    .decode(
                                                            "=('1:':':1'1:1':':1':':1'1:2':':4':'1:1'3)#[27, -1, 34, -7, -18, -39](:1':':':1':1'1:4':':3'1:':4'1:1':'1:1':'3:'1:'1)#[34, 30, 28, -15, -15, -27](:3':'2:1'3:':1'1:1'1:4'2:)#[16, -1, 1, -16, -30, -41](':'1:1'1:':'4:1'1:6'1:'2:'1:1':4'1:'1:':'2:1'2:1':'1:2':':':1)#[31, -28, -78, -106, -140, -148]('1:1':3':2'2:1':':':'1:)#[16, 1, -6, -18, -24, -34](':3':1'5:'7:4':2'1:6':)#[5, -7, -22, -36, -45, -79](:2'2:1':1':'2:1':1'4:'1:1':2'1:')#[6, -2, -31, -34, -38, -49]('1:1'3:'1:1'2:':'1:'2:':2':11':':':2':3':1':'1:1':4'3:'5);")
                                }),
                        new TeknikProfilePasteEndpoint(
                                (remotePasteEndpoint, licenseProfileRecord) -> {
                                    TeknikProfilePasteEndpoint teknikProfilePasteEndpoint =
                                            (TeknikProfilePasteEndpoint) remotePasteEndpoint;
                                    FluentListWrapper fluentListWrapper =
                                            teknikProfilePasteEndpoint.getFluentListWrapper();
                                    int[] aint1 = ClientServices.getLegacyServiceMarker();
                                    Iterator iterator = fluentListWrapper.asList().iterator();
                                    int[] aint = aint1;

                                    while (iterator.hasNext()) {
                                        String text = (String) iterator.next();
                                        if (!text.equals(
                                                        remix.client.RemixClient.encodedStringCodec
                                                                .getDecoder()
                                                                .decode(
                                                                        "=('2:1':3'1:'1:':3'1:':1':'4:':1':':1':1'1:'1:1'1:1':1'1:4':3')#[11, -59, -70, -82, -102, -142](:':':':':'2:'1:2'2:5':1'2:2':1'2:1')#[18, -18, -22, -55, -93, -114](:'3:'1:2':2':2':'2)#[7, -2, -16, -17, -25, -42](:':1'1:2'3:':2'2:'1:1'4:1'3:3':1'1:':1'1:2':'1:1':1)#[38, -14, -24, -53, -112, -159]('1:1'2:1':3':':'1:':'2:1':'3:':1':':1':'2:'1:'1:3':1':1':'1);"))
                                                || aint != null) {
                                            return false;
                                        }
                                    }

                                    return teknikProfilePasteEndpoint
                                            .fluentListWrapper
                                            .asList()
                                            .contains(licenseProfileRecord);
                                },
                                new String[] {
                                    (String)
                                            remix.client.RemixClient.encodedStringCodec
                                                    .getDecoder()
                                                    .decode(
                                                            "=('2:1':3'1:'1:':3'1:':1':'4:':1':':1':1'1:'1:1'1:1':1'1:4':3')#[11, -59, -70, -82, -102, -142](:':':':':'2:'1:2'2:5':1'2:2':1'2:1')#[18, -18, -22, -55, -93, -114](:'3:'1:2':2':2':'2)#[7, -2, -16, -17, -25, -42](:':1'1:2'3:':2'2:'1:1'4:1'3:3':1'1:':1'1:2':'1:1':1)#[38, -14, -24, -53, -112, -159]('1:1'2:1':3':':'1:':'2:1':'3:':1':':1':'2:'1:'1:3':1':1':'1);")
                                })),
                SecurityManagerRegistry.INSTANCE);
    }

    public boolean handleKeyAction(KeyAction keyAction, InputType inputType, int i) {
        ClientServices clientServices = (ClientServices) this.getClientServices();
        return keyAction
                        .getInputType()
                        .getDisplayName()
                        .equals(inputType.getDisplayName())
                && keyAction.canExecute(clientServices, i)
                && keyAction.getInputType() == inputType;
    }
}
