package com.aleitox.demo.controller;

import com.aleitox.demo.dto.ComidaRequestDto;
import com.aleitox.demo.dto.ComidaResponseDto;
import com.aleitox.demo.service.ServiceComida;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comidas")
public class ControllerComida {

    private final ServiceComida serviceComida;

    public ControllerComida(ServiceComida serviceComida) {
        this.serviceComida = serviceComida;
    }

    @PostMapping
    public ResponseEntity<ComidaResponseDto> create(@Valid @RequestBody ComidaRequestDto request) {
        ComidaResponseDto created = serviceComida.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ComidaResponseDto>> getAll() {
        return ResponseEntity.ok(serviceComida.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComidaResponseDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(serviceComida.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComidaResponseDto> update(@PathVariable Integer id,
                                                    @Valid @RequestBody ComidaRequestDto request) {
        return ResponseEntity.ok(serviceComida.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        serviceComida.delete(id);
        return ResponseEntity.noContent().build();
    }
}
