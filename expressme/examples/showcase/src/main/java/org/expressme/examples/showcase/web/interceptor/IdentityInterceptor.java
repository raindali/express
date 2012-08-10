package org.expressme.examples.showcase.web.interceptor;

import org.expressme.modules.web.Identity;
import org.expressme.modules.web.SignOnRequiredException;
import org.expressme.modules.web.security.IdentityManager;
import org.expressme.modules.web.security.SecurityContext;
import org.expressme.webwind.ActionContext;
import org.expressme.webwind.Execution;
import org.expressme.webwind.Interceptor;
import org.expressme.webwind.InterceptorChain;
import org.expressme.webwind.InterceptorOrder;

import com.google.inject.Inject;

/**
 * Interceptor for identity detect.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
@InterceptorOrder(20)
public class IdentityInterceptor implements Interceptor {

    @Inject IdentityManager identityManager;

    public void intercept(Execution execution, InterceptorChain chain) throws Exception {
        ActionContext actionContext = ActionContext.getActionContext();
        Identity identity = identityManager.getIdentity(
                actionContext.getHttpServletRequest(),
                actionContext.getHttpServletResponse()
        );
        if (identity==null || !identity.isAdmin()) {
            String url = actionContext.getHttpServletRequest().getRequestURI();
            if (url.startsWith("/manage/")) {
                throw new SignOnRequiredException();
            }
        }
        SecurityContext.setIdentity(identity);
        try {
            chain.doInterceptor(execution);
        }
        finally {
            SecurityContext.removeIdentity();
        }
    }

}
