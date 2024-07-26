package com.srikanth.recipe_app.dto;

import com.srikanth.recipe_app.entity.Ingredient;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {
    private Long id;

    @NotBlank(message = "Ingredient name cannot be blank")
    private String name;

    // Convert Ingredient entity to DTO
    public static IngredientDTO mapToDTO(Ingredient ingredient) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId(ingredient.getId());
        ingredientDTO.setName(ingredient.getName());
        return ingredientDTO;
    }
}
