package com.corneliadavis.cloudnative;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = CloudnativeHelloworldApplication.class,
				properties = { "apikey:9e181285b03240d09a6a6595c160dc80", "bestsellersUrl:https://api.nytimes.com/svc/books/v3/lists.json",
								"spring.cloud.config.uri:http://noop"})
public class CloudnativeHelloworldConfigApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void	loginNamed() throws Exception {
		mockMvc.perform(post("/login").param("name", "Cornelia"))
				.andExpect(cookie().exists("userToken"));
	}

	@Test
	public void	booksGoodAPIkey() throws Exception {
		assertFalse(CloudnativeHelloworldApplication.validTokens.isEmpty());

		String validToken = CloudnativeHelloworldApplication.validTokens.keySet().iterator().next();
		String validName = CloudnativeHelloworldApplication.validTokens.get(validToken);

		String specialization = System.getenv("SPECIALIZATION");
		if (specialization == null) specialization = "Education";

		mockMvc.perform(get("/").cookie(new Cookie("userToken", validToken)))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello "+validName+"!")))
				.andExpect(content().string(containsString(specialization)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.books").isNotEmpty());
	}

}