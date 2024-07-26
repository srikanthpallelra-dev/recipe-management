package com.srikanth.recipe_app.util;

public final class AppConstants {
    private AppConstants() {
        throw new UnsupportedOperationException(CLASS_CANNOT_BE_INSTANTIATED);
    }
    public static final String BASE_PACKAGE =  "com.srikanth.recipe_app";
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public  static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    public static final String SUCCESS = "Success";
    public static final String UNKNOWN_ERROR = "Unknown error occurred";
    public static final String CLASS_CANNOT_BE_INSTANTIATED = "This is a utility class and cannot be instantiated";
    public static final String NOT_FOUND_INGREDIENT = "Requested ingredient is not found";
    public static final String NOT_FOUND_RECIPE = "Requested recipe is not found";
    public static final String ALREADY_EXISTS_INGREDIENT = "Requested ingredient already exists (IngredientId: %d)";
}
