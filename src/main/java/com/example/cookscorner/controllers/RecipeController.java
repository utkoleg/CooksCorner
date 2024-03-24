package com.example.cookscorner.controllers;

import com.example.cookscorner.dto.ingredient.IngredientRequestDTO;
import com.example.cookscorner.dto.recipe.RecipeResponseDTO;
import com.example.cookscorner.entities.CustomResponse;
import com.example.cookscorner.entities.Recipe;
import com.example.cookscorner.services.CommentService;
import com.example.cookscorner.services.ElasticSearchService;
import com.example.cookscorner.services.RecipeService;
import com.example.cookscorner.wrappers.IngredientListWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cookscorner/recipe")
@CrossOrigin("*")
@Tag(name = "Recipe controller", description = "Endpoints for recipe management")
public class RecipeController {
    private final RecipeService recipeService;
    private final ElasticSearchService elasticSearchService;
    private final CommentService commentService;

    @GetMapping()
    @Operation(summary = "Get all recipes", responses = {
            @ApiResponse(responseCode = "200", description = "List of recipes",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))),
    })
    public List<RecipeResponseDTO> getRecipes(){
        return recipeService.getRecipes();
    }

    @GetMapping("/category")
    @Operation(summary = "Get recipes by category", responses = {
            @ApiResponse(responseCode = "200", description = "List of recipes in the specified category",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Recipe.class)))),
    })
    public List<RecipeResponseDTO> getRecipesByCategory(@RequestParam(name = "id") String categoryId){
        return recipeService.getRecipesByCategory(categoryId);
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


    @PostMapping("/add_recipe")
    @Operation(summary = "Add a new recipe",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recipe added successfully",
                            content = @Content(schema = @Schema(implementation = UUID.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<CustomResponse> addRecipe(
            @RequestParam("name")
            @Parameter(description = "Name of the recipe", required = true) String name,
            @RequestParam("description")
            @Parameter(description = "Description of the recipe", required = true) String description,
            @RequestParam("difficulty")
            @Parameter(description = "Difficulty level of the recipe", required = true) String difficulty,
            @RequestParam("category")
            @Parameter(description = "Category of the recipe", required = true) String category,
            @RequestParam("preparationTime")
            @Parameter(description = "Preparation time of the recipe", required = true) String preparationTime,
            @ModelAttribute
            @Parameter(description = "List of ingredients", required = true) IngredientListWrapper ingredientWrapper,
            @RequestParam("image")
            @Parameter(description = "Image of the recipe", required = true) MultipartFile image,
            HttpSession session
    ) {
        List<IngredientRequestDTO> ingredientRequestDTOs = ingredientWrapper.getIngredients();
        return recipeService.addRecipe(name, description, difficulty, category, preparationTime, ingredientRequestDTOs, image, session);
    }

    @PostMapping("/save")
    @Operation(summary = "Save a recipe to the user's saved recipes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recipe saved successfully",
                            content = @Content(schema = @Schema(implementation = UUID.class))),
                    @ApiResponse(responseCode = "404", description = "Recipe or user not found")
            })
    public ResponseEntity<CustomResponse> saveRecipe(
            @Parameter(description = "The ID of the recipe to save") @RequestParam UUID recipeId,
            HttpSession session
    ) {
        return recipeService.saveRecipeToUser(recipeId, session);
    }

    @PostMapping("/like")
    @Operation(summary = "Like a recipe",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recipe liked successfully",
                            content = @Content(schema = @Schema(implementation = UUID.class))),
                    @ApiResponse(responseCode = "404", description = "Recipe or user not found")
            })
    public ResponseEntity<CustomResponse> likeRecipe(
            @Parameter(description = "The ID of the recipe to like") @RequestParam UUID recipeId,
            HttpSession session
    ) {
        return recipeService.likeRecipe(recipeId, session);
    }

    @GetMapping("/search")
    @Operation(summary = "Search for recipes in Elasticsearch",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of recipes matching the search criteria",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RecipeResponseDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "No recipes found")
            })
    public List<Object> getElasticRecipes(@RequestParam @Parameter(description = "The name or description to search for in recipes") String name) {
        return elasticSearchService.searchByField(name);
    }

    @PostMapping("/{id}/add-comment")
    public ResponseEntity<UUID> addComment(
            @PathVariable("id") UUID recipeId,
            @RequestParam("commentText") String commentText,
            @RequestParam("rating") Double rating,
            HttpSession session
    ){
        return commentService.addComment(recipeId, commentText, session, rating);
    }
}
