package org.expressme.examples.showcase.web.interceptor;

import org.expressme.webwind.Execution;
import org.expressme.webwind.Interceptor;
import org.expressme.webwind.InterceptorChain;
import org.expressme.webwind.InterceptorOrder;

@InterceptorOrder(3)
public class SecurityInterceptor implements Interceptor {

	@Override
	public void intercept(Execution execution, InterceptorChain chain) throws Exception {
//		execution.action.method
		chain.doInterceptor(execution);
	}

}
