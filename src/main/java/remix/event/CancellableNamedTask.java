package remix.event;

import remix.setting.SettingOption;

import remix.client.ClientServices;

public class CancellableNamedTask implements Cancellable, SettingOption {
    private String b;
    private boolean cancelled;
    private ClientServices clientServices;

    public void setCancelled(boolean flag) {
        this.cancelled = flag;
    }

    public boolean isCancelled() {
        return this.clientServices == null || this.cancelled && this.clientServices.getVerifiedLicenseProfile() != null;
    }

    public void cancel() {
        this.setCancelled(true);
    }

    public CancellableNamedTask(String text) {
        this.b = text;
    }

    public String getDisplayName() {
        return this.b;
    }

    public boolean method_fb2409edd951f8e30da1(ClientServices clientServices) {
        if (this.clientServices == null) {
            this.clientServices = clientServices;
        }

        return this.isCancelled() && clientServices.getVerifiedLicenseProfile() != null;
    }
}
