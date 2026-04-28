package com.aleitox.demo.service;

import com.aleitox.demo.domain.Comida;
import com.aleitox.demo.dto.ComidaRequestDto;
import com.aleitox.demo.dto.ComidaResponseDto;
import com.aleitox.demo.entity.ComidaEntity;
import com.aleitox.demo.mapper.ComidaMapper;
import com.aleitox.demo.repository.ComidaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class ServiceComida {

    private final ComidaRepository comidaRepository;
    private final ComidaMapper comidaMapper;

    public ServiceComida(ComidaRepository comidaRepository, ComidaMapper comidaMapper) {
        this.comidaRepository = comidaRepository;
        this.comidaMapper = comidaMapper;
    }

    public ComidaResponseDto create(ComidaRequestDto request) {
        Comida comida = comidaMapper.toDomain(request, null);
        ComidaEntity entityToSave = comidaMapper.toEntity(comida);
        LocalDate now = LocalDate.now();
        entityToSave.setCreatedAt(now);
        entityToSave.setUpdatedAt(now);

        ComidaEntity saved = comidaRepository.save(entityToSave);
        return comidaMapper.toResponseDto(comidaMapper.toDomain(saved));
    }

    public ComidaResponseDto getById(Integer id) {
        Objects.requireNonNull(id, "El id no puede ser null");
        ComidaEntity entity = findEntityById(id);
        return comidaMapper.toResponseDto(comidaMapper.toDomain(entity));
    }

    public List<ComidaResponseDto> getAll() {
        return comidaRepository.findAll()
                .stream()
                .map(comidaMapper::toDomain)
                .map(comidaMapper::toResponseDto)
                .toList();
    }

    public ComidaResponseDto update(Integer id, ComidaRequestDto request) {
        Objects.requireNonNull(id, "El id no puede ser null");
        ComidaEntity existing = findEntityById(id);
        Comida incoming = comidaMapper.toDomain(request, id);
        existing.setNombre(incoming.nombre());
        existing.setCalorias(incoming.calorias());
        existing.setProteinas(incoming.proteinas());
        existing.setCarbohidratos(incoming.carbohidratos());
        existing.setGrasas(incoming.grasas());
        existing.setUpdatedAt(LocalDate.now());

        ComidaEntity updated = comidaRepository.save(existing);
        return comidaMapper.toResponseDto(comidaMapper.toDomain(updated));
    }

    public void delete(Integer id) {
        Objects.requireNonNull(id, "El id no puede ser null");
        if (!comidaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comida no encontrada con id: " + id);
        }
        comidaRepository.deleteById(id);
    }

    private ComidaEntity findEntityById(Integer id) {
        Objects.requireNonNull(id, "El id no puede ser null");
        return comidaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comida no encontrada con id: " + id));
    }
}
