package com.example.cookscorner.entities;

import com.example.cookscorner.enums.Difficulty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    private Difficulty difficulty;

    //private String difficulty;

    private String category;

    private int preparationTime;

    private String imageUrl;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;
}
