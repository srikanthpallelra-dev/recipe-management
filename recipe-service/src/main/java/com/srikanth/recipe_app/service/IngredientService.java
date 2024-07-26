package com.srikanth.recipe_app.service;

import com.srikanth.recipe_app.dto.IngredientDTO;

public interface IngredientService {
    IngredientDTO getIngredientById(Long id);
    IngredientDTO createIngredient(String ingredientName);
    IngredientDTO updateIngredient(Long ingredientId, String ingredientName);
    void deleteIngredient(Long id);
}
