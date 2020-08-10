package com.example.HaulmontTestApp.backend.repository;


import com.example.HaulmontTestApp.backend.models.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipes,Integer> {
}

