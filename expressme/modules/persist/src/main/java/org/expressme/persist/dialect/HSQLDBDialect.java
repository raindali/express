package org.expressme.persist.dialect;

import org.expressme.persist.DaoConfigException;

/**
 * Dialect for HSQLDB.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class HSQLDBDialect implements Dialect {

    public String getLimitSQL(String sql, boolean hasFirstResult, boolean hasMaxResults) {
        StringBuilder sb = new StringBuilder(sql.length() + 10);
        sb.append(sql);
        int pos = sql.toLowerCase().indexOf("select");
        if (pos==(-1))
            throw new DaoConfigException("Bad SQL syntax, missing 'select': " + sql);
        pos += 6;
        if (hasFirstResult && hasMaxResults)
            sb.insert(pos, " limit ? ? ");
        else if (!hasFirstResult && hasMaxResults)
            sb.insert(pos, " limit 0 ? ");
        else if (hasFirstResult && !hasMaxResults)
            sb.insert(pos, " limit ? 2147483647 " );
        return sb.toString();
    }

    public boolean bindLimitParametersFirst() {
        return true;
    }

    public boolean bindLimitParametersInReverseOrder() {
        return false;
    }

}
