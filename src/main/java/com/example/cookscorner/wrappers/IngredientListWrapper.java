package com.example.cookscorner.wrappers;

import com.example.cookscorner.dto.request.IngredientRequestDTO;
import lombok.Data;

import java.util.List;

@Data
public class IngredientListWrapper {
    private List<IngredientRequestDTO> ingredients;

}