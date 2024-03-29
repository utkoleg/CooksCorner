package com.example.cookscorner.entities;

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
@ToString
public class Recipe {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    private String difficulty;

    private String category;

    private String preparationTime;

    private String imageUrl;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;

    private String author;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    private Double rating = 0.0;
}
