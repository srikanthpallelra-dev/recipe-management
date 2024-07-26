package com.srikanth.recipe_app.service;

import com.srikanth.recipe_app.dto.IngredientDTO;
import com.srikanth.recipe_app.entity.Ingredient;
import com.srikanth.recipe_app.exception.ElementAlreadyExistsException;
import com.srikanth.recipe_app.exception.NoSuchElementFoundException;
import com.srikanth.recipe_app.repository.IngredientRepository;
import org.junit.jupiter.api.Assertions;
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
class IngredientServiceTest {
    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    private Ingredient ingredient;
    @BeforeEach
    void setup() {
        ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Ingredient");
    }

    @Test
    void givenGetIngredientById_whenIngredientExists_thenReturnIngredient() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));
        IngredientDTO ingredientDTO = ingredientService.getIngredientById(anyLong());

        assertEquals(ingredient.getId(), ingredientDTO.getId());
        assertEquals(ingredient.getName(), ingredientDTO.getName());
        verify(ingredientRepository).findById(anyLong());
    }

    @Test
    void givenGetIngredientById_whenIngredientNotExists_thenThrowNoSuchElementFoundException() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementFoundException.class, () -> ingredientService.getIngredientById(anyLong()));
        verify(ingredientRepository).findById(anyLong());
    }

    @Test
    void givenCreateIngredient_whenIngredientNotExists_thenReturnIngredient() {
        when(ingredientRepository.existsByNameIgnoreCase(anyString())).thenReturn(false);
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);
        IngredientDTO ingredientDTO = ingredientService.createIngredient(ingredient.getName());

        assertEquals(ingredient.getId(), ingredientDTO.getId());
        assertEquals(ingredient.getName(), ingredientDTO.getName());
        verify(ingredientRepository).existsByNameIgnoreCase(anyString());
        verify(ingredientRepository).save(any(Ingredient.class));
    }

    @Test
    void givenCreateIngredient_whenIngredientAlreadyExists_thenThrowElementAlreadyExistsException() {
        when(ingredientRepository.existsByNameIgnoreCase(any())).thenReturn(true);
        assertThrows(ElementAlreadyExistsException.class, () -> ingredientService.createIngredient(ingredient.getName()));
        verify(ingredientRepository).existsByNameIgnoreCase(any());
    }

    @Test
    void givenUpdateIngredient_whenIngredientNotExists_thenThrowNoSuchElementFoundException() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementFoundException.class, () -> ingredientService.updateIngredient(ingredient.getId(), ingredient.getName()));
        verify(ingredientRepository).findById(anyLong());
    }

    @Test
    void givenUpdateIngredient_whenIngredientExists_thenReturnIngredient() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);
        IngredientDTO ingredientDTO = ingredientService.updateIngredient(ingredient.getId(), ingredient.getName());

        assertEquals(ingredient.getId(), ingredientDTO.getId());
        assertEquals(ingredient.getName(), ingredientDTO.getName());
        verify(ingredientRepository).findById(anyLong());
        verify(ingredientRepository).save(any(Ingredient.class));
    }

    @Test
    void givenDeleteIngredient_whenIngredientExists_thenDeleteIngredient() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));
        doNothing().when(ingredientRepository).delete(any());
        ingredientService.deleteIngredient(anyLong());

        verify(ingredientRepository).findById(anyLong());
        verify(ingredientRepository).delete(any(Ingredient.class));
    }

    @Test
    void givenDeleteIngredient_whenIngredientNotExists_thenThrowNoSuchElementFoundException() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementFoundException.class, () -> ingredientService.deleteIngredient(anyLong()));
        verify(ingredientRepository).findById(anyLong());
    }
}
