package shadersmod.common;

import net.minecraft.entity.Entity;

import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class SMCLogFormatter extends Formatter {
    private static String b;
    int a = Calendar.getInstance().getTimeZone().getRawOffset();
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    SMCLogFormatter(ShaderLogFormatterAccessMarker accessMarker1) {
        this();
    }

    // region String decoding support

    // endregion

    private SMCLogFormatter() {}

    static {
        shadersmod.common.SMCLogFormatter.packedTextFragments = new String[7];
        shadersmod.common.SMCLogFormatter.packedTextFragments[0] = new String("[");
        shadersmod.common.SMCLogFormatter.packedTextFragments[1] = new String("]");
        shadersmod.common.SMCLogFormatter.packedTextFragments[2] = new String("[");
        shadersmod.common.SMCLogFormatter.packedTextFragments[3] = new String("]");
        shadersmod.common.SMCLogFormatter.packedTextFragments[4] = new String(" ");
        shadersmod.common.SMCLogFormatter.packedTextFragments[5] = new String("\n");
        shadersmod.common.SMCLogFormatter.packedTextFragments[6] = new String("w68IQA.");
        b = "Shaders";
    }

    // region String decoding support

    // endregion

    @Override
    public String format(LogRecord logrecord) {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(packedTextFragments[0]);
        Entity[] entityArray = ShaderLog.method_da2ac5c94686ee11e9e6();
        stringbuilder.append(b).append(packedTextFragments[1]);
        Entity[] entityArrayValue = entityArray;
        if (logrecord.getLevel() != ShaderLog.PR) {
            stringbuilder
                    .append(packedTextFragments[2])
                    .append(logrecord.getLevel())
                    .append(packedTextFragments[3]);
        }

        stringbuilder.append(packedTextFragments[4]);
        stringbuilder.append(logrecord.getMessage()).append(packedTextFragments[5]);
        String text = stringbuilder.toString();
        if (entityArrayValue != null) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }

        return text;
    }
}
