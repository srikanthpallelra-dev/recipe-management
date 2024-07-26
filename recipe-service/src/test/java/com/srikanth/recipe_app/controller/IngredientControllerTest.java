package com.srikanth.recipe_app.controller;

import com.srikanth.recipe_app.dto.IngredientDTO;
import com.srikanth.recipe_app.service.IngredientServiceImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IngredientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    IngredientServiceImpl ingredientService;

    private final String apiKey = "recipe";

    @Test
    void givenCreateIngredient_whenIngredientNotExist_thenReturnIngredient() throws Exception {
        IngredientDTO ingredientDTO = new IngredientDTO(1L, "Ingredient");

        when(ingredientService.createIngredient(anyString())).thenReturn(ingredientDTO);
        mockMvc.perform(post("/api/ingredient")
                .header("X-API-KEY", apiKey)
                .param("ingredientName", "Ingredient")
        ).andExpect(status().isCreated());
    }

    @Test
    void givenUpdateIngredient_whenIngredientExist_thenReturnIngredient() throws Exception {
        IngredientDTO ingredientDTO = new IngredientDTO(1L, "Ingredient");

        when(ingredientService.updateIngredient(anyLong(), anyString())).thenReturn(ingredientDTO);
        mockMvc.perform(put("/api/ingredient")
                .header("X-API-KEY", apiKey)
                .param("ingredientName", "Ingredient")
                .param("ingredientId", "1")
        ).andExpect(status().isOk());
    }

    @Test
    void givenGetIngredientById_whenIngredientExist_thenReturnIngredient() throws Exception {
        IngredientDTO ingredientDTO = new IngredientDTO(1L, "Ingredient");

        when(ingredientService.getIngredientById(anyLong())).thenReturn(ingredientDTO);
        mockMvc.perform(get("/api/ingredient/{id}", 1L)
                .header("X-API-KEY", apiKey)
        ).andExpect(status().isOk());
    }

    @Test
    void givenDeleteIngredient_whenIngredientExist_thenDeleteIngredient() throws Exception {
        Mockito.doNothing().when(ingredientService).deleteIngredient(anyLong());
        mockMvc.perform(delete("/api/ingredient/{id}", 1L)
                .header("X-API-KEY", apiKey)
        ).andExpect(status().isNoContent());
    }
}
