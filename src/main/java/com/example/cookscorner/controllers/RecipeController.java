package com.example.cookscorner.controllers;

import com.example.cookscorner.dto.response.RecipeResponseDTO;
import com.example.cookscorner.entities.Ingredient;
import com.example.cookscorner.entities.Recipe;
import com.example.cookscorner.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cookscorner/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @Operation(summary = "Get all recipes")
    @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Recipe[].class))})
    @GetMapping()
    public List<Recipe> getRecipes(){
        return recipeService.getRecipes();
    }

    @Operation(summary = "Get recipes by category")
    @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Recipe[].class))})
    @GetMapping("/category")
    public List<Recipe> getRecipesByCategory(@RequestParam(name = "id") String category){
        return recipeService.getRecipesByCategory(category);
    }

    @Operation(summary = "Get a recipe by ID")
    @ApiResponse(responseCode = "200", description = "Recipe retrieved successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = RecipeResponseDTO.class))})
    @GetMapping("/{id}")
    public RecipeResponseDTO getRecipe(@PathVariable(name = "id") UUID id){
        return recipeService.getRecipe(id);
    }

    @Operation(summary = "Add a new recipe")
    @ApiResponse(responseCode = "200", description = "Recipe added successfully",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UUID.class))})
    @PostMapping()
    public UUID addRecipe(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "difficulty") String difficulty,
            @RequestParam(name = "category") String category,
            @RequestParam(name = "preparationTime") String preparationTime,
            @RequestParam(name = "listOfIngredients") List<Ingredient> ingredients,
            @RequestParam(name = "image")MultipartFile image
            ){
        return recipeService.addRecipe(name, description, difficulty, category, preparationTime, ingredients, image);
    }

}
