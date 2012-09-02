package org.expressme.bind2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.ServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("rawtypes")
public class BindUtils {
	static final Log log = LogFactory.getLog(BindUtils.class);
	static final Map<String, ModelAndMethod> modelMap = new HashMap<String, ModelAndMethod>();
	static final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    static final Lock rLock = rwLock.readLock();
    static final Lock wLock = rwLock.writeLock();

    public static <T> T bind(ServletRequest request, String prefix, Class<T> clazz) {
    	ModelAndMethod<T> mam = getModelAndMethod(clazz);
        try {
            // try instance action:
            T model = mam.createModel();
            if (log.isDebugEnabled()) {
                log.debug("Bind parameters to clazz: " + clazz.getName());
            }
            // try to invoke all setters:
            List<String> props = mam.getProperties();
            for (String prop : props) {
                String value = request.getParameter(startingWith(prefix, prop));
                if(value!=null) {
                    // only invoke with non-null value:
                    mam.invokeSetter(model, prop, value);
                }
            }
            props = mam.getArrayProperties();
            for (String prop : props) {
                String[] values = request.getParameterValues(startingWith(prefix, prop));
                if(values!=null) {
                    mam.invokeSetter(model, prop, values);
                }
            }
            return model;
        } catch (Exception e) {
			log.error("bind error", e);
		}
        return null;
    }
    
    static String startingWith(String prefix, String prop) {
    	return (prefix==null?"":prefix.concat(".")).concat(prop);
    }

	@SuppressWarnings("unchecked")
	static <T> ModelAndMethod<T> getModelAndMethod(Class<T> clazz) {
		rLock.lock();
		try {
			ModelAndMethod<T> mam = modelMap.get(clazz.getName());
			if (mam != null)
				return mam;
			rLock.unlock();
			wLock.lock();
			try {
				mam = new ModelAndMethod<T>(clazz);
				modelMap.put(clazz.getName(), mam);
				rLock.lock();
			} finally {
				wLock.unlock();
			}
			return mam;
		} finally {
			rLock.unlock();
		}
	}
}
