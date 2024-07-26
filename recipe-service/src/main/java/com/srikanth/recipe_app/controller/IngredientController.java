package com.srikanth.recipe_app.controller;

import com.srikanth.recipe_app.dto.IngredientDTO;
import com.srikanth.recipe_app.service.IngredientService;
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

@Tag(description = "The ingredient Api", name = "Ingredient")
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
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = IngredientDTO.class)))})
    @PostMapping(value = "/ingredient", produces = "application/json")
    public ResponseEntity<IngredientDTO> createIngredient(@Parameter(description = "Name of the ingredient") @RequestParam(name = "ingredientName", required = true) @NotBlank String ingredientName){
        return new ResponseEntity<>(ingredientService.createIngredient(ingredientName), HttpStatus.CREATED);
    }

    @Operation(summary = "Update given ingredient", description = "Updates the given ingredient")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = IngredientDTO.class)))})
    @PutMapping(value = "/ingredient", produces = "application/json")
    public ResponseEntity<IngredientDTO> updateIngredient(
            @Parameter(description = "Id of the ingredient") @RequestParam(name = "ingredientId", required = true) @Digits(integer = 15, fraction = 0) long ingredientId,
            @Parameter(description = "Name of the ingredient") @RequestParam(name = "ingredientName", required = true) String ingredientName){
        return new ResponseEntity<>(ingredientService.updateIngredient(ingredientId, ingredientName), HttpStatus.OK);
    }

    @Operation(summary = "Delete given ingredient", description = "Deletes given ingredient")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "successful operation", content = @Content(schema = @Schema(implementation = IngredientDTO.class)))})
    @DeleteMapping(value = "/ingredient/{id}", produces = "application/json")
    public ResponseEntity<String> deleteIngredient(@Parameter(description = "Id of the ingredient") @PathVariable @Digits(integer = 15, fraction = 0) Long id) {
        ingredientService.deleteIngredient(id);
        return new ResponseEntity<>(String.valueOf(id), HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Fetch ingredient by id", description = "Fetches ingredient by id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(type = "String")))})
    @GetMapping(value = "/ingredient/{id}", produces = "application/json")
    public ResponseEntity<IngredientDTO> getIngredientById(@Parameter(description = "Id of the ingredient") @PathVariable @Digits(integer = 15, fraction = 0) Long id) {
        return new ResponseEntity<>(ingredientService.getIngredientById(id), HttpStatus.OK);
    }
}