package remix.compat.legacy.security;

import net.minecraft.entity.Entity;

public class InvalidResourceIdentifierException extends IllegalArgumentException {

    private static Entity[] b;

    private static InvalidResourceIdentifierException method_f1a33d80d088a0e79457(
            InvalidResourceIdentifierException invalidResourceIdentifierException) {
        return invalidResourceIdentifierException;
    }

    public InvalidResourceIdentifierException(
            IdentifiedResource identifiedResource, Throwable throwable) {
        this(identifiedResource, throwable, method_da2ac5c94686ee11e9e6());
    }

    private InvalidResourceIdentifierException(
            IdentifiedResource identifiedResource,
            Throwable throwable,
            Entity[] initialFlowMarker) {
        super(
                String.format(
                        "Resource [%s] must have a valid identifier:%s. ex:%s",
                        identifiedResource.getResource(),
                        identifiedResource.getIdentifier(),
                        throwable));

        Entity[] entityArray = initialFlowMarker;

        Entity[] entityArrayValue = entityArray;

        try {
            if (entityArrayValue != null) {
                Entity.setLegacyGlobalFlowMarker(new String[4]);
            }
        } catch (InvalidResourceIdentifierException invalidResourceIdentifierException) {
            throw method_f1a33d80d088a0e79457(invalidResourceIdentifierException);
        }
    }

    public static void b(Entity[] entityArray) {
        b = entityArray;
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return b;
    }

    // region String decoding support

    // endregion

    static {
        b(null);
    }

    // region String decoding support

    // endregion

    public InvalidResourceIdentifierException(IdentifiedResource identifiedResource) {
        super(
                String.format(
                        "Resource [%s] must have a valid identifier:%s",
                        identifiedResource.getResource()));

        try {
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                b(new Entity[1]);
            }
        } catch (InvalidResourceIdentifierException invalidResourceIdentifierException) {
            throw method_f1a33d80d088a0e79457(invalidResourceIdentifierException);
        }
    }
}
