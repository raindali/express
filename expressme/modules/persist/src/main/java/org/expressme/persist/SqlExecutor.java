package org.expressme.persist;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Execute SQL.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public abstract class SqlExecutor {

    protected final Log log = LogFactory.getLog(getClass());
    protected static final Object[] EMPTY_ARGS = new Object[0];

    public abstract Object execute(TransactionManager txManager, Object... args);

    boolean isInt(Class<?> type) {
        return int.class.equals(type) || Integer.class.equals(type);
    }

    Annotation[] getAnnotationsOfArgs(Method method) {
        Class<?>[] types = method.getParameterTypes();
        Annotation[][] annoss = method.getParameterAnnotations();
        Annotation[] ret = new Annotation[types.length];
        for (int i=0; i<types.length; i++) {
            ret[i] = getArgAnnotation(method, types[i], annoss[i]);
        }
        return ret;
    }

    Annotation getArgAnnotation(Method method, Class<?> type, Annotation[] annos) {
        boolean firstResult = false;
        boolean maxResults = false;
        boolean param = false;
        Annotation ret = null;
        for (Annotation anno : annos) {
            if (anno.annotationType().equals(FirstResult.class)) {
                firstResult = true;
                ret = anno;
            }
            if (anno.annotationType().equals(MaxResults.class)) {
                maxResults = true;
                ret = anno;
            }
            if (anno.annotationType().equals(Param.class)) {
                param = true;
                ret = anno;
            }
        }
        if (ret == null)
            throw new DaoConfigException("Missing @Param or @FirstResult or @MaxResults on the parameters of method: " + method.toString());
        if (firstResult && maxResults)
            throw new DaoConfigException("Cannot put both @FirstResult and @MaxResults on the same parameter in method: " + method.toString());
        if (firstResult && !isInt(type))
            throw new DaoConfigException("Cannot put both @FirstResult on parameter of type '" + type.getName() + "' in method: " + method.toString());
        if (maxResults && !isInt(type))
            throw new DaoConfigException("Cannot put both @MaxResults on parameter of type '" + type.getName() + "' in method: " + method.toString());
        if (param && firstResult)
            throw new DaoConfigException("Cannot put both @Param and @FirstResult on the same parameter in method: " + method.toString());
        if (param && maxResults)
            throw new DaoConfigException("Cannot put both @Param and @MaxResults on the same parameter in method: " + method.toString());
        return ret;
    }

    int indexOfArgByName(Method method, Annotation[] annos, String argName) {
        int pos = argName.indexOf('.');
        if (pos!=(-1))
            argName = argName.substring(0, pos);
        for (int i=0; i<annos.length; i++) {
            Annotation anno = annos[i];
            if (anno.annotationType().equals(Param.class)) {
                if (((Param)anno).value().equals(argName))
                    return i;
            }
        }
        throw new DaoConfigException("Missing @Param(\"" + argName + "\") in parameter of method: " + method.toString());
    }

    Object[] getSqlParams(int[] argIndex, Method[] argGets, Object[] args) {
        if (argIndex.length==0)
            return EMPTY_ARGS;
        Object[] reorderedArgs = new Object[argIndex.length];
        for (int i=0; i<argIndex.length; i++) {
            Method m = argGets[i];
            if (m==null) {
                reorderedArgs[i] = args[argIndex[i]];
            }
            else {
                try {
                    reorderedArgs[i] = m.invoke(args[argIndex[i]]);
                }
                catch(Exception e) {
                    throw new DataAccessException("Invoke getter failed: " + m.toString(), e);
                }
            }
        }
        return reorderedArgs;
    }

    protected String methodToString(Method method) {
        StringBuilder sb = new StringBuilder(128);
        sb.append(method.getReturnType().getSimpleName())
          .append(' ')
          .append(method.getName())
          .append('(');
        Class<?>[] types = method.getParameterTypes();
        for (Class<?> t : types) {
            sb.append(t.getSimpleName()).append(',');
        }
        if (types.length>0)
            sb.deleteCharAt(sb.length()-1);
        sb.append(')');
        return sb.toString();
    }

    String getPropertyName(String argName) {
        int pos = argName.indexOf('.');
        if (pos==(-1))
            return null;
        return argName.substring(pos + 1);
    }

    Method findGetterByPropertyName(Class<?> clazz, String prop) {
        String n = Character.toUpperCase(prop.charAt(0)) + prop.substring(1);
        String getter1 = "get" + n;
        String getter2 = "is" + n;
        Method[] ms = clazz.getMethods();
        for (Method m : ms) {
            Class<?> rt = m.getReturnType();
            if (m.getParameterTypes().length==0 && !rt.equals(void.class)) {
                if (getter1.equals(m.getName())) {
                    return m;
                }
                if (getter2.equals(m.getName()) && (rt.equals(boolean.class) || rt.equals(Boolean.class))) {
                    return m;
                }
            }
        }
        throw new DaoConfigException("Cannot find getter of property '" + prop + "' of class: " + clazz.getName());
    }
}
