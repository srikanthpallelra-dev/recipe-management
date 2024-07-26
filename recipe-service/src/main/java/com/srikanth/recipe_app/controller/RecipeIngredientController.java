package com.srikanth.recipe_app.controller;

import com.srikanth.recipe_app.dto.RecipeIngredientDTO;
import com.srikanth.recipe_app.service.RecipeIngredientService;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RecipeIngredientController {
    private final RecipeIngredientService recipeIngredientService;
    @Autowired
    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    /**
     * Adds given ingredient to recipe
     *
     * @param recipeId
     * @param ingredientName
     * @return Added ingredient of the recipe
     */
    @PostMapping("/recipe_ingredient")
    public ResponseEntity<RecipeIngredientDTO> addIngredientToRecipe(
            @RequestParam(value = "recipeId", required = true) @Digits(integer = 15, fraction = 0) long recipeId,
            @RequestParam(value = "ingredientName", required = true) @NotBlank String ingredientName){
        return new ResponseEntity<>(recipeIngredientService.addIngredientToRecipe(recipeId, ingredientName), HttpStatus.CREATED);
    }

    /**
     * Removes given ingredient from recipe
     *
     * @param recipeId
     * @param ingredientName
     * @return Name of the removed ingredient of recipe
     */
    @DeleteMapping("/recipe_ingredient")
    public ResponseEntity<String> removeIngredientFromRecipe(
            @RequestParam(value = "recipeId", required = true) @Digits(integer = 15, fraction = 0) long recipeId,
            @RequestParam(value = "ingredientName", required = true) @NotBlank String ingredientName) {
        recipeIngredientService.removeIngredientFromRecipe(recipeId, ingredientName);
        return new ResponseEntity<>(ingredientName, HttpStatus.NO_CONTENT);
    }
}
