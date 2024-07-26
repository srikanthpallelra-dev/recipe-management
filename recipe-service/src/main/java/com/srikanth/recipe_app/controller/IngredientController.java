package com.srikanth.recipe_app.controller;

import com.srikanth.recipe_app.dto.IngredientDTO;
import com.srikanth.recipe_app.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(description = "Ingredients Rest Api", name = "Ingredients")
@RestController
@RequestMapping("/api")
public class IngredientController {
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Creates a new ingredient
     *
     * @param ingredientName
     * @return Created ingredient data
     */
    @PostMapping("/ingredient")
    @Operation(summary = "Create a new ingredient", description = "Creates a new ingredient")
    public ResponseEntity<IngredientDTO> createIngredient(@RequestParam(name = "ingredientName", required = true) @NotBlank String ingredientName){
        return new ResponseEntity<>(ingredientService.createIngredient(ingredientName), HttpStatus.CREATED);
    }

    /**
     * Updates given ingredient
     *
     * @param ingredientId
     * @param ingredientName
     * @return Updated ingredient data
     */
    @PutMapping("/ingredient")
    public ResponseEntity<IngredientDTO> updateIngredient(
            @RequestParam(name = "ingredientId", required = true) @Digits(integer = 15, fraction = 0) long ingredientId,
            @RequestParam(name = "ingredientName", required = true) String ingredientName){
        return new ResponseEntity<>(ingredientService.updateIngredient(ingredientId, ingredientName), HttpStatus.OK);
    }

    /**
     * Deletes ingredient by id
     *
     * @param id
     * @return id of the deleted ingredient
     */
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
