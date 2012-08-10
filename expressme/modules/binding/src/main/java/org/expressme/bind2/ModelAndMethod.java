package org.expressme.bind2;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Holds Action and Method. This class is used to cache properties of Action 
 * class to improve runtime performance.
 * 
 * @author Xuefeng
 */
public class ModelAndMethod<T> {

    private Log log = LogFactory.getLog(getClass());

    private final Class<?> modelClass;

    // store setXxx(String):
    private List<String> propertyList = new ArrayList<String>();
    // store setXxx(String[]):
    private List<String> arrayPropertyList = new ArrayList<String>();

    private Map<String, ParameterType> parameterMap = new HashMap<String, ParameterType>();
    private Map<String, ParameterType> arrayParameterMap = new HashMap<String, ParameterType>();

    public ModelAndMethod(Class<?> modelClass) {
        this.modelClass = modelClass;
        Method[] methods = modelClass.getMethods();
        for(Method method : methods) {
            // find setXxx(Object):
            String setterName = getSingleSetterName(method);
            if(setterName!=null) {
                if(log.isDebugEnabled()) {
                    log.debug("Setter found in "
                            + modelClass.getName()
                            + ": void "
                            + setterName
                            + "("
                            + method.getParameterTypes()[0].getName()
                            + ")");
                }
                propertyList.add(setterName);
                parameterMap.put(setterName, new ParameterType(method, method.getParameterTypes()[0]));
            }
            // find setXxx(Object[]):
            setterName = getArraySetterName(method);
            if(setterName!=null) {
                if(log.isDebugEnabled()) {
                    log.debug("Setter found in "
                            + modelClass.getName()
                            + ": void "
                            + setterName
                            + "("
                            + method.getParameterTypes()[0].getName()
                            + ")");
                }
                arrayPropertyList.add(setterName);
                arrayParameterMap.put(setterName, new ParameterType(method, method.getParameterTypes()[0]));
            }
        }
    }

    public final Class<?> getModelClass() {
        return modelClass;
    }

    public final List<String> getProperties() {
        return propertyList;
    }

    public final List<String> getArrayProperties() {
        return arrayPropertyList;
    }

    public final void invokeSetter(T action, String propertyName, String value) {
        ParameterType p = parameterMap.get(propertyName);
        if(p!=null) {
            Method m = p.getMethod();
            try {
                m.invoke(action, p.convert(value));
            }
            catch(Exception e) {
                if(log.isDebugEnabled())
                    log.debug("Invoke setter failed: " + m.getName(), e);
            }
        }
    }

    public final void invokeSetter(T action, String propertyName, String[] values) {
        ParameterType p = arrayParameterMap.get(propertyName);
        if(p!=null) {
            Method m = p.getMethod();
            try {
                m.invoke(action, p.convert(values));
            }
            catch(Exception e) {
                if(log.isDebugEnabled())
                    log.debug("Invoke setter failed: " + m.getName(), e);
            }
        }
    }

    private String getSingleSetterName(Method method) {
        String name = method.getName();
        if(!name.startsWith("set"))
            return null;
        if(name.length()<4)
            return null;
        if(!method.getReturnType().equals(void.class))
            return null;
        Class<?>[] params = method.getParameterTypes();
        if(params.length!=1)
            return null;
        if(!ParameterType.isSupportSingle(params[0]))
            return null;
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    private String getArraySetterName(Method method) {
        String name = method.getName();
        if(!name.startsWith("set"))
            return null;
        if(name.length()<4)
            return null;
        if(!method.getReturnType().equals(void.class))
            return null;
        Class<?>[] params = method.getParameterTypes();
        if(params.length!=1)
            return null;
        if(!ParameterType.isSupportArray(params[0]))
            return null;
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }
    
    @SuppressWarnings("unchecked")
	public T createModel() {
    	try {
			return (T) modelClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
    	return null;
    }

}
