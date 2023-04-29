package org.lyle.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lyle.listener.ApplicationStartedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 演示拦截器
 */
public class TestInterceptor implements HandlerInterceptor {
	private static final Logger log = LoggerFactory.getLogger(ApplicationStartedListener.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
		log.info("进入到了拦截器里了.................");

		return true;
	}


}
