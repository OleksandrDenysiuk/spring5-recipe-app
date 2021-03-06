package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}")
    public String showById(@PathVariable String id,
                            Model model){
        model.addAttribute("recipe", recipeService.getRecipeById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeForm";
    }

    @GetMapping("/recipe/{id}/update")
    public String update(@PathVariable(name = "id") String recipeId, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/recipeForm";
    }

    @PostMapping("/recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand){
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + savedRecipeCommand.getId();
    }

    @GetMapping("/recipe/{id}/delete")
    public String delete(@PathVariable(name = "id") String recipeId, Model model){
        recipeService.deleteById(Long.valueOf(recipeId));
        return "redirect:/";
    }
}
