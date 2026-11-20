package optifine;

import java.util.HashMap;

final class BlockingHttpResponseListener implements HttpListener {

    HashMap val$hashMap;

    public void failed(HttpRequest httpRequest, Exception exception) {
        HashMap hashmap = this.val$hashMap;
        synchronized (this.val$hashMap) {
            this.val$hashMap.put("Exception", exception);
            this.val$hashMap.notifyAll();
        }
    }

    BlockingHttpResponseListener(HashMap hashmap) {
        this.val$hashMap = hashmap;
    }

    public void finished(HttpRequest httpRequest, HttpResponse httpResponse) {
        HashMap hashmap = this.val$hashMap;
        synchronized (this.val$hashMap) {
            this.val$hashMap.put("Response", httpResponse);
            this.val$hashMap.notifyAll();
        }
    }


}
