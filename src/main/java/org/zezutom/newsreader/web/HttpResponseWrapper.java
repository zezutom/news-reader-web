package org.zezutom.newsreader.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


/*
 * See:
 * http://www.wetfeetblog.com/servlet-filer-to-log-request-and-response-details-and-payload/431
 * */
public class HttpResponseWrapper extends HttpServletResponseWrapper {

	private HttpServletResponse response;

	private TeeServletOutputStream teeOut;
	
	private ByteArrayOutputStream baOut;
	
	public HttpResponseWrapper(HttpServletResponse response) {
		super(response);
		this.response = response;
	}
	
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (teeOut == null) {
			baOut = new ByteArrayOutputStream();
			teeOut = new TeeServletOutputStream(response.getOutputStream(), baOut);
		}
		return teeOut;	
	}
	
	public String getContent() {
		return baOut.toString();
	}
		
}
