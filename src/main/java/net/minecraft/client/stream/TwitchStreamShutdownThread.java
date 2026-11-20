package net.minecraft.client.stream;

class TwitchStreamShutdownThread extends Thread {
    TwitchStreamThread1 twitchStreamThread1;

    @Override
    public void run() {
        this.twitchStreamThread1.aOJ.shutdownStream();
    }

    TwitchStreamShutdownThread(TwitchStreamThread1 aOKValue, String text) {
        super(text);
        this.twitchStreamThread1 = aOKValue;
    }
}
