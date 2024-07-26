package com.srikanth.recipe_app.controller;

import com.srikanth.recipe_app.dto.RecipeIngredientDTO;
import com.srikanth.recipe_app.dto.RecipeResponse;
import com.srikanth.recipe_app.service.RecipeIngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(description = "The Recipe Ingredient Api", name = "Recipe Ingredient")
@SecurityRequirement(name = "RecipeSecurityScheme")
@RestController
@RequestMapping("/api")
public class RecipeIngredientController {
    private final RecipeIngredientService recipeIngredientService;
    @Autowired
    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    @Operation(summary = "Add ingredient to recipe", description = "Adds ingredient to recipe by checking with name if not exits")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = RecipeIngredientDTO.class)))})
    @PostMapping(value = "/recipe_ingredient", produces = "application/json")
    public ResponseEntity<RecipeIngredientDTO> addIngredientToRecipe(
            @Parameter(description = "Id of the recipe") @RequestParam(value = "recipeId", required = true) @Digits(integer = 15, fraction = 0) long recipeId,
            @Parameter(description = "Name of the ingredient") @RequestParam(value = "ingredientName", required = true) @NotBlank String ingredientName){
        return new ResponseEntity<>(recipeIngredientService.addIngredientToRecipe(recipeId, ingredientName), HttpStatus.CREATED);
    }

    @Operation(summary = "Remove ingredient from recipe", description = "Removes ingredient from recipe but keeps in ingredients")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "successful operation", content = @Content(schema = @Schema(type = "String")))})
    @DeleteMapping(value = "/recipe_ingredient", produces = "application/json")
    public ResponseEntity<String> removeIngredientFromRecipe(
            @RequestParam(value = "recipeId", required = true) @Digits(integer = 15, fraction = 0) long recipeId,
            @RequestParam(value = "ingredientName", required = true) @NotBlank String ingredientName) {
        recipeIngredientService.removeIngredientFromRecipe(recipeId, ingredientName);
        return new ResponseEntity<>(ingredientName, HttpStatus.NO_CONTENT);
    }
}
