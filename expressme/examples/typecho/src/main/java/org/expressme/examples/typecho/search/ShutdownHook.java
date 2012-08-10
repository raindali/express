package org.expressme.examples.typecho.search;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShutdownHook {

    class Shutdown {
        private Object object;
        private String method;

        Shutdown(Object object, String method) {
            this.object = object;
            this.method = method;
        }

        void shutdown() throws Exception {
            object.getClass().getMethod(method).invoke(object);
        }

        @Override
        public String toString() {
            return object + " [" + method + "]";
        }
    }

    private Log log = LogFactory.getLog(getClass());
    private List<Shutdown> shutdownList = new ArrayList<Shutdown>();

    public void addShutdownHook(Object object, String method) {
        shutdownList.add(new Shutdown(object, method));
    }

    public void shutdown() {
        for (Shutdown shutdown : shutdownList) {
            try {
                shutdown.shutdown();
            }
            catch(Exception e) {
                log.error("Destroy object '" + shutdown + "' failed.", e);
            }
        }
    }
}
