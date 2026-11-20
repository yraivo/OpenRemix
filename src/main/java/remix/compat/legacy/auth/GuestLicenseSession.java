package remix.compat.legacy.auth;

import net.minecraft.entity.Entity;

import remix.client.RemixClient;
import remix.compat.legacy.codec.CipherKeySelector;

public final class GuestLicenseSession extends DefaultLicenseSession {
    private static String g;
    private static String f;
    private static String te;
    private static String h;

    public GuestLicenseSession() throws Exception {
        this(LicenseProfileRecord.getLegacyProfileRecordFlowToken());
    }

    private GuestLicenseSession(int initialFlowToken) throws Exception {
        super(
                new DefaultClientIdentityToken(
                        new CipherKeySelector(new String("g"), true), null, null),
                UserRoleTier.BANNED_EXTRA);

        int j = initialFlowToken;

        int i = j;

        try {
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                LicenseProfileRecord.setLegacyProfileRecordFlowState(++i);
            }
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception));
        }
    }

    static String method_cee993844cce4b97ed62() {
        return te;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    static String method_c60693f457aa70be7160() {
        return h;
    }

    public static DefaultLicenseSession acR() {
        try {
            return new GuestLicenseSession();
        } catch (Exception exception1) {
            try {
                return new FallbackLicenseSession(new String("I"));
            } catch (Exception exception) {
                return null;
            }
        }
    }

    static {
        f =
                (String)
                        RemixClient.encodedStringCodec
                                .getDecoder()
                                .decode(
                                        "=('1:'2:4':1':':'1:'1:'2:2':1':)#[16, 11, 4, -2, -11,"
                                            + " -26](':'1:4'7:':1':':1'2:':'8:1':3'2:1':'2:':1'1:'1:)#[56,"
                                            + " 15, -26, -37, -46, -86](:1':'1:'1:4'4:4':'2:')#[5,"
                                            + " -22, -35, -43, -58,"
                                            + " -80](:4'1:'1:':':'1:':4':1'1:':6':'1:1':');");
        g =
                (String)
                        RemixClient.encodedStringCodec
                                .getDecoder()
                                .decode(
                                        "=(:':2':1'2:':2':':'1:1':'3:1'2:5'2:1'3:'5:':3'1:1':'1:1'2:'1:1')#[72,"
                                            + " 20, 13, -10, -81,"
                                            + " -146](\".1\".4\".\"1.1\"2.1\"3.1\".\".3\".\".\".3\".\".\".1\"1.6\".\"1.\".1\".1\"1.\".\"4.3\"1.\")#[3,"
                                            + " -23, -80, -121, -192,"
                                            + " -277](:'2:':1'3:'4:1'1:1':2'2:3':1':1'1)#[28, 19,"
                                            + " -23, -64, -75,"
                                            + " -116](':1':1'1:1'3:1':5':1':':2'2:':1':1'1:'1:');");
        h =
                (String)
                        RemixClient.encodedStringCodec
                                .getDecoder()
                                .decode(
                                        "=(:1':1':'1:1'1:1':5':':1':5'2:'1)#[25, 1, -23, -51, -74,"
                                            + " -101](:':3'2:'1:'1:':1'2:5':'2:'2:1'1:':2':':':1':'1:2':')#[2,"
                                            + " -19, -39, -40, -51,"
                                            + " -106](:'3:1':':5'2:2':':1':1'1:1)#[8, 6, -2, -14,"
                                            + " -25,"
                                            + " -39](':1'1:':':2'1:':1':4':2'2:'7:':3'1:':1'2:1)#[25,"
                                            + " -28, -44, -79, -115,"
                                            + " -154](':2'1:3'3:1':':'3:4':'2:':4')#[8, -6, -33,"
                                            + " -43, -60,"
                                            + " -92](:':':'3:1'2:1'1:':'1:4':'2:6'3:3'1:2':1'1:1'1:'1:)#[2,"
                                            + " -54, -69, -73, -103,"
                                            + " -136]('1:2':1'1:1':'1:1':':':':'1:'1:'1:'1:4'1);");
        te =
                (String)
                        RemixClient.encodedStringCodec
                                .getDecoder()
                                .decode(
                                        "=(:2'3:3'1:'1:':'1:1'1:'1:1'1:':'1:':':'2:'3:'2:1'1:':1'1:1':1':'1:')#[47,"
                                            + " -15, -69, -95, -130,"
                                            + " -167](.\".\".\"1.\"1.1\"1.\"2.1\".1\".3\"2.\".5\"2.\"1.\"1.\"1.\"1.\".\".2\".4\".1\".3\".2\".\"1.\".3)#[29,"
                                            + " 0, -7, -43, -90,"
                                            + " -161]('2:2':1':':1'1:1'2:':':1':':'1:1':1'3:1'1:1'3:1'1:3'1:4'4:')#[22,"
                                            + " -18, -63, -136, -137,"
                                            + " -157](:1':':':2'5:1'2:3':1':3'3:':1'1:':1':':'2:':)#[19,"
                                            + " -32, -70, -70, -93, -116](:':2'3:'2:2'2:2':)#[23,"
                                            + " 9, 9, -1, 8,"
                                            + " -9](:3':2':':2'2:4':4'1:1':':'2:'2:1'1:2':1'3:':':'2:':2'4)#[5,"
                                            + " -27, -89, -118, -185,"
                                            + " -254](:'2:2':'7:'4:'7:'1:1':1)#[36, 27, 14, 10, -2,"
                                            + " -23](.\"1.\".1\"3.3\"1.\"1.1\"1.\".\"1.\"1.2\"5.\".\".\"2.1\"2.\"1.2\"2.\".4\".1\"2.\"3')#[38,"
                                            + " -40, -80, -83, -146,"
                                            + " -191](:1':'2:2'2:1':':':2'1:1'1:1':1':2':3':3':)#[11,"
                                            + " -24, -28, -57, -93,"
                                            + " -101](':':5'2:':':'1:1':1'1:'3:'1:':1'1:'2:'2:1':'6:1'2:)#[64,"
                                            + " 60, 42, 40, 13,"
                                            + " 4](':':1':1'2:3':5':'1:2':':3'1:':':':1':':':1'1:1':3)#[57,"
                                            + " 22, 21, 5, 3,"
                                            + " 1](':':'1:1':2'2:3':':2':'1:':':':':'1:':'1:2)#[36,"
                                            + " 31, 1, -32, -38, -38](':':2'1:'1:'2:1':2':1')#[16,"
                                            + " 11, -1, 15, 2,"
                                            + " -14]('2:1':2':3':2':1':2':'1:'3:'3:'2:1':1'1:3':'1:':'1:2'2)#[12,"
                                            + " -54, -111, -176, -223,"
                                            + " -224]('1:'1:6':':'1:'1:':5'4:'3)#[20, 0, -14, -15,"
                                            + " -25,"
                                            + " -41](':'1:'1:2':2'1:2'7:':':'4:'4:'5:1':'5:1'2:1':2'2:3'1:2':':');");
    }
}
