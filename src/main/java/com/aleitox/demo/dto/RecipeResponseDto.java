package com.aleitox.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public record RecipeResponseDto(
        Integer id,
        String name,
        String description,
        List<RecipeIngredientResponseDto> ingredients,
        List<RecipeStepResponseDto> steps,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
