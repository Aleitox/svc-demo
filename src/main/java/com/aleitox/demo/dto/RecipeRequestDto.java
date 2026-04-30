package com.aleitox.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RecipeRequestDto(
        @NotBlank
        @Size(max = 255)
        String name,

        @Size(max = 5000)
        String description,

        @NotEmpty
        List<@Valid RecipeIngredientRequestDto> ingredients,

        @NotEmpty
        List<@Valid RecipeStepRequestDto> steps
) {
}
