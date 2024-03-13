package com.example.cookscorner.controllers;

import com.example.cookscorner.dto.response.RecipeResponseDTO;
import com.example.cookscorner.entities.Ingredient;
import com.example.cookscorner.entities.Recipe;
import com.example.cookscorner.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cookscorner/recipe")
@CrossOrigin("*")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping()
    @Operation(summary = "Get all recipes", responses = {
            @ApiResponse(responseCode = "200", description = "List of recipes",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))),
    })
    public List<Recipe> getRecipes(){
        return recipeService.getRecipes();
    }

    @GetMapping("/category")
    @Operation(summary = "Get recipes by category", responses = {
            @ApiResponse(responseCode = "200", description = "List of recipes in the specified category",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))),
    })
    public List<Recipe> getRecipesByCategory(@RequestParam(name = "id") String category){
        return recipeService.getRecipesByCategory(category);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single recipe", responses = {
            @ApiResponse(responseCode = "200", description = "Recipe details",
                    content = @Content(schema = @Schema(implementation = RecipeResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    public RecipeResponseDTO getRecipe(@PathVariable(name = "id") UUID id){
        return recipeService.getRecipe(id);
    }

    @PostMapping()
    @Operation(summary = "Add a new recipe", responses = {
            @ApiResponse(responseCode = "200", description = "Recipe added successfully",
                    content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
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
