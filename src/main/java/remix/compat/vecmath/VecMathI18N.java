package remix.compat.vecmath;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

class VecMathI18N {


    static String getString(String text) {
        String textValue;
        try {
            textValue =
                    ResourceBundle.getBundle("remix.compat.vecmath.ExceptionStrings").getString(text);
        } catch (MissingResourceException missingresourceexception) {
            System.err.println("VecMathI18N: Error looking up: " + text);
            textValue = text;
        }

        return textValue;
    }
}
