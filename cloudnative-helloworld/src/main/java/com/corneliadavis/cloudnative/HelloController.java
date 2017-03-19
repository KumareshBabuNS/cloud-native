package com.corneliadavis.cloudnative;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
public class HelloController {

    @RequestMapping("/")
    public Greeting hello(HttpServletResponse response) {

    	String specialization;
		specialization = System.getenv("SPECIALIZATION");
		if (specialization == null)
		    specialization = "Science"; // default specialization
		response.setStatus(200);
		Greeting greeting = new Greeting();
		greeting.setGreeting("Hello " + specialization + " Enthusiast!");

		return greeting;
    }
}
