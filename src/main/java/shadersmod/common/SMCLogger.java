package shadersmod.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SMCLogger extends Logger {
    private static String a;

    // region String decoding support

    // endregion

    static {
        a = "logs/shadersmod.log";
    }

    // region String decoding support

    // endregion

    public SMCLogger(String text) {
        super(text, (String) null);
        this.setUseParentHandlers(false);
        SMCLogFormatter smcLogFormatter =
                new SMCLogFormatter(null);
        ConsoleHandler consolehandler = new ConsoleHandler();
        consolehandler.setFormatter(smcLogFormatter);
        consolehandler.setLevel(Level.ALL);
        this.addHandler(consolehandler);

        try {
            FileOutputStream fileoutputstream = new FileOutputStream(a, false);
            SMCLogStreamHandler smcLogStreamHandler =
                    new SMCLogStreamHandler(
                            this, fileoutputstream, smcLogFormatter);
            smcLogStreamHandler.setFormatter(smcLogFormatter);
            smcLogStreamHandler.setLevel(Level.ALL);
            this.addHandler(smcLogStreamHandler);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

        this.setLevel(Level.ALL);
    }
}
