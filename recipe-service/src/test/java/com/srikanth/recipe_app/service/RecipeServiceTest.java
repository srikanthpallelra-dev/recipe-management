package com.srikanth.recipe_app.service;

import com.srikanth.recipe_app.dto.RecipeDTO;
import com.srikanth.recipe_app.dto.RecipeResponse;
import com.srikanth.recipe_app.entity.Ingredient;
import com.srikanth.recipe_app.entity.Recipe;
import com.srikanth.recipe_app.entity.RecipeIngredient;
import com.srikanth.recipe_app.exception.NoSuchElementFoundException;
import com.srikanth.recipe_app.repository.IngredientRepository;
import com.srikanth.recipe_app.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {
    @InjectMocks
    private RecipeServiceImpl recipeService;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private IngredientRepository ingredientRepository;
    private Recipe recipe;
    private Ingredient ingredient;

    @BeforeEach
    void setup() {
        ingredient = new Ingredient("Ingredient");
        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Recipe");
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setRecipeIngredients(Set.of(new RecipeIngredient(recipe, ingredient)));
    }

    @Test
    void givenGetAllRecipes_whenRecipes_thenReturnRecipes() {
        Page<Recipe> recipes = new PageImpl<>(List.of(recipe));

        when(recipeRepository.findAll(isA(Pageable.class))).thenReturn(recipes);

        RecipeResponse recipeResponse = recipeService.getAllRecipes(0, 10, "id", "asc");
        assertEquals(1, recipeResponse.getContent().size());
        verify(recipeRepository).findAll(isA(Pageable.class));
    }

    @Test
    void givenCreateRecipe_whenIngredientExist_thenReturnRecipe() {
        when(ingredientRepository.existsByNameIgnoreCase(any())).thenReturn(true);
        when(ingredientRepository.findByNameIgnoreCase(any())).thenReturn(ingredient);
        when(recipeRepository.save(any())).thenReturn(recipe);
        RecipeDTO recipeDTO = recipeService.createRecipe(RecipeDTO.mapToDTO(recipe));

        assertEquals(recipe.getName(), recipeDTO.getName());
        verify(recipeRepository).save(any());
    }

    @Test
    void givenCreateRecipe_whenIngredientNotExist_thenReturnRecipe() {
        when(ingredientRepository.existsByNameIgnoreCase(any())).thenReturn(false);
        when(ingredientRepository.save(any())).thenReturn(ingredient);
        when(recipeRepository.save(any())).thenReturn(recipe);
        RecipeDTO recipeDTO = recipeService.createRecipe(RecipeDTO.mapToDTO(recipe));

        assertEquals(recipe.getName(), recipeDTO.getName());
        verify(recipeRepository).save(any());
    }

    @Test
    void givenGetRecipeById_whenRecipeExists_thenReturnRecipe() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        RecipeDTO recipeDTO = recipeService.getRecipeById(anyLong());

        assertEquals(recipe.getId(), recipeDTO.getId());
        assertEquals(recipe.getName(), recipeDTO.getName());
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    void givenGetRecipeById_whenRecipeNotExists_thenThrowNoSuchElementFoundException() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementFoundException.class, () -> {
            recipeService.getRecipeById(anyLong());
        });
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    void givenUpdateRecipe_whenRecipeNotExists_thenThrowNoSuchElementFoundException() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementFoundException.class, () -> {
            recipeService.updateRecipe(RecipeDTO.mapToDTO(recipe));
        });
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    void givenUpdateRecipe_whenRecipeExists_thenReturnRecipe() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any())).thenReturn(recipe);
        RecipeDTO recipeDTO = recipeService.updateRecipe(RecipeDTO.mapToDTO(recipe));

        assertEquals(recipe.getId(), recipeDTO.getId());
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).save(any());
    }

    @Test
    void givenDeleteRecipe_whenRecipeExists_thenDeleteRecipe() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        doNothing().when(recipeRepository).delete(any());
        recipeService.deleteRecipe(anyLong());

        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).delete(any());
    }

    @Test
    void givenDeleteRecipe_whenRecipeNotExists_thenThrowNoSuchElementFoundException() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementFoundException.class, () -> {
            recipeService.deleteRecipe(anyLong());
        });
        verify(recipeRepository).findById(anyLong());
    }
}
