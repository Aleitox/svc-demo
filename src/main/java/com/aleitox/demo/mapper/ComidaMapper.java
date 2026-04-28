package com.aleitox.demo.mapper;

import com.aleitox.demo.domain.Comida;
import com.aleitox.demo.dto.ComidaRequestDto;
import com.aleitox.demo.dto.ComidaResponseDto;
import com.aleitox.demo.entity.ComidaEntity;
import org.springframework.stereotype.Component;

@Component
public class ComidaMapper {

    public Comida toDomain(ComidaEntity entity) {
        return new Comida(
                entity.getId(),
                entity.getNombre(),
                entity.getCalorias(),
                entity.getProteinas(),
                entity.getCarbohidratos(),
                entity.getGrasas(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ComidaEntity toEntity(Comida domain) {
        ComidaEntity entity = new ComidaEntity();
        entity.setId(domain.id());
        entity.setNombre(domain.nombre());
        entity.setCalorias(domain.calorias());
        entity.setProteinas(domain.proteinas());
        entity.setCarbohidratos(domain.carbohidratos());
        entity.setGrasas(domain.grasas());
        entity.setCreatedAt(domain.createdAt());
        entity.setUpdatedAt(domain.updatedAt());
        return entity;
    }

    public Comida toDomain(ComidaRequestDto request, Integer id) {
        return new Comida(
                id,
                request.nombre(),
                request.calorias(),
                request.proteinas(),
                request.carbohidratos(),
                request.grasas(),
                null,
                null
        );
    }

    public ComidaResponseDto toResponseDto(Comida domain) {
        return new ComidaResponseDto(
                domain.id(),
                domain.nombre(),
                domain.calorias(),
                domain.proteinas(),
                domain.carbohidratos(),
                domain.grasas(),
                domain.createdAt(),
                domain.updatedAt()
        );
    }
}
