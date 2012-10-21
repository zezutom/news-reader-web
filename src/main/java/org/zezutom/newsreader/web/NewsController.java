package org.zezutom.newsreader.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zezutom.newsreader.model.RssEntry;
import org.zezutom.newsreader.model.RssReader;

@Controller
public class NewsController {

	public static final String LOG_MESSAGE_FORMAT = "[%s]:%s";
	@Autowired
	private RssReader reader;
	
	@RequestMapping("/get/news/{category}")
	public @ResponseBody List<RssEntry> getNews(@PathVariable String category) {
		return reader.getFeeds(category);
	}
	
	public static String getLogMessage(HttpServletRequest request, String message) {
		return String.format(LOG_MESSAGE_FORMAT, request.getRequestURL(), message);
	}
}
