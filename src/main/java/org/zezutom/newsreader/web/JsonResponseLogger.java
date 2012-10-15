package org.zezutom.newsreader.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class JsonResponseLogger extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = Logger.getLogger(JsonResponseLogger.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		long startTime = (Long) request.getAttribute("startTime");
		long endTime = System.currentTimeMillis(); 
		long executeTime = endTime - startTime;
		
		LOGGER.info("[" + executeTime + " ms]:" + response);		
	}
	
	

}
