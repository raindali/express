package org.expressme.persist;

/**
 * Make a DAO support JDBC batch.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public interface BatchSupport {

    /**
     * Prepare resources for batch.
     */
    void prepareBatch();

    /**
     * Execute the batch updates.
     * 
     * @return Array of integer returned by each update.
     */
    int[] executeBatch();

    /**
     * Clean up any resources used by batch.
     */
    void closeBatch();
}
