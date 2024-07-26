package com.srikanth.recipe_app.controller;

import com.srikanth.recipe_app.dto.IngredientDTO;
import com.srikanth.recipe_app.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(description = "The ingredients Api", name = "Ingredients")
@SecurityRequirement(name = "RecipeSecurityScheme")
@RestController
@RequestMapping("/api")
public class IngredientController {
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(summary = "Create a new ingredient", description = "Creates a new ingredient")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "successful operation")})
    @PostMapping(value = "/ingredient", produces = "application/json")
    public ResponseEntity<IngredientDTO> createIngredient(@RequestParam(name = "ingredientName", required = true) @NotBlank String ingredientName){
        return new ResponseEntity<>(ingredientService.createIngredient(ingredientName), HttpStatus.CREATED);
    }

    @Operation(summary = "Update given ingredient", description = "Updates the given ingredient")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation")})
    @PutMapping(value = "/ingredient", produces = "application/json")
    public ResponseEntity<IngredientDTO> updateIngredient(
            @RequestParam(name = "ingredientId", required = true) @Digits(integer = 15, fraction = 0) long ingredientId,
            @RequestParam(name = "ingredientName", required = true) String ingredientName){
        return new ResponseEntity<>(ingredientService.updateIngredient(ingredientId, ingredientName), HttpStatus.OK);
    }

    @Operation(summary = "Create a new ingredient", description = "Creates a new ingredient")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation")})
    @DeleteMapping("/ingredient/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable @Digits(integer = 15, fraction = 0) Long id) {
        ingredientService.deleteIngredient(id);
        return new ResponseEntity<>(String.valueOf(id), HttpStatus.NO_CONTENT);
    }

    /**
     * Fetches ingredient by id
     *
     * @param id
     * @return A single ingredient
     */
    @GetMapping("/ingredient/{id}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable @Digits(integer = 15, fraction = 0) Long id) {
        return new ResponseEntity<>(ingredientService.getIngredientById(id), HttpStatus.OK);
    }
}
