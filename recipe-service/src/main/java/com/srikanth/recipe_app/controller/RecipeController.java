package com.srikanth.recipe_app.controller;

import com.srikanth.recipe_app.dto.RecipeDTO;
import com.srikanth.recipe_app.dto.RecipeResponse;
import com.srikanth.recipe_app.service.RecipeService;
import com.srikanth.recipe_app.util.AppConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Fetches all recipes based on the given filter parameters
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return Paginated recipe data
     */
    @GetMapping
    public RecipeResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return recipeService.getAllRecipes(pageNo, pageSize, sortBy, sortDir);
    }

    /**
     * Creates a new recipe
     *
     * @param recipeDTO
     * @return Created recipe data
     */
    @PostMapping("/recipe")
    public ResponseEntity<RecipeDTO> createRecipe(@Valid @RequestBody RecipeDTO recipeDTO){
        return new ResponseEntity<>(recipeService.createRecipe(recipeDTO), HttpStatus.CREATED);
    }

    /**
     * Updates given recipe
     *
     * @param recipeDTO
     * @return Updated recipe data
     */
    @PutMapping("/recipe")
    public ResponseEntity<RecipeDTO> updateRecipe(@Valid @RequestBody RecipeDTO recipeDTO){
        return new ResponseEntity<>(recipeService.updateRecipe(recipeDTO), HttpStatus.OK);
    }

    /**
     * Deletes recipe by id
     *
     * @param id
     * @return id of the deleted recipe
     */
    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable @Digits(integer = 15, fraction = 0) Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(String.valueOf(id), HttpStatus.NO_CONTENT);
    }

    /**
     * Fetches recipe by id
     *
     * @param id
     * @return A single recipe
     */
    @GetMapping("/recipe/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable @Digits(integer = 15, fraction = 0) Long id) {
        return new ResponseEntity<>(recipeService.getRecipeById(id), HttpStatus.OK);
    }
}
