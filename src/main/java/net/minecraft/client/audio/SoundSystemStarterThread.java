package net.minecraft.client.audio;

import paulscode.sound.CommandObject;
import paulscode.sound.Library;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.Source;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SoundSystemStarterThread extends SoundSystem {
    SoundManager field_148591_a;

    private volatile List<CommandObject> asyncCommandQueue;
    private volatile ListenerPose pendingListener;
    private ListenerPose appliedListener;
    private volatile long playbackRequest;
    private volatile long sampledPlaybackRequest;
    private volatile Set<String> playingSources = Collections.emptySet();

    public SoundSystemStarterThread(SoundManager soundManager, Object object) {
        this(soundManager);
    }

    private SoundSystemStarterThread(SoundManager soundManager) {
        this.field_148591_a = soundManager;
        // The library starts and initializes its command thread in the superclass constructor.
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (this.commandQueue != null) {
                this.commandQueue = Collections.synchronizedList(new LinkedList<>(this.commandQueue));
                this.asyncCommandQueue = this.commandQueue;
            }
        }
    }

    @Override
    public boolean CommandQueue(CommandObject command) {
        if (command != null) {
            // Appending a command only needs the list's lock. The mixer holds THREAD_SYNC
            // while it processes the queue, which can include decoding or OpenAL work.
            List<CommandObject> queue = this.asyncCommandQueue;
            return queue != null ? queue.add(command) : super.CommandQueue(command);
        }

        long request = this.playbackRequest;
        boolean hasPendingCommands = super.CommandQueue(null);
        if (request != this.sampledPlaybackRequest) {
            this.updatePlaybackSnapshot();
            this.sampledPlaybackRequest = request;
        }
        return hasPendingCommands;
    }

    void setListenerPose(float x, float y, float z, float lookX, float lookY, float lookZ,
            float upX, float upY, float upZ) {
        ListenerPose previous = this.pendingListener;
        if (previous == null || !previous.matches(x, y, z, lookX, lookY, lookZ, upX, upY, upZ)) {
            this.pendingListener = new ListenerPose(x, y, z, lookX, lookY, lookZ, upX, upY, upZ);
            this.interruptCommandThread();
        }
    }

    @Override
    protected void ManageSources() {
        if (this.pendingListener == this.appliedListener) {
            return;
        }
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            ListenerPose listener = this.pendingListener;
            if (listener != null && this.soundLibrary != null) {
                listener.applyTo(this.soundLibrary);
                this.appliedListener = listener;
            }
        }
    }

    Set<String> requestPlaybackSnapshot() {
        Set<String> previous = this.sampledPlaybackRequest == this.playbackRequest
                ? this.playingSources : null;
        ++this.playbackRequest;
        this.interruptCommandThread();
        return previous;
    }

    @Override
    public boolean playing(String name) {
        return this.playingSources.contains(name);
    }

    private void updatePlaybackSnapshot() {
        Set<String> active = new HashSet<>();
        synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (this.soundLibrary != null) {
                for (Map.Entry<String, Source> entry : this.soundLibrary.getSources().entrySet()) {
                    Source source = entry.getValue();
                    if (source.playing() || source.paused() || source.preLoad) {
                        active.add(entry.getKey());
                    }
                }
            }
        }
        this.playingSources = active;
    }

    @Override
    public void cleanup() {
        super.cleanup();
        this.asyncCommandQueue = null;
        this.pendingListener = null;
        this.playingSources = Collections.emptySet();
    }

    private static final class ListenerPose {
        private final float x, y, z;
        private final float lookX, lookY, lookZ;
        private final float upX, upY, upZ;

        private ListenerPose(float x, float y, float z, float lookX, float lookY, float lookZ,
                float upX, float upY, float upZ) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.lookX = lookX;
            this.lookY = lookY;
            this.lookZ = lookZ;
            this.upX = upX;
            this.upY = upY;
            this.upZ = upZ;
        }

        private boolean matches(float x, float y, float z, float lookX, float lookY, float lookZ,
                float upX, float upY, float upZ) {
            return this.x == x && this.y == y && this.z == z
                    && this.lookX == lookX && this.lookY == lookY && this.lookZ == lookZ
                    && this.upX == upX && this.upY == upY && this.upZ == upZ;
        }

        private void applyTo(Library library) {
            library.setListenerPosition(this.x, this.y, this.z);
            library.setListenerOrientation(this.lookX, this.lookY, this.lookZ, this.upX, this.upY, this.upZ);
        }
    }
}
