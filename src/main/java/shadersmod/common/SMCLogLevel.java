package shadersmod.common;

import java.util.logging.Level;

public class SMCLogLevel extends Level {
    private SMCLogLevel(String text, int p171_1) {
        super(text, p171_1);
    }

    public SMCLogLevel(String text, int p171_1, ShaderLogAccessMarker p171_2) {
        this(text, p171_1);
    }
}
