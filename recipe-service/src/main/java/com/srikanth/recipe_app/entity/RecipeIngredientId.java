package com.srikanth.recipe_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RecipeIngredientId implements Serializable {
    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    @Column(name = "ingredient_id", nullable = false)
    private Long ingredientId;
}
