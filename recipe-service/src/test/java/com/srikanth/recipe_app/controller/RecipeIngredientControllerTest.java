package com.srikanth.recipe_app.controller;

import com.srikanth.recipe_app.dto.RecipeIngredientDTO;
import com.srikanth.recipe_app.service.RecipeIngredientServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeIngredientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    RecipeIngredientServiceImpl recipeIngredientService;
    private final String apiKey = "recipe";

    @Test
    void givenAddIngredientToRecipe_whenIngredientNotExist_thenReturnIngredient() throws Exception {
        RecipeIngredientDTO recipeIngredientDTO = new RecipeIngredientDTO();
        when(recipeIngredientService.addIngredientToRecipe(anyLong(), anyString())).thenReturn(recipeIngredientDTO);
        mockMvc.perform(post("/api/recipe_ingredient")
                .header("X-API-KEY", apiKey)
                .param("recipeId", "1")
                .param("ingredientName", "Ingredient")
        ).andExpect(status().isCreated());
    }

    @Test
    void givenDeleteIngredient_whenIngredientExist_thenDeleteIngredient() throws Exception {
        Mockito.doNothing().when(recipeIngredientService).removeIngredientFromRecipe(anyLong(), anyString());
        mockMvc.perform(delete("/api/recipe_ingredient")
                .header("X-API-KEY", apiKey)
                .param("recipeId", "1")
                .param("ingredientName", "Ingredient")
        ).andExpect(status().isNoContent());
    }
}
