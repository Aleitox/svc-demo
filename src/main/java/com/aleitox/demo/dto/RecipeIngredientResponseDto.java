package com.aleitox.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecipeIngredientResponseDto(
        Integer id,
        Integer recipeId,
        Integer ingredientId,
        String ingredientName,
        BigDecimal quantity,
        String unit,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
