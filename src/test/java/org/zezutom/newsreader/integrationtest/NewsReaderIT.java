package org.zezutom.newsreader.integrationtest;

import static org.grep4j.core.Grep4j.grep;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;
import org.grep4j.core.result.GrepResults;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class NewsReaderIT {

	public static final String CATEGORY_PARAM = "{category}";
	
	public static final String NEWS_URL = "http://localhost:8080/get/news/" + CATEGORY_PARAM;
	
	public static final String NEWS_CATEGORY = "politics";
	
	private Profile profile = ProfileBuilder.newBuilder()
											.name("Local server log")
											.filePath("newsreader.log")
											.onLocalhost()
											.build();
	
							
	@Test
	public void newsRequestShouldBeTriggeredExactlyOnce() {
		sendNewsRequest(NEWS_CATEGORY);
		GrepResults results = grep(NEWS_URL.replace(CATEGORY_PARAM, NEWS_CATEGORY), on(profile));
		assertThat(results.totalLines(), is(1));
	}
	
	
	@Test
	public void newsShouldBeReturnedInJsonFormat() {
		// TODO
	}
	
	private void sendNewsRequest(String category) {
		Map<String, String>[] requestBody = new RestTemplate().getForObject(NEWS_URL, Map[].class, category);
		assertNotNull(requestBody);
				
		for (Map<String, String> props : requestBody) {
			assertTrue(category.equals(props.get("category")));
		}
	}
}
