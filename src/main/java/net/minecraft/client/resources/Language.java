package net.minecraft.client.resources;

public class Language implements Comparable {
    private String name;
    private String region;
    private boolean bidirectional;
    private static String e;

    private String languageCode;

    @Override
    public boolean equals(Object object) {
        return this == object
                ? true
                : (!(object instanceof Language)
                        ? false
                        : this.languageCode.equals(((Language) object).languageCode));
    }

    // region String decoding support

    // endregion

    public Language(String text, String textValue, String textValue2, boolean bidirectionalValue) {
        this.languageCode = text;
        this.region = textValue;
        this.name = textValue2;
        this.bidirectional = bidirectionalValue;
    }

    public String getLanguageCode() {
        return this.languageCode;
    }

    public boolean isBidirectional() {
        return this.bidirectional;
    }

    // region String decoding support

    // endregion

    public int compareTo(Language language) {
        return this.languageCode.compareTo(language.languageCode);
    }

    @Override
    public int hashCode() {
        return this.languageCode.hashCode();
    }

    @Override
    public String toString() {
        return String.format(e, this.name, this.region);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Language) object);
    }

    static {
        e = "%s (%s)";
    }
}
