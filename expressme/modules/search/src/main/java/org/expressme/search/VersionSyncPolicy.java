package org.expressme.search;

/**
 * Synchronize policy by version check, that means, after each modification, the 
 * reader will see a change notification immediately.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class VersionSyncPolicy implements SyncPolicy {

    private volatile int readVersion = 0;
    private volatile int writeVersion = 0;

    /**
     * Init sync state as synchronized.
     */
    public VersionSyncPolicy() {
        this(true);
    }

    public VersionSyncPolicy(boolean synced) {
        if (! synced)
            markModified();
    }

    public boolean isSync() {
        return readVersion == writeVersion;
    }

    public void markModified() {
        writeVersion ++;
    }

    public void markSynchronized() {
        readVersion = writeVersion;
    }

}
