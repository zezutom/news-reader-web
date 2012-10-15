package org.zezutom.newsreader.model.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.zezutom.newsreader.model.RssEntry;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class RssEntryTest {

	@Test
	public void allFieldsShouldBePopulatedAsExpected() {
		SyndEntry syndEntry = createSyndEntry();
		RssEntry rssEntry = createRssEntry("test", syndEntry);
		
		assertThat(rssEntry.getCategory(), is("test"));
		assertThat(rssEntry.getAuthor(), is(syndEntry.getAuthor()));
		assertThat(rssEntry.getDescription(), is(syndEntry.getDescription().getValue()));
		assertThat(rssEntry.getLink(), is(syndEntry.getLink()));
		assertThat(rssEntry.getTitle(), is(syndEntry.getTitle()));
		
		DateFormat dateFormat = new SimpleDateFormat(RssEntry.DATE_FORMAT);
		
		assertThat(rssEntry.getPublishedDate(), is(dateFormat.format(syndEntry.getPublishedDate())));
	}
	
	@Test
	public void entriesShouldBeEqualIfTheirFieldsAreEqual() {
		RssEntry rssEntryOne = createRssEntry("test");
		RssEntry rssEntryTwo = createRssEntry("test");
		
		assertEquals(rssEntryOne, rssEntryTwo);
	}
	
	@Test
	public void entriesShouldNotBeEqualIfTheirFieldsDiffer() {
		RssEntry rssEntryOne = createRssEntry("a");
		RssEntry rssEntryTwo = createRssEntry("b");
		
		assertFalse(rssEntryOne.equals(rssEntryTwo));		
	}
		
	private RssEntry createRssEntry(String category) {
		return createRssEntry(category, createSyndEntry());
	}
	
	private RssEntry createRssEntry(String category, SyndEntry syndEntry) {
		RssEntry entry = RssEntry.createInstance(category, syndEntry);
		assertNotNull(entry);
		
		return entry;
	}
	
	private SyndEntry createSyndEntry() {
		SyndEntry entry = new SyndEntryImpl();
		entry.setAuthor("test author");
		entry.setLink("test link");
		entry.setTitle("test title");
		entry.setPublishedDate(new Date());
		
		SyndContent description = new SyndContentImpl();
		description.setValue("test description");
		
		entry.setDescription(description);
		
		return entry;
	}

}
