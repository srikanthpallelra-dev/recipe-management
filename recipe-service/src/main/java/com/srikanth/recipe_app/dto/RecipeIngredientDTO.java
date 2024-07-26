package com.srikanth.recipe_app.dto;

import com.srikanth.recipe_app.entity.RecipeIngredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDTO {
    private Long recipeId;
    private Long ingredientId;
    private String ingredientName;

    public static RecipeIngredientDTO mapToDTO(RecipeIngredient recipeIngredient) {
        RecipeIngredientDTO recipeIngredientDTO = new RecipeIngredientDTO();
        recipeIngredientDTO.setRecipeId(recipeIngredient.getRecipe().getId());
        recipeIngredientDTO.setIngredientId(recipeIngredient.getIngredient().getId());
        recipeIngredientDTO.setIngredientName(recipeIngredient.getIngredient().getName());
        return recipeIngredientDTO;
    }
}
