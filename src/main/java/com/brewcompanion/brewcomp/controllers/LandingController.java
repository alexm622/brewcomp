package com.brewcompanion.brewcomp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = { "" })
public class LandingController {

	// redirect to landing page
	@GetMapping("/")
	public String landing() {
		return "landing";
	}

	// landing.html page
	@GetMapping("/landing")
	public String landing(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			Model model) {
		return "landing";
	}
}
