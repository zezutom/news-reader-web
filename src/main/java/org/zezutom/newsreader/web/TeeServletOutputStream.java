package org.zezutom.newsreader.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

import org.apache.commons.io.output.TeeOutputStream;

public class TeeServletOutputStream extends ServletOutputStream {

	private final TeeOutputStream target;
	
	public TeeServletOutputStream(OutputStream outOne, OutputStream outTwo) {
		target = new TeeOutputStream(outOne, outTwo);
	}
	
	@Override
	public void write(int b) throws IOException {
		target.write(b);		
	}
	
	@Override
	public void flush() throws IOException {
		super.flush();
		target.flush();
	}
	
	@Override
	public void close() throws IOException {
		super.close();
		target.close();
	}

}
