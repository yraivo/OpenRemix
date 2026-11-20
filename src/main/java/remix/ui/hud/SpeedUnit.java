package remix.ui.hud;

import remix.ui.hud.WatermarkHudElement;

public enum SpeedUnit {
    BLOCKSPERTICK(1.0F, "bpt"),
    BLOCKSPERSECOND(0.05F, "bps"),
    METERSPERSECOND(0.05F, "ms"),
    KILOMETERSPERHOUR(0.0138889F, "kmh"),
    MILESPERHOUR(0.022352F, "mph");

    public String name;
    public String gv;
    private String b;
    private float gw;

    public double method_0521fd60892429b00fdd(double d0) {
        return d0 * this.gw;
    }


    // region String decoding support

    // endregion

    public static SpeedUnit fromName(String text) {
        SpeedUnit[] speedUnitArray = ag();
        WatermarkHudElement.isWatermarkDisabled();

        int length = speedUnitArray.length;
        int j = 0;

        while (j < length) {
            SpeedUnit speedUnit = speedUnitArray[j];
            if (speedUnit.b.equals(text)) {
                return speedUnit;
            }

            j++;
        }

        return null;
    }

    public String method_e0e3092a59fee58fae52() {
        return this.b;
    }

    public static SpeedUnit[] ag() {
        return values();
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public double convert(double d0) {
        return d0 / this.gw;
    }

    @Override
    public final String toString() {
        return this.name;
    }

    SpeedUnit(float f, String nameValue) {
        this.gw = f;
        this.name = nameValue;
        this.gv = this.name;
        this.b = nameValue;
    }
}
