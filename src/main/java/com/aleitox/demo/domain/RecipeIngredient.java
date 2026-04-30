package com.aleitox.demo.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecipeIngredient(
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
