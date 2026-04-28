package com.aleitox.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ComidaRequestDto(
        @NotBlank
        @Size(max = 255)
        String nombre,

        @NotNull
        @DecimalMin(value = "0.00")
        BigDecimal calorias,

        @NotNull
        @DecimalMin(value = "0.00")
        BigDecimal proteinas,

        @NotNull
        @DecimalMin(value = "0.00")
        BigDecimal carbohidratos,

        @NotNull
        @DecimalMin(value = "0.00")
        BigDecimal grasas
) {
}
