package remix.compat.legacy.security;

public class IdentifiedResource<O> {
    private O resource;
    private String identifier;
    private static String[] legacyResourceMarker;

    static {
        if (getLegacyResourceMarker() != null) {
            setLegacyResourceMarker(new String[1]);
        }
    }

    public static String[] getLegacyResourceMarker() {
        return remix.compat.legacy.security.IdentifiedResource.legacyResourceMarker;
    }

    public IdentifiedResource(O object, String text) {
        this.resource = (O) object;
        this.identifier = text;
    }

    public static void setLegacyResourceMarker(String[] astring) {
        remix.compat.legacy.security.IdentifiedResource.legacyResourceMarker = astring;
    }

    public O getResource() {
        return this.resource;
    }

    public String getIdentifier() {
        return this.identifier;
    }
}
