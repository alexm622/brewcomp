package com.brewcompanion.brewcomp.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.brewcompanion.brewcomp.utils.minio.Buckets;
import com.brewcompanion.brewcomp.utils.minio.Minio;

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
