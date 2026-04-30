package com.aleitox.demo.dto;

import java.time.LocalDateTime;

public record RecipeStepResponseDto(
        Integer id,
        Integer recipeId,
        Integer stepOrder,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
