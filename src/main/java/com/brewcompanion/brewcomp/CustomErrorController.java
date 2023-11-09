package com.brewcompanion.brewcomp;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {


    @RequestMapping("/error")
    public String error(Model model) {
        model.addAttribute("message", "Oops! The page you requested cannot be found.");
        return "error";
    }

}


