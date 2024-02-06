package com.brewcompanion.brewcomp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = { "/labels" })
public class LabelController {

	@GetMapping("labelMaker")
	public String labelMaker(Model model) {
        return "labelmaker/print-label";        
    }
	
    //editor stuff

	@GetMapping("editor")
	public String editor(Model model) {
		return "labelmaker/editor";
	}

}
