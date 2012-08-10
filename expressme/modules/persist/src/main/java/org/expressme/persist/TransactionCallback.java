package org.expressme.persist;

/**
 * Useful class for simplify transaction.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 * 
 * @param <T> Returning object within a transaction.
 */
public abstract class TransactionCallback<T> {

    private TransactionManager txManager;

    public TransactionCallback(TransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setTransactionManager(TransactionManager txManager) {
        this.txManager = txManager;
    }

    public final T execute() {
        Transaction tx = txManager.beginTransaction();
        try {
            T t = doInTransaction();
            tx.commit();
            return t;
        }
        catch (DataAccessException e) {
            if (! tx.isRollbackOnly())
                tx.rollback();
            throw e;
        }
        catch (Exception e) {
            if (! tx.isRollbackOnly())
                tx.rollback();
            throw new DataAccessException(e);
        }
        catch (Error e) {
            if (! tx.isRollbackOnly())
                tx.rollback();
            throw e;
        }
    }

    protected abstract T doInTransaction() throws Exception;

}
