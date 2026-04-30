package com.aleitox.demo.mapper;

import com.aleitox.demo.domain.RecipeIngredient;
import com.aleitox.demo.dto.RecipeIngredientRequestDto;
import com.aleitox.demo.dto.RecipeIngredientResponseDto;
import com.aleitox.demo.entity.RecipeIngredientEntity;
import org.springframework.stereotype.Component;

@Component
public class RecipeIngredientMapper {

    public RecipeIngredient toDomain(RecipeIngredientEntity entity) {
        return new RecipeIngredient(
                entity.getId(),
                entity.getRecipe().getId(),
                entity.getIngredient().getId(),
                entity.getIngredient().getName(),
                entity.getQuantity(),
                entity.getUnit(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public RecipeIngredient toDomain(RecipeIngredientRequestDto request, Integer id, Integer recipeId, String ingredientName) {
        return new RecipeIngredient(
                id,
                recipeId,
                request.ingredientId(),
                ingredientName,
                request.quantity(),
                request.unit(),
                null,
                null
        );
    }

    public RecipeIngredientResponseDto toResponseDto(RecipeIngredient domain) {
        return new RecipeIngredientResponseDto(
                domain.id(),
                domain.recipeId(),
                domain.ingredientId(),
                domain.ingredientName(),
                domain.quantity(),
                domain.unit(),
                domain.createdAt(),
                domain.updatedAt()
        );
    }
}
