package org.zezutom.newsreader.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class LoggingFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class);

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpResponseWrapper responseWrapper = new HttpResponseWrapper(httpResponse);
		
		// Continue with the usual processing
		chain.doFilter(request, responseWrapper);
		
		// Log the resulting response
		LOGGER.info("[" + httpRequest.getRequestURL() + "]:" + responseWrapper.getContent());	
	}
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
