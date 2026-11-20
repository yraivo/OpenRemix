package remix.client.lifecycle;

import java.io.Serializable;

public interface ServiceComponentState extends Serializable {
    boolean passesLegacyStateCheck();
}
