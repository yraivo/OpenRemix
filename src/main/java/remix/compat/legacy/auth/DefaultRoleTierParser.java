package remix.compat.legacy.auth;

import remix.compat.legacy.codec.IdentityStringTransformer;
import remix.compat.legacy.crypto.Md5LengthValidator;

public final class DefaultRoleTierParser extends remix.compat.legacy.auth.AbstractRoleTierParser {
    public UserRoleTier bM(String text) {
        AbstractRoleTierParser.b();

        UserRoleTier userRoleTier = UserRoleTier.BANNED;

        if (this.getValueValidator().method_c7b139ccf3ff22603b21(text)) {
            return userRoleTier;
        }

        String textValue = (String) this.getValueTransformer().method_69be51ad1433bd242905(text);
        if (userRoleTier.a(textValue)) {
            UserRoleTier[] userRoleTierArray =
                    UserRoleTier.values();
            int i = 0;
            int j = userRoleTierArray.length;

            while (i < j) {
                UserRoleTier userRoleTierValue =
                        userRoleTierArray[i];
                if (userRoleTierValue.method_e0e3092a59fee58fae52().equals(textValue)) {
                    userRoleTier = userRoleTierValue;

                    break;
                }

                i++;
            }
        }

        return userRoleTier;
    }

    public DefaultRoleTierParser() {
        super(new Md5LengthValidator(), new IdentityStringTransformer());
    }

    public UserRoleTier method_96e3e8c70ae398f6f3ff(Object object) {
        return this.bM((String) object);
    }
}
