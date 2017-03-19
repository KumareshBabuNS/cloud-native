package com.corneliadavis.cloudnative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CloudnativeHelloworldApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void	helloWorld() throws Exception {

		String specialization = System.getenv("SPECIALIZATION");
		if (specialization == null) specialization = "Science";

		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello " + specialization +" Enthusiast!")))
				.andExpect(content().string(containsString(specialization)));
	}

	@Test
	public void	actuator() throws Exception {

		mockMvc.perform(get("/env"))
				.andExpect(status().isOk());
	}

}
