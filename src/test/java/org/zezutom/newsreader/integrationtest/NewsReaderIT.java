package org.zezutom.newsreader.integrationtest;

import static org.grep4j.core.Grep4j.egrep;
import static org.grep4j.core.fluent.Dictionary.executing;
import static org.grep4j.core.fluent.Dictionary.on;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;
import org.grep4j.core.model.Profile;
import org.grep4j.core.model.ProfileBuilder;
import org.grep4j.core.result.GrepResult;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class NewsReaderIT {
	
	public static final String NEWS_URL = "http://localhost:8080/get/news/{category}";
	
	public static final String NEWS_CATEGORY = "politics";
	
	private static Map<String, String>[] capturedResponse;
	
	ObjectMapper mapper = new ObjectMapper();
	
	private Profile profile = ProfileBuilder.newBuilder()
											.name("Local server log")
											.filePath("newsreader.log")
											.onLocalhost()
											.build();
	
	@BeforeClass
	public static void setUp() {
		capturedResponse = new RestTemplate().getForObject(NEWS_URL, Map[].class, NEWS_CATEGORY);
		assertNotNull(capturedResponse);
				
		for (Map<String, String> props : capturedResponse) {		
			assertTrue(NEWS_CATEGORY.equals(props.get("category")));
		}
	}
							
	@Test
	public void newsRequestShouldBeTriggeredExactlyOnce() {			
		assertThat(executing(egrep("[0-9]+ms$", on(profile))).totalLines(), is(1));
	}
	
	@Test
	public void newsShouldBePopulatedInLessThanTenSeconds() {
		GrepResult result = executing(egrep("[0-9]+ms$", on(profile))).getSingleResult();
		Pattern pattern = Pattern.compile("([0-9]+)ms$");
		Matcher matcher = pattern.matcher(result.getText());
		
		if (matcher.find()) {
			Long responseTime = new Long(matcher.group(1));			
			assertThat(responseTime, greaterThan(0L));
			assertThat(responseTime, lessThan(10000L));
		} else {
			fail("No response time could be found!");
		}				
	}
	
	@Test
	public void newsShouldBeReturnedInJsonFormat() throws IOException {
		GrepResult result = executing(egrep("\\[\\{.*\\}\\]", on(profile))).getSingleResult();
		Map<String, String>[] jsonResponse = mapper.readValue(result.getText().replaceFirst("^.*" + NEWS_CATEGORY + "\\]:", ""), Map[].class);
		
		assertNotNull(jsonResponse);
		assertThat(jsonResponse.length, is(capturedResponse.length));
		
		for (int i=0; i<capturedResponse.length; i++) {
			Map<String, String> props = jsonResponse[i];
			assertNotNull(props);
			
			for (String key : props.keySet()) {
				assertThat(props.get(key), is(capturedResponse[i].get(key)));
			}
			
		}
		
	}
			
}
