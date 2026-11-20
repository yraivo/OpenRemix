package remix.compat.legacy.crypto;

import net.minecraft.entity.Entity;

import remix.compat.legacy.auth.LicenseCode4Exception;
import remix.compat.legacy.auth.LicenseCode6Exception;
import remix.compat.legacy.http.AbstractStringPasteEndpoint;
import remix.compat.legacy.http.PastebinStringPasteEndpoint;
import remix.compat.legacy.http.TeknikStringPasteEndpoint;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;
import remix.util.task.NamedValueHolder;

public final class RemoteVersionHashCheck extends AbstractVersionHashCheck {

    private static String[] packedTextFragments;
    private AbstractStringPasteEndpoint bPV;
    public static Class Jh;

    public static boolean d;
    private AbstractStringPasteEndpoint bPW;

    public Double method_77ca1ae9ef52990e8495(NamedValueHolder namedValueHolder) {
        Entity[] entityArray = AbstractVersionHashCheck.method_da2ac5c94686ee11e9e6();
        FluentListWrapper fluentListWrapper = this.ax();
        String[] astring =
                (String[])
                        fluentListWrapper.method_8ca1e132b58cd35591d7(
                                new String[fluentListWrapper.size()]);
        int i = 0;
        Entity[] entityArrayValue = entityArray;
        int j = astring.length;

        while (i < j) {
            if (astring[i].startsWith("1.7.1")
                    && astring[i].contains(packedTextFragments[0])
                    && this.method_e0e3092a59fee58fae52()
                            .equalsIgnoreCase(astring[i].split(packedTextFragments[1])[1])) {
                Jh = LicenseCode6Exception.class;

                return (Double) namedValueHolder.setValue(4.8479E-319);
            }

            i++;
            if (entityArrayValue != null) {
                Entity.setLegacyGlobalFlowMarker(new String[5]);
                break;
            }
        }

        Jh = LicenseCode4Exception.class;

        return (Double) namedValueHolder.setValue(9.6251538E-316);
    }

    private FluentListWrapper ax() {
        FluentListWrapper fluentListWrapper;
        try {
            fluentListWrapper = this.bPW.fetchParsedProfiles();
        } catch (Exception exception2) {
            try {
                d = true;
                fluentListWrapper = this.bPV.fetchParsedProfiles();
            } catch (Exception exception1) {
                fluentListWrapper = FluentCollections.ax();
            }
        }

        try {
            if (fluentListWrapper != null && !fluentListWrapper.isEmpty()) {
                return fluentListWrapper;
            }
        } catch (Exception exception3) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception3));
        }

        try {
            d = true;
            fluentListWrapper = this.bPV.fetchParsedProfiles();
        } catch (Exception exception) {
            fluentListWrapper = FluentCollections.ax();
        }

        return fluentListWrapper;
    }

    static {
        remix.compat.legacy.crypto.RemoteVersionHashCheck.packedTextFragments = new String[3];
        remix.compat.legacy.crypto.RemoteVersionHashCheck.packedTextFragments[0] = new String("-");
        remix.compat.legacy.crypto.RemoteVersionHashCheck.packedTextFragments[1] = new String("-");
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public RemoteVersionHashCheck(String text) {
        this(text, AbstractVersionHashCheck.method_da2ac5c94686ee11e9e6());
    }

    private RemoteVersionHashCheck(String text, net.minecraft.entity.Entity[] initialFlowMarker) {
        super(text);

        this.bPW = new PastebinStringPasteEndpoint(null, new String[] {"Kf7na5sD"});
        this.bPV = new TeknikStringPasteEndpoint(null, new String[] {"TJ6id"});
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            AbstractVersionHashCheck.b(new Entity[2]);
        }
    }
}
