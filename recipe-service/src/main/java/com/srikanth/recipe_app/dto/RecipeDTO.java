package com.srikanth.recipe_app.dto;

import com.srikanth.recipe_app.entity.Recipe;
import com.srikanth.recipe_app.entity.RecipeLabel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private Long id;

    @NotBlank(message = "Recipe name cannot be blank")
    private String name;

    @NotBlank(message = "Recipe cuisine cannot be blank")
    private String cuisine;

    @NotNull(message = "Recipe servings cannot be null")
    @Min(1)
    @Max(5)
    private Integer servings;

    @NotNull(message = "Recipe label cannot be null")
    private RecipeLabel recipeLabel;

    private Set<RecipeIngredientDTO> recipeIngredients;
    private String description;
    private String createdAt;
    private String lastUpdatedAt;

    // Convert Recipe entity to DTO
    public static RecipeDTO mapToDTO(Recipe recipe) {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(recipe.getId());
        recipeDTO.setName(recipe.getName());
        recipeDTO.setRecipeLabel(recipe.getRecipeLabel());
        recipeDTO.setCuisine(recipe.getCuisine());
        recipeDTO.setServings(recipe.getServings());

        recipeDTO.setDescription(recipe.getDescription());
        recipeDTO.setCreatedAt(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(recipe.getCreatedAt()));
        if(recipe.getLastUpdatedAt()!=null)
            recipeDTO.setLastUpdatedAt(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(recipe.getLastUpdatedAt()));

        recipeDTO.setRecipeIngredients(recipe.getRecipeIngredients().stream().map(RecipeIngredientDTO::mapToDTO).collect(Collectors.toSet()));

        return recipeDTO;
    }

    // Convert Recipe DTO to Entity
    public static Recipe mapToEntity(RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeDTO.getId());
        recipe.setName(recipeDTO.getName());
        recipe.setRecipeLabel(recipeDTO.getRecipeLabel());
        recipe.setCuisine(recipeDTO.getCuisine());
        recipe.setServings(recipeDTO.getServings());
        recipe.setDescription(recipeDTO.getDescription());

        return recipe;
    }
}
