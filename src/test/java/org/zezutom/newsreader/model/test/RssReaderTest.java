package org.zezutom.newsreader.model.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Test;
import org.zezutom.newsreader.model.RssEntry;
import org.zezutom.newsreader.model.RssReader;

public class RssReaderTest {

	public static final int NEWS_COUNT = 2;
	
	private RssReader newsReader = new RssReader("http://rss.news.yahoo.com/rss/", NEWS_COUNT);
	
	@Test
	public void theExpectedNumberOfNewsShouldBeReturned() {
		Set<RssEntry> news = getEntries("stocks");
		assertThat(news.size(), is(NEWS_COUNT));
	}
		
	@Test
	public void differentNewsShouldBeReadUnderDifferentCategories() {
		Set<RssEntry> stocks = newsReader.getFeeds("stocks");
		Set<RssEntry> politics = newsReader.getFeeds("politics");
		Set<RssEntry> business = newsReader.getFeeds("business");
		
		assertFalse(stocks.equals(politics));
		assertFalse(stocks.equals(business));
		assertFalse(business.equals(politics));
	}
	
	private Set<RssEntry> getEntries(String category) {
		Set<RssEntry> entries = newsReader.getFeeds(category);
		assertNotNull(entries);
		
		return entries;
	}
	
}
