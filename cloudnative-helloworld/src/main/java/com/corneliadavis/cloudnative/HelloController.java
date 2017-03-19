package com.corneliadavis.cloudnative;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
public class HelloController {

	@RequestMapping("/")
	public Greeting hello(@CookieValue(value = "userToken", required=false) String token, HttpServletResponse response) {

		if (token == null)
			response.setStatus(401);
		else {
			String name = CloudnativeHelloworldApplication.validTokens.get(token);
			if (name == null)
				response.setStatus(401);
			else {
				String specialization;
				specialization = System.getenv("SPECIALIZATION");
				if (specialization == null)
					specialization = "Science"; // default specialization
				Greeting greeting = new Greeting();
				greeting.setGreeting("Hello " + name + "!");
				greeting.setSpecialization(specialization);
				response.setStatus(200);
				return greeting;
			}
		}
		return null;
	}
}
