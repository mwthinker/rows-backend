package se.mwthinker.rows.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@GetMapping("/")
	public String homePage() {
		return "index.html"; // Redirects to static/index.html
	}

}
