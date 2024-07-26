package com.srikanth.recipe_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srikanth.recipe_app.dto.RecipeDTO;
import com.srikanth.recipe_app.dto.RecipeResponse;
import com.srikanth.recipe_app.entity.RecipeLabel;
import com.srikanth.recipe_app.service.RecipeServiceImpl;
import com.srikanth.recipe_app.util.AppConstants;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    RecipeServiceImpl recipeService;
    private final String apiKey = "recipe";

    @Test
    void givenGetAllRecipes_whenRecipesExist_thenReturnRecipes() throws Exception {
        RecipeResponse recipeResponse = new RecipeResponse();
        when(recipeService.getAllRecipes(anyInt(), anyInt(), anyString(), anyString())).thenReturn(recipeResponse);
        mockMvc.perform(get("/api")
                .header("X-API-KEY", apiKey)
                .param("pageNo", AppConstants.DEFAULT_PAGE_NUMBER)
                .param("pageSize", AppConstants.DEFAULT_PAGE_SIZE)
                .param("sortBy", AppConstants.DEFAULT_SORT_BY)
                .param("SortDir", AppConstants.DEFAULT_SORT_DIRECTION)
        ).andExpect(status().isOk());
    }

    @Test
    void givenCreateRecipe_whenNewRecipe_thenReturnRecipe() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(1L);
        recipeDTO.setName("recipe");
        recipeDTO.setCuisine("recipe");
        recipeDTO.setServings(5);
        recipeDTO.setRecipeLabel(RecipeLabel.VEG);
        String content = (new ObjectMapper()).writeValueAsString(recipeDTO);
        when(recipeService.createRecipe(any())).thenReturn(recipeDTO);
        mockMvc.perform(post("/api/recipe")
                .header("X-API-KEY", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isCreated());
    }

    @Test
    void givenUpdateRecipe_whenRecipeExist_thenReturnRecipe() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(1L);
        recipeDTO.setName("recipe");
        recipeDTO.setCuisine("recipe");
        recipeDTO.setServings(5);
        recipeDTO.setRecipeLabel(RecipeLabel.VEG);
        String content = (new ObjectMapper()).writeValueAsString(recipeDTO);
        when(recipeService.createRecipe(any(RecipeDTO.class))).thenReturn(recipeDTO);
        mockMvc.perform(put("/api/recipe")
                .header("X-API-KEY", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk());
    }

    @Test
    void givenGetRecipeById_whenRecipeExist_thenReturnRecipe() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(1L);
        recipeDTO.setName("recipe");
        recipeDTO.setCuisine("recipe");
        recipeDTO.setServings(5);
        recipeDTO.setRecipeLabel(RecipeLabel.VEG);

        when(recipeService.getRecipeById(anyLong())).thenReturn(recipeDTO);
        mockMvc.perform(get("/api/recipe/{id}", 1L)
                .header("X-API-KEY", apiKey)
        ).andExpect(status().isOk());
    }

    @Test
    void givenDeleteRecipe_whenRecipeExist_thenDeleteRecipe() throws Exception {
        Mockito.doNothing().when(recipeService).deleteRecipe(anyLong());
        mockMvc.perform(delete("/api/recipe/{id}", 1L)
                .header("X-API-KEY", apiKey)
        ).andExpect(status().isNoContent());
    }
}
