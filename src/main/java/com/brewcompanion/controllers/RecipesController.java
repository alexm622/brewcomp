package com.brewcompanion.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brewcompanion.brewcomp.objects.Recipe;

@Controller
@RequestMapping(value = {"/recipes"})
public class RecipesController {

	//landing page
	@GetMapping("")
	public String recipes(@RequestParam(name="page", required=false, defaultValue="1") Integer page, Model model) {
        List<Recipe> recipes = Recipe.getExampleData();
        //log the recipes
        for (Recipe recipe : recipes) {
            System.out.println(recipe.getName());
        }
        model.addAttribute("recipes", recipes);
        return "recipes";
    }


}