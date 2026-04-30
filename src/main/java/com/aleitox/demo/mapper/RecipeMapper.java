package com.aleitox.demo.mapper;

import com.aleitox.demo.domain.Recipe;
import com.aleitox.demo.dto.RecipeRequestDto;
import com.aleitox.demo.entity.RecipeEntity;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {

    public Recipe toDomain(RecipeEntity entity) {
        return new Recipe(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public RecipeEntity toEntity(Recipe domain) {
        RecipeEntity entity = new RecipeEntity();
        entity.setId(domain.id());
        entity.setName(domain.name());
        entity.setDescription(domain.description());
        entity.setCreatedAt(domain.createdAt());
        entity.setUpdatedAt(domain.updatedAt());
        return entity;
    }

    public Recipe toDomain(RecipeRequestDto request, Integer id) {
        return new Recipe(
                id,
                request.name(),
                request.description(),
                null,
                null
        );
    }
}
