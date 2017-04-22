package com.corneliadavis.cloudnative;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RefreshScope
@RestController
public class HelloController {

	@Value("${bestsellersUrl}")
	private String NYTUrl;
	@Value("${apikey}")
	private String apiKey;
	@Value("${specialization}")
	private String specialization;

	@RequestMapping("/")
	public Greeting hello(@CookieValue(value = "userToken", required=false) String token, HttpServletResponse response) {

		if (token == null)
			response.setStatus(401);
		else {
			String name = CloudnativeHelloworldApplication.validTokens.get(token);
			if (name == null)
				response.setStatus(401);
			else {
				Greeting greeting = new Greeting();
				greeting.setGreeting("Hello " + name + "!");
				greeting.setSpecialization(specialization);

				String url = NYTUrl+"?api-key="+apiKey+"&list="+specialization;

				try {
					RestTemplate restTemplate = createRestTemplate();
					Map<String, Object> resp = restTemplate.getForObject(url, Map.class);
					Book[] books = parseBooks(resp);

					greeting.setBooks(books);
				} catch (RestClientException e) {
					greeting.setBooks(new Book[0]);
				}


				return greeting;
			}
		}
		return null;
	}

	private RestTemplate createRestTemplate() {
		// json mapping
		MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = createObjectMapper();
		mappingJacksonHttpMessageConverter.setObjectMapper(objectMapper);

		// create message converters list
		List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<HttpMessageConverter<?>>();
		httpMessageConverters.add(mappingJacksonHttpMessageConverter);

		// create rest template
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(httpMessageConverters);

		return restTemplate;
	}

	private ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		// use arrays
		objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
		return objectMapper;
	}

	private Book[] parseBooks(Map<String, Object> resp) {

		List<Book> books = new ArrayList<Book>();

		Object[] results = (Object[]) resp.get("results");
		for (int i=0; i<results.length; i++) {
			Map<String, Object> result = (Map<String, Object>) results[i];
			Map<String, String> bookDetails = (Map<String, String>) ((Object[])result.get("book_details"))[0];
			books.add(new Book(bookDetails.get("author"), bookDetails.get("title")));
		}

		return books.toArray(new Book[0]);
	}

}
