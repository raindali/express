package org.expressme.search;

/**
 * Synchronize policy by delay notification. That means, after a modification, 
 * and during the certain time, the reader will not get notification of change, 
 * but after the certain delay, the reader will get the notification of change.
 * 
 * <pre>
 * SyncPolicy policy = new DelaySyncPolicy(3000); // 3 seconds delay
 * policy.isSync(); // true!
 * ...
 * policy.markModified();
 * // after 2 seconds
 * policy.isSync(); // true!
 * // after 2 seconds:
 * policy.isSync(); // false! because 4 seconds passed, more than delay (3 sec).
 * </pre>
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class DelaySyncPolicy implements SyncPolicy {

    private volatile long lastReadTime;
    private volatile long lastWriteTime;

    private final long delay;

    public DelaySyncPolicy(long delayInMilliSeconds) {
        this.delay = delayInMilliSeconds;
        lastReadTime = lastWriteTime = System.currentTimeMillis();
    }

    public boolean isSync() {
        if (lastReadTime==lastWriteTime)
            return true;
        return System.currentTimeMillis() < (lastWriteTime + delay);
    }

    public void markModified() {
        lastWriteTime = System.currentTimeMillis();
        if (lastReadTime==lastWriteTime)
            lastReadTime --;
    }

    public void markSynchronized() {
        lastReadTime = lastWriteTime = System.currentTimeMillis();
    }

    /**
     * Debug purpose only.
     */
    @Override
    public String toString() {
        return "Last write: " + (lastWriteTime % 100000) + ", Last read: " + (lastReadTime % 100000) + ", Current = " + (System.currentTimeMillis() % 100000) + ", isSync = " + isSync();
    }
}
