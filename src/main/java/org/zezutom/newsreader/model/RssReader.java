package org.zezutom.newsreader.model;

import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zezutom.newsreader.web.NewsController;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RssReader {
	
	private static final Logger LOGGER = Logger.getLogger(NewsController.class);
	
	// How many news items should be read at once
	public static final int MAX_DEFAULT = 10;
	
	private String url;
	
	private int maxCount = MAX_DEFAULT;
	
	public RssReader(String url, int maxCount) {
		this.url = url;
		this.maxCount = maxCount;
	}
	
	private String getFeedUrl(String category) {
		StringBuilder sb = new StringBuilder(url);

		if (category != null && !category.isEmpty()) {
			sb.append(category);
		}

		return sb.toString();
	}

	public Set<RssEntry> getFeeds(String category) {
		// preserve the order of insertion
		Set<RssEntry> feeds = new LinkedHashSet<RssEntry>();
		
		try {
			URLConnection feedUrl = new URL(getFeedUrl(category)).openConnection();
			SyndFeedInput input = new SyndFeedInput();

			// Populate data
			SyndFeed feed = input.build(new XmlReader(feedUrl));

			List feedList = feed.getEntries();
			int size = feedList.size();

			if (size > maxCount) {
				size = maxCount;
			}

			for (int i = 0; i < size; i++) {
				SyndEntry syndEntry = (SyndEntry) feedList.get(i);
				RssEntry rssEntry = RssEntry.createInstance(category, syndEntry);				
				feeds.add(rssEntry);
			}

		} catch (Exception ex) {
			LOGGER.error(ex);
		}
		
		return feeds;
	}
}
