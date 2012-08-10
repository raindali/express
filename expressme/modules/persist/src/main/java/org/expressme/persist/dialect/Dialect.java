package org.expressme.persist.dialect;

/**
 * Database dialect for limit select, etc.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public interface Dialect {

    /**
     * Get SQL with limited select.
     * 
     * @param sql Original select SQL statement.
     * @param hasFirstResult Does have first result (offset) ?
     * @param hasMaxResults Does have max results (limit) ?
     * @return SQL with limited select statement.
     */
    String getLimitSQL(String sql, boolean hasFirstResult, boolean hasMaxResults);

    /**
     * Does the <tt>LIMIT</tt> clause come at the start of the
     * <tt>SELECT</tt> statement, rather than at the end?
     * 
     * @return true if limit parameters should come before other parameters
     */
    boolean bindLimitParametersFirst();

    /**
     * ANSI SQL defines the LIMIT clause to be in the form LIMIT offset, limit.
     * Does this dialect require us to bind the parameters in reverse order?
     * 
     * @return true if the correct order is limit, offset
     */
    boolean bindLimitParametersInReverseOrder();

}
