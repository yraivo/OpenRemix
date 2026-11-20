package optifine;

public class LagometerTimerNano {
    public long timeNano;
    public long timeStartNano;

    public void end() {
        if (Lagometer.active) {

            if (this.timeStartNano != 0L) {
                this.timeNano = this.timeNano + (System.nanoTime() - this.timeStartNano);

                this.timeStartNano = 0L;
            }
        }
    }

    public void start() {
        if (Lagometer.active) {

            if (this.timeStartNano == 0L) {
                this.timeStartNano = System.nanoTime();
            }
        }
    }

    private void reset() {

        this.timeNano = 0L;

        this.timeStartNano = 0L;
    }

    static void resetTimer(LagometerTimerNano lagometerTimerNano) {
        lagometerTimerNano.reset();
    }

    public LagometerTimerNano() {

        this.timeStartNano = 0L;

        this.timeNano = 0L;
    }
}
