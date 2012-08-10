package org.expressme.examples.showcase.web.interceptor;

import org.expressme.persist.Transaction;
import org.expressme.persist.TransactionManager;
import org.expressme.webwind.Execution;
import org.expressme.webwind.Interceptor;
import org.expressme.webwind.InterceptorChain;
import org.expressme.webwind.InterceptorOrder;

import com.google.inject.Inject;

/**
 * Interceptor for JDBC transaction.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
@InterceptorOrder(10)
public class JdbcTransactionInterceptor implements Interceptor {

    @Inject TransactionManager txManager;

    public void intercept(Execution execution, InterceptorChain chain) throws Exception {
        Transaction tx = txManager.beginTransaction();
        try {
            chain.doInterceptor(execution);
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

}
