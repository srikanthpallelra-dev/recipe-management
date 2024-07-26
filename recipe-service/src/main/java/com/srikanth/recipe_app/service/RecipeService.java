package com.srikanth.recipe_app.service;

import com.srikanth.recipe_app.dto.RecipeDTO;
import com.srikanth.recipe_app.dto.RecipeResponse;

public interface RecipeService {
     RecipeResponse getAllRecipes(int pageNo, int pageSize, String sortBy, String sortDir);
     RecipeDTO getRecipeById(Long id);
     RecipeDTO createRecipe(RecipeDTO recipeDTO);
     RecipeDTO updateRecipe(RecipeDTO recipeDTO);
     void deleteRecipe(Long id);
}
