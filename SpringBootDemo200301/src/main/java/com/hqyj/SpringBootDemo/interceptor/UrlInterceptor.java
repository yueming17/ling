package com.hqyj.SpringBootDemo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class UrlInterceptor implements HandlerInterceptor {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UrlInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOGGER.debug("======== Pre controller ========");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		LOGGER.debug("======== Post controller ========");
		
		if (modelAndView == null || modelAndView.getViewName().startsWith("redirect")) {
			return;
		}
		
		String path = request.getServletPath();
		String template = (String) modelAndView.getModelMap().get("template");
		if (StringUtils.isBlank(template)) {
			if (path.startsWith("/")) {
				path = path.substring(1);
			}
			modelAndView.getModelMap().addAttribute("template", path.toLowerCase());
		}
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		LOGGER.debug("======== After controller ========");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
