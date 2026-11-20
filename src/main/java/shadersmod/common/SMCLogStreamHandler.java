package shadersmod.common;

import net.minecraft.entity.Entity;
import shadersmod.common.ShaderLog;

import java.io.OutputStream;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

class SMCLogStreamHandler extends StreamHandler {
    SMCLogger sMCLogger;

    @Override
    public synchronized void publish(LogRecord logrecord) {
        ShaderLog.method_da2ac5c94686ee11e9e6();
        super.publish(logrecord);
        this.flush();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ShaderLog.b(new Entity[1]);
        }
    }

    SMCLogStreamHandler(
            SMCLogger sMCLoggerValue,
            OutputStream outputstream,
            Formatter formatter) {
        super(outputstream, formatter);
        this.sMCLogger = sMCLoggerValue;
    }
}
