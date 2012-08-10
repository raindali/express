import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.expressme.persist.DaoConfigException;
import org.expressme.persist.JdbcUtils;
import org.expressme.persist.MappedBy;
import org.expressme.persist.Query;
import org.expressme.persist.QuerySqlExecutor;
import org.expressme.persist.dialect.MySQLDialect;
import org.expressme.persist.mapper.BeanRowMapper;
import org.expressme.persist.mapper.MapperUtils;
import org.expressme.persist.mapper.ObjectArrayRowMapper;
import org.expressme.persist.mapper.RowMapper;
import org.junit.Test;


public class QuerySqlExcutorTest {
	
	@Test
	public void test() throws Exception {
		MySQLDialect dialect = new MySQLDialect();
		for (Method method : DocsDao.class.getDeclaredMethods()) {
			try {
				System.out.println(method.toString());
				if (method.getAnnotation(Query.class) != null)
					new QuerySqlExecutor(method, dialect);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Method m0 = DocsDao.class.getMethod("queryId", int.class);
		Method m1 = DocsDao.class.getMethod("queryById", int.class);
		Method m2 = DocsDao.class.getMethod("queryByGoodsId", int.class);
		System.out.println("m0:"+JdbcUtils.getRowMapperGenericType(m0));
		System.out.println("m1:"+JdbcUtils.getRowMapperGenericType(m1));
		System.out.println("m2:"+JdbcUtils.getRowMapperGenericType(m2));
		
		if (m0.getGenericReturnType() instanceof Class<?>) {
			m0.getReturnType();
			if (m0.getReturnType().isPrimitive())
				System.out.println("09 : " +m0.getGenericReturnType());
			System.out.println(m0.getGenericReturnType() == int.class);
			return;
		}
		
		Class<?> returnType = m0.getReturnType();
		Class<? extends RowMapper<?>> rowMapper = null;
		if (returnType.isPrimitive())
		{
			returnType = getWrapperClass(returnType);
			rowMapper = MapperUtils.getRowMapper(returnType);
		}
		else {
			MappedBy mappedBy = m0.getAnnotation(MappedBy.class);
			rowMapper = (Class<? extends RowMapper<?>>) (mappedBy==null ? ObjectArrayRowMapper.class : BeanRowMapper.class);
		}
		System.out.println(rowMapper);;
	
	}
	
    Class<?> getWrapperClass(Class<?> returnType) {
        if (boolean.class.equals(returnType))
            return Boolean.class;
        if (byte.class.equals(returnType))
            return Byte.class;
        if (char.class.equals(returnType))
            return Character.class;
        if (short.class.equals(returnType))
            return Short.class;
        if (int.class.equals(returnType))
            return Integer.class;
        if (long.class.equals(returnType))
            return Long.class;
        if (float.class.equals(returnType))
            return Float.class;
        if (double.class.equals(returnType))
            return Double.class;
        throw new DaoConfigException("No wrapper class for primitive type: " + returnType);
    }
}
