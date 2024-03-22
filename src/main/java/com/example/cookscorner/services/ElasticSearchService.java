package com.example.cookscorner.services;

import com.example.cookscorner.entities.Recipe;
import java.util.*;
public interface ElasticSearchService {
    public void createIndex();
    public void saveRecipe(Recipe recipe);

    public List<Object> searchByField(String name);
}
