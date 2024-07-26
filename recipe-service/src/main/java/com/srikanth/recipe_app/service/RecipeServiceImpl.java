package com.srikanth.recipe_app.service;

import com.srikanth.recipe_app.dto.RecipeDTO;
import com.srikanth.recipe_app.dto.RecipeResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.srikanth.recipe_app.dto.RecipeDTO.mapToDTO;
import static com.srikanth.recipe_app.dto.RecipeDTO.mapToEntity;
import static com.srikanth.recipe_app.util.AppConstants.NOT_FOUND_RECIPE;

@Service
public class RecipeServiceImpl implements RecipeService{

    private static final Logger logger = LoggerFactory.getLogger(RecipeServiceImpl.class);

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    // Fetches recipes in pages by given conditions
    @Override
    public RecipeResponse getAllRecipes(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Recipe> recipes = recipeRepository.findAll(pageable);

        // get content for page object
        List<Recipe> listOfRecipes = recipes.getContent();
        List<RecipeDTO> content= listOfRecipes.stream().map(RecipeDTO::mapToDTO).toList();

        RecipeResponse recipeResponse = new RecipeResponse();
        recipeResponse.setContent(content);
        recipeResponse.setPageNo(recipes.getNumber());
        recipeResponse.setPageSize(recipes.getSize());
        recipeResponse.setTotalElements(recipes.getTotalElements());
        recipeResponse.setTotalPages(recipes.getTotalPages());
        recipeResponse.setLast(recipes.isLast());

        return recipeResponse;
    }

    // Creates ingredients if not exits and creates recipe by attaching ingredients
    @Override
    @Transactional
    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {
        final Recipe recipe = mapToEntity(recipeDTO);
        if(recipeDTO.getRecipeIngredients() != null && !recipeDTO.getRecipeIngredients().isEmpty()) {
            recipeDTO.getRecipeIngredients().forEach(recipeIngredientDTO -> {
                final Ingredient ingredient;
                if (ingredientRepository.existsByNameIgnoreCase(recipeIngredientDTO.getIngredientName())) {
                    ingredient = ingredientRepository.findByNameIgnoreCase(recipeIngredientDTO.getIngredientName());
                } else {
                    ingredient = ingredientRepository.save(new Ingredient(recipeIngredientDTO.getIngredientName()));
                }
                recipe.addRecipeIngredient(new RecipeIngredient(recipe, ingredient));
            });
        }
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setLastUpdatedAt(null);

        Recipe persistedRecipe = recipeRepository.save(recipe);

        return mapToDTO(persistedRecipe);
    }

    // Gives recipe fetching by id and throws exception if not exits
    @Override
    public RecipeDTO getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .map(RecipeDTO::mapToDTO)
                .orElseThrow(() -> {
                    logger.error(NOT_FOUND_RECIPE);
                    return new NoSuchElementFoundException(NOT_FOUND_RECIPE);
                });
    }

    // Updates recipe details only and throws exception if recipe not exits
    @Override
    @Transactional
    public RecipeDTO updateRecipe(RecipeDTO recipeDTO) {
        final Recipe recipe = recipeRepository.findById(recipeDTO.getId())
                .orElseThrow(() -> {
                    logger.error(NOT_FOUND_RECIPE);
                    return new NoSuchElementFoundException(NOT_FOUND_RECIPE);
                });

        recipe.setId(recipeDTO.getId());
        recipe.setName(recipeDTO.getName());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setRecipeLabel(recipeDTO.getRecipeLabel());
        recipe.setServings(recipeDTO.getServings());
        recipe.setCuisine(recipeDTO.getCuisine());
        recipe.setLastUpdatedAt(LocalDateTime.now());

        Recipe persistedRecipe = recipeRepository.save(recipe);
        return mapToDTO(persistedRecipe);
    }

    // Deletes recipe by id and throws exception if not exits
    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.findById(id).ifPresentOrElse(
                recipeRepository::delete,
                () -> {
                    logger.error(NOT_FOUND_RECIPE);
                    throw new NoSuchElementFoundException(NOT_FOUND_RECIPE);
                });
    }
}
