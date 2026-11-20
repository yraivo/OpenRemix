package remix.client;

import remix.client.lifecycle.ServiceComponentState;

public abstract class ClientMetadata implements ServiceComponentState {
    private String version;
    private String name;
    private String[] authors;

    public ClientMetadata(String text, String textValue, String[] astring) {
        this.name = text;
        this.version = textValue;
        this.authors = astring;
    }

    public String getVersion() {
        return this.version;
    }

    public String[] getAuthors() {
        return this.authors;
    }

    public String getName() {
        return this.name;
    }
}
