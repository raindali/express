package org.expressme.examples.showcase.web.interceptor;

import java.lang.reflect.Method;

import org.expressme.modules.web.Container;
import org.expressme.modules.web.ContainerContext;
import org.expressme.webwind.Execution;
import org.expressme.webwind.Interceptor;
import org.expressme.webwind.InterceptorChain;
import org.expressme.webwind.InterceptorOrder;

import com.dali.validator.ValidatorUtils;
import com.dali.validator.annotation.Valid;
import com.google.inject.Binding;

@InterceptorOrder(1)
public class BindAndValidatorInterceptor implements Interceptor {

	@SuppressWarnings("unchecked")
	@Override
	public void intercept(Execution execution, InterceptorChain chain) throws Exception {
		Method method = execution.getAction().method;
		Valid binding = method.getAnnotation(Valid.class);
		if (binding == null) {
			binding = method.getDeclaringClass().getAnnotation(Valid.class);
		}
//		if (binding != null) {
//			// 将request参数绑定到bean上
//			Container container = (Container) BindingExecutor.bindParameters(binding.value(), execution.request.getParameterMap());
//			ContainerContext.setContainerContext(container);
//			ValidatorUtils.validator(container, method.getName());
//		}
//		chain.doInterceptor(execution);
	}

}
