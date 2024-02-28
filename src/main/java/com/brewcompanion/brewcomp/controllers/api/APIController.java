package com.brewcompanion.brewcomp.controllers.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = {"/api"})
public class APIController {

	//get presigned url for labels
	// TODO this will return a json object with the url for the minio presigned url

	@GetMapping("/getlabeluploadurl")
	public String getLabelUploadUrl(@RequestParam(name="page", required=false, defaultValue="1") Integer page, Model model) {
		return "recipes";
	}
	

}
