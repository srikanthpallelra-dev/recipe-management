package com.srikanth.recipe_app.service;

import com.srikanth.recipe_app.dto.RecipeIngredientDTO;
import com.srikanth.recipe_app.entity.Ingredient;
import com.srikanth.recipe_app.entity.Recipe;
import com.srikanth.recipe_app.entity.RecipeIngredient;
import com.srikanth.recipe_app.exception.NoSuchElementFoundException;
import com.srikanth.recipe_app.repository.IngredientRepository;
import com.srikanth.recipe_app.repository.RecipeIngredientRepository;
import com.srikanth.recipe_app.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeIngredientServiceTest {
    @InjectMocks
    RecipeIngredientServiceImpl recipeIngredientService;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeIngredientRepository recipeIngredientRepository;

    private Ingredient ingredient;
    private Recipe recipe;
    private RecipeIngredient recipeIngredient;
    @BeforeEach
    void setup() {
        ingredient = new Ingredient("Ingredient");
        ingredient.setId(1L);
        recipe = new Recipe();
        recipe.setId(1L);
        recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
    }

    @Test
    void givenAddIngredientToRecipe_whenRecipeNotExists_thenThrowNoSuchElementFoundException() {
        when(recipeRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementFoundException.class, () -> {
            recipeIngredientService.addIngredientToRecipe(recipe.getId(), ingredient.getName());
        });
        verify(recipeRepository).findById(any());
    }

    @Test
    void givenAddIngredientToRecipe_whenIngredientNotExists_thenReturnRecipeIngredient() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(ingredientRepository.existsByNameIgnoreCase(anyString())).thenReturn(false);
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);
        when(recipeIngredientRepository.existsByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(false);
        when(recipeIngredientRepository.save(any(RecipeIngredient.class))).thenReturn(recipeIngredient);

        RecipeIngredientDTO recipeIngredientDTO = recipeIngredientService.addIngredientToRecipe(recipe.getId(), ingredient.getName());

        assertEquals(ingredient.getId(), recipeIngredientDTO.getIngredientId());
        assertEquals(ingredient.getName(), recipeIngredientDTO.getIngredientName());
    }

    @Test
    void givenAddIngredientToRecipe_whenIngredientExists_thenReturnRecipeIngredient() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(ingredientRepository.existsByNameIgnoreCase(anyString())).thenReturn(true);
        when(ingredientRepository.findByNameIgnoreCase(anyString())).thenReturn(ingredient);
        when(recipeIngredientRepository.existsByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(true);
        when(recipeIngredientRepository.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(Optional.of(recipeIngredient));

        RecipeIngredientDTO recipeIngredientDTO = recipeIngredientService.addIngredientToRecipe(recipe.getId(), ingredient.getName());

        assertEquals(ingredient.getId(), recipeIngredientDTO.getIngredientId());
        assertEquals(ingredient.getName(), recipeIngredientDTO.getIngredientName());
    }

    @Test
    void givenRemoveIngredientFromRecipe_whenRecipeNotExists_thenThrowNoSuchElementFoundException() {
        when(recipeRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementFoundException.class, () -> {
            recipeIngredientService.removeIngredientFromRecipe(recipe.getId(), ingredient.getName());
        });
        verify(recipeRepository).findById(any());
    }

    @Test
    void givenRemoveIngredientFromRecipe_whenIngredientNotExists_thenThrowNoSuchElementFoundException() {
        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        when(ingredientRepository.existsByNameIgnoreCase(any())).thenReturn(false);
        assertThrows(NoSuchElementFoundException.class, () -> {
            recipeIngredientService.removeIngredientFromRecipe(recipe.getId(), ingredient.getName());
        });
        verify(recipeRepository).findById(any());
        verify(ingredientRepository).existsByNameIgnoreCase(any());
    }

    @Test
    void givenRemoveIngredientFromRecipe_whenRecipeIngredientExists_thenDeleteRecipeIngredient() {
        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));
        when(ingredientRepository.existsByNameIgnoreCase(any())).thenReturn(true);
        when(ingredientRepository.findByNameIgnoreCase(any())).thenReturn(ingredient);
        when(recipeIngredientRepository.existsByRecipeIdAndIngredientId(any(), any())).thenReturn(true);
        doNothing().when(recipeIngredientRepository).deleteByRecipeIdAndIngredientId(any(), any());
        recipeIngredientService.removeIngredientFromRecipe(recipe.getId(), ingredient.getName());

        verify(recipeRepository).findById(any());
        verify(ingredientRepository).existsByNameIgnoreCase(any());
        verify(ingredientRepository).findByNameIgnoreCase(any());
        verify(recipeIngredientRepository).existsByRecipeIdAndIngredientId(any(), any());
        verify(recipeIngredientRepository).deleteByRecipeIdAndIngredientId(any(), any());
    }
}
