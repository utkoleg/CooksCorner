package com.example.cookscorner.services.impl;

import com.example.cookscorner.dto.ingredient.IngredientRequestDTO;
import com.example.cookscorner.dto.recipe.RecipeResponseDTO;
import com.example.cookscorner.entities.Ingredient;
import com.example.cookscorner.entities.Recipe;
import com.example.cookscorner.entities.User;
import com.example.cookscorner.mappers.RecipeMapper;
import com.example.cookscorner.repositories.IngredientRepository;
import com.example.cookscorner.repositories.RecipeRepository;
import com.example.cookscorner.repositories.UserRepository;
import com.example.cookscorner.services.ElasticSearchService;
import com.example.cookscorner.services.FileUploadService;
import com.example.cookscorner.services.RecipeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final FileUploadService fileUploadService;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final RecipeMapper recipeMapper;
    private final ElasticSearchService elasticSearchService;


    @Override
    public List<RecipeResponseDTO> getRecipes() {
        return recipeRepository.findAll().stream().map(recipeMapper).toList();
    }

    @Override
    public UUID addRecipe(String name, String description, String difficulty, String category,
                          String preparationTime, List<IngredientRequestDTO> ingredientRequestDTOs,
                          MultipartFile image, HttpSession session) {

        List<Ingredient> ingredients = ingredientRequestDTOs.stream()
                .map(this::convertToIngredient)
                .collect(Collectors.toList());

        ingredientRepository.saveAll(ingredients);
        Recipe recipe;
        UUID userId = (UUID) session.getAttribute("authorizedUserId");
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        String author = user.getName() + " " + user.getSurname();
        try {
            recipe = Recipe.builder()
                    .name(name)
                    .description(description)
                    .difficulty(difficulty.toUpperCase())
                    .category(category)
                    .preparationTime(preparationTime)
                    .ingredients(ingredients)
                    .imageUrl(fileUploadService.uploadFile(image))
                    .author(author)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        user.getRecipes().add(recipe);
        recipeRepository.saveAndFlush(recipe);
        elasticSearchService.saveRecipe(recipe);
        return recipe.getId();
    }

    @Override
    @Transactional
    public UUID saveRecipeToUser(UUID recipeId, HttpSession session) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));
        User userTemp = (User) session.getAttribute("authorizedUser");
        User user = userRepository.findById(userTemp.getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!user.getSavedRecipes().contains(recipe)) {
            user.getSavedRecipes().add(recipe);
            log.info("Recipe saved");
        } else {
            user.getSavedRecipes().remove(recipe);
            log.info("Recipe unsaved");
        }

        userRepository.save(user);
        return user.getId();
    }

    @Override
    public UUID likeRecipe(UUID recipeId, HttpSession session) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));
        User userTemp = (User) session.getAttribute("authorizedUser");
        User user = userRepository.findById(userTemp.getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (user.getLikedRecipes().contains(recipe)) {
            user.getLikedRecipes().remove(recipe);
            log.info("recipe unliked");
        } else {
            user.getLikedRecipes().add(recipe);
            log.info("recipe liked");
        }

        userRepository.save(user);
        return user.getId();
    }

    @Override
    public List<RecipeResponseDTO> search(String name) {
        return recipeRepository.findByNameContaining(name).stream()
                .map(recipeMapper)
                .collect(Collectors.toList());
    }


    @Override
    public List<RecipeResponseDTO> getRecipesByCategory(String category) {
        return recipeRepository.findAllByCategory(category).stream().map(recipeMapper).toList();
    }

    @Override
    public RecipeResponseDTO getRecipe(UUID id) {
        return recipeMapper.apply(recipeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe not found")));
    }

    public Ingredient convertToIngredient(IngredientRequestDTO ingredientRequestDTO) {
        return Ingredient.builder()
                .name(ingredientRequestDTO.getName())
                .quantity(ingredientRequestDTO.getQuantity())
                .measurement(ingredientRequestDTO.getMeasurement())
                .build();
    }
}