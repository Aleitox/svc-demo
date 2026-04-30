package com.aleitox.demo.repository;

import com.aleitox.demo.entity.RecipeIngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientEntity, Integer> {
    List<RecipeIngredientEntity> findByRecipeId(Integer recipeId);

    Optional<RecipeIngredientEntity> findByIdAndRecipeId(Integer id, Integer recipeId);

    boolean existsByRecipeIdAndIngredientId(Integer recipeId, Integer ingredientId);

    void deleteByRecipeId(Integer recipeId);
}
