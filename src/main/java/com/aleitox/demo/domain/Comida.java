package com.aleitox.demo.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Comida(
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
