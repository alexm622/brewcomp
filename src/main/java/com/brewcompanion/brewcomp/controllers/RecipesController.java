package com.brewcompanion.brewcomp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brewcompanion.brewcomp.Main;
import com.brewcompanion.brewcomp.objects.Recipe;

@Controller
@RequestMapping(value = {"/recipes"})
public class RecipesController {

	//landing page
	@GetMapping("")
	public String recipes(@RequestParam(name="page", required=false, defaultValue="1") Integer page, Model model) {
        List<Recipe> recipes = Recipe.getRandomData(12);
        //log the recipes
        for (Recipe recipe : recipes) {
            Main.getLogger().info(recipe.getName());
        }
        model.addAttribute("recipetable", toTableView(recipes));
        return "recipes";
    }

    private List<List<Recipe>> toTableView(List<Recipe> recipes) {
        //make a 3 column table
        List<List<Recipe>> table = new ArrayList<>();
        int i = 0;
        while (i < recipes.size()) {
            List<Recipe> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                if (i < recipes.size()) {
                    row.add(recipes.get(i));
                    i++;
                }
            }
            table.add(row);
        }
        return table;
    }


}