package org.expressme.search;

import static org.junit.Assert.*;

import org.junit.Test;

public class VersionSyncPolicyTest {

    @Test
    public void testInitialSynced() {
        SyncPolicy sync = new VersionSyncPolicy(true);
        assertTrue(sync.isSync());
        sync.markModified();
        assertFalse(sync.isSync());
        sync.markSynchronized();
        assertTrue(sync.isSync());
    }

    @Test
    public void testInitialNotSynced() {
        SyncPolicy sync = new VersionSyncPolicy(false);
        assertFalse(sync.isSync());
        sync.markSynchronized();
        assertTrue(sync.isSync());
        sync.markModified();
        assertFalse(sync.isSync());
    }

}
