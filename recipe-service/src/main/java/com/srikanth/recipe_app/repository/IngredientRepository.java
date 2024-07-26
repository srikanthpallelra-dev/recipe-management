package com.srikanth.recipe_app.repository;

import com.srikanth.recipe_app.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    boolean existsByNameIgnoreCase(String name);
    Ingredient findByNameIgnoreCase(String name);
}
