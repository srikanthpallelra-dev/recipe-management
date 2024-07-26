package com.srikanth.recipe_app.controller;

import com.srikanth.recipe_app.dto.IngredientDTO;
import com.srikanth.recipe_app.dto.RecipeDTO;
import com.srikanth.recipe_app.dto.RecipeResponse;
import com.srikanth.recipe_app.service.RecipeService;
import com.srikanth.recipe_app.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(description = "The Recipe Api", name = "Recipe")
@SecurityRequirement(name = "RecipeSecurityScheme")
@RestController
@RequestMapping("/api")
@Validated
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(summary = "Fetches all recipes with pagination", description = "Fetches all recipes with pagination")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = RecipeResponse.class)))})
    @GetMapping(produces = "application/json")
    public ResponseEntity<RecipeResponse> getAllRecipes(
            @Parameter(description = "Page number") @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @Parameter(description = "Page size") @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @Parameter(description = "Sort column") @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(recipeService.getAllRecipes(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @Operation(summary = "Create a new recipe", description = "Creates ingredients first if not exits and then creates a new recipe after that Attaches created ingredients to the new recipe")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = RecipeDTO.class)))})
    @PostMapping(value = "/recipe", produces = "application/json")
    public ResponseEntity<RecipeDTO> createRecipe(@Parameter(description = "Recipe request") @Valid @RequestBody RecipeDTO recipeDTO){
        return new ResponseEntity<>(recipeService.createRecipe(recipeDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update given recipe", description = "Updates given recipe data only and ignores ingredients data attached to it. Use Recipe Ingredient Apis to add/remove ingredients to/from recipe")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = RecipeDTO.class)))})
    @PutMapping(value = "/recipe", produces = "application/json")
    public ResponseEntity<RecipeDTO> updateRecipe(@Parameter(description = "Recipe request") @Valid @RequestBody RecipeDTO recipeDTO){
        return new ResponseEntity<>(recipeService.updateRecipe(recipeDTO), HttpStatus.OK);
    }

    @Operation(summary = "Delete given recipe", description = "Detaches ingredients from given recipe and then deletes the recipe")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "successful operation", content = @Content(schema = @Schema(type = "String")))})
    @DeleteMapping(value = "/recipe/{id}", produces = "application/json")
    public ResponseEntity<String> deleteRecipe(@Parameter(description = "Id of the recipe") @PathVariable @Digits(integer = 15, fraction = 0) Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(String.valueOf(id), HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Fetch recipe by id", description = "Fetches recipe by id with ingredients data")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = RecipeDTO.class)))})
    @GetMapping(value = "/recipe/{id}", produces = "application/json")
    public ResponseEntity<RecipeDTO> getRecipeById(@Parameter(description = "Id of the recipe") @PathVariable @Digits(integer = 15, fraction = 0) Long id) {
        return new ResponseEntity<>(recipeService.getRecipeById(id), HttpStatus.OK);
    }
}
