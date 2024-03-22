package com.example.cookscorner.services;

import com.example.cookscorner.entities.Recipe;
import java.util.*;
public interface ElasticSearchService {
    void createIndex();
    void saveRecipe(Recipe recipe);

    List<Object> searchByField(String name);
}
