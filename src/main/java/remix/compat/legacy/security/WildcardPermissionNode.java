package remix.compat.legacy.security;

import remix.command.AbstractClientCommand;

import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;

public final class WildcardPermissionNode {
    private Set<String> set;
    private static final String a;
    private boolean e;
    private String c;
    public static WildcardPermissionNode iR;
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    public String method_e0e3092a59fee58fae52() {
        return this.c;
    }

    static {
        remix.compat.legacy.security.WildcardPermissionNode.packedTextFragments = new String[8];
        remix.compat.legacy.security.WildcardPermissionNode.packedTextFragments[0] = new String("*");
        remix.compat.legacy.security.WildcardPermissionNode.packedTextFragments[1] = new String("*");
        remix.compat.legacy.security.WildcardPermissionNode.packedTextFragments[2] = new String(".");
        remix.compat.legacy.security.WildcardPermissionNode.packedTextFragments[3] = new String(".");
        remix.compat.legacy.security.WildcardPermissionNode.packedTextFragments[4] = new String("*");
        remix.compat.legacy.security.WildcardPermissionNode.packedTextFragments[5] = new String("*");
        remix.compat.legacy.security.WildcardPermissionNode.packedTextFragments[6] = new String("jv'/");
        remix.compat.legacy.security.WildcardPermissionNode.packedTextFragments[7] = new String("*");
        a = WildcardPermissionNode.packedTextFragments[5];
        iR = new WildcardPermissionNode("root", Collections.singleton(packedTextFragments[7]));
    }

    public boolean a(String text) {
        int i = AbstractClientCommand.getLegacyCommandFlowMarker();
        if (!this.e && !this.set.contains(text) && !text.equals(packedTextFragments[1])) {
            String[] astring = text.split(Pattern.quote(packedTextFragments[2]));

            for (String textValue : this.set) {
                String[] astring1 = textValue.split(Pattern.quote(packedTextFragments[3]));
                int j = 0;

                while (true) {
                    if (j < astring.length && j < astring1.length) {
                        String textValue2 = astring[j];
                        String textValue3 = astring1[j];
                        if (!textValue2.equals(textValue3)
                                && !textValue3.equals(packedTextFragments[4])) {
                            return false;
                        }

                        j++;
                        if (i == 0) {
                            continue;
                        }
                    }

                    if (i != 0) {
                        return true;
                    }
                    break;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public WildcardPermissionNode(String text, Set<String> set) {
        this.c = text;
        this.set = set;
        this.e = set.contains(packedTextFragments[0]);
    }

    public Set<String> bu() {
        return this.set;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
