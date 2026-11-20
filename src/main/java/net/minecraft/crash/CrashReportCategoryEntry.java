package net.minecraft.crash;

class CrashReportCategoryEntry {

    private String value;
    private String key;

    public String getKey() {
        return this.key;
    }


    public String getValue() {
        return this.value;
    }

    public CrashReportCategoryEntry(String text, Object object) {
        this.key = text;
        if (object == null) {
            this.value = "~~NULL~~";
        } else if (object instanceof Throwable) {
            Throwable throwable = (Throwable) object;
            this.value =
                    "~~ERROR~~ "
                            + throwable.getClass().getSimpleName()
                            + ": "
                            + throwable.getMessage();
        } else {
            this.value = object.toString();
        }
    }
}
