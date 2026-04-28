package com.aleitox.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ComidaResponseDto(
        Integer id,
        String nombre,
        BigDecimal calorias,
        BigDecimal proteinas,
        BigDecimal carbohidratos,
        BigDecimal grasas,
        LocalDate createdAt,
        LocalDate updatedAt
) {
}
