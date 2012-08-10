package org.expressme.search;

import static org.junit.Assert.*;

import org.junit.Test;

public class DelaySyncPolicyTest {

    final long DELAY_1 = 1000; // 1 second
    final long DELAY_2 = 2000; // 2 second
    final long DELAY_3 = 3000; // 3 second

    @Test
    public void testDelaySync1() {
        SyncPolicy sync = new DelaySyncPolicy(DELAY_2);
        assertTrue(sync.isSync());
        sync.markModified();
        assertTrue(sync.isSync());

        sleep(DELAY_1);

        assertTrue(sync.isSync());

        sleep(DELAY_2);

        assertFalse(sync.isSync());
    }

    @Test
    public void testDelaySync2() {
        SyncPolicy sync = new DelaySyncPolicy(DELAY_2);
        assertTrue(sync.isSync());
        sync.markModified();
        assertTrue(sync.isSync());

        sleep(DELAY_3);

        assertFalse(sync.isSync());
        sync.markSynchronized();
        assertTrue(sync.isSync());

        sleep(DELAY_1);

        assertTrue(sync.isSync());

        sleep(DELAY_3);

        assertTrue(sync.isSync());
        sync.markModified();
        assertTrue(sync.isSync());

        sleep(DELAY_3);

        assertFalse(sync.isSync());
    }

    void sleep(long ms) {
        try {
            Thread.sleep(ms);
        }
        catch(InterruptedException e) {
            throw new RuntimeException();
        }
    }

}
