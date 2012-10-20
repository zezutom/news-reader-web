package org.zezutom.newsreader.web;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zezutom.newsreader.model.RssEntry;
import org.zezutom.newsreader.model.RssReader;

@Controller
public class NewsController {

	@Autowired
	private RssReader reader;
	
	@RequestMapping("/get/news/{category}")
	public @ResponseBody List<RssEntry> getNews(@PathVariable String category) {
		return reader.getFeeds(category);
	}
}
