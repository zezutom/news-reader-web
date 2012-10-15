package org.zezutom.newsreader.integrationtest;

import java.util.Set;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import org.zezutom.newsreader.model.RssEntry;
import static org.junit.Assert.*;

public class NewsReaderIT {

	@Test
	public void newsShouldBeFilteredByCategory() {
		String category = "politics";
		Set<RssEntry> news = new RestTemplate().getForObject("http://localhost:8080/get/news/{category}", Set.class, category);
		assertNotNull(news);
	}
}
