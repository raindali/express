package org.expressme.persist.dialect;

/**
 * Dialect for Oracle.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class OracleDialect implements Dialect {

    public String getLimitSQL(String sql, boolean hasFirstResult, boolean hasMaxResults) {
        boolean isForUpdate = sql.toLowerCase().endsWith(" for update");
        if (isForUpdate)
            sql = sql.substring(0, sql.length()-11);
        StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);
        if (hasFirstResult && hasMaxResults) {
            pagingSelect.append("select * from (select row_.*, rownum rownum_ from (")
                        .append(sql)
                        .append(") row_ where rownum <= ?) where rownum_ > ?");
        }
        else if (!hasFirstResult && hasMaxResults) {
            pagingSelect.append("select * from (")
                        .append(sql)
                        .append(") where rownum <= ?");
        }
        else if (hasFirstResult && !hasMaxResults) {
            pagingSelect.append("select * from (")
                        .append(sql)
                        .append(") where rownum > ?");
        }
        if (isForUpdate)
            pagingSelect.append(" for update");
        return pagingSelect.toString();
    }

    public boolean bindLimitParametersFirst() {
        return false;
    }

    public boolean bindLimitParametersInReverseOrder() {
        return true;
    }

}
