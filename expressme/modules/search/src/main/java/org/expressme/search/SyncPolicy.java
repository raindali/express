package org.expressme.search;

/**
 * Define the synchronization policy: when the reader will get notification of 
 * changes on index and refresh the reader.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public interface SyncPolicy {

    /**
     * If content is sync.
     */
    boolean isSync();

    /**
     * Mark content modified.
     */
    void markModified();

    /**
     * Mark content as sync.
     */
    void markSynchronized();

}
