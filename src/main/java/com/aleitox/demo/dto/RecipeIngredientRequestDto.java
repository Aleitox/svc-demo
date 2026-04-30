package com.aleitox.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RecipeIngredientRequestDto(
        @NotNull
        @Positive
        Integer ingredientId,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal quantity,

        @NotBlank
        @Size(max = 50)
        String unit
) {
}
