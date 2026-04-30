package com.aleitox.demo.repository;

import com.aleitox.demo.domain.RecipeComponentType;
import com.aleitox.demo.entity.RecipeComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeComponentRepository extends JpaRepository<RecipeComponentEntity, Integer> {
    List<RecipeComponentEntity> findByRecipeId(Integer recipeId);

    Optional<RecipeComponentEntity> findByIdAndRecipeId(Integer id, Integer recipeId);

    boolean existsByRecipeIdAndComponentTypeAndIngredientId(Integer recipeId, RecipeComponentType componentType, Integer ingredientId);

    boolean existsByRecipeIdAndComponentTypeAndChildRecipeId(Integer recipeId, RecipeComponentType componentType, Integer childRecipeId);

    List<RecipeComponentEntity> findByChildRecipeId(Integer childRecipeId);

    void deleteByRecipeId(Integer recipeId);
}
