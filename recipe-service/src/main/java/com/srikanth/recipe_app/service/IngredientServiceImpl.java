package com.srikanth.recipe_app.service;

import com.srikanth.recipe_app.dto.IngredientDTO;
import com.srikanth.recipe_app.entity.Ingredient;
import com.srikanth.recipe_app.exception.ElementAlreadyExistsException;
import com.srikanth.recipe_app.exception.NoSuchElementFoundException;
import com.srikanth.recipe_app.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.srikanth.recipe_app.util.AppConstants.ALREADY_EXISTS_INGREDIENT;
import static com.srikanth.recipe_app.util.AppConstants.NOT_FOUND_INGREDIENT;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static final Logger logger = LoggerFactory.getLogger(IngredientServiceImpl.class);
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientDTO getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .map(IngredientDTO::mapToDTO)
                .orElseThrow(() -> {
                    logger.error(NOT_FOUND_INGREDIENT);
                    return new NoSuchElementFoundException(NOT_FOUND_INGREDIENT);
                });
    }

    @Override
    public IngredientDTO createIngredient(String ingredientName) {
        if (ingredientRepository.existsByNameIgnoreCase(ingredientName)) {
            logger.error(ALREADY_EXISTS_INGREDIENT);
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_INGREDIENT);
        } else {
            final Ingredient ingredient = ingredientRepository.save(new Ingredient(ingredientName));
            return IngredientDTO.mapToDTO(ingredient);
        }
    }

    @Override
    public IngredientDTO updateIngredient(Long ingredientId, String ingredientName) {
        final Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_INGREDIENT));
        ingredient.setName(ingredientName);
        return IngredientDTO.mapToDTO(ingredientRepository.save(ingredient));
    }

    @Override
    public void deleteIngredient(Long id) {
        ingredientRepository.findById(id).ifPresentOrElse(
                ingredientRepository::delete,
                () -> {
                    logger.error(NOT_FOUND_INGREDIENT);
                    throw new NoSuchElementFoundException(NOT_FOUND_INGREDIENT);
                });
    }
}
