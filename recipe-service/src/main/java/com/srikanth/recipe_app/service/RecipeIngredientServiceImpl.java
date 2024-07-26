package com.srikanth.recipe_app.service;

import com.srikanth.recipe_app.dto.RecipeIngredientDTO;
import com.srikanth.recipe_app.entity.Ingredient;
import com.srikanth.recipe_app.entity.Recipe;
import com.srikanth.recipe_app.entity.RecipeIngredient;
import com.srikanth.recipe_app.exception.NoSuchElementFoundException;
import com.srikanth.recipe_app.repository.IngredientRepository;
import com.srikanth.recipe_app.repository.RecipeIngredientRepository;
import com.srikanth.recipe_app.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.srikanth.recipe_app.util.AppConstants.NOT_FOUND_INGREDIENT;
import static com.srikanth.recipe_app.util.AppConstants.NOT_FOUND_RECIPE;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private static final Logger logger = LoggerFactory.getLogger(RecipeIngredientServiceImpl.class);

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeIngredientServiceImpl(RecipeIngredientRepository recipeIngredientRepository, IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public RecipeIngredientDTO addIngredientToRecipe(Long recipeId, String ingredientName) {
        final Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> {
                    logger.error(NOT_FOUND_RECIPE);
                    return new NoSuchElementFoundException(NOT_FOUND_RECIPE);
                });

        final Ingredient ingredient;
        if (ingredientRepository.existsByNameIgnoreCase(ingredientName)) {
            ingredient = ingredientRepository.findByNameIgnoreCase(ingredientName);
        } else {
            ingredient = ingredientRepository.save(new Ingredient(ingredientName));
        }

        final RecipeIngredient recipeIngredient;
        if(recipeIngredientRepository.existsByRecipeIdAndIngredientId(recipe.getId(), ingredient.getId())){
            recipeIngredient = recipeIngredientRepository.findByRecipeIdAndIngredientId(recipe.getId(), ingredient.getId()).get();
        } else {
            recipeIngredient = recipeIngredientRepository.save(new RecipeIngredient(recipe, ingredient));
        }
        return RecipeIngredientDTO.mapToDTO(recipeIngredient);
    }

    @Override
    @Transactional
    public void removeIngredientFromRecipe(Long recipeId, String ingredientName) {
         recipeRepository.findById(recipeId)
                .orElseThrow(() -> {
                    logger.error(NOT_FOUND_RECIPE);
                    return new NoSuchElementFoundException(NOT_FOUND_RECIPE);
                });

        if (!ingredientRepository.existsByNameIgnoreCase(ingredientName)) {
            logger.error(NOT_FOUND_INGREDIENT);
            throw new NoSuchElementFoundException(NOT_FOUND_INGREDIENT);
        }
        Ingredient ingredient = ingredientRepository.findByNameIgnoreCase(ingredientName);

        if(recipeIngredientRepository.existsByRecipeIdAndIngredientId(recipeId, ingredient.getId())){
            recipeIngredientRepository.deleteByRecipeIdAndIngredientId(recipeId, ingredient.getId());
        }
    }
}
