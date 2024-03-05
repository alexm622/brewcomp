package com.brewcompanion.brewcomp.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = {"/auth"})
public class AuthController {

	@GetMapping("/login")
	public String getLogin() {
		return "auth/login";
	}

	@GetMapping("/logout")
	public String getLogout(@RequestParam String param) {
		return new String();
	}

	@GetMapping("/register")
	public String getRegister(@RequestParam String param) {
		return new String();
	}
	

}
