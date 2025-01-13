package se.mwthinker.rows_backend;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	/*
	@GetMapping("/")
	public Resource homePage() {
		return new ClassPathResource("static/index.html");
	}
	*/

	@GetMapping("/")
	public String homePage() {
		return "index.html"; // Redirects to static/index.html
	}

}
