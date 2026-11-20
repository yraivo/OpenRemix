package net.minecraft.world.storage;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class ThreadedFileIOBase implements Runnable {
    private volatile long writeQueuedCounter;

    private volatile boolean isThreadWaiting;
    private static String f;
    private static ThreadedFileIOBase threadedIOInstance;
    private volatile long savedIOCounter;
    private List threadedIOQueue = Collections.synchronizedList(Lists.newArrayList());

    private ThreadedFileIOBase() {
        Thread thread = new Thread(this, f);
        thread.setPriority(1);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            this.method_784e95624d6d70fe7f50();
        }
    }

    private void method_784e95624d6d70fe7f50() {
        WorldInfo.YP();

        int i = 0;

        while (i < this.threadedIOQueue.size()) {
            IThreadedFileIO iThreadedFileIO = (IThreadedFileIO) this.threadedIOQueue.get(i);
            boolean flag = iThreadedFileIO.writeNextIO();

            try {
                if (false)
                    throw (java.lang.InterruptedException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (!flag) {
                    this.threadedIOQueue.remove(i--);

                    this.savedIOCounter += 1L;
                }
            } catch (InterruptedException interruptedexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        method_372052f7d0d87f976f76(interruptedexception1));
            }

            long j;
            try {
                label44:
                {
                    try {
                        if (false)
                            throw (java.lang.InterruptedException)
                                    null; // Original JVM checked handler; no emitted instructions.
                        if (this.isThreadWaiting) {

                            j = 0L;
                            break label44;
                        }
                    } catch (InterruptedException interruptedexception2) {
                        throw remix.compat.compiler.JvmThrowTypes.flow(
                                method_372052f7d0d87f976f76(interruptedexception2));
                    }

                    j = 10L;
                }

                Thread.sleep(j);
            } catch (InterruptedException interruptedexception3) {
                interruptedexception3.printStackTrace();
            }

            i++;
        }

        if (this.threadedIOQueue.isEmpty()) {
            try {

                Thread.sleep(25L);
            } catch (InterruptedException interruptedexception) {
                interruptedexception.printStackTrace();
            }
        }
    }

    static {
        f = "File IO Thread";
        net.minecraft.world.storage.ThreadedFileIOBase.threadedIOInstance =
                new ThreadedFileIOBase();
    }

    private static InterruptedException method_372052f7d0d87f976f76(
            InterruptedException interruptedexception) {
        return interruptedexception;
    }

    // region String decoding support

    // endregion

    public static ThreadedFileIOBase getThreadedIOInstance() {
        return net.minecraft.world.storage.ThreadedFileIOBase.threadedIOInstance;
    }

    // region String decoding support

    // endregion

    public void queueIO(IThreadedFileIO iThreadedFileIO) {
        if (!this.threadedIOQueue.contains(iThreadedFileIO)) {

            this.writeQueuedCounter += 1L;
            this.threadedIOQueue.add(iThreadedFileIO);
        }
    }

    public void waitForFinish() throws InterruptedException {
        WorldInfo.YP();

        this.isThreadWaiting = true;

        while (this.writeQueuedCounter != this.savedIOCounter) {

            Thread.sleep(10L);
        }

        this.isThreadWaiting = false;
    }
}
